package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.FlashPromotionLog;
import com.atguigu.gmall.sms.mapper.FlashPromotionLogMapper;
import com.atguigu.gmall.sms.service.FlashPromotionLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 限时购通知记录 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
@Component
public class FlashPromotionLogServiceImpl extends ServiceImpl<FlashPromotionLogMapper, FlashPromotionLog> implements FlashPromotionLogService {

}
