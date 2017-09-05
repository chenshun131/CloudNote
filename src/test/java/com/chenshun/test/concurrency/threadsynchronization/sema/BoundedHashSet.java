package com.chenshun.test.concurrency.threadsynchronization.sema;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * User: mew <p />
 * Time: 17/9/4 18:07  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class BoundedHashSet<T> {

    private final Set<T> tempSet;

    private final Semaphore sem;

    public BoundedHashSet(int size) {
        this.tempSet = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(size);
    }

    public boolean add(T o) throws Exception {
        sem.acquire();
        boolean isAdd = false;
        try {
            isAdd = tempSet.add(o);
            return isAdd;
        } finally {
            if (isAdd) {
                System.out.println("[" + o + "]数据添加成功" + showTime());
            } else {
                System.out.println("[" + o + "]数据添加失败" + showTime());
                sem.release();
            }
        }
    }

    public boolean remove(Object o) {
        boolean isRemoved = tempSet.remove(o);
        if (isRemoved) {
            System.out.println("[" + o + "]数据删除成功" + showTime());
            sem.release();
        } else {
            System.out.println("[" + o + "]数据删除失败" + showTime());
        }
        return isRemoved;
    }

    public Set<T> getAllData() {
        return tempSet;
    }

    private String showTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static void main(String[] args) throws Exception {
        BoundedHashSet<String> set = new BoundedHashSet<>(2);
        set.add("1");
        set.add("2");
        set.remove("2");
        set.add("3");
        System.out.println(JSON.toJSONString(set.getAllData()));
        set.add("4");
        set.remove("3");
        System.out.println(JSON.toJSONString(set.getAllData()));
    }

}
