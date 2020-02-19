package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.HomeRecommendProduct;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface HomeRecommendProductService extends IService<HomeRecommendProduct> {

    void updateSort(Long id, Integer sort);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    PageInfoVo listrecommendProductForPage(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
