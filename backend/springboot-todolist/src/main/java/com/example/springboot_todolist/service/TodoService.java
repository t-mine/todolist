package com.example.springboot_todolist.service;

import java.util.List;
import java.util.Optional;

import com.example.springboot_todolist.entity.TodoEntity;

public interface TodoService {
    List<TodoEntity> findAllTodo();

    Optional<TodoEntity> findById(Long id);

    TodoEntity saveTodo(TodoEntity TodoEntity);

    TodoEntity updateTodo(TodoEntity TodoEntity);

    void deleteTodo(Long id);
}
