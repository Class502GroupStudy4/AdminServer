package org.g9project4.admin.board.controllers;

import lombok.RequiredArgsConstructor;
import org.g9project4.global.exceptions.ExceptionProcessor;
import org.g9project4.menus.Menu;
import org.g9project4.menus.MenuDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController implements ExceptionProcessor {


    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "board";
    }
    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {
        return Menu.getMenus("board");
    }
    @GetMapping
    public String list(@ModelAttribute BoardSearch search, Model model){
        return "admin/board/list";
    }

}
