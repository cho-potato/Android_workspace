package com.example.app20230213;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    EditText t_input;
    LinearLayout box;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button bt_regist = findViewById(R.id.bt_regist);
        t_input = findViewById(R.id.t_input);
        box = findViewById(R.id.box);

        // 버튼과 리스너 연결
        bt_regist.setOnClickListener((v)->{
            regist();
        });
    }
    public void regist(){
        // 입력한 제목을 반영하여 갤러리 복합형 규모의 뷰를 동적으로 등록하자

        // 모든 View는 해당 컨트롤러에 소속관계가 있어야 하므로 생성시 생성자의 인수로
        // 어느 액티비티 소속인지를 지정해야 한다
        // RelativeLayout wrapper = new RelativeLayout(this);
        // GalleryItem wrapper = new GalleryItem(this, t_input.getText().toString());
        GalleryItem2 wrapper = new GalleryItem2(this, t_input.getText().toString());
        box.addView(wrapper.layout); // GalleryItem2이 보유하고 있는 layout이 붙을 예정(RelativeLayout)
    }
}