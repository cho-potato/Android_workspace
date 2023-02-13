package com.example.app20230213;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    EditText t_input;
    List nationList;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  xml을 반드시 써야하는 것은 아니나, Swing처럼 직접 자바코드만으로 디자인을 할 경우 효율성이 떨어진다
        // 따라서 xml은 사용빈도가 높다
        setContentView(R.layout.activity_main);

        // xml로부터 View 객체들이 태어나는 과정을 inflation이라 한다
        // setContentView 메서드 호출 이후부터는 id만 알면 인스턴스를 접근할 수 있다
        // 이 때 아이디를 통해 접근하는 메서드사 findViewById(=getElementById와 흡사)
        Button bt_regist = (Button)findViewById(R.id.bt_regist);
        t_input = (EditText)findViewById(R.id.t_input);



        // xml 문서에 있는 listView를 제어하기 위해 id를 이용하여 레퍼런스 얻기
        ListView listView = this.findViewById(R.id.listView);

        // ListView, GridView = 일명 어댑터 View라 하는데, 주로 목록을 처리하는데 압도적으로 많이 사용함
        // Swing에서의 JTable이 TableModel을 이용하여 데이터를  연동하는 것과 동일
        // JTable- ListView, GridView 등의 어댑터뷰
        // TableModel - Adapter
        nationList = new ArrayList();
        nationList.add("대한민국");
        nationList.add("미합중국");
        nationList.add("영국");
        nationList.add("대만");
        nationList.add("일본");
        nationList.add("독일");
        nationList.add("프랑스");
        nationList.add("스위스");
        nationList.add("체코");
        nationList.add("이탈리아");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nationList);
        // listView.setAdapter(연결대상어댑터);
        listView.setAdapter(adapter);

        // 버튼과 리스너 연결(재사용성이 없기 때문에 람다)
        // 소괄호 안에 들어가는 것은 view
        bt_regist.setOnClickListener((v)-> {
            // Log.d(TAG, "ㄴㄹㅇ?");

            regist();
        });
    }
    public void regist() {
        // 입력창에 입력한 값을 List에 추가
        // 입력값 가져오기
        String value= t_input.getText().toString();
        nationList.add(value);
        // 어댑터 새로 고치자
        adapter.notifyDataSetChanged();
    }
}