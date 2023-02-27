package com.example.googlemapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    // 구글맵을 보여주기 위해서는 그냥 Fragment가 아닌 MapFragment를 사용
    //    SupportMapFragment가 자동으로 안뜨면 라이브러리가 없는 것
    SupportMapFragment mapFragment; // 구글맵 전용 프레그먼트

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = SupportMapFragment.newInstance();

        // 액티비티가 프레그먼트를 제어하려면 프레그먼트 매니저를 사용해야 한다
        // 매니저의 주 역할 : 페이저에 대한 트랜잭션 처리
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 프레그먼트와리스너 연결
        mapFragment.getMapAsync(this);

        // 트랜잭션 시작
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, mapFragment);
        transaction.commit();
    }

    public void onMapReady(@NonNull GoogleMap googleMap) {
        // 구글맵 객체 이용하여 아이콘, 그림, 메세지 등을 구현할 수 있음
        // 위도, 경도 설정
        MarkerOptions options = new MarkerOptions();
        // options.position(new LatLng(위도, 경도));
        options.position(new LatLng(37.55555, 126.88888));
        options.title("여기가 맛집");
        googleMap.addMarker(options);
    }
}