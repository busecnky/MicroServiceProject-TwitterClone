package com.pawer.service;

import com.pawer.dto.request.AuthLoginDto;
import com.pawer.dto.request.AuthRegisterRequestDto;
import com.pawer.dto.request.ChangePasswordDto;
import com.pawer.dto.response.AuthLoginResponse;
import com.pawer.exception.AuthException;
import com.pawer.exception.EErrorType;
import com.pawer.mapper.IAuthMapper;
import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import com.pawer.rabbitmq.producer.ProducerDirectSave;
import com.pawer.repository.IAuthRepository;
import com.pawer.repository.entity.Auth;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManagerImpl<Auth,Long> {
    private final IAuthRepository authRepository;
    private final ProducerDirectSave producerDirectSave;
    private final JwtTokenManager jwtTokenManager;

    public AuthService(IAuthRepository authRepository, ProducerDirectSave producerDirectSave, JwtTokenManager jwtTokenManager) {
        super(authRepository);
        this.authRepository=authRepository;
        this.producerDirectSave = producerDirectSave;
        this.jwtTokenManager = jwtTokenManager;
    }
    public  Boolean register(AuthRegisterRequestDto dto) {
        if (!dto.getPassword().equals(dto.getRepassword()))
            throw new AuthException(EErrorType.AUTH_PASSWORD_ERROR);
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        if (authRepository.findByUsername(auth.getUsername()).stream().count()!=0){
            throw new AuthException(EErrorType.AUTH_USERNAME_ERROR);}
        save(auth);
        producerDirectSave.sendMessageSaveUser(IAuthMapper.INSTANCE.ToModel(auth));
        return true;
    }

    public AuthLoginResponse doLogin(AuthLoginDto dto){
        Optional<Auth> auth = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(),dto.getPassword());
        if(auth.isEmpty())
            throw new AuthException(EErrorType.AUTH_PARAMETRE_ERROR);
        Optional<String> token = jwtTokenManager.createToken(auth.get().getId());
        if(token.isEmpty())
            throw new AuthException(EErrorType.INVALID_TOKEN);

        return AuthLoginResponse.builder()
                .token(token.get())
                .build();
    }

    public Boolean changePassword(ChangePasswordDto dto){
        Long id= jwtTokenManager.validToken(dto.getToken()).get();
        Auth auth= findById(id).get();

        if (auth==null) throw new AuthException(EErrorType.INVALID_TOKEN);
        if (auth.getPassword()!= dto.getOldpassword()) throw new AuthException(EErrorType.AUTH_PASSWORD_ERROR);
        if (dto.getNewpassword()!= dto.getConfirmpassword()) throw new AuthException(EErrorType.AUTH_PASSWORD_ERROR);
        auth.setPassword(dto.getNewpassword());
        update(auth);
        return true;
    }


    public void updateAuth(ModelUpdateUser model){

        if (model.getAuthId()==null) throw new AuthException(EErrorType.BAD_REQUEST_ERROR);
        Auth auth = findById(model.getAuthId()).get();
        if (auth==null) throw new AuthException(EErrorType.BAD_REQUEST_ERROR);
        auth.setName(model.getName());
        auth.setSurname(model.getSurname());
        if (auth.getId()==null||auth.getId() == -1)throw new AuthException(EErrorType.BAD_REQUEST_ERROR) ;
        update(auth);
    }
}
