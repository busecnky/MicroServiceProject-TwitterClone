package com.pawer.mapper;

import com.pawer.dto.request.CreatePostDto;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPostMapper {
    IPostMapper INSTANCE= Mappers.getMapper(IPostMapper.class);
    ModelCreatePost toCreatePost(final CreatePostDto model);
    ModelCreatePost toCreatePost(final User user);


}
