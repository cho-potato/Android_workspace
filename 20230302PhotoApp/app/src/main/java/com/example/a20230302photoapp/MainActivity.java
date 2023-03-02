package com.example.a20230302photoapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.Edits;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getName();

    ActivityResultLauncher launcher; // 권한 요청 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_internal=findViewById(R.id.bt_internal);
        Button bt_external=findViewById(R.id.bt_external);

        bt_internal.setOnClickListener((v)->{
            openInternal();
        });
        bt_external.setOnClickListener((v)->{
            openExternal();
        });

        // 안드로이드의 새로운 권한 정책으로 인해 앱의 시작과 동시에 사용자로부터 권한에 대한 확인 및 수락을 받는다
        // 단, 마시멜로우 이전 폰의 경우는 허락필요없음

        /*
        * 안드로이드의 새로운 정책을 구현하기 위해서는 사용자에게 권한을 요청하고 수락받아야 하는데,
        * 이 때 사용되는 객체가 ActivityResultLauncher라 하며
        * 이 객체의 인스턴스를 생성하기 위한 메서드는 registerForActivityResult()라 한다

        * 매개변수 1) 어떤 퍼미션을 요청했는지에 대한 정보를 담고 있는 매개변수
        *   ActivityResultContracts
        * 매개변수 2) 사용자가 해당 요청에 대해 어떤 반응을 보였는지에 대한 정보를 처리할 콜백메서드
        *   ActivityResultCallback
        */

        launcher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                // READ ~~~ true
                // WRITE ~~~ false
                Log.d(TAG, "onActivityResult : 요청에 대한 사용자의 반응 결과는 "+ result);

                Iterator<String> it = result.keySet().iterator(); // 맵에 들어있는 키값만을 일렬로 늘어서게 한다
                while (it.hasNext()) { // 키의 수 만큼 반복문
                     String permission_name = it.next(); // 권한명을 반환받음

                    // 키(권한명)을 이용하여 맵의 실제 데이터(수락여부 논리값) 접근
                    boolean granted = result.get(permission_name);
                    if(granted==false) { // 해당 권한에 대해 수락을 안한 경우라면
                        // 일단 거부를 한 번 이상 하면 수락할 의도가 없는 것이므로 아래의 메세지를 무조건 수행하면 안된다
                        // 즉, 한 번만 나와야 한다
                        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission_name)) {
                            Toast.makeText(MainActivity.this, "권한을 수락하셔야 이용 가능합니다", Toast.LENGTH_SHORT).show();
                        } else {
                            // 여러 번 거절한 경우
                            Toast.makeText(MainActivity.this, "정상적인 앱 이용을 위해 앱설정에서 권한을 수락해주세요", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
            }
        });

        // 마이멜로우 이전 버전인지 아닌지 먼저 체크
        // checkVersion();
        if(checkVersion()) {
            // 최신 핸드폰이므로 파일에 대한 접근보다는 사용자로부터 허락을 먼저 받는다
            if(checkGranted()) {
                openExternal();
            } else {
                // 권한 요청을 시도( 사용자에게는 권한 수락에 대한 팝업이 보이게 된다 )
                launcher.launch(new String[] {
                        // 원하는 권한을 명시하면 됨...
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
            }

        } else {
            // 구버전이므로 허락이고뭐고 필요없이 접근가능하다
        }


    }
    public boolean checkVersion() {
        // 마시멜로우 폰부터 새로운 정책을 적용해야 하므로 현재 사용자의 폰이 어떤 버전인지 파악
        Log.d(TAG, "SDK_INT는 " + Build.VERSION.SDK_INT);
        // SDK_INT 는 전문적인 숫자형이니까 역추적해서 모델을 알아보자
        Log.d(TAG, "마시엘로우 버전은  " + Build.VERSION_CODES.M);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            return true;
        } else {
            return false;
        }
    }

    // 사용자로부터 권한 수락을 요청하는 메서드
    public boolean checkGranted() {
        // 이미 해당 유저가 권한을 수락했는지 여부를 확인해보자
        int read_permission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_permission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // 읽기 권한을 수락했다면
        boolean result1 = read_permission == PackageManager.PERMISSION_GRANTED;

        // 쓰기 권한을 수락했다면
        boolean result2 = write_permission == PackageManager.PERMISSION_GRANTED;

        return result1 && result2;
    }


    /*
    * 안드로이드의 저장소(storage)는 크게 2가지 유형으로 나뉜다
    * 1) 내부저장소
    *   - 외부 저장소 중, 해당 앱만이 전용으로 사용하는 저장소를 가리켜 내부저장소라고한다
    *   - 해당 앱이 폰에서 삭제되면 저장도소 함께 삭제된다
    *
    * 2) 외부저장소
    *   - 탈부착이 가능하기 때문에 외부 기기가 연결되어 있는지 확인해야 함
    *   - mount : 외부 장치가 붙어 있는 현상을 가리킴
    */
    public void openInternal() {
        File file = new File(this.getFilesDir(),""); // 아무 것도 안 적으면 내부저장소 경로가 반환됨
        Log.d(TAG, file.getAbsolutePath()); // D/com.example.a20230302photoapp.MainActivity: /data/user/0/com.example.a20230302photoapp/files
    }
    public void openExternal() {
        File storage = Environment.getExternalStorageDirectory();
        Log.d(TAG, "외부저장소 경로 "+storage.getAbsolutePath());

        // 외부저장소의 하위 디렉토리 및 모든 파일의 목록을 조회해보자
        File[] files = storage.listFiles();
        Log.d(TAG, "하위 디렉토리 및 파일 수는 "+files.length);
        for( File file : files) {
            Log.d(TAG, file.getName());
        }
    }
}