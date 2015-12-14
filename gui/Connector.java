package com.piotrek;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Connector {

    private final static String TYPE = "type";
    private final static String INIT_MSG_TYPE = "init";
    private final static String DATA_MSG_TYPE = "data";
    private final static String SRC = "src";
    private final static String GUI = "gui";
    private final static int BUFF_SIZE = 1024;

    private Socket sck;
    private DataInputStream input;
    private DataOutputStream output;

    byte [] buff = new byte [BUFF_SIZE];

    public Connector(byte[] addr, int portNumber){
        try {
            sck = new Socket(InetAddress.getByAddress(addr), portNumber);
            System.out.println("connection success");
            input = new DataInputStream(sck.getInputStream());
            output = new DataOutputStream(sck.getOutputStream());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendInitMsg(){
        try{
            JSONObject initMsg = new JSONObject();
            initMsg.put(TYPE, INIT_MSG_TYPE);
            initMsg.put(SRC, GUI);


            System.out.println("init msg: " + initMsg.toString());
            output.write(initMsg.toString().getBytes());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendInitMsg(JSONObject initMsg){
        try{
            initMsg.put(TYPE, INIT_MSG_TYPE);
            initMsg.put(SRC, GUI);


            System.out.println("init msg: " + initMsg.toString());
            output.write(initMsg.toString().getBytes());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public String receive(){
        try {
            if(sck.isClosed())
                return null;
            int length = input.read(buff);
            String msg = new String(buff, 0, length);

            //System.out.println("Server: " + msg);
/*
            JSONObject obj = new JSONObject(msg);

            String type = null;
            if(obj.has("abc"))
                type = obj.getString("abc");

            if(type == null)
                System.out.println("null !!!");
            int delta = obj.getInt("trzy_miliony");
            //System.out.println(type + " " + delta);
*/
            if (length == -1)
                return null;
            return msg;
        }
        catch (Exception e) {
            System.out.println(e);
            closeConnection();
            return null;
        }
    }

    public void response(){
        try {
            JSONObject temp = new JSONObject();
            temp.put(TYPE, DATA_MSG_TYPE);
            temp.put(SRC, GUI);

            System.out.println("resp: " + temp.toString());

            output.write(temp.toString().getBytes());
        }
        catch (Exception e) {
            System.out.println(e);
            closeConnection();
        }
    }

    public void response(JSONObject msg){
        try {
            if(sck.isClosed())
                return;
            msg.put(TYPE, DATA_MSG_TYPE);
            msg.put(SRC, GUI);

            System.out.println("resp: " + msg.toString());

            output.write(msg.toString().getBytes());
        }
        catch (Exception e) {
            System.out.println(e);
            closeConnection();
        }
    }

    public void closeConnection(){
        try {
            if(!sck.isClosed()) {
                input.close();
                output.close();
            //sck.shutdownInput();
            //sck.shutdownOutput();
            //if(!sck.isClosed())
                sck.close();
                System.out.println("connection closed");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
