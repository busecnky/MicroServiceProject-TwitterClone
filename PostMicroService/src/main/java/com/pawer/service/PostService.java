package com.pawer.service;

import com.pawer.mapper.IPostMapper;
import com.pawer.rabbitmq.messagemodel.ModelCreatePost;
import com.pawer.repository.IPostRepository;
import com.pawer.repository.entity.Post;
import com.pawer.utility.JwtTokenManager;
import com.pawer.utility.ServiceManagerImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PostService extends ServiceManagerImpl<Post,String> {

    private final IPostRepository postrepository;
    private final JwtTokenManager jwtTokenManager;
    int a=0;

    public PostService(IPostRepository postrepository, JwtTokenManager jwtTokenManager) {
        super(postrepository);
        this.postrepository = postrepository;
        this.jwtTokenManager = jwtTokenManager;
    }


    public void savePost(ModelCreatePost modelCreatePost){
        Post post = IPostMapper.INSTANCE.toPost(modelCreatePost);
        Long id = jwtTokenManager.validToken(modelCreatePost.getToken()).get();
        post.setUserId(id);
        save(post);
    }


    public Page<Post> findAll(Integer pageSize, Integer currentPage, Sort.Direction direction, String sortingParameter){
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

//    public PageRequest next() {
//        return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
//    }
//
//    public PageRequest previous() {
//        return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());
//    }



//    public Page<Post>findAllByUserId(Integer pageSize, Integer currentPage,String direction,  String sortingParameter,String token){
//        Pageable pageable = PageRequest.of(currentPage,  pageSize ,Sort.by(direction, sortingParameter) );
//        List<Page<Post>> posts = Collections.singletonList(postrepository.findAll(pageable));
//        Long id = jwtTokenManager.validToken(token).get();
//        return postrepository.findAll(pageable);
//    }

}
