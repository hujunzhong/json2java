package com.hjz.tools.json2java;

import com.beust.jcommander.JCommander;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        AppConfig appConfig = new AppConfig();
        JCommander jc = new JCommander(appConfig);
        jc.parse(args);

    }
}
