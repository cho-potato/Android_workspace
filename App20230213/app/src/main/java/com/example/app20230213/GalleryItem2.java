package com.example.app20230213;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

// 안드로이드 View들 간의 디자인을 순수 자바코드로 작성하면 개발은 가능하짐난 효율성일 떨어진다
// 따라서, 디자인은 XML로 작성하고 이 작성된 XML을 인플레이션 시켜보자
public class GalleryItem2{
    RelativeLayout layout;
    public GalleryItem2(Context context, String title) {
        // 이미 xml로 디자인 된 파일이 있으므로, Layoutinflatior를 이용하여
        // xml에 명시된 태크글을 실제 안드로이드 뷰로 생성해보자
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // layoutInflater.inflate(R.layout.gallery_item, null, false);
        layout = (RelativeLayout)layoutInflater.inflate(R.layout.gallery_item, null, false);
        // 최종적으로 RelativeLayout으로 반환된다 (RelativeLayout 박스 하나)

        // 인플레이션 된 View들 중 아이디를 이용하여 접근(제어)하기
        // 가장 바깥 쪽에 있는 레이아웃은 안쪽에 있는 자식을 아이디로 접근할 수 있다
        TextView t_title = layout.findViewById(R.id.t_title); // 생성자의 매개변수에 추가
        t_title.setText(title); // 제목대입
    }
}
