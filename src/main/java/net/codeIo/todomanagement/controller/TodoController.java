package net.codeIo.todomanagement.controller;

import lombok.AllArgsConstructor;
import net.codeIo.todomanagement.dto.TodoDto;
import net.codeIo.todomanagement.service.TodoService;
import net.codeIo.todomanagement.service.impl.TodoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody  TodoDto todoDto){
        TodoDto savedEntity = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @GetMapping("/getTodo/{id}")
    public ResponseEntity<TodoDto> getById(@PathVariable  Long id){
        TodoDto todoDto = todoService.getTodo(id);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @GetMapping("/getAllTodos")
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> dtos = todoService.getAllTodos();
        return new ResponseEntity<>(dtos , HttpStatus.OK);
    }

    @PutMapping("/updateTodo/{id}")
    public ResponseEntity<TodoDto> updateTodo( @RequestBody TodoDto todoDto ,@PathVariable Long id){
        TodoDto updatedTodo = todoService.updateTodo(todoDto, id);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
}
