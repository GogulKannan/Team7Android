package com.example.gogul.adandroid;

/**
 * Created by e0046709 on 1/17/2017.
 */

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTrace {
    public static String trace(Exception ex) {
        StringWriter outStream = new StringWriter();
        ex.printStackTrace(new PrintWriter(outStream));
        return outStream.toString();
    }
}