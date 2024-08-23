package cyou.forum.controller;

import cyou.forum.dto.PostDeleteDto;
import cyou.forum.dto.PostDto;
import cyou.forum.dto.PostViewDto;
import cyou.forum.entity.Post;
import cyou.forum.service.CommentService;
import cyou.forum.service.PostService;
import cyou.forum.service.PostTagService;
import cyou.forum.service.ReactionPostOrCommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/forum")
public class ForumController {

    private final PostService postService;
    private final PostTagService postTagService;
    private final CommentService commentService;
    private final ReactionPostOrCommentService reactionPostOrCommentService;

    @GetMapping
    public String postPage(Model model) {
        model.addAttribute("postDetailUrl", MvcUriComponentsBuilder.fromMethodName(ForumController.class, "getPostView", "").toUriString());
        return "post/post";
    }

    @GetMapping("/api/posts")
    @ResponseBody
    public Map<String, Object> getPosts(@RequestParam int page, @RequestParam int size, @RequestParam String sortOrder) {

        Sort sort = switch (sortOrder) {
            case "viewMostCount" -> Sort.by(Sort.Direction.DESC, "viewCount");
            case "viewLessCount" -> Sort.by(Sort.Direction.ASC, "viewCount");
            case "reactionMostCount" -> Sort.by(Sort.Direction.DESC, "reactionPosts.size");
            case "reactionLessCount" -> Sort.by(Sort.Direction.ASC, "reactionPosts.size");
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };

        Page<Post> postPage = postService.getAllPosts(PageRequest.of(page - 1, size, sort));

        Map<String, Object> response = new HashMap<>();
        List<PostDto> postDtos = new ArrayList<>();

        postPage.getContent().forEach(x -> postDtos.add(getPostDto(x)));

        response.put("posts", postDtos);
        response.put("totalPages", postPage.getTotalPages());

        return response;
    }

    private PostDto getPostDto(Post post) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        todo need to get user details form userProfile service
        var postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setNumber(post.getNumber());
        postDto.setCreatedBy("Lalith Kahatapitiya");
        postDto.setProfileUrl("https://lh3.googleusercontent.com/a/ACg8ocJRH_OSbF2Rwv2IUTEEnPiYt2060hjVXv3pPCELza9PJTZtsSQ=s96-c");
        postDto.setCreatedAt(localDateTimeToString(post.getCreatedAt()));
        postDto.setReactCount(post.getReactionPosts().size());
        postDto.setViewCount(post.getViewCount());
        postDto.setEditable(post.getCreatedBy().equals(username));
        return postDto;
    }

    private String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    private String commonForPost(Model model, Post post) {
        model.addAttribute("post", post);
        model.addAttribute("postTags", postTagService.findAll());
        return "post/addPost";
    }

    @GetMapping("/post/add")
    public String createPostPage(Model model) {
        return commonForPost(model, new Post());
    }

    @GetMapping("/post/edit/{number}")
    public String editPostPage(@PathVariable("number") String number, Model model) {
        model.addAttribute("postDUrl", MvcUriComponentsBuilder.fromMethodName(ForumController.class, "deletePost", "").toUriString());
        return commonForPost(model, postService.findByNumber(number));
    }

    @PostMapping("/post")
    public String savePostPage(@Valid @ModelAttribute Post post, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return commonForPost(model, post);
        }
        postService.persist(post);
        return "redirect:/forum";
    }

    @GetMapping("/delete/{number}")
    @ResponseBody
    public ResponseEntity<PostDeleteDto> deletePost(@PathVariable("number") String number) {
        var status = postService.deleteByNumber(number);
        var redirectUrl = status ? MvcUriComponentsBuilder.fromMethodName(ForumController.class, "postPage", "").toUriString() : "";
        return new ResponseEntity<>(new PostDeleteDto(status, redirectUrl), HttpStatus.OK);
    }

    //    dummy method for view
    @GetMapping("/num/{num}")
    public String getView(@PathVariable("num") String num, Model model) {

        return "post/view";
    }

    @GetMapping("/view/{number}")
    @ResponseBody
    public ResponseEntity<PostViewDto> getPostView(@PathVariable("number") String number) {
        Post post = postService.findByNumber(number);
//        todo need to create postViewDto
        var postViewDto = new PostViewDto();
        postViewDto.setNumber(post.getNumber());
        postViewDto.setTitle(post.getTitle());
        postViewDto.setContent(post.getContent());
        return new ResponseEntity<>(postViewDto, HttpStatus.OK);
    }
}
