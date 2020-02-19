package com.atguigu.gmall.vo.order;

import com.atguigu.gmall.cart.vo.CartItem;
import com.atguigu.gmall.sms.entity.Coupon;
import com.atguigu.gmall.ums.entity.MemberReceiveAddress;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderConfirmVo implements Serializable {

    List<CartItem> items;//购物项
    List<MemberReceiveAddress> addresses;//地址列表
    List<Coupon> coupons; //优惠卷
    //其他的支付、配送方式
    private String orderToken;//订单令牌，这个令牌保存好，下一步提交订单必须带上

    private BigDecimal productTotalPrice = new BigDecimal("0");//商品总额
    private BigDecimal totalPrice = new BigDecimal("0");//订单总额
    private Integer count = 0;//商品总数
    private BigDecimal couponPrice = new BigDecimal("0");//优惠卷减免
    private BigDecimal transPrice = new BigDecimal("10");//运费

}
