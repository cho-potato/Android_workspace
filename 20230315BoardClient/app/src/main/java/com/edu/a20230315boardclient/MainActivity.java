package com.edu.a20230315boardclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_board = findViewById(R.id.bt_board);
        Button bt_chat = findViewById(R.id.bt_chat);

        bt_board.setOnClickListener((v)->{
            // 게시판을 담당하는 액티비티 띄우기
            // 화면을 담당하는 액티비티는 안드로이드에서 주요 컴포넌트 중 하나이다
            // 또한, 컴포넌트는 시스템에 의해 관리되므로 개발자가 직접 new 할 수 없다
            // 요청만 하면 된다 intent
            // Intent intent = new Intent(현재 액티비티, 어떤 액티비티로);
            Intent intent = new Intent(this, BoardActivity.class);
            // 의도를 심었으니 출발하기
            startActivity(intent);
        });
    }
}