package com.chenshun.test.other;

import org.junit.Test;

/**
 * User: mew <p />
 * Time: 17/9/29 17:53  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class NullTest {

    @Test
    public void testNull1() {
        String str = null; // null can be assigned to String
        Integer itr = null; // you can assign null to Integer also
        Double dbl = null;  // null can also be assigned to Double

        String myStr = (String) null; // null can be type cast to String
        Integer myItr = (Integer) null; // it can also be type casted to Integer
        Double myDbl = (Double) null; // yes it's possible, no error

        StringBuilder sb = new StringBuilder();
        sb.append("str = ").append(str).append("\n")
                .append("itr = ").append(itr).append("\n")
                .append("dbl = ").append(dbl).append("\n")
                .append("++++++++++++++++++++++++++++++++")
                .append("myStr = ").append(myStr).append("\n")
                .append("myItr = ").append(myItr).append("\n")
                .append("myDbl = ").append(myDbl).append("\n");
        System.out.println(sb.toString());
    }

    @Test
    public void testNull2() {
//        int i = null; // type mismatch : cannot convert from null to int
//        short s = null; //  type mismatch : cannot convert from null to short
//        byte b = null; // type mismatch : cannot convert from null to byte
//        double d = null; //type mismatch : cannot convert from null to double

        Integer itr = null; // this is ok
        int j = itr; // this is also ok, but NullPointerException at runtime

        System.out.println(j);
    }

}
