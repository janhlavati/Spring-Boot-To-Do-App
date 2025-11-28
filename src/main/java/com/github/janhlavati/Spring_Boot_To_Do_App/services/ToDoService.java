package com.github.janhlavati.Spring_Boot_To_Do_App.services;

import com.github.janhlavati.Spring_Boot_To_Do_App.models.ToDoItem;
import com.github.janhlavati.Spring_Boot_To_Do_App.repositories.ToDoItemRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class ToDoService {

    private final EntityManager entityManager;

    private final ZoneId zoneId = ZoneId.of("CET"); 

    public ToDoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<ToDoItem> getTasksCreatedTodayWithoutRepositoryMethod() {

        LocalDate today = LocalDate.now(zoneId);

        Instant startOfToday = today.atStartOfDay(zoneId).toInstant();

        Instant startOfNextDay = today.plusDays(1).atStartOfDay(zoneId).toInstant();

        String jpql = "SELECT t FROM ToDoItem t WHERE t.complete = false OR t.createdDate >= :start AND t.createdDate < :end";

        List<ToDoItem> tasks = entityManager.createQuery(jpql, ToDoItem.class)
                .setParameter("start", startOfToday)
                .setParameter("end", startOfNextDay)
                .getResultList();

        return tasks;
    }
}