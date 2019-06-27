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
        Field[] fields=o.getClass().getDeclaredFields();
//        String[] fieldNames=new String[fields.length];
        List list = new ArrayList();
        Map infoMap=null;
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            println(fields[i].getType().toString())
            infoMap.put("name", fields[i].getName());
//            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }
}
