package com.pawer.mapper;

import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.rabbitmq.messagemodel.ModelUserSave;
import com.pawer.repository.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-23T18:30:32+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class IUserMapperImpl implements IUserMapper {

    @Override
    public User toUser(ModelUserSave model) {
        if ( model == null ) {
            return null;
        }

        User.UserBuilder<?, ?> user = User.builder();

        user.authId( model.getAuthId() );
        user.username( model.getUsername() );
        user.name( model.getName() );
        user.surname( model.getSurname() );
        user.email( model.getEmail() );

        return user.build();
    }

    @Override
    public FindByIdResponseDto toFindByIdResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        FindByIdResponseDto.FindByIdResponseDtoBuilder findByIdResponseDto = FindByIdResponseDto.builder();

        findByIdResponseDto.name( user.getName() );
        findByIdResponseDto.surname( user.getSurname() );
        findByIdResponseDto.username( user.getUsername() );

        return findByIdResponseDto.build();
    }
}
