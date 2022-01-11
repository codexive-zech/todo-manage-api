package com.zechariah.springbootmongodb.controller;

import com.zechariah.springbootmongodb.exeception.TodoCollectionException;
import com.zechariah.springbootmongodb.model.TodoDTO;
import com.zechariah.springbootmongodb.repository.TodoRepository;
import com.zechariah.springbootmongodb.service.TodoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoServiceImp todoServiceImp;

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodo(){
        List<TodoDTO> todoList = todoServiceImp.getAllTodos();
        return new ResponseEntity<>(todoList, todoList.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
        try {
            todoServiceImp.createTodo(todo);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable String id){
        try {
            return new ResponseEntity<>(todoServiceImp.getSingleTodo(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String id, @RequestBody TodoDTO todo){
        try {
            todoServiceImp.updateTodo(id, todo);
            return new ResponseEntity<>("Updated Todo with ID " + id, HttpStatus.OK);
        } catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable String id) {
        try {
            todoServiceImp.deleteTodo(id);
            return new ResponseEntity<>("Todo Activity with ID (" + id + ") was Deleted Successfully", HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
