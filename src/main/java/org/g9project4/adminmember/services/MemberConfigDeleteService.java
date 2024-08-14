package org.g9project4.adminmember.services;

import lombok.RequiredArgsConstructor;
import org.g9project4.board.entities.Board;
import org.g9project4.board.exceptions.BoardNotFoundException;
import org.g9project4.board.repositories.BoardRepository;
import org.g9project4.board.services.BoardConfigDeleteService;
import org.g9project4.board.services.BoardConfigInfoService;
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
    private final BoardRepository boardRepository;
    private MemberRepository memberRepository;
    private MemberInfoService memberInfoService;
    private final Utils utils;
    public void delete(Long seq) {
        Member member = memberInfoService.get(seq);
        memberRepository.delete(member);
        boardRepository.flush();
    }

    public void delete(String email){
        Member member = memberInfoService.get(email);
        memberRepository.delete(member);
        boardRepository.flush();
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

