package org.g9project4.adminmember.services;

import lombok.RequiredArgsConstructor;
import org.g9project4.adminmember.controllers.RequestMember;
import org.g9project4.global.Utils;
import org.g9project4.global.exceptions.script.AlertException;
import org.g9project4.member.constants.Authority;
import org.g9project4.member.entities.Authorities;
import org.g9project4.member.entities.Member;
import org.g9project4.member.repositories.MemberRepository;
import org.g9project4.member.services.MemberSaveService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberConfigSaveService {
    private final MemberRepository memberRepository;
    private final Utils utils;
    private final MemberSaveService memberSaveService;

    public void save(RequestMember form) {

        String email = form.getEmail();
        Member member = memberRepository.findByEmail(email).orElseGet(Member::new);

        member.setMobile(form.getMobile());
        member.setUserName(form.getUserName());
        member.setPassword(form.getPassword());
        //member.setType(Authority.valueOf(form.getType()));

        memberRepository.saveAndFlush(member);
    }

    public void saveList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 게시판을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            String email = utils.getParam("email_" + chk);
            Member member = memberRepository.findByEmail(email).orElse(null);
            if (member == null) continue;
            List<Authority> authorities = List.of(Authority.valueOf(utils.getParam("authority_"+chk)));
            memberSaveService.save(member,authorities);
        }

    }
}
