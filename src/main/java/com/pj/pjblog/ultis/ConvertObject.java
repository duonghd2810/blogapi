package com.pj.pjblog.ultis;

import com.pj.pjblog.dao.Blog;
import com.pj.pjblog.dao.User;
import com.pj.pjblog.dto.BlogDTO;
import com.pj.pjblog.dto.UserDTO;

public class ConvertObject {
    public static User fromUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }

    public static Blog fromBlogDTOToBlog(BlogDTO blogDTO) {
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        return blog;
    }
}
