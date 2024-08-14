package org.g9project4.member.entities;

import jakarta.persistence.*;
import lombok.*;
import org.g9project4.global.entities.BaseEntity;
import org.g9project4.member.constants.Authority;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Member extends BaseEntity implements Serializable {
    @Id @GeneratedValue
    private Long seq;

    @Column(length = 65, unique = true, nullable = false)
    private String email;

    @Column(length = 65,nullable = false)
    private String password;

    @Column(length = 40,nullable = false)
    private String userName;

    @Column(length=15,nullable = false)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority type = Authority.ALL; // 권한 설정 - 글쓰기


    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Authorities> authorities;
}
