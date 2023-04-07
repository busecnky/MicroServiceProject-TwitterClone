package com.pawer.service;

import com.pawer.dto.request.AcceptFollowerRequestDto;
import com.pawer.repository.IFollowerRepository;
import com.pawer.repository.entity.Follow;
import com.pawer.repository.entity.Follower;
import com.pawer.repository.entity.User;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowerService extends ServiceManagerImpl<Follower, Long>  {
    private final IFollowerRepository followerRepository;
    private final FollowService followService;
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;

    public FollowerService(IFollowerRepository followerRepository,@Lazy FollowService followService, JwtTokenManager jwtTokenManager, @Lazy UserService userService) {
        super(followerRepository);
        this.followerRepository = followerRepository;
        this.followService = followService;
        this.jwtTokenManager = jwtTokenManager;
        this.userService = userService;
    }

    public void createFollowerForNewUser(Long userId)  {
        /**
         * user service.findall metodları patlatıyor
         * yoruma alınan kısımlar acilacak yanindakiler silinecek
         */
        int i = 1;
        while (i<5   /*i <= userService.findAll().size()*/) {

            if (i<5  /*userService.findAll().get(i).getId() != userId*/ ) {
                Follower follower = new Follower();
                follower.setUserId(userId);
                follower.setFollowerId(2L  /*userService.findAll().get(i).getId()*/);
                follower.setStatee(0);
                follower.setCreateDate(System.currentTimeMillis() / 10000);
                follower.setUpdateDate(System.currentTimeMillis() / 10000);
                save(follower);
            }
            i++;
        }


    }

        public Integer acceptFollower(AcceptFollowerRequestDto dto) {

            Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());

            Optional<User> followerUser = userService.findOptionalByUsername(dto.getUsername());

            Optional<Follower> follower = followerRepository.findOptionalByUserIdAndFollowerId(userId.get(), followerUser.get().getId());

            Optional<Follow> follow = followService.findOptionalByUserIdAndFollowId(followerUser.get().getId(), userId.get());
            if (follower.get().getStatee() == 0) {
                //followerRepository.save(follower.get()); //furkan dursun dedi ama buse silmek istedi
                return 0;
            } else if (follower.get().getStatee() == 1) {
                follower.get().setStatee(2);
                followerRepository.save(follower.get());
                follow.get().setFollowRequest(2);
                followService.save(follow.get());
                return 2;
            } else if (follower.get().getStatee() == 2) {
                follower.get().setStatee(0);
                followerRepository.save(follower.get());
                follow.get().setFollowRequest(0);
                followService.save(follow.get());
                return 0;
            } else {
                return 3;
            }
        }

    }

