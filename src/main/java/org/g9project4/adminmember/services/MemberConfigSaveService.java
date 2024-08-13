package org.g9project4.adminmember.services;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.g9project4.adminmember.controllers.RequestMember;
import org.g9project4.global.Utils;
import org.g9project4.member.controllers.RequestJoin;
import org.g9project4.member.entities.Member;
import org.g9project4.member.repositories.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberConfigSaveService {
    private final MemberRepository memberRepository;
    private final Utils utils;

    public void save(RequestMember form){
        String email = form.getEmail();
        String mode = form.getMode();
        Member member = memberRepository.findByEmail(email).orElseGet(Member::new);

        member.setEmail(form.getEmail());
        member.setUserName(form.getUserName());
        member.setPassword(form.getPassword());
        member.setAuthorities(form.getUserType());

        memberRepository.saveAndFlush(member);
    }
}
