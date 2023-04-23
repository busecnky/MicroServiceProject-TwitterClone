package com.pawer.mapper;

import com.pawer.dto.request.AuthLoginDto;
import com.pawer.dto.request.AuthRegisterRequestDto;
import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import com.pawer.rabbitmq.messagemodel.ModelUserSave;
import com.pawer.repository.entity.Auth;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-23T02:50:30+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class IAuthMapperImpl implements IAuthMapper {

    @Override
    public Auth toAuth(AuthRegisterRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Auth.AuthBuilder<?, ?> auth = Auth.builder();

        auth.username( dto.getUsername() );
        auth.name( dto.getName() );
        auth.surname( dto.getSurname() );
        auth.password( dto.getPassword() );
        auth.email( dto.getEmail() );

        return auth.build();
    }

    @Override
    public Auth toAuth(AuthLoginDto dto) {
        if ( dto == null ) {
            return null;
        }

        Auth.AuthBuilder<?, ?> auth = Auth.builder();

        auth.username( dto.getUsername() );
        auth.password( dto.getPassword() );

        return auth.build();
    }

    @Override
    public ModelUserSave ToModel(Auth auth) {
        if ( auth == null ) {
            return null;
        }

        ModelUserSave.ModelUserSaveBuilder modelUserSave = ModelUserSave.builder();

        modelUserSave.authId( auth.getId() );
        modelUserSave.username( auth.getUsername() );
        modelUserSave.name( auth.getName() );
        modelUserSave.surname( auth.getSurname() );
        modelUserSave.email( auth.getEmail() );

        return modelUserSave.build();
    }

    @Override
    public Auth toAuth(ModelUpdateUser modelUpdateUser) {
        if ( modelUpdateUser == null ) {
            return null;
        }

        Auth.AuthBuilder<?, ?> auth = Auth.builder();

        auth.id( modelUpdateUser.getAuthId() );
        auth.name( modelUpdateUser.getName() );
        auth.surname( modelUpdateUser.getSurname() );

        return auth.build();
    }
}
