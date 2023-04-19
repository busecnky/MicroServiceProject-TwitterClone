package com.pawer.service;

//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
import com.pawer.dto.request.CommentToPostDto;
import com.pawer.dto.request.FawPostRequestDto;
import com.pawer.dto.request.BaseRequestDto;
import com.pawer.dto.request.LikePostRequestDto;
import com.pawer.dto.response.CommentToPostResponse;
import com.pawer.dto.response.MyFawPostListResponseDto;
import com.pawer.dto.response.PostFindAllResponse;
import com.pawer.exception.EErrorType;
import com.pawer.exception.PostException;
import com.pawer.rabbitmq.messagemodel.ModelCreateCommentToPost;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelLikePost;
import com.pawer.repository.ICommentToPostRepository;
import com.pawer.repository.IPostRepository;
import com.pawer.repository.entity.CommentToPost;
import com.pawer.repository.entity.FawToPost;
import com.pawer.repository.entity.LikeToPost;
import com.pawer.repository.entity.Post;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService extends ServiceManagerImpl<Post,String> {

    private final IPostRepository postrepository;
    private final JwtTokenManager jwtTokenManager;
    private final ICommentToPostRepository commentToPostRepository;
    private final FawToPostService fawToPostService;
    private final LikeToPostService likeToPostService;

    //    @Value("${myproject.google.storage.bucketname}")
//    private String bucketname;
//    @Getter
//    @Autowired
//    Storage storage;



    int a=0;

    public PostService(IPostRepository postrepository, JwtTokenManager jwtTokenManager, ICommentToPostRepository commentToPostRepository, FawToPostService fawToPostService, LikeToPostService likeToPostService) {
        super(postrepository);
        this.postrepository = postrepository;
        this.jwtTokenManager = jwtTokenManager;
        this.commentToPostRepository = commentToPostRepository;
        this.fawToPostService = fawToPostService;
        this.likeToPostService = likeToPostService;
    }


    public void savePost(ModelCreatePost modelCreatePost){
        String resim_id = UUID.randomUUID().toString();
        Long id = jwtTokenManager.validToken(modelCreatePost.getToken()).get();
        Post post = Post.builder()
                .userId(id)
                .content(modelCreatePost.getContent())
                .name(modelCreatePost.getName())
                .username(modelCreatePost.getUsername())
                .likeCount(0)
              .build();
        save(post);

        /* MUHAMMETHOCA Storage denemeleri
        try{
            Optional<String> resimurl = uploadFile(modelCreatePost.getImage());
            if(resimurl.isPresent()){
                Post post = Post.builder()
                        .userId(id)
                        .content(modelCreatePost.getContent())
                        .name(modelCreatePost.getName())
                        .username(modelCreatePost.getUsername())
                        .url(resimurl.get()).build();
                save(post);
            }
        }catch (Exception ex){
            System.out.println("****************************************");
            //ekranaYildizBastirma(100);
            System.out.println("post kayit ederken hata oldu "+ex.getMessage());
        }
        */

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

        for(Post post: findAll()){
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
            postFindAllResponse.setIsFaw(fawToPostService.findFawToPostBoolean(post.getId(),userId));
            postFindAllResponse.setIsLiked(likeToPostService.findByPostIdAndUserIdBoolean(post.getId(), userId));
            postFindAllResponses.add(postFindAllResponse);
        }
        Page<PostFindAllResponse> myPage = new PageImpl<>(postFindAllResponses, pageable, postFindAllResponses.size());

        return myPage;
    }


    public Page<Post> findAllPlus(Integer pageSize, Integer currentPage, Sort.Direction direction, String sortingParameter){
        // for kontrolü ile sayfa sayısı kontrol edilecek.
        a++;
        Pageable pageable = PageRequest.of(a,  pageSize ,Sort.by(direction, sortingParameter) );
        return postrepository.findAll(pageable);
    }


    public Page<Post> findAllMinus(Integer pageSize, Integer currentPage, Sort.Direction direction, String sortingParameter){
        // for kontrolü ile sayfa sayısı kontrol edilecek.
        a--;
        Pageable pageable = PageRequest.of(a,  pageSize ,Sort.by(direction, sortingParameter) );
        return postrepository.findAll(pageable);
    }

    public  Page<Post> myPost(String token,
                              Integer pageSize,
                              int currentPage, Sort.Direction direction,
                              String sortingParameter){
        Long userid = jwtTokenManager.validToken(token).get();
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        return postrepository.findByUserId(userid,pageable);
    }
    public void createCommentToPost(ModelCreateCommentToPost model){
        CommentToPost commentToPost= new CommentToPost();
        Long userId = jwtTokenManager.validToken(model.getToken()).get();
        commentToPost.setComment(model.getComment());
        commentToPost.setUserId(userId);
        commentToPost.setPostId(model.getPostId());
        commentToPostRepository.save(commentToPost);
    }

    public List<CommentToPostResponse> findAllComment(CommentToPostDto dto){
        List<CommentToPostResponse> comments= new ArrayList<>();
        for (CommentToPost comment: commentToPostRepository.findByPostId(dto.getPostId()).get()){
            CommentToPostResponse commentToPostResponse=new CommentToPostResponse();
            commentToPostResponse.setComment(comment.getComment());
            commentToPostResponse.setPostId(comment.getPostId());
            commentToPostResponse.setUserId(String.valueOf(comment.getUserId()));
            comments.add(commentToPostResponse);
        }

        return comments;

    }
    public Boolean createFawPost(FawPostRequestDto dto) {
        Long userId= jwtTokenManager.validToken(dto.getToken()).get();
        Optional<FawToPost> fawToPost= fawToPostService.findFawToPost(dto.getPostId(), userId);
        if (fawToPost.isPresent()){
                fawToPost.get().setStatement(dto.getStatement());
                fawToPostService.save(fawToPost.get());
                return dto.getStatement();
        }else {
            fawToPostService.save(FawToPost.builder()
                            .userId(userId)
                            .postId(dto.getPostId())
                            .statement(dto.getStatement())
                    .build());
            return dto.getStatement();
        }
    }
    public void createLikePost(ModelLikePost model) {
        Long modelUserId = jwtTokenManager.validToken(model.getToken()).get();
        Optional<LikeToPost> likeToPost = likeToPostService.findByPostIdAndUserId(model.getPostId(), modelUserId);
        Optional<Post> post = findById(model.getPostId());

            if (likeToPost.isPresent()){
                if(model.getStatement() == true){
                        post.get().setLikeCount(post.get().getLikeCount()+1);
                        save(post.get());
                }else {
                    post.get().setLikeCount(post.get().getLikeCount()-1);
                    save(post.get());
                }
                    likeToPost.get().setStatement(model.getStatement());
                    likeToPostService.save(likeToPost.get());
            }else {
                likeToPostService.save(LikeToPost.builder()
                        .postId(model.getPostId())
                        .userId(modelUserId)
                        .statement(model.getStatement())
                        .build());
                post.get().setLikeCount(post.get().getLikeCount()+1);
                save(post.get());
            }
    }

    public Integer likePostCount(LikePostRequestDto dto) {
        if (dto.getToken() == null || dto.getToken() == "") {
            throw new PostException(EErrorType.INVALID_TOKEN);
        }
        Optional<Post> post = findById(dto.getPostId());
        return post.get().getLikeCount();
    }
    public List<Post> findAllMyLikesList(BaseRequestDto dto) {
        Long userId =  jwtTokenManager.validToken(dto.getToken()).get();
        List<Post> likePostList= new ArrayList<>();
        for (LikeToPost like : likeToPostService.findByUserIdList(userId).get()) {
            Post post = findById(like.getPostId()).get();
            likePostList.add(post);
        }
        return likePostList;
    }

    public List<MyFawPostListResponseDto> myFawPostList(BaseRequestDto dto){
        Long userId=jwtTokenManager.validToken(dto.getToken()).get();

        Optional<List<FawToPost>>  fawToPosts= fawToPostService.findAllFawToPost(userId);
        List<MyFawPostListResponseDto> myFawPostListResponseDtos =new ArrayList<>();
        System.out.println("userıd **** "+userId);

        System.out.println("list ******"+fawToPosts.get().toString());
        if (fawToPosts.get()!=null){
            System.out.println("\n\n\nvar ise girilen yer 243");
            for (FawToPost fawToPost: fawToPosts.get()){
                if (fawToPost.getStatement()==true){

                System.out.println("for ici hemen 245");
                MyFawPostListResponseDto myFawPostListResponseDto= new MyFawPostListResponseDto();
                System.out.println("new sonrası");
                System.out.println(fawToPost.getId());
                System.out.println(fawToPost.getPostId());
                Post postt = findById(fawToPost.getPostId()).get();
                System.out.println("246");
                myFawPostListResponseDto.setContent(postt.getContent());
                myFawPostListResponseDto.setIsFaw(fawToPost.getStatement());
                myFawPostListResponseDto.setName(postt.getName());
                myFawPostListResponseDto.setDate(postt.getDate());
                System.out.println("250");
                myFawPostListResponseDto.setTime(postt.getTime());
                myFawPostListResponseDto.setUrl(postt.getUrl());
                myFawPostListResponseDto.setSurname(postt.getSurname());
                myFawPostListResponseDto.setUsername(postt.getUsername());
                myFawPostListResponseDto.setId(postt.getId());
                myFawPostListResponseDto.setIsLiked(likeToPostService.findByPostIdAndUserIdBoolean(postt.getId(),userId));
                myFawPostListResponseDto.setLikeCount(postt.getLikeCount());
                myFawPostListResponseDto.setUserId(postt.getUserId());
                myFawPostListResponseDtos.add(myFawPostListResponseDto);
                }
            }
            System.out.println("for cikisi");
            System.out.println(myFawPostListResponseDtos.toString());
            return myFawPostListResponseDtos;
        }
        System.out.println("en alt return oncesi");
        return myFawPostListResponseDtos;
    }
/*
    public Boolean createFindLikePost(ModelFindLikePost model) {
        Long userId =  jwtTokenManager.validToken(model.getToken()).get();
        for (LikeToPost like : likeToPostRepository.findOptionalByUserId(userId).get()) {
            Post post = findById(like.getPostId()).get();
            likePostList.add(post);
        }
        return ;
    }

*/
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




//    public Page<Post>findAllByUserId(Integer pageSize, Integer currentPage,String direction,  String sortingParameter,String token){
//        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
//        List<Page<Post>> posts = Collections.singletonList(postrepository.findAll(pageable));
//        Long id = jwtTokenManager.validToken(token).get();
//        return postrepository.findAll(pageable);
//    }


}
