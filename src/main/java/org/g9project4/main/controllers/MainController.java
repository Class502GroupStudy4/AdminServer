package org.g9project4.main.controllers;

import lombok.RequiredArgsConstructor;

import org.g9project4.main.entities.VisitorCount;
import org.g9project4.main.services.VisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
    private final VisitorService visitorService;





    @GetMapping
    public String index( Model model) {
        String bid = "qna";
        List<String> addCss = new ArrayList<>();
        addCss.add("main/style");
        List<String> addScript = new ArrayList<>();
        addScript.add("main/chart");
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);


        visitorService.recordVisit();
        List<VisitorCount> stats = visitorService.getVisitorStatistics();

        model.addAttribute("stats", stats);
        return "main/index";
    }



}
