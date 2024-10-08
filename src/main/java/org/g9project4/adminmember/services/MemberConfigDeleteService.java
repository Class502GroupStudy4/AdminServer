package org.g9project4.adminmember.services;

import lombok.RequiredArgsConstructor;

import org.g9project4.global.Utils;
import org.g9project4.global.exceptions.script.AlertException;
import org.g9project4.member.entities.Authorities;
import org.g9project4.member.entities.AuthoritiesId;
import org.g9project4.member.entities.Member;
import org.g9project4.member.entities.QAuthorities;
import org.g9project4.member.repositories.AuthoritiesRepository;
import org.g9project4.member.repositories.MemberRepository;
import org.g9project4.member.services.MemberInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberConfigDeleteService {
    private final MemberRepository memberRepository;
    private final AllMemberConfigInfoService memberConfigInfoService;
    private final AuthoritiesRepository authoritiesRepository;
    private final Utils utils;
    public void delete(String email){
        QAuthorities authorities = QAuthorities.authorities;
        List<Authorities> items = (List<Authorities>)authoritiesRepository.findAll(authorities.member.email.eq(email));
        List<AuthoritiesId> ids = items.stream().map(s -> new AuthoritiesId(s.getMember(), s.getAuthority())).toList();
        authoritiesRepository.deleteAllById(ids);
        authoritiesRepository.flush();
        Member member = memberConfigInfoService.get(email);
        if (member == null) {
            throw new AlertException("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        memberRepository.delete(member);
        memberRepository.flush();
    }
    public void deleteList(List<Integer> chks){
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("삭제할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
        }
        for (int chk : chks) {
            String email = utils.getParam("email_" + chk);
            delete(email);
        }
    }
}

