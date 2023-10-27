package br.com.lagcompany.todolist.tasks.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lagcompany.todolist.tasks.model.TasksModel;
import java.util.List;


public interface ITaskRepository extends JpaRepository<TasksModel, UUID>{
    List<TasksModel> findByUserId(UUID userId);
}
