package com.atguigu.gmall.vo.sms;

import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.sms.entity.FlashPromotionProductRelation;
import lombok.Getter;
import lombok.Setter;

/**
 * 限时购及商品信息封装
 */
public class SmsFlashPromotionProduct extends FlashPromotionProductRelation {
    @Getter
    @Setter
    private Product product;
}
