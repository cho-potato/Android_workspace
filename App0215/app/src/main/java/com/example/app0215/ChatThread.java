package com.example.app0215;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//소켓을 보관하고 + 각각 독리적으로 해당 소켓을 이용하여 메시지 주고 받는 
//객체가 되어야 함==쓰레드
public class ChatThread implements Runnable{
	//Runnable은 이미 해당 클래스가 누군가의 자식일 경우 쓰레드를 상속할 수 없을때
	//사용할 수 있는 인터페이스이다. 주의할 점은 Runnable은 쓰레드 자체가 아닌, 그냥
	//run()메서드만을 보유한 객체이다. 따라서 Runnable을 구현하더라도 쓰데르 객체는 필요하다.
	Thread thread;
	Socket socket;//서버 소켓이 접속자를 발견하면 그때 넘겨받게 되는 소켓
	BufferedReader buffr;//버퍼처리된 문자 기반의 입력스트림
	BufferedWriter buffw;//버퍼처리된 문자 기반의 출력스트림

	Context context;

	MainActivity mainActivity;
	
	Handler handler;//쓰레드가 디자인을 접근하지 못하므로, 대신 메인에게 제어할 것을
	//부탁해주는 객체

	public ChatThread(Socket socket, Context context) {
		thread=new Thread(this);//러너블 구현 객체를 매개변수로 넣는다.
											//이때부터 Runnable의 run메서드와 쓰레드 객체가 연계가 된다.
		
		this.socket=socket;
		this.context=context;
		mainActivity=(MainActivity)context;
		handler=new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(@NonNull Message message) {
				//메인쓰레드에게 부탁할 디자인 제어 코드를 여기에 작성
				//주의 : 핸들러가 직접 디자인을 제어하는게 아니라, 메인 쓰레드가 동작한다..
				//오직 핸들러만이 메인쓰레드에게 부탁할 수 있다.
				Bundle bundle=message.getData();
				String msg=bundle.getString("msg");
				mainActivity.t_view.append(message+"\n");
			}
		};

		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		thread.start();//태어날때부터 청취시작
	}
	
	//듣기
	public void listen() {
		String msg=null;
		try {
			msg=buffr.readLine();//무한 대기상태

			//디자인 제어는 오직 메인쓰레드에서만 가능하다
			//아래의 코드는 현재 클래스가 개발자 정의 쓰레드이므로,
			//즉 메인이 아닌 쓰레드이므로 디자인을 제어할 수 없다.
			//따라서 직접 디자인을 제어하려 하지말고, 메인스레드에게 부탁해야 한다.
			//이때 사용되는 객체가 바로 android.os 패키지의 Handler 객체이다.

			//핸들러에게 대신 부탁할 예정..
			Message message=new Message();
			Bundle bundle=new Bundle();
			bundle.putString("msg", msg);
			message.setData(bundle);

			handler.handleMessage(message);//핸들러에 전달

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//말하기
	public void send(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//무한 청취 시작
	public void run() {
		while(true) {
			listen();
		}
		
	}
}
