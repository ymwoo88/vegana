package com.company.vegana.service;

import com.company.vegana.model.user.User;
import com.company.vegana.model.user.UserDto;
import com.company.vegana.model.user.UserSearch;
import com.company.vegana.repository.user.UserRepository;
import com.company.vegana.service.common.AbstractService;
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
