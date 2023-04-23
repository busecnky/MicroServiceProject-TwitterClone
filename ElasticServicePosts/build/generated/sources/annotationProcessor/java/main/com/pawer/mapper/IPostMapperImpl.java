package com.pawer.mapper;

import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.repository.entity.Post;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-23T02:56:09+0300",
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
}
