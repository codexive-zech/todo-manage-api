package com.zechariah.springbootmongodb.controller;

import com.zechariah.springbootmongodb.model.TodoDTO;
import com.zechariah.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodo(){
        List<TodoDTO> todoList = todoRepository.findAll();
        if (todoList.size() > 0){
            return new ResponseEntity<List<TodoDTO>>(todoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No TODO activity is Available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
        try {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable String id){
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()){
            return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo not found with ID " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String id, @RequestBody TodoDTO todo){
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()){
            TodoDTO todoSave = todoOptional.get();
            todoSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoSave.getTodo());
            todoSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoSave.getDescription());
            todoSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoSave.getCompleted());
            todoSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoSave);
            return new ResponseEntity<>(todoSave, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo not found with ID " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable String id) {
        try {
            todoRepository.deleteById(id);
            return new ResponseEntity<>("Todo Activity with ID (" + id + ") was Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
