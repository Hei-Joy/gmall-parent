package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.CouponHistory;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 优惠券使用、领取历史表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface CouponHistoryService extends IService<CouponHistory> {

    /**
     * 分页获取优惠券领取记录
     * @param couponId
     * @param useStatus
     * @param orderSn
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo listCouponHistoryForPage(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum);
}
