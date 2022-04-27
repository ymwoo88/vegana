package com.vegana.vegana.controller;

import com.vegana.vegana.model.user.UserDto;
import com.vegana.vegana.model.user.UserSearch;
import com.vegana.vegana.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    public UserDto detail(@PathVariable Long id) {
        return userService.detail(UserDto.builder().id(id).build());
    }

    @GetMapping("/list")
    public List<UserDto> searchList(@ModelAttribute UserSearch search) {
        return userService.searchList(search);
    }

    @GetMapping("/page")
    public Page<UserDto> searchPage(@ModelAttribute UserSearch search) {
        return userService.searchPage(search);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto dto) {
        return userService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
