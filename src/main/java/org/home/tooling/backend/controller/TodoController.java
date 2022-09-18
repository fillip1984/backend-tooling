package org.home.tooling.backend.controller;

import java.util.List;

import org.home.tooling.backend.model.Todo;
import org.home.tooling.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Todo (as in bank todo) controller
 */
@RestController
@RequestMapping("/api/v1/todos")
@Slf4j
public class TodoController {

    @Autowired
    private TodoService todoService;

    // create
    @PostMapping
    public ResponseEntity<Todo> save(@RequestBody Todo todo) {
        log.info("Saving todo: {}", todo);
        return ResponseEntity.ok(todoService.save(todo));
    }

    // read
    @GetMapping
    public ResponseEntity<List<Todo>> findAll() {
        log.info("Retrieving all todos");
        return ResponseEntity.ok(todoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> findById(@PathVariable Long id) {
        log.info("Retrieving todo by id: {}", id);
        return ResponseEntity.ok(todoService.findById(id));
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody Todo todo) {
        log.info("Updating todo: {}", todo);

        // limit which fields can be edited
        var existingTodo = todoService.findById(id);
        existingTodo.setName(todo.getName());
        existingTodo.setDescription(todo.getDescription());
        return ResponseEntity.ok(todoService.save(existingTodo));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        log.info("Deleting todo by id: {}", id);
        return ResponseEntity.ok(todoService.deleteById(id));
    }
}