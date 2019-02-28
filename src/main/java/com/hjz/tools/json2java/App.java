package com.hjz.tools.json2java;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
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
        } catch (ParameterException e){
            System.err.println("params error:\n" +
                    "-i json file path for input;required\n" +
                    "-o java file path for output\n" +
                    "-p java package,default:com.hjz.json.pojo\n" +
                    "-c root class name,default:root");
            System.exit(11);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        
    }
}
