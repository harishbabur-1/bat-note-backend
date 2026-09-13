package com.harishsai.HelloWorld.repository;

import com.harishsai.HelloWorld.Models.Todo;

import com.harishsai.HelloWorld.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByUser(User user);
}
