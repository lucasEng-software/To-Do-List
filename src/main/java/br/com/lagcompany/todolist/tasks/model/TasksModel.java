package br.com.lagcompany.todolist.tasks.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Getters and Setters from Lombok Dependecy
@Entity(name = "tasks")
public class TasksModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID userId;
    private String description;
    @Column(length = 50)
    private String title;
    private String priority;
    
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @CreationTimestamp
    private LocalDateTime updatedAt;

}
