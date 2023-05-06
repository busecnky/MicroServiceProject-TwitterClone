package com.pawer.mapper;

import com.pawer.dto.request.PostSaveRequestDto;
import com.pawer.repository.entity.Post;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-07T00:52:29+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class IPostMapperImpl implements IPostMapper {

    @Override
    public Post toPost(PostSaveRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Post.PostBuilder<?, ?> post = Post.builder();

        post.id( dto.getId() );
        post.userId( dto.getUserId() );
        post.username( dto.getUsername() );
        post.name( dto.getName() );
        post.surname( dto.getSurname() );
        post.content( dto.getContent() );
        post.url( dto.getUrl() );
        post.likeCount( dto.getLikeCount() );
        post.date( dto.getDate() );
        post.time( dto.getTime() );

        return post.build();
    }
}
