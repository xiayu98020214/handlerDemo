package com.honjane.handlerdemo.lib;


import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by honjane on 2017/3/12.
 */

public class MessageQueue {
    private static final String TAG = MessageQueue.class.getName();

    private List<Message> mQueue;

    //锁
    Lock mLock;
    //条件变量
    Condition mNotEmpty;
    Condition mNotFull;

    public MessageQueue() {
        //mQueue = new LinkedBlockingQueue<>(50);
        mQueue = new ArrayList<Message>();
        mLock = new ReentrantLock();
        mNotEmpty = mLock.newCondition();
        mNotFull = mLock.newCondition();

    }

    /**
     * 消息队列取消息 出队
     *
     * @return
     */
    Message next() {

        Message message;
        long remain;
        mLock.lock();

        while (true) {
            if (mQueue.size() > 0) {
                message = mQueue.get(0);
                remain = message.getWhen() - SystemClock.uptimeMillis();
                if (remain > 0) {
                    try {
                        mNotEmpty.await(remain, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            } else {
                try {
                    mNotEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        message = mQueue.remove(0);

        mLock.unlock();
        return message;
    }

    /**
     * 添加消息进队列
     *
     * @param message
     */

    public boolean enqueueMessage(Message message, long uptimeMillis) {
        mLock.lock();
        message.when = uptimeMillis;
        insert(message);
        mNotEmpty.signalAll();
        mLock.unlock();
        return true;
    }


    private void insert(Message msg) {
        for (int i = 0; i < mQueue.size(); i++) {
            if (msg.getWhen() < mQueue.get(i).getWhen()) {
                mQueue.add(i, msg);
                return;
            }
        }
        mQueue.add(msg);
    }
}
