package com.piotrek;

public class Worker{

    private Connector connector = null;
    private GUI gui = null;
    private SystemData systemData = null;


    Worker(){

    }

    Worker(Connector connector, GUI gui, SystemData systemData){
        this.connector = connector;
        this.gui = gui;
        this.systemData = systemData;
    }

    public void run() {
        new Thread(
                () -> start()
        ).start();

    }

    public void stop(){
        connector.closeConnection();
    }

    public void start(){
        gui.clear();
        gui.setLatch();
        gui.update(systemData);
        connector.sendInitMsg(systemData.getInitData());
        String recvStr = connector.receive();
        while (recvStr != null) {
            System.out.println("recv msg: " + recvStr + "\n");
            systemData.updateSystemData(recvStr);
            gui.update(systemData);
            gui.log(systemData.getMinutes(), recvStr);
            connector.response(systemData.getDataToSend());
            recvStr = connector.receive();
        }
        connector.closeConnection();
        gui.resetButtons();
        System.out.println("thread closed");
    }
}


