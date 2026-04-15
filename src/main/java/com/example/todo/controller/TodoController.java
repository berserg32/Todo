    package com.example.todo.controller;

    import java.util.List;

    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    import com.example.todo.model.Status;
    import com.example.todo.model.Todo;
    import com.example.todo.model.TodoRequest;
    import com.example.todo.service.TodoService;


    @RestController
    public class TodoController {
        private final TodoService todoService;

        public TodoController(TodoService todoService){
            this.todoService = todoService;
        }
        
        @PostMapping("/todos")
        public Todo todoCreate(@RequestBody TodoRequest q){
            return todoService.create(q);
        }

        @GetMapping("/todos/{id}")
        public Todo todoGet(@PathVariable Long id){
            return todoService.get(id);
        }

        @DeleteMapping("/todos/{id}")
        public Todo todoDelete(@PathVariable Long id){
            return todoService.delete(id);
        }

        @PutMapping("/todos/{id}")
        public Todo todoPut(@PathVariable Long id, @RequestBody TodoRequest q){
            return todoService.put(id, q);
        }

        @GetMapping("/todos")
        public List<Todo> todoGetFilter(@RequestParam(required=false) Status status){
            return todoService.getFilter(status);
        }

    }
