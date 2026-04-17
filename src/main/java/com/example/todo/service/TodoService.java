package com.example.todo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.todo.model.Status;
import com.example.todo.model.Todo;
import com.example.todo.model.TodoRequest;
import com.example.todo.repository.TodoRepository;


@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository){
        this.repository = repository;
    }

    private Todo findById(Long id){
        return repository.findById(id)
            .orElseThrow(NoSuchElementException::new);
    }

    public Todo create(TodoRequest q){
        return repository.save(
            new Todo(
                q.getTitle(),
                q.getDescription(),
                q.getStatus()
            )
        );
    }

    // 🟢 @Cacheable → Паттерн Cache-Aside (чтение)
    // 1. Ключ в Redis: todos::<id>
    // 2. Spring сначала делает GET из Redis. Если есть → возвращает сразу (БД не трогается)
    // 3. Если нет (cache miss) → вызывает findById(id) → идёт SELECT в БД → результат кладёт в Redis (SET) → возвращает
    // 🗣 Что говорить: "Кэшируем чтение по ID. При повторных запросах одного таска убираем нагрузку с БД."
    @Cacheable(value = "todos", key = "#id")
    public Todo get(Long id){
        return findById(id);
    }

    // 🟠 @CacheEvict → Инвалидация после удаления
    // 1. Метод выполняется полностью: findById → repository.delete (DELETE в БД)
    // 2. После успешного выполнения Spring делает DEL todos::<id> в Redis
    // 3. Evict по умолчанию срабатывает ПОСЛЕ метода, чтобы при ошибке/откате кэш не удалился зря
    // 🗣 Что говорить: "Удаляю ключ из кэша после удаления из БД, чтобы не отдавать stale-данные. Безопаснее, чем удалять до запроса."
    @CacheEvict(value = "todos", key = "#id")
    public Todo delete(Long id){
        Todo temp = findById(id);
        repository.delete(temp);
        return temp;
    }

    // 🟠 @CacheEvict → Инвалидация после обновления
    // 1. Обновляем поля → repository.save (UPDATE в БД)
    // 2. После коммита Spring удаляет старый кэш: DEL todos::<id>
    // 💡 Можно заменить на @CachePut (сразу запишет обновлённый объект в Redis), но Evict проще и исключает race-conditions
    // 🗣 Что говорить: "Инвалидирую кэш после UPDATE. На следующем GET кэш заполнится заново уже с новыми данными."
    @CacheEvict(value = "todos", key = "#id")
    public Todo put(Long id, TodoRequest q){
        Todo temp = findById(id);
        temp.setDescription(q.getDescription());
        temp.setStatus(q.getStatus());
        temp.setTitle(q.getTitle());
        return repository.save(temp);
    }

    // ⚠️ БЕЗ аннотаций! Почему?
    // 1. Фильтр возвращает список. Ключ был бы вроде todos::filter::ACTIVE
    // 2. При put/delete одного элемента Spring НЕ знает, что нужно инвалидировать ЭТОТ список
    // 3. Если закэшировать → получится рассинхрон: в списке старый объект, хотя в БД он уже изменён/удалён
    // 🗣 Что говорить: "Списки не кэширую через @Cacheable, потому что обновление одного Todo не инвалидирует кэш списка автоматически. 
    //    Это классическая проблема cache inconsistency. Если нужно ускорить — добавлю короткий TTL или отдельное имя кэша с ручным evict."
    public List<Todo> getFilter(Status status){
        if(status == null) return repository.findAll();
        return repository.findByStatus(status);
    }
}
