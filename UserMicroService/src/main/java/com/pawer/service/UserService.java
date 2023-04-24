package com.pawer.service;

//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;

import com.pawer.dto.request.*;
import com.pawer.dto.response.FindByIdResponseDto;
import com.pawer.dto.response.ProfileCartResponse;
import com.pawer.exception.EErrorType;
import com.pawer.exception.UserException;
import com.pawer.mapper.IPostMapper;
import com.pawer.mapper.IUserMapper;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelFollowId;
import com.pawer.rabbitmq.messagemodel.ModelUpdateUser;
import com.pawer.rabbitmq.messagemodel.ModelUserSave;
import com.pawer.rabbitmq.producer.ProducerDirectService;
import com.pawer.repository.IUserRepository;
import com.pawer.repository.entity.Follow;
import com.pawer.repository.entity.User;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (id.isEmpty()) throw new UserException(EErrorType.INVALID_TOKEN);
        User user = findById(id.get()).get();
        ModelCreatePost modelCreatePost = IPostMapper.INSTANCE.toCreatePost(user);
        modelCreatePost.setToken(dto.getToken());
        modelCreatePost.setContent(dto.getContent());
        //modelCreatePost.setImage(dto.getImage());
        //System.out.println(modelCreatePost.getImage());
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

    public FindByIdResponseDto findByIdFromToken(BaseRequestDto dto) {
        Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());
        if (userId.isEmpty()) {
            throw new UserException(EErrorType.INVALID_TOKEN);
        }
        Optional<User> user = findById(userId.get());

        return IUserMapper.INSTANCE.toFindByIdResponseDto(user.get());
    }





    public Optional<User> findOptionalByUsername(String username){
        return userRepository.findOptionalByUsername(username);
    }



    public List<ProfileCartResponse> isFollow(BaseRequestDto dto){
        Long userId= jwtTokenManager.validToken(dto.getToken()).get();
        Optional<List<Follow>> follows= followService.isFollow(userId);         // beni 0 takip etmiyor, 1 bana takip isteği atmış,2 beni takip ediyor
        List<ProfileCartResponse>  profileCartResponses = new ArrayList<>();
        ProfileCartResponse profileCartResponse = new ProfileCartResponse();
        int i=0;
        if (follows.isPresent()){
        for (Follow f: follows.get()){
            profileCartResponse= new ProfileCartResponse();
            User user = findById(f.getFollowId()).get();
            profileCartResponse.setJob(user.getJob());
            profileCartResponse.setAvatar(user.getAvatar());
            profileCartResponse.setName(user.getName());
            profileCartResponse.setSurname(user.getSurname());
            profileCartResponse.setUsername(user.getUsername());
            profileCartResponse.setPostCount(0);
            //ben karttaki kullanıcıyı takip ediyor muyum?
            profileCartResponse.setFollow(f.getFollowRequest()==0 ? "Takip Et" : (f.getFollowRequest()==1 ? "İstek Gönderildi" : "Takiptesin") );
            profileCartResponses.add(profileCartResponse);

        }

          //  profileCartResponse.setFollower(follower.getStatee()==0 ? "Takip Etmiyor":(follower.getStatee()==1 ? "Kabul Et" : "Cikart"));

        for (ProfileCartResponse responses : profileCartResponses){
            if (i<followerService.isFollower(userId).size()){
                responses.setFollower(followerService.isFollower(userId).get(i).getStatee()==0 ? "Takip Etmiyor":(followerService.isFollower(userId).get(i).getStatee()==1 ? "Cevap Bekleniyor" : "Cikart"));
            i++;
            }
        }

        }
        return profileCartResponses;
    }
    public FindByIdResponseDto findMe(BaseRequestDto dto){
        Optional<Long> userId= jwtTokenManager.validToken(dto.getToken());
        User user=findById(userId.get()).get();
        FindByIdResponseDto findByIdResponseDto= new FindByIdResponseDto();
        findByIdResponseDto.setName(user.getName());
        findByIdResponseDto.setSurname(user.getSurname());
        findByIdResponseDto.setUsername(user.getUsername());
        Optional<List<Long>> follows = followService.findOptionalFollowList(userId.get());
        ModelFollowId model = new ModelFollowId();
        model.setFollodId(follows.get());
        System.out.println("follow ıd'leri getir... "+model.toString());
        producerDirectService.sendFollodId(model);
        return findByIdResponseDto;
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
