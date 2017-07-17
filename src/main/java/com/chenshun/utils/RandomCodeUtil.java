package com.chenshun.utils;

import org.cosmos.modules.utils.validate.CommonValidateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: mew <p />
 * Time: 17/6/19 09:24  <p />
 * Version: V1.0  <p />
 * Description: 随机码工具类 <p />
 */
public final class RandomCodeUtil {

    private static RandomCodeUtil randomCodeUtilInst = new RandomCodeUtil();

    private final ReentrantLock lock = new ReentrantLock();

    private RandomCodeUtil() {
    }

    /** 去除了字母I,L,O;去除了数字0;避免歧义 */
    private static final String REF_STRING = "ABCDEFGHJKMNPQRSTUVWXYZ123456789";

    /**
     * 生成指定位数的随机编码,编码由23个大写英文字母和9个阿拉伯数字组成.最少5位.
     *
     * @param len
     * @return
     */
    public String generateByUpperCaseCharacterAndDigital(int len) {
        lock.lock();
        StringBuilder builder = new StringBuilder();
        try {
            if (len <= 0) {
                len = 5;
            }
            for (int i = 0; i < len; i++) {
                builder.append(REF_STRING.charAt((int) (Math.random() * 32)));
            }
        } finally {
            /*
            try {
				//强制休眠0.05秒
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
            lock.unlock();
        }

        return builder.toString();
    }

    /**
     * 生成指定位数的随机编码,编码由当前时间和指定位数的阿拉伯数字组成.
     *
     * @param digitalLen
     * @return
     */
    public String generateByTimeAndRandomDigital(int digitalLen) {
        lock.lock();
        StringBuilder builder = new StringBuilder();
        try {
            String serialNo = DateOperator.formatDate(new Date(), "yyyyMMddHHmmssSSS");
            int i = 1;
            builder.append(serialNo);
            if (digitalLen > 0) {
                //当前时间 + digitalLen位的数字随机码
                while (i <= digitalLen) {
                    builder.append((int) (Math.random() * 10));
                    i++;
                }
            }
        } finally {
            try {
                //强制休眠0.05秒
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }

        return builder.toString();
    }

    /**
     * 生成会员PIN
     *
     * @return
     */
    public String memberPinGenerator(String prefix) {
        if (CommonValidateUtil.isEmpty(prefix)) {
            throw new IllegalArgumentException("prefix must not be empty");
        }

        long timeStamp = -1;
        lock.lock();
        try {
            timeStamp = Calendar.getInstance().getTimeInMillis();
            Random random = new Random();
            int randomNum = random.nextInt(999);
            System.out.println("member pin is: " + prefix + timeStamp + randomNum);
            return prefix + timeStamp + randomNum;
        } finally {
            try {
                //强制休眠0.05秒
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    /**
     * 生成手机验证码
     *
     * @return
     */
    public String generateMobileValidateCode() {
        Random random = new Random();
        int code = random.nextInt(899999);
        code = code + 100000;
        return String.valueOf(code);
    }

    public static RandomCodeUtil getInstance() {
        return randomCodeUtilInst;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Thread(new CustomRequest(), "线程" + i).start();
        }
    }

}

// 模拟并发请求的线程类
class CustomRequest implements Runnable {

    public void run() {
        String threadName = Thread.currentThread().getName();
        String code = RandomCodeUtil.getInstance().generateByUpperCaseCharacterAndDigital(5);
        System.out.println(threadName + "-推荐码:" + code + "-" + code.length());
        String orderCode = RandomCodeUtil.getInstance().generateByTimeAndRandomDigital(3);
        System.out.println(threadName + "-订单号:" + orderCode + "-" + orderCode.length());
    }
}
