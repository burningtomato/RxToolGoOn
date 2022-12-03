package com.bt.rxtoolgoon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bt.dialog.RxDialogDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private RxDialogDate dialogDate;
    private SimpleDateFormat simpleDateFormat;
    public void showDialog(View view) {
        if (dialogDate == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dialogDate = new RxDialogDate(this);
            dialogDate.setTitle("选择时间");
            dialogDate.setDateFormat(RxDialogDate.DateFormat.年月日);
        }
        dialogDate.setOnSureClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               缺点按钮 。。。。
                dialogDate.dismiss();
            }
        });
        dialogDate.setOnCancelClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                取消按钮
                dialogDate.dismiss();
            }
        });
        dialogDate.show();
    }
}