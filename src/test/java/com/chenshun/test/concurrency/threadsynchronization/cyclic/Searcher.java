package com.chenshun.test.concurrency.threadsynchronization.cyclic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * User: mew <p />
 * Time: 17/9/6 11:22  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Searcher implements Runnable {

    private int firstRow;

    private int lastRow;

    private MatrixMock mock;

    private Results results;

    private int number;

    private final CyclicBarrier cyclicBarrier;

    public Searcher(int firstRow, int lastRow, MatrixMock mock, Results results, int number,
                    CyclicBarrier cyclicBarrier) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.mock = mock;
        this.results = results;
        this.number = number;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        int counter;
        System.out.printf("%s: Processing lines from %d to %d.\n", Thread.currentThread().getName(), firstRow, lastRow);
        for (int i = firstRow; i < lastRow; i++) {
            int row[] = mock.getRow(i);
            counter = 0;
            for (int j = 0; j < row.length; j++) {
                if (row[j] == number) {
                    counter++;
                }
            }
            results.setData(i, counter);
        }
        System.out.printf("%s: Lines processed.\n", Thread.currentThread().getName());
        try {
            cyclicBarrier.await();
            Thread.sleep(10);
            System.out.printf("Searcher: finish.\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final int ROWS = 10000;
        final int NUMBERS = 1000;
        final int SEARCH = 5;
        final int PARTICIPANTS = 5;
        final int LINES_PARTICIPANT = 2000;
        MatrixMock matrixMock = new MatrixMock(ROWS, NUMBERS, SEARCH);
        Results results = new Results(ROWS);
        Grouper grouper = new Grouper(results);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(PARTICIPANTS, grouper);
        Searcher searcher[] = new Searcher[PARTICIPANTS];
        for (int i = 0; i < PARTICIPANTS; i++) {
            searcher[i] = new Searcher(i * LINES_PARTICIPANT,
                    (i * LINES_PARTICIPANT) + LINES_PARTICIPANT,
                    matrixMock, results, 5, cyclicBarrier);
            Thread thread = new Thread(searcher[i]);
            thread.start();
        }
        System.out.printf("Main: The main thread has finished.\n");
    }

}
