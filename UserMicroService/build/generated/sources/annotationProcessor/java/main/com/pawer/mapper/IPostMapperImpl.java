package com.pawer.mapper;

import com.pawer.dto.request.CommentToPostDto;
import com.pawer.dto.request.CreatePostDto;
import com.pawer.rabbitmq.messagemodel.ModelCreateCommentToPost;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.repository.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-04T19:19:31+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class IPostMapperImpl implements IPostMapper {

    @Override
    public ModelCreatePost toCreatePost(CreatePostDto model) {
        if ( model == null ) {
            return null;
        }

        ModelCreatePost.ModelCreatePostBuilder modelCreatePost = ModelCreatePost.builder();

        modelCreatePost.content( model.getContent() );
        modelCreatePost.token( model.getToken() );

        return modelCreatePost.build();
    }

    @Override
    public ModelCreatePost toCreatePost(User user) {
        if ( user == null ) {
            return null;
        }

        ModelCreatePost.ModelCreatePostBuilder modelCreatePost = ModelCreatePost.builder();

        modelCreatePost.username( user.getUsername() );
        modelCreatePost.name( user.getName() );
        modelCreatePost.surname( user.getSurname() );

        return modelCreatePost.build();
    }

    @Override
    public ModelCreateCommentToPost toCreateComment(CommentToPostDto dto) {
        if ( dto == null ) {
            return null;
        }

        ModelCreateCommentToPost.ModelCreateCommentToPostBuilder modelCreateCommentToPost = ModelCreateCommentToPost.builder();

        modelCreateCommentToPost.token( dto.getToken() );
        modelCreateCommentToPost.postId( dto.getPostId() );
        modelCreateCommentToPost.comment( dto.getComment() );

        return modelCreateCommentToPost.build();
    }
}
