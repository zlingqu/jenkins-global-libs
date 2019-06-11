package com.tool

class Test implements Serializable{

    Test() {
    }

    public PrintMap(Map<String, String> printMap) {
        Set<String> key = printMap.keySet()
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String s = it.next()
            System.out.println(s+":"+printMap.get(s))
        }
    }
}
