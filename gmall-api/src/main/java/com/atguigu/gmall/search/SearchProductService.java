package com.atguigu.gmall.search;

import com.atguigu.gmall.vo.search.SearchParam;
import com.atguigu.gmall.vo.search.SearchResponse;

/**
 * 商品检索服务
 */
public interface SearchProductService {
    SearchResponse searchProduct(SearchParam searchParam);
}
