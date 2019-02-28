package com.hjz.tools.json2java.parse.definition;

/**
 * 类型定义
 * Created by hujunzhon on 2019/2/28.
 */
public abstract class AbstractDefinition {
    protected String packageName;
    protected String name;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
