package com.hjz.tools.json2java;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

/**
 * main参数
 * Created by hujunzhon on 2019/2/28.
 */
public class AppConfig {

    @Parameter(names={"-i"}, description = "json文件输入", converter = ExistFileConverter.class, required = true)
    private File jsonFile;

    @Parameter(names={"-o"}, description = "java文件输出", required = false)
    private String outPath = "";

    @Parameter(names={"-p"}, description = "包名" )
    private String packageName ="com.hjz.json.pojo";

    @Parameter(names={"-c"}, description = "根类名" )
    private String rootClass = "root";

    @Parameter(names={"-f"}, description = "前缀" )
    private String prefix = "";
    

    private String frame = "fastjson";

    public String getPrefix() {
        if(prefix == null){
            return "";
        }

        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public File getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(File jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRootClass() {
        return rootClass;
    }

    public void setRootClass(String rootClass) {
        this.rootClass = rootClass;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public static class ExistFileConverter extends FileConverter{
        @Override
        public File convert(String value) {
            File file = super.convert(value);
            if(file == null || !file.exists() || !file.isFile() ){
                return null;
            }

            return file;
        }
    }
}
