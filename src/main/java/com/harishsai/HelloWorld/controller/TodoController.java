package com.harishsai.HelloWorld.controller;

import com.harishsai.HelloWorld.Models.Todo;
import com.harishsai.HelloWorld.service.TodoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.*;


@ApiResponses(value = {
        @ApiResponse(responseCode = "404" , description = "todo not found") ,
        @ApiResponse(responseCode = "200" , description = "todo created sucessfully")
})

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/get")
    public void getTodo() {

    }



    @GetMapping("name")
    String ReqName(@RequestParam("name") String id) {
        return id+" is beautiful";
    }

    @GetMapping("romantic")
    String printRom(@RequestParam("n1") String id1 ,@RequestParam("n2") String id2) {
        return id1+" and "+id2+" are romantic";
    }

    @PostMapping("post")
    ResponseEntity<Todo> Create(@Valid @RequestBody Todo todo , Authentication authentication) {
        String email = authentication.getName();

        System.out.println("Controller Executed");
        return new ResponseEntity<>(todoService.createTodo(todo , email) , HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodo(@PathVariable Long id ) {
        try {
            System.out.print("get request is running .....");
            Todo createdTodo = todoService.getTodoById(id);
            return new ResponseEntity<>(createdTodo,HttpStatus.OK);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    ResponseEntity<List<Todo>> getAll( Authentication authentication) {
        String email = authentication.getName();
        return new ResponseEntity<>(todoService.getAllTodos(email) , HttpStatus.OK) ;
    }

    @GetMapping("page")
    ResponseEntity<Page<Todo>> getallPage(@RequestParam int page , @RequestParam int size) {
        return new ResponseEntity<>(todoService.getAllTodoPages(page , size),HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<Todo> updateTodo(@RequestBody Todo todo , Authentication authentication) {
        String email = authentication.getName();
        return new ResponseEntity<>(todoService.updateTodo(todo , email) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void DeleteTodo(@PathVariable Long id , Authentication authentication) {
        String email = authentication.getName();
        todoService.deleteTodo(id , email);
    }
}
