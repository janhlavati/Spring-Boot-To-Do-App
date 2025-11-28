package com.github.janhlavati.Spring_Boot_To_Do_App.controllers;

import com.github.janhlavati.Spring_Boot_To_Do_App.models.ToDoItem;
import com.github.janhlavati.Spring_Boot_To_Do_App.repositories.ToDoItemRepository;
import com.github.janhlavati.Spring_Boot_To_Do_App.services.ToDoService;
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
import java.time.ZoneId;

@Controller
public class ToDoItemController {

    private final Logger logger = LoggerFactory.getLogger(ToDoItemController.class);

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    private final ToDoService toDoService;

    public ToDoItemController(ToDoItemRepository toDoItemRepository, ToDoService toDoService) {
        this.toDoItemRepository = toDoItemRepository;
        this.toDoService = toDoService;
    }


    @GetMapping("/")
    public ModelAndView showTodayTasks() {
        logger.debug("request to GET today's tasks");
        ModelAndView modelAndView = new ModelAndView("index"); // Reuse the index view

        // Call the service method to get only today's tasks
        // NOTE: Ensure your service method name matches (e.g., getTasksCreatedTodayNative)
        modelAndView.addObject("todoItems", toDoService.getTasksCreatedTodayWithoutRepositoryMethod());

        modelAndView.addObject("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
        modelAndView.addObject("viewTitle", "Tasks Created Today"); // Optional: for custom view title
        return modelAndView;
    }

    @PostMapping("/todo")
    public String createToDoItem(@Valid ToDoItem toDoItem, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "add-todo-item";
        }

        toDoItem.setCreatedDate(Instant.now());
        toDoItem.setModifiedDate(Instant.now());
        toDoItemRepository.save(toDoItem);
        return "redirect:/";
    }

    @PostMapping("/todo/{id}")
    public String updateToDoItem(@PathVariable("id") Long id, @Valid ToDoItem toDoItem, BindingResult result, Model model) {
        if(result.hasErrors()) {
            ToDoItem originalItem = toDoItemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
            model.addAttribute("toDoItem", originalItem);

            return "update-todo-item";
        }
        toDoItem.setId(id);

        toDoItem.setModifiedDate(Instant.now());
        toDoItemRepository.save(toDoItem);
        return "redirect:/";
    }

}
