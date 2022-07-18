package com.xiao.juc._08_concurrent_packge.demo02;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aloneMan
 * @projectName java-juc
 * @createTime 2022-07-17 15:33:03
 * @description 使用固定大小的线程池
 */
@Slf4j(topic = "x.PoolTest")
public class ThreadPoolTest {
    /**
     * 固定核心线程的线程池
     */
    @Test
    public void t1() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        threadPoolExecutor.execute(() -> {
            log.debug("1");
        });
        threadPoolExecutor.execute(() -> {
            log.debug("2");
        });
        threadPoolExecutor.execute(() -> {
            log.debug("3");
        });
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            log.debug("5");
        });
        executorService.execute(() -> {
            log.debug("6");
        });
    }

    /**
     * 只有一个线程的线程池
     */
    @Test
    public void t2() {
        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "当前线程数是 " + atomicInteger.getAndIncrement());
            }
        });
        executorService.execute(() -> {
            log.info("1");
            int i = 1 / 0;
        });
        executorService.execute(() -> {
            log.info("2");
        });
        executorService.execute(() -> {
            log.info("3");
        });
        executorService.execute(() -> {
            log.info("4");
        });
    }

    /**
     * 带缓存的队列
     */
    @Test
    public void t3() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool(new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "当前线程为 >>> " + atomicInteger.getAndIncrement());
            }
        });
        pool.execute(() -> {
            log.debug("1");
        });
        Thread.sleep(65000);
        pool.execute(() -> {
            log.debug("2");
        });
    }

    /**
     * 批量执行带返回值的任务
     *
     * @throws InterruptedException
     */
    @Test
    public void t4_invoke_all() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> submit = pool.submit(() -> {
            return "test";
        });
        log.debug("{}", submit.get());
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                    Thread.sleep(1000);
                    return "big ";
                },
                () -> {
                    Thread.sleep(500);
                    return "small";
                },
                () -> {
                    Thread.sleep(2000);
                    return "牛蛙";
                }
        ));
        futures.forEach(item -> {
            try {
                log.debug("{}", item.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 只返回一个结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void t5_invokeAny() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        String o = pool.invokeAny(Arrays.asList(
                () -> {
                    Thread.sleep(1000);
                    return "1";
                },
                () -> {
                    Thread.sleep(500);
                    return "2";
                },
                () -> {
                    Thread.sleep(2000);
                    return "3";
                }
        ));
        log.debug("{}", o);
    }

    /**
     * 任务调度线程池
     */
    @Test
    public void t5_task_scheduling() throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
        ScheduledFuture<?> scheduledFuture = scheduled.scheduleAtFixedRate(() -> {
            log.debug("1");

        }, 10, 10, TimeUnit.SECONDS);

        Thread.sleep(100000);
    }

    @Test
    public void t6() {
        ExecutorService pool = Executors.newCachedThreadPool();
        ListeningExecutorService listeningExecutorService =
                MoreExecutors.listeningDecorator(pool);
        ListenableFuture<String> future = listeningExecutorService.submit(() -> {
            int i=1/0;
            return "123123";
        });
        Futures.addCallback(future, new FutureCallback<String>() {

            @Override
            public void onSuccess(@Nullable String result) {
                log.debug("{}",result);
            }

            @Override
            public void onFailure(Throwable t) {
            log.error("xiaosiwol");
            }
        }, listeningExecutorService);
    }

}
