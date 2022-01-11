package com.zechariah.springbootmongodb.service;

import com.zechariah.springbootmongodb.exeception.TodoCollectionException;
import com.zechariah.springbootmongodb.model.TodoDTO;
import com.zechariah.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImp implements TodoService{

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.todoAlreadyExist());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
       Optional<TodoDTO> optionalTodo = todoRepository.findById(id);
       if (!optionalTodo.isPresent()){
           throw  new TodoCollectionException(TodoCollectionException.todoNotFound(id));
       } else {
           return optionalTodo.get();
       }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todoList = todoRepository.findAll();
        if (todoList.size() > 0){
            return todoList;
        } else {
            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        Optional<TodoDTO> todoSameName = todoRepository.findByTodo(todo.getTodo());

        if (todoOptional.isPresent()){
            if (todoSameName.isPresent() && !todoSameName.get().getTodo().equals(id)){
                throw new TodoCollectionException(TodoCollectionException.todoAlreadyExist());
            }
            TodoDTO todoUpdate = todoOptional.get();

            todoUpdate.setTodo(todo.getTodo());
            todoUpdate.setDescription(todo.getDescription());
            todoUpdate.setCompleted(todo.getCompleted());
            todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoUpdate);
        } else {
            throw new TodoCollectionException(TodoCollectionException.todoNotFound(id));
        }
    }

    @Override
    public void deleteTodo(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        if (!todoOptional.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.todoNotFound(id));
        } else {
            todoRepository.deleteById(id);
        }
    }
}
