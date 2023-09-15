package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("app")
@Controller
public class LoginController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String loginFor = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loginFor".equals(cookie.getName())) {
                    loginFor = cookie.getValue();
                    break;
                }
            }
        }
        request.getSession().setAttribute("loginFor", loginFor);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }


    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {

        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView error() {

        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    @GetMapping("/error/connection")
    public ModelAndView errorConnection(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("error-connection");
        return mav;
    }
}
