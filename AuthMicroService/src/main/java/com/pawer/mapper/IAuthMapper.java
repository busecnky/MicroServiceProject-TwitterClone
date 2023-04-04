package com.pawer.mapper;



import com.pawer.dto.request.AuthLoginDto;
import com.pawer.dto.request.AuthRegisterRequestDto;
import com.pawer.rabbitmq.messagemodel.ModelSave;
import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import com.pawer.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth toAuth(final AuthRegisterRequestDto dto);


    Auth toAuth(final AuthLoginDto dto);

    @Mapping(source = "id",target = "authId")
    ModelSave ToModel(final Auth auth);
    @Mapping(source = "authId",target = "id")
    Auth toAuth(final ModelUpdateUser modelUpdateUser);
}
