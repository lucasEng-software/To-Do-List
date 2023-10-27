package br.com.lagcompany.todolist.tasks.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.lagcompany.todolist.tasks.model.TasksModel;
import br.com.lagcompany.todolist.tasks.repository.ITaskRepository;
import br.com.lagcompany.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("")
    public ResponseEntity create(@RequestBody TasksModel tasksModel, HttpServletRequest request){
        tasksModel.setUserId((UUID)request.getAttribute("userId"));
        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(tasksModel.getStartAt()) || currentDate.isAfter(tasksModel.getEndAt())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Data de inicio da tarefa deve ser posterior a data atual");
        }
        if(tasksModel.getStartAt().isAfter(tasksModel.getEndAt())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Data de inicio da tarefa deve ser posterior a data de finalização da tarefa");
        }
        var task = this.taskRepository.save(tasksModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
    
    @GetMapping("")
    public ResponseEntity list ( HttpServletRequest request){
        var tasksList = this.taskRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(tasksList);
    }
    @GetMapping("/user")
    public ResponseEntity findByUserId( HttpServletRequest request){
        var userId = request.getAttribute("userId");
        var userTasksList = this.taskRepository.findByUserId((UUID)userId);
        System.out.println(userTasksList);
        return ResponseEntity.status(HttpStatus.OK).body(userTasksList);
    }

    @PutMapping("/{id}")
    public ResponseEntity update( @RequestBody TasksModel tasksModel, HttpServletRequest request, @PathVariable UUID id){
        var task = this.taskRepository.findById(id).orElse(null);
        var userId = request.getAttribute("userId");
        if(task == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task não encontrada!");
        }

        if (!task.getUserId().equals(userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O usuário não tem permissão de alterar essa task!");
        }
        Utils.copyNonNullPropperties(tasksModel, task); 
        task.setUpdatedAt(LocalDateTime.now());
        var updatedTask = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
        
    }
    
}

 