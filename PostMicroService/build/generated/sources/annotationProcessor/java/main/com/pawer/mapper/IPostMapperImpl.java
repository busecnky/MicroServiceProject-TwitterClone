package com.pawer.mapper;

import com.pawer.dto.request.PostSaveRequestDto;
import com.pawer.repository.entity.Post;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-26T03:07:46+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class IPostMapperImpl implements IPostMapper {

    @Override
    public PostSaveRequestDto fromPost(Post post) {
        if ( post == null ) {
            return null;
        }

        PostSaveRequestDto.PostSaveRequestDtoBuilder postSaveRequestDto = PostSaveRequestDto.builder();

        postSaveRequestDto.id( post.getId() );
        postSaveRequestDto.userId( post.getUserId() );
        postSaveRequestDto.username( post.getUsername() );
        postSaveRequestDto.name( post.getName() );
        postSaveRequestDto.surname( post.getSurname() );
        postSaveRequestDto.content( post.getContent() );
        postSaveRequestDto.url( post.getUrl() );
        postSaveRequestDto.likeCount( post.getLikeCount() );
        postSaveRequestDto.date( post.getDate() );
        postSaveRequestDto.time( post.getTime() );

        return postSaveRequestDto.build();
    }
}
