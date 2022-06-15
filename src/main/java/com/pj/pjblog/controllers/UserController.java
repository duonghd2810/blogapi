package com.pj.pjblog.controllers;

import com.pj.pjblog.dao.User;
import com.pj.pjblog.dto.UserDTO;
import com.pj.pjblog.exceptions.NotFoundException;
import com.pj.pjblog.repositories.UserRepository;
import com.pj.pjblog.ultis.ConvertObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            throw new NotFoundException("khong co user nao");
        }
        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "user_id") Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Khong co user co id nay");
        }
        return ResponseEntity.status(200).body(user.get());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        User user = ConvertObject.fromUserDTOToUser(userDTO);
        User user1 = userRepository.save(user);
        return ResponseEntity.status(201).body(user1);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO,
                                        @PathVariable(name = "user_id") Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Khong co thong tin user nay");
        }
        User newUser = ConvertObject.fromUserDTOToUser(userDTO);
        newUser.setUserId(user.get().getUserId());
        return ResponseEntity.status(200).body(userRepository.save(newUser));
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUserById(@PathVariable(name = "user_id") Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Khong co thong tin user nay");
        }
        userRepository.deleteById(userId);
        return ResponseEntity.status(200).body("Xoa thanh cong");
    }
}
