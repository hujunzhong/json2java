package com.hjz.tools.json2java.parse.definition;

/**
 * 基本定义，没有字段，暂用于json字段类型引用
 * Created by hujunzhon on 2019/2/28.
 */
public class BaseDefinition extends AbstractDefinition {
    public BaseDefinition(String name, String type) {
        super.name = name;
        super.packageName = type;
    }
}
