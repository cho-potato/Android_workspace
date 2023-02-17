package com.example.a20230217noticeclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    List<String> list = new ArrayList<String>(); // Model MVC중 데이터, 즉 모델이다
    ArrayAdapter<String> adapter; // MVC 중 Controller
    NoticeAdapter noticeAdapter; // 복합된 아이템을 보여주기 위한 재정의된 어댑터


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        // 리스트 뷰는 보여지는 부분(View)과 데이터를 분리시켜 개발한다
        // 즉, MVC로 되어 있다. M(데이터 : 사과 ,,, 딸기 ,,,), V(ListView), C(Adaper)
        //                             ArrayList                    V(JTable), C(TableModel)
        list.add("banana");
        list.add("berry");
        list.add("apple");
        list.add("orange");

        // R.java의 용도 : 프로젝트 환경의 구조중에서 res로 표현되는 디렉토리를 R.java로 관리가 된다
        // 따라서 개발자가 이미지, xml, style 파일 등을 자원으로 등록하면
        // 시스템을 실시간으로 R.java 상수로 등록한다
        // ex) test.xml을 레이아웃 파일용으로 추가할 경우 res/layout/test.xml
        // R.layout.test로 접근이 가능하다
        // -------------------------------------------------------------------------------------------
        // R : 현재 나의 프로젝트 res (나만 사용 가능)
        // android.R : 시스템 차원에서 지원하는 res(나말고 다른 프로젝트도 사용가능)
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        noticeAdapter = new NoticeAdapter(this);
        // JavaSE에서처럼 new JTable(model)와 같이 뷰와 컨트롤러를 연결해야 한다
        // listView.setAdapter(adapter);
        listView.setAdapter(noticeAdapter);

        // 글쓰기 버튼 이벤트 연결
        Button bt_regist = findViewById(R.id.bt_regist);

        bt_regist.setOnClickListener((v)->{
            // 안드로이드의 Activity는 시스템이 관리하기 때문에 개발자가 new 할 수 없다
            // 시스템에 의해 관리는 Activity는 생명주기 존재하고, 그 생명주기에 맞게
            // 개발자는 적절한 코드를 작성만 하면 된다.. 웹에서의 서블릿을 서버가 관리하는 것처럼
            // 개발자는 안드로이드 시스템에 무엇을 원하는지 의도를 보여야 하는데,
            // Intent intent= new Intent(어디서, 어디로); 어디로 : 주소값을 모르니까 클래스 자체를 찍어버림
            Intent intent= new Intent(this,RegistActivity.class);
            startActivity(intent);
        });
    }
}