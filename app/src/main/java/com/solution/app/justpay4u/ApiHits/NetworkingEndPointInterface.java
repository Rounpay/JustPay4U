package com.solution.app.justpay4u.ApiHits;


import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Networking.Request.ActivateUserRequest;
import com.solution.app.justpay4u.Api.Networking.Request.AddMemberRequest;
import com.solution.app.justpay4u.Api.Networking.Request.BankTransferRequest;
import com.solution.app.justpay4u.Api.Networking.Request.CouponRedeemRequest;
import com.solution.app.justpay4u.Api.Networking.Request.FindUserDetailsByIdRequest;
import com.solution.app.justpay4u.Api.Networking.Request.GetIncomeWiseReportRequest;
import com.solution.app.justpay4u.Api.Networking.Request.GetTopupDetailsByUserIdRequest;
import com.solution.app.justpay4u.Api.Networking.Request.GetUserNameRequest;
import com.solution.app.justpay4u.Api.Networking.Request.PoolUpLineAppRequest;
import com.solution.app.justpay4u.Api.Networking.Request.TopupUserRequest;
import com.solution.app.justpay4u.Api.Networking.Request.WalletToWalletRequest;
import com.solution.app.justpay4u.Api.Networking.Response.CouponTypeCountResponse;
import com.solution.app.justpay4u.Api.Networking.Response.EPinListResponse;
import com.solution.app.justpay4u.Api.Networking.Response.FindUserDetailsByIdResponse;
import com.solution.app.justpay4u.Api.Networking.Response.GetIncomeWiseReportResponse;
import com.solution.app.justpay4u.Api.Networking.Response.GetPoolUpLineResponse;
import com.solution.app.justpay4u.Api.Networking.Response.ListCouponResponse;
import com.solution.app.justpay4u.Api.Networking.Response.NetworkingDashboardResponse;
import com.solution.app.justpay4u.Api.Networking.Response.PackagePlanResponse;
import com.solution.app.justpay4u.Api.Networking.Response.PoolTargetResponse;
import com.solution.app.justpay4u.Api.Networking.Response.PoolUpLineLevelDetailsAppResponse;
import com.solution.app.justpay4u.Api.Networking.Response.UserGlobalPoolTargetResponse;
import com.solution.app.justpay4u.Networking.Activity.CryptoWithdrawalRequest;
import com.solution.app.justpay4u.Networking.Activity.WalletCryptoReportRequest;
import com.solution.app.justpay4u.Networking.Activity.WalletCryptoReportResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Vishnu on 20-01-2017.
 */

public interface NetworkingEndPointInterface {


    @Headers("Content-Type: application/json")
    @POST("App/AddMember")
    Call<BasicResponse> AddMember(@Body AddMemberRequest upiPaymentReq);

    @Headers("Content-Type: application/json")
    @POST("App/AppBindPackageMaster")
    Call<PackagePlanResponse> AppBindPackageMaster(@Body BasicRequest req);

    @Headers("Content-Type: application/json")
    @POST("App/AppGetUserNameById")
    Call<PackagePlanResponse> AppGetUserNameById(@Body GetUserNameRequest req);

    @Headers("Content-Type: application/json")
    @POST("App/TopupUserApp")
    Call<PackagePlanResponse> TopupUserApp(@Body TopupUserRequest req);


    @Headers("Content-Type: application/json")
    @POST("App/CouponListForApp")
    Call<ListCouponResponse> CouponListForApp(@Body BasicRequest req);

    @Headers("Content-Type: application/json")
    @POST("App/GetCouponCount")
    Call<CouponTypeCountResponse> GetCouponCount(@Body BasicRequest req);


    @Headers("Content-Type: application/json")
    @POST("App/CouponRequestByUserApp")
    Call<ListCouponResponse> CouponRequestByUserApp(@Body CouponRedeemRequest req);

    @Headers("Content-Type: application/json")
    @POST("App/ConvertCouponForApp")
    Call<ListCouponResponse> ConvertCouponForApp(@Body CouponRedeemRequest req);


    @Headers("Content-Type: application/json")
    @POST("Dashboard/BindNetworkDashboard")
    Call<NetworkingDashboardResponse> DownlineData(@Body BasicRequest req);

    @Headers("Content-Type: application/json")
    @POST("App/PoolUplineUserApp")
    Call<GetPoolUpLineResponse>GetPoolUpLineUserApp(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/PoolUplineLevelDetailsApp")
    Call<PoolUpLineLevelDetailsAppResponse>PoolUpLineLevelDetailsApp(@Body PoolUpLineAppRequest request);


    @Headers("Content-Type: application/json")
    @POST("FindUserDetailsById")
    Call<FindUserDetailsByIdResponse> FindUserDetailsById(@Body FindUserDetailsByIdRequest request);

    @Headers("Content-Type: application/json")
    @POST("WalletToWalletTransfer")
    Call<FindUserDetailsByIdResponse> WalletToWalletTransfer(@Body WalletToWalletRequest request);

    @Headers("Content-Type: application/json")
    @POST("GetOPIDListByUserId")
    Call<FindUserDetailsByIdResponse>GetTypeListByUserId(@Body FindUserDetailsByIdRequest request);

//    @Headers("Content-Type: application/json")
//    @POST("GetTopupDetailsByUserID")
//    Call<FindUserDetailsByIdResponse> GetTopupDetailsByUserID(@Body FindUserDetailsByIdRequest request);
    @Headers("Content-Type: application/json")
    @POST("GetTopupDetailsByUserID")
    Call<FindUserDetailsByIdResponse> GetTopupDetailsByUserID(@Body GetTopupDetailsByUserIdRequest request);
    @Headers("Content-Type: application/json")
    @POST("GetOPIDListByUserId")
    Call<FindUserDetailsByIdResponse> GetOPIDListByUserId(@Body FindUserDetailsByIdRequest request);

    @Headers("Content-Type: application/json")
    @POST("ActivateUserByApp")
    Call<BasicResponse> ActivateUser(@Body ActivateUserRequest request);

    @Headers("Content-Type: application/json")
    @POST("GetEPinList")
    Call<EPinListResponse> GetEPinList(@Body FindUserDetailsByIdRequest request);

    @Headers("Content-Type: application/json")
    @POST("Dashboard/DirectMemberList")
    Call<MemberListResponse> DirectMemberList(@Body MemberListRequest request);

    @Headers("Content-Type: application/json")
    @POST("Dashboard/SponserList")
    Call<MemberListResponse> SponserList(@Body MemberListRequest request);

    @Headers("Content-Type: application/json")
    @POST("Dashboard/BindLevel")
    Call<MemberListResponse> getLevel(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("Dashboard/TotalTeam")
    Call<MemberListResponse> TotalTeam(@Body MemberListRequest request);

    @Headers("Content-Type: application/json")
    @POST("CryptoWithDrawal")
    Call<BasicResponse> CryptoWithDrawal(@Body CryptoWithdrawalRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/WithdrawalWalletReport")
    Call<WalletCryptoReportResponse> WithdrawalWalletReport(@Body WalletCryptoReportRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/BankTranferServiceMLM")
    Call<BasicResponse> BankTranferServiceMLM(@Body BankTransferRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetIncomeWiseReport")
    Call<GetIncomeWiseReportResponse> GetIncomeWiseReport(@Body GetIncomeWiseReportRequest request);

    @Headers("Content-Type: application/json")
    @POST("Dashboard/BinaryBusiness")
    Call<MemberListResponse> BinaryBusiness(@Body MemberListRequest request);

    @Headers("Content-Type: application/json")
    @POST("Dashboard/DirectBusiness")
    Call<MemberListResponse> DirectBusiness(@Body MemberListRequest request);

    @Headers("Content-Type: application/json")
    @POST("Dashboard/SponserBusinessDetail")
    Call<MemberListResponse> SponserBusiness(@Body MemberListRequest request);

    @Headers("Content-Type: application/json")
    @POST("Dashboard/SelfBusiness")
    Call<MemberListResponse> SelfBusiness(@Body BasicRequest request);


    @Headers("Content-Type: application/json")
    @POST("GetLevelWiseCount")
    Call<UplinePoolCountResponse> GetLevelWiseCount(@Body UplinePoolCountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetPoolTargetReport")
    Call<PoolTargetResponse> GetPoolTargetReport(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetUserGlobalPoolTarget")
    Call<UserGlobalPoolTargetResponse> GetUserGlobalPoolTarget(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetRankList")
    Call<RankListResponse> RankList(@Body BasicRequest basicRequest);
}
