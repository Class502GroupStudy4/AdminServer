package org.g9project4.adminmember.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.g9project4.adminmember.constants.Authority;
import org.g9project4.adminmember.services.MemberConfigSaveService;
import org.g9project4.board.entities.Board;
import org.g9project4.global.ListData;
import org.g9project4.global.Pagination;
import org.g9project4.global.Utils;
import org.g9project4.global.exceptions.ExceptionProcessor;
import org.g9project4.member.entities.Member;
import org.g9project4.member.services.MemberInfoService;
import org.g9project4.menus.Menu;
import org.g9project4.menus.MenuDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/manager/member")
@RequiredArgsConstructor
public class AdminMemberController implements ExceptionProcessor {

    private final MemberInfoService infoService;
    private final MemberConfigSaveService memberConfigSaveService;
    private final Utils utils;

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "member";
    }
    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {

        return Menu.getMenus("member");
    }
    @ModelAttribute("memberAuthorities")
    public List<String[]> memberAuthorities() {

        return Authority.getList(); //enum 상수 String으로...
    }



    @GetMapping
    public String list(@ModelAttribute MemberSearch search, Model model) {
        commonProcess("list", model);

        ListData<Member> data = infoService.getSearchedMemberList(search);
        model.addAttribute("items", data.getItems()); // 목록
        model.addAttribute("pagination", data.getPagination()); // 페이징

        return "adminMember/member/list";
    }

    @GetMapping("/edit/{email}")
    public String edit(@PathVariable("email") String email, Model model) {
        commonProcess("edit",model);
        RequestMember form = infoService.getForm(email);
        model.addAttribute("requestMember", form);
        return "adminMember/member/edit";
    }




    @PostMapping("/save")
    public String save(@Valid RequestMember form, Errors errors, Model model) {
        String mode = form.getMode();
        commonProcess(mode,model);
        if(errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(System.out::println);
            return "adminMember/member/" + mode;
        }
        memberConfigSaveService.save(form);

        return "redirect:" + utils.redirectUrl("/manager/member");

    }



    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "회원 목록";
        if(mode.equals("edit")) {
            pageTitle = "회원 수정";
        }else if (mode.equals("add")) {
            pageTitle = "회원 등록";
        }

        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }
}