package com.pawer.mapper;


import com.pawer.dto.request.PostSaveRequestDto;
import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.repository.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPostMapper {
    IPostMapper INSTANCE= Mappers.getMapper(IPostMapper.class);


   // Post toPost(final ModelCreatePost modelCreatePost);

    PostFindAllResponse toPostFindAllResponse(final Post post);


    Post toPost(final PostSaveRequestDto dto);

}