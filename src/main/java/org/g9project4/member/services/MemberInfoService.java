package org.g9project4.member.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.g9project4.adminmember.controllers.MemberSearch;
import org.g9project4.adminmember.controllers.RequestMember;
import org.g9project4.global.ListData;
import org.g9project4.global.Pagination;
import org.g9project4.member.MemberInfo;
import org.g9project4.member.constants.Authority;
import org.g9project4.member.entities.Authorities;
import org.g9project4.member.entities.Member;
import org.g9project4.member.entities.QMember;
import org.g9project4.member.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final HttpServletRequest request;
    private final EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        List<Authorities> tmp = Objects.requireNonNullElse(member.getAuthorities(),//getAuthorities 가 Null 이면 뒤에 반환
                List.of(Authorities.builder()
                        .member(member)
                        .authority(Authority.USER)
                        .build()));

        List<SimpleGrantedAuthority> authorities = tmp.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().name()))
                .toList();
        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(authorities)
                .member(member)
                .build();
    }

    public Member get(Long seq) {
        Member member = memberRepository.findById(seq).orElse(null);
        return member;
    }

    public RequestMember getForm(Long seq) {
        Member member = get(seq);
        RequestMember form = new ModelMapper().map(member, RequestMember.class);
        form.setMode("edit");
        return form;
    }

    public RequestMember getForm(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        RequestMember form = new ModelMapper().map(member, RequestMember.class);
        form.setMode("edit");
        return form;
    }

    public ListData<Member> getList(MemberSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = Math.max(search.getLimit(), 1);
        int offset = (page - 1) * limit;
        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;

        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");

        List<Member> items = new JPAQueryFactory(em)
                .selectFrom(member)
                .leftJoin(member.authorities)
                .fetchJoin()
                .where(andBuilder)
                .limit(limit)
                .offset(offset)
                .orderBy(new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt")))
                .fetch();

        int total = (int) memberRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, total, 10, limit, request);
        return new ListData<>(items, pagination);

    }

    public ListData<Member> getSearchedMemberList(MemberSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;


        /* 검색 조건 처리 S */
        QMember member = QMember.member;
        BooleanBuilder andBuilder = new BooleanBuilder();
        String sopt = search.getSopt();//검색 옵션
        sopt = StringUtils.hasText(sopt) ? sopt.trim() : "ALL";
        String skey = search.getSkey(); //키워드

        // 키워드가 있을 때 조건별 키워드 검색
        if (StringUtils.hasText(skey) && StringUtils.hasText(skey.trim())) {
            sopt = sopt.trim();
            skey = skey.trim();
            BooleanExpression condition = null;
            if (sopt.equals("ALL")) {
                condition = member.userName.concat(member.email).concat(member.mobile).contains(skey);
            } else if (sopt.equals("USERNAME")) {
                condition = member.userName.contains(skey);
            } else if (sopt.equals("EMAIL")) {
                condition = member.email.contains(skey);
            } else if (sopt.equals("MOBILE")) {
                skey = skey.replaceAll("\\D", ""); // 숫자만 남긴다.
                if (StringUtils.hasText(skey) && StringUtils.hasText(skey.trim())) {
                    condition = member.mobile.contains(skey);
                } else {
                    condition = member.mobile.contains("abcde");
                }
            }
            if (condition != null) {
            }
/*            if(authority != null) {
                andBuilder.and(member.authorities.equals(Authority.)
            }*/
            /* 검색 조건 처리 E */

            // 페이징 및 정렬 처리
            Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

            // 데이터 조회
            Page<Member> data = memberRepository.findAll(andBuilder, pageable);
            System.out.println(data);
            Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

            List<Member> items = data.getContent();

            return new ListData<>(items, pagination);
        }
    }
