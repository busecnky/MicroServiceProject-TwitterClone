package com.pawer.service;

import com.pawer.dto.request.FollowingUserRequestDto;
import com.pawer.repository.IFollowRepository;
import com.pawer.repository.entity.Follow;
import com.pawer.repository.entity.Follower;
import com.pawer.repository.entity.User;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
        /**
         * user service.findall metodları patlatıyor
         * yoruma alınan kısımlar acilacak yanindakiler silinecek
         *
         * !! burada null olarak olustur ve tusa bastıgında tabloyu güncelle
         */

        int i = 0;
        while (   i <= userService.findAll().size()-1) {
            int time = LocalDateTime.now().getHour();
            int time2 = LocalDateTime.now().getMinute();
            int time3 = LocalDateTime.now().getDayOfMonth();
            int time4 = LocalDateTime.now().getMonthValue();
            int time5= LocalDateTime.now().getSecond();
            int time6= LocalDateTime.now().getYear();
            String tm= LocalDateTime.now().toString();

            System.out.println("diretk get : "+tm);

            System.out.println("gethour: "+time);
            System.out.println("getminute: "+time2);
            System.out.println("getdayofmount: "+time3);
            System.out.println("getmonthvalue: "+time4);
            System.out.println("getdayofyear: "+time5);
            System.out.println("getyear: "+time6);
            String date= String.valueOf(time3+"."+time4+"."+time6 +"/"+time+":"+time2+":"+time5);
            System.out.println("string datesi: "+date);
            if ( userService.findAll().get(i).getId() == userId || userService.findAll().size()==0){}else  {
                Follow follow = new Follow();
                follow.setUserId(userId);
                follow.setFollowId(/*2L */  userService.findAll().get(i).getId());
                follow.setFollowRequest(0);
                follow.setCreateDate(System.currentTimeMillis() / 1000*60*15);
                follow.setUpdateDate(System.currentTimeMillis() / 1000);
                save(follow);
            }
            i++;
        }
    }


    public Integer followUser(FollowingUserRequestDto dto) {
        Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());

        Optional<User> followUser = userService.findOptionalByUsername(dto.getUsername());

        Optional<Follow> follow = followRepository.findOptionalByUserIdAndFollowId(userId.get(), followUser.get().getId());
        System.out.println("*/*/*/*/*/*/*/" + follow.get().getFollowRequest());

        if (follow.get().getFollowRequest() == 0) {
            follow.get().setFollowRequest(1);
            followRepository.save(follow.get());
            followerService.save(Follower.builder()
                    .followerId(userId.get())
                    .userId(follow.get().getFollowId())
                    .statee(1)
                    .build());
            return 1;
        } else if (follow.get().getFollowRequest() == 1) { //bu istek ve isteği geri çekme
            //follower statee i 0 a çekilecek. (follower eklerken dikkatli eklenecek)
            follow.get().setFollowRequest(0);
            followRepository.save(follow.get());
            return 0;
        } else if (follow.get().getFollowRequest() == 2) {
            follow.get().setFollowRequest(2);
            followRepository.save(follow.get());
            return 0;
        } else {
            return 3;
        }
    }

    public Optional<Follow> findOptionalByUserIdAndFollowId(Long id, Long aLong) {
        return findOptionalByUserIdAndFollowId(id,aLong);
    }
}
