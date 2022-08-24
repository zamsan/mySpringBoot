package com.zamsan.myspringboot.controller;

import com.zamsan.myspringboot.config.auth.dto.SessionUser;
import com.zamsan.myspringboot.controller.dto.PostResponseDto;
import com.zamsan.myspringboot.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if(sessionUser!= null){
            model.addAttribute("name",sessionUser.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postSave(){
        return "post-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model){
        PostResponseDto responseDto = postsService.findById(id);
        model.addAttribute("post",responseDto);

        return "posts-update";
    }

}
