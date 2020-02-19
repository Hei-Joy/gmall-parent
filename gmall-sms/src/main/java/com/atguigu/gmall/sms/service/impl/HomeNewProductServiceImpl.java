package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.HomeNewProduct;
import com.atguigu.gmall.sms.mapper.HomeNewProductMapper;
import com.atguigu.gmall.sms.service.HomeNewProductService;
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
 * 新鲜好物表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class HomeNewProductServiceImpl extends ServiceImpl<HomeNewProductMapper, HomeNewProduct> implements HomeNewProductService {

    @Autowired
    HomeNewProductMapper homeNewProductMapper;

    @Override
    public PageInfoVo listNewProductForPage(String productName,
                                            Integer recommendStatus,
                                            Integer pageSize, Integer pageNum) {

        QueryWrapper<HomeNewProduct> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(productName)){
            wrapper.like("product_name",productName);
        }
        if(!StringUtils.isEmpty(productName)){
            wrapper.eq("recommend_status",recommendStatus);
        }

        IPage<HomeNewProduct> iPage = homeNewProductMapper.selectPage(new Page<HomeNewProduct>(pageNum, pageSize), wrapper);
        return PageInfoVo.getVo(iPage,pageSize.longValue());
    }

    @Override
    public void updateRecommendStatus(List<Long> ids, Integer recommendStatus) {

        ids.forEach((id)->{
            HomeNewProduct product = new HomeNewProduct();
            product.setId(id);
            product.setRecommendStatus(recommendStatus);
            homeNewProductMapper.updateById(product);
        });
    }

    @Override
    public void updateSort(Long id, Integer sort) {
        HomeNewProduct product = new HomeNewProduct();
        product.setId(id);
        product.setSort(sort);
        homeNewProductMapper.updateById(product);
    }
}
