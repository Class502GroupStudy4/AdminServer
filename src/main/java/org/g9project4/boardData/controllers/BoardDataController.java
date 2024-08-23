package org.g9project4.boardData.controllers;

import lombok.RequiredArgsConstructor;

import org.g9project4.board.entities.Board;
import org.g9project4.board.exceptions.BoardNotFoundException;
import org.g9project4.board.repositories.BoardRepository;
import org.g9project4.board.services.BoardConfigDeleteService;
import org.g9project4.boardData.entities.BoardData;
import org.g9project4.boardData.services.BoardDataConfigInfoService;
import org.g9project4.boardData.services.BoardDataInfoService;
import org.g9project4.global.ListData;
import org.g9project4.global.Utils;
import org.g9project4.member.entities.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board/data")
@RequiredArgsConstructor
public class BoardDataController {
    public final BoardDataConfigInfoService configInfoService;
    public final BoardRepository repository;
    public final BoardDataInfoService infoService;
    private final BoardConfigDeleteService configDeleteService;
    private Board board; // 게시판 설정
    private BoardData boardData; // 게시글 내용


    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, @ModelAttribute BoardDataSearch search, Model model) {

        Optional<Board> board = repository.findById(bid);
        Board boardEntity = board.get();
        model.addAttribute("bName", boardEntity.getBName());

        ListData<BoardData> data = infoService.getList(bid, search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "boardData/list";
    }
    @DeleteMapping
    public String deleteList(@RequestParam("chk") List<Integer> chks, Model model) {
        configDeleteService.deleteList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        boardData = infoService.get(seq);
        model.addAttribute("boardData", boardData);

        return "boardData/view";
    }

}
