package com.chenshun.test.other.mavenfile;

import org.junit.Test;

import java.io.*;

/**
 * User: mew <p />
 * Time: 17/8/23 10:15  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MavenTest {

    @Test
    public void getFileText() {
        String filePath = "/Users/mew/Desktop/AllMyFile/CompanyReposity/GitHub/CloudNote/src/test/java/com/chenshun/test/other/mavenfile/maven_html.txt";
        String basePath = "http://uk.maven.org/maven2/org/apache/activemq/activemq-core/5.7.0/";
        readTxtFile(filePath, basePath);
    }

    public static void readTxtFile(String filePath, String basePath) {
        InputStreamReader read = null;
        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] strs = lineTxt.split(" ");
                    if (strs.length > 0) {
                        System.out.println(basePath + strs[0]);
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        } finally {
            if (read != null) {
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
