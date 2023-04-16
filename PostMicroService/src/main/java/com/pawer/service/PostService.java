package com.pawer.service;

//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
import com.pawer.dto.request.CommentToPostDto;
import com.pawer.dto.request.FindAllLikePostRequestDto;
import com.pawer.dto.request.LikePostRequestDto;
import com.pawer.dto.response.CommentToPostResponse;
import com.pawer.exception.EErrorType;
import com.pawer.exception.PostException;
import com.pawer.rabbitmq.messagemodel.ModelCreateCommentToPost;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.rabbitmq.messagemodel.ModelLikePost;
import com.pawer.repository.ICommentToPostRepository;
import com.pawer.repository.ILikePostRepository;
import com.pawer.repository.IPostRepository;
import com.pawer.repository.entity.CommentToPost;
import com.pawer.repository.entity.LikeToPost;
import com.pawer.repository.entity.Post;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ILikePostRepository likeToPostRepository;
    @Value("${myproject.google.storage.bucketname}")
    private String bucketname;

//    @Getter
//    @Autowired
//    Storage storage;



    int a=0;

    public PostService(IPostRepository postrepository, JwtTokenManager jwtTokenManager, ICommentToPostRepository commentToPostRepository, ILikePostRepository likeToPostRepository) {
        super(postrepository);
        this.postrepository = postrepository;
        this.jwtTokenManager = jwtTokenManager;
        this.commentToPostRepository = commentToPostRepository;
        this.likeToPostRepository = likeToPostRepository;
    }


    public void savePost(ModelCreatePost modelCreatePost){
        String resim_id = UUID.randomUUID().toString();
        Long id = jwtTokenManager.validToken(modelCreatePost.getToken()).get();
        Post post = Post.builder()
                .userId(id)
                .content(modelCreatePost.getContent())
                .name(modelCreatePost.getName())
                .username(modelCreatePost.getUsername())
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


    public Page<Post> findAll(Integer pageSize, int currentPage, Sort.Direction direction, String sortingParameter){
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        return postrepository.findAll(pageable);
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

    public  Page<Post> findByToken(String token,
                                             Integer pageSize,
                                             int currentPage, Sort.Direction direction,
                                             String sortingParameter){
        Long userid = jwtTokenManager.validToken(token).get();
        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
        return postrepository.findByUserId(userid,pageable);
    }
    public void createCommentToPost(ModelCreateCommentToPost model){
        System.out.println("post servisi create comment");
        CommentToPost commentToPost= new CommentToPost();
        Long userId = jwtTokenManager.validToken(model.getToken()).get();
        commentToPost.setComment(model.getComment());
        commentToPost.setUserId(userId);
        commentToPost.setPostId(model.getPostId());
        commentToPost.setState(true);
        commentToPostRepository.save(commentToPost);
    }

    public List<CommentToPostResponse> findAllComment(CommentToPostDto dto){
        List<CommentToPostResponse> comments= new ArrayList<>();
        for (CommentToPost comment: commentToPostRepository.findByPostId(dto.getPostId()).get()){
            CommentToPostResponse commentToPostResponse = new CommentToPostResponse();
            commentToPostResponse.setComment(comment.getComment());
            commentToPostResponse.setPostId(comment.getPostId());
            comments.add(commentToPostResponse);
        }

        return comments;

    }

    public void createLikePost(ModelLikePost model) {
        Long modelUserId = jwtTokenManager.validToken(model.getToken()).get();
        Optional<LikeToPost> likeToPost = likeToPostRepository.findOptionalByPostIdAndUserId(model.getPostId(), modelUserId);

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
                likeToPostRepository.save(likeToPost.get());

            }else {
                likeToPostRepository.save(LikeToPost.builder()
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
    public List<Post> findAllMyLikesList(FindAllLikePostRequestDto dto) {
        Long userId =  jwtTokenManager.validToken(dto.getToken()).get();
        List<Post> likePostList= new ArrayList<>();
        for (LikeToPost like : likeToPostRepository.findOptionalByUserId(userId).get()) {
            Post post = findById(like.getPostId()).get();
            likePostList.add(post);
        }
        return likePostList;
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




//    public Page<Post>findAllByUserId(Integer pageSize, Integer currentPage,String direction,  String sortingParameter,String token){
//        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
//        List<Page<Post>> posts = Collections.singletonList(postrepository.findAll(pageable));
//        Long id = jwtTokenManager.validToken(token).get();
//        return postrepository.findAll(pageable);
//    }


}
