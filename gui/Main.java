package com.piotrek;

import org.json.JSONObject;

import java.lang.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Main {
    public static final int PORT_NUMBER = 1234;
    public static final String DEFAULT_IP_ADDR = "192.168.1.101";
    public static final double FAILURE_THERSHOLD = 70.0;

    public static void main(String[] args) {

        new GUI();
    }


    /*
    private static void testChart(GUI gui){
        try {
            double x = 0, y = 0;
            while (x<1000) {
                x++;
                y = x*x;
                System.out.println(x +" "+y);
                gui.updateCharts(x, y);
                Thread.sleep(100);
            }
        }catch(Exception e){
            System.out.print(e);
        }
    }*/
}
