package com.example.app20230214;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Button bt_prev = findViewById(R.id.bt_prev);
        Button bt_next = findViewById(R.id.bt_next);
        GalleryView galleryView = findViewById(R.id.galleryView);

        bt_next.setOnClickListener((v)->{
            // 갤러리 View의 존재를 알아야 됨 -> 아이디 부여해서 땡겨오자
            galleryView.nextImg();
        });

        bt_prev.setOnClickListener((v)->{
            galleryView.prevImg();
        });
    }
}