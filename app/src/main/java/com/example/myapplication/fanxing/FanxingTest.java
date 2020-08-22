package com.example.myapplication.fanxing;

import com.example.myapplication.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FanxingTest {
    public static void fanxingTest() {
        List<Banana> scr1 = new ArrayList<>();
        scr1.add(new Banana(1));

        List<Fruit> dest1 = new ArrayList<>();
        dest1.add(new Banana(1));

        FanxingTest.<Fruit>copy1(dest1, scr1);//可以灵活转型

    }

    private static <T> void copy1(List<? super T>dest, List<? extends T> scr1) {
        Collections.copy(dest,scr1);
    }
}
