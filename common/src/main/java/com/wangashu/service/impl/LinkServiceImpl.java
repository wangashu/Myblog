package com.wangashu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangashu.entity.Link;
import com.wangashu.mapper.LinkMapper;
import com.wangashu.service.LinkService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2025-08-26 14:23:22
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService  {

}
