package com.tool

import java.lang.reflect.Field

class Tools {
    Tools() {}

    public static String  simpleTemplate(text, binding) {
        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(text).make(binding)
        return template.toString()
    }

    public static List getFiledsInfo(Object o){
        println(111111)
        Field[] fields=o.getClass().getDeclaredFields();
//        String[] fieldNames=new String[fields.length];
        println(11111222)
        List list = new ArrayList();
        Map infoMap=null;
        System.out.println(11111)
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            System.out.println(fields[i].getType().toString())
            infoMap.put("name", fields[i].getName());
//            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    public static def addItemToListHead(list, item) {
        def returnList = []
        if (item in list) {
            returnList = list - item
        } else {
            returnList = list
        }
        returnList = [item] + returnList
        return returnList
    }

    public static def handleBranchName(branchName) {
        return branchName.replaceAll('/', '-').replaceAll('_', '-').replaceAll('#','-').replaceAll('@', '-').toLowerCase()
    }
}
