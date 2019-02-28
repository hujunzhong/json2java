package com.hjz.tools;

import com.hjz.tools.json2java.App;
import junit.framework.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void test(){
        String[] args = new String[]{"-i", "C:\\Users\\tiankai.qin\\Desktop\\test.json"};
        App.main(args);
    }
}
