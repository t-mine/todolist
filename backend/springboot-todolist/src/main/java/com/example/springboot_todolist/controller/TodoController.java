package com.example.springboot_todolist.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_todolist.entity.TodoEntity;
import com.example.springboot_todolist.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService employeeService;

    public TodoController(TodoService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<TodoEntity> findAllTodo() {
        return employeeService.findAllTodo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoEntity> findTodoById(@PathVariable("id") Long id) {
       return employeeService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TodoEntity saveTodo(@RequestBody TodoEntity employeeEntity) {
        return employeeService.saveTodo(employeeEntity);
    }

    @PutMapping
    public TodoEntity updateTodo(@RequestBody TodoEntity employeeEntity) {
        return employeeService.saveTodo(employeeEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {
        employeeService.deleteTodo(id);
    }
}