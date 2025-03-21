package com.solution.app.justpay4u.Api.Shopping.Request;

/**
 * Created by Vishnu Agarwal on 21,December,2019
 */
public class SearchKeywordRequest {
    String websiteId;
    String searchKeyword;
    int startIndex;
    int endIndex;
    String loginId;

    public SearchKeywordRequest(String websiteId, String searchKeyword, int startIndex, int endIndex, String loginId) {
        this.websiteId = websiteId;
        this.searchKeyword = searchKeyword;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.loginId = loginId;
    }
}
