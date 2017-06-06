package com.honjane.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.honjane.handlerdemo.lib.Handler;
import com.honjane.handlerdemo.lib.Looper;
import com.honjane.handlerdemo.lib.Message;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ThreadWithLooper().start();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test();
    }

    private void test() {



        for (int i = 0; i < 1; i++) {
            new Thread(new Mythread(i,mHandler)).start();
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run");
            }
        });
    }


    private Handler mHandler;
    class ThreadWithLooper extends Thread{
        ThreadWithLooper(){

        }

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message message) {
                    Log.e(TAG, "xiayu main thread recv message------" + message.obj.toString());
                }
            };
            Looper.loop();
        }

    }



    class Mythread implements Runnable{

        private int count;
        Handler mHandler;
        Mythread(int i, Handler handler){
            count = i;
            mHandler = handler;
        }

        @Override
        public void run() {
            Message msg = new Message();
            msg.obj = count;
            Log.i(TAG, "sup thread " + Thread.currentThread().getName() + ": send message------" + msg.obj);
            mHandler.sendMessage(msg);
        }
    }
}
