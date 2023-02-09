package com.example.app20230209;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // xml읽기
        setContentView(R.layout.grid_view);

        GridView gridView = (GridView)this.findViewById(R.id.gridView);

        ArrayList<String> list = new ArrayList<String>();

        list.add("마린");
        list.add("질럿");
        list.add("저글링");
        list.add("골리앗");
        list.add("드라군");
        list.add("히드라");
        list.add("시즈");
        list.add("러커");
        list.add("리버");

        // 어댑터 생성
        ArrayAdapter<String> adapter = null;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        gridView.setAdapter(adapter);
    }
}