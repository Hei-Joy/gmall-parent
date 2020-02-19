package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.FlashPromotionSession;
import com.atguigu.gmall.sms.mapper.FlashPromotionSessionMapper;
import com.atguigu.gmall.sms.service.FlashPromotionSessionService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 限时购场次表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class FlashPromotionSessionServiceImpl extends ServiceImpl<FlashPromotionSessionMapper, FlashPromotionSession> implements FlashPromotionSessionService {

    @Autowired
    FlashPromotionSessionMapper flashPromotionSessionMapper;
    @Override
    public void updateStatus(Long id, Integer status) {
        FlashPromotionSession session = new FlashPromotionSession();
        session.setId(id);
        session.setStatus(status);

        flashPromotionSessionMapper.updateById(session);
    }

    @Override
    public PageInfoVo selectListForPage(Long flashPromotionId) {
        QueryWrapper<FlashPromotionSession> wrapper =
                new QueryWrapper<FlashPromotionSession>();
        return PageInfoVo.getVo(flashPromotionSessionMapper
                .selectPage(new Page<FlashPromotionSession>(1,100),wrapper),100L);
    }
}
