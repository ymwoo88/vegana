package com.company.vegana.model.user;

import com.company.vegana.model.common.AbstractAudit;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Table(name = "USER")
@Entity
public class User extends AbstractAudit<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_KEY")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "AGE", nullable = false)
    private String age;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Builder
    protected User(final String name,
                   final String age,
                   final String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }


    public void update(final User user) {
        this.name = user.getName();
        this.age = user.getAge();
        this.email = user.getEmail();
    }
}
