package com.zechariah.springbootmongodb.service;

import com.zechariah.springbootmongodb.exeception.TodoCollectionException;
import com.zechariah.springbootmongodb.model.TodoDTO;
import com.zechariah.springbootmongodb.repository.TodoRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;


public interface TodoService {
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;
    public List<TodoDTO> getAllTodos();
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException;
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException;
    public void deleteTodo(String id)throws TodoCollectionException;
}
