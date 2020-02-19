package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.HomeNewProduct;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 新鲜好物表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface HomeNewProductService extends IService<HomeNewProduct> {

    PageInfoVo listNewProductForPage(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    void updateSort(Long id, Integer sort);
}
