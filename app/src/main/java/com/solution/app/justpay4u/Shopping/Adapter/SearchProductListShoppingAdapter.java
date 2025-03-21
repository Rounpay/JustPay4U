package com.solution.app.justpay4u.Shopping.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.Api.Shopping.Response.AddToCartResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.CartDetailShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.ItemDetailsShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.SearchProductShoppingActivity;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class SearchProductListShoppingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_LOADING = 1;
    private final RequestOptions optionsRectangleImage;
    private final ArrayList<DashboardProductListData> mRelatedProducts;
    private final Activity mContext;
    CustomLoader loader;
    private int mCartItemCount;

    public SearchProductListShoppingAdapter(Activity mContext, ArrayList<DashboardProductListData> mRelatedProducts) {
        this.mRelatedProducts = mRelatedProducts;
        this.mContext = mContext;

        loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);

        optionsRectangleImage = new RequestOptions()
                .fitCenter()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .timeout(65000)
                .placeholder(R.drawable.placeholder_square)
                .error(R.drawable.defaultlogo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_loader_view, parent, false);
            return new LoadingViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_similar_product, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        final DashboardProductListData operator = mRelatedProducts.get(position);
        if (viewHolder instanceof MyViewHolder) {
            try {
                ((MyViewHolder) viewHolder).tvOriginalPrice.setText("\u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(operator.getSellingPrice() + ""));


                if (operator.getDiscount() == 0 || operator.getDiscount() == 0.00) {
                    ((MyViewHolder) viewHolder).discount_label.setVisibility(View.GONE);
                    ((MyViewHolder) viewHolder).tvMRP.setVisibility(View.GONE);
                } else {
                    ((MyViewHolder) viewHolder).discount_label.setVisibility(View.VISIBLE);
                    ((MyViewHolder) viewHolder).tvMRP.setVisibility(View.VISIBLE);

                    ((MyViewHolder) viewHolder).tvMRP.setText("MRP: \u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(operator.getMrp() + ""));
                    ((MyViewHolder) viewHolder).tvMRP.setPaintFlags(((MyViewHolder) viewHolder).tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    if (operator.isDiscountType()) {
                        ((MyViewHolder) viewHolder).discount_label.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmount(operator.getDiscount() + "") + "% Off");
                    } else {
                        ((MyViewHolder) viewHolder).discount_label.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees((operator.getDiscount() ) + "") + " Off");
                    }

                    /*((MyViewHolder) viewHolder).discount_label.setText("\u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(operator.getDiscount() + "") + " Off");*/
                }
                ((MyViewHolder) viewHolder).tvDescription.setText(operator.getProductName() != null && !operator.getProductName().isEmpty() ? operator.getProductName() : operator.getTitle());

                Glide.with(mContext)
                        .load(operator.getFrontImage())
                        .thumbnail(0.6f)
                        .apply(optionsRectangleImage)
                        .into(((MyViewHolder) viewHolder).ivProduct);


                ((MyViewHolder) viewHolder).itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ItemDetailsShoppingActivity.class);
                    intent.putExtra("ProductId", operator.getPosId() + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                });

                /*if (isLogin == 1) {
                    ((MyViewHolder) viewHolder).shareIcon.setVisibility(View.GONE);
                    ((MyViewHolder) viewHolder).moreIcon.setVisibility(View.VISIBLE);
                    ((MyViewHolder) viewHolder).moreIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPopupDialog(v, operator);
                        }
                    });
                } else {*/
                    /*((MyViewHolder) viewHolder).shareIcon.setVisibility(View.VISIBLE);
                    ((MyViewHolder) viewHolder).moreIcon.setVisibility(View.GONE);
                    */
                ((MyViewHolder) viewHolder).shareIcon.setOnClickListener(v -> shareLink(operator.getProductName() != null && !operator.getProductName().isEmpty() ? operator.getProductName() : operator.getTitle(), operator.getShareLink()));
                /*}*/


            } catch (Exception e) {

            }
            //   holder.tvDate.setText(operator.getValue());


            // holder.vectorAddToCard.setLikeDrawable(mContext.getResources().getDrawable(R.drawable.ic_shopping_cart_blue_24dp));
            // holder.vectorAddToCard.setUnlikeDrawable(mContext.getResources().getDrawable(R.drawable.ic_shopping_cart_black_24dp));


            ((MyViewHolder) viewHolder).vectorAddToCard.setOnAnimationEndListener(likeButton -> ((MyViewHolder) viewHolder).vectorAddToCard.setLiked(false));
            ((MyViewHolder) viewHolder).vectorAddToCard.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
               /* if (isLogin == 0) {
                    isLogin = pref.getInt(mContext.ApplicationConstant.INSTANCE.IsLoginPref);
                    userId = pref.get(mContext.getString(R.string.user_id));
                }*/


                    if (operator.getIsCartAdded() == 1 && ApiShoppingUtilMethods.INSTANCE.cartProductIdList.contains(operator.getPosId())) {
                        ((MyViewHolder) viewHolder).vectorAddToCard.setLiked(false);
                        Intent intent = new Intent(mContext, CartDetailShoppingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mContext.startActivity(intent);
                    } else {
                        saveCartApi(operator, ((MyViewHolder) viewHolder).vectorAddToCard);
                    }


                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    Toast.makeText(mContext, "Item removed from cart!", Toast.LENGTH_SHORT).show();
                }
            });


            usingCustomIcons(((MyViewHolder) viewHolder));
        }
    }

    @Override
    public int getItemCount() {
        return mRelatedProducts.size();
    }

    public void usingCustomIcons(MyViewHolder holder) {

        holder.vectorAddToCard.setUnlikeDrawable(new BitmapDrawable(mContext.getResources(), new IconicsDrawable(mContext, CommunityMaterial.Icon.cmd_cart).colorRes(R.color.greycon).sizeDp(40).toBitmap()));

        //shown when the button is liked!
        holder.vectorAddToCard.setLikeDrawable(new BitmapDrawable(mContext.getResources(), new IconicsDrawable(mContext, CommunityMaterial.Icon.cmd_cart).colorRes(R.color.colorPrimaryDark).sizeDp(40).toBitmap()));

        holder.vectorLike.setUnlikeDrawable(new BitmapDrawable(mContext.getResources(), new IconicsDrawable(mContext, CommunityMaterial.Icon.cmd_heart).colorRes(R.color.greycon).sizeDp(40).toBitmap()));

        //shown when the button is liked!
        holder.vectorLike.setLikeDrawable(new BitmapDrawable(mContext.getResources(), new IconicsDrawable(mContext, CommunityMaterial.Icon.cmd_heart).colorRes(R.color.red).sizeDp(40).toBitmap()));

    }

    /* @SuppressLint("RestrictedApi")
     private void showPopupDialog(View actionView, final DashboardProductListData operator) {
         Context wrapper = new ContextThemeWrapper(mContext, R.style.CustomPopupTheme);
         PopupMenu popupMenu = new PopupMenu(wrapper, actionView);

         popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem menuItem) {
                 if (menuItem.getItemId() == R.id.share) {
                     shareLink(operator.getTitle(), operator.getAffiliateShareLink());
                     return true;
                 } else if (menuItem.getItemId() == R.id.affiliate) {
                     UtilMethods.INSTANCE.openAffiliateLevelScreen(mContext, pref, operator, true, loader);
                     return true;
                 } else if (menuItem.getItemId() == R.id.level) {
                     UtilMethods.INSTANCE.openAffiliateLevelScreen(mContext, pref, operator, false, loader);
                     return true;
                 } else {
                     return false;
                 }

             }
         });

         popupMenu.inflate(R.menu.menu_more);
         if (pref.getBoolean(mContext.getResources().getString(R.string.buisness_volume_enable))) {
             popupMenu.getMenu().getItem(2).setVisible(true);
         } else {
             popupMenu.getMenu().getItem(2).setVisible(false);
         }
         // Force icons to show
         Object menuHelper;
         Class[] argTypes;
         try {
             Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
             fMenuHelper.setAccessible(true);
             menuHelper = fMenuHelper.get(popupMenu);
             argTypes = new Class[]{boolean.class};
             menuHelper.getClass().getDeclaredMethod("setForceShowIcon",
                     argTypes).invoke(menuHelper, true);
         } catch (Exception e) {

             popupMenu.show();
             return;
         }
         popupMenu.show();

     }
 */
    void shareLink(String title, String link) {
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = title + "\n" + link;
            String shareSub = ApplicationConstant.INSTANCE.baseUrl;
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            mContext.startActivity(Intent.createChooser(sharingIntent, "Share via " + mContext.getResources().getString(R.string.app_name) + "..."));
        } catch (Exception e) {

        }
    }

    void saveCartApi(final DashboardProductListData operator, LikeButton vectorAddToCard) {


        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            ApiShoppingUtilMethods.INSTANCE.AddCart(mContext, operator.getPosId() + "", "1", new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    AddToCartResponse data = (AddToCartResponse) response;
                    if (data.getStatuscode() == 1) {

                        mCartItemCount = data.getTotalItem();
                        ApiShoppingUtilMethods.INSTANCE.setCartCount(mContext, mCartItemCount);
                        ApiShoppingUtilMethods.INSTANCE.cartProductIdList.add(operator.getPosId());

                        operator.setIsCartAdded(1);
                        ((SearchProductShoppingActivity) mContext).cartUpdate(mCartItemCount);
                        Toast.makeText(mContext, "Item added to cart!", Toast.LENGTH_SHORT).show();
                    } else {
                        /*vectorAddToCard.setLiked(false);
                        Intent intent = new Intent(mContext, CartDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mContext.startActivity(intent);
                        Toast.makeText(mContext, data.getMessage() + "", Toast.LENGTH_SHORT).show();*/
                        new CustomAlertDialog(mContext).Error(data.getMessage());
                    }
                }

                @Override
                public void onError(int errorCode, String msg) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (errorCode == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                        ApiShoppingUtilMethods.INSTANCE.NetworkError(mContext);
                    } else {
                        new CustomAlertDialog(mContext).Error(mContext.getString(R.string.some_thing_error));
                    }
                }
            });

        } else {
            ApiShoppingUtilMethods.INSTANCE.NetworkError(mContext);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mRelatedProducts.get(position) == null) {
            return VIEW_TYPE_LOADING;
        } else {
            int VIEW_TYPE_ITEM = 0;
            return VIEW_TYPE_ITEM;

        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "MainActivity";
        public TextView tvOriginalPrice;
        public TextView tvMRP;
        public TextView tvDescription, discount_label;
        public ImageView ivProduct, moreIcon, shareIcon;
        public View llMainCat, likeOverView;
        public LikeButton vectorAddToCard;
        public LikeButton vectorLike;
        View itemView;
        View discountView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            tvOriginalPrice =  view.findViewById(R.id.priceText);
            tvMRP =  view.findViewById(R.id.mrpText);
            tvDescription =  view.findViewById(R.id.titleText);
            ivProduct =  view.findViewById(R.id.iv_product_image);
            llMainCat = view.findViewById(R.id.ll_main_cat);
            discountView = view.findViewById(R.id.discountView);
            likeOverView =  view.findViewById(R.id.likeOverView);
            vectorAddToCard = (LikeButton) view.findViewById(R.id.vector_cart_icon);
            vectorLike = (LikeButton) view.findViewById(R.id.vector_like);
            discount_label =  view.findViewById(R.id.discountText);
            moreIcon = view.findViewById(R.id.moreIcon);
            shareIcon = view.findViewById(R.id.shareIcon);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        //ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            // progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}