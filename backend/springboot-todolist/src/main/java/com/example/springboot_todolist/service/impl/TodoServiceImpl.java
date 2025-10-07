package com.example.springboot_todolist.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springboot_todolist.entity.TodoEntity;
import com.example.springboot_todolist.repository.TodoRepository;
import com.example.springboot_todolist.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository TodoRepository;

    public TodoServiceImpl(TodoRepository TodoRepository) {
        this.TodoRepository = TodoRepository;
    }

    @Override
    public List<TodoEntity> findAllTodo() {
        return TodoRepository.findAll();
    }

    @Override
    public Optional<TodoEntity> findById(Long id) {
        return TodoRepository.findById(id);
    }

    @Override
    public TodoEntity saveTodo(TodoEntity TodoEntity) {
        return TodoRepository.save(TodoEntity);
    }

    @Override
    public TodoEntity updateTodo(TodoEntity TodoEntity) {
        return TodoRepository.save(TodoEntity);
    }

    @Override
    public void deleteTodo(Long id) {
        TodoRepository.deleteById(id);
    }
}