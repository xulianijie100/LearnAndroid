package com.hy.location.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.hy.location.R;

public class LoadDialog extends Dialog {

    TextView msg_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_dialog);
        msg_text = (TextView) findViewById(R.id.msg);

    }

    public LoadDialog(Context context) {
        super(context, R.style.loaddialog);
        this.setCanceledOnTouchOutside(false);

    }

    public void setMessage(String str) {
        msg_text.setText(str);
    }

}
