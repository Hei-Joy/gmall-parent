package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.FlashPromotion;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 限时购表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface FlashPromotionService extends IService<FlashPromotion> {

    /**
     * 创建限时购活动
     * @param flashPromotion
     * @return
     */
    int createFlashPromotion(FlashPromotion flashPromotion);

    /**
     * 更新活动状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 分页查询
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo listflashPromotionForPage(String keyword, Integer pageSize, Integer pageNum);
}
