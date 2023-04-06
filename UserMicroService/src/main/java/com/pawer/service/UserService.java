package com.pawer.service;

import com.pawer.dto.request.*;
import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.exception.EErrorType;
import com.pawer.exception.UserException;
import com.pawer.mapper.IPostMapper;
import com.pawer.mapper.IUserMapper;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelSave;
import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import com.pawer.rabbitmq.producer.ProducerDirectService;
import com.pawer.repository.IFollowRepository;
import com.pawer.repository.IFollowerRepository;
import com.pawer.repository.IUserRepository;
import com.pawer.repository.entity.Follow;
import com.pawer.repository.entity.Follower;
import com.pawer.repository.entity.User;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends ServiceManagerImpl<User,Long> {
    private final IUserRepository userRepository;
    private final IFollowRepository followRepository;
    private final IFollowerRepository followerRepository;
    private final ProducerDirectService producerDirectService;
    private final JwtTokenManager jwtTokenManager;


    public UserService(IUserRepository userRepository, IFollowRepository followRepository, IFollowerRepository followerRepository, ProducerDirectService producerDirectService, JwtTokenManager jwtTokenManager) {
        super(userRepository);
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.followerRepository = followerRepository;
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
        if (dto.getComment() ==null || dto.getComment()==""|| dto.getToken()==null||dto.getToken()==""){
            throw new UserException(EErrorType.BAD_REQUEST_ERROR,"create comment de hata eksik veya hatali bilgi");
        }
        producerDirectService.sendCreateCommentToPost(IPostMapper.INSTANCE.toCreateComment(dto));
        return true;
    }

    public Integer followUser(FollowingUserRequestDto dto) {
        Optional<Long> userId=jwtTokenManager.validToken(dto.getToken());

        Optional<User> followUser = userRepository.findOptionalByUsername(dto.getUsername());
// bu aç kapa olmasın diye kullanıcı oluşturulurkene çekilecek.
        followRepository.save(Follow.builder()
                .followId(followUser.get().getId())
                .userId(userId.get())
                .build());

        Optional<Follow> follow = followRepository.findOptionalByUserIdAndFollowId(userId.get(), followUser.get().getId());
        System.out.println("*/*/*/*/*/*/*/" + follow.get().getFollowRequest());

        if (follow.get().getFollowRequest()==0) {
            follow.get().setFollowRequest(1);
            System.out.println("112de 1 bastırmasını bekliyoruz" + follow.get().getFollowRequest());
            followRepository.save(follow.get());
            followerRepository.save(Follower.builder()
                            .followerId(userId.get())
                            .userId(follow.get().getFollowId())
                            .statee(1)
                    .build());
            return 1;
        }else if (follow.get().getFollowRequest()==1){ //bu istek ve isteği geri çekme
            //follower statee i 0 a çekilecek. (follower eklerken dikkatli eklenecek)
            follow.get().setFollowRequest(0);
            followRepository.save(follow.get());
            return 0;
        }else if (follow.get().getFollowRequest()==2){
            follow.get().setFollowRequest(2);
            followRepository.save(follow.get());
            return 0;
        }else{
            return 3;
        }
    }

    public Integer acceptFollower(AcceptFollowerRequestDto dto) {
        Optional<Long> userId=jwtTokenManager.validToken(dto.getToken());

        Optional<User> followerUser = userRepository.findOptionalByUsername(dto.getUsername());

        Optional<Follower> follower = followerRepository.findOptionalByUserIdAndFollowerId(userId.get(),followerUser.get().getId());

        Optional<Follow> follow = followRepository.findOptionalByUserIdAndFollowId(followerUser.get().getId(), userId.get());
        if (follower.get().getStatee()==0) {
            //followerRepository.save(follower.get()); //furkan dursun dedi ama buse silmek istedi
            return 0;
        }else if (follower.get().getStatee()==1) {
            follower.get().setStatee(2);
            followerRepository.save(follower.get());
            follow.get().setFollowRequest(2);
            followRepository.save(follow.get());
            return 2;
        }else if(follower.get().getStatee()==2){
            follower.get().setStatee(0);
            followerRepository.save(follower.get());
            follow.get().setFollowRequest(0);
            followRepository.save(follow.get());
            return 0;
        }else{
            return 3;
        }
    }
}
