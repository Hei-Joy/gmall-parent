package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.FlashPromotionProductRelation;
import com.atguigu.gmall.sms.mapper.FlashPromotionProductRelationMapper;
import com.atguigu.gmall.sms.service.FlashPromotionProductRelationService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品限时购与商品关系表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class FlashPromotionProductRelationServiceImpl extends ServiceImpl<FlashPromotionProductRelationMapper, FlashPromotionProductRelation> implements FlashPromotionProductRelationService {

    @Autowired
    FlashPromotionProductRelationMapper promotionProductRelationMapper;

    @Override
    public PageInfoVo listRelationForPage(Long flashPromotionId,
                                          Long flashPromotionSessionId,
                                          Integer pageSize, Integer pageNum) {

        QueryWrapper<FlashPromotionProductRelation> wrapper = new QueryWrapper<>();
        if(flashPromotionId!=null){
            wrapper.eq("flash_promotion_id",flashPromotionId);
        }
        if(flashPromotionSessionId!=null){
            wrapper.eq("flash_promotion_session_id",flashPromotionSessionId);
        }
        IPage<FlashPromotionProductRelation> iPage = promotionProductRelationMapper
                .selectPage(new Page<FlashPromotionProductRelation>(pageNum, pageSize),
                        wrapper);
        return PageInfoVo.getVo(iPage,pageSize.longValue());
    }
}
