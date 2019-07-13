package com.gs.learn;

public class ChapterItem {
    private String name;
    private Class<?> cls;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public ChapterItem(String name, Class<?> cls) {
        this.name = name;
        this.cls = cls;
    }

}
