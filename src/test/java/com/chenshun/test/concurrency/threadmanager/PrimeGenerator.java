package com.chenshun.test.concurrency.threadmanager;

/**
 * User: mew <p />
 * Time: 17/8/29 11:15  <p />
 * Version: V1.0  <p />
 * Description: 线程阻断，当前案例线程不会被阻断 <p />
 */
public class PrimeGenerator extends Thread {

    @Override
    public void run() {
        long number = 1L;
        while (true) {
            if (isPrime(number)) {
                System.out.printf("Number %d is Prime\n", number);
            }
            if (isInterrupted()) {
                System.out.printf("The Prime Generator has been interrupted\n");
                return;
            }
            number++;
        }
    }

    private boolean isPrime(long number) {
        if (number <= 2) {
            return true;
        }
        for (long i = 2; i < (number / 2); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        primeGenerator.start();

        Thread.sleep(2000);
        primeGenerator.interrupt();
    }

}
