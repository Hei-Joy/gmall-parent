package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.FlashPromotion;
import com.atguigu.gmall.sms.mapper.FlashPromotionMapper;
import com.atguigu.gmall.sms.service.FlashPromotionService;
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
 * 限时购表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class FlashPromotionServiceImpl extends ServiceImpl<FlashPromotionMapper, FlashPromotion> implements FlashPromotionService {

    @Autowired
    FlashPromotionMapper flashPromotionMapper;

    @Override
    public int createFlashPromotion(FlashPromotion flashPromotion) {
        flashPromotionMapper.insert(flashPromotion);
        return 1;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        FlashPromotion flashPromotion = new FlashPromotion();
        flashPromotion.setId(id);flashPromotion.setStatus(status);
        flashPromotionMapper.updateById(flashPromotion);
        return 1;
    }

    @Override
    public PageInfoVo listflashPromotionForPage(String keyword,
                                                Integer pageSize, Integer pageNum) {

        QueryWrapper<FlashPromotion> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)){
            wrapper.eq("title",keyword);
        }

        IPage<FlashPromotion> iPage = flashPromotionMapper.selectPage(new Page<FlashPromotion>(pageNum, pageSize),
                wrapper);

        return PageInfoVo.getVo(iPage,pageSize.longValue());
    }
}
