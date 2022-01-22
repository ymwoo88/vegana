package com.company.vegana.controller;

import com.company.vegana.model.user.UserDto;
import com.company.vegana.model.user.UserSearch;
import com.company.vegana.service.UserService;
import com.company.vegana.servlet.dto.ApiResponse;
import com.company.vegana.servlet.log.LogKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> create(@RequestBody UserDto dto) {
        return ResponseEntity.ok(
                ApiResponse.<UserDto>builder()
                        .logKey(LogKey.get())
                        .data(userService.create(dto))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<UserDto>builder()
                        .logKey(LogKey.get())
                        .data(userService.detail(UserDto.builder().id(id).build()))
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UserDto>>> searchList(@ModelAttribute UserSearch search) {
        return ResponseEntity.ok(
                ApiResponse.<List<UserDto>>builder()
                        .logKey(LogKey.get())
                        .data(userService.searchList(search))
                        .build()
        );
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<UserDto>>> searchPage(@ModelAttribute UserSearch search) {
        return ResponseEntity.ok(
                ApiResponse.<Page<UserDto>>builder()
                        .logKey(LogKey.get())
                        .data(userService.searchPage(search))
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserDto>> update(@RequestBody UserDto dto) {
        return ResponseEntity.ok(
                ApiResponse.<UserDto>builder()
                        .logKey(LogKey.get())
                        .data(userService.update(dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .logKey(LogKey.get())
                        .data("delete success")
                        .build()
        );
    }
}
