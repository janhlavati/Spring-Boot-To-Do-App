package com.github.janhlavati.Spring_Boot_To_Do_App.config;

import com.github.janhlavati.Spring_Boot_To_Do_App.models.ToDoItem;
import com.github.janhlavati.Spring_Boot_To_Do_App.repositories.ToDoItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class ToDoItemDataLoader implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(ToDoItemDataLoader.class);

    @Autowired
    ToDoItemRepository toDoItemRepository;

    @Override
    public void run(String... args) throws Exception {
        loadSeedData();
    }


    private void loadSeedData() {
        if(toDoItemRepository.count() == 0) {
            ToDoItem toDoItem1 = new ToDoItem("get the milk");
            ToDoItem toDoItem2 = new ToDoItem("rake the leaves");

            toDoItemRepository.save(toDoItem1);
            toDoItemRepository.save(toDoItem2);
        }

        logger.info("Number of TodoItems: {}", toDoItemRepository.count());
    }
}
