package com.xiao.juc._03_thread_synchronize;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * projectName: java-juc
 * <p>
 * package: com.xiao.juc._03_thread_synchronize
 * <p>
 * author: aloneMan
 * <p>
 * createTime: 2022-07-10 09:20:42
 * <p>
 * description：售票案例
 */
@Slf4j
public class TicketSalesSync {
    public static void main(String[] args) {
        Random random = new Random(47);
        log.debug("开始售票...");
        TicketWindow ticketWindow = new TicketWindow(1000);
        List<Integer> statistics = new Vector<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            if (ticketWindow.getTicket() == 0) {
                break;
            }
            Thread thread = new Thread(() -> {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int quantity = ticketWindow.buyTicket(random.nextInt(10) + 1);
                statistics.add(quantity);
            }, "Thread >> " + i);
            thread.start();
            threads.add(thread);
        }
        //所有线程开启执行等待
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        //获取余票和售出的票
        int ticket = ticketWindow.getTicket();
        int sum = statistics.stream().mapToInt(item -> item).sum();
        log.info("售出： {} -- 余票： {} -- 余票加售出： {}", sum, ticket, sum + ticket);
    }
}

@Slf4j
class TicketWindow {
    private int votes;

    //通过构造器设置票数
    public TicketWindow(int votes) {
        this.votes = votes;
    }

    public TicketWindow() {
    }


    /**
     * 购票
     *
     * @param ticketQuantity
     *         购买票的数量
     * @return 成功购买票的数量
     */
    public synchronized int buyTicket(int ticketQuantity) {
        if (this.votes > 0) {
            if (this.votes > ticketQuantity) {
                //当余票大于购买票数时
                this.votes -= ticketQuantity;
//                log.debug("线程 {} 购票 ：{} 张 -- 余票： {}", Thread.currentThread().getName(), ticketQuantity, this.votes);
                return ticketQuantity;
            } else if (this.votes <= ticketQuantity) {
                //当余票小于购买数量时
                int temp = this.votes;
                this.votes = 0;
//                log.debug("库存紧张,线程 {} 购票 ：{} 张 -- 余票 ：{}张", Thread.currentThread().getName(), this.votes, this.votes);
                return temp;
            }
        }
//        log.debug("线程 {} 购票 ：{} 张", Thread.currentThread().getName(), this.votes);
        return this.votes;
    }

    /**
     * @return 获取票数
     */
    public synchronized int getTicket() {
        return this.votes;
    }


}
