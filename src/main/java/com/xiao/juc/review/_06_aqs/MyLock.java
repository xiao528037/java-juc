package com.xiao.juc.review._06_aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-08-01 13:47:19
 * @description
 */
public class MyLock implements Lock {

    private MyAqs aqs = new MyAqs();

    @Override
    public void lock() {
        aqs.tryAcquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        aqs.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return aqs.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return aqs.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        aqs.tryRelease(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    class MyAqs extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryRelease(int arg) {//释放锁
            //获取锁状态
            int state = getState();
            if (state > 1 && Thread.currentThread() == getExclusiveOwnerThread()) {//锁重入
                int i = state - arg;
                if (compareAndSetState(state, i)) {
                    return true;
                }
            }
            if (state == 1) {
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryAcquire(int arg) {//获取锁
            int state = getState();
            if (state > 1 && Thread.currentThread() == getExclusiveOwnerThread()) {//出现了锁重入
                int newState = state + arg;
                setState(newState);
            } else if (state == 0) {
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }
            return false;
        }

        @Override
        protected boolean isHeldExclusively() {//是否是独占锁
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }

    }
}
