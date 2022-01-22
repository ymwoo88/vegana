package com.company.vegana.model.user;

import com.company.vegana.model.common.AbstractDto;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto<Long, User, UserDto> {

    private Long id;

    private String name;

    private String age;

    private String email;

    public UserDto of(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .build();

    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .email(email)
                .build();
    }
}
