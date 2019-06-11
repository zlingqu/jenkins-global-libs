package com.tool

class Tools {
    Tools() {}

    public static String  simpleTemplate(text, binding) {
        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(text).make(binding)
        return template.toString()
    }
}
