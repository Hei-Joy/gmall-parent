package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.entity.MemberPrice;
import com.atguigu.gmall.pms.mapper.MemberPriceMapper;
import com.atguigu.gmall.pms.service.MemberPriceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品会员价格表 服务实现类
 * </p>
 *
 * 1、将逆向工程的东西，先复制mapper进来。
 * 2、给当前项目中手动创建一个service包，再把impl包下的所有复制进来。
 *
 * @author Lfy
 * @since 2019-05-08
 */
@Service
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceMapper, MemberPrice> implements MemberPriceService {



}
