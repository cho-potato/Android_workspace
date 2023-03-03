package com.example.a20230302photoapp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadManager {
    // Http 통신을 위한 객체
    HttpURLConnection con;
    String host = "http://172.30.1.56:7777/admin/rest/product";
    String hypen="--";
    String boundary="*********"; // 하이픈으로 감쌀 데이터의 경계기준 문자열
    String line="\r\n";

    // 유저가 전송을 위해 선택한 파일
    File file;


    // text + binary file도 함께 Http로 전송하므로 multipart/form-data로 전송해야 한다
    //  text + binary file = multipart/form-data
    public void regist(Product product, File file) throws MalformedURLException, IOException {
        this.file = file;

        URL url = new URL(host);
        con = (HttpURLConnection)url.openConnection(); // 웹용으로 전환

        // 웹 전송을 위한 코드 작성 (머리와 몸 구성, HttpURLConnection 필요)
        // 머리(Header) 구성하기
        con.setRequestProperty("Content-Type", "multipart/form-data;charset=UTF-8;boundary="+boundary); // 데이터와 데이터의 경계를 정해야 하는데 그 경계는 ,,,
        con.setRequestMethod("POST");
        con.setDoOutput(true); // 내보낼고양 서버에 보낼 때
        con.setDoInput(true); // 받을고양 서버에서 가져올 때
        con.setUseCaches(false); // 캐시 없애기
        con.setConnectTimeout(2500); // 응용 프로그램이 서버에 말 걸었는데 반응이 없으면 포기

        // 몸(Body) 구성하기(스트림으로 처리)
        DataOutputStream ds = new DataOutputStream(con.getOutputStream());

        // 텍스트 파라미터의 시작을 알리는 구분자 선언
        // ds.writeBytes("--"+boundary+"\r\n");
        ds.writeBytes(hypen+boundary+line); // 시작할 때
        // 바디를 구성하는 요소들 간에는 줄바꿈으로 구분한다
        // ds.writeBytes("Content-Disposition:form-data;파라미터명"); // 파라미터 선언 뒤에는 줄바꿈 표시 필수
        ds.writeBytes("Content-Disposition:form-data;name=\"category.category_idx\""+line); // 파라미터 선언 뒤에는 줄바꿈 표시 필수
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
        ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
        ds.writeBytes(product.getCategory_idx()+line);
        // ----------------------------------------------------여기까지가 파라미터 하나에 대한 블럭

        ds.writeBytes(hypen+boundary+line); // 시작할 때
        ds.writeBytes("Content-Disposition:form-data;name=\"product_name\""+line);
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
        ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
        ds.writeBytes(product.getProduct_name()+line);

        ds.writeBytes(hypen+boundary+line); // 시작할 때
        ds.writeBytes("Content-Disposition:form-data;name=\"price\""+line);
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
        ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
        ds.writeBytes(product.getPrice()+line);

        ds.writeBytes(hypen+boundary+line); // 시작할 때
        ds.writeBytes("Content-Disposition:form-data;name=\"discount\""+line);
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
        ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
        ds.writeBytes(product.getDiscount()+line);

        ds.writeBytes(hypen+boundary+line); // 시작할 때
        ds.writeBytes("Content-Disposition:form-data;name=\"brand\""+line);
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
        ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
        ds.writeBytes(product.getBrand()+line);

        ds.writeBytes(hypen+boundary+line); // 시작할 때
        ds.writeBytes("Content-Disposition:form-data;name=\"detail\""+line);
        ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
        ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
        ds.writeBytes(product.getDetail()+line);

        // 파일 파라미터 처리
        ds.writeBytes(hypen+boundary+line); // 시작할 때
        // ds.writeBytes("Content-Disposition:form-data;name=\"detail\";filename=\"파일명\""+line);
        ds.writeBytes("Content-Disposition:form-data;name=\"photo\";filename=\""+file.getName()+"\""+line);
        ds.writeBytes("Cotent-Type:image/jpeg"+line); // 파일의 종류, 형식, jpeg는 변수로 처리하든지 ,,,
        ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분

        // 파일 쪼개서 전송
        FileInputStream fis = new FileInputStream(file);
        byte[] buff = new byte[1024];

        int data = -1;

        while(true) {
            data = fis.read(buff);
            if(data==-1) break;
            ds.write(buff);
        }

        // 전송
        ds.writeBytes(line);
        ds.writeBytes(hypen+boundary+hypen+line); // 끝 맺을 때
        ds.flush(); // 버퍼 처리된 출력스트림의 경우 flush()가 사용됨
        fis.close();
        ds.close();

        // 웹서버로부터 받은 http 상태 코드로 성공여부를 따져보자
        int status = con.getResponseCode();
        if(status==HttpURLConnection.HTTP_OK) {
            System.out.println("성공");
        } else {
            System.out.println("실패");
        }
    }

}
