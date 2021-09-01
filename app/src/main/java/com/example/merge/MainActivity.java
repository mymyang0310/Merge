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

public class MainActivity extends AppCompatActivity {
//    TextView tvTimer1,tvTimer2;
//    int t1Month,t1Date,t1Hour,t1Minute,t2Month,t2Date,t2Hour,t2Minute;
    TextView timeTextview;
    CheckBox mon,tue,wed,thu,fri,sat,sun;
    Button mergeButton;

    private ArrayList<String> dayResult;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextview = findViewById(R.id.resultView);
//        dayTextView = findViewById(R.id.textViewDay);
//        resultTextView = findViewById(R.id.textViewResult);
        mergeButton = findViewById(R.id.MergeBtn);

//        tvTimer1 = findViewById(R.id.tv_timer1);
//        tvTimer2 = findViewById(R.id.tv_timer2);

        dayResult = new ArrayList<>();

//        tvTimer1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        //Initialize hour and minute
//                        t1Hour = hourOfDay;
//                        t1Minute = minute;
//                        //Initialize calendar
//                        String time = t1Hour + ":" + t1Minute;
//                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
//                        try{
//                            Date date = f24Hours.parse(time);
//                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
//                            tvTimer1.setText(f12Hours.format(date));
//                        }catch(ParseException e){
//                            e.printStackTrace();
//                        }
//                    }
//                },12,0,false);
//                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                timePickerDialog.updateTime(t1Hour,t1Minute);
//                timePickerDialog.show();
//            }
//        });

//        tvTimer2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        //Initialize hour and minute
//                        t2Hour = hourOfDay;
//                        t2Minute = minute;
//                        //Initialize calendar
//                        String time = t2Hour + ":" + t2Minute;
//                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
//                        try{
//                            Date date = f24Hours.parse(time);
//                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
//                            tvTimer2.setText(f12Hours.format(date));
//                        }catch(ParseException e){
//                            e.printStackTrace();
//                        }
//                    }
//                },12,0,false);
//                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                timePickerDialog.updateTime(t2Hour,t2Minute);
//                timePickerDialog.show();
//            }
//        });


//        mon = (CheckBox)findViewById(R.id.mon);
//        tue = (CheckBox)findViewById(R.id.tue);
//        wed = (CheckBox)findViewById(R.id.wed);
//        thu = (CheckBox)findViewById(R.id.thu);
//        fri = (CheckBox)findViewById(R.id.fri);
//        sat = (CheckBox)findViewById(R.id.sat);
//        sun = (CheckBox)findViewById(R.id.sun);
//        mon.setOnCheckedChangeListener(checkBoxOnCheckedChange);
//        tue.setOnCheckedChangeListener(checkBoxOnCheckedChange);
//        wed.setOnCheckedChangeListener(checkBoxOnCheckedChange);
//        thu.setOnCheckedChangeListener(checkBoxOnCheckedChange);
//        fri.setOnCheckedChangeListener(checkBoxOnCheckedChange);
//        sat.setOnCheckedChangeListener(checkBoxOnCheckedChange);
//        sun.setOnCheckedChangeListener(checkBoxOnCheckedChange);


        mergeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MergeActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";

                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Note note = documentSnapshot.toObject(Note.class);
                    String Name = note.getfName();
                    String Email = note.getEmail();

                    data += "Name: " + Name;
                    data += "\nEmail: " + Email + "\n";
                    for(Map<String, String> s : note.getCourse()){
                        Set set = s.keySet();
                        Object[] arr = set.toArray();
                        Arrays.sort(arr);
                        data += s.get(arr[0]) + ":\n";
                        for(int i = 1; i < arr.length; i++){
                            if(s.get(arr[i]) == ""){
                                data += arr[i] + ": " + s.get(arr[i]) + "\n";
                            }
                        }
                        data += "-----------------------\n";
                    }
                }
                timeTextview.setText(data);
            }
        });
    }

    public CompoundButton.OnCheckedChangeListener checkBoxOnCheckedChange =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    mon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mon.isChecked()){
                                dayResult.add("一 ");
                            }else{
                                dayResult.remove("一 ");
                            }
                        }
                    });
                    tue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(tue.isChecked()){
                                dayResult.add("二 ");
                            }else{
                                dayResult.remove("二 ");
                            }
                        }
                    });
                    wed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(wed.isChecked()){
                                dayResult.add("三 ");
                            }else{
                                dayResult.remove("三 ");
                            }
                        }
                    });
                    thu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(thu.isChecked()){
                                dayResult.add("四 ");
                            }else{
                                dayResult.remove("四 ");
                            }
                        }
                    });
                    fri.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(fri.isChecked()){
                                dayResult.add("五 ");
                            }else{
                                dayResult.remove("五 ");
                            }
                        }
                    });
                    sat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(sat.isChecked()){
                                dayResult.add("六 ");
                            }else{
                                dayResult.remove("六 ");
                            }
                        }
                    });
                    sun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(sun.isChecked()){
                                dayResult.add("日 ");
                            }else{
                                dayResult.remove("日 ");
                            }
                        }
                    });
//                    String data = "";
//                    StringBuilder dataDay = new StringBuilder();
//                    Collections.sort(dayResult);
//                    for(String s : dayResult){
//                        dataDay.append(s);
//                    }
//
//                    data = "選取星期：" + dataDay.toString() + "\n";
//                    dayTextView.setText(data);
                }
            };
}