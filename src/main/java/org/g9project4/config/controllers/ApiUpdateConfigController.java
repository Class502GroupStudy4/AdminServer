package org.g9project4.config.controllers;

import lombok.RequiredArgsConstructor;
import org.g9project4.config.service.ConfigInfoService;
import org.g9project4.config.service.ConfigSaveService;
import org.g9project4.menus.Menu;
import org.g9project4.menus.MenuDetail;
import org.g9project4.publicData.greentour.services.GreenUpdateService;
import org.g9project4.publicData.tour.services.ApiUpdateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/config/api/update")
@RequiredArgsConstructor
public class ApiUpdateConfigController {
    private final ConfigInfoService infoService;
    private final ApiUpdateService apiUpdateService;
    private final GreenUpdateService greenUpdateService;
    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "config";
    }

    @ModelAttribute("subMenuCode")
    public String getSubMenuCode() {
        return "update";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {
        return Menu.getMenus("config");
    }

    @ModelAttribute("pageTitle")
    public String getPageTitle() {
        return "API 업데이트 설정";
    }
    @GetMapping()
    public String update(Model model) {
        ApiConfig config = infoService.get("apiConfig", ApiConfig.class).orElseGet(ApiConfig::new);

        model.addAttribute("apiConfig", config);

        return "config/update";
    }
    @PostMapping("/{type}")
    public String updateTour(@PathVariable("type") String type, Model model) {
        ApiConfig config = infoService.get("apiConfig", ApiConfig.class).orElseGet(ApiConfig::new);
        if(type.equals("tour")){
            apiUpdateService.update(config.getPublicOpenApiKey());
        } else if (type.equals("green")) {
            greenUpdateService.greenUpdate(config.getPublicOpenApiKey());
        }
        return "config/api/update";
    }
}
