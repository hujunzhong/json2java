package com.hjz.tools.json2java.parse;


import com.hjz.tools.json2java.parse.definition.JavaDefinition;

import java.io.File;

/**
 * json解析
 * Created by hujunzhon on 2019/2/28.
 */
public interface IJsonParser {
    JavaDefinition parse(File json) throws Exception;
}
