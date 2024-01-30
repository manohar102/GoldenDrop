package com.techbuddy.goldendrop.controller;


import com.techbuddy.goldendrop.dto.UserDTO;
import com.techbuddy.goldendrop.mapper.UserMapper;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.request.UserRequest;
import com.techbuddy.goldendrop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserDTO> index() {
        log.info("Request GET /user");
        List<User> users = service.findAll();
        return mapper.map(users);
    }

    @GetMapping("/{id}")
    public UserDTO show(@PathVariable("id") Long id) throws Exception {
        log.info(String.format("Request GET /user/{%d}", id));
        User user = service.findById(id);
        return mapper.map(user);
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserRequest request) {
        log.info("Request POST /user");
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = mapper.map(request);
        service.save(user);
        return mapper.map(user);
    }

}
