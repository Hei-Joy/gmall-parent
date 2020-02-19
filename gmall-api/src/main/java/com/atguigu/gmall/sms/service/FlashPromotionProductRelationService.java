package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.FlashPromotionProductRelation;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品限时购与商品关系表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface FlashPromotionProductRelationService extends IService<FlashPromotionProductRelation> {

    /**
     * 分页查询不同场次关联及商品信息
     * @param flashPromotionId
     * @param flashPromotionSessionId
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo listRelationForPage(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum);
}
