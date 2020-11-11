package com.example.threadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView text=(TextView)findViewById(R.id.txt);
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                text.setText(msg.arg1+"");
            }
        };

        Handler handler1=new Handler();
        handler1.post(new Runnable() {
            @Override
            public void run() {
                text.setText("123");
            }
        });
        final Runnable myWorker=new Runnable() {
            @Override
            public void run() {
                int progress=8;
                int i=2;
                //while (progress<=100){
                while (i<(progress/2)){
                    Message msg=new Message();
                    if((progress%i)==0) {
                        msg.arg1 = progress;
                        handler.sendMessage(msg);
                    }
                   // progress+=10;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                Message msg=handler.obtainMessage();
                msg.arg1=-1;
                handler.sendMessage(msg);
            }
        };
        Button button=(Button)findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread=new Thread(null,myWorker,"WorkThread");
                workThread.start();
            }
        });
    }
}