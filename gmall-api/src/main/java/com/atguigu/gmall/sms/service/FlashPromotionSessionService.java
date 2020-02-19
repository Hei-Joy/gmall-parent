package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.FlashPromotionSession;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 限时购场次表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface FlashPromotionSessionService extends IService<FlashPromotionSession> {

    void updateStatus(Long id, Integer status);

    PageInfoVo selectListForPage(Long flashPromotionId);
}
