package com.honjane.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.honjane.handlerdemo.lib.Handler;
import com.honjane.handlerdemo.lib.Looper;
import com.honjane.handlerdemo.lib.Message;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();
    public native String stringFromJNI();

    static {
        System.loadLibrary("handlerdemo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "JNI:"+stringFromJNI());
        new ThreadWithLooper().start();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test();
    }

    private void test() {

/*        for (int i = 0; i < 2; i++) {
            new Thread(new Mythread(i,mHandler)).start();
        }*/
        new Thread(new Mythread(0,mHandler,1000)).start();
        new Thread(new Mythread(1,mHandler,4000)).start();
        new Thread(new Mythread(2,mHandler,3000)).start();
/*        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run");
            }
        });*/
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
                    Log.e(TAG, "xiayu main thread recv message------" + message.obj.toString()+"  attime:"+ SystemClock.uptimeMillis());
                }
            };
            Looper.loop();
        }

    }



    class Mythread implements Runnable{

        private int count;
        private long mDelay;
        private Handler mHandler;
        Mythread(int i, Handler handler, long delay){
            count = i;
            mHandler = handler;
            mDelay = delay;
        }

        @Override
        public void run() {
            Message msg = new Message();
            msg.obj = count;
            Log.e(TAG, "sup thread " + Thread.currentThread().getName() + ": send message---" + msg.obj+"  attime:"+ SystemClock.uptimeMillis());
            mHandler.sendMessageDelayed(msg, mDelay);

        }
    }
}
