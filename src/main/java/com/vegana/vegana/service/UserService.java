package com.vegana.vegana.service;

import com.vegana.vegana.model.user.User;
import com.vegana.vegana.model.user.UserDto;
import com.vegana.vegana.model.user.UserSearch;
import com.vegana.vegana.repository.user.UserRepository;
import com.vegana.vegana.service.common.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService extends AbstractService<Long, User, UserDto, UserSearch, UserRepository> {

    private final UserRepository userRepository;

    public void test() {
        userRepository.count();
    }

//    @Override
//    public UserDto createPreProcess(Dto dto) {return null;} // 등록 전처리
}
