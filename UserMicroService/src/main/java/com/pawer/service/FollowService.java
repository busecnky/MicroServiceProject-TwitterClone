package com.pawer.service;

import com.pawer.dto.request.BaseRequestDto;
import com.pawer.repository.IFollowRepository;
import com.pawer.repository.entity.Follow;
import com.pawer.repository.entity.Follower;
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
public class FollowService extends ServiceManagerImpl<Follow, Long>  {
    private final IFollowRepository followRepository;
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;
    private final FollowerService followerService;

    public FollowService(IFollowRepository followRepository, @Lazy UserService userService, JwtTokenManager jwtTokenManager,@Lazy FollowerService followerService) {
        super(followRepository);
        this.followRepository = followRepository;
        this.userService = userService;
        this.jwtTokenManager = jwtTokenManager;
        this.followerService = followerService;
    }

    public void createFollowForNewUser(Long userId )  {
        int i = 0;
        System.out.println("follow service threadler kafasına göre calismadi sout ekleyince calisti o yüzden eklendi");
        while ( i <= userService.findAll().size()-1) {
            if ( userService.findAll().get(i).getId() == userId || userService.findAll().size()==0){
            }else  {
                Follow follow = new Follow(); // yeni eklenen kişi icin eski kisiler ile olan takiplesme
                Follow follow1= new Follow(); // eski kisiler icin yeni kisi ile olan takiplesme
                follow.setUserId(userId);
                follow.setFollowId(userService.findAll().get(i).getId());
                follow.setFollowRequest(0);
                follow1.setFollowId(userId);
                follow1.setUserId(userService.findAll().get(i).getId());
                follow1.setFollowRequest(0);
                save(follow);
                save(follow1);
            }
            i++;
        }
    }


    public Integer followUser(BaseRequestDto dto) {

        Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());
        Optional<User> followUser = userService.findOptionalByUsername(dto.getUsername());
        Optional<Follow> follow = followRepository.findOptionalByUserIdAndFollowId(userId.get(), followUser.get().getId());
        Optional<Follower> follower=followerService.findOptionalByUserIdAndFollowerId(follow.get().getFollowId(), follow.get().getUserId());

        if (follow.get().getFollowRequest() == 0) { // istek atma işlemi
            follow.get().setFollowRequest(1);
            follow.get().setUpdateDate(LocalDateTime.now().toString());
            update(follow.get());
            follower.get().setStatee(1); // bu ve bi altındaki islemi follower servis metodu cagirarak
            followerService.save(follower.get());// yapmak daha iyi olacak gibi
            return 1;
        } else if (follow.get().getFollowRequest() == 1) { // isteği geri çekme
            follow.get().setFollowRequest(0);
            update(follow.get());
            follower.get().setStatee(0);
            followerService.save(follower.get());
            return 0;
        } else if (follow.get().getFollowRequest() == 2) { // diger kullanıcı atılan isteği kabul edince etkinleşiyor
            follow.get().setFollowRequest(2);
            update(follow.get());
            return 0;
        } else {
            return 3;
        }
    }

    //ben karttaki kullanıcıyı takip ediyor muyum?
    public Optional<List<Follow>> isFollow(Long userid){
        List<User> users= userService.findAll();
        Optional<List<Follow>> follows= Optional.of(new ArrayList<>());
        for(User user:users){
            if (user.getId()!=userid){
                Follow follow = followRepository.findOptionalByUserIdAndFollowId(userid,user.getId()).get();
                follows.get().add(follow);
            }

        }
        return follows;
    }

    public Optional<Follow> findOptionalByUserIdAndFollowId(Long id, Long aLong) {

        return followRepository.findOptionalByUserIdAndFollowId(id,aLong);
    }

    public Optional<List<Long>> findOptionalFollowList(Long userId){
        Optional<List<Long>> followIdList= Optional.of(new ArrayList<>());

        for (Follow follow:followRepository.findOptionalByUserId(userId).get()){
            if (follow.getFollowRequest()==2){
                followIdList.get().add(follow.getFollowId());
            }
        }
        return followIdList;
    }
}
