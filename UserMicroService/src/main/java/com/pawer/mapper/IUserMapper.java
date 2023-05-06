package com.pawer.mapper;

import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.rabbitmq.messagemodel.ModelUserSave;
import com.pawer.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);
    User toUser(final ModelUserSave model);
    FindByIdResponseDto toFindByIdResponseDto(final User user);
}
