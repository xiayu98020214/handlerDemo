package com.honjane.handlerdemo.lib;


import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by honjane on 2017/3/12.
 */

public class MessageQueue {
    private static final String TAG = MessageQueue.class.getName();

    BlockingQueue<Message> mQueue;


    public MessageQueue() {
        mQueue = new LinkedBlockingQueue<>(50);

    }

    /**
     * 消息队列取消息 出队
     *
     * @return
     */
    Message next() {

        Message message=null;
        try {
            message=mQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * 添加消息进队列
     *
     * @param message
     */

    public boolean enqueueMessage(Message message,long uptimeMillis) {

        try {
            Log.e(TAG,"message:"+message.obj);
            mQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return true;
    }

}
