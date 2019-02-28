支持用json字符串生成java pojo类，符合javabean规范

参数：
-i json文件输入路径.必须
-o java文件输出路径
-p java输出包名,default:com.hjz.json.pojo
-c 根类名，default:root
例：
java -cp target\\json2java-1.0.0-jar-with-dependencies.jar com.hjz.tools.json2java.App -i src\test\resources\test.json
