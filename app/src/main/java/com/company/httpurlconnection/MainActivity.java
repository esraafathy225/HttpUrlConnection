package com.company.httpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button,button1;
    TextView textView;

    String link="https://jsonplaceholder.typicode.com/users"; // API
    HttpURLConnection httpURLConnection;
    URL url;
    InputStream inputStream;
    String result;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.txt);
        button=findViewById(R.id.btn);
        button1=findViewById(R.id.btn1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // background or worker thread

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            url=new URL(link);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        try {

                            httpURLConnection= (HttpURLConnection) url.openConnection();
                            httpURLConnection.setReadTimeout(15000);
                            httpURLConnection.setConnectTimeout(15000);
                            httpURLConnection.setRequestMethod("GET");


                            inputStream=httpURLConnection.getInputStream();

                            int c=0;

                            StringBuffer stringBuffer=new StringBuffer();

                            int statusCode=httpURLConnection.getResponseCode();

                            if(statusCode==200) {
                                while ((c = inputStream.read()) != -1) {
                                    stringBuffer.append((char) c);
                                }
                            }

                            result=stringBuffer.toString();

                            inputStream.close();
                            httpURLConnection.disconnect();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(result);
            }
        });

    }
}