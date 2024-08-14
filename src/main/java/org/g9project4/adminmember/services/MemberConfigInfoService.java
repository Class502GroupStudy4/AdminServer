package org.g9project4.adminmember.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.g9project4.board.exceptions.BoardNotFoundException;
import org.g9project4.board.repositories.BoardRepository;
import org.g9project4.member.entities.Member;
import org.g9project4.member.repositories.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberConfigInfoService {
    private final MemberRepository memberRepository;
    private final HttpServletRequest request;


    public Member get(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(BoardNotFoundException::new); // 일단 넣음 수정할예정
            return member;
    }


}