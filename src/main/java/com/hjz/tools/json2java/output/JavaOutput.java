package com.hjz.tools.json2java.output;

import com.hjz.tools.json2java.parse.definition.JavaDefinition;

/**
 * Created by hujunzhon on 2019/2/28.
 */
public interface JavaOutput {
    void print(JavaDefinition root) throws Exception;
}
