package com.example.app20230214;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

// View를 상속받아 그래픽 처리를 개발자가 주도해보자
public class GalleryView extends View {
    Context context; //  이 View를 관리하는 Activity
    Bitmap[] bitmaps = new Bitmap[7];
    int index; // 사진 배열을 접근할 인덱스

    // 우리가 만든 View를 XML에 올려 놓을 때는 해당 XML에 사용된 태그 속성도 가져와야 하므로
    // 이 때 이 태그 속성을 받아들이는 객체가 바로 AttributeSet이다
    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    // 비트맵 배열을 미리 만들어 놓기
    public void init() {
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.img0);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.img1);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.img2);
        bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.img3);
        bitmaps[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.img4);
        bitmaps[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.img5);
        bitmaps[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.img6);
    }
    @Override
    public void onDraw(Canvas canvas) {
        // 이미지를 그리자
        canvas.drawBitmap(bitmaps[index], 50, 50, null);
    }

    // 다음 이미지
    public void nextImg() {
        index++;
        invalidate(); // 다시 그리기 (==Java repaint()와 동일)
    }

    // 이전 이미지
    public void prevImg() {
        index--;
        invalidate();
    }


}
