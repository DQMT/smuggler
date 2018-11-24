package cn.tincat.smuggler.controller;

import cn.tincat.smuggler.dto.YourText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class MainController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static StringBuilder stringBuilder;

    @RequestMapping("s")
    public String hello(Model model) {
        model.addAttribute("s", YourText.get());
        model.addAttribute("k", YourText.getKey());
        return "hello";
    }

    @RequestMapping("go")
    public void go(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String s = httpServletRequest.getParameter("s");
        YourText.set(s);
    }

    @RequestMapping("snk")
    public void sneak(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String s = httpServletRequest.getParameter("s");
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }
        if (s == null) {
            stringBuilder = new StringBuilder();
            return;
        }
        if (s.startsWith("^")) {
            stringBuilder = new StringBuilder(s);
            YourText.setTIme();
        } else {
            stringBuilder.append(s);
        }
        if (s.endsWith("$")) {
            YourText.set(stringBuilder.toString().substring(1,stringBuilder.length()-1));
        }
    }
}