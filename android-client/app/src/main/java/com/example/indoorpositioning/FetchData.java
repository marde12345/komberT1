package com.example.indoorpositioning;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.indoorpositioning.Config.Config;
import com.example.indoorpositioning.Helper.DatabaseHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import androidx.annotation.Nullable;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class FetchData extends AsyncTask<String, Integer, Integer> {
    private String baseUrl = Config.BASE_URL;
    private Context context;
    private SweetAlertDialog dialogProgress, dialogWarning, dialogError, dialogInfo, dialogSuccess;

    public FetchData(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgress("Proses", "Harap Tunggu");
    }

    @Override
    protected Integer doInBackground(String... params) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(baseUrl + "");
        String json = null;

        publishProgress(0);

        try {
            // Add your data

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httpGet);

            if (response != null) {
                try {
                    json = EntityUtils.toString(response.getEntity());
                    Log.d("Fetch Data", json);
                    JSONArray buildings = new JSONArray(json);
                    DatabaseHelper db = new DatabaseHelper(context);
                    db.updateDatabase(buildings);
                    return 1;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block

        } catch (IOException e) {
            // TODO Auto-generated catch block

        }

        publishProgress(1);
        return 0;
    }

    protected void onPostExecute(Integer status) {
        hideProgress();
        switch (status) {
            case 0:
                showWarning("Warning", "Tidak bisa mengakses server");
                break;
            case 1:
                showInfo("Success", "Database berhasil diupdate");
                break;
            default:
                showError("Error", "Terjadi kesalahan");
                break;

        }
    }


    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
    }


    public void showProgress(String titleText, @Nullable String contentText) {
        hideProgress();
        dialogProgress = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialogProgress.setTitleText(titleText);
        dialogProgress.setCancelable(false);
        dialogProgress.getProgressHelper().setBarColor(Color.parseColor("#4A90E2"));
        if (contentText != null)
            dialogProgress.setContentText(contentText);
        dialogProgress.show();
    }

    public void setProgressIndicator(@Nullable String titleText, @Nullable String contentText) {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            if (titleText != null)
                dialogProgress.setTitle(titleText);
            if (contentText != null)
                dialogProgress.setContentText(contentText);
        }
    }

    public void hideProgress() {
        if (dialogProgress != null && dialogProgress.isShowing())
            dialogProgress.dismiss();
    }

    public void showWarning(String titleText, @Nullable String contentText) {
        hideWarning();
        dialogWarning = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialogWarning.setTitleText(titleText);
        if (contentText != null)
            dialogWarning.setContentText(contentText);
        dialogWarning.show();
    }

    public void hideWarning() {
        if (dialogWarning != null && dialogWarning.isShowing())
            dialogWarning.dismiss();
    }

    public void showInfo(String titleText, @Nullable String contentText) {
        hideInfo();
        dialogInfo = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogInfo.setTitleText(titleText);
        if (contentText != null)
            dialogInfo.setContentText(contentText);
        dialogInfo.show();
    }

    public void showInfo(String titleText, @Nullable String contentText,
                         @Nullable SweetAlertDialog.OnSweetClickListener listener) {
        hideInfo();
        dialogInfo = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogInfo.setTitleText(titleText);
        if (listener != null)
            dialogInfo.setConfirmClickListener(listener);
        if (contentText != null)
            dialogInfo.setContentText(contentText);
        dialogInfo.show();
    }

    public void hideInfo() {
        if (dialogInfo != null && dialogInfo.isShowing())
            dialogInfo.dismiss();
    }

    public void showError(String titleText, @Nullable String contentText) {
        hideError();
        dialogError = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialogError.setTitleText(titleText);
        if (contentText != null)
            dialogError.setContentText(contentText);
        dialogError.show();
    }

    public void hideError() {
        if (dialogError != null && dialogError.isShowing())
            dialogError.dismiss();
    }

    public void showSuccess(String titleText, @Nullable String contentText) {
        hideSuccess();
        dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialogSuccess.setTitleText(titleText);
        if (contentText != null)
            dialogSuccess.setContentText(contentText);
        dialogSuccess.show();
    }

    public void hideSuccess() {
        if (dialogSuccess != null && dialogSuccess.isShowing())
            dialogSuccess.dismiss();
    }
}