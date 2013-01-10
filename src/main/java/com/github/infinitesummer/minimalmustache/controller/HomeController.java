package com.github.infinitesummer.minimalmustache.controller;

import com.github.infinitesummer.minimalmustache.util.ViewEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getDashboard() {
        return ViewEnum.HOME.toModelAndView();
    }
}