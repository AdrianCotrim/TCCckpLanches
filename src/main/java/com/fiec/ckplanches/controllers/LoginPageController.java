package com.fiec.ckplanches.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginPageController {
    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView("/loginPage/index.html");
    }

}
