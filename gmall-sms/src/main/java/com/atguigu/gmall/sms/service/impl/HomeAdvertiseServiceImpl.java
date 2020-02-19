package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.HomeAdvertise;
import com.atguigu.gmall.sms.mapper.HomeAdvertiseMapper;
import com.atguigu.gmall.sms.service.HomeAdvertiseService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 首页轮播广告表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
public class HomeAdvertiseServiceImpl extends ServiceImpl<HomeAdvertiseMapper, HomeAdvertise> implements HomeAdvertiseService {

    @Autowired
    HomeAdvertiseMapper homeAdvertiseMapper;

    @Override
    public int updateStatus(Long id, Integer status) {
        HomeAdvertise homeAdvertise = new HomeAdvertise();
        homeAdvertise.setId(id);
        homeAdvertise.setStatus(status);
        return homeAdvertiseMapper.updateById(homeAdvertise);
    }

    @Override
    public PageInfoVo listAdvertiseForPage(String name,
                                           Integer type,
                                           String endTime,
                                           Integer pageSize, Integer pageNum) {

        QueryWrapper<HomeAdvertise> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(endTime)){
            wrapper.lt("end_time",endTime);
        }
        if(type!=null){
            wrapper.eq("type",type);
        }

        IPage<HomeAdvertise> iPage = homeAdvertiseMapper.selectPage(new Page<HomeAdvertise>(pageNum, pageSize), wrapper);

        return PageInfoVo.getVo(iPage,pageSize.longValue());
    }
}
