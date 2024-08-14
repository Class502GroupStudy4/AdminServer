package org.g9project4.adminmember.controllers;

import lombok.Data;
import org.g9project4.member.constants.Authority;

@Data
public class MemberSearch {
    private int page = 1;
    private int limit = 20;

    /**
     * sKey
     * userName : 이름 검색
     * email
     * mobile
     * authority
     */

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드
}