package com.zamsan.myspringboot.web;

import com.zamsan.myspringboot.config.auth.LoginUser;
import com.zamsan.myspringboot.config.auth.dto.SessionUser;
import com.zamsan.myspringboot.web.dto.PostResponseDto;
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
    public String index(Model model,@LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());
        if(user!= null){
            model.addAttribute("name",user.getName());
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
