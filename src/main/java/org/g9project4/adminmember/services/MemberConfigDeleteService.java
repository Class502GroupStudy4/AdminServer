package org.g9project4.adminmember.services;

import lombok.RequiredArgsConstructor;

import org.g9project4.global.Utils;
import org.g9project4.global.exceptions.script.AlertException;
import org.g9project4.member.entities.Member;
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
    private final MemberInfoService memberInfoService;
    private final Utils utils;
    public void delete(Long seq) {
        Member member = memberInfoService.get(seq);
        memberRepository.delete(member);
        memberRepository.flush();
    }

    public void delete(String email){
        Member member = memberInfoService.get(email);
        System.out.println(member);
        memberRepository.delete(member);
        System.out.println("member:" + member);

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

