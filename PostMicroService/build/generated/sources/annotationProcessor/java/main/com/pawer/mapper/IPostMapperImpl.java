package com.pawer.mapper;

import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.repository.entity.Post;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-06T16:43:12+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class IPostMapperImpl implements IPostMapper {

    @Override
    public Post toPost(ModelCreatePost modelCreatePost) {
        if ( modelCreatePost == null ) {
            return null;
        }

        Post.PostBuilder<?, ?> post = Post.builder();

        post.username( modelCreatePost.getUsername() );
        post.name( modelCreatePost.getName() );
        post.surname( modelCreatePost.getSurname() );
        post.content( modelCreatePost.getContent() );

        return post.build();
    }

    @Override
    public PostFindAllResponse toPostFindAllResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostFindAllResponse.PostFindAllResponseBuilder postFindAllResponse = PostFindAllResponse.builder();

        postFindAllResponse.userId( post.getUserId() );
        postFindAllResponse.username( post.getUsername() );
        postFindAllResponse.name( post.getName() );
        postFindAllResponse.surname( post.getSurname() );
        postFindAllResponse.content( post.getContent() );
        if ( post.getDate() != null ) {
            postFindAllResponse.date( LocalDate.parse( post.getDate() ) );
        }

        return postFindAllResponse.build();
    }
}
