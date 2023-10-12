package br.com.lagcompany.todolist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.lagcompany.todolist.user.model.UserModel;
import br.com.lagcompany.todolist.user.repository.IUserRepository;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;
    @PostMapping("")
    public ResponseEntity create(@RequestBody UserModel userModel){
        System.out.println( userModel.getUsername());
        var verifyUser = this.userRepository.findByUsername(userModel.getUsername());
        if(verifyUser != null) {
           System.out.println("Usuário já cadastrado");
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado"); 
        } 
        userModel.setPassword(BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()));
        
        var userCreated = this.userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
