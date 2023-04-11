package com.pawer.service;

//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
import com.pawer.dto.request.*;
import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.exception.EErrorType;
import com.pawer.exception.UserException;
import com.pawer.mapper.IPostMapper;
import com.pawer.mapper.IUserMapper;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelUserSave;
import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import com.pawer.rabbitmq.producer.ProducerDirectService;
import com.pawer.repository.IUserRepository;
import com.pawer.repository.entity.User;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService extends ServiceManagerImpl<User, Long> {
    private final IUserRepository userRepository;

    private final ProducerDirectService producerDirectService;
    private final JwtTokenManager jwtTokenManager;
    private final FollowService followService;
    private final FollowerService followerService;

//    @Value("${myproject.google.storage.bucketname}")
//    private String bucketname;
//    @Getter
//    @Autowired
//    Storage storage;

    public UserService(IUserRepository userRepository, ProducerDirectService producerDirectService
            , JwtTokenManager jwtTokenManager, @Lazy FollowService followService, @Lazy FollowerService followerService) {
        super(userRepository);
        this.userRepository = userRepository;
        this.producerDirectService = producerDirectService;
        this.jwtTokenManager = jwtTokenManager;
        this.followService = followService;
        this.followerService = followerService;
    }


    public void createUser(ModelUserSave modelUserSave)  {

        User user = IUserMapper.INSTANCE.toUser(modelUserSave);
        user.setCreateDate(LocalDateTime.now().toString());
        user.setUpdateDate(LocalDateTime.now().toString());
        save(user);

        new Thread(()->{
            followService.createFollowForNewUser(user.getId());
        }).start();

        new Thread(()->{
            followerService.createFollowerForNewUser(user.getId());
        }).start();
    }


    public Boolean createPost(CreatePostDto dto) {
        Optional<Long> id = jwtTokenManager.validToken(dto.getToken());
        System.out.println("----------------------------" + dto.getToken());
        System.out.println("----------------------------" + id);
        if (id.isEmpty()) throw new UserException(EErrorType.INVALID_TOKEN);
        User user = findById(id.get()).get();
        ModelCreatePost modelCreatePost = IPostMapper.INSTANCE.toCreatePost(user);
        modelCreatePost.setToken(dto.getToken());
        modelCreatePost.setContent(dto.getContent());
        modelCreatePost.setImage(dto.getImage());
        System.out.println(modelCreatePost.getImage());
        producerDirectService.sendCreatePost(modelCreatePost);
        return true;
    }

    public Boolean updateUserProfile(UpdateUserProfileRequestDto dto) {
        Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());
        if (userId.isEmpty()) {
            throw new UserException(EErrorType.INVALID_TOKEN);
        }

        User user = userRepository.findOptionalByUsername(dto.getUsername()).get();
        if (user == null) {
            throw new UserException(EErrorType.USER_NOT_FOUND);
        }
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAge(dto.getAge());
        update(user);
        ModelUpdateUser modelUpdateUser = ModelUpdateUser.builder()
                .authId(user.getAuthId())
                .surname(user.getSurname())
                .name(user.getName())
                .age(user.getAge())
                .build();
        producerDirectService.sendUpdateUser(modelUpdateUser);
        return true;
    }

    public FindByIdResponseDto findByIdFromToken(FindByIdRequestDto dto) {
        Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());
        if (userId.isEmpty()) {
            throw new UserException(EErrorType.INVALID_TOKEN);
        }
        Optional<User> user = findById(userId.get());

        return IUserMapper.INSTANCE.toFindByIdResponseDto(user.get());
    }

    public boolean createCommentToPost(CommentToPostDto dto) {
        if (dto.getComment() == null || dto.getComment() == "" || dto.getToken() == null || dto.getToken() == "") {
            throw new UserException(EErrorType.BAD_REQUEST_ERROR, "create comment de hata eksik veya hatali bilgi");
        }
        producerDirectService.sendCreateCommentToPost(IPostMapper.INSTANCE.toCreateComment(dto));
        return true;
    }

    public Optional<User> findOptionalByUsername(String username){
        return userRepository.findOptionalByUsername(username);
    }


    /**storage
     *  baslangic
     */
//
//    public Optional<String> uploadFile(MultipartFile file){
//        try {
//            String mediaUrl;
//            if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
//                mediaUrl = UUID.randomUUID().toString()+".png";
//            }else
//                mediaUrl = UUID.randomUUID().toString()+".mp4";
//            BlobInfo blobInfo = BlobInfo.newBuilder(bucketname, mediaUrl).build();
//            storage.create(blobInfo, file.getBytes());
//            return Optional.of(mediaUrl);
//        }catch (Exception e){
//            return Optional.empty();
//        }
//    }
//
//
//
//    private BlobId constructBlobId(String bucketname, String subdirectory,
//                                   String filename) {
//        return Optional.ofNullable(subdirectory)
//                .map(s -> BlobId.of(bucketname, subdirectory + "/" + filename))
//                .orElse(BlobId.of(bucketname, filename));
//    }
//
//    public Optional<URL> getGoogleSignedMediaPath(String mediaName, int minutes) {
//        BlobId blobId = constructBlobId(bucketname, null, mediaName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//        return createSignedPathStyleUrl(blobInfo, minutes, TimeUnit.MINUTES);
//    }
//
//    private Optional<URL> createSignedPathStyleUrl(BlobInfo blobInfo,
//                                                   int duration, TimeUnit unit) {
//        return Optional.of(getStorage()
//                .signUrl(blobInfo,duration,unit,Storage.SignUrlOption.withPathStyle()));
//
//    }
//

    /**storage
     *  baslangic
     */


}
