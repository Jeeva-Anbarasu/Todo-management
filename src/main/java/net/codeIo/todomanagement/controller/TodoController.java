package net.codeIo.todomanagement.controller;

import lombok.AllArgsConstructor;
import net.codeIo.todomanagement.dto.TodoDto;
import net.codeIo.todomanagement.service.TodoService;
import net.codeIo.todomanagement.service.impl.TodoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private TodoService todoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody  TodoDto todoDto){
        TodoDto savedEntity = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @GetMapping("/getTodo/{id}")
    public ResponseEntity<TodoDto> getById(@PathVariable  Long id){
        TodoDto todoDto = todoService.getTodo(id);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @GetMapping("/getAllTodos")
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> dtos = todoService.getAllTodos();
        return new ResponseEntity<>(dtos , HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateTodo/{id}")
    public ResponseEntity<TodoDto> updateTodo( @RequestBody TodoDto todoDto ,@PathVariable Long id){
        TodoDto updatedTodo = todoService.updateTodo(todoDto, id);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteTodo/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return new ResponseEntity<>("Deleted Successfully" , HttpStatus.OK);
    }

    //Build complete to do REST API
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @PatchMapping("/complete/{id}")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id){
        TodoDto updatedTodo = todoService.completeTodo(id);
        return new ResponseEntity<>(updatedTodo , HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @PatchMapping("/incomplete/{id}")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable Long id){
        TodoDto updatedTodo = todoService.incompleteTodo(id);
        return new ResponseEntity<>(updatedTodo , HttpStatus.OK);
    }


}
