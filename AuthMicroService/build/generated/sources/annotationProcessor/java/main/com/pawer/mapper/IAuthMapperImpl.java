package com.pawer.mapper;

import com.pawer.dto.request.AuthRegisterRequestDto;
import com.pawer.rabbitmq.messagemodel.ModelUserSave;
import com.pawer.repository.entity.Auth;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-07T00:47:17+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
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
}
