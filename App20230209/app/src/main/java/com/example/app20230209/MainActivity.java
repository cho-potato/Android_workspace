package com.example.app20230209;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView() 메서드에 의해 xml이 읽혀지고 해석되므로
        // xml에 명시된 뷰를 얻기 위해서는 setContentView() 메서드 이후에 접근해야 한다
        setContentView(R.layout.list_view);
        // R은 R.java라는 상수를 모아놓은 클래스이다
        // R.java는 프로젝트 구성 디렉토리 중 res 디렉토리를 반영한 클래스이다
        // 즉, 개발자가 res 디렉토리에 xml, 이미지, 음원 등 리소스를 등록하면
        // 개발환경 자체에서 실시간으로 R.java 에 상수로 등록한다
        ListView listView = (ListView) this.findViewById(R.id.listView); // document.getElementById()

        // Alt + Enter : 자동 임포트
        // ListView에 데이터를 채우기 위해 ArryList를 사용할 수 있다
        // ListView, GridView는 일명 어댑터뷰라 불리며 MVC로 분리되어 있다
        ArrayList<String> list = new ArrayList<String>();
        list.add("Apple");
        list.add("Straw");
        list.add("Finee");
        list.add("Grape");
        list.add("Mango");
        // 위 모델을 디자인에 반영하기 위해 중간 객체인 어댑터가 필요
        // 어댑터 생성
        // 어댑터의 생성자 매개변수 중 두번째 매개변수에는 아이템을 담게될 뷰가 올 수 있다
        // 특히 이 뷰는 xml 레이아웃 파일로 처리돼야 하는데
        // 이 파일은 개발자가 직접 생성해도 되고, 이미 안드로이드 자체에서 지원하는 xml 파일을 이용할 수 있는데
        // 어디까지나 개발자의 선택 ,,,
        // 참고 ] 현재 프로젝트의 res와 연계된 자바 클래스는 R.java이고, 안드로이드 시스템 자체에서 지원하는 리소스와 연계된 자바 클래스는 android.R.java이다
        // android.R.java = 안드로이드 시스템 소속(범용성 넓음) / R.java(프로젝트 소속)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        // 어댑터 반영하기
        listView.setAdapter(adapter);
    }
}