package com.hjz.tools.json2java.parse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjz.tools.json2java.AppConfig;
import com.hjz.tools.json2java.parse.definition.AbstractDefinition;
import com.hjz.tools.json2java.parse.definition.BaseDefinition;
import com.hjz.tools.json2java.parse.definition.JavaDefinition;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * fastjson实现
 * Created by hujunzhon on 2019/2/28.
 */
public class FastJsonParser extends AbstractJsonParser {

    public FastJsonParser(AppConfig config) {
        super(config);
    }

    @Override
    protected void doParse(JavaDefinition rootDefinition, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        parseParent(rootDefinition, jsonObject);
    }

    private void parseParent(JavaDefinition pDefinition, JSONObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            Object field = jsonObject.get(key);
            JavaDefinition.FieldDefinition fieldDefinition = new JavaDefinition.FieldDefinition();
            String fieldName = formatFieldName(key);
            fieldDefinition.setFieldName(fieldName);
            if (!key.equals(fieldName)) {
                fieldDefinition.addAnnotation(getJsonFieldAnntotation(key));
                pDefinition.addImport("com.alibaba.fastjson.annotation.JSONField");
            }

            if (field instanceof List) {
                fieldName = pluralToSingular(fieldName);
                fieldDefinition.setArray(true);
                pDefinition.addImport("java.util.List");
                List jsonArray = (List) field;
                if (jsonArray.size() == 0) {
                    field = new Object();
                } else {
                    field = jsonArray.get(0);
                }
            }

            AbstractDefinition type = getFieldType(fieldName, field);
            if (type.getPackageName() != null && !"".equals(type.getPackageName()) && !pDefinition.getPackageName().equals(type.getPackageName())) {
                pDefinition.addImport(type.getPackageName() + "." + type.getName());
            }

            if(type instanceof BaseDefinition){
                fieldDefinition.setNote("eg: " + String.valueOf(field));
            }

            fieldDefinition.setType(type);
            pDefinition.addField(fieldDefinition);
        }
    }

    private boolean fieldNameValidate(Set<String> keys) {
        for (String key : keys) {
            if (!key.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
                return false;
            }
        }

        return true;
    }

    private AbstractDefinition getFieldType(String fieldName, Object field) {
        if (field instanceof JSONObject) {
            if (!fieldNameValidate(((JSONObject) field).keySet())) {
                //todo:value的类型？
                return new BaseDefinition("Map", "java.util");
            } else {
                JavaDefinition sDefinition = new JavaDefinition();
                sDefinition.setName(formatClassNameByField(fieldName));
                sDefinition.setPackageName(getDftPackage());
                sDefinition.setNote(buildDftNote());
                parseParent(sDefinition, (JSONObject) field);
                return sDefinition;
            }

        } else if (field instanceof String) {
            return new BaseDefinition("String", "");
        } else if (field instanceof Number) {
            if (field instanceof Integer) {
                return new BaseDefinition("int", "");
            } else if (field instanceof Long) {
                return new BaseDefinition("long", "");
            } else if (field instanceof Float) {
                return new BaseDefinition("float", "");
            } else if (field instanceof Short) {
                return new BaseDefinition("short", "");
            } else {
                return new BaseDefinition("double", "");
            }
        } else if (field instanceof Date) {
            return new BaseDefinition("Date", "java.util");
        } else if (field instanceof Boolean) {
            return new BaseDefinition("boolean", "");
        } else {
            //不支持的类型，存为object
            return new BaseDefinition("Object", "");
        }
    }

    private String getJsonFieldAnntotation(String paramName) {
        return String.format("@JSONField(name = \"%s\")", paramName);
    }

}
