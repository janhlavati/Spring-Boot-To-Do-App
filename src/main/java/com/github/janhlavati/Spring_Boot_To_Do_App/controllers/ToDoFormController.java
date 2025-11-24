package com.github.janhlavati.Spring_Boot_To_Do_App.controllers;

import com.github.janhlavati.Spring_Boot_To_Do_App.repositories.ToDoItemRepository;
import com.github.janhlavati.Spring_Boot_To_Do_App.models.ToDoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ToDoFormController {

    private final Logger logger = LoggerFactory.getLogger(ToDoFormController.class);

    @Autowired
    ToDoItemRepository toDoItemRepository;

    @GetMapping("/create-todo")
    public String showCreateForm(ToDoItem toDoItem) {
        return "add-todo-item";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        ToDoItem toDoItem = toDoItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("TodoItem id id: " + id + " not found"));

        model.addAttribute("todo", toDoItem);
        return "update-todo-item";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") long id, Model model) {
        ToDoItem toDoItem = toDoItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("TodoItem id id: " + id + " not found"));
        toDoItemRepository.delete(toDoItem);
        return "redirect:/";
    }
}
