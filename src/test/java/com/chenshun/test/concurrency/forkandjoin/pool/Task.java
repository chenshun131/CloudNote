package com.chenshun.test.concurrency.forkandjoin.pool;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/11 14:28  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task extends RecursiveAction {

    private static final long serialVersionUID = -3009073490385728376L;

    private List<Product> products;

    private int first;

    private int last;

    private double increment;

    public Task(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrices();
        } else {
            int middle = (last + first) / 2;
            System.out.printf("Task: Pending tasks:%s\n", getQueuedTaskCount());
            Task t1 = new Task(products, first, middle + 1, increment);
            Task t2 = new Task(products, middle + 1, last, increment);
            invokeAll(t1, t2);
        }
    }

    private void updatePrices() {
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (i + increment));
        }
    }

    public static void main(String[] args) {
        ProductListGenerator generator = new ProductListGenerator();
        List<Product> products = generator.generate(10000);

        Task task = new Task(products, 0, products.size(), 0.2);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(task);

        try {
            do {
                System.out.printf("Main: Thread Count: %d\n", forkJoinPool.getActiveThreadCount());
                System.out.printf("Main: Thread Steal: %d\n", forkJoinPool.getStealCount());
                System.out.printf("Main: Thread Parallelism: %d\n", forkJoinPool.getParallelism());
                TimeUnit.MILLISECONDS.sleep(5);
            } while (!task.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        forkJoinPool.shutdown();

        if (task.isCompletedNormally()) {
            System.out.printf("Main: The process has completed normally.\n");
        }
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getPrice() != 12) {
                System.out.printf("Produt %s: %f\n", product.getName(), product.getPrice());
            }
        }
        System.out.printf("Main: End of the program.\n");
    }

}
