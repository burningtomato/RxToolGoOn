package com.bt.rxtoolgoon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bt.dialog.RxDialogDate;
import com.example.error_crash.RxTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
//        RxTool.init(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (!hasAllPermissionsGranted(grantResults)) {
//            return;
//        }
        switch (requestCode) {
            case 222:
                Toast.makeText(getApplicationContext(), "已申请权限", Toast.LENGTH_SHORT).show();
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    private RxDialogDate dialogDate;
    public void showDialog(View view) {
        if (dialogDate == null) {
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

    public void errorTest(View view) {
        startActivity(new Intent(this, SimpleActivity.class));
    }
}