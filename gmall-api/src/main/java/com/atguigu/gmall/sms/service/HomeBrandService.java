package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.HomeBrand;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页推荐品牌表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface HomeBrandService extends IService<HomeBrand> {

    int updateSort(Long id, Integer sort);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    PageInfoVo listBrandForPage(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
