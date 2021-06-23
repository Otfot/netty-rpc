package com.netty.rpc.client;

import java.util.Objects;

/**
 * @author otfot
 * @date
 */
public class Main {
    static int count = 0;

    public static void main(String[] args) {
        try {
//            demo1("dkfsdkf", "dkfjdsk", "dkjfaksd", 33.4, 445.0);
            demo2(10);
        } catch (StackOverflowError e) {
            System.out.println(count);
        }

    }

    public static void demo1(String a, String b, String c, double d, double e) {
        count++;
        demo1(a,b,c,d,e);

    }

    public static void demo2(int a) {
        count++;
        demo2(a);

    }


}


