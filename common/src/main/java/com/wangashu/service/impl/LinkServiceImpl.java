package com.wangashu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangashu.constants.SystemCanstants;
import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.Link;
import com.wangashu.mapper.LinkMapper;
import com.wangashu.service.LinkService;
import com.wangashu.utils.BeanCopyUtils;
import com.wangashu.vo.LinkVo;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author     ：王阿书

 * @ DateTime       :2025/8/26 15:00

 * @ Description:

 * @program :

 */

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService  {



    @Override
    public ResponseResult getAllLinkList() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemCanstants.LINK_STATUS_PASS);
        List<Link> list = list(queryWrapper);

        //封装vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}
