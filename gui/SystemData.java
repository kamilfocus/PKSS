package com.piotrek;

import org.json.JSONObject;
import java.util.Arrays;

public class SystemData {

    static private final String TIME_KEY = "trzy_miliony";
    static private final String T_o_KEY = "T_o";            // - tempertatura otoczenia
    static private final String T_zco_KEY = "T_zco";        // - temperatura wody z wymiennika
    static private final String F_zco_KEY = "F_zco";
    static private final String T_pcob1_KEY = "T_pcob1";    // - temperatura wody zwrotnej z budynku 1
    static private final String F_cob1_KEY = "F_cob1";
    static private final String T_pcob2_KEY = "T_pcob2";    // - temperatura wody zwrotnej z budynku 2
    static private final String F_cob2_KEY = "F_cob2";
    static private final String U_b1_KEY = "U_b1";          // - sterowanie budynku 1
    static private final String U_b2_KEY = "U_b2";          // - sterowanie budynku 2
    static private final String T_r1_KEY = "T_r1";          // - œrednia temperatura w pomieszczeniach budynku 1
    static private final String T_r2_KEY = "T_r2";          // - œrednia temperatura w pomieszczeniach budynku 2
    static private final String T_pm_KEY = "T_pm";          // - temp. wody powrotnej do dostawcy
    static private final String U_m_KEY = "U_m";
    static private final String F_zm_KEY = "F_zm";
    static private final String T_zm_KEY = "T_zm";
    static private final String T_pco_KEY = "T_pco";

    static private final String Kp_reg1_KEY  = "kp_reg1";
    static private final String Ki_reg1_KEY  = "ki_reg1";
    static private final String Kd_reg1_KEY  = "kd_reg1";

    static private final String Kp_b1_KEY  = "kp_b1";
    static private final String Ki_b1_KEY  = "ki_b1";

    static private final String Kp_b2_KEY  = "kp_b2";
    static private final String Ki_b2_KEY  = "ki_b2";
    static private final String Kd_b2_KEY  = "kd_b2";
    static private final String T_b1_KEY  = "T_b1";
    static private final String T_b2_KEY  = "T_b2";

    static private final String JSON_KEYS_ARRAY[] = {TIME_KEY, T_o_KEY, T_zco_KEY, F_zco_KEY, T_pcob1_KEY, F_cob1_KEY, T_pcob2_KEY, F_cob2_KEY,
            U_b1_KEY, U_b2_KEY, T_r1_KEY, T_r2_KEY, T_pm_KEY, U_m_KEY, F_zm_KEY, T_zm_KEY, T_pco_KEY, Kp_reg1_KEY, Ki_reg1_KEY, Kd_reg1_KEY,
            Kp_b1_KEY, Ki_b1_KEY, Kp_b2_KEY, Ki_b2_KEY, Kd_b2_KEY, T_b1_KEY, T_b2_KEY};

    static public final int TIME = Arrays.asList(JSON_KEYS_ARRAY).indexOf(TIME_KEY);
    static public final int T_o = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_o_KEY);
    static public final int T_zco = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_zco_KEY);
    static public final int F_zco = Arrays.asList(JSON_KEYS_ARRAY).indexOf(F_zco_KEY);
    static public final int T_pcob1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_pcob1_KEY);
    static public final int F_cob1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(F_cob1_KEY);
    static public final int T_pcob2 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_pcob2_KEY);
    static public final int F_cob2 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(F_cob2_KEY);
    static public final int U_b1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(U_b1_KEY);
    static public final int U_b2 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(U_b2_KEY);
    static public final int T_r1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_r1_KEY);
    static public final int T_r2 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_r2_KEY);
    static public final int T_pm = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_pm_KEY);
    static public final int U_m = Arrays.asList(JSON_KEYS_ARRAY).indexOf(U_m_KEY);
    static public final int F_zm = Arrays.asList(JSON_KEYS_ARRAY).indexOf(F_zm_KEY);
    static public final int T_zm = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_zm_KEY);
    static public final int T_pco = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_pco_KEY);

    static public final int Kp_reg1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(Kp_reg1_KEY);
    static public final int Ki_reg1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(Ki_reg1_KEY);
    static public final int Kd_reg1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(Kd_reg1_KEY);

    static public final int Kp_b1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(Kp_b1_KEY);
    static public final int Ki_b1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(Ki_b1_KEY);

    static public final int Kp_b2 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(Kp_b2_KEY);
    static public final int Ki_b2 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(Ki_b2_KEY);
    static public final int Kd_b2 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(Kd_b2_KEY);
    static public final int T_b1 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_b1_KEY);
    static public final int T_b2 = Arrays.asList(JSON_KEYS_ARRAY).indexOf(T_b2_KEY);

    private double totalTime = 0;
    private int days = 0;
    private int hours = 0;
    long minutes = 0;
    private double [] systemParam = new double [JSON_KEYS_ARRAY.length];
    private boolean failure = false;

    public SystemData(){
        for(double x : systemParam)
            x = 0;
    }

    public void updateSystemData(String json){

        JSONObject obj = new JSONObject(json);

        for(int i=0; i<JSON_KEYS_ARRAY.length; i++){
            if(obj.has(JSON_KEYS_ARRAY[i]))
                systemParam[i] = obj.getDouble(JSON_KEYS_ARRAY[i]);
        }

        minutes += systemParam[TIME];
        totalTime += systemParam[TIME]/60;
        days = (int)totalTime/24;
        hours = ((int)totalTime)%24;

        failure = (systemParam[T_zm] < Main.FAILURE_THERSHOLD) ? true : false;

    }

    public double getParam(int paramIndex){
        return systemParam[paramIndex];
    }

    public void setParam(double newValue, int paramIndex){
        systemParam[paramIndex] = newValue;
    }

    public JSONObject getInitData(){
        JSONObject obj = new JSONObject();
        obj.put(JSON_KEYS_ARRAY[TIME], systemParam[TIME]);
        return obj;
    }

    public JSONObject getDataToSend(){
        JSONObject obj = new JSONObject();
        obj.put(JSON_KEYS_ARRAY[TIME], systemParam[TIME]);
        obj.put(JSON_KEYS_ARRAY[Kp_reg1], systemParam[Kp_reg1]);
        obj.put(JSON_KEYS_ARRAY[Ki_reg1], systemParam[Ki_reg1]);
        obj.put(JSON_KEYS_ARRAY[Kd_reg1], systemParam[Kd_reg1]);

        obj.put(JSON_KEYS_ARRAY[Kp_b1], systemParam[Kp_b1]);
        obj.put(JSON_KEYS_ARRAY[Ki_b1], systemParam[Ki_b1]);

        obj.put(JSON_KEYS_ARRAY[Kp_b2], systemParam[Kp_b2]);
        obj.put(JSON_KEYS_ARRAY[Ki_b2], systemParam[Ki_b2]);
        obj.put(JSON_KEYS_ARRAY[Kd_b2], systemParam[Kd_b2]);

        obj.put(JSON_KEYS_ARRAY[T_b1], systemParam[T_b1]);
        obj.put(JSON_KEYS_ARRAY[T_b2], systemParam[T_b2]);
        return obj;
    }

    public double getTotalTime() { return totalTime; }
    public int getDays() { return days; }
    public int getHours() { return hours; }
    public long getMinutes() { return minutes; }
    public boolean isFailure() { return failure;}

}
