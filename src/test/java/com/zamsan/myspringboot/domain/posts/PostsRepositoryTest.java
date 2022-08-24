package com.zamsan.myspringboot.domain.posts;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanUp(){
        postsRepository.deleteAll();
    }

    @Test
    public void content_load(){
        String title = "Test Title";
        String contet = "Test Content";

        postsRepository.save(Posts.builder().title(title).content(contet).author("zamsan").build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(contet);

    }

    @Test
    public void BaseTimeEntity_register(){

        LocalDateTime now = LocalDateTime.of(2022,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("zamsan").build());

        List<Posts> postsList =postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>> create Date ="+posts.getCreateDate()+", modifiedDate ="+posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
