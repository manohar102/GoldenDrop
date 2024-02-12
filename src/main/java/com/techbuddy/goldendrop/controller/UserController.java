package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.UserDTO;
import com.techbuddy.goldendrop.exception.UserNotFoundException;
import com.techbuddy.goldendrop.mapper.UserMapper;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.model.UserStatus;
import com.techbuddy.goldendrop.request.UserRequest;
import com.techbuddy.goldendrop.service.UserService;
import java.util.List;
import java.util.Optional;

import com.techbuddy.goldendrop.specification.UserSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserDTO> index(UserSpecificationBuilder builder,
                               @PageableDefault(value = 25, page = 0) Pageable pageable) {
        log.info("Request GET /user/list");
        Specification<User> spec = builder.build();
        Page<User> users = service.findAll(spec, pageable);
        List<UserDTO> userDTOS = mapper.map(users.getContent());
        return new PageImpl<>(userDTOS, pageable, users.getTotalElements());
    }

    @GetMapping
    public UserDTO show() throws Exception {
        log.info("Request GET /user");
        return mapper.map(service.fetchUser().orElseThrow(() -> new UserNotFoundException("User " + "Not found")));
    }

    @PostMapping("/invite")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public UserDTO inviteUser(@RequestBody UserRequest request) {
        log.info("Request POST /user/invite");
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = mapper.map(request);
        user.setStatus(UserStatus.ACTIVE);
        service.save(user);
        return mapper.map(user);
    }
}
