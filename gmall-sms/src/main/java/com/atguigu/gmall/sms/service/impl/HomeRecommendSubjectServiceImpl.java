package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.HomeRecommendSubject;
import com.atguigu.gmall.sms.mapper.HomeRecommendSubjectMapper;
import com.atguigu.gmall.sms.service.HomeRecommendSubjectService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class HomeRecommendSubjectServiceImpl extends ServiceImpl<HomeRecommendSubjectMapper, HomeRecommendSubject> implements HomeRecommendSubjectService {

    @Autowired
    HomeRecommendSubjectMapper homeRecommendSubjectMapper;

    @Override
    public void updateSort(Long id, Integer sort) {
        HomeRecommendSubject subject = new HomeRecommendSubject();
        subject.setId(id);subject.setSort(sort);
        homeRecommendSubjectMapper.updateById(subject);
    }

    @Override
    public void updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ids.forEach((id)->{
            HomeRecommendSubject subject = new HomeRecommendSubject();
            subject.setId(id);subject.setRecommendStatus(recommendStatus);
            homeRecommendSubjectMapper.updateById(subject);
        });
    }

    @Override
    public PageInfoVo listForPage(String subjectName,
                                  Integer recommendStatus,
                                  Integer pageSize, Integer pageNum) {
        QueryWrapper<HomeRecommendSubject> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(subjectName)){
            wrapper.like("subject_name",subjectName);
        }
        if(recommendStatus!=null){
            wrapper.eq("recommend_status",recommendStatus);
        }


        IPage<HomeRecommendSubject> iPage = homeRecommendSubjectMapper.selectPage(new Page<HomeRecommendSubject>(pageNum, pageSize), wrapper);
        return  PageInfoVo.getVo(iPage,pageSize.longValue());
    }
}
