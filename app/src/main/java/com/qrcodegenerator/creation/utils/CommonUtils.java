package com.qrcodegenerator.creation.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static Bitmap encodeAsBitmap(History history, int height, int width) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(history.qrText,
                    getBarcodeFormat(history.format), height, height, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

    private static BarcodeFormat getBarcodeFormat(String format) {
        switch (format) {
            case AppConstants.QR_CODE:
                return BarcodeFormat.QR_CODE;
            case AppConstants.EAN_13:
                return BarcodeFormat.EAN_13;
            case AppConstants.EAN_8:
                return BarcodeFormat.EAN_8;
            case AppConstants.CODABAR:
                return BarcodeFormat.CODABAR;
            case AppConstants.UPC_A:
                return BarcodeFormat.UPC_A;
            case AppConstants.UPC_E:
                return BarcodeFormat.UPC_E;
            case AppConstants.UPC_EAN_EXTENSION:
                return BarcodeFormat.UPC_EAN_EXTENSION;
            case AppConstants.AZTEC:
                return BarcodeFormat.AZTEC;
            case AppConstants.CODE_39:
                return BarcodeFormat.CODE_39;
            case AppConstants.CODE_93:
                return BarcodeFormat.CODE_93;
            case AppConstants.CODE_128:
                return BarcodeFormat.CODE_128;
            case AppConstants.DATA_MATRIX:
                return BarcodeFormat.DATA_MATRIX;
            case AppConstants.ITF:
                return BarcodeFormat.ITF;
            case AppConstants.MAXICODE:
                return BarcodeFormat.MAXICODE;
            case AppConstants.PDF_417:
                return BarcodeFormat.PDF_417;
            case AppConstants.RSS_14:
                return BarcodeFormat.RSS_14;
            case AppConstants.RSS_EXPANDED:
                return BarcodeFormat.RSS_EXPANDED;
            default:
                return BarcodeFormat.QR_CODE;
        }
    }
}
