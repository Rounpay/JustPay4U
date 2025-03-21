package com.solution.app.justpay4u.Fintech.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dreamseller.imagepicker.ImagePicker;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MultiImageUploadActivity extends AppCompatActivity {

    Bitmap firstImageBitmap, secondImageBitmap, previewImageBitmap;
    String firstImageUriPath, secondImageUriPath, previewImageUriPath;
    int imageType;
    RequestOptions requestOptions;
    CustomLoader mLoader;
    private LinearLayout firstImageViewCenterView;
    private AppCompatImageView firstImage;
    private LinearLayout secondImageViewCenterView;
    private AppCompatImageView secondImage;
    private RelativeLayout previewImageView;
    private AppCompatImageView previewImage;
    private AppCompatTextView closeBtn, firstImageNotice, secodImageNotice;
    private Button submitBtn;
    private ImagePicker imagePicker;
    private boolean isPreviewComplete;
    private String title;
    private int INTENT_PERMISSION_IMAGE = 5971;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_multi_image_upload);
            findViews();
            mLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        });


    }

    private void findViews() {
        title=getIntent().getStringExtra("Title");
        setTitle("Upload " + ((title != null && !title.isEmpty()) ? title : "") + " Image");
        previewImageUriPath = getExternalCacheDir().getAbsolutePath() + "/" + title.replaceAll(" ", "") + "Image.jpg";

        imagePicker = new ImagePicker(this, null, imageUri -> {
            Uri imgUri = imageUri;
            previewImageBitmap = null;
            if (imageType == 1) {
                firstImageUriPath = imgUri.getPath();
            } else {
                secondImageUriPath = imgUri.getPath();
            }

            getBitmap(imageUri);
            //uploadDocApi(new File(imgUri.getPath()));
        }).setWithImageCrop();


        firstImageViewCenterView = (LinearLayout) findViewById(R.id.firstImageViewCenterView);
        firstImage = (AppCompatImageView) findViewById(R.id.firstImage);
        secondImageViewCenterView = (LinearLayout) findViewById(R.id.secondImageViewCenterView);
        secondImage = (AppCompatImageView) findViewById(R.id.secondImage);
        previewImageView = (RelativeLayout) findViewById(R.id.previewImageView);
        previewImage = (AppCompatImageView) findViewById(R.id.previewImage);
        closeBtn = (AppCompatTextView) findViewById(R.id.closeBtn);
        firstImageNotice = (AppCompatTextView) findViewById(R.id.firstImageNotice);
        secodImageNotice = (AppCompatTextView) findViewById(R.id.secondImageNotice);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        firstImageNotice.setText(getString(R.string.upload_multidoc_notice, "first", title));
        secodImageNotice.setText(getString(R.string.upload_multidoc_notice, "second", title));


        firstImage.setOnClickListener(v -> {
            imageType = 1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(this, PermissionActivity.class),
                            INTENT_PERMISSION_IMAGE);

                } else {
                    imagePicker.choosePictureWithoutPermission(true, false);
                }
            } else {
                imagePicker.choosePictureWithoutPermission(true, false);
            }
        });

        secondImage.setOnClickListener(v -> {
            imageType = 2;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(this, PermissionActivity.class),
                            INTENT_PERMISSION_IMAGE);

                } else {
                    imagePicker.choosePictureWithoutPermission(true, false);
                }
            } else {
                imagePicker.choosePictureWithoutPermission(true, false);
            }
        });

        closeBtn.setOnClickListener(v -> {
            isPreviewComplete = false;
            submitBtn.setText("Preview");
            previewImageView.setVisibility(View.GONE);
        });

        submitBtn.setOnClickListener(v -> {
            if (isPreviewComplete) {

                if (previewImageBitmap != null && previewImageBitmap.getWidth() != 0 && previewImageBitmap.getHeight() != 0) {
                    saveBitmap();
                } else {
                    Toast.makeText(this, "Please Upload Both Image", Toast.LENGTH_SHORT).show();
                }


            } else {
                if (firstImageUriPath == null || firstImageUriPath.isEmpty()) {
                    Toast.makeText(this, "Please Choose First Image", Toast.LENGTH_SHORT).show();
                } else if (secondImageUriPath == null || secondImageUriPath.isEmpty()) {
                    Toast.makeText(this, "Please Choose Second Image", Toast.LENGTH_SHORT).show();
                } else {

                    submitBtn.setText("Submit Document");
                    previewImageView.setVisibility(View.VISIBLE);
                    if (previewImageBitmap != null && previewImageBitmap.getWidth() != 0 && previewImageBitmap.getHeight() != 0) {
                        previewImage.setImageBitmap(previewImageBitmap);
                        isPreviewComplete = true;
                    } else {
                        mergeBitmap(firstImageBitmap, secondImageBitmap);
                    }

                }

            }
        });
    }

    void getBitmap(Uri path) {

        Glide.with(this)
                .asBitmap()
                .load(path)
                .apply(requestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        if (resource != null) {
                            if (imageType == 1) {
                                firstImageBitmap = resource;
                                firstImageViewCenterView.setVisibility(View.GONE);
                                firstImage.setImageBitmap(resource);
                                //setRoundedBitmap(resource, firstImage);
                            } else {
                                secondImageBitmap = resource;
                                secondImageViewCenterView.setVisibility(View.GONE);
                                secondImage.setImageBitmap(resource);
                                //setRoundedBitmap(resource, secondImage);
                            }
                        }
                    }
                });
    }

    public void mergeBitmap(Bitmap image1, Bitmap image2) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom


        int width, height = 0;

        if (image1.getWidth() > image2.getWidth()) {
            width = image1.getWidth();
        } else {
            width = image2.getWidth();
        }
        height = image1.getHeight() + image2.getHeight();

        previewImageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(previewImageBitmap);


        if (image1.getWidth() < comboImage.getWidth()) {
            comboImage.drawBitmap(image1, (comboImage.getWidth() - image1.getWidth()) / 2, 0f, null);
        } else {
            comboImage.drawBitmap(image1, 0f, 0f, null);
        }
        if (image2.getWidth() < comboImage.getWidth()) {
            comboImage.drawBitmap(image2, (comboImage.getWidth() - image2.getWidth()) / 2, image1.getHeight(), null);
        } else {
            comboImage.drawBitmap(image2, 0f, image1.getHeight(), null);
        }


        previewImage.setImageBitmap(previewImageBitmap);
        isPreviewComplete = true;

    }

    public void saveBitmap() {
        // Create a media file name
        mLoader.show();
        new Handler().post(() -> {
            File imagePath = new File(previewImageUriPath);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(imagePath);
                previewImageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                fos.flush();
                fos.close();
                Log.v("first", "second");
                Intent intent = new Intent();
                intent.putExtra("DocPath", previewImageUriPath);
                setResult(RESULT_OK, intent);
                mLoader.dismiss();
                finish();
            } catch (FileNotFoundException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("GREC", e.getMessage(), e);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("photopath", ImagePicker.currentCameraFileName);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("photopath") && ImagePicker.currentCameraFileName.length() < 5) {
                ImagePicker.currentCameraFileName = savedInstanceState.getString("photopath");
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_PERMISSION_IMAGE && resultCode == Activity.RESULT_OK) {

            imagePicker.choosePictureWithoutPermission(true, false);
        } else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }

        /* Toast.makeText(this,"Unable to capture image, please try again",Toast.LENGTH_LONG).show();*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
