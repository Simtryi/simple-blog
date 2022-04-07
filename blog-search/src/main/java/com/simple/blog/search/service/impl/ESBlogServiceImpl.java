package com.simple.blog.search.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.simple.blog.search.entity.ESBlog;
import com.simple.blog.search.mapper.ESBlogMapper;
import com.simple.blog.search.repository.ESBlogRepository;
import com.simple.blog.search.service.ESBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * ES 博客 Service 实现类
 */
@Service
public class ESBlogServiceImpl implements ESBlogService {

    @Autowired
    private ESBlogMapper esBlogMapper;

    @Autowired
    private ESBlogRepository esBlogRepository;

    @Override
    public int create() {
        List<ESBlog> esBlogList = esBlogMapper.selectAll();
        Iterable<ESBlog> iterable = esBlogRepository.saveAll(esBlogList);
        Iterator<ESBlog> iterator = iterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }

    @Override
    public void delete(Long id) {
        esBlogRepository.deleteById(id);
    }

    @Override
    public void delete(List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            List<ESBlog> esBlogList = new ArrayList<>();
            ids.forEach(id -> {
                ESBlog esBlog = new ESBlog();
                esBlog.setId(id);
                esBlogList.add(esBlog);
            });
            esBlogRepository.deleteAll(esBlogList);
        }
    }

    @Override
    public Page<ESBlog> search(String title, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esBlogRepository.findByTitle(title, pageable);
    }

    @Override
    public Optional<ESBlog> findById(Long id) {
        return esBlogRepository.findById(id);
    }
}
