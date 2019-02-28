package com.hjz.tools;

import com.hjz.tools.json2java.App;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void test(){
        String path = Thread.currentThread().getContextClassLoader().getResource("test.json").getPath();
        String[] args = new String[]{"-i", path };
        App.main(args);
    }
}
