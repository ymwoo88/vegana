package com.company.vegana.repository.user;

import com.company.vegana.model.user.User;
import com.company.vegana.model.user.UserDto;
import com.company.vegana.model.user.UserSearch;
import com.company.vegana.repository.common.SearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, SearchRepository<UserDto, UserSearch> {
}
