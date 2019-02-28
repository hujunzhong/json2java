package com.hjz.tools.json2java.parse;

import com.hjz.tools.json2java.AppConfig;
import com.hjz.tools.json2java.parse.definition.JavaDefinition;

import java.io.*;
import java.util.Date;

/**
 * Created by hujunzhon on 2019/2/28.
 */
public abstract class AbstractJsonParser implements IJsonParser {
    protected AppConfig config;

    public AbstractJsonParser(AppConfig config) {
        this.config = config;
    }

    public JavaDefinition parse(File jsonFile) throws Exception {
        String json = readJsonString(jsonFile);
        JavaDefinition rootDefinition = new JavaDefinition();
        rootDefinition.setPackageName(getDftPackage());
        rootDefinition.setName(config.getRootClass());
        rootDefinition.setNote(buildDftNote());
        doParse(rootDefinition, json);
        return rootDefinition;
    }

    protected abstract void doParse(JavaDefinition rootDefinition, String json) ;

    protected String getDftPackage(){
        return config.getPackageName();
    }

    protected String buildDftNote(){
        return String.format("/**\n" +
                "  * Auto-generated: %tF \n" +
                "  * @author com.hjz.json2java \n" +
                "  */", new Date());
    }

    /**
     * javabean规范格式化field
     * @param fieldName
     * @return
     */
    protected String formatFieldName(String fieldName){
        if(fieldName.matches("[A-Z]*")){//都是大写
            return fieldName.toLowerCase();
        } else if(fieldName.length() > 1 && fieldName.substring(0, 2).matches("[A-Z]*")){
            //首两个字母要么大小要么小写
            return fieldName.substring(0, 2).toLowerCase() + fieldName.substring(2);
        } else if('A' <= fieldName.charAt(0) && 'Z' >= fieldName.charAt(0)){
            return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }

        return fieldName;
    }

    /**
     * 格式化类名
     * @param fieldName
     * @return
     */
    protected String formatClassNameByField(String fieldName){
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 复数变单数
     * @param name
     * @return
     */
    protected String pluralToSingular(String name){
        if(name.endsWith("ies")){
            return name.substring(0, name.length() - 3) + "y";
        } else if(name.endsWith("ses")){
            return name.substring(0, name.length() - 2) ;
        } else if(name.endsWith("s")){
            return name.substring(0, name.length() - 1);
        }

        return name;
    }

    private String readJsonString(File jsonFile) throws Exception {
        InputStream is = new FileInputStream(jsonFile);
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            isr.close();
            return sb.toString();
        } finally {
            is.close();
        }
    }
}
