package com.github.janhlavati.Spring_Boot_To_Do_App.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.github.janhlavati.Spring_Boot_To_Do_App.repositories.ToDoItemRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "todo_item")
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotBlank (message = "Description is required")
    private String description;

    @Getter
    @Setter
    private boolean complete;

    @Getter
    @Setter
    private Instant createdDate;

    @Getter
    @Setter
    private Instant modifiedDate;



    public ToDoItem(){}

    public ToDoItem(String description) {
        this.description = description;
        this.complete = false;
        this.createdDate = Instant.now();
        this.modifiedDate = Instant.now();
    }



    public static final ZoneId CET_ZONE = ZoneId.of("Europe/Belgrade");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern(
            "dd-MM-yyyy HH:mm:ss z", Locale.ENGLISH
    );
    public String getFormattedCreatedDateCET() {
        if (this.createdDate == null) {
            return "N/A";
        }
        // Conversion step: Instant (UTC) -> ZonedDateTime (CET)
        ZonedDateTime cetTime = this.createdDate.atZone(CET_ZONE);
        return cetTime.format(DISPLAY_FORMATTER);
    }

    /**
     * @return The modifiedDate converted and formatted to CET time.
     */
    public String getFormattedModifiedDateCET() {
        if (this.modifiedDate == null) {
            return "N/A";
        }
        // Conversion step: Instant (UTC) -> ZonedDateTime (CET)
        ZonedDateTime cetTime = this.modifiedDate.atZone(CET_ZONE);
        return cetTime.format(DISPLAY_FORMATTER);
    }



    @Override
    public String toString() {
        return String.format("TodoItem{id=%d, description='%s', complete='%s', createdDate='%s', modifiedDate='%s'}",
                id, description, complete, getFormattedCreatedDateCET(), getFormattedModifiedDateCET());    }
}
