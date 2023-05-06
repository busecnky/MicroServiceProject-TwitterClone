package com.pawer.service;

import com.pawer.dto.request.AcceptFollowerRequestDto;
import com.pawer.dto.request.BaseRequestDto;
import com.pawer.dto.response.FindAllRequestsResponse;
import com.pawer.repository.IFollowerRepository;
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
public class FollowerService extends ServiceManagerImpl<Follower, Long> {
    private final IFollowerRepository followerRepository;
    private final FollowService followService;
    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;

    public FollowerService(IFollowerRepository followerRepository, @Lazy FollowService followService, JwtTokenManager jwtTokenManager, @Lazy UserService userService) {
        super(followerRepository);
        this.followerRepository = followerRepository;
        this.followService = followService;
        this.jwtTokenManager = jwtTokenManager;
        this.userService = userService;
    }

    public void createFollowerForNewUser(Long userId) {

        int i = 0;
        while (i <= userService.findAll().size() - 1) {

            if (userService.findAll().get(i).getId() == userId || userService.findAll().size() == 0) {

            } else {
                Follower follower = new Follower(); // istek atan
                Follower follower1 = new Follower(); //
                follower.setUserId(userService.findAll().get(i).getId());
                follower.setFollowerId(userId);
                follower.setStatee(0);
                follower1.setUserId(userId);
                follower1.setFollowerId(userService.findAll().get(i).getId());
                save(follower);
                save(follower1);
            }
            i++;
        }
    }

    public Integer acceptFollower(AcceptFollowerRequestDto dto) {
        Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());
        Optional<User> followerUser = userService.findOptionalByUsername(dto.getUsername());
        Optional<Follower> follower = followerRepository.findOptionalByUserIdAndFollowerId(userId.get(), followerUser.get().getId());
        Optional<Follow> follow = followService.findOptionalByUserIdAndFollowId(followerUser.get().getId(), userId.get());

        dto.setResponseForFollowRequest(true);
        if (follower.get().getStatee() == 0) {
            //followerRepository.save(follower.get()); //furkan dursun dedi ama buse silmek istedi
            return 0;
        } else if (follower.get().getStatee() == 1) { // gelen isteği kabul etmek & reddetmek
                follower.get().setStatee(2);
                update(follower.get());
                follow.get().setFollowRequest(2);
                followService.update(follow.get());
                return 2;


        } else if (follower.get().getStatee() == 2) { // beni takip edeni çıkartmak
            follower.get().setStatee(0);
            follower.get().setUpdateDate(LocalDateTime.now().toString());
            followerRepository.save(follower.get());

            follow.get().setFollowRequest(0);
            follow.get().setUpdateDate(LocalDateTime.now().toString());
            followService.save(follow.get());
            return 0;

        } else {
            return 3;
        }
    }
    public Integer rejectFollower(AcceptFollowerRequestDto dto) {

        Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());
        Optional<User> followerUser = userService.findOptionalByUsername(dto.getUsername());
        Optional<Follower> follower = followerRepository.findOptionalByUserIdAndFollowerId(userId.get(), followerUser.get().getId());
        Optional<Follow> follow = followService.findOptionalByUserIdAndFollowId(followerUser.get().getId(), userId.get());

        dto.setResponseForFollowRequest(false);
        if (follower.get().getStatee() == 0) {
            //followerRepository.save(follower.get()); //furkan dursun dedi ama buse silmek istedi
            return 0;
        } else if (follower.get().getStatee() == 1) { // gelen isteği kabul etmek & reddetmek

                follower.get().setStatee(0);
                update(follower.get());
                follow.get().setFollowRequest(0);
                followService.update(follow.get());
                return 0;


        } else if (follower.get().getStatee() == 2) { // beni takip edeni çıkartmak

            follower.get().setStatee(0);
            follower.get().setUpdateDate(LocalDateTime.now().toString());
            followerRepository.save(follower.get());

            follow.get().setFollowRequest(0);
            follow.get().setUpdateDate(LocalDateTime.now().toString());
            followService.save(follow.get());
            return 0;

        } else {
            return 3;
        }
    }
    public Optional<Follower> findOptionalByUserIdAndFollowerId(Long userId, Long followerId) {
        return followerRepository.findOptionalByUserIdAndFollowerId(userId, followerId);
    }
    public List<Follower> isFollower(Long userid){
        List<User> users= userService.findAll();
        List<Follower> Followers= new ArrayList<>();
        for(User user:users){
            if (user.getId()!=userid){
                Follower Follower = followerRepository.findOptionalByUserIdAndFollowerId(userid,user.getId()).get();
                Followers.add(Follower);
            }

        }
        return Followers;
    }

    public List<FindAllRequestsResponse> findAllRequests(BaseRequestDto dto) {
        Optional<Long> userId = jwtTokenManager.validToken(dto.getToken());
        List<Follower> followers= isFollower(userId.get());
        FindAllRequestsResponse findAllRequestsResponse;
        List<FindAllRequestsResponse> followersRequest= new ArrayList<>();

        for(Follower f:followers){
            if (f.getStatee()==1){
                User user = userService.findById((f.getFollowerId())).get();
                findAllRequestsResponse = new FindAllRequestsResponse();
                findAllRequestsResponse.setJob(user.getJob());
                findAllRequestsResponse.setAvatar(user.getAvatar());
                findAllRequestsResponse.setName(user.getName());
                findAllRequestsResponse.setSurname(user.getSurname());
                findAllRequestsResponse.setUsername(user.getUsername());
                findAllRequestsResponse.setFollowerId(f.getFollowerId());
                followersRequest.add(findAllRequestsResponse);
            }
        }
        return followersRequest;
    }

    public Integer findAllRequestsCount(BaseRequestDto dto) {
        return findAllRequests(dto).size();
    }
}

