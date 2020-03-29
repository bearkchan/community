package com.bk.community.service;

import com.bk.community.mapper.PublishMapper;
import com.bk.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bear
 */
@Service
public class PublishService {

    @Autowired
    private PublishMapper publishMapper;

    public void create(Question question) {
        publishMapper.create(question);
    }

}
