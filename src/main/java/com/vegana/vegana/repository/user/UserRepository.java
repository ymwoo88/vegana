package com.vegana.vegana.repository.user;

import com.vegana.vegana.model.user.User;
import com.vegana.vegana.model.user.UserDto;
import com.vegana.vegana.model.user.UserSearch;
import com.vegana.vegana.repository.common.SearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, SearchRepository<UserDto, UserSearch> {
}
