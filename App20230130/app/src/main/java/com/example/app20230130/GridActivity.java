package com.example.app20230130;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        // GridLayout도 사용해보기

        GridLayout layout = new GridLayout(this);
        layout.setColumnCount(3);
        layout.setRowCount(5);

        for(int i = 1; i<15; i++) {
            Button bt = new Button(this);
            layout.addView(bt); // 그리드 레이아웃에 버튼 부착
        }
        // 화면에 레이아웃 부착
        setContentView(layout);
    }
}