package cyou.forum.main_form_app.controller;

import cyou.CommonService;
import cyou.enums.Reaction;
import cyou.forum.comment.entity.Comment;
import cyou.forum.comment.service.CommentService;
import cyou.forum.comment_reaction.entity.CommentReaction;
import cyou.forum.comment_reaction.service.CommentReactionService;
import cyou.forum.main_form_app.dto.*;
import cyou.forum.main_form_app.mappers.CommentMapper;
import cyou.forum.post.entity.Post;
import cyou.forum.post.service.PostService;
import cyou.forum.post_reaction.entity.PostReaction;
import cyou.forum.post_reaction.service.PostReactionService;
import cyou.forum.post_tag.service.PostTagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/forum")
@Slf4j
public class ForumController {

    private final PostService postService;
    private final PostTagService postTagService;
    private final CommentService commentService;
    private final PostReactionService postReactionService;
    private final CommentReactionService commentReactionService;
    private final CommonService commonService;

    @GetMapping
    public String postPage(Model model) {
        model.addAttribute("postDetailUrl", MvcUriComponentsBuilder.fromMethodName(ForumController.class, "getPostView", "").toUriString());
        model.addAttribute("persistCommentUrl", MvcUriComponentsBuilder.fromMethodName(ForumController.class, "persistComment", "").toUriString());
        model.addAttribute("deleteCommentUrl", MvcUriComponentsBuilder.fromMethodName(ForumController.class, "deleteComment", "").toUriString());
        model.addAttribute("persistPostReactionUrl", removeTrailingSlash(MvcUriComponentsBuilder.fromMethodName(ForumController.class, "persistPostReaction", "", "").toUriString()));
        model.addAttribute("getPostReactionUrl", MvcUriComponentsBuilder.fromMethodName(ForumController.class, "getPostReaction", "").toUriString());
        model.addAttribute("getCommentUrl",
                MvcUriComponentsBuilder.fromMethodName(ForumController.class, "getComments", "", "", "").toUriString());
        model.addAttribute("persistCommentReactionUrl",
                removeTrailingSlash(MvcUriComponentsBuilder.fromMethodName(ForumController.class, "persistCommentReaction", "", "").toUriString()));
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
        postDto.setReactCount(post.getPostReactions().size());
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

    @GetMapping("/view/{number}")
    @ResponseBody
    public ResponseEntity<PostViewDto> getPostView(@PathVariable("number") String number) {
        Post post = postService.findByNumber(number);
        post.setViewCount(post.getViewCount() + 1);
        post = postService.persist(post);

        var postViewDto = new PostViewDto();
        postViewDto.setNumber(post.getNumber());
        postViewDto.setTitle(post.getTitle());
        postViewDto.setContent(post.getContent());
//        todo get data from userprofile
        postViewDto.setPostOwner("Lalith Kahatapitiya");
        postViewDto.setPostViewCount(post.getViewCount());
        return new ResponseEntity<>(postViewDto, HttpStatus.OK);
    }

    private Map<Reaction, Long> allReactionList() {
        Map<Reaction, Long> reactionCounts = new EnumMap<>(Reaction.class);
        for (Reaction reaction : Reaction.values()) {
            reactionCounts.put(reaction, 0L);
        }
        return reactionCounts;
    }

    private Map<Reaction, Long> countReactionPost(List<PostReaction> reactions) {
        Map<Reaction, Long> reactionCounts = allReactionList();
        reactionCounts.putAll(reactions.stream().filter(reaction -> reaction.getReaction() != null).collect(Collectors.groupingBy(PostReaction::getReaction, Collectors.counting())));
        return reactionCounts;
    }

    @GetMapping("/view/{number}/{reaction}")
    public String persistPostReaction(@PathVariable("number") String number, @PathVariable("reaction") Reaction reaction) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var post = postService.findByNumber(number);
        PostReaction postReaction = postReactionService.findByPostAndCreatedBy(post, username);
        if (postReaction == null) {
            postReaction = new PostReaction(reaction, post);
            postReactionService.persist(postReaction);
        } else {
            if (postReaction.getReaction().equals(reaction)) {
                postReactionService.deleteByPostReaction(postReaction);
            } else {
                postReaction.setReaction(reaction);
                postReactionService.persist(postReaction);
            }
        }
        return "redirect:/forum/post/reaction/" + number;
    }

    @GetMapping("/post/reaction/{number}")
    @ResponseBody
    public Map<Reaction, Long> getPostReaction(@PathVariable("number") String number) {
        var post = postService.findByNumber(number);
        return countReactionPost(post.getPostReactions());
    }

    private String removeTrailingSlash(String url) {
        if (url != null && url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    private final CommentMapper commentMapper;

    @PostMapping("/post/comment")
    @ResponseBody
    private ResponseEntity<String> persistComment(@RequestBody CommentSaveDto commentSaveDto) {
        commonService.printAttributesInObject(commentSaveDto);
        Comment comment = new Comment();
        if (commentSaveDto.getCommentId() != null) {
            if (commentSaveDto.isEdit()) {
                comment = commentService.findById(commentSaveDto.getCommentId());
            } else {
                comment = new Comment();
                comment.setParentComment(commentService.findById(commentSaveDto.getCommentId()));
            }
            comment.setContent(commentSaveDto.getContent());
        } else if (commentSaveDto.getNumber() != null) {
            comment = new Comment();
            var post = postService.findByNumber(commentSaveDto.getNumber());
            comment.setPost(post);
            comment.setContent(commentSaveDto.getContent());
        }
        commentService.persist(comment);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/comment/{number}")
    @ResponseBody
    public Page<CommentViewDto> getComments(@PathVariable("number") String number,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size) {
        log.info("number {},  page {},  size  {}", number, page, size);
        var post = postService.findByNumber(number);
        log.info("come here list {}", LocalDateTime.now());
        return commentService.findByPost(post, page, size);
    }

    @GetMapping("/view/comment/{id}/{reaction}")
    public String persistCommentReaction(@PathVariable("id") long id, @PathVariable("reaction") Reaction reaction) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var comment = commentService.findById(id);
        CommentReaction postReaction = commentReactionService.findByCommentAndCreatedBy(comment, username);
        if (postReaction == null) {
            postReaction = new CommentReaction(reaction, comment);
            commentReactionService.persist(postReaction);
        } else {
            if (postReaction.getReaction().equals(reaction)) {
                commentReactionService.deleteByCommentReaction(postReaction);
            } else {
                postReaction.setReaction(reaction);
                commentReactionService.persist(postReaction);
            }
        }
        return "redirect:/forum/comment/reaction/" + id;
    }

    @GetMapping("/comment/reaction/{id}")
    @ResponseBody
    public Map<Reaction, Long> getCommentReaction(@PathVariable("id") long id) {
        var comment = commentService.findById(id);
        return countReactionComment(comment.getCommentReactions());
    }

    private Map<Reaction, Long> countReactionComment(List<CommentReaction> reactions) {
        Map<Reaction, Long> reactionCounts = allReactionList();
        reactionCounts.putAll(reactions.stream().filter(reaction -> reaction.getReaction() != null).collect(Collectors.groupingBy(CommentReaction::getReaction, Collectors.counting())));
        return reactionCounts;
    }

    @GetMapping("/delete/comment/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteComment(@PathVariable("id") Long id) {
        var comment = commentService.findById(id);
        var delete = false;
        if (comment.getCreatedBy().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            delete = commentService.deleteByComment(comment);
        }
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

}
