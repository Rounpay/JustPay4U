package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.payment.PaymentInitialization;
import com.pnsol.sdk.util.UtilManager;
import com.pnsol.sdk.vo.response.ICCTransactionResponse;
import com.pnsol.sdk.vo.response.TransactionStatusResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author pradeep.arige
 * updated by vasanthi.k  on 08/12/2018
 */

public class SignatureCaptureActivity extends AppCompatActivity implements
        PaymentTransactionConstants {

    public static final String SIGNATURE = "/signature.bmp";
    private LinearLayout signatureCapture;
    private Signature mSignature;
    private Button save, clear;
    private ICCTransactionResponse iccTransactionResponse;
    private String paymentType;
    private CustomLoader loader;
    // The Handler that gets information back from the PaymentProcessThread
    @SuppressLint("HandlerLeak")
    private final Handler handlerTransactionDetail = new Handler() {

        public void handleMessage(android.os.Message msg) {
            loader.dismiss();
            if (msg.what == SUCCESS) {
                //
                List<TransactionStatusResponse> transactionStatusResponse = (List<TransactionStatusResponse>) msg.obj;

                /*Intent i = new Intent(TransactionDetailsActivity.this, PaymentDetailsActivity.class);*/
                Intent i = new Intent(SignatureCaptureActivity.this, PaymentDetailsSlipActivity.class);
                i.putExtra("txnResponse", transactionStatusResponse.get(0));
                if (iccTransactionResponse != null) {
                    i.putExtra("referenceNumber", iccTransactionResponse.getReferenceNumber());
                    i.putExtra("rrn", iccTransactionResponse.getRrn());
                }
                finish();
                startActivity(i);

            } else if (msg.what == FAIL) {
                alertMessageWithFinshError("Failed", (String) msg.obj);
                /*Toast.makeText(PaymentTransactionActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();*/
                //finish();
            } else if (msg.what == ERROR_MESSAGE) {
                alertMessageWithFinshError("Error", (String) msg.obj);
               /* Toast.makeText(PaymentTransactionActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();*/
            } else {
                if (msg.obj != null) {
                    alertMessageWithFinshError("Alert", (String) msg.obj);
                    /*Toast.makeText(PaymentTransactionActivity.this, (String) msg.obj,
                            Toast.LENGTH_LONG).show();*/
                }
            }

        }

        ;
    };
    @android.annotation.SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            loader.dismiss();
            if (msg.what == SUCCESS) {

                Toast.makeText(SignatureCaptureActivity.this, getString(R.string.success), Toast.LENGTH_LONG).show();

               /* Intent i = new Intent(SignatureCaptureActivity.this, TransactionDetailsActivity.class);
                i.putExtra("vo", iccTransactionResponse);
                finish();
                SignatureCaptureActivity.this.startActivity(i);*/

                try {
                    loader.show();
                    PaymentInitialization initialization = new PaymentInitialization(SignatureCaptureActivity.this);
                    initialization.initiateTransactionDetails(handlerTransactionDetail, iccTransactionResponse.getReferenceNumber(), null, paymentType, null, null);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            } else if (msg.what == FAIL) {
                alertMessageWithFinshError("Failed", (String) msg.obj);
               /* Toast.makeText(SignatureCaptureActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();*/

            } else if (msg.what == ERROR_MESSAGE) {
                alertMessageWithFinshError("Error", (String) msg.obj);
                /*Toast.makeText(SignatureCaptureActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();*/
            }

        }

        ;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paynear_signature_capture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        signatureCapture = (LinearLayout) findViewById(R.id.linearLayout1);
        mSignature = new Signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        signatureCapture.addView(mSignature, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        save = (Button) findViewById(R.id.save);
        save.setEnabled(false);
        clear = (Button) findViewById(R.id.clear);

        iccTransactionResponse = (ICCTransactionResponse) getIntent()
                .getSerializableExtra("vo");
        //mpaySDk 2.0
        paymentType = getIntent().getExtras().getString("paymentType");

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignature.clear();
                save.setEnabled(false);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();
                mSignature.save(signatureCapture);
                Bitmap bitmap = BitmapFactory.decodeFile(SignatureCaptureActivity.this.getFilesDir().getPath() + SIGNATURE);


                PaymentInitialization initialization = new PaymentInitialization(SignatureCaptureActivity.this);
                initialization.initiateSignatureCapture(handler, iccTransactionResponse.getReferenceNumber(), UtilManager.convertBitmapToByteArray(bitmap));


            }
        });

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void alertMessageWithFinshError(String title, String message) {

        new CustomAlertDialog(this).ErrorWithSingleBtnCallBack(title, message, "Ok", false,
                new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(SignatureCaptureActivity.this, PaynearActivity.class);
        startActivity(intent);
        finish();
    }

    public class Signature extends View {
        public static final float STORKE_WIDTH = 5f;
        public static final float HALF_STROKE_WIDTH = STORKE_WIDTH / 2;
        public static final String SIGNATURE = "signature.bmp";
        private final RectF dirtyRect = new RectF();
        private Paint paint = new Paint();
        private Path path = new Path();
        private float lastTouchX;
        private float lastTouchY;
        private Bitmap bitmap;

        public Signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STORKE_WIDTH);
        }

        public void save(View view) {
            Log.v("log_tag", "Width: " + view.getWidth());
            Log.v("log_tag", "Height: " + view.getHeight());
            if (bitmap == null) {
                try {
                    bitmap = Bitmap.createBitmap(view.getWidth(),
                            view.getHeight(), Config.ARGB_8888);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                FileOutputStream fos = openFileOutput(SIGNATURE,
                        Context.MODE_PRIVATE);
                view.draw(canvas);
                bitmap.compress(Bitmap.CompressFormat.PNG, 45, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                Log.v("log_tag", e.toString());
            } catch (IOException e) {
                Log.v("log_tag", e.toString());
            }
        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            //save.setEnabled(true);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    resetDirtyRect(eventX, eventY);
                    int historysize = event.getHistorySize();
                    for (int i = 0; i < historysize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    //Enabling save button only on move that means after drawing somthing on screen instead of enabling after simple touch
                    save.setEnabled(true);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    debug("Ignored touch event: " + event.toString());
                    break;
            }
            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
            lastTouchX = eventX;
            lastTouchY = eventY;
            return true;
        }

        private void debug(String string) {
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }
            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.bottom = Math.min(lastTouchY, eventY);
            dirtyRect.top = Math.max(lastTouchY, eventY);
        }
    }
}
