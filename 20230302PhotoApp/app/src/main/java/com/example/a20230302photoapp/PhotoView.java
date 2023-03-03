package com.example.a20230302photoapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// 사진을 미리 볼 수 있는 뷰
public class PhotoView extends View {
    Bitmap bitmap; // 사진도, 이미지도,,,다 할 수 있는
    MainActivity mainActivity;

    // 이 뷰를 XML에서 사용하려면, 생성자의 매개변수에는 반드시 xml의 태그 속성을 받을 수 있는
    // 매개변수인 AttibuteSet 존재해야 한다
    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mainActivity = (MainActivity)context; // 사진이 액티비티에 있으므로
    }
    // 사진 넘겨받기
    public void createBitmap() {
        // 메인 액티비티가 보유한 자료형은 File형이므로 여기서 Bitmap형으로 바꿔서 사용하자
        FileInputStream fis=null;
        try {
            fis = new FileInputStream(mainActivity.selectedFile);
            bitmap= BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // 자바에서의 paint()



    protected void onDraw(Canvas canvas) {
        if(bitmap!=null) {
        canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }
}
