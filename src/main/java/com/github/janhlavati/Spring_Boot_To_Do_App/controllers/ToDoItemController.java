package com.github.janhlavati.Spring_Boot_To_Do_App.controllers;

import com.github.janhlavati.Spring_Boot_To_Do_App.models.ToDoItem;
import com.github.janhlavati.Spring_Boot_To_Do_App.repositories.ToDoItemRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;

@Controller
public class ToDoItemController {

    private final Logger logger = LoggerFactory.getLogger(ToDoItemController.class);

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @GetMapping("/")
    public ModelAndView index() {
        logger.debug("request to GET index");
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("todoItems", toDoItemRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/todo/{id}")
    public String updateToDoItem(@PathVariable("id") Long id, @Valid ToDoItem toDoItem, BindingResult result, Model model) {
        if(result.hasErrors()) {
            toDoItem.setId(id);
            return "update-todo-item";
        }

        toDoItem.setModifiedDate(Instant.now());
        toDoItemRepository.save(toDoItem);
        return "redirect:/";
    }

}
