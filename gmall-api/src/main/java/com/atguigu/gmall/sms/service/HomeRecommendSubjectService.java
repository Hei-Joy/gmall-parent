package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.HomeRecommendSubject;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-05-08
 */
public interface HomeRecommendSubjectService extends IService<HomeRecommendSubject> {

    void updateSort(Long id, Integer sort);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    PageInfoVo listForPage(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
