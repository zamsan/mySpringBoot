package com.zamsan.myspringboot.service;

import com.zamsan.myspringboot.controller.dto.PostListResponseDto;
import com.zamsan.myspringboot.domain.posts.Posts;
import com.zamsan.myspringboot.domain.posts.PostsRepository;
import com.zamsan.myspringboot.controller.dto.PostResponseDto;
import com.zamsan.myspringboot.controller.dto.PostsSaveRequestDto;
import com.zamsan.myspringboot.controller.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto){
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
       Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id ="+id));
       posts.update(requestDto.getTitle(),requestDto.getContent());
       return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id ="+id));
        postsRepository.delete(posts);
    }

    public PostResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }
}
