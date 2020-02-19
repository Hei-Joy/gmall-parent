package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.Coupon;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.sms.SmsCouponParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 优惠卷表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface CouponService extends IService<Coupon> {

    /**
     * 创建优惠卷
     * @param couponParam
     * @return
     */
    int create(SmsCouponParam couponParam);

    /**
     * 修改优惠卷信息
     * @param id
     * @param couponParam
     * @return
     */
    int updateCouponInfos(Long id, SmsCouponParam couponParam);

    /**
     * 分页查询优惠券信息
     * @param name
     * @param type
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo listForPage(String name, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 查询单个优惠券信息
     * @param id
     * @return
     */
    SmsCouponParam getCouponItemInfo(Long id);
}
