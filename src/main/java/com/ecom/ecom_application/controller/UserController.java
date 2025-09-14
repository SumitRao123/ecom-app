package com.ecom.ecom_application.controller;


import com.ecom.ecom_application.dto.UserRequest;
import com.ecom.ecom_application.model.User;
import com.ecom.ecom_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(){
         return  ResponseEntity.ok(userService.getAllUser());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
         return ResponseEntity.ok(userService.getUserById(id));
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveEntity(@RequestBody UserRequest user){
         return new ResponseEntity<>(userService.saveUser(user) , HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable Long id, @RequestBody UserRequest user){
        return new ResponseEntity<>(userService.updateUser(id,user) ? "Update Succesfully" : "Error",HttpStatus.CREATED);
    }


}
