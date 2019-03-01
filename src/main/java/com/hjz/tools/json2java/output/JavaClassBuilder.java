package com.hjz.tools.json2java.output;

import com.hjz.tools.json2java.parse.definition.BaseDefinition;
import com.hjz.tools.json2java.parse.definition.JavaDefinition;

import java.io.File;
import java.util.HashMap;

/**
 * Created by hujunzhon on 2019/2/28.
 */
public class JavaClassBuilder {

    public static  final  String LINE_END = "\n";

    private JavaDefinition javaDefinition;

    public JavaClassBuilder(JavaDefinition javaDefinition){
        this.javaDefinition = javaDefinition;
    }

    /**
     * 生成class文件内容
     * @return
     */
    public HashMap.Entry<String, StringBuffer> buildJavaClass(){
        StringBuffer sb = new StringBuffer();
        buildNote(sb);
        buildPackage(sb);
        buildImports(sb);
        buildClassStart(sb);
        buildFields(sb);
        buildGetSets(sb);
        buildCLassEnd(sb);
        String fileName = javaDefinition.getPackageName().replace('.', File.separatorChar);
        fileName = fileName + File.separator + javaDefinition.getName() + ".java";
        return new HashMap.SimpleEntry<String, StringBuffer>(fileName, sb);
    }

    private void buildCLassEnd(StringBuffer sb) {
        sb.append("}").append(LINE_END);
    }

    private void buildGetSets(StringBuffer sb) {
        if(javaDefinition.getFieldMap() != null){
            for (JavaDefinition.FieldDefinition field : javaDefinition.getFieldMap().values()){
                String setKey = "set";
                String getKey = "get";
                if(field.getType() instanceof BaseDefinition && "boolean".equals(field.getType().getName())){
                    getKey = "is";
                }

                String fieldType = buildFieldType(field);
                sb.append("    public ").append(fieldType).append(" ").append(getKey)
                        .append(transKeyFirstUpper(field.getFieldName())).append("() {").append(LINE_END);
                sb.append("        return this.").append(field.getFieldName()).append(";").append(LINE_END);
                sb.append("    }").append(LINE_END).append(LINE_END);
                sb.append("    public void ").append(setKey).append(transKeyFirstUpper(field.getFieldName()))
                        .append("(").append(fieldType).append(" ").append(field.getFieldName()).append(") {").append(LINE_END);
                sb.append("        this.").append(field.getFieldName()).append(" = ")
                        .append(field.getFieldName()).append(";").append(LINE_END);
                sb.append("    }").append(LINE_END).append(LINE_END);
            }
        }
    }

    private String transKeyFirstUpper(String key){
        return key.substring(0,1).toUpperCase() + key.substring(1);
    }

    private void buildFields(StringBuffer sb) {
        if(javaDefinition.getFieldMap() != null){
            for (JavaDefinition.FieldDefinition field : javaDefinition.getFieldMap().values()){
                if(field.getAnnotations() != null){
                    for(String ann : field.getAnnotations()){
                        sb.append("    ").append(ann).append(LINE_END);
                    }
                }

                String fieldType = buildFieldType(field);
                sb.append("    private ").append(fieldType).append(" ");
                sb.append(field.getFieldName()).append(";").append(LINE_END).append(LINE_END);
            }
        }
    }

    private String buildFieldType(JavaDefinition.FieldDefinition field) {
        if(field.isArray()){
            return String.format( "List<%s>", field.getType().getName());
        }

        return field.getType().getName();
    }

    private void buildClassStart(StringBuffer sb) {
        sb.append("public class ").append(javaDefinition.getName()).append(" {").append(LINE_END);
    }

    private void buildImports(StringBuffer sb) {
        if(javaDefinition.getImports() != null){
            for(String importItem : javaDefinition.getImports()){
                sb.append("import ").append(importItem).append(";").append(LINE_END);
            }
        }

        sb.append(LINE_END);
    }

    private void buildNote(StringBuffer sb) {
        sb.append(javaDefinition.getNote()).append(LINE_END);
    }

    private void buildPackage(StringBuffer sb) {
        sb.append("package ");
        sb.append(javaDefinition.getPackageName()).append(";").append(LINE_END);
    }

}
