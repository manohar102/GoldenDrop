package com.techbuddy.goldendrop.mapper;

import com.techbuddy.goldendrop.dto.UserDTO;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.request.UserRequest;
import java.util.List;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    @Mapping(target = "userName", expression = "java(user.getUsername())")
    UserDTO map(User user);

    List<UserDTO> map(List<User> users);

    User map(UserRequest request);

    void merge(@MappingTarget User user, UserRequest request);
}
