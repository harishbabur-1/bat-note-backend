package com.harishsai.HelloWorld.service;

import com.harishsai.HelloWorld.Models.Todo;
import com.harishsai.HelloWorld.Models.User;
import com.harishsai.HelloWorld.repository.TodoRepository;
import com.harishsai.HelloWorld.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    public Todo createTodo(Todo todo , String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        todo.setUser(user);
        return (todoRepository.save(todo));
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(()-> new RuntimeException("file not found"));
    }

    public List<Todo> getAllTodos(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return todoRepository.findByUser(user);
    }

    public Page<Todo> getAllTodoPages(int page , int size) {
        Pageable pageable = PageRequest.of(page , size);
        return todoRepository.findAll(pageable);
    }

    public Todo updateTodo(@NonNull Todo todo , String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Todo Exsisting = todoRepository.findById(todo.getId()).orElseThrow(() ->
                new RuntimeException("Todo not found")
        );
        if(!Exsisting.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You are not allowed to update this Todo"
            );
        }

        Exsisting.setId(todo.getId());
        Exsisting.setDescription(todo.getDescription());
        Exsisting.setTitle(todo.getTitle());
        Exsisting.setIsCompleted(todo.getIsCompleted());
        return todoRepository.save(Exsisting);
    }

    public void deleteTodo(Long id, String  email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Todo todo = todoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Todo not found")
        );
        if(!todo.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You are not allowed to delete this Todo"
            );
        }
         todoRepository.delete(todo );
    }
}
