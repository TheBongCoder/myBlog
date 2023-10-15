package com.thebongcoder.blog.blogapp.service;

import com.thebongcoder.blog.blogapp.dto.CommentDTO;
import com.thebongcoder.blog.blogapp.dto.PostDTO;
import com.thebongcoder.blog.blogapp.dto.PostResponse;
import com.thebongcoder.blog.blogapp.entity.Post;
import com.thebongcoder.blog.blogapp.exceptions.ResourceNotFoundException;
import com.thebongcoder.blog.blogapp.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final ModelMapperService modelMapperService;

    public PostService(PostRepository postRepository, ModelMapperService modelMapperService) {
        this.postRepository = postRepository;
        this.modelMapperService = modelMapperService;
    }

    public PostDTO createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        post = postRepository.save(post);
//        new PostDTO(post.getId(),post.getTitle(),post.getDescription(),post.getContent())
        /*PostDTO postDTOResponse = new PostDTO();
        postDTOResponse.setId(post.getId());
        postDTOResponse.setTitle(post.getTitle());
        postDTOResponse.setDescription(post.getDescription());
        postDTOResponse.setContent(post.getContent());*/
        return modelMapperService.map(post, PostDTO.class);
    }

    public List<PostDTO> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList) {
           /* PostDTO postDTO = new PostDTO();
            postDTO.setId(post.getId());
            postDTO.setTitle(post.getTitle());
            postDTO.setDescription(post.getDescription());
            postDTO.setContent(post.getContent());*/
            PostDTO postDTO = modelMapperService.map(post, PostDTO.class);
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    public PostDTO getPostById(long id) {
        log.info("Get Post By Id : " + id);
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) throw  new ResourceNotFoundException("Post","id",id);
        PostDTO postDTOResponse = new PostDTO();
        postDTOResponse.setId(post.getId());
        postDTOResponse.setTitle(post.getTitle());
        postDTOResponse.setDescription(post.getDescription());
        postDTOResponse.setContent(post.getContent());
        postDTOResponse.setCommentDTOs((new HashSet<>(modelMapperService.mapAll(post.getComments(), CommentDTO.class))));
        return postDTOResponse;
    }

    public PostDTO updatePost(PostDTO postDTO, long id) {
        log.info("Update post for Id : {} , postDto : {} ", id, postDTO);
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            log.info("Post with Id not found :{}", id);
            return null;
        }
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Post updatedPost = postRepository.save(post);
        PostDTO postDTOResponse = new PostDTO();
        postDTOResponse.setId(updatedPost.getId());
        postDTOResponse.setTitle(updatedPost.getTitle());
        postDTOResponse.setDescription(updatedPost.getDescription());
        postDTOResponse.setContent(updatedPost.getContent());
        postDTOResponse.setCommentDTOs((new HashSet<>(modelMapperService.mapAll(post.getComments(), CommentDTO.class))));

        return postDTOResponse;
    }

    public boolean deletePost(long id) {
        log.info("Delete post for Id : {}", id);
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            log.info("Post with Id not found :{}", id);
            return false;
        }
        postRepository.delete(post);
        return true;

    }

    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create pageable instance
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : posts.getContent()) {
            PostDTO postDTO = modelMapperService.map(post, PostDTO.class);
            List<CommentDTO> commentDTOList = modelMapperService.mapAll(post.getComments(), CommentDTO.class);
            postDTO.setCommentDTOs(new HashSet<>(commentDTOList));
            postDTOList.add(postDTO);
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOList);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }
}