package com.solution.app.justpay4u.Api.Shopping.Request;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 16,December,2019
 */
public class AllProductListRequest {
    ArrayList<String> FilterOptionTypeIdList = new ArrayList<>();
    String LoginId;
    int StartIndex, PageLimitIndex;
    String OrderBy, OrderByType, WebsiteId, FilterType, KeywordId;
    int FilterTypeId;

    public AllProductListRequest(ArrayList<String> filterOptionTypeIdList, int startIndex, int pageLimitIndex, String orderBy, String orderByType, String websiteId, String filterType, int filterTypeId, String keywordId, String LoginId) {
        this.FilterOptionTypeIdList = filterOptionTypeIdList;
        this.StartIndex = startIndex;
        this.PageLimitIndex = pageLimitIndex;
        this.OrderBy = orderBy;
        this.OrderByType = orderByType;
        this.WebsiteId = websiteId;
        this.FilterType = filterType;
        this.FilterTypeId = filterTypeId;
        this.KeywordId = keywordId;
        this.LoginId = LoginId;
    }
}
