package com.pj.pjblog.controllers;

import com.pj.pjblog.dao.Blog;
import com.pj.pjblog.dao.User;
import com.pj.pjblog.dto.BlogDTO;
import com.pj.pjblog.exceptions.NotFoundException;
import com.pj.pjblog.repositories.BlogRepository;
import com.pj.pjblog.repositories.UserRepository;
import com.pj.pjblog.ultis.ConvertObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blogs")
@CrossOrigin(origins = "*")
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllBlog() {
        List<Blog> listBlog = blogRepository.findAll();
        if (listBlog.size() == 0) {
            throw new NotFoundException("Khong co blog nao");
        }
        return ResponseEntity.status(200).body(listBlog);
    }

    @PostMapping("/{id_user}")
    public ResponseEntity<?> createBlog(@RequestParam("title") String title,
                                        @RequestParam("content") String content,
                                        @PathVariable("id_user") Integer idUser) {
        Optional<User> user = userRepository.findById(idUser);
        if (user.isEmpty()) {
            throw new NotFoundException("khong tim thay");
        }
        Blog blog = new Blog(title, content, user.get());
        user.get().getBlogs().add(blog);
        Blog blog1 = blogRepository.save(blog);
        return ResponseEntity.status(200).body(blog1);
    }

    @PatchMapping("/{blog_id}")
    public ResponseEntity<?> updateBlog(@RequestBody BlogDTO blogDTO,
                                        @PathVariable(name = "blog_id") Integer blogId) {
        Optional<Blog> blog = blogRepository.findById(blogId);
        if (blog.isEmpty()) {
            throw new NotFoundException("Khong co blog nay");
        }
        Blog newBlog = ConvertObject.fromBlogDTOToBlog(blogDTO);
        newBlog.setBlogId(blog.get().getBlogId());
        return ResponseEntity.status(201).body(blogRepository.save(newBlog));
    }

    @DeleteMapping("/{blog_id}")
    public ResponseEntity<?> deleteBlog(@PathVariable(name = "blog_id") Integer blogId) {
        Optional<Blog> blog = blogRepository.findById(blogId);
        if (blog.isEmpty()) {
            throw new NotFoundException("Khong co blog nay");
        }
        blogRepository.deleteById(blogId);
        return ResponseEntity.status(201).body("Xoa thanh cong");
    }
}
