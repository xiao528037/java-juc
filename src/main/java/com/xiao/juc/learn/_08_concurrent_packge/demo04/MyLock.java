package com.xiao.juc.learn._08_concurrent_packge.demo04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-18 11:08:10
 * @description
 */
public class MyLock implements Lock {


    //自定义独占锁
    class MySync extends AbstractQueuedSynchronizer {
        //尝试获得锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, arg)) {
                //得到锁后owner中放入当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //尝试解锁，当你能释放锁的时候，肯定当前线程是获得了锁
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(arg);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {//是否持有锁
            return getState() == 1;
        }


        public Condition newCondition() {
            return new ConditionObject();
        }
    }


    private MySync sync = new MySync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(0);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
