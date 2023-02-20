package com.example.noticeclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    List<String> list=new ArrayList<String>();//mvc중 데이터 즉 model이다
    ArrayAdapter<String> adapter;//MVC 중 Controller이다
    NoticeAdapter noticeApapter;//복합된 아이템을 보여주기 위한 재정의된 어댑터
    private String TAG=this.getClass().getName();

    Handler handler;//개발자가 정의한 스레드가 메인쓰레드로 작업하고 싶은 업무가 있을 경우
                            //핸들러에게 부탁하면 된다.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
        //리스트뷰는 보여지는 부분(view)과 데이터를 분리시켜서 구현
        //즉 MVC로 되어있다...M(데이터 : 사과, 딸기 ..) V(ListView), C(Adapter)
        //                                                            V(JTable),    C(TableModel)

        list.add("banana");
        list.add("berry");
        list.add("apple");
        list.add("orange");

        //R.java 의 용도 : 프로젝트 환경의 구조중에서 res로 표현되는 디렉토리는 R.java로 관리가 된다.
        //따라서 개발자가 이미지, xml, style 파일등등을 자원으로 등록하면 시스템은 실시간으로
        //R.java로 상수로 등록을 한다.
        //ex) test.xml 를 레이아웃 파일용으로 추가할 경우, res/layout/text.xml->R.layout.test로 접근가능
        //R과 android.R의 차이점
        // R: 현재 나의 프로젝트의 res(나만 사용 가능)
        //android.R : 시스템 차원에서 지원하는 res(나말고 다른 프로젝트도 사용 가능)
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        noticeApapter= new NoticeAdapter(this);

        //javaSE처럼 newJTable(model)와 같이 뷰와 컨트롤러를 연결해야 한다.
        listView.setAdapter(noticeApapter);//adapter는 갈아탈 수 있다.

        //글쓰기 버튼 이벤트 연결
        Button bt_regist=findViewById(R.id.bt_regist);
        bt_regist.setOnClickListener((v)->{
            //안드로이드의 Activity는 시스템이 관리하기 때문에 개발자가 new 할 수 없다.
            //시스템에 의해서 관리되는 Activity는 생명주기가 존재하고, 그 생명주기에 맞게
            //개발자는 적절한 코드를 작성만 하면 된다.
            //웹에서의 서블릿을 서버가 관리하는 것처럼  개발자는 안드로이드에 시스템에 무엇을 원하는지
            //의도를 보여야하는데,
            Intent intent=new Intent(this, Regist_Activity.class);
            startActivity(intent);
        });

        handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                noticeApapter.notifyDataSetChanged();//새로고침

            }
        };

    }

    protected void onStart() {//생명주기에 맞춰서 DB를 등록하자
        //부모를 호출해줘야 시스템이 관리해주니까 지우지말자
        super.onStart();

        //현재 액티비티에 의한 화면이 막 등장하려고 할 때,
        Log.d(TAG, "저 이제 막 보여지려해요");

        //디자인이 화면에 드러나기 전에, 스프링에서 데이터 긁어오기
        Thread thread=new Thread(){
            public void run() {
                requestList();
            }
        };
        thread.start();
    }

    public void convertJsonToList(JSONArray jsonArray){
        List<Notice> list=new ArrayList<Notice>();

        Log.d(TAG, "json"+jsonArray.length());
        try {
            for(int i=0;i<jsonArray.length();i++){
                JSONObject json=(JSONObject)jsonArray.get(i);
                Notice notice=new Notice();//empty 상태의 dto
                notice.setNotice_idx(json.getInt("notice_idx"));
                notice.setTitle(json.getString("title"));
                notice.setWriter(json.getString("writer"));
                notice.setContent(json.getString("content"));
                notice.setRegdate(json.getString("regdate"));
                notice.setHit(json.getInt("hit"));

                list.add(notice);
            }
            //어댑터에 반영하기
            noticeApapter.noticeList=list;
            handler.sendEmptyMessage(0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void requestList(){
        //GET 방식의 요청시도!!json으로 가져오기->item에 반영하기
        BufferedReader buffr=null;
        InputStreamReader reader=null;

        try {
            URL url=new URL("http://172.30.1.56:7777/rest/notice/list");
            URLConnection uCon=url.openConnection();
            HttpURLConnection httpCon=(HttpURLConnection)uCon;
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);

            int code=httpCon.getResponseCode();//200,404,500
            Log.d(TAG, "서버의 응답정보"+code);
            if(code==HttpURLConnection.HTTP_OK){//성공되면
                reader=new InputStreamReader(httpCon.getInputStream(), "UTF-8");
                buffr=new BufferedReader(reader);

                StringBuilder sb=new StringBuilder();
                String msg=null;
                while(true){
                    msg=buffr.readLine();
                    if(msg==null)break;
                    sb.append(msg);
                }
                //Log.d(TAG, sb.toString());

                //파싱~~~~
                JSONArray jsonArray=new JSONArray(sb.toString());
                //[]-->ArrayList, {}-->Notice
                convertJsonToList(jsonArray);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(buffr!=null){
                try {
                    buffr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    protected void onResume() {
        super.onResume();

        Log.d(TAG, "저 이제 완전히 보여졌어요");
    }
}