package com.github.janhlavati.Spring_Boot_To_Do_App.repositories;

import com.github.janhlavati.Spring_Boot_To_Do_App.models.ToDoItem;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface ToDoItemRepository extends CrudRepository<ToDoItem, Long> {
}
