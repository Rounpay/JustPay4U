package com.solution.app.justpay4u.Util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.text.Html;

import androidx.annotation.RequiresApi;

import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;

import java.io.FileOutputStream;
import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MyPrintDocumentAdapter extends PrintDocumentAdapter {

    public PdfDocument myPdfDocument;
    public int totalpages = 1;
    Context context;
    int n = 0;
    int fontSize, fontSizeHeader;
    double relation;
    String Rechargedetail;
    AppPreferences mAppPreferences;
    private int pageHeight;
    private int pageWidth;

    public MyPrintDocumentAdapter(Context context, String Rechargedetail) {
        this.context = context;
        this.Rechargedetail = Rechargedetail;
        mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(context);
    }

    public void printDocument(String detail) {
        //   n = tv_item.getAdapter().getCount();
        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
        Rechargedetail = detail;
        String jobNameD = "Reciept Preview";
        printManager.print(jobNameD, new MyPrintDocumentAdapter(context, Rechargedetail),
                null);
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes,
                         PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback,
                         Bundle metadata) {

        myPdfDocument = new PrintedPdfDocument(context, newAttributes);
        double mPageHeight = newAttributes.getMediaSize().getHeightMils();
        mPageHeight = mPageHeight / 1000;
        mPageHeight = mPageHeight * 72;
        pageHeight = (int) mPageHeight;
        double mPageWidth = newAttributes.getMediaSize().getWidthMils();
        mPageWidth = mPageWidth / 1000;
        mPageWidth = mPageWidth * 72;
        pageWidth = (int) mPageWidth;

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        if (totalpages > 0) {
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_Preview.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Page count is zero.");
        }
    }

    @Override
    public void onWrite(final PageRange[] pageRanges,
                        final ParcelFileDescriptor destination,
                        final CancellationSignal cancellationSignal,
                        final WriteResultCallback callback) {

        for (int i = 0; i < totalpages; i++) {
            if (pageInRange(pageRanges, i)) {
                // page builder
                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create();

                PdfDocument.Page page =
                        myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                drawPage(page, i, pageWidth, pageHeight);
                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }
        callback.onWriteFinished(pageRanges);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean pageInRange(PageRange[] pageRanges, int page) {
        for (int i = 0; i < pageRanges.length; i++) {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public PdfDocument.Page drawPage(PdfDocument.Page page, int pagenumber, int pageWidth1, int pageHeight1) {

        pageWidth = pageWidth1;
        pageHeight = pageHeight1;
        String jobNameD = "Reciept Preview";
        Canvas canvas = page.getCanvas();

        pagenumber++;
        Paint paint = new Paint();
        paint.setTextScaleX(1.0f);
        relation = Math.sqrt(canvas.getWidth() * canvas.getHeight());
        relation = relation / 250;
        fontSize = context.getResources().getDimensionPixelSize(R.dimen.scoreFontSize);
        fontSizeHeader = context.getResources().getDimensionPixelSize(R.dimen.scoreFontSizeHeader);
        paint.setTextSize((float) (fontSizeHeader * relation));
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        drawHeader(canvas, paint);
        return page;
    }

    private void drawHeader(Canvas canvas, Paint paint) {

        paint.setTextAlign(Paint.Align.CENTER);
        int y = 10;
        paint.setColor(context.getResources().getColor(R.color.style_color_primary_dark));


        canvas.drawText(
                "Printed Receipt",
                (pageWidth) / 2,//190,
                (y * pageHeight) / 100,
                paint);
        paint.setTextAlign(Paint.Align.LEFT);
        y = y + 7;
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.app_full_logo);
        Bitmap b = Bitmap.createScaledBitmap(icon, (10 * pageWidth) / 100, (7 * pageWidth) / 100, false);
        canvas.drawBitmap(b, (7 * pageWidth) / 100, (y * pageHeight) / 100, null);
        y = y + 2;
        String[] str = Rechargedetail.split("\n");
        AppUserListResponse companyData = ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences);
       /* String company="MIEUX INFRACON LTD\n" +
                "Nr Nehrunagar BRTS Stand \n" +
                "S M Road, Nehrunagar, Ahmedabad - 380015 \n" +
                "Ahmedabad,Gujarat ";*/

        paint.setTextSize((float) (fontSize * relation));

        String company = companyData.getCompanyProfile().getName() + "\n" + Html.fromHtml(companyData.getCompanyProfile().getAddress());
        String[] str2 = company.split("\n");
        paint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        for (int i = 0; i < str2.length; i++) {
            canvas.drawText(
                    str2[i] + "",
                    (19 * pageWidth) / 100,
                    (y * pageHeight) / 100,
                    paint);
            y = y + 4;
        }
        y = y + 3;
        paint.setColor(context.getResources().getColor(R.color.grey));
        canvas.drawLine(20, ((y - 3) * pageHeight) / 100, pageWidth - 20, ((y - 3) * pageHeight) / 100, paint);
        y = y + 3;
        paint.setColor(context.getResources().getColor(R.color.black));
        for (int i = 0; i < str.length; i++) {
            canvas.drawText(
                    str[i] + "",
                    (19 * pageWidth) / 100,
                    (y * pageHeight) / 100,
                    paint);
            y = y + 5;
        }

    }
}




