package com.solution.app.justpay4u.ApiHits;

import com.solution.app.justpay4u.Api.Fintech.Object.GetDthPackageRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.ASPayCollectRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AccountOpenListRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AchieveTargetRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AggrePayTransactionUpdateRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AppGetAMRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserListRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserReffDetailRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserRegisterRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AreaWithPincodeRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.BalanceRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.CallBackRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.ChangePinPasswordRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.ChoosePaymentGatwayRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DFStatusRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DTHChannelPlanInfoRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DTHSubscriptionRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DmrRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DthPlanLanguageWiseRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DthSubscriptionReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.EKycStepsValidationRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.FetchBillRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.FosAccStmtAndCollReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.FundDCReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.FundTransferRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GatewayTransactionRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GenerateDepositOTPRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GenerateOrderForUPIRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetAdditionalServiceRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetAepsRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetChargedAmountRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetDMTReceiptRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetDthPackageChannelRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetHLRLookUpRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetRoleForReferralRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetSenderRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetUserClaimsReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.HeavyrefreshRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.IncentiveDetailRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.InitiateMiniBankRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.IntiateUPIRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.LapuRealCommissionRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.LedgerReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.LoginRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.LogoutRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.MapQRToUserRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.MoveToBankReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.NewsRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.NunberListRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.OnboardingRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.OptionalOperatorRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.OtpRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.PayTMTransactionUpdateRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.PlanRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.PurchaseTokenRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.ROfferRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RealApiChangeRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RechargeReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RechargeRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RefundLogRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RefundRequestRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.ResendOtpRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SendMoneyRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SignupRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SlabRangeDetailRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SubmitSocialDetailsRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UPIPaymentRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateFcmRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateKycRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateKycStatusRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateMiniBankStatusRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateSettlementAccountRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateUPIRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateUserRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpgradePackageRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UserDayBookRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UserMNPClaimRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UserMNPRegistrationRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.W2RRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AccountOpenListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AppGetAMResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserReffDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AreaWithPincodeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BankListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.CircleZoneListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.CreateSenderResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DFStatusResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DMTChargedAmountResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DMTReceiptResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DTHInfoResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DthPlanChannelAllResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DthPlanInfoAllResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DthPlanInfoResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DthSubscriptionReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FetchBillResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FosAccStmtAndCollReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FundreqToResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GenerateDepositOTPResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetAEPSResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetAvailablePackageResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetBankAndPaymentModeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetDthPackageResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetEKYCDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetHLRLookUPResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetMNPStatusResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetVAResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.InitiateMiniBankResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.InitiateUpiResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.NumberSeriesListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OnboardingResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OpTypeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorOptionalResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.PlanResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RefundRequestResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RofferResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SettlementAccountResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabCommissionResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabRangeDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.UpdateKycResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.VPAListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.VPAVerifyResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.WalletTypeResponse;
import com.solution.app.justpay4u.Api.Networking.Request.AadhaarOtpReq;
import com.solution.app.justpay4u.Api.Networking.Request.BankTransferRequest;
import com.solution.app.justpay4u.Api.Networking.Request.RequestAadhaarOTP;
import com.solution.app.justpay4u.Api.Networking.Request.ValidatePANAppRequest;
import com.solution.app.justpay4u.Api.Networking.Request.ValidatePINRequest;
import com.solution.app.justpay4u.Api.Networking.Response.GatwayStatusCheckResponse;
import com.solution.app.justpay4u.Api.Networking.Response.ResponseAadhaarOTP;
import com.solution.app.justpay4u.Api.Networking.Response.ValidateAadhaarOTPResponse;
import com.solution.app.justpay4u.Api.Networking.Response.ValidatePANAppResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.EmpUserRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.GetPSTReportEmpRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.GetTargetReportEmpRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.GetTodayTransactorsRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.GetUserByMobileRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.MapPointRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.MeetingDetailRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.PostDailyClosingRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.SetUserCommitmentRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.CreateMeatingResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmpDownlineUserResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmpTodayLivePSTResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmployeesResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmployeesUserResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLMTDVsMTDResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLastSevenDayPSTDataResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLastdayVsTodayChartResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetMeetingDetailResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetMeetingReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetPstReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTargetReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTargetSegmentResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTeriatryReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTodayOutletListForEmpResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTodayTransactorsResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetUserCommitmentResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.MapPointResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.PostDailyClosingResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.UserByMobileResponse;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Model.MoveToWalletRequest;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Model.TransactionModeResponse;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.UDetailByMobRequest;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.UDetailByMobResponse;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.WalletToWalletFTRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Vishnu on 20-01-2017.
 */

public interface FintechEndPointInterface {

    @Headers("Content-Type: application/json")
    @POST("App/GetOperators")
    Call<OperatorListResponse> GetOperators(@Body NunberListRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetOperatorSession")
    Call<OperatorListResponse> GetOperatorSession(@Body BasicRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetCircles")
    Call<CircleZoneListResponse> GetCircles(@Body NunberListRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetNumberSeries")
    Call<NumberSeriesListResponse> GetNumberSeries(@Body NunberListRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/Login")
    Call<LoginResponse> secureLogin(@Body LoginRequest appInfo);


    @Headers("Content-Type: application/json")
    @POST("App/AppUserSignup")
    Call<LoginResponse> AppUserSignup(@Body SignupRequest appInfo);



    @Headers("Content-Type: application/json")
    @POST("App/GetRoleForReferral")
    Call<LoginResponse> GetRoleForReferral(@Body GetRoleForReferralRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/ValidateOTP")
    Call<LoginResponse> ValidateOTP(@Body OtpRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/ValidateGAuthPIN")
    Call<LoginResponse> ValidateGAuthPIN(@Body OtpRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/CheckFlagsEmail")
    Call<BasicResponse> CheckFlagsEmail(@Body BasicRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/SendEmailVerification")
    Call<BasicResponse> SendEmailVerification(@Body BasicRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/SaveSocialAlertSetting")
    Call<BasicResponse> SaveSocialAlertSetting(@Body SubmitSocialDetailsRequest appInfo);

    /* @Headers("Content-Type: application/json")
     @POST("App/GetBalance")
     Call<BalanceResponse> Balancecheck(@Body BalanceRequest appInfo);
 */
    @Headers("Content-Type: application/json")
    @POST("App/v2/GetBalance")
    Call<BalanceResponse> BalancecheckV2(@Body BalanceRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetPopupAfterLogin")
    Call<BasicResponse> GetPopupAfterLogin(@Body BalanceRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetOpTypes")
    Call<OpTypeResponse> GetOpTypes(@Body BalanceRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetOpTypesIndustryWise")
    Call<OpTypeIndustryWiseResponse> GetOpTypesIndustryWise(@Body BalanceRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetAvailablePackages")
    Call<GetAvailablePackageResponse> GetAvailablePackages(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAdditionalService")
    Call<GetAvailablePackageResponse> GetAdditionalService(@Body GetAdditionalServiceRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpgradePackage")
    Call<BasicResponse> UpgradePackage(@Body UpgradePackageRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ActivateAdditionalService")
    Call<BasicResponse> ActivateAdditionalService(@Body UpgradePackageRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/Transaction")
    Call<RechargeResponse> Recharge(@Body RechargeRequest rechargeRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DTHSubscription")
    Call<RechargeResponse> DTHSubscription(@Body DTHSubscriptionRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DTHSubscriptionReport")
    Call<DthSubscriptionReportResponse> DTHSubscriptionReport(@Body DthSubscriptionReportRequest request);

    @Headers("Content-Type: application/json")
    @POST("/App/GetDTHPackage")
    Call<GetDthPackageResponse> GetDTHPackage(@Body GetDthPackageRequest onboardingRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetPincodeArea")
    Call<AreaWithPincodeResponse> GetPincodeArea(@Body AreaWithPincodeRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/Logout")
    Call<RechargeResponse> Logout(@Body LogoutRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DTHCustomerInfo")
    Call<DTHInfoResponse> DTHCustomerInfo(@Body ROfferRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetRNPDTHCustInfo")
    Call<DTHInfoResponse> GetRNPDTHCustInfo(@Body ROfferRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GeUserCommissionRate")
    Call<BasicResponse> GeUserCommissionRate(@Body UpdateKycRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetOperatorOptionals")
    Call<OperatorOptionalResponse> GetOperatorOptionals(@Body OptionalOperatorRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetTargetAchieved")
    Call<AppUserListResponse> GetTargetAchieved(@Body AchieveTargetRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UserDaybook")
    Call<AppUserListResponse> UserDaybook(@Body UserDayBookRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UserDaybookDMR")
    Call<AppUserListResponse> UserDaybookDmt(@Body UserDayBookRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DTHSimplePlanInfo")
    Call<DthPlanInfoResponse> DTHSimplePlanInfo(@Body ROfferRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetDTHSimplePlan")
    Call<DthPlanInfoAllResponse> GetDTHSimplePlanInfo(@Body ROfferRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("/App/DTHChannelByPackageID")
    Call<GetDthPackageResponse> DTHChannelByPackageID(@Body GetDthPackageChannelRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/GetDTHPlanByLang")
    Call<DthPlanInfoAllResponse> GetDTHPlanByLang(@Body DthPlanLanguageWiseRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("/App/DTHChannelPlanInfo")
    Call<DthPlanInfoResponse> DTHChannelPlanInfo(@Body DTHChannelPlanInfoRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetDTHChannelList")
    Call<DthPlanChannelAllResponse> GetDTHChannelList(@Body DTHChannelPlanInfoRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetDTHPlanListByLanguage")
    Call<DthPlanInfoResponse> GetDTHPlanListByLanguage(@Body DthPlanLanguageWiseRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/ROffer")
    Call<RofferResponse> ROffer(@Body ROfferRequest rOfferRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetRNPRoffer")
    Call<RofferResponse> GetRNPRoffer(@Body ROfferRequest rOfferRequest);

    @Headers("Content-Type: application/json")
    @POST("App/SimplePlan")
    Call<PlanResponse> Rechageplans(@Body PlanRequest rOfferRequest);

    @Headers("Content-Type: application/json")
    @POST("App/RechSimplePlan")
    Call<PlanResponse> RechagePlansUpdated(@Body PlanRequest rOfferRequest);

    @Headers("Content-Type: application/json")
    @POST("App/RechargeReport")
    Call<RechargeReportResponse> RechargeReport(@Body RechargeReportRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AEPSReport")
    Call<RechargeReportResponse> AEPSReport(@Body RechargeReportRequest rechargeReportRequest);


    @Headers("Content-Type: application/json")
    @POST("App/IncentiveDetail")
    Call<AppUserListResponse> IncentiveDetail(@Body IncentiveDetailRequest incentiveDetailRequest);

    @Headers("Content-Type: application/json")
    @POST("App/ResendOTP")
    Call<BasicResponse> ResendOTP(@Body ResendOtpRequest resendOtpRequest);

    @Headers("Content-Type: application/json")
    @POST("App/FundOrderReport")
    Call<RechargeReportResponse> FundOrderReport(@Body LedgerReportRequest rechargeReportRequest);


    @Headers("Content-Type: application/json")
    @POST("App/LedgerReport")
    Call<RechargeReportResponse> LedgerReport(@Body LedgerReportRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/FundDCReport")
    Call<RechargeReportResponse> FundDCReport(@Body FundDCReportRequest fundDCReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetWalletType")
    Call<WalletTypeResponse> GetWalletType(@Body BalanceRequest AppFundOrder);


    @Headers("Content-Type: application/json")
    @POST("App/RefundLog")
    Call<AppUserListResponse> RefundLog(@Body RefundLogRequest fundDCReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/FundRequestTo")
    Call<FundreqToResponse> FundRequestTo(@Body BalanceRequest fundDCReportRequest);


    @Headers("Content-Type: application/json")
    @POST("App/GetBankAndPaymentMode")
    Call<GetBankAndPaymentModeResponse> GetBankAndPaymentMode(@Body BalanceRequest fundDCReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AppFundOrder")
    Call<GetBankAndPaymentModeResponse> AppFundOrder(@Body BalanceRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/AppGenerateOrderForUPI")
    Call<RechargeReportResponse> AppGenerateOrderForUPI(@Body GenerateOrderForUPIRequest request);

    @Multipart
    @POST("App/AppFundOrder")
    Call<GetBankAndPaymentModeResponse> AppFundOrder(@Part MultipartBody.Part file, @Part("UserFundRequest") RequestBody userRequest);

    @Multipart
    @POST("App/UploadProfile")
    Call<BasicResponse> UploadProfile(@Part MultipartBody.Part file, @Part("userRequest") RequestBody userRequest);


    @Headers("Content-Type: application/json")
    @POST("App/MyCommission")
    Call<RechargeReportResponse> MyCommission(@Body BalanceRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/DisplayCommission")
    Call<SlabCommissionResponse> DisplayCommission(@Body BalanceRequest AppFundOrder);


    @Headers("Content-Type: application/json")
    @POST("App/RSlabRangDetail")
    Call<SlabRangeDetailResponse> SlabRangDetail(@Body SlabRangeDetailRequest AppFundOrder);


    @Headers("Content-Type: application/json")
    @POST("App/GetSender")
    Call<CreateSenderResponse> GetSender(@Body GetSenderRequest getSenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetSenderP")
    Call<CreateSenderResponse> GetSenderWithPipe(@Body GetSenderRequest getSenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetCallMeUserReq")
    Call<BasicResponse> GetCallMeUserReq(@Body CallBackRequest getSenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/CreateSender")
    Call<RechargeReportResponse> CreateSender(@Body GetSenderRequest createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/CreateSenderP")
    Call<RechargeReportResponse> CreateSenderWithPipe(@Body GetSenderRequest createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifySender")
    Call<RechargeReportResponse> VerifySender(@Body GetSenderRequest createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifySenderP")
    Call<RechargeReportResponse> VerifySenderWithPipe(@Body GetSenderRequest createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AppUserReffDetail")
    Call<AppUserReffDetailResponse> AppUserReffDetail(@Body AppUserReffDetailRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppUserRegistraion")
    Call<BasicResponse> AppUserRegistraion(@Body AppUserRegisterRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetBankList")
    Call<BankListResponse> GetBankList(@Body BalanceRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AddBeneficiary")
    Call<RechargeReportResponse> AddBeneficiary(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AddBeneficiaryP")
    Call<RechargeReportResponse> AddBeneficiaryWithPipe(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyAccount")
    Call<RechargeReportResponse> VerifyAccount(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyAccountP")
    Call<RechargeReportResponse> VerifyAccountWithPipe(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetRealLapuCommission")
    Call<AppUserListResponse> GetRealLapuCommission(@Body LapuRealCommissionRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetCalculatedCommission")
    Call<AppUserListResponse> GetCalculatedCommission(@Body LapuRealCommissionRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBeneficiary")
    Call<RechargeReportResponse> GetBeneficiary(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBeneficiaryP")
    Call<RechargeReportResponse> GetBeneficiaryWithPipe(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetChargedAmount")
    Call<DMTChargedAmountResponse> GetChargedAmount(@Body GetChargedAmountRequest getChargedAmountRequeat);

    //Old Money Transfer Api
    @Headers("Content-Type: application/json")
    @POST("App/GetChargedAmountP")
    Call<DMTChargedAmountResponse> GetChargedAmountWithPipe(@Body GetChargedAmountRequest getChargedAmountRequeat);

    //New Money Transfer Api MLM
    @Headers("Content-Type: application/json")
    @POST("App/BankTranferServiceMLM")
    Call<DMTChargedAmountResponse> BankTranferServiceMLM(@Body BankTransferRequest getChargedAmountRequeat);

    @Headers("Content-Type: application/json")
    @POST("App/SendMoney")
    Call<RechargeReportResponse> SendMoney(@Body SendMoneyRequest sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/SendMoneyP")
    Call<RechargeReportResponse> SendMoneyWithPipe(@Body SendMoneyRequest sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteBeneficiary")
    Call<RechargeReportResponse> DeleteBeneficiary(@Body GetSenderRequest sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteBeneficiaryP")
    Call<RechargeReportResponse> DeleteBeneficiaryWithPipe(@Body GetSenderRequest sendMoneyRequest);


    @Headers("Content-Type: application/json")
    @POST("App/GetDMTReceipt")
    Call<DMTReceiptResponse> GetDMTReceipt(@Body GetDMTReceiptRequest sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DMTReport")
    Call<RechargeReportResponse> DMTReport(@Body DmrRequest dmrRequest);


    @Headers("Content-Type: application/json")
    @POST("App/AppUserChildRoles")
    Call<AppUserListResponse> AppUserChildRoles(@Body AppUserListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/FundOrderPending")
    Call<AppUserListResponse> FundOrderPending(@Body AppUserListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppFundTransfer")
    Call<AppUserListResponse> AppFundTransfer(@Body FundTransferRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppFundReject")
    Call<AppUserListResponse> AppFundReject(@Body FundTransferRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ChangeUserStatus")
    Call<AppUserListResponse> ChangeUserStatus(@Body FundTransferRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppUserList")
    Call<AppUserListResponse> AppUserList(@Body AppUserListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/FOSRetailerList")
    Call<AppUserListResponse> FOSRetailerList(@Body AppUserListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/PGatewayTransaction")
    Call<AppUserListResponse> GatewayTransaction(@Body GatewayTransactionRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/PayTMTransactionUpdate")
    Call<BasicResponse> PayTMTransactionUpdate(@Body PayTMTransactionUpdateRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AggrePayTransactionUpdate")
    Call<BasicResponse> AggrePayTransactionUpdate(@Body AggrePayTransactionUpdateRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAppNews")
    Call<AppUserListResponse> GetAppNews(@Body NewsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAppBanner")
    Call<AppUserListResponse> GetAppBanner(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ChoosePaymentGateway")
    Call<AppUserListResponse> ChoosePaymentGateway(@Body ChoosePaymentGatwayRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetProfile")
    Call<GetUserResponse> GetProfile(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateProfile")
    Call<BasicResponse> UpdateProfile(@Body UpdateUserRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppUpdateBank")
    Call<BasicResponse> UpdateBank(@Body UpdateUserRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppDocumentDetails")
    Call<UpdateKycResponse> UpdateKycApi(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateFCMID")
    Call<BasicResponse> UpdateFCMID(@Body UpdateFcmRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ForgetPassword")
    Call<BasicResponse> ForgetPassword(@Body LoginRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/UpdateKYCStatus")
    Call<UpdateKycResponse> UpdateKYCStatus(@Body UpdateKycStatusRequest request);

    @Multipart
    @POST("App/UploadDocs")
    Call<BasicResponse> UploadDocs(@Part MultipartBody.Part file, @Part("userRequest") RequestBody userRequest);


    @Headers("Content-Type: application/json")
    @POST("App/DisplayCommissionLvl")
    Call<SlabCommissionResponse> DisplayCommissionLvl(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAppNotification")
    Call<AppUserListResponse> GetAppNotification(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetActiveSerive")
    Call<AppUserListResponse> GetActiveSerive(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetCompanyProfile")
    Call<AppUserListResponse> GetCompanyProfile(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/FetchBill")
    Call<FetchBillResponse> FetchBill(@Body FetchBillRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ChangePinOrPassword")
    Call<RechargeReportResponse> ChangePinOrPassword(@Body ChangePinPasswordRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/RefundRequest")
    Call<RefundRequestResponse> RefundRequest(@Body RefundRequestRequest request);

    @POST("App/ChangeDFStatus")
    Call<DFStatusResponse> ChangeDFStatus(@Body DFStatusRequest createsenderRequest);


    @POST("App/ChangeRealAPIStatus")
    Call<BasicResponse> ChangeRealAPIStatus(@Body RealApiChangeRequest createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("/App/CallOnboarding")
    Call<OnboardingResponse> CallOnboarding(@Body OnboardingRequest onboardingRequest);

    @Headers("Content-Type: application/json")
    @POST("App/PSATransaction")
    Call<AppUserListResponse> GetAppPurchageToken(@Body PurchaseTokenRequest request);

    @POST("App/GetTransactionMode")
    Call<TransactionModeResponse> GetTransactionMode(@Body BalanceRequest balanceRequest);

    @POST("App/MoveToWallet")
    Call<TransactionModeResponse> MoveToWallet(@Body MoveToWalletRequest moveToWalletRequest);

    @POST("App/MakeW2RRequest")
    Call<RefundRequestResponse> MakeW2RRequest(@Body W2RRequest w2RRequest);

    @POST("App/WTRLog")
    Call<AppUserListResponse> WTRLog(@Body RefundLogRequest wTRLogRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetHLRLookUp")
    Call<GetHLRLookUPResponse> GetHLRLookUp(@Body GetHLRLookUpRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBank")
    Call<GetBankAndPaymentModeResponse> GetBank(@Body BalanceRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/DTHHeavyRefresh")
    Call<DthPlanInfoResponse> DTHHeavyRefresh(@Body HeavyrefreshRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetRNPDTHHeavyRefresh")
    Call<DthPlanInfoResponse> GetRNPDTHHeavyRefresh(@Body HeavyrefreshRequest dthInfoRequest);


    @Headers("Content-Type: application/json")
    @POST("App/InitiateMiniBank")
    Call<InitiateMiniBankResponse> InitiateMiniBank(@Body InitiateMiniBankRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateMiniBankStatus")
    Call<InitiateMiniBankResponse> UpdateMiniBankStatus(@Body UpdateMiniBankStatusRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/GetAEPSBanks")
    Call<BankListResponse> GetAEPSBanks(@Body BalanceRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBalanceAEPS")
    Call<GetAEPSResponse> GetBalanceAEPS(@Body GetAepsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GenerateDepositOTP")
    Call<GenerateDepositOTPResponse> GenerateDepositOTP(@Body GenerateDepositOTPRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyDepositOTP")
    Call<GenerateDepositOTPResponse> VerifyDepositOTP(@Body GenerateDepositOTPRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DepositNow")
    Call<GenerateDepositOTPResponse> DepositNow(@Body GenerateDepositOTPRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/BankMiniStatement")
    Call<GetAEPSResponse> BankMiniStatement(@Body GetAepsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AEPSWithdrawal")
    Call<GetAEPSResponse> AEPSWithdrawal(@Body GetAepsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/Aadharpay")
    Call<GetAEPSResponse> Aadharpay(@Body GetAepsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/IntiateUPI")
    Call<InitiateUpiResponse> IntiateUPI(@Body IntiateUPIRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UPIPaymentUpdate")
    Call<InitiateUpiResponse> UPIPaymentUpdate(@Body UpdateUPIRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetVADetail")
    Call<GetVAResponse> GetVADetail(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetSmartCollectDetail")
    Call<GetVAResponse> GetSmartCollectDetail(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAppRefferalContent")
    Call<AppUserListResponse> GetAppRefferalContent(@Body BasicRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/ASPayCollect")
    Call<AppUserListResponse> ASPayCollect(@Body ASPayCollectRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/GetASCollectBank")
    Call<BankListResponse> GetASCollectBank(@Body BalanceRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/AccStmtAndColl")
    Call<FosAccStmtAndCollReportResponse> AccStmtAndColl(@Body FosAccStmtAndCollReportRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/GetASSumm")
    Call<FosAccStmtAndCollReportResponse> GetASSumm(@Body FosAccStmtAndCollReportRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppGetAM")
    Call<AppGetAMResponse> AppGetAM(@Body AppGetAMRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAccountOpeningBanner")
    Call<AccountOpenListResponse> GetAccountOpeningList(@Body AccountOpenListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/MapQRToUser")
    Call<BasicResponse> MapQRToUser(@Body MapQRToUserRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DoUPIPayment?")
    Call<RechargeReportResponse> doUPIPayment(@Body UPIPaymentRequest upiPaymentReq);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyUPI")
    Call<VPAVerifyResponse> VerifyUPI(@Body UPIPaymentRequest upiPaymentReq);

    @Headers("Content-Type: application/json")
    @POST("App/GetVPAListUPIPayement")
    Call<VPAListResponse> GetVPAListUPIPayement(@Body UPIPaymentRequest upiPaymentReq);


    @Headers("Content-Type: application/json")
    @POST("App/GetSettlementAccount")
    Call<SettlementAccountResponse> GetSettlementAccount(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateSettlementAccount")
    Call<BasicResponse> UpdateSettlementAccount(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteSettlementAcount")
    Call<BasicResponse> DeleteSettlementAcount(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ToggleDefaulSettlementAcount")
    Call<BasicResponse> ToggleDefaulSettlementAcount(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/VerifySettlementAccountOfUser")
    Call<BasicResponse> VerifySettlementAccountOfUser(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateUTRByUser")
    Call<BasicResponse> UpdateUTRByUser(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/MoveToBankReport")
    Call<RechargeReportResponse> MoveToBankReport(@Body MoveToBankReportRequest dmrRequest);

    @Headers("Content-Type: application/json")
    @POST("App/RealTimeCommission")
    Call<SlabRangeDetailResponse> RealTimeCommission(@Body SlabRangeDetailRequest AppFundOrder);

    //KYC Verification Steps
    @Headers("Content-Type: application/json")
    @POST("App/GetEKYCDetail")
    Call<GetEKYCDetailResponse> GetE_KYCDetail(@Body BasicRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/ValidateGSTINForEKYC")
    Call<GetEKYCDetailResponse> ValidateGSTINE_KYC(@Body EKycStepsValidationRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/ValidateAadharForEKYC")
    Call<GetEKYCDetailResponse> ValidateAadharE_KYC(@Body EKycStepsValidationRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/ValidatePAN")
    Call<GetEKYCDetailResponse> ValidatePAN(@Body EKycStepsValidationRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/ValidateBankAccount")
    Call<GetEKYCDetailResponse> ValidateBankAccount(@Body EKycStepsValidationRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/ValidateAadharOTPForEKYC")
    Call<GetEKYCDetailResponse> ValidateAadharOTPE_KYC(@Body EKycStepsValidationRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/EditEKYCStep")
    Call<GetEKYCDetailResponse> EditE_KYCSteps(@Body EKycStepsValidationRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/CashFreeTransactionUpdate")
    Call<BasicResponse> CashFreeTransactionUpdate(@Body PayTMTransactionUpdateRequest request);

    //@Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("PGCallback/CashFreeStatusCheck")
    Call<InitiateUpiResponse> CashFreeStatusCheck(@Field("TID") String tid);

    @Headers("Content-Type: application/json")
    @POST("/App/GetMNPStatus")
    Call<GetMNPStatusResponse> GetMNPStatus(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UserMNPRegistration")
    Call<GetMNPStatusResponse> UserMNPRegistration(@Body UserMNPRegistrationRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetUserClaimsReport")
    Call<GetMNPStatusResponse> GetUserClaimsReport(@Body GetUserClaimsReportRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UserMNPClaim")
    Call<BasicResponse> UserMNPClaim(@Body UserMNPClaimRequest request);

    @Headers("Content-Type: application/json")
    @POST("/App/GenerateTokenGI")
    Call<OnboardingResponse> GenerateTokenGI(@Body OptionalOperatorRequest request);


    //Emp API
    @Headers("Content-Type: application/json")
    @POST("App/GetTargetSegment")
    Call<GetTargetSegmentResponse> GetTargetSegment(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetTodayTransactors")
    Call<GetTodayTransactorsResponse> GetTodayTransactors(@Body GetTodayTransactorsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetTodayOutletsListForEmp")
    Call<GetTodayOutletListForEmpResponse> GetTodayOutletsListForEmp(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetEmpTodayLivePST")
    Call<GetEmpTodayLivePSTResponse> GetEmpTodayLivePST(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetComparisionChart")
    Call<GetLMTDVsMTDResponse> GetComparisionChart(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetLastdayVsTodayChart")
    Call<GetLastdayVsTodayChartResponse> GetLastdayVsTodayChart(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetLastSevenDayPSTData")
    Call<GetLastSevenDayPSTDataResponse> GetLastSevenDayPSTDataChart(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetUserCommitment")
    Call<GetUserCommitmentResponse> GetUserCommitment(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/SetUserCommitment")
    Call<BasicResponse> SetUserCommitment(@Body SetUserCommitmentRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetUserCommitmentChart")
    Call<BasicResponse> GetUserCommitmentChart(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetEmpDownlineUser")
    Call<GetEmpDownlineUserResponse> GetEmpDownlineUser(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetPSTReportEmp")
    Call<GetPstReportResponse> GetPSTReportEmp(@Body GetPSTReportEmpRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetTertiaryReportEmp")
    Call<GetTeriatryReportResponse> GetTertiaryReportEmp(@Body GetPSTReportEmpRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetEmployeeTargetReport")
    Call<GetTargetReportResponse> GetEmployeeTargetReport(@Body GetTargetReportEmpRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetEmployees")
    Call<GetEmployeesResponse> GetEmployees(@Body EmpUserRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetEmployeeeUser")
    Call<GetEmployeesUserResponse> GetEmployeeeUser(@Body EmpUserRequest request);

    @GET("GetCharts")
    Call<String> GetCharts(@Query("UserID") String userid, @Query("chartType") int chartType);

    @Headers("Content-Type: application/json")
    @POST("App/CreateMeeting")
    Call<CreateMeatingResponse> CreateMeeting(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UserDaybookForEmployee")
    Call<AppUserListResponse> UserDaybookForEmployee(@Body UserDayBookRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DMTReportForEmployee")
    Call<RechargeReportResponse> DMTReportForEmployee(@Body DmrRequest dmrRequest);

    /*@Headers("Content-Type: application/json")
    @POST("App/PostCreateMeeting")
    Call<BasicResponse> PostCreateMeeting(@Body SubmitMeetingRequest request);*/


    /*@Headers("Content-Type: application/json")*/
    @Multipart
    @POST("App/PostCreateMeeting")
    Call<BasicResponse> PostCreateMeeting(@Part MultipartBody.Part file, @Part("CreateMeetingRequest") RequestBody userRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetUserByMobile")
    Call<UserByMobileResponse> GetUserByMobile(@Body GetUserByMobileRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/PostDailyClosing")
    Call<PostDailyClosingResponse> PostDailyClosing(@Body PostDailyClosingRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetMeetingDetail")
    Call<GetMeetingDetailResponse> GetMeetingDetail(@Body MeetingDetailRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetMeetingReport")
    Call<GetMeetingReportResponse> GetMeetingReport(@Body MeetingDetailRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAreabyPincode")
    Call<AreaWithPincodeResponse> GetAreabyPincode(@Body AreaWithPincodeRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetMeetingSubReport")
    Call<GetMeetingDetailResponse> GetMeetingSubReport(@Body MeetingDetailRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/LedgerReportForEmployee")
    Call<RechargeReportResponse> LedgerReportForEmployee(@Body LedgerReportRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetMapPoints")
    Call<MapPointResponse> GetMapPoints(@Body MapPointRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/RechargeReportForEmployee")
    Call<RechargeReportResponse> RechargeReportForEmployee(@Body RechargeReportRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/FundDCReportForEmployee")
    Call<RechargeReportResponse> FundDCReportForEmployee(@Body FundDCReportRequest fundDCReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetUDetailByMob?")
    Call<UDetailByMobResponse> getUDetailByMob(@Body UDetailByMobRequest uDetailByMobRequest);

    @Headers("Content-Type: application/json")
    @POST("App/WalletToWalletFT?")
    Call<UDetailByMobResponse> walletToWalletFT(@Body WalletToWalletFTRequest walletToWalletFTRequest);

    @FormUrlEncoded
    @POST("PGCallback/AllUPIStatusCheck")
    Call<GatwayStatusCheckResponse>AllUPIStatusCheck(@Field("TID") String tid);


    //aadhaar verification
    @Headers("Content-Type: application/json")
    @POST("App/GenerateAadharOTP")
    Call<ResponseAadhaarOTP>GenerateAadhaarOTP(@Body RequestAadhaarOTP aadhaarOTPReq);

    @Headers("Content-Type: application/json")
    @POST("app/ValidateAadharOTP")
    Call<ValidateAadhaarOTPResponse>ValidateAadhaarOTP(@Body AadhaarOtpReq request);

    @Headers("Content-Type: application/json")
    @POST("App/ValidatePANApp")
    Call<ValidatePANAppResponse>ValidatePanApp(@Body ValidatePANAppRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ValidatePIN")
    Call<BasicResponse> ValidatePIN(@Body ValidatePINRequest validatePINRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetTotalRechargeComm")
    Call<RechargeResponse> GetTotalRechargeComm(@Body BalanceRequest basicRequest);
}
