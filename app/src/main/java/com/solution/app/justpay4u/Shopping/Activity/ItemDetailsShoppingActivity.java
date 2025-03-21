package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.like.LikeButton;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.solution.app.justpay4u.Api.Shopping.Object.AddressData;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductDetailImageListItem;
import com.solution.app.justpay4u.Api.Shopping.Response.AddToCartResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.BasicApiResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.CheckDeliveryResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.MainCategoryWiseProductResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.ProductInfoDetailsResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.DashboardGridShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.FeatureCategoryListShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.FeatureListShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.ProductDetailImageListShoppingAdapter;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.RecyclerViewItemDecoration;
import com.solution.app.justpay4u.Util.SelectAddressBottomSheet;

import java.util.ArrayList;


public class ItemDetailsShoppingActivity extends AppCompatActivity {
    private final int CHANGE_ADDRESS_REQUEST = 647;
    String productId, linkSm;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    ProductDetailImageListShoppingAdapter imageListAdapter;
    FeatureListShoppingAdapter featureListAdapter;
    ProductInfoDetailsResponse mProductInfoDetailsResponse = new ProductInfoDetailsResponse();
    int CART_DELETE_REQUEST = 324;
    private final int LOGIN_ACTIVITY_RESULT = 720;
    boolean isFromDeepLInk;
    ArrayList<ProductDetailImageListItem> imageListItems = new ArrayList<>();
    CustomLoader loader;
    int selectedImagePosition = 0;
    String shareLinkStr, affiliateShareLinkStr;
    int isInCart;
    LikeButton vectorLike;
    int[] locationBottomBtnView = new int[2];
    int[] locationScrollBtnView = new int[2];
    View discountView;
    View loaderView, shippingLine;
    TextView addressType, pinCodeAddress, noDeliveryAddress, deliveryAddressTitle, userName, address, addressBtn, shippingTime, shippingCharge;
    View addressContainer, shippindDeliveryDetailView, shippingDetailsView, delveryAddressView;
    View similarViewAllBtn, recentViewAllBtn;
    ArrayList<DashboardProductListData> similarProductListData = new ArrayList<>();
    ArrayList<DashboardProductListData> recentViewProductListData = new ArrayList<>();
    private NestedScrollView scrollbar;
    private ImageView banner;
    private RecyclerView imageRecyclerView, featureRecyclerView;
    private TextView titleText;
    private TextView priceText;
    private TextView mrpText, discountText, productCodeTitleText;
    private TextView availabilityText;
    private LinearLayout layoutActionShare;
    private View likeOverView;
    private LinearLayout descriptionView, additionalDescriptionView, productCodeView;
    private RecyclerView featureCategoryRecyclerView;
    private WebView descriptionWebView, additionalDescriptionWebView;
    private RecyclerView relatedProductRecyclerView, recentViewProductRecyclerView;
    private LinearLayout bottomBtnView;
    private View noNetworkView, noDataView, retryBtn;
    private CardView elevationView;
    private LinearLayout featureCategoryView;
    private LinearLayout featureView;
    private TextView productCodeValueText;
    private LinearLayout detailView;
    private LinearLayout scrollBtnView;
    private TextView actionScrollCart;
    private TextView actionScrollBuy;
    private LinearLayout relatedProductView, recentViewProductView;
    private TextView textActionCart;
    private TextView textActionBuy;
    private String productName;
    private int availibility;
    private boolean isScrollBottomSimilarApiCalled, isScrollBottomRecentApiCalled;
    private RequestOptions requestOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_item_details);
            requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.placeholder_square);
            requestOptions.error(R.drawable.placeholder_square);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            //requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            //requestOptions.skipMemoryCache(true);
            initToolBar();
            findViews();
            onClickView();
            featureRecyclerView.setNestedScrollingEnabled(true);
            featureRecyclerView.setLayoutManager(new LinearLayoutManager(ItemDetailsShoppingActivity.this, LinearLayoutManager.VERTICAL, false));

            imageRecyclerView.setNestedScrollingEnabled(true);
            imageRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.HORIZONTAL, false));
            imageRecyclerView.setLayoutManager(new LinearLayoutManager(ItemDetailsShoppingActivity.this, LinearLayoutManager.HORIZONTAL, false));
            imageListAdapter = new ProductDetailImageListShoppingAdapter(this, imageListItems, false);
            imageRecyclerView.setAdapter(imageListAdapter);
            imageListAdapter.setClickListener((blurView, view, position) -> {


                selectedImagePosition = position;
                imageListItems.get(position).setSelected(true);
                for (int i = 0; i < imageListItems.size(); i++) {
                    if (i != position) {
                        imageListItems.get(i).setSelected(false);
                    }
                }

                if (blurView != null && blurView.getVisibility() == View.VISIBLE) {
                    startActivity(new Intent(ItemDetailsShoppingActivity.this, ZoomImageShoppingActivity.class)
                            .putExtra("ImageList", imageListItems)
                            .putExtra("SelectIndex", position)
                            .putExtra("ShareLink", shareLinkStr)
                            .putExtra("ProductName", productName)
                            .putExtra("AffiliateShareLink", affiliateShareLinkStr)
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                } else {
                    setImage(imageListItems.get(position).getImageMedium());
                    imageListAdapter.notifyDataSetChanged();
                }

            });

            banner.setOnClickListener(v -> startActivity(new Intent(ItemDetailsShoppingActivity.this, ZoomImageShoppingActivity.class)
                    .putExtra("ImageList", imageListItems)
                    .putExtra("SelectIndex", selectedImagePosition)
                    .putExtra("ShareLink", shareLinkStr)
                    .putExtra("ProductName", productName)
                    .putExtra("AffiliateShareLink", affiliateShareLinkStr)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));

            featureCategoryRecyclerView.setNestedScrollingEnabled(true);
            featureCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(ItemDetailsShoppingActivity.this, LinearLayoutManager.VERTICAL, false));

        /*recentViewProductRecyclerView.setNestedScrollingEnabled(true);
        recentViewProductRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.HORIZONTAL));
        recentViewProductRecyclerView.setLayoutManager(new LinearLayoutManager(ItemDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
*/


            Intent intent = getIntent();
            //String productIdStr;
            if (intent.getData() != null) {
                Uri data = intent.getData();
                productId = data.getLastPathSegment();
                linkSm = data.getQueryParameter("SM");
                if (productId == null || productId.isEmpty()) {
                    productId = intent.getStringExtra("ProductId");
                } else {
                    isFromDeepLInk = true;
                }
            } else {
                productId = intent.getStringExtra("ProductId");
            }
      /*  productId = Integer.parseInt(productIdStr);
        Log.e("prod_id", String.valueOf(ProductId));*/

            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
            appBarLayout.setExpanded(false);
            appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> addScrollEfectInbottomBtn());


            scrollbar.setOnScrollChangeListener(
                    (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

                        addScrollEfectInbottomBtn();

                        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                            if (!isScrollBottomSimilarApiCalled) {
                                HitApiSimilarProduct();
                            }

                            if (!isScrollBottomRecentApiCalled) {
                                HitApiRecentProduct();
                            }

                            // Toast.makeText(ItemDetailsActivity.this, "BOTTOM SCROLL", Toast.LENGTH_LONG).show();
                        }
                    });

            HitApi();

            retryBtn.setOnClickListener(v -> {
                noNetworkView.setVisibility(View.GONE);
                noDataView.setVisibility(View.GONE);
                HitApi();

            });
        });

    }


    /*void getLevelCommissionAmount() {
        UtilMethods.INSTANCE.GetLevelCommissionChart(this, productId, pref, new UtilMethods.ApiHitCallBack() {
            @Override
            public void onSucess(Object response) {

                LevelCommissionChartResponse mLevelCommissionChartResponse = (LevelCommissionChartResponse) response;

                if (mLevelCommissionChartResponse != null && mLevelCommissionChartResponse.getStatus()
                        && mLevelCommissionChartResponse.getListData() != null && mLevelCommissionChartResponse.getData() != null) {
                    if (mLevelCommissionChartResponse.getListData().size() > 0) {
                        if (mLevelCommissionChartResponse.getListData().get(0).getCommission() != 0) {
                            directIncomeText.setText("Cash back of \u20B9 " + UtilMethods.INSTANCE.formatedAmount(mLevelCommissionChartResponse.getListData().get(0).getCommission() + ""));
                            directIncomeText.setVisibility(View.VISIBLE);
                        }
                        if (mLevelCommissionChartResponse.getListData().get(1).getCommission() != 0) {
                            textActionShare.setText("Share to earn");
                            shareMsgTv.setVisibility(View.VISIBLE);
                            shareMsgTv.setText("You will get \u20B9 " + UtilMethods.INSTANCE.formatedAmount(mLevelCommissionChartResponse.getListData().get(1).getCommission() + "") + "\nWhen your friend buy this.");
                        }
                    }
                }
            }

            @Override
            public void onError(int ErrorType, String msg) {

            }
        });
    }*/

    void onClickView() {
        similarViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ItemDetailsShoppingActivity.this, SimilarProductShoppingActivity.class)
                        .putExtra("TITLE", "Similar Products")
                        .putExtra("SIMILAR_PRODUCT_LIST", similarProductListData)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), LOGIN_ACTIVITY_RESULT);
            }
        });
        recentViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ItemDetailsShoppingActivity.this, SimilarProductShoppingActivity.class)
                        .putExtra("TITLE", "Recent View Products")
                        .putExtra("SIMILAR_PRODUCT_LIST", recentViewProductListData)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), LOGIN_ACTIVITY_RESULT);
            }
        });
        /*layoutActionSimilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemDetailsActivity.this, SimilarProductActivity.class)

                        .putExtra("TITLE", "Similar Products")
                        .putExtra("SIMILAR_PRODUCT_LIST", simillarProductList)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });*/

        layoutActionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLink();
            }
        });

        textActionCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCartApi(true);
            }
        });

        textActionBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCartApi(false);
            }
        });

        actionScrollBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCartApi(false);
            }
        });

        actionScrollCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCartApi(true);
            }
        });


        likeOverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vectorLike.performClick();
                AddRemoveWishList(!vectorLike.isLiked(), productId);

            }
        });
        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAddressBottomSheet mSelectAddressBottomSheet = new SelectAddressBottomSheet();
                mSelectAddressBottomSheet.show(getSupportFragmentManager(), productId);
                // mSelectAddressBottomSheet.setTitle("");

                mSelectAddressBottomSheet.setCallBack(new SelectAddressBottomSheet.ButtonCallBack() {
                    @Override
                    public void pincodeCheckBtnClick(String pincode) {
                        CheckDeliveryApi(pincode, true);
                    }

                    @Override
                    public void gpsBtnClick(String pincode) {
                        CheckDeliveryApi(pincode, true);
                    }

                    @Override
                    public void selectAddressBTnClick() {
                        Intent intent = new Intent(ItemDetailsShoppingActivity.this, AddressListShoppingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("ProductId", productId);
                        intent.putExtra("FromDetail", true);
                        startActivityForResult(intent, CHANGE_ADDRESS_REQUEST);
                    }
                });
            }
        });
    }

    void CheckDeliveryApi(final String pincode, final boolean isFromChange) {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (isFromChange) {
                loader.show();
            }


            ApiShoppingUtilMethods.INSTANCE.CheckDeliveryApi(pincode, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    CheckDeliveryResponse data = (CheckDeliveryResponse) response;
                    if (data.isStatus() && data.getData() != null) {

                        if (isFromChange) {
                            addressType.setVisibility(View.GONE);
                            shippindDeliveryDetailView.setVisibility(View.VISIBLE);
                            addressContainer.setVisibility(View.GONE);
                            pinCodeAddress.setVisibility(View.VISIBLE);
                            noDeliveryAddress.setVisibility(View.GONE);
                            pinCodeAddress.setTextColor(getResources().getColor(R.color.black));
                            pinCodeAddress.setText(data.getData().getCity() + ", " + data.getData().getState() + " - " + pincode);

                        }

                        if (availibility > 0) {
                            textActionBuy.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            textActionBuy.setText("BUY NOW");
                            textActionBuy.setClickable(true);
                            actionScrollBuy.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            actionScrollBuy.setText("BUY NOW");
                            actionScrollBuy.setClickable(true);
                            actionScrollCart.setVisibility(View.VISIBLE);
                            textActionCart.setVisibility(View.VISIBLE);
                            bottomBtnView.setVisibility(View.GONE);
                            bottomBtnView.setVisibility(View.VISIBLE);
                        } else {
                            textActionBuy.setBackgroundColor(getResources().getColor(R.color.red));
                            textActionBuy.setText("OUT OF STOCK");
                            textActionBuy.setClickable(false);
                            actionScrollBuy.setBackgroundColor(getResources().getColor(R.color.red));
                            actionScrollBuy.setText("OUT OF STOCK");
                            actionScrollBuy.setClickable(false);
                            actionScrollCart.setVisibility(View.GONE);
                            textActionCart.setVisibility(View.GONE);
                            bottomBtnView.setVisibility(View.GONE);
                            bottomBtnView.setVisibility(View.VISIBLE);
                        }


                    } else {
                        shippindDeliveryDetailView.setVisibility(View.VISIBLE);
                        if (isFromChange) {
                            addressType.setVisibility(View.GONE);
                            addressContainer.setVisibility(View.GONE);
                            noDeliveryAddress.setVisibility(View.GONE);
                            pinCodeAddress.setVisibility(View.VISIBLE);
                            pinCodeAddress.setText("Sorry, Delivery not available on Pincode - " + pincode);
                            pinCodeAddress.setTextColor(getResources().getColor(R.color.red));
                        } else {
                            noDeliveryAddress.setVisibility(View.VISIBLE);
                            pinCodeAddress.setVisibility(View.GONE);
                            addressType.setVisibility(View.VISIBLE);
                            noDeliveryAddress.setText("Sorry, Delivery not available on Pincode - " + pincode);
                        }


                        if (availibility > 0) {
                            textActionBuy.setBackgroundColor(getResources().getColor(R.color.red));
                            textActionBuy.setText("No Delivery");
                            textActionBuy.setClickable(false);
                            actionScrollBuy.setBackgroundColor(getResources().getColor(R.color.red));
                            actionScrollBuy.setText("No Delivery");
                            actionScrollBuy.setClickable(false);
                            actionScrollCart.setVisibility(View.VISIBLE);
                            textActionCart.setVisibility(View.VISIBLE);
                            bottomBtnView.setVisibility(View.GONE);
                            bottomBtnView.setVisibility(View.VISIBLE);
                        } else {
                            textActionBuy.setBackgroundColor(getResources().getColor(R.color.red));
                            textActionBuy.setText("OUT OF STOCK");
                            textActionBuy.setClickable(false);
                            actionScrollBuy.setBackgroundColor(getResources().getColor(R.color.red));
                            actionScrollBuy.setText("OUT OF STOCK");
                            actionScrollBuy.setClickable(false);
                            actionScrollCart.setVisibility(View.GONE);
                            textActionCart.setVisibility(View.GONE);
                            bottomBtnView.setVisibility(View.GONE);
                            bottomBtnView.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onError(int errorCode, String msg) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (isFromChange) {
                        if (errorCode == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                            ApiShoppingUtilMethods.INSTANCE.NetworkError(ItemDetailsShoppingActivity.this);
                        } else {
                            ApiShoppingUtilMethods.INSTANCE.Error(ItemDetailsShoppingActivity.this, getString(R.string.some_thing_error));
                        }
                    }
                }
            });
        } else {
            if (isFromChange) {
                ApiShoppingUtilMethods.INSTANCE.NetworkError(ItemDetailsShoppingActivity.this);
            }
        }
    }

    void saveCartApi(final boolean isFromAddCart) {

        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (!isFromAddCart && isInCart == 1) {
                Intent intent = new Intent(ItemDetailsShoppingActivity.this, CartDetailShoppingActivity.class);
                intent.putExtra("PRODUCT_ID", productId);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, CART_DELETE_REQUEST);
            } else {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                ApiShoppingUtilMethods.INSTANCE.AddCart(this, productId, 1 + "", new ApiShoppingUtilMethods.ApiHitCallBack() {
                    @Override
                    public void onSucess(final Object response) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        AddToCartResponse data = (AddToCartResponse) response;
                        if (data.getStatuscode() == 1) {

                            mCartItemCount = data.getTotalItem();
                            ApiShoppingUtilMethods.INSTANCE.setCartCount(ItemDetailsShoppingActivity.this, mCartItemCount);

                            try {
                                if (!ApiShoppingUtilMethods.INSTANCE.cartProductIdList.contains(Integer.parseInt(productId))) {
                                    ApiShoppingUtilMethods.INSTANCE.cartProductIdList.add(Integer.parseInt(productId));
                                }
                            } catch (NullPointerException nfe) {

                            }
                            setupBadge();
                            isInCart = 1;
                            if (!isFromAddCart) {
                                Intent intent = new Intent(ItemDetailsShoppingActivity.this, CartDetailShoppingActivity.class);
                                intent.putExtra("PRODUCT_ID", productId);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(intent, CART_DELETE_REQUEST);
                            }
                        } else {
                            ApiShoppingUtilMethods.INSTANCE.Error(ItemDetailsShoppingActivity.this, data.getMessage());
                        }
                    }

                    @Override
                    public void onError(int errorCode, String msg) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (errorCode == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                            ApiShoppingUtilMethods.INSTANCE.NetworkError(ItemDetailsShoppingActivity.this);
                        } else {
                            ApiShoppingUtilMethods.INSTANCE.Error(ItemDetailsShoppingActivity.this, getString(R.string.some_thing_error));
                        }
                    }
                });
            }
        } else {
            ApiShoppingUtilMethods.INSTANCE.NetworkError(ItemDetailsShoppingActivity.this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CART_DELETE_REQUEST && resultCode == RESULT_OK) {
            isInCart = 0;
        }
        if (requestCode == CHANGE_ADDRESS_REQUEST && resultCode == RESULT_OK) {
            HitApi();
        }
        if (requestCode == LOGIN_ACTIVITY_RESULT && resultCode == RESULT_OK) {


            isScrollBottomSimilarApiCalled = false;
            isScrollBottomRecentApiCalled = false;
            HitApi();
        }
    }

    void shareLink() {
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody;

            shareBody = titleText.getText().toString() + "\n" + affiliateShareLinkStr;


            String shareSub = ApplicationConstant.INSTANCE.baseUrl;
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via " + getString(R.string.app_name) + "..."));
        } catch (Exception e) {

        }
    }

    private void initToolBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        ImageView logoIv = findViewById(R.id.image);
        setSupportActionBar(mToolbar);
        /*if (ApiShoppingUtilMethods.INSTANCE.mLogo != 0) {

            if (logoIv != null) {
                setTitle("");
                logoIv.setVisibility(View.VISIBLE);
                logoIv.setImageResource(ApiShoppingUtilMethods.INSTANCE.mLogo);
            } else {
                logoIv.setVisibility(View.GONE);
                setTitle("Details");
            }

        } else {
            logoIv.setVisibility(View.GONE);
            setTitle("Details");
        }*/
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               /* Intent ii = new Intent(getApplicationContext(), DashboardActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);*/
            }
        });
    }


    private void addScrollEfectInbottomBtn() {
        scrollBtnView.getLocationInWindow(locationScrollBtnView);
        bottomBtnView.getLocationInWindow(locationBottomBtnView);

        //bottomBtnView.getY();
        if (locationScrollBtnView[1] >= locationBottomBtnView[1]) {
            elevationView.setVisibility(View.VISIBLE);
            bottomBtnView.setVisibility(View.VISIBLE);
        } else {
            elevationView.setVisibility(View.GONE);
            bottomBtnView.setVisibility(View.GONE);
        }

        // Log.e("scroll pos", locationScrollBtnView[1] + " " + locationBottomBtnView[1]);
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-11-17 17:07:26 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        TextView textActionShare = findViewById(R.id.text_action_share);
        TextView shareMsgTv = findViewById(R.id.shareMsgTv);
        TextView directIncomeText = findViewById(R.id.directIncomeText);
        vectorLike = findViewById(R.id.vector_like);
        vectorLike.setUnlikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(this, CommunityMaterial.Icon.cmd_heart).colorRes(R.color.black).sizeDp(40).toBitmap()));
        //shown when the button is liked!
        vectorLike.setLikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(this, CommunityMaterial.Icon.cmd_heart).colorRes(R.color.red).sizeDp(40).toBitmap()));


        scrollbar = findViewById(R.id.scrollbar);
        scrollbar.setVisibility(View.GONE);
        banner = findViewById(R.id.banner);
        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        titleText = findViewById(R.id.titleText);
        priceText = findViewById(R.id.priceText);
        mrpText = findViewById(R.id.mrpText);
        discountView = findViewById(R.id.discountView);
        discountText = findViewById(R.id.discountText);
        availabilityText = findViewById(R.id.availabilityText);
        layoutActionShare = findViewById(R.id.layout_action_share);
        similarViewAllBtn = findViewById(R.id.similarViewAllBtn);
        recentViewAllBtn = findViewById(R.id.recentViewAllBtn);
        addressType = findViewById(R.id.addressType);
        pinCodeAddress = findViewById(R.id.pinCodeAddress);
        deliveryAddressTitle = findViewById(R.id.deliveryAddressTitle);
        noDeliveryAddress = findViewById(R.id.noDeliveryAddress);
        userName = findViewById(R.id.userName);
        address = findViewById(R.id.address);
        addressBtn = findViewById(R.id.addressBtn);
        addressContainer = findViewById(R.id.addressContainer);
        shippindDeliveryDetailView = findViewById(R.id.shippindDetailView);
        shippingTime = findViewById(R.id.shippingTime);
        shippingLine = findViewById(R.id.shippingLine);
        shippingCharge = findViewById(R.id.shippingCharge);
        shippingDetailsView = findViewById(R.id.shippingDetails);
        delveryAddressView = findViewById(R.id.delveryAddressView);
        likeOverView = findViewById(R.id.likeOverView);
        featureCategoryView = findViewById(R.id.featureCategoryView);
        featureCategoryRecyclerView = findViewById(R.id.featureCategoryRecyclerView);
        featureView = findViewById(R.id.featureView);
        productCodeView = findViewById(R.id.productCodeView);
        productCodeTitleText = findViewById(R.id.productCodeTitleText);
        productCodeValueText = findViewById(R.id.productCodeValueText);
        featureRecyclerView = findViewById(R.id.featureRecyclerView);
        detailView = findViewById(R.id.detailView);
        descriptionView = findViewById(R.id.descriptionView);
        descriptionWebView = findViewById(R.id.descriptionWebView);
        additionalDescriptionView = findViewById(R.id.additionalDescriptionView);
        additionalDescriptionWebView = findViewById(R.id.additionalDescriptionWebView);
        scrollBtnView = findViewById(R.id.scrollBottomBtnView);
        actionScrollCart = findViewById(R.id.action_cart);
        actionScrollBuy = findViewById(R.id.action_buy);
        relatedProductView = findViewById(R.id.relatedProductView);
        recentViewProductView = findViewById(R.id.recentViewProductView);
        relatedProductRecyclerView = findViewById(R.id.relatedProductRecyclerView);
        recentViewProductRecyclerView = findViewById(R.id.recentViewProductRecyclerView);
        elevationView = findViewById(R.id.elevationView);
        bottomBtnView = findViewById(R.id.bottomBtnView);
        textActionCart = findViewById(R.id.text_action_cart);
        textActionBuy = findViewById(R.id.text_action_buy);
        noNetworkView = findViewById(R.id.noNetworkView);
        noDataView = findViewById(R.id.noDataView);
        retryBtn = findViewById(R.id.retryBtn);
        loaderView = findViewById(R.id.loaderView);
        WebSettings webSettings = descriptionWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultFontSize(14);
        descriptionWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        descriptionWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        descriptionWebView.setLongClickable(false);
        descriptionWebView.setHapticFeedbackEnabled(false);

        WebSettings webSettingsAdditional = additionalDescriptionWebView.getSettings();
        webSettingsAdditional.setJavaScriptEnabled(true);
        webSettingsAdditional.setDefaultFontSize(14);
        additionalDescriptionWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        additionalDescriptionWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        additionalDescriptionWebView.setLongClickable(false);
        additionalDescriptionWebView.setHapticFeedbackEnabled(false);
    }

    private void HitApi() {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            ApiShoppingUtilMethods.INSTANCE.GetProductInfo(this, productId, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    mProductInfoDetailsResponse = (ProductInfoDetailsResponse) response;
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scrollbar.setVisibility(View.VISIBLE);
                            ShowContent();
                        }
                    });
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    scrollbar.setVisibility(View.GONE);
                    if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                        noNetworkView.setVisibility(View.VISIBLE);
                        noDataView.setVisibility(View.GONE);
                    } else {
                        noNetworkView.setVisibility(View.GONE);
                        noDataView.setVisibility(View.VISIBLE);
                    }
                }
            });
            /*UtilMethods.INSTANCE.ProductDetail(this, userId, CatId, new UtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scrollbar.setVisibility(View.VISIBLE);
                            ShowContent((ProductDetailResponse) response);
                        }
                    });

                }

                @Override
                public void onError(int errorCode, String msg) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    scrollbar.setVisibility(View.GONE);
                    if (errorCode == UtilMethods.INSTANCE.ERRORR_NETWORK) {
                        noNetworkView.setVisibility(View.VISIBLE);
                        noDataView.setVisibility(View.GONE);
                    } else {
                        noNetworkView.setVisibility(View.GONE);
                        noDataView.setVisibility(View.VISIBLE);
                    }
                }
            });*/

        } else {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);

            /* UtilMethods.INSTANCE.Error(this, getResources().getString(R.string.err_msg_network));*/
        }
    }


    private void HitApiRecentProduct() {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loaderView.setVisibility(View.VISIBLE);
            ApiShoppingUtilMethods.INSTANCE.GetRecentViewProductInfo(this, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    final MainCategoryWiseProductResponse mMainCategoryWiseProductResponse = (MainCategoryWiseProductResponse) response;
                    isScrollBottomRecentApiCalled = true;
                    loaderView.setVisibility(View.GONE);
                    recentViewProductView.setVisibility(View.VISIBLE);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (mMainCategoryWiseProductResponse != null && mMainCategoryWiseProductResponse.getMainCategoryWiseProductData() != null && mMainCategoryWiseProductResponse.getMainCategoryWiseProductData().size() > 0) {
                                recentViewProductListData = mMainCategoryWiseProductResponse.getMainCategoryWiseProductData();

                                if (recentViewProductListData.size() > 6) {
                                    recentViewAllBtn.setVisibility(View.VISIBLE);
                                } else {
                                    recentViewAllBtn.setVisibility(View.GONE);
                                }

                                setAdditionalBottomData(recentViewProductListData, "Grid_Horizonatl", recentViewProductRecyclerView);
                            } else {
                                recentViewProductView.setVisibility(View.GONE);
                            }
                        }
                    });
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    loaderView.setVisibility(View.GONE);
                    isScrollBottomRecentApiCalled = ErrorType != ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK;
                    recentViewProductView.setVisibility(View.GONE);
                }
            });


        } else {
            isScrollBottomRecentApiCalled = false;
            recentViewProductView.setVisibility(View.GONE);
            loaderView.setVisibility(View.GONE);
        }
    }

    private void HitApiSimilarProduct() {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loaderView.setVisibility(View.VISIBLE);
            ApiShoppingUtilMethods.INSTANCE.GetSimilarProductInfo(this, productId, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    final MainCategoryWiseProductResponse mMainCategoryWiseProductResponse = (MainCategoryWiseProductResponse) response;
                    isScrollBottomSimilarApiCalled = true;
                    loaderView.setVisibility(View.GONE);
                    relatedProductView.setVisibility(View.VISIBLE);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if (mMainCategoryWiseProductResponse != null && mMainCategoryWiseProductResponse.getMainCategoryWiseProductData() != null && mMainCategoryWiseProductResponse.getMainCategoryWiseProductData().size() > 0) {
                                similarProductListData = mMainCategoryWiseProductResponse.getMainCategoryWiseProductData();
                                if (similarProductListData.size() > 6) {
                                    similarViewAllBtn.setVisibility(View.VISIBLE);
                                } else {
                                    similarViewAllBtn.setVisibility(View.GONE);
                                }

                                setAdditionalBottomData(similarProductListData, "Grid_Vertical", relatedProductRecyclerView);
                            } else {
                                relatedProductView.setVisibility(View.GONE);
                            }
                        }
                    });
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    loaderView.setVisibility(View.GONE);
                    isScrollBottomSimilarApiCalled = ErrorType != ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK;
                    relatedProductView.setVisibility(View.GONE);
                }
            });


        } else {
            isScrollBottomSimilarApiCalled = false;
            relatedProductView.setVisibility(View.GONE);
            loaderView.setVisibility(View.GONE);


        }
    }

    void setAdditionalBottomData(ArrayList<DashboardProductListData> listData, String ViewType, RecyclerView mRecyclerView) {


        if (ViewType.equalsIgnoreCase("Grid_Vertical")) {
            mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(4, RecyclerViewItemDecoration.GRID, false));
            mRecyclerView.setLayoutManager(new GridLayoutManager(ItemDetailsShoppingActivity.this, 2));
            DashboardGridShoppingAdapter dashboardGridAdapter = new DashboardGridShoppingAdapter(ItemDetailsShoppingActivity.this, listData.size() > 6 ? listData.subList(0, 6) : listData, R.layout.adapter_shopping_grid_item_layout_vertical);
            mRecyclerView.setAdapter(dashboardGridAdapter);


        } else if (ViewType.equalsIgnoreCase("Grid_Horizontal")) {
            mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(4, RecyclerViewItemDecoration.HORIZONTAL, false));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ItemDetailsShoppingActivity.this, LinearLayoutManager.HORIZONTAL, false));
            DashboardGridShoppingAdapter dashboardGridAdapter = new DashboardGridShoppingAdapter(ItemDetailsShoppingActivity.this, listData.size() > 6 ? listData.subList(0, 6) : listData, R.layout.adapter_shopping_grid_item_layout_horizontal);
            mRecyclerView.setAdapter(dashboardGridAdapter);

        } else {
            mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(4, RecyclerViewItemDecoration.HORIZONTAL, true));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ItemDetailsShoppingActivity.this, LinearLayoutManager.HORIZONTAL, false));
            DashboardGridShoppingAdapter dashboardGridAdapter = new DashboardGridShoppingAdapter(ItemDetailsShoppingActivity.this, listData.size() > 6 ? listData.subList(0, 6) : listData, R.layout.adapter_shopping_grid_item_layout);
            mRecyclerView.setAdapter(dashboardGridAdapter);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
        setupBadge();
    }


    private void ShowContent() {

       /* simillarProductList.clear();
        simillarProductList.addAll(mProductInfoDetailsResponse.getSimillarProductAll());*/
        String title = mProductInfoDetailsResponse.getProductDetail().getTitle();
        productName = mProductInfoDetailsResponse.getProductDetail().getTitle();
        String price = ApiShoppingUtilMethods.INSTANCE.formatedAmount(mProductInfoDetailsResponse.getProductDetail().getSellingPrice() + "");
        String mrp = ApiShoppingUtilMethods.INSTANCE.formatedAmount(mProductInfoDetailsResponse.getProductDetail().getMrp() + "");
        String description = mProductInfoDetailsResponse.getProductDetail().getDescription();
        String additionalDescription = mProductInfoDetailsResponse.getProductDetail().getAdditionalDescription();
        availibility = mProductInfoDetailsResponse.getProductDetail().getRemainingQuantity();

        String productCode = mProductInfoDetailsResponse.getProductDetail().getProductCode();
        vectorLike.setLiked(mProductInfoDetailsResponse.getProductDetail().getIsWishlistAdded() == 1);
        shareLinkStr = mProductInfoDetailsResponse.getProductDetail().getShareLink();
        affiliateShareLinkStr = mProductInfoDetailsResponse.getProductDetail().getAffiliateShareLink();
        isInCart = mProductInfoDetailsResponse.getProductDetail().getIsCartAdded();
        titleText.setText(title);
        //priceText.setText("\u20B9 " + price.replace(".00",""));
        priceText.setText("\u20B9 " + price.replace(".00", ""));
        if (mrp != null && mrp.length() > 0) {
            // mrpText.setText("\u20B9 " + mrp.replace(".00",""));
            //mrpText.setPaintFlags(mrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mrpText.setText("MRP: \u20B9 " + mrp.replace(".00", ""));
            mrpText.setPaintFlags(mrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            mrpText.setVisibility(View.GONE);
        }
        if (mProductInfoDetailsResponse.getProductDetail().getDiscount() != 0) {
            if (mProductInfoDetailsResponse.getProductDetail().isDiscountType()) {
                discountText.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmount(mProductInfoDetailsResponse.getProductDetail().getDiscount() + "") + "% Off");
            } else {
                discountText.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees((mProductInfoDetailsResponse.getProductDetail().getDiscount() /** operator.getQuantity()*/) + "") + " Off");
            }

            /*String discount = ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mProductInfoDetailsResponse.getProductDetail().getDiscount() + "");
            discountText.setText(discount + " off");*/
        } else {
            discountText.setVisibility(View.GONE);
            discountView.setVisibility(View.GONE);
            mrpText.setVisibility(View.GONE);
        }

        detailView.setVisibility(View.GONE);
        if (description != null && description.length() > 0) {
            descriptionWebView.loadData(description, "text/html", "UTF-8");
            detailView.setVisibility(View.VISIBLE);
            //descriptionText.setText(Html.fromHtml(description));
        } else {
            descriptionView.setVisibility(View.GONE);
        }
        if (additionalDescription != null && additionalDescription.length() > 0) {
            additionalDescriptionWebView.loadData(additionalDescription, "text/html", "UTF-8");
            detailView.setVisibility(View.VISIBLE);
            //additionDescriptionText.setText(Html.fromHtml(additionalDescription));
        } else {
            additionalDescriptionView.setVisibility(View.GONE);
        }

        imageListItems.clear();
        imageListItems.add(new ProductDetailImageListItem(mProductInfoDetailsResponse.getProductDetail().getFrontImage1200(), mProductInfoDetailsResponse.getProductDetail().getFrontImage100(), mProductInfoDetailsResponse.getProductDetail().getFrontImage(), true));
        imageListItems.add(new ProductDetailImageListItem(mProductInfoDetailsResponse.getProductDetail().getSideImage1200(), mProductInfoDetailsResponse.getProductDetail().getSideImage100(), mProductInfoDetailsResponse.getProductDetail().getSideImage(), false));
        imageListItems.add(new ProductDetailImageListItem(mProductInfoDetailsResponse.getProductDetail().getBackImage1200(), mProductInfoDetailsResponse.getProductDetail().getBackImage100(), mProductInfoDetailsResponse.getProductDetail().getBackImage(), false));

        imageListAdapter.notifyDataSetChanged();
        if (imageListItems.size() > 0) {
            setImage(mProductInfoDetailsResponse.getProductDetail().getFrontImage100());
        }


        if (availibility == -1 || availibility == 0) {
            availabilityText.setText("Out of Stock");
            availabilityText.setTextColor(getResources().getColor(R.color.red));
            textActionBuy.setBackgroundColor(getResources().getColor(R.color.red));
            textActionBuy.setText("OUT OF STOCK");
            textActionBuy.setClickable(false);
            actionScrollBuy.setBackgroundColor(getResources().getColor(R.color.red));
            actionScrollBuy.setText("OUT OF STOCK");
            actionScrollBuy.setClickable(false);

            actionScrollCart.setVisibility(View.GONE);
            textActionCart.setVisibility(View.GONE);
            bottomBtnView.setVisibility(View.GONE);
            bottomBtnView.setVisibility(View.VISIBLE);

        } else {
            if (availibility <= 5) {
                availabilityText.setTextColor(getResources().getColor(R.color.red));
            }
            availabilityText.setText(availibility + " Unit left");
        }

        featureView.setVisibility(View.GONE);
        if (productCode != null && !productCode.isEmpty()) {
            productCodeTitleText.setText("Product Code :");
            productCodeValueText.setText(productCode);
            featureView.setVisibility(View.VISIBLE);
        } else {
            productCodeView.setVisibility(View.GONE);
        }

        //set feature list data
        if (mProductInfoDetailsResponse.getProductDetail().getSpecificFeatures() != null && mProductInfoDetailsResponse.getProductDetail().getSpecificFeatures().size() > 0) {
            featureListAdapter = new FeatureListShoppingAdapter(ItemDetailsShoppingActivity.this, mProductInfoDetailsResponse.getProductDetail().getSpecificFeatures());
            featureRecyclerView.setAdapter(featureListAdapter);
            featureView.setVisibility(View.VISIBLE);
        }

        AddressData mAddressData = mProductInfoDetailsResponse.getProductDetail().getAddressData();
        if (mAddressData != null) {
            shippindDeliveryDetailView.setVisibility(View.VISIBLE);
            delveryAddressView.setVisibility(View.VISIBLE);
            addressContainer.setVisibility(View.VISIBLE);
            pinCodeAddress.setVisibility(View.GONE);
            noDeliveryAddress.setVisibility(View.GONE);
            addressBtn.setVisibility(View.VISIBLE);
            setAddressData(mAddressData);

        } else {
            shippindDeliveryDetailView.setVisibility(View.GONE);
            delveryAddressView.setVisibility(View.GONE);

        }


        if (mProductInfoDetailsResponse.getProductDetail().getExpectedDeliveryDate() != null) {
            shippindDeliveryDetailView.setVisibility(View.VISIBLE);
            shippingDetailsView.setVisibility(View.VISIBLE);
            String time = mProductInfoDetailsResponse.getProductDetail().getExpectedDeliveryDate();
            shippingTime.setText(ApiShoppingUtilMethods.INSTANCE.formatedDate(time + ""));
        } else {
            shippingTime.setVisibility(View.GONE);
        }


        shippingLine.setVisibility(shippingTime.getVisibility());

        if (mProductInfoDetailsResponse.getProductDetail().getShippingCharge() != 0) {
            shippingCharge.setText(getString(R.string.rupiya) + " " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(mProductInfoDetailsResponse.getProductDetail().getShippingCharge() + ""));
        } else {
            shippingCharge.setText("Free");
        }


        String value = "";
        if (mProductInfoDetailsResponse.getProductDetail().getFilters() != null && mProductInfoDetailsResponse.getProductDetail().getFilters().size() > 0) {

            FeatureCategoryListShoppingAdapter mFeatureCategoryListAdapter = new FeatureCategoryListShoppingAdapter(this, mProductInfoDetailsResponse.getProductDetail().getFilters());
            featureCategoryRecyclerView.setAdapter(mFeatureCategoryListAdapter);
        } else {
            featureCategoryView.setVisibility(View.GONE);
        }
    }


    void setAddressData(AddressData mAddressData) {
        if (mAddressData.getType() != null && !mAddressData.getType().isEmpty()) {
            addressType.setVisibility(View.VISIBLE);
            addressType.setText(mAddressData.getType() + "");
        } else {
            addressType.setVisibility(View.GONE);
        }

        userName.setText(mAddressData.getName() + "");
        int addressId = mAddressData.getAddressId();
        String addressStr = "";
        if (!mAddressData.getAddress().isEmpty()) {
            addressStr = mAddressData.getAddress();
        }

        if (!mAddressData.getArea().isEmpty() && !addressStr.contains(mAddressData.getArea() + "")) {
            addressStr = (!addressStr.isEmpty() ? addressStr + ", " : "") + mAddressData.getArea();
        }
        if (!mAddressData.getLandmark().isEmpty() && !addressStr.contains(mAddressData.getLandmark() + "")) {
            addressStr = (!addressStr.isEmpty() ? addressStr + ", " : "") + mAddressData.getLandmark();
        }
        if (!mAddressData.getCity().isEmpty() && !addressStr.contains(mAddressData.getCity() + "")) {
            addressStr = (!addressStr.isEmpty() ? addressStr + ", " : "") + mAddressData.getCity();
        }
        if (!mAddressData.getState().isEmpty() && !addressStr.contains(mAddressData.getState() + "")) {
            addressStr = (!addressStr.isEmpty() ? addressStr + ", " : "") + mAddressData.getState();
        }
        if (!mAddressData.getPinCode().isEmpty() && !addressStr.contains(mAddressData.getPinCode() + "")) {
            addressStr = (!addressStr.isEmpty() ? addressStr + "- " : "") + mAddressData.getPinCode();

        }

        if (mAddressData.getPinCode() != null && !mAddressData.getPinCode().isEmpty()) {
            CheckDeliveryApi(mAddressData.getPinCode() + "", false);
        }

        if (addressStr != null && !addressStr.isEmpty()) {

            addressContainer.setVisibility(View.VISIBLE);
            address.setVisibility(View.VISIBLE);
            address.setText(addressStr);
        } else {
            addressContainer.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
        }
    }

    private void setImage(String url) {
        Glide.with(ItemDetailsShoppingActivity.this)
                .load(url)
                .thumbnail(0.8f)
                .apply(requestOptions)
                .into(banner);
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(false);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    private void setupBadge() {
        mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void cartUpdate(int count) {
        mCartItemCount = count;
        setupBadge();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            startActivity(new Intent(ItemDetailsShoppingActivity.this, SearchResultShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (id == R.id.action_cart) {

            Intent intent = new Intent(ItemDetailsShoppingActivity.this, CartDetailShoppingActivity.class);
            intent.putExtra("PRODUCT_ID", productId);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, CART_DELETE_REQUEST);

        }

        return super.onOptionsItemSelected(item);
    }


    public void AddRemoveWishList(final boolean isWishList, String posId) {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {


            ApiShoppingUtilMethods.INSTANCE.WishListAddRemove(ItemDetailsShoppingActivity.this, isWishList, posId, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(Object response) {

                    Toast.makeText(ItemDetailsShoppingActivity.this, ((BasicApiResponse) response).getMessage() + "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    ApiShoppingUtilMethods.INSTANCE.Error(ItemDetailsShoppingActivity.this, msg);
                }
            });

        /*    UtilMethods.INSTANCE.AddRemoveWishList(ItemDetailsActivity.this, isWishList, userId, posId, new UtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(Object response) {

                    Toast.makeText(ItemDetailsActivity.this, ((BasicResponse) response).getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    mCustomAlertDialog.Error(true, msg);
                }
            });
*/

        } else {
            ApiShoppingUtilMethods.INSTANCE.NetworkError(this);

        }
    }


}
