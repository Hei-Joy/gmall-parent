package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.CouponProductRelation;
import com.atguigu.gmall.sms.mapper.CouponProductRelationMapper;
import com.atguigu.gmall.sms.service.CouponProductRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 优惠券和产品的关系表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class CouponProductRelationServiceImpl extends ServiceImpl<CouponProductRelationMapper, CouponProductRelation> implements CouponProductRelationService {

}
