package com.pawer.mapper;

import com.pawer.dto.request.PostSaveRequestDto;
import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.repository.entity.Post;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-26T03:41:13+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class IPostMapperImpl implements IPostMapper {

    @Override
    public PostFindAllResponse toPostFindAllResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostFindAllResponse.PostFindAllResponseBuilder postFindAllResponse = PostFindAllResponse.builder();

        postFindAllResponse.id( post.getId() );
        postFindAllResponse.userId( post.getUserId() );
        postFindAllResponse.username( post.getUsername() );
        postFindAllResponse.name( post.getName() );
        postFindAllResponse.surname( post.getSurname() );
        postFindAllResponse.content( post.getContent() );
        postFindAllResponse.url( post.getUrl() );
        postFindAllResponse.likeCount( post.getLikeCount() );
        postFindAllResponse.date( post.getDate() );
        postFindAllResponse.time( post.getTime() );

        return postFindAllResponse.build();
    }

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
