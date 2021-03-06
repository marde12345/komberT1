package com.example.indoorpositioning;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.indoorpositioning.Config.Config;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class Submit extends AsyncTask<String, Integer, JSONObject> {

    private String baseUrl = Config.BASE_URL;
    private Context context;
    private SweetAlertDialog dialogProgress, dialogWarning, dialogError, dialogInfo, dialogSuccess;

    public Submit(Context context) {

        this.context = context;
        }

    @Override
    protected JSONObject doInBackground(String... params) {
        // TODO Auto-generated method stub
        try {
            return postData(params[0]);
        } catch (IOException e) {
            return null;
        }

    }

    protected void onPostExecute(JSONObject json) {

        if (json == null) {
//            showWarning("Null","JSON Null");
            Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
        } else {

            try {
                if (json.get("result").equals("success")) {
//                    showSuccess("Success","Berhasil mengirim data ke server");
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();

                } else {
//                    showError("Fail","Gagal mengirim data");
                    Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
//                showWarning("Server Error","Cek Konfigurasi");
                Toast.makeText(context, "Network/Server Error", Toast.LENGTH_LONG).show();
            }
        }


    }


    protected void onProgressUpdate(Integer... progress) {

    }

    public JSONObject postData(String jsonData) throws IOException {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(baseUrl + "submit");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo();

                String mac = info.getMacAddress();
                Log.d("Data Sent", jsonData);
                nameValuePairs.add(new BasicNameValuePair("mac", mac));
                nameValuePairs.add(new BasicNameValuePair("data",jsonData));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                JSONObject json = null;
                if (response == null)
                    return null;
                else {
                    try {
                        json = new JSONObject(EntityUtils.toString(response.getEntity()));
                    } catch (JSONException e) {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return json;

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                return null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return null;
            }
    }

    public void showWarning(String titleText, @Nullable String contentText) {
        hideWarning();
        dialogWarning = new SweetAlertDialog(this.context, SweetAlertDialog.WARNING_TYPE);
        dialogWarning.setTitleText(titleText);
        if (contentText != null)
            dialogWarning.setContentText(contentText);
        dialogWarning.show();
    }

    public void hideWarning() {
        if (dialogWarning != null && dialogWarning.isShowing())
            dialogWarning.dismiss();
    }

    public void showError(String titleText, @Nullable String contentText) {
        hideError();
        dialogError = new SweetAlertDialog(this.context, SweetAlertDialog.ERROR_TYPE);
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
        dialogSuccess = new SweetAlertDialog(this.context, SweetAlertDialog.SUCCESS_TYPE);
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
