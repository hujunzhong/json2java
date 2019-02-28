package com.hjz.tools.json2java;

import com.beust.jcommander.JCommander;
import com.hjz.tools.json2java.output.JavaFileOutput;
import com.hjz.tools.json2java.output.JavaOutput;
import com.hjz.tools.json2java.parse.FastJsonParser;
import com.hjz.tools.json2java.parse.IJsonParser;
import com.hjz.tools.json2java.parse.definition.JavaDefinition;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        try {
            AppConfig appConfig = new AppConfig();
            JCommander jc = new JCommander(appConfig);
            jc.parse(args);
            IJsonParser jsonParser = new FastJsonParser(appConfig);
            JavaDefinition root = jsonParser.parse(appConfig.getJsonFile());
            JavaOutput output = new JavaFileOutput(appConfig);
            output.print(root);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        
    }
}
