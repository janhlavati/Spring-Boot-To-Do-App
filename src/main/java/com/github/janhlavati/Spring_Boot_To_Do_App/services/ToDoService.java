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

    // Use an appropriate ZoneId, typically UTC or your application's defined zone
    private final EntityManager entityManager;

    // Use an appropriate ZoneId for calculating 'today'
    private final ZoneId zoneId = ZoneId.of("CET"); // Using CET based on your current location

    // Constructor injection
    public ToDoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<ToDoItem> getTasksCreatedTodayWithoutRepositoryMethod() {

        // 1. Calculate the Instant Range (Start of Today and Start of Tomorrow)
        LocalDate today = LocalDate.now(zoneId);

        // Start of today (inclusive)
        Instant startOfToday = today.atStartOfDay(zoneId).toInstant();

        // Start of tomorrow (exclusive)
        Instant startOfNextDay = today.plusDays(1).atStartOfDay(zoneId).toInstant();

        // 2. Define the JPQL Query
        String jpql = "SELECT t FROM ToDoItem t WHERE t.complete = false OR t.createdDate >= :start AND t.createdDate < :end";

        // 3. Execute the Query via EntityManager
        List<ToDoItem> tasks = entityManager.createQuery(jpql, ToDoItem.class)
                .setParameter("start", startOfToday)
                .setParameter("end", startOfNextDay)
                .getResultList();

        return tasks;
    }
}