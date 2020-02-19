package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.CouponHistory;
import com.atguigu.gmall.sms.mapper.CouponHistoryMapper;
import com.atguigu.gmall.sms.service.CouponHistoryService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 优惠券使用、领取历史表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class CouponHistoryServiceImpl extends ServiceImpl<CouponHistoryMapper, CouponHistory> implements CouponHistoryService {

    @Reference
    CouponHistoryMapper couponHistoryMapper;

    @Override
    public PageInfoVo listCouponHistoryForPage(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum) {
        QueryWrapper<CouponHistory> wrapper = new QueryWrapper<>();
        if(couponId!=null){
            wrapper.eq("coupon_id",couponId);
        }
        if(useStatus!=null){
            wrapper.eq("use_status",useStatus);
        }
        if(!StringUtils.isEmpty(orderSn)){
            wrapper.eq("order_sn",orderSn);
        }
        IPage<CouponHistory> iPage = couponHistoryMapper.selectPage(new Page<CouponHistory>(pageNum, pageSize), wrapper);
        return PageInfoVo.getVo(iPage,pageSize.longValue());
    }
}
