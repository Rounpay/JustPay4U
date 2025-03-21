package com.solution.app.justpay4u.ApiHits;


import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.AddShippingAddressRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.AddToCartRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.AddressMasterRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.AllProductListRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.ChangeQuantityRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.CitiesRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.OrderDetailRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.PincodeAreaRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.PlaceOrderRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.RemoveFromCartRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.SearchKeywordRequest;
import com.solution.app.justpay4u.Api.Shopping.Request.WishListAddRemoveRequest;
import com.solution.app.justpay4u.Api.Shopping.Response.AddToCartResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.AddressListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.AddressMasterResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.AllProductListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.BasicApiResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.CartDetailResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.CheckDeliveryResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.DashbaordInfoResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.GetAllFilterResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.MainCategoryWiseProductResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.NewArrivalAndSaleDataResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.OfferListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.OrderDetailResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.OrderListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.PincodeAreaResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.PincodeResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.PlaceOrderResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.ProductInfoDetailsResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.PromotionPopupResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.SearchKeywordResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.StatesCitiesResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.TrackOrderResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Vishnu on 20-01-2017.
 */

public interface ShoppingEndPointInterface {


    //Shopping Api
    @GET("App/promotionPopUp")
    Call<PromotionPopupResponse> getPromotionPopUp(@Query("WebsiteID") String webSiteId);

    /* @GET("App/productInfoApp")
     Call<ProductInfoDetailsResponse> getProductInfo(@Query("WebSiteId") String webSiteId,
                                                     @Query("POSId") String posId,
                                                     @Query("LoginId") String loginId);*/
    @GET("App/getAllProductInfo")
    Call<ProductInfoDetailsResponse> getProductInfo(@Query("WebSiteId") String webSiteId,
                                                    @Query("POSId") String posId,
                                                    @Query("LoginId") String loginId);


    @GET("App/websiteInfo")
    Call<DashbaordInfoResponse> getWebsiteInfo(@Query("HostName") String hostName,
                                               @Query("LoginId") String mLoginId,
                                               @Query("WebsiteId") String wbsiteId);

    /*@GET("App/newArrivalOnSaleApi")*/
    @GET("App/GetnewArrivalOnSaleApi")
    Call<NewArrivalAndSaleDataResponse> getNewArrivalOnSaleApi(@Query("WebSiteId") String webSiteId,
                                                               @Query("LoginId") String mLoginId);

    @GET("App/getMaincategoriesList")
    Call<MainCategoryWiseProductResponse> getMainCategoriesList(@Query("LoginId") String mLoginId,
                                                                @Query("WebSiteId") String webSiteId,
                                                                @Query("MainCategoryId") int mainCategoryId,
                                                                @Query("CategoryId") int categoryId);

    @GET("App/getMultiMaincategoriesList")
    Call<MainCategoryWiseProductResponse> getMultiMaincategoriesList(@Query("LoginId") String mLoginId,
                                                                     @Query("WebSiteId") String webSiteId,
                                                                     @Query("MainCategoryId") String mainCategoryId,
                                                                     @Query("CategoryId") int categoryId);


    @GET("App/getAllSimilarItems")
    Call<MainCategoryWiseProductResponse> getAllSimilarItems(@Query("LoginId") String loginId,
                                                             @Query("WebSiteId") String webSiteId,
                                                             @Query("POSId") String posId);

    @GET("App/getRecentViewItems")
    Call<MainCategoryWiseProductResponse> getRecentViewItems(@Query("WebSiteId") String webSiteId,
                                                             @Query("BrowserId") String browserId);

    @GET("App/getAllWishListItems")
    Call<MainCategoryWiseProductResponse> getAllWishListItems(@Query("CustomerId") String loginId,
                                                              @Query("WebSiteId") String webSiteId);

    @GET("App/getCartDetails")
    Call<CartDetailResponse> getCartDetailsApi(@Query("LoginId") String loginId,
                                               @Query("WebSiteId") String webSiteId);

    @Headers("Content-Type: application/json")
    /*@POST("App/CartDetail")*/
    @POST("App/ProceedToPay")
    Call<CartDetailResponse> CartDetail(@Body BasicRequest request);

    @GET("App/getKeywordList")
    Call<ArrayList<SearchKeywordResponse>> getKeywordList(@Query("SearchKeyword") String searchKeyword,
                                                          @Query("WebSiteId") String webSiteId);

    @Headers("Content-Type: application/json")
    @POST("App/getAllProductsetList")
    Call<AllProductListResponse> getAllProductsetList(@Body AllProductListRequest allProductListRequest);

    @Headers("Content-Type: application/json")
    @POST("App/getAllFilterList")
    Call<GetAllFilterResponse> getAllFilterList(@Body AllProductListRequest allProductListRequest);

    @Headers("Content-Type: application/json")
    @POST("App/InsertWishList")
    Call<BasicApiResponse> InsertWishList(@Body WishListAddRemoveRequest wishListAddRemoveRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteWishList")
    Call<BasicApiResponse> DeleteWishList(@Body WishListAddRemoveRequest wishListAddRemoveRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AddressMaster")
    Call<AddressMasterResponse> AddressMaster(@Body AddressMasterRequest addressMasterRequest);

    @Headers("Content-Type: application/json")
    @POST("App/SearchKeywordApi")
    Call<MainCategoryWiseProductResponse> SearchKeywordApi(@Body SearchKeywordRequest searchKeywordRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AddToCart")
    Call<AddToCartResponse> AddToCartApi(@Body AddToCartRequest addToCartRequest);

    @GET("App/CheckDeliveryApi")
    Call<CheckDeliveryResponse> CheckDeliveryApi(@Query("PinCode") String PinCode);

    @GET("App/getAddressbyPinCode")
    Call<PincodeResponse> getAddressbyPinCode(@Query("PinCode") String pincode);

    @GET("App/getOrderDetails")
    Call<OrderDetailResponse> getOrderDetails(@Query("LoginId") String loginId,
                                              @Query("OrderNo") String orderNo,
                                              @Query("WebSiteId") String webSiteId);

    @GET("App/getAllOrderList")
    Call<OrderListResponse> getAllOrderList(@Query("LoginId") String loginId,
                                            @Query("WebSiteId") String webSiteId);

    @GET("App/TrackOrderApi")
    Call<TrackOrderResponse> TrackOrder(@Query("CourierTrackingNo") String courierTrackingNo, @Query("OrderNo") String orderNo);

    @GET("LoginApi/getOtherOfferDetailsNew")
    Call<OfferListResponse> getOtherOfferDetails(@Query("userid") String userid);


    @Headers("Content-Type: application/json")
    @POST("App/PlaceOrder")
    Call<PlaceOrderResponse> PlaceOrder(@Body PlaceOrderRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetShippingAddresses")
    Call<AddressListResponse> GetShippingAddresses(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAreabyPincodeRequest")
    Call<PincodeAreaResponse> GetAreabyPincodeRequest(@Body PincodeAreaRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetStates")
    Call<StatesCitiesResponse> GetStates(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetCities")
    Call<StatesCitiesResponse> GetCities(@Body CitiesRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AddShippingAddress")
    Call<StatesCitiesResponse> AddShippingAddress(@Body AddShippingAddressRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/RemoveFromCart")
    Call<BasicResponse> RemoveFromCart(@Body RemoveFromCartRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ChangeQuantity")
    Call<BasicResponse> ChangeQuantity(@Body ChangeQuantityRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/Order")
    Call<OrderListResponse> GetOrders(@Body OrderDetailRequest request);


}
