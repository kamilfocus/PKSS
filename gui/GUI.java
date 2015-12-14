package com.piotrek;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class GUI {

    private static final String CELSIUS_DEGREE = "\u00b0C";
    private static final String FLOW_UNIT = "t/h";
    private static final String SIM_TIME_STRING = "Czas symulacji: ";
    private static final String OUTDOOR_TEMP_STRING = "Temperatura otoczenia: ";
    private static final String BACKGROUND_PICTURE_PATH = "C:\\Users\\Piotr Janus\\Desktop\\PKSS\\schemat.png";

    private static final String WINDOW_NAME = "PKSS - GUI";
    private static final String SIM_TAB_NAME = "Symulacja";
    private static final String CHART1_TAB_NAME = "Wymiennik";
    private static final String CHART2_TAB_NAME = "Budynki";
    private static final String LOG_TAB_NAME = "Logi";

    private static final String CHART1_TITLE = "Temperatury w wymienniku";
    private static final String CHART2_TITLE = "Temperatury w budynkach";

    private static final String CHART1_SERIES1_NAME = "temp. wody od dostawcy";
    private static final String CHART1_SERIES2_NAME = "temp. wody z wymiennika";
    private static final String CHART1_SERIES3_NAME = "temp. wody powrotnej do wymiennika";
    private static final String CHART1_SERIES4_NAME = "temp. wody powrotnej do dostawcy";

    private static final String CHART2_SERIES1_NAME = "temp. otoczenia";
    private static final String CHART2_SERIES2_NAME = "temp. w budynku 1";
    private static final String CHART2_SERIES3_NAME = "temp. w budynku 2";

    private static final String CHART_LEGEND_X = "czas [h]";
    private static final String CHART_LEGEND_Y = "temperatura [" + CELSIUS_DEGREE + "]";



    private static final String DEFAULT_PID_PARAM = "0.1";
    private static final String DEFAULT_DELTA_TIME = "60";
    private static final String DEFAULT_TEMP_PARAM = "25";


    Worker worker = new Worker();

    // chart 1
    XYSeries t_zmSeries;
    XYSeries t_zcoSeries;
    XYSeries t_pcoSeries;
    XYSeries t_pmSeries;

    //chart 2
    XYSeries t_oSeries;
    XYSeries t_r1Series;
    XYSeries t_r2Series;
    XYSeries t_b1Series;
    XYSeries t_b2Series;

    private JFrame frame;
    private JTabbedPane tabbedPane;

    private JTextField u_m_control;
    private JTextField f_zm_control;
    private JTextField t_zm_control;
    private JTextField t_pm_control;
    private JTextField f_zco_control;
    private JTextField t_zco_control;
    private JTextField t_pco_control;
    private JTextField t_pcob1_control;
    private JTextField t_pcob2_control;
    private JTextField tr1_control;
    private JTextField tr2_control;
    private JTextField ub1_control;
    private JTextField ub2_control;
    private JTextField fcob1_control;
    private JTextField fcbo2_control;
    private JTextField simTimeControl;
    private JTextField currTempControl;

    private JTextField kpReg1Ctrl;
    private JTextField kiReg1Ctrl;
    private JTextField kdReg1Ctrl;

    private JTextField kpB1Ctrl;
    private JTextField kiB1Ctrl;

    private JTextField kpB2Ctrl;
    private JTextField kiB2Ctrl;
    private JTextField kdB2Ctrl;

    private JTextField b1SetPointCtrl;
    private JTextField b2SetPointCtrl;


    private JTextField deltaTimeText;
    private JTextField ipAddrText;

    private JButton btnStart;
    private JButton btnStop;
    private JButton btnSave;

    private JTextArea logTextArea;

    volatile private boolean latch = true;

    /**
     * Create the application.
     */
    public GUI() {
        frame = new JFrame(WINDOW_NAME);
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        initialize();
        defineActions();
        initCharts();
        createLogPanel();

        frame.setVisible(true);

        update(new SystemData());
    }

    public void update(SystemData systemData){
        String temp;
        if(systemData.getDays() != 1)
            temp = " dni, ";
        else
            temp = " dzien, ";

        simTimeControl.setText(SIM_TIME_STRING + systemData.getDays() + temp + systemData.getHours() + " godz.");
        currTempControl.setText(OUTDOOR_TEMP_STRING + (int)systemData.getParam(SystemData.T_o) + CELSIUS_DEGREE);

        u_m_control.setText(new Integer((int)(systemData.getParam(SystemData.U_m)*100)).toString() + "%");
        f_zm_control.setText(new Integer((int)systemData.getParam(SystemData.F_zm)).toString()+FLOW_UNIT);
        t_zm_control.setText(new Integer((int)systemData.getParam(SystemData.T_zm)).toString()+CELSIUS_DEGREE);
        t_pm_control.setText(new Integer((int)systemData.getParam(SystemData.T_pm)).toString()+CELSIUS_DEGREE);
        f_zco_control.setText(new Integer((int)systemData.getParam(SystemData.F_zco)).toString()+FLOW_UNIT);
        t_zco_control.setText(new Integer((int)systemData.getParam(SystemData.T_zco)).toString()+CELSIUS_DEGREE);
        t_pco_control.setText(new Integer((int)systemData.getParam(SystemData.T_pco)).toString()+CELSIUS_DEGREE);
        t_pcob1_control.setText(new Integer((int)systemData.getParam(SystemData.T_pcob1)).toString()+CELSIUS_DEGREE);
        t_pcob2_control.setText(new Integer((int)systemData.getParam(SystemData.T_pcob2)).toString()+CELSIUS_DEGREE);
        tr1_control.setText(new Integer((int)systemData.getParam(SystemData.T_r1)).toString()+CELSIUS_DEGREE);
        tr2_control.setText(new Integer((int)systemData.getParam(SystemData.T_r2)).toString()+CELSIUS_DEGREE);
        ub1_control.setText(new Integer((int)(systemData.getParam(SystemData.U_b1)*100)).toString() + "%");
        ub2_control.setText(new Integer((int)(systemData.getParam(SystemData.U_b2)*100)).toString() + "%");
        fcob1_control.setText(new Integer((int)systemData.getParam(SystemData.F_cob1)).toString()+FLOW_UNIT);
        fcbo2_control.setText(new Integer((int)systemData.getParam(SystemData.F_cob2)).toString()+FLOW_UNIT);

        if(systemData.isFailure())
            t_zm_control.setBackground(SystemColor.RED);
        else
            t_zm_control.setBackground(SystemColor.controlHighlight);

        if(latch) {
            systemData.setParam(new Double(deltaTimeText.getText()), SystemData.TIME);

            systemData.setParam(new Double(kpReg1Ctrl.getText()), SystemData.Kp_reg1);
            systemData.setParam(new Double(kiReg1Ctrl.getText()), SystemData.Ki_reg1);
            systemData.setParam(new Double(kdReg1Ctrl.getText()), SystemData.Kd_reg1);

            systemData.setParam(new Double(kpB1Ctrl.getText()), SystemData.Kp_b1);
            systemData.setParam(new Double(kiB1Ctrl.getText()), SystemData.Ki_b1);

            systemData.setParam(new Double(kpB2Ctrl.getText()), SystemData.Kp_b2);
            systemData.setParam(new Double(kiB2Ctrl.getText()), SystemData.Ki_b2);
            systemData.setParam(new Double(kdB2Ctrl.getText()), SystemData.Kd_b2);

            systemData.setParam(new Double(b1SetPointCtrl.getText()), SystemData.T_b1);
            systemData.setParam(new Double(b2SetPointCtrl.getText()), SystemData.T_b2);
            latch = false;
        }

        double totalTime = systemData.getTotalTime();
        t_zmSeries.add(totalTime, systemData.getParam(SystemData.T_zm));
        t_zcoSeries.add(totalTime, systemData.getParam(SystemData.T_zco));
        t_pcoSeries.add(totalTime, systemData.getParam(SystemData.T_pco));
        t_pmSeries.add(totalTime, systemData.getParam(SystemData.T_pm));

        t_oSeries.add(totalTime, systemData.getParam(SystemData.T_o));
        t_r1Series.add(totalTime, systemData.getParam(SystemData.T_r1));
        t_r2Series.add(totalTime, systemData.getParam(SystemData.T_r2));
        t_b1Series.add(totalTime, systemData.getParam(SystemData.T_b1));
        t_b2Series.add(totalTime, systemData.getParam(SystemData.T_b2));

    }

    // test
    public void updateCharts(double x, double y) {
        t_oSeries.add(x, y);
        t_r1Series.add(x, y/2);

        t_pcoSeries.add(x,y);
    }

    public void setLatch(){
        latch = true;
    }

    public void clear(){

        t_pcoSeries.clear();
        t_pmSeries.clear();
        t_zcoSeries.clear();
        t_zmSeries.clear();
        t_oSeries.clear();
        t_r1Series.clear();
        t_r2Series.clear();
        t_b1Series.clear();
        t_b2Series.clear();

        logTextArea.setText("");
    }

    public void log(long time, String str){
        long h = time/60;
        long min = time%60;
        logTextArea.append(h +"godz. " + min + "min  :\n " + str + "\n\n");
    }

    private void defineActions() {
        GUI self = this;

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnStop.setEnabled(true);
                    btnStart.setEnabled(false);
                    byte[] address = InetAddress.getByName(ipAddrText.getText()).getAddress();
                    worker = new Worker(new Connector(address, Main.PORT_NUMBER), self , new SystemData());
                    worker.run();
                }catch(Exception exception){
                    System.out.println(exception);
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtons();
                worker.stop();
            }
        });

        btnSave.addActionListener(
            actionEvent -> latch = true
        );
    }

    public void resetButtons(){
        btnStop.setEnabled(false);
        btnStart.setEnabled(true);
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane);

        JPanelWithBackground simulation = new JPanelWithBackground(BACKGROUND_PICTURE_PATH);
        tabbedPane.addTab(SIM_TAB_NAME, null, simulation, null);
        simulation.setLayout(null);

        u_m_control = new JTextField();
        u_m_control.setBounds(176, 74, 40, 20);
        u_m_control.setEditable(false);
        u_m_control.setBackground(SystemColor.controlHighlight);
        simulation.add(u_m_control);
        u_m_control.setColumns(10);

        f_zm_control = new JTextField();
        f_zm_control.setBounds(231, 136, 50, 20);
        f_zm_control.setEditable(false);
        f_zm_control.setBackground(SystemColor.controlHighlight);
        simulation.add(f_zm_control);
        f_zm_control.setColumns(10);

        t_zm_control = new JTextField();
        t_zm_control.setBounds(231, 105, 50, 20);
        t_zm_control.setEditable(false);
        t_zm_control.setBackground(SystemColor.controlHighlight);
        t_zm_control.setColumns(10);
        simulation.add(t_zm_control);

        t_pm_control = new JTextField();
        t_pm_control.setBounds(231, 193, 60, 20);
        t_pm_control.setEditable(false);
        t_pm_control.setBackground(SystemColor.controlHighlight);
        t_pm_control.setColumns(10);
        simulation.add(t_pm_control);

        f_zco_control = new JTextField();
        f_zco_control.setBounds(346, 74, 60, 20);
        f_zco_control.setEditable(false);
        f_zco_control.setBackground(SystemColor.controlHighlight);
        f_zco_control.setColumns(10);
        simulation.add(f_zco_control);

        t_zco_control = new JTextField();
        t_zco_control.setBounds(346, 43, 60, 20);
        t_zco_control.setEditable(false);
        t_zco_control.setBackground(SystemColor.controlHighlight);
        t_zco_control.setColumns(10);
        simulation.add(t_zco_control);

        t_pco_control = new JTextField();
        t_pco_control.setBounds(403, 181, 50, 20);
        t_pco_control.setEditable(false);
        t_pco_control.setBackground(SystemColor.controlHighlight);
        t_pco_control.setColumns(10);
        simulation.add(t_pco_control);

        t_pcob1_control = new JTextField();
        t_pcob1_control.setBounds(550, 181, 50, 20);
        t_pcob1_control.setBackground(SystemColor.text);
        t_pcob1_control.setEditable(false);
        t_pcob1_control.setColumns(10);
        simulation.add(t_pcob1_control);

        t_pcob2_control = new JTextField();
        t_pcob2_control.setBounds(550, 443, 50, 20);
        t_pcob2_control.setBackground(SystemColor.text);
        t_pcob2_control.setEditable(false);
        t_pcob2_control.setColumns(10);
        simulation.add(t_pcob2_control);

        tr1_control = new JTextField();
        tr1_control.setBounds(657, 148, 50, 20);
        tr1_control.setBackground(SystemColor.text);
        tr1_control.setEditable(false);
        tr1_control.setColumns(10);
        simulation.add(tr1_control);

        tr2_control = new JTextField();
        tr2_control.setBounds(657, 408, 50, 20);
        tr2_control.setBackground(SystemColor.text);
        tr2_control.setEditable(false);
        tr2_control.setColumns(10);
        simulation.add(tr2_control);

        ub1_control = new JTextField();
        ub1_control.setBounds(573, 43, 40, 20);
        ub1_control.setEditable(false);
        ub1_control.setColumns(10);
        ub1_control.setBackground(SystemColor.text);
        simulation.add(ub1_control);

        ub2_control = new JTextField();
        ub2_control.setBounds(573, 304, 40, 20);
        ub2_control.setEditable(false);
        ub2_control.setColumns(10);
        ub2_control.setBackground(SystemColor.text);
        simulation.add(ub2_control);

        fcob1_control = new JTextField();
        fcob1_control.setBounds(583, 89, 60, 20);
        fcob1_control.setEditable(false);
        fcob1_control.setColumns(10);
        fcob1_control.setBackground(SystemColor.text);
        simulation.add(fcob1_control);

        fcbo2_control = new JTextField();
        fcbo2_control.setBounds(583, 351, 60, 20);
        fcbo2_control.setEditable(false);
        fcbo2_control.setColumns(10);
        fcbo2_control.setBackground(Color.WHITE);
        simulation.add(fcbo2_control);

        simTimeControl = new JTextField();
        simTimeControl.setBounds(10, 11, 180, 20);
        simTimeControl.setEditable(false);
        simulation.add(simTimeControl);
        simTimeControl.setColumns(10);

        currTempControl = new JTextField();
        currTempControl.setBounds(10, 43, 180, 20);
        currTempControl.setEditable(false);
        simulation.add(currTempControl);
        currTempControl.setColumns(10);

        JSeparator separator = new JSeparator();
        separator.setForeground(SystemColor.desktop);
        separator.setBounds(10, 250, 443, 7);
        simulation.add(separator);

        JSeparator separator_2 = new JSeparator();
        separator_2.setForeground(SystemColor.desktop);
        separator_2.setOrientation(SwingConstants.VERTICAL);
        separator_2.setBounds(453, 250, 2, 275);
        simulation.add(separator_2);

        Label ipAddrLabel = new Label("adres ip serwera:");
        ipAddrLabel.setBackground(SystemColor.window);
        ipAddrLabel.setBounds(247, 268, 108, 16);
        simulation.add(ipAddrLabel);

        ipAddrText = new JTextField();
        ipAddrText.setBackground(SystemColor.controlHighlight);
        ipAddrText.setBounds(356, 268, 90, 16);
        simulation.add(ipAddrText);
        ipAddrText.setText(Main.DEFAULT_IP_ADDR);

        btnStart = new JButton("Start");
        btnStart.setBounds(10, 268, 70, 16);
        simulation.add(btnStart);

        btnStop = new JButton("Stop");
        btnStop.setEnabled(false);
        btnStop.setBounds(90, 268, 70, 16);
        simulation.add(btnStop);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 291, 433, 16);
        simulation.add(separator_1);

        deltaTimeText = new JTextField();
        deltaTimeText.setBackground(SystemColor.controlHighlight);
        deltaTimeText.setBounds(130, 307, 78, 16);
        simulation.add(deltaTimeText);
        deltaTimeText.setText(DEFAULT_DELTA_TIME);

        Label timeConstLabel = new Label("delta czasowa (min) :");
        timeConstLabel.setBackground(SystemColor.text);
        timeConstLabel.setBounds(10, 308, 114, 16);
        simulation.add(timeConstLabel);

        JSeparator separator_3 = new JSeparator();
        separator_3.setBounds(10, 332, 206, 7);
        simulation.add(separator_3);


        //--------- B1 ------------------------------------------------
        Label KpB1Label = new Label("Kp b1: ");
        KpB1Label.setBackground(SystemColor.text);
        KpB1Label.setBounds(12, 435, 50, 16);
        simulation.add(KpB1Label);

        Label KiB1Label = new Label("Ki b1: ");
        KiB1Label.setBackground(SystemColor.text);
        KiB1Label.setBounds(12, 457, 50, 16);
        simulation.add(KiB1Label);

        kpB1Ctrl = new JTextField();
        kpB1Ctrl.setBackground(SystemColor.controlHighlight);
        kpB1Ctrl.setBounds(68, 435, 50, 16);
        kpB1Ctrl.setText("0.5");
        simulation.add(kpB1Ctrl);

        kiB1Ctrl = new JTextField();
        kiB1Ctrl.setBackground(SystemColor.controlHighlight);
        kiB1Ctrl.setBounds(68, 457, 50, 16);
        kiB1Ctrl.setText("0.00025");
        simulation.add(kiB1Ctrl);


        //------------- B2 ---------------------------------------
        Label kpB2Label = new Label("Kp b2:");
        kpB2Label.setBackground(Color.WHITE);
        kpB2Label.setBounds(132, 435, 50, 16);
        simulation.add(kpB2Label);

        Label kiB2Label = new Label("Ki b2:");
        kiB2Label.setBackground(Color.WHITE);
        kiB2Label.setBounds(132, 457, 50, 16);
        simulation.add(kiB2Label);

        Label kdB2Label = new Label("Kd b2:");
        kdB2Label.setBackground(Color.WHITE);
        kdB2Label.setBounds(132, 479, 50, 16);
        simulation.add(kdB2Label);

        kpB2Ctrl = new JTextField();
        kpB2Ctrl.setBackground(SystemColor.controlHighlight);
        kpB2Ctrl.setBounds(188, 435, 50, 16);
        kpB2Ctrl.setText("0.5");
        simulation.add(kpB2Ctrl);

        kiB2Ctrl = new JTextField();
        kiB2Ctrl.setBackground(SystemColor.controlHighlight);
        kiB2Ctrl.setText("0.00025");
        kiB2Ctrl.setBounds(188, 457, 50, 16);

        simulation.add(kiB2Ctrl);

        kdB2Ctrl = new JTextField();
        kdB2Ctrl.setBackground(SystemColor.controlHighlight);
        kdB2Ctrl.setBounds(188, 479, 50, 16);
        kdB2Ctrl.setText("30");
        simulation.add(kdB2Ctrl);


        // -------- REG -------------------------------------------------------
        Label kpRegLabel = new Label("Kp reg:");
        kpRegLabel.setBackground(Color.WHITE);
        kpRegLabel.setBounds(255, 435, 50, 16);
        simulation.add(kpRegLabel);

        kpReg1Ctrl = new JTextField();
        kpReg1Ctrl.setBackground(SystemColor.controlHighlight);
        kpReg1Ctrl.setBounds(305, 435, 50, 16);
        kpReg1Ctrl.setText("0");
        simulation.add(kpReg1Ctrl);

        Label kiRegLabel = new Label("Ki reg:");
        kiRegLabel.setBackground(Color.WHITE);
        kiRegLabel.setBounds(255, 457, 50, 16);
        simulation.add(kiRegLabel);

        kiReg1Ctrl = new JTextField();
        kiReg1Ctrl.setBackground(SystemColor.controlHighlight);
        kiReg1Ctrl.setBounds(305, 457, 50, 16);
        kiReg1Ctrl.setText("0.02");
        simulation.add(kiReg1Ctrl);

        kdReg1Ctrl = new JTextField();
        kdReg1Ctrl.setBackground(SystemColor.controlHighlight);
        kdReg1Ctrl.setBounds(305, 479, 50, 16);
        kdReg1Ctrl.setText("0");
        simulation.add(kdReg1Ctrl);

        Label kdRegLabel = new Label("Kd reg:");
        kdRegLabel.setBackground(Color.WHITE);
        kdRegLabel.setBounds(255, 479, 50, 16);
        simulation.add(kdRegLabel);

        // --------- bud 1---------------------------------
        Label b1TLabel = new Label("temperatura w budynku 1:");
        b1TLabel.setBackground(Color.WHITE);
        b1TLabel.setBounds(10, 351, 145, 16);
        simulation.add(b1TLabel);

        Label b2TLabel = new Label("temperatura w budynku 2:");
        b2TLabel.setBackground(Color.WHITE);
        b2TLabel.setBounds(10, 373, 145, 16);
        simulation.add(b2TLabel);

        b2SetPointCtrl = new JTextField();
        b2SetPointCtrl.setBackground(SystemColor.controlHighlight);
        b2SetPointCtrl.setBounds(160, 373, 50, 16);
        b2SetPointCtrl.setText(DEFAULT_TEMP_PARAM);
        simulation.add(b2SetPointCtrl);

        b1SetPointCtrl = new JTextField();
        b1SetPointCtrl.setBackground(SystemColor.controlHighlight);
        b1SetPointCtrl.setBounds(160, 351, 50, 16);
        b1SetPointCtrl.setText(DEFAULT_TEMP_PARAM);
        simulation.add(b1SetPointCtrl);

        JSeparator separator_4 = new JSeparator();
        separator_4.setBounds(10, 398, 433, 8);
        simulation.add(separator_4);

        Label regB1Label = new Label("regulator budynku 1");
        regB1Label.setBackground(Color.WHITE);
        regB1Label.setBounds(10, 412, 115, 16);
        simulation.add(regB1Label);

        Label regB2Label = new Label("regulator budynku 2");
        regB2Label.setBackground(Color.WHITE);
        regB2Label.setBounds(132, 412, 115, 16);
        simulation.add(regB2Label);

        Label reg1Label = new Label("regulator wymiennika");
        reg1Label.setBackground(Color.WHITE);
        reg1Label.setBounds(255, 412, 125, 16);
        simulation.add(reg1Label);

        btnSave = new JButton("zapisz parametry");
        btnSave.setBounds(132, 509, 140, 16);
        simulation.add(btnSave);
    }

    private void initCharts(){
        JPanel chartPanel1 = new JPanel();
        tabbedPane.addTab(CHART1_TAB_NAME, null, chartPanel1, null);
        chartPanel1.setLayout(new BorderLayout());

        JPanel chartPanel2 = new JPanel();
        tabbedPane.addTab(CHART2_TAB_NAME, null, chartPanel2, null);
        chartPanel2.setLayout(new BorderLayout());

        t_zmSeries = new XYSeries(CHART1_SERIES1_NAME);
        t_zcoSeries = new XYSeries(CHART1_SERIES2_NAME);
        t_pcoSeries = new XYSeries(CHART1_SERIES3_NAME);
        t_pmSeries = new XYSeries(CHART1_SERIES4_NAME);

        t_oSeries = new XYSeries(CHART2_SERIES1_NAME);
        t_r1Series = new XYSeries(CHART2_SERIES2_NAME);
        t_r2Series = new XYSeries(CHART2_SERIES3_NAME);

        t_b1Series = new XYSeries("wart. zadana b1");
        t_b2Series = new XYSeries("wart. zadana b2");

        XYSeriesCollection data1 = new XYSeriesCollection();
        data1.addSeries(t_zmSeries);
        data1.addSeries(t_zcoSeries);
        data1.addSeries(t_pcoSeries);
        data1.addSeries(t_pmSeries);

        final JFreeChart chart1 = ChartFactory.createXYLineChart(
                CHART1_TITLE,
                CHART_LEGEND_X,
                CHART_LEGEND_Y,
                data1,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        final ChartPanel cp1 = new ChartPanel(chart1);
        chartPanel1.add(cp1, BorderLayout.CENTER);
        chartPanel1.validate();

        XYSeriesCollection data2 = new XYSeriesCollection();
        data2.addSeries(t_oSeries);
        data2.addSeries(t_r1Series);
        data2.addSeries(t_r2Series);
        data2.addSeries(t_b1Series);
        data2.addSeries(t_b2Series);

        final JFreeChart chart2 = ChartFactory.createXYLineChart(
                CHART2_TITLE,
                CHART_LEGEND_X,
                CHART_LEGEND_Y,
                data2,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        final ChartPanel cp2 = new ChartPanel(chart2);
        chartPanel2.add(cp2, BorderLayout.CENTER);
        chartPanel2.validate();
    }

    private void createLogPanel(){
        Box verticalBox = Box.createVerticalBox();
        verticalBox.setAlignmentY(Component.TOP_ALIGNMENT);
        verticalBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        tabbedPane.addTab(LOG_TAB_NAME, null, verticalBox, null);

        logTextArea = new JTextArea();
        logTextArea.setText("");
        logTextArea.setEditable(false);
        logTextArea.setAutoscrolls(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(logTextArea);
        verticalBox.add(sp);
    }

}
