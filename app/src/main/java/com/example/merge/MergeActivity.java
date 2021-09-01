package com.example.merge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MergeActivity extends AppCompatActivity {

    TextView resultTextView,timesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);

        resultTextView = findViewById(R.id.resultView);
        timesTextView = findViewById(R.id.textViewTimes);

        //班组
        String[] dutyArr = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        String data = "";
        int dutyNum=dutyArr.length;
        //日期
        int days = getDaysOfMonth(new Date());
        //班次
        String[] classArr = {"早班","中班","晚班"};
        int classNum = classArr.length;
        int allClass=0;
        for (int i=1; i<=days; i++) {
            data += i+"號排班： \n";
            for (int j=1; j<=classNum; j++) {
                //总排班数加1
                allClass++;
                int temp = allClass%dutyNum;
                //班组下标,确定取哪个班组
                int tempSub = temp-1;
                //如果整除，取最后一个班组
                if(temp==0){
                    tempSub=dutyNum-1;
                }
                data += classArr[j-1]+"-"+dutyArr[tempSub] + "\n";
                resultTextView.setText(data);
            }
        }
        timesTextView.setText("值班排班次數:"+allClass);

    }
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}

//class GetTime(){
//
//}