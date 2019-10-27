package com.example.indoorpositioning;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseActivity extends AppCompatActivity {

    //    private Session mSession;
//    private Retrofit mRetrofit;
    private SweetAlertDialog dialogProgress, dialogWarning, dialogError, dialogInfo;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mSession = Session.getInstance(this);
//        mRetrofit = Server.getInstance();
    }

//    public Session getSession() {
//        return mSession;
//    }

//    public Retrofit getRetrofit() {
//        return mRetrofit;
//    }

    public void enableScrollView() {
        ViewGroup root = findViewById(android.R.id.content);
        ViewGroup child = (ViewGroup) root.getChildAt(0);
        child.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ((ViewGroup) child.getParent()).removeView(child);
        NestedScrollView nestedScrollView = new NestedScrollView(this);
        nestedScrollView.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        nestedScrollView.addView(child);
        root.removeAllViews();
        root.addView(nestedScrollView);
    }

    @Override
    protected void onDestroy() {
        hideError();
        hideWarning();
        hideProgress();
        hideInfo();
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void setTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    public void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setSwipeRefresher(int id, final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        swipeRefreshLayout = findViewById(id);
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (onRefreshListener != null)
                        onRefreshListener.onRefresh();
                }
            });
    }

    public void setSwipeRefresher(final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        ViewGroup root = findViewById(android.R.id.content);
        ViewGroup child = (ViewGroup) root.getChildAt(0);

        ((ViewGroup) child.getParent()).removeView(child);
        swipeRefreshLayout = new SwipeRefreshLayout(this);
        swipeRefreshLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        swipeRefreshLayout.addView(child);
        root.removeAllViews();
        root.addView(swipeRefreshLayout);

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (onRefreshListener != null)
                        onRefreshListener.onRefresh();
                }
            });
    }

    public void disableSwipeRefresher(int id) {
        swipeRefreshLayout = findViewById(id);
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setEnabled(false);
    }

    public void setRefreshing(boolean refreshing) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(refreshing);
    }

    public void showProgress(String titleText, @Nullable String contentText) {
        hideProgress();
        dialogProgress = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialogProgress.setTitleText(titleText);
        dialogProgress.setCancelable(false);
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
        dialogWarning = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
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
        dialogInfo = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        dialogInfo.setTitleText(titleText);
        if (contentText != null)
            dialogInfo.setContentText(contentText);
        dialogInfo.show();
    }

    public void showInfo(String titleText, @Nullable String contentText,
                         @Nullable SweetAlertDialog.OnSweetClickListener listener) {
        hideInfo();
        dialogInfo = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
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
        dialogError = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dialogError.setTitleText(titleText);
        if (contentText != null)
            dialogError.setContentText(contentText);
        dialogError.show();
    }

    public void hideError() {
        if (dialogError != null && dialogError.isShowing())
            dialogError.dismiss();
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

//    public void doLogout(){
//        mSession.logout();
//
//        Intent loginActivity = new Intent(this, LoginActivity.class);
//        loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(loginActivity);
//    }
//
//    public String getToken() {
//        return mSession.getToken();
//    }
}
