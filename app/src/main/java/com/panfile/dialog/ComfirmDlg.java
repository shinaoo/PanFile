package com.panfile.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.panfile.R;

public class ComfirmDlg extends Dialog {
    public ComfirmDlg(Context context, int themeResId) {
        super(context, themeResId);
    }

    private String filename;
    private TextView tv_filename;

    public TextView getTv_filename() {
        return tv_filename;
    }

    public void setTv_filename(TextView tv_filename) {
        this.tv_filename = tv_filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
        tv_filename.setText(filename);
    }

    public static class Builder {
        private View mLayout;
        private ComfirmDlg mDialog;
        private TextView tv_filename;
        private Button btn_sure,btn_cancel;
        private View.OnClickListener lsnr_confirm;

        public Builder(Context context) {
            mDialog = new ComfirmDlg(context, R.style.Theme_AppCompat_Dialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mLayout = inflater.inflate(R.layout.dlg_comfirm, null, false);
            mDialog.addContentView(mLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            tv_filename = mLayout.findViewById(R.id.tv_dlg_filename);
            btn_sure = mLayout.findViewById(R.id.btn_dlg_comfirm_sure);
            btn_cancel = mLayout.findViewById(R.id.btn_dlg_comfirm_cancel);
        }

        public Builder setConfirListener(View.OnClickListener lsnr_confirm){
            this.lsnr_confirm = lsnr_confirm;
            return this;
        }

        public ComfirmDlg build(){
            btn_cancel.setOnClickListener(view -> mDialog.dismiss());
            btn_sure.setOnClickListener(lsnr_confirm);
            mDialog.setContentView(mLayout);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setTv_filename(tv_filename);
            return mDialog;
        }

    }

}
