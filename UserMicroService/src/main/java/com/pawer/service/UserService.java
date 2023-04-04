package com.pawer.service;

import com.pawer.dto.request.CommentToPostDto;
import com.pawer.dto.request.CreatePostDto;
import com.pawer.dto.request.FindByIdRequestDto;
import com.pawer.dto.request.UpdateUserProfileRequestDto;
import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.exception.EErrorType;
import com.pawer.exception.UserException;
import com.pawer.mapper.IPostMapper;
import com.pawer.mapper.IUserMapper;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelSave;
import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import com.pawer.rabbitmq.producer.ProducerDirectService;
import com.pawer.repository.IUserRepository;
import com.pawer.repository.entity.User;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends ServiceManagerImpl<User,Long> {
    private final IUserRepository userRepository;
    private final ProducerDirectService producerDirectService;
    private final JwtTokenManager jwtTokenManager;


    public UserService(IUserRepository userRepository, ProducerDirectService producerDirectService, JwtTokenManager jwtTokenManager) {
        super(userRepository);
        this.userRepository = userRepository;
        this.producerDirectService = producerDirectService;
        this.jwtTokenManager = jwtTokenManager;
    }
    public  void createUser(ModelSave modelSave){
        User user= IUserMapper.INSTANCE.toUser(modelSave);
        save(user);
    }

    public Boolean  createPost(CreatePostDto dto){
        Optional<Long> id = jwtTokenManager.validToken(dto.getToken());
        System.out.println("----------------------------" + dto.getToken());
        System.out.println("----------------------------" + id);
        if (id.isEmpty()) throw new UserException(EErrorType.INVALID_TOKEN);
        User user = findById(id.get()).get();
        ModelCreatePost modelCreatePost = IPostMapper.INSTANCE.toCreatePost(user);
        modelCreatePost.setToken(dto.getToken());
        modelCreatePost.setContent(dto.getContent());
        producerDirectService.sendCreatePost(modelCreatePost);
        return true;
    }

    public Boolean updateUserProfile(UpdateUserProfileRequestDto dto){
        Optional<Long> userId=jwtTokenManager.validToken(dto.getToken());
        if (userId.isEmpty()){
            throw new UserException(EErrorType.INVALID_TOKEN);
        }

        User user = userRepository.findOptionalByUsername(dto.getUsername()).get();
        if (user==null){
            throw new UserException(EErrorType.USER_NOT_FOUND);
        }
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAge(dto.getAge());
        update(user);
        ModelUpdateUser modelUpdateUser=ModelUpdateUser.builder()
                .authId(user.getAuthId())
                .surname(user.getSurname())
                .name(user.getName())
                .age(user.getAge())
                .build();
        producerDirectService.sendUpdateUser(modelUpdateUser);
        return true;
    }

    public FindByIdResponseDto findByIdFromToken(FindByIdRequestDto dto){
        Optional<Long> userId=jwtTokenManager.validToken(dto.getToken());
        if (userId.isEmpty()){
            throw new UserException(EErrorType.INVALID_TOKEN);
        }
        Optional<User> user = findById(userId.get());

        return IUserMapper.INSTANCE.toFindByIdResponseDto(user.get());
    }

    public boolean createCommentToPost(CommentToPostDto dto){
        producerDirectService.sendCreateCommentToPost(IPostMapper.INSTANCE.toCreateComment(dto));
        return true;
    }

}
