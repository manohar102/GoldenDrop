package com.techbuddy.goldendrop.mapper;

import com.techbuddy.goldendrop.dto.UserDTO;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.request.UserRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    UserDTO map(User user);

    List<UserDTO> map(List<User> users);

    User map(UserRequest request);
}
