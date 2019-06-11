package com.tool
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class Test {

    Test() {
    }

    public static printMap(Map<String, String> printMap) {
        Set<String> key = printMap.keySet()
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String s = it.next()
            System.out.println(s+":"+printMap.get(s))
        }
    }
}
