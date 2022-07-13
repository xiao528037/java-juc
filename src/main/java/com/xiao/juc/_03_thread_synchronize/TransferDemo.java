package com.xiao.juc._03_thread_synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * projectName: java-juc
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-10 10:51:39
 * <p>
 * description： 转账案例
 */
@Slf4j
public class TransferDemo {

    public static void main(String[] args) throws InterruptedException {
        Account a1 = new Account(10000);
        Account a2 = new Account(10000);
        List<Thread> threads = new Vector<>();
        for (int i = 0; i < 1000; i++) {
            Thread t1 = new Thread(() -> {
                //a1向a2转20
                a1.transfer(a2, 20);
            }, "t1");
            Thread t2 = new Thread(() -> {
                //a2向a1转30
                a2.transfer(a1, 30);
            }, "t2");
            Thread t3 = new Thread(() -> {
                a1.saveMoney(10);
            }, "t3");
            Thread t4 = new Thread(() -> {
                a2.saveMoney(10);
            }, "t4");
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            threads.add(t3);
            threads.add(t4);
            threads.add(t1);
            threads.add(t2);
        }

        threads.forEach(item -> {
            try {
                item.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("账户1余额： {} --- 账户2余额： {}", a1.getMoney(), a2.getMoney());
    }
}


@Slf4j
class Account {
    private int money;

    public Account(int amount) {
        this.money = amount;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int amount) {
        this.money = amount;
    }

    public void saveMoney(int amount) {
        synchronized (Account.class) {
            this.money += amount;
            log.info("存入 {} 成功 余额 {}", amount, this.money);
        }
    }

    public void transfer(Account target, int amount) {
        synchronized (Account.class) {
            //正常将余额转出
            if (this.money > amount) {
                setMoney(this.money - amount);
                target.setMoney(amount + target.getMoney());
                log.info("{} >>> 转账成功,当前余额 {} 转账金额 {}", Thread.currentThread().getName(), this.money, amount);
            }
            //当余额不足时，将所有的余额转出
            else if (this.money > 0 && this.money <= amount) {
                int tmp = this.money;
                setMoney(0);
                target.setMoney(target.getMoney() + tmp);
                log.info("{} >>> 转账成功,当前余额 {} 转账金额 {}", Thread.currentThread().getName(), this.money, amount);
            }
            //当余额为零时
            else {
                log.info("{} >>> 当前余额不足，请充值后重试！", Thread.currentThread().getName());
            }
        }
    }
}