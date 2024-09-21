package com.blog.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.Dto.PostDto;
import com.blog.entities.Comment;
import com.blog.entities.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

	Optional<List<Comment>> findByPost(Post post);
}
