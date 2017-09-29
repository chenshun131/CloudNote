package com.chenshun.test.streamapi.forkandjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 22:02  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class TestForkJoin {

    // ForkJoin框架
    @Test
    public void test1() {
        long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 10000000000L);

        long sum = pool.invoke(task);
        System.out.println(sum);

        long end = System.currentTimeMillis();
        System.out.println("耗费的时间为: " + (end - start)); //112-1953-1988-2654-2647-20663-113808
    }

    // 普通 for
    @Test
    public void test2() {
        long start = System.currentTimeMillis();

        long sum = 0L;
        for (long i = 0L; i <= 10000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);

        long end = System.currentTimeMillis();
        System.out.println("耗费的时间为: " + (end - start)); //34-3174-3132-4227-4223-31583
    }

    // Java8 并行流(底层使用ForkJoin框架)
    @Test
    public void test3() {
        long start = System.currentTimeMillis();

        Long sum = LongStream.rangeClosed(0L, 10000000000L).parallel().sum();
        System.out.println(sum);

        long end = System.currentTimeMillis();
        System.out.println("耗费的时间为: " + (end - start)); //2061-2053-2086-18926
    }

}

