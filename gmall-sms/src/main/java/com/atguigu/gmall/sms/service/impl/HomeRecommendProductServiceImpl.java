package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.HomeRecommendProduct;
import com.atguigu.gmall.sms.mapper.HomeRecommendProductMapper;
import com.atguigu.gmall.sms.service.HomeRecommendProductService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class HomeRecommendProductServiceImpl extends ServiceImpl<HomeRecommendProductMapper, HomeRecommendProduct> implements HomeRecommendProductService {

    @Autowired
    HomeRecommendProductMapper homeRecommendProductMapper;

    @Override
    public void updateSort(Long id, Integer sort) {
        HomeRecommendProduct product = new HomeRecommendProduct();
        product.setId(id);product.setSort(sort);
        homeRecommendProductMapper.updateById(product);
    }

    @Override
    public void updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ids.forEach((id)->{
            HomeRecommendProduct product = new HomeRecommendProduct();
            product.setId(id);
            product.setRecommendStatus(recommendStatus);
            homeRecommendProductMapper.updateById(product);
        });
    }

    @Override
    public PageInfoVo listrecommendProductForPage(String productName,
                                                  Integer recommendStatus,
                                                  Integer pageSize, Integer pageNum) {
        QueryWrapper<HomeRecommendProduct> wrapper = new QueryWrapper<>();
        IPage<HomeRecommendProduct> iPage = homeRecommendProductMapper.selectPage(new Page<HomeRecommendProduct>(pageNum, pageSize), wrapper);
        return PageInfoVo.getVo(iPage,pageSize.longValue());
    }
}
