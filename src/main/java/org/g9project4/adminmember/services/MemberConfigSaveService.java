package org.g9project4.adminmember.services;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.g9project4.adminmember.controllers.RequestMember;
import org.g9project4.global.Utils;
import org.g9project4.member.constants.Authority;
import org.g9project4.member.controllers.RequestJoin;
import org.g9project4.member.entities.Member;
import org.g9project4.member.repositories.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MemberConfigSaveService {
    private final MemberRepository memberRepository;
    private final Utils utils;



    public void save(RequestMember form){

        String email = form.getEmail();
        Member member = memberRepository.findByEmail(email).orElseGet(Member::new);

        member.setMobile(form.getMobile());
        member.setUserName(form.getUserName());
        member.setPassword(form.getPassword());
        //member.setType(Authority.valueOf(form.getType()));

        memberRepository.saveAndFlush(member);
    }

}
