package com.pawer.service;

//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;

import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.exception.EErrorType;
import com.pawer.exception.PostException;
import com.pawer.rabbitmq.consumer.Consumers;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelFollowId;
import com.pawer.repository.ICommentToPostRepository;
import com.pawer.repository.IPostRepository;
import com.pawer.repository.entity.Post;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService extends ServiceManagerImpl<Post,String> {

    private final IPostRepository postrepository;
    private final JwtTokenManager jwtTokenManager;
    private final ICommentToPostRepository commentToPostRepository;
    private final FavToPostService favToPostService;
    private final LikeToPostService likeToPostService;
    private ModelFollowId model;

    public PostService(IPostRepository postrepository, JwtTokenManager jwtTokenManager, @Lazy ICommentToPostRepository commentToPostRepository
            , @Lazy FavToPostService favToPostService,@Lazy  LikeToPostService likeToPostService) {
        super(postrepository);
        this.postrepository = postrepository;
        this.jwtTokenManager = jwtTokenManager;
        this.commentToPostRepository = commentToPostRepository;
        this.favToPostService = favToPostService;
        this.likeToPostService = likeToPostService;
    }


    public void savePost(ModelCreatePost modelCreatePost){
        Long id = jwtTokenManager.validToken(modelCreatePost.getToken()).get();
        Post post = Post.builder()
                .userId(id)
                .content(modelCreatePost.getContent())
                .name(modelCreatePost.getName())
                .username(modelCreatePost.getUsername())
                .likeCount(0)
              .build();
        save(post);

    }


    public Page<PostFindAllResponse> findAllPosts(String token,
                                                  Integer pageSize,
                                                  int currentPage, Sort.Direction direction,
                                                  String sortingParameter){
        if (token == null || token == "") {
            throw new PostException(EErrorType.INVALID_TOKEN);
        }
        Long userId = jwtTokenManager.validToken(token).get();
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        List<PostFindAllResponse> postFindAllResponses = new ArrayList<>();

        for(Post post: homePagePosts(model)){
            PostFindAllResponse postFindAllResponse = new PostFindAllResponse();
            postFindAllResponse.setId(post.getId());
            postFindAllResponse.setUserId(post.getUserId());
            postFindAllResponse.setUsername(post.getUsername());
            postFindAllResponse.setName(post.getName());
            postFindAllResponse.setSurname(post.getSurname());
            postFindAllResponse.setContent(post.getContent());
            postFindAllResponse.setUrl(post.getUrl());
            postFindAllResponse.setLikeCount(post.getLikeCount());
            postFindAllResponse.setDate(post.getDate());
            postFindAllResponse.setTime(post.getTime());
            postFindAllResponse.setIsFav(favToPostService.findFavToPostBoolean(post.getId(),userId));
            postFindAllResponse.setIsLiked(likeToPostService.findByPostIdAndUserIdBoolean(post.getId(), userId));
            postFindAllResponses.add(postFindAllResponse);
        }
        Page<PostFindAllResponse> myPage = new PageImpl<>(postFindAllResponses, pageable, postFindAllResponses.size());

        return myPage;
    }


    public  Page<Post> myPost(String token,
                              Integer pageSize,
                              int currentPage, Sort.Direction direction,
                              String sortingParameter){
        Long userid = jwtTokenManager.validToken(token).get();
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        return postrepository.findByUserId(userid,pageable);
    }



    public List<Post> homePagePosts(ModelFollowId model){
        this.model=model;
        System.out.println("model ici gelen mesaj:... "+model.toString());
        List<Post> posts = new ArrayList<>();
        for (Long folloId: model.getFollodId()){
            Optional<List<Post>> posts1= postrepository.findOptionalByUserId(folloId);
            for (Post post : posts1.get()){
                posts.add(post);
            }
        }
        System.out.println("postss " + posts.toString());
        return posts;
    }

    public List<Post> discover(ModelFollowId model){
        this.model=model;
        System.out.println("model ici gelen mesaj:... "+model.toString());
        List<Post> posts = new ArrayList<>();
        for (Long folloId: model.getFollodId()){
            Optional<List<Post>> posts1= postrepository.findOptionalByUserId(folloId);
            for (Post post : posts1.get()){
                posts.add(post);
            }
        }
        List<Post> findallPosts= findAll();
        findallPosts.removeAll(posts);
        System.out.println("postss " + findallPosts.toString());
        return findallPosts;
    }


    public Page<PostFindAllResponse> discoverPage(String token,
                                                  Integer pageSize,
                                                  int currentPage, Sort.Direction direction,
                                                  String sortingParameter){
        if (token == null || token == "") {
            throw new PostException(EErrorType.INVALID_TOKEN);
        }
        Long userId = jwtTokenManager.validToken(token).get();
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        List<PostFindAllResponse> postFindAllResponses = new ArrayList<>();

        for(Post post: discover(model)){
            if (post.getUserId()!=userId){


            PostFindAllResponse postFindAllResponse = new PostFindAllResponse();
            postFindAllResponse.setId(post.getId());
            postFindAllResponse.setUserId(post.getUserId());
            postFindAllResponse.setUsername(post.getUsername());
            postFindAllResponse.setName(post.getName());
            postFindAllResponse.setSurname(post.getSurname());
            postFindAllResponse.setContent(post.getContent());
            postFindAllResponse.setUrl(post.getUrl());
            postFindAllResponse.setLikeCount(post.getLikeCount());
            postFindAllResponse.setDate(post.getDate());
            postFindAllResponse.setTime(post.getTime());
            postFindAllResponse.setIsFav(favToPostService.findFavToPostBoolean(post.getId(),userId));
            postFindAllResponse.setIsLiked(likeToPostService.findByPostIdAndUserIdBoolean(post.getId(), userId));
            postFindAllResponses.add(postFindAllResponse);
            }
        }
        Page<PostFindAllResponse> myPage = new PageImpl<>(postFindAllResponses, pageable, postFindAllResponses.size());

        return myPage;
    }

    /**storage
     *  baslangic


    public Optional<String> uploadFile(MultipartFile file){
        try {
            String mediaUrl;
            if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
                mediaUrl = UUID.randomUUID().toString()+".png";
            }else
                mediaUrl = UUID.randomUUID().toString()+".mp4";
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketname, mediaUrl).build();
            storage.create(blobInfo, file.getBytes());
            return Optional.of(mediaUrl);
        }catch (Exception e){
            return Optional.empty();
        }
    }



    private BlobId constructBlobId(String bucketname, String subdirectory,
                                   String filename) {
        return Optional.ofNullable(subdirectory)
                .map(s -> BlobId.of(bucketname, subdirectory + "/" + filename))
                .orElse(BlobId.of(bucketname, filename));
    }

    public Optional<URL> getGoogleSignedMediaPath(String mediaName, int minutes) {
        BlobId blobId = constructBlobId(bucketname, null, mediaName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        return createSignedPathStyleUrl(blobInfo, minutes, TimeUnit.MINUTES);
    }

    private Optional<URL> createSignedPathStyleUrl(BlobInfo blobInfo,
                                                   int duration, TimeUnit unit) {
        return Optional.of(getStorage()
                .signUrl(blobInfo,duration,unit, Storage.SignUrlOption.withPathStyle()));

    }



     * storage
     * baslangic
     */



    public String ekranaYildizBastirma(int adet){
        String [] yildiz= new String[adet];
        int i =0;
        while (i<adet){
            yildiz[i]="*";
            i++;
        }
        return yildiz.toString();
    }


}
