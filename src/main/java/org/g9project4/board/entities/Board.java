package org.g9project4.board.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.g9project4.file.entities.FileInfo;
import org.g9project4.global.entities.BaseMemberEntity;
import org.g9project4.member.constants.Authority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = @Index(name="idx_board_basic", columnList = "listOrder DESC, createdAt DESC"))
public class Board extends BaseMemberEntity {
    @Id
    @Column(length=30)
    private String bid; // 게시판 아이디

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString();

    private int listOrder; // 진열 가중치

    @Column(length=60, nullable = false)
    private String bName; // 게시판 이름

    private boolean active; // 사용 여부

    private int rowsPerPage = 20; // 1페이지 게시글 수

    private int pageCountPc = 10; // PC 페이지 구간 갯수

    private int pageCountMobile = 5; // Mobile 페이지 구간 갯수

    private boolean useReply; // 답글 사용 여부

    private boolean useComment; // 댓글 사용 여부

    private boolean useEditor; // 에디터 사용 여부

    private boolean useUploadImage; // 이미지 첨부 사용 여부

    private boolean useUploadFile; // 파일 첨부 사용 여부

    @Column(length=10, nullable = false)
    private String locationAfterWriting = "list"; // 글 작성 후 이동 위치

    private boolean showListBelowView; // 글 보기 하단 게시글 목록 노출 여부

    @Column(length=10, nullable = false)
    private String skin = "default"; // 스킨

    @Lob
    private String category; // 게시판 분류

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority listAccessType = Authority.ALL; // 권한 설정 - 글목록

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority viewAccessType = Authority.ALL; // 권한 설정 - 글보기

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority writeAccessType = Authority.ALL; // 권한 설정 - 글쓰기

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority replyAccessType = Authority.ALL; // 권한 설정 - 답글

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority commentAccessType = Authority.ALL; // 권한 설정 - 댓글

    @Lob
    private String htmlTop; // 게시판 상단 HTML

    @Lob
    private String htmlBottom; // 게시판 하단 HTML

    @Transient
    private List<FileInfo> htmlTopImages; // 게시판 상단 Top 이미지

    @Transient
    private List<FileInfo> htmlBottomImages; // 게시판 하단 Bottom 이미지

    /**
     * 분류 List 형태로 변환
     *
     * @return
     */
    @JsonIgnore
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();

        if (StringUtils.hasText(category)) {
            categories = Arrays.stream(category.trim().split("\\n"))
                    .map(s -> s.trim().replaceAll("\\r", ""))
                    .toList();
        }

        return categories;
    }
}
