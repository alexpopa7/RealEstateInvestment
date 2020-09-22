//package com.proiect.licentam.controller;
//
//
//import com.proiect.licentam.model.User;
//import com.proiect.licentam.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping("/realestate")
//
//public class UserController {
//
//    @Autowired
//    UserService userService;
//
//    @GetMapping("/users")
//    public List<User> getAllUsers(){
//        return userService.findAllUsers();
//    }
//
//    @PostMapping("/users")
//    public void saveOneUser(@Valid @RequestBody User toBeSavedUsers){
//        userService.saveOneUser(toBeSavedUsers);
//    }
//
//}
