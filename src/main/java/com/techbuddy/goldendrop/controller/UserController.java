package com.techbuddy.goldendrop.controller;

import com.techbuddy.goldendrop.dto.UserDTO;
import com.techbuddy.goldendrop.exception.UserNotFoundException;
import com.techbuddy.goldendrop.mapper.UserMapper;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.model.UserStatus;
import com.techbuddy.goldendrop.request.UserRequest;
import com.techbuddy.goldendrop.service.UserService;
import com.techbuddy.goldendrop.specification.SearchCriteria;
import com.techbuddy.goldendrop.specification.SearchOperation;
import com.techbuddy.goldendrop.specification.UserSpecificationBuilder;
import java.util.List;
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
    public Page<UserDTO> index(
            UserSpecificationBuilder builder, @PageableDefault(value = 25, page = 0) Pageable pageable) {
        log.info("Request GET /user/list");
        User user = service.fetchUser();
        SearchCriteria criteria = new SearchCriteria("storeId", SearchOperation.EQUALITY, user.getStore().getId());
        builder.params.add(criteria);

        Specification<User> spec = builder.build();
        Page<User> users = service.findAll(spec, pageable);
        List<UserDTO> userDTOS = mapper.map(users.getContent());
        return new PageImpl<>(userDTOS, pageable, users.getTotalElements());
    }

    @GetMapping
    public UserDTO show() throws Exception {
        log.info("Request GET /user");
        return mapper.map(service.getLoggedInUser().orElseThrow(() -> new UserNotFoundException("User Not found")));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO create(@RequestBody UserRequest request) {
        log.info("Request POST /user with request : " + request);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return mapper.map(service.createUser(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO update(@PathVariable(value = "id") Long id, @RequestBody UserRequest request) throws Exception {
        log.info(String.format("Request PUT /user/%d with request : " + request, id));
        User user = service.findById(id);
        mapper.merge(user, request);
        service.save(user);
        return mapper.map(user);
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
