package com.epic.pos.ui.sale.receipt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.epic.pos.R;
import com.epic.pos.dagger.AppComponent;
import com.epic.pos.databinding.ActivityReceiptBinding;
import com.epic.pos.ui.BaseActivity;
import com.epic.pos.ui.failed.FailedActivity;
import com.epic.pos.device.PosDevice;

import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiptActivity extends BaseActivity<ReceiptPresenter> implements ReceiptContact.View {

    private ActivityReceiptBinding binding;
    private int MERCHANT_COPY_RES = 150;
    private int CUSTOMER_COPY_RES = 151;

    @BindString(R.string.msg_mer_copy_print_error)
    String msg_mer_copy_print_error;
    @BindString(R.string.msg_cus_copy_print_error)
    String msg_cus_copy_print_error;
    @BindString(R.string.msg_receipt_error)
    String msg_receipt_error;
    @BindString(R.string.btn_ok)
    String btn_ok;
    @BindString(R.string.app_name)
    String app_name;
    @BindString(R.string.msg_feed_paper)
    String msg_feed_paper;

    public static void startActivity(BaseActivity activity) {
        activity.startActivity(new Intent(activity, ReceiptActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_receipt);
        ButterKnife.bind(this);
        setUpToolbarDefault((Toolbar) binding.toolbar, mPresenter.getTitle(), true);
        Log.d("Buddhika","CheckReceiptPrintWithECR");
       // mPresenter.CheckReceiptPrintWithECR();
        mPresenter.initData();

    }

    @Override
    protected void onCloseButtonPressed() {
        mPresenter.closeButtonPressed();
    }

    @Override
    public void onBackPressed() {
        mPresenter.closeButtonPressed();
        super.onBackPressed();
    }

    @Override
    public void onCustomerReceiptGenerated(Bitmap bitmap) {
        binding.ivPreview.setImageBitmap(bitmap);
    }

    @Override
    public void setActionButtonEnabled(boolean isEnabled) {
        binding.btnPrint.setEnabled(isEnabled);
    }



    @OnClick(R.id.btnPrint)
    void onPrintClicked(View view) {
        mPresenter.printCustomerCopy();
    }

    @OnClick(R.id.btnCancel)
    void onCancelClicked(View view) {
        mPresenter.closeButtonPressed();
        finish();
    }

    @Override
    public void onMerchantCopyPrintError(String msg) {
        FailedActivity.startActivity(msg_mer_copy_print_error, msg, false, false, this, MERCHANT_COPY_RES);
    }

    @Override
    public void onCustomerCopyPrintError(String msg) {
        FailedActivity.startActivity(msg_cus_copy_print_error, msg, false, false, this, CUSTOMER_COPY_RES);
    }

    @Override
    public void onReceiptGenerationError(String msg) {
        FailedActivity.startActivity(msg_receipt_error, msg, btn_ok, this);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int result, @Nullable Intent data) {
        super.onActivityResult(requestCode, result, data);
        if (requestCode == MERCHANT_COPY_RES) {
            if (result == RESULT_OK) {
                mPresenter.retryToPrintMerchantCopy();
            }
        } else if (requestCode == CUSTOMER_COPY_RES) {
            if (result == RESULT_OK) {
                mPresenter.retryToPrintCustomerCopy();
            }
        }
    }
    @Override
    public void onfinish() {
        mPresenter.closeButtonPressed();
        finish();
    }
    @Override
    public void onCustomerReceiptPrinted() {
        mPresenter.closeButtonPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PosDevice.getInstance().stopPrinting();
    }

    @Override
    public void setUiVisible() {
        binding.root.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initDependencies(AppComponent appComponent) {
        appComponent.inject(this);
    }

}