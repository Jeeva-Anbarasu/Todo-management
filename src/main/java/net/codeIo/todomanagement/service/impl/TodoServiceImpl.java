package net.codeIo.todomanagement.service.impl;

import lombok.AllArgsConstructor;
import net.codeIo.todomanagement.dto.TodoDto;
import net.codeIo.todomanagement.entity.Todo;
import net.codeIo.todomanagement.exception.ResourceNotFoundException;
import net.codeIo.todomanagement.repository.TodoRepository;
import net.codeIo.todomanagement.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private ModelMapper modelMapper;

    private TodoRepository todoRepository;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);

        Todo savedToDo = todoRepository.save(todo);

        TodoDto entity = modelMapper.map(savedToDo, TodoDto.class);
        return entity;
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo entity = todoRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("Not found");
        });
        TodoDto todoDto = modelMapper.map(entity, TodoDto.class);
        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        List<TodoDto> todoDtos = todos.stream()
                .map(todo ->
                        modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
        return todoDtos;
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> {
                    return new ResourceNotFoundException("Not Found");
                }
        );

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo savedEntity = todoRepository.save(todo);

        return modelMapper.map(savedEntity, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Not found with id : %s " , id)));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found")
        );

        todo.setCompleted(Boolean.TRUE);
        Todo savedEntity = todoRepository.save(todo);

        return modelMapper.map(savedEntity , TodoDto.class);
    }

    @Override
    public TodoDto incompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found")
        );
        todo.setCompleted(Boolean.FALSE);
        Todo savedEntity = todoRepository.save(todo);

        return modelMapper.map(savedEntity , TodoDto.class);
    }
}
