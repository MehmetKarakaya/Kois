/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kois.pkg3;

import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;



/**
 *
 * @author Ersn
 */
public class anasayfa extends javax.swing.JFrame implements SerialPortEventListener{

    /**
     * @param aComPortName the comPortName to set
     */
    public static void setComPortName(String aComPortName) {
        comPortName = aComPortName;
    }

    String inputLine;
    String[] readArray = new String[100];
    SerialPort serialPort;
    int i = 0;
    double sicaklikC = 25;
    double sicaklikF = 77;
    double basinc = 90000;
    double rakimM = 500;
    double rakimF = 1640;
    double nem = 30;
    double gaz = 50;
    double yangin = 0;
    String kullanici_adi;
    String sifre;
    String yetki;
    boolean giris = false;
    
    DecimalFormat df = new DecimalFormat("###,###");
    
     private static String comPortName = "COM6";//BU BİLGİYİ FORM DAN DA ALABİLİRİZ

    public static String getComPortName() {
        return comPortName;
    }
    private static final String PORT_NAMES[] = {"COM6",};

    private BufferedReader input;
    private static OutputStream output;
    private static int TIME_OUT = 1000;
    private static int DATA_RATE = 57600;
    
     public void initialize() {
        System.setProperty("gnu.io.rxtx.SerialPorts", "COM6");        
        CommPortIdentifier portId = null;        
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    System.out.println(portName);
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            
            JOptionPane.showMessageDialog(null," PORTUNA BAĞLI CİHAZ YOK!","HATA",JOptionPane.ERROR_MESSAGE);
            System.out.println("PORTA BAĞLI CİHAZ YOK!");
            return;
        }
System.out.println(portId);
        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
           serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
DefaultListModel model = new DefaultListModel();    
    veriTabani vt = new veriTabani();
    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {

        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                if (input.ready() == true) {
                    //**********************************************
                    //ARDUINO dan SERİ PORTTAN GELEN VERİ BURADAN KONTROL EDİLİR
                    //**********************************************
                    inputLine = input.readLine();
                    
                    String[] parts = inputLine.split(";");
                    
                    
                    Object node = inputLine;
                    model.addElement(node);
                    if(parts.length == 8) {
                        
                        vt.veriEkle(parts);
                        System.out.println("--------------------");
                        System.out.println("parts[0] : " + parts[0]);
                        System.out.println("parts[1] : " + parts[1]);
                        System.out.println("parts[2] : " + parts[2]);
                        System.out.println("parts[3] : " + parts[3]);
                        System.out.println("parts[4] : " + parts[4]);
                        System.out.println("parts[5] : " + parts[5]);   
                        System.out.println("parts[6] : " + parts[6]);
                        System.out.println("parts[7] : " + parts[7]); 
                        
                        jProgressBar_dereceC.setValue((int)  Double.parseDouble(parts[0]));
                        jProgressBar_dereceC.setString(parts[0]);
                        jProgressBar_dereceF.setValue((int)  Double.parseDouble(parts[1]));
                        jProgressBar_dereceF.setString(parts[1]);
                        jProgressBar_basinc.setValue((int)  Double.parseDouble(parts[2]));
                        jProgressBar_basinc.setString(parts[2]);
                        jProgressBar_rakimM.setValue((int)  Double.parseDouble(parts[3]));
                        jProgressBar_rakimM.setString(parts[3]);
                        jProgressBar_rakimF.setValue((int)  Double.parseDouble(parts[4]));
                        jProgressBar_rakimF.setString(parts[4]);
                        jProgressBar_nem.setValue((int)  Double.parseDouble(parts[5]));
                        jProgressBar_nem.setString(parts[5]);
                        jProgressBar_gaz.setValue((int)  Double.parseDouble(parts[6]));
                        jProgressBar_gaz.setString(parts[6]);
                        jProgressBar_yangin.setValue((int)  Double.parseDouble(parts[7]));
                        
                        
                        if(parts[7].equals("1")){
                            jProgressBar_yangin.setString("Yangin Var");
                        }
                        else
                            jProgressBar_yangin.setString("Yangin Yok");
                        
                        if(Double.parseDouble(parts[0]) > sicaklikC){
                            dereceC_Y.setVisible(false);
                            dereceC_D.setVisible(true);
                            dereceC_S.setVisible(false);
                            
                            String serialMessage = "1";
                            output.write(serialMessage.getBytes());
                        }
                        else if(Double.parseDouble(parts[0]) < sicaklikC){
                            dereceC_Y.setVisible(true);
                            dereceC_D.setVisible(false);
                            dereceC_S.setVisible(false);
                            
                            String serialMessage = "2";
                            output.write(serialMessage.getBytes());
                        }
                        else{
                            dereceC_Y.setVisible(false);
                            dereceC_D.setVisible(false);
                            dereceC_S.setVisible(true);
                        }
                        
                        if(Double.parseDouble(parts[1])<sicaklikF){
                            dereceF_Y.setVisible(false);
                            dereceF_D.setVisible(true);
                            dereceF_S.setVisible(false);
                        }
                        else if(Double.parseDouble(parts[1])>sicaklikF){
                            dereceF_Y.setVisible(true);
                            dereceF_D.setVisible(false);
                            dereceF_S.setVisible(false);
                        }
                        else{
                            dereceF_Y.setVisible(false);
                            dereceF_D.setVisible(false);
                            dereceF_S.setVisible(true);
                        }
                        
                        
                        if(Double.parseDouble(parts[2])<basinc){
                            basinc_Y.setVisible(false);
                            basinc_D.setVisible(true);
                            basinc_S.setVisible(false);
                        }
                        else if(Double.parseDouble(parts[2])>basinc){
                            basinc_Y.setVisible(true);
                            basinc_D.setVisible(false);
                            basinc_S.setVisible(false);
                        }
                        else{
                            basinc_Y.setVisible(false);
                            basinc_D.setVisible(false);
                            basinc_S.setVisible(true);
                        }
                         if(Double.parseDouble(parts[3])<rakimM){
                            rakimM_Y.setVisible(false);
                            rakimM_D.setVisible(true);
                            rakimM_S.setVisible(false);
                        }
                        else if(Double.parseDouble(parts[3])>rakimM){
                            rakimM_Y.setVisible(true);
                            rakimM_D.setVisible(false);
                            rakimM_S.setVisible(false);
                        }
                        else{
                            rakimM_Y.setVisible(false);
                            rakimM_D.setVisible(false);
                            rakimM_S.setVisible(true);
                        }
                        if(Double.parseDouble(parts[4])<rakimF){
                            rakimM_Y.setVisible(false);
                            rakimM_D.setVisible(true);
                            rakimM_S.setVisible(false);
                        }
                        else if(Double.parseDouble(parts[4])>rakimF){
                            rakimF_Y.setVisible(true);
                            rakimF_D.setVisible(false);
                            rakimF_S.setVisible(false);
                        }
                        else{
                            rakimF_Y.setVisible(false);
                            rakimF_D.setVisible(false);
                            rakimF_S.setVisible(true);
                        }
                        if(Double.parseDouble(parts[5])<nem){
                            nem_Y.setVisible(false);
                            nem_D.setVisible(true);
                            nem_S.setVisible(false);
                        }
                        else if(Double.parseDouble(parts[5])>nem){
                            nem_Y.setVisible(true);
                            nem_D.setVisible(false);
                            nem_S.setVisible(false);
                        }
                        else{
                            nem_Y.setVisible(false);
                            nem_D.setVisible(false);
                            nem_S.setVisible(true);
                        }
                        if(Double.parseDouble(parts[6])<gaz){
                            gaz_Y.setVisible(false);
                            gaz_D.setVisible(true);
                            gaz_S.setVisible(false);
                        }
                        else if(Double.parseDouble(parts[6])>gaz){
                            gaz_Y.setVisible(true);
                            gaz_D.setVisible(false);
                            gaz_S.setVisible(false);
                        }
                        else{
                            gaz_Y.setVisible(false);
                            gaz_D.setVisible(false);
                            gaz_S.setVisible(true);
                        }
                        if(Double.parseDouble(parts[7])<yangin){
                            yangin_Y.setVisible(false);
                            yangin_D.setVisible(true);
                            yangin_S.setVisible(false);
                        }
                        else if(Double.parseDouble(parts[7])>yangin){
                            yangin_Y.setVisible(true);
                            yangin_D.setVisible(false);
                            yangin_S.setVisible(false);
                        }
                        else{
                            yangin_Y.setVisible(false);
                            yangin_D.setVisible(false);
                            yangin_S.setVisible(true);
                        }
                        
                        
                    }
                } else {
              }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }

    }

    public anasayfa() {
        this.setResizable(false);
        initComponents();
        this.setSize(700, 410);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        panel_Bilesen_Guncelle.setVisible(false);
        panel_Veri_ekle.setVisible(false);
        panel_Veri_ekle1.setVisible(false);
        panel_Giris_Yap.setVisible(false);
        panel_Kullanici_ekle.setVisible(false);
        label_kullaniciEkle.setVisible(false);
        
        dereceC_Y.setVisible(false);
        dereceC_D.setVisible(false);
        dereceC_S.setVisible(true);
        dereceF_Y.setVisible(false);
        dereceF_D.setVisible(false);
        dereceF_S.setVisible(true);
        basinc_Y.setVisible(false);
        basinc_D.setVisible(false);
        basinc_S.setVisible(true);
        rakimM_Y.setVisible(false);
        rakimM_D.setVisible(false);
        rakimM_S.setVisible(true);
        nem_Y.setVisible(false);
        nem_D.setVisible(false);
        nem_S.setVisible(true);
        gaz_Y.setVisible(false);
        gaz_D.setVisible(false);
        gaz_S.setVisible(true);
        yangin_Y.setVisible(false);
        yangin_D.setVisible(false);
        yangin_S.setVisible(true);
        rakimF_Y.setVisible(false);
        rakimF_D.setVisible(false);
        rakimF_S.setVisible(true);
        
        
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        AnaPanel = new javax.swing.JPanel();
        panel_Kullanici_ekle = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        label_temizle1 = new javax.swing.JLabel();
        label_kullanici_ekle = new javax.swing.JLabel();
        jTextField_kullaniciAdi1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_yetki = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_sifre1 = new javax.swing.JPasswordField();
        panel_Giris_Yap = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        label_temizle = new javax.swing.JLabel();
        label_girisYap = new javax.swing.JLabel();
        jTextField_kullaniciAdi = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_sifre = new javax.swing.JPasswordField();
        panel_Veri_ekle = new javax.swing.JPanel();
        jProgressBar_dereceC = new javax.swing.JProgressBar();
        jProgressBar_dereceF = new javax.swing.JProgressBar();
        jProgressBar_basinc = new javax.swing.JProgressBar();
        jProgressBar_rakimM = new javax.swing.JProgressBar();
        jProgressBar_rakimF = new javax.swing.JProgressBar();
        jProgressBar_nem = new javax.swing.JProgressBar();
        jProgressBar_gaz = new javax.swing.JProgressBar();
        jProgressBar_yangin = new javax.swing.JProgressBar();
        dereceC_Y = new javax.swing.JLabel();
        dereceC_D = new javax.swing.JLabel();
        dereceF_Y = new javax.swing.JLabel();
        dereceF_D = new javax.swing.JLabel();
        basinc_D = new javax.swing.JLabel();
        basinc_Y = new javax.swing.JLabel();
        rakimM_D = new javax.swing.JLabel();
        rakimM_Y = new javax.swing.JLabel();
        rakimF_D = new javax.swing.JLabel();
        rakimF_Y = new javax.swing.JLabel();
        nem_D = new javax.swing.JLabel();
        nem_Y = new javax.swing.JLabel();
        gaz_Y = new javax.swing.JLabel();
        gaz_D = new javax.swing.JLabel();
        yangin_Y = new javax.swing.JLabel();
        yangin_D = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        dereceC_S = new javax.swing.JLabel();
        dereceF_S = new javax.swing.JLabel();
        basinc_S = new javax.swing.JLabel();
        rakimM_S = new javax.swing.JLabel();
        rakimF_S = new javax.swing.JLabel();
        nem_S = new javax.swing.JLabel();
        gaz_S = new javax.swing.JLabel();
        yangin_S = new javax.swing.JLabel();
        panel_Veri_ekle1 = new javax.swing.JPanel();
        jProgressBar_dereceC1 = new javax.swing.JProgressBar();
        jProgressBar_dereceF1 = new javax.swing.JProgressBar();
        jProgressBar_basinc1 = new javax.swing.JProgressBar();
        jProgressBar_rakimM1 = new javax.swing.JProgressBar();
        jProgressBar_rakimF1 = new javax.swing.JProgressBar();
        jProgressBar_nem1 = new javax.swing.JProgressBar();
        jProgressBar_gaz1 = new javax.swing.JProgressBar();
        jProgressBar_yangin1 = new javax.swing.JProgressBar();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        label_minimum = new javax.swing.JLabel();
        label_ortalama = new javax.swing.JLabel();
        label_maximum = new javax.swing.JLabel();
        jCmb_Tarih = new javax.swing.JComboBox<>();
        panel_Bilesen_Guncelle = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField_sicaklikC = new javax.swing.JTextField();
        jTextField_sicaklikF = new javax.swing.JTextField();
        jTextField_basinc = new javax.swing.JTextField();
        jTextField_rakimM = new javax.swing.JTextField();
        jTextField_rakimF = new javax.swing.JTextField();
        jTextField_nem = new javax.swing.JTextField();
        jTextField_gaz = new javax.swing.JTextField();
        jTextField_yangin = new javax.swing.JTextField();
        label_degiskenKaydet = new javax.swing.JLabel();
        label_baglan = new javax.swing.JLabel();
        label_degiskenBelirle = new javax.swing.JLabel();
        jLabel_kullanici_adi = new javax.swing.JLabel();
        jLabel_sifre = new javax.swing.JLabel();
        label_arama = new javax.swing.JLabel();
        label_kullaniciEkle = new javax.swing.JLabel();
        label_cikisYap = new javax.swing.JLabel();
        labelBackgraund = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setResizable(false);
        setSize(new java.awt.Dimension(500, 500));
        getContentPane().setLayout(null);

        AnaPanel.setBackground(new java.awt.Color(255, 255, 255));
        AnaPanel.setLayout(null);

        panel_Kullanici_ekle.setBackground(new java.awt.Color(255, 255, 255));
        panel_Kullanici_ekle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 10, true));
        panel_Kullanici_ekle.setLayout(null);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Kullanıcı Adı   :");
        panel_Kullanici_ekle.add(jLabel32);
        jLabel32.setBounds(120, 30, 130, 15);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Sifre              :");
        jLabel33.setToolTipText("");
        panel_Kullanici_ekle.add(jLabel33);
        jLabel33.setBounds(120, 70, 130, 15);

        label_temizle1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_temizle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Trash Empty.png"))); // NOI18N
        label_temizle1.setText("Temizle");
        label_temizle1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_temizle1.setDoubleBuffered(true);
        label_temizle1.setName(""); // NOI18N
        label_temizle1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_temizle1click(evt);
            }
        });
        panel_Kullanici_ekle.add(label_temizle1);
        label_temizle1.setBounds(390, 130, 100, 50);

        label_kullanici_ekle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_kullanici_ekle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save.png"))); // NOI18N
        label_kullanici_ekle.setText("Kaydet");
        label_kullanici_ekle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_kullanici_ekle.setDoubleBuffered(true);
        label_kullanici_ekle.setName(""); // NOI18N
        label_kullanici_ekle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_kullanici_ekleclick(evt);
            }
        });
        panel_Kullanici_ekle.add(label_kullanici_ekle);
        label_kullanici_ekle.setBounds(280, 140, 90, 32);

        jTextField_kullaniciAdi1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panel_Kullanici_ekle.add(jTextField_kullaniciAdi1);
        jTextField_kullaniciAdi1.setBounds(280, 30, 190, 20);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/User.png"))); // NOI18N
        panel_Kullanici_ekle.add(jLabel3);
        jLabel3.setBounds(240, 20, 30, 30);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lock.png"))); // NOI18N
        panel_Kullanici_ekle.add(jLabel4);
        jLabel4.setBounds(240, 60, 30, 40);

        jComboBox_yetki.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jComboBox_yetki.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Personel", "Yonetici" }));
        panel_Kullanici_ekle.add(jComboBox_yetki);
        jComboBox_yetki.setBounds(280, 110, 190, 20);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Yetki              :");
        jLabel34.setToolTipText("");
        panel_Kullanici_ekle.add(jLabel34);
        jLabel34.setBounds(120, 110, 130, 15);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Help Blue Button.png"))); // NOI18N
        panel_Kullanici_ekle.add(jLabel5);
        jLabel5.setBounds(240, 100, 30, 40);
        panel_Kullanici_ekle.add(jTextField_sifre1);
        jTextField_sifre1.setBounds(280, 70, 190, 20);

        AnaPanel.add(panel_Kullanici_ekle);
        panel_Kullanici_ekle.setBounds(48, 100, 590, 200);

        panel_Giris_Yap.setBackground(new java.awt.Color(255, 255, 255));
        panel_Giris_Yap.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 10, true));
        panel_Giris_Yap.setVerifyInputWhenFocusTarget(false);
        panel_Giris_Yap.setLayout(null);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Kullanıcı Adı   :");
        panel_Giris_Yap.add(jLabel30);
        jLabel30.setBounds(120, 50, 130, 15);

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Sifre              :");
        jLabel31.setToolTipText("");
        panel_Giris_Yap.add(jLabel31);
        jLabel31.setBounds(120, 90, 130, 15);

        label_temizle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_temizle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Trash Empty.png"))); // NOI18N
        label_temizle.setText("Temizle");
        label_temizle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_temizle.setDoubleBuffered(true);
        label_temizle.setName(""); // NOI18N
        label_temizle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_temizleclick(evt);
            }
        });
        panel_Giris_Yap.add(label_temizle);
        label_temizle.setBounds(390, 130, 100, 50);

        label_girisYap.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_girisYap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/power-512.png"))); // NOI18N
        label_girisYap.setText("Giriş");
        label_girisYap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_girisYap.setDoubleBuffered(true);
        label_girisYap.setName(""); // NOI18N
        label_girisYap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_girisYapclick(evt);
            }
        });
        panel_Giris_Yap.add(label_girisYap);
        label_girisYap.setBounds(280, 130, 100, 50);

        jTextField_kullaniciAdi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panel_Giris_Yap.add(jTextField_kullaniciAdi);
        jTextField_kullaniciAdi.setBounds(280, 50, 190, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/User.png"))); // NOI18N
        panel_Giris_Yap.add(jLabel1);
        jLabel1.setBounds(240, 40, 30, 30);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lock.png"))); // NOI18N
        panel_Giris_Yap.add(jLabel2);
        jLabel2.setBounds(240, 80, 30, 40);
        panel_Giris_Yap.add(jTextField_sifre);
        jTextField_sifre.setBounds(280, 90, 190, 20);

        AnaPanel.add(panel_Giris_Yap);
        panel_Giris_Yap.setBounds(48, 100, 590, 200);

        panel_Veri_ekle.setBackground(new java.awt.Color(255, 255, 255));
        panel_Veri_ekle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 10, true));
        panel_Veri_ekle.setLayout(null);

        jProgressBar_dereceC.setMinimum(-40);
        jProgressBar_dereceC.setValue(-40);
        jProgressBar_dereceC.setDoubleBuffered(true);
        jProgressBar_dereceC.setString("%0");
        jProgressBar_dereceC.setStringPainted(true);
        panel_Veri_ekle.add(jProgressBar_dereceC);
        jProgressBar_dereceC.setBounds(250, 20, 146, 17);

        jProgressBar_dereceF.setMaximum(300);
        jProgressBar_dereceF.setStringPainted(true);
        panel_Veri_ekle.add(jProgressBar_dereceF);
        jProgressBar_dereceF.setBounds(250, 40, 146, 17);

        jProgressBar_basinc.setMaximum(1000000);
        jProgressBar_basinc.setStringPainted(true);
        panel_Veri_ekle.add(jProgressBar_basinc);
        jProgressBar_basinc.setBounds(250, 60, 146, 17);

        jProgressBar_rakimM.setMaximum(100000);
        jProgressBar_rakimM.setStringPainted(true);
        panel_Veri_ekle.add(jProgressBar_rakimM);
        jProgressBar_rakimM.setBounds(250, 80, 146, 17);

        jProgressBar_rakimF.setMaximum(300000);
        jProgressBar_rakimF.setStringPainted(true);
        panel_Veri_ekle.add(jProgressBar_rakimF);
        jProgressBar_rakimF.setBounds(250, 100, 146, 17);

        jProgressBar_nem.setStringPainted(true);
        panel_Veri_ekle.add(jProgressBar_nem);
        jProgressBar_nem.setBounds(250, 120, 146, 17);

        jProgressBar_gaz.setMaximum(1000);
        jProgressBar_gaz.setStringPainted(true);
        panel_Veri_ekle.add(jProgressBar_gaz);
        jProgressBar_gaz.setBounds(250, 140, 146, 17);

        jProgressBar_yangin.setMaximum(1);
        jProgressBar_yangin.setStringPainted(true);
        panel_Veri_ekle.add(jProgressBar_yangin);
        jProgressBar_yangin.setBounds(250, 160, 146, 17);

        dereceC_Y.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/up.png"))); // NOI18N
        panel_Veri_ekle.add(dereceC_Y);
        dereceC_Y.setBounds(400, 20, 70, 15);

        dereceC_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/down.png"))); // NOI18N
        panel_Veri_ekle.add(dereceC_D);
        dereceC_D.setBounds(400, 20, 70, 15);

        dereceF_Y.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/up.png"))); // NOI18N
        panel_Veri_ekle.add(dereceF_Y);
        dereceF_Y.setBounds(400, 40, 70, 15);

        dereceF_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/down.png"))); // NOI18N
        panel_Veri_ekle.add(dereceF_D);
        dereceF_D.setBounds(400, 40, 70, 15);

        basinc_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/down.png"))); // NOI18N
        panel_Veri_ekle.add(basinc_D);
        basinc_D.setBounds(400, 60, 70, 15);

        basinc_Y.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/up.png"))); // NOI18N
        panel_Veri_ekle.add(basinc_Y);
        basinc_Y.setBounds(400, 60, 70, 15);

        rakimM_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/down.png"))); // NOI18N
        panel_Veri_ekle.add(rakimM_D);
        rakimM_D.setBounds(400, 80, 70, 15);

        rakimM_Y.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/up.png"))); // NOI18N
        panel_Veri_ekle.add(rakimM_Y);
        rakimM_Y.setBounds(400, 80, 70, 15);

        rakimF_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/down.png"))); // NOI18N
        panel_Veri_ekle.add(rakimF_D);
        rakimF_D.setBounds(400, 100, 70, 15);

        rakimF_Y.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/up.png"))); // NOI18N
        panel_Veri_ekle.add(rakimF_Y);
        rakimF_Y.setBounds(400, 100, 70, 15);

        nem_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/down.png"))); // NOI18N
        panel_Veri_ekle.add(nem_D);
        nem_D.setBounds(400, 120, 70, 15);

        nem_Y.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/up.png"))); // NOI18N
        panel_Veri_ekle.add(nem_Y);
        nem_Y.setBounds(400, 120, 70, 15);

        gaz_Y.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/up.png"))); // NOI18N
        panel_Veri_ekle.add(gaz_Y);
        gaz_Y.setBounds(400, 140, 70, 15);

        gaz_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/down.png"))); // NOI18N
        panel_Veri_ekle.add(gaz_D);
        gaz_D.setBounds(400, 140, 70, 15);

        yangin_Y.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/up.png"))); // NOI18N
        panel_Veri_ekle.add(yangin_Y);
        yangin_Y.setBounds(400, 160, 70, 15);

        yangin_D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/down.png"))); // NOI18N
        panel_Veri_ekle.add(yangin_D);
        yangin_D.setBounds(400, 160, 70, 15);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Sıcaklık Derece");
        panel_Veri_ekle.add(jLabel6);
        jLabel6.setBounds(110, 20, 130, 15);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Sıcaklık Fahrenheit");
        panel_Veri_ekle.add(jLabel7);
        jLabel7.setBounds(110, 40, 130, 15);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Basınç");
        panel_Veri_ekle.add(jLabel8);
        jLabel8.setBounds(110, 60, 130, 15);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Rakım Metre");
        panel_Veri_ekle.add(jLabel9);
        jLabel9.setBounds(110, 80, 130, 15);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Rakım Feet");
        panel_Veri_ekle.add(jLabel10);
        jLabel10.setBounds(110, 100, 130, 15);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Nem");
        panel_Veri_ekle.add(jLabel11);
        jLabel11.setBounds(110, 120, 130, 15);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Hava Kalitesi");
        panel_Veri_ekle.add(jLabel12);
        jLabel12.setBounds(110, 140, 130, 15);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Yangın Durumu");
        panel_Veri_ekle.add(jLabel13);
        jLabel13.setBounds(110, 160, 130, 15);

        dereceC_S.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minus Green Button.png"))); // NOI18N
        panel_Veri_ekle.add(dereceC_S);
        dereceC_S.setBounds(400, 20, 70, 15);

        dereceF_S.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minus Green Button.png"))); // NOI18N
        panel_Veri_ekle.add(dereceF_S);
        dereceF_S.setBounds(400, 40, 70, 15);

        basinc_S.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minus Green Button.png"))); // NOI18N
        panel_Veri_ekle.add(basinc_S);
        basinc_S.setBounds(400, 60, 70, 15);

        rakimM_S.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minus Green Button.png"))); // NOI18N
        panel_Veri_ekle.add(rakimM_S);
        rakimM_S.setBounds(400, 80, 70, 15);

        rakimF_S.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minus Green Button.png"))); // NOI18N
        panel_Veri_ekle.add(rakimF_S);
        rakimF_S.setBounds(400, 100, 70, 15);

        nem_S.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minus Green Button.png"))); // NOI18N
        panel_Veri_ekle.add(nem_S);
        nem_S.setBounds(400, 120, 70, 15);

        gaz_S.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minus Green Button.png"))); // NOI18N
        panel_Veri_ekle.add(gaz_S);
        gaz_S.setBounds(400, 140, 70, 15);

        yangin_S.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minus Green Button.png"))); // NOI18N
        panel_Veri_ekle.add(yangin_S);
        yangin_S.setBounds(400, 160, 70, 15);

        AnaPanel.add(panel_Veri_ekle);
        panel_Veri_ekle.setBounds(48, 100, 590, 200);

        panel_Veri_ekle1.setBackground(new java.awt.Color(255, 255, 255));
        panel_Veri_ekle1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 10, true));
        panel_Veri_ekle1.setLayout(null);

        jProgressBar_dereceC1.setMinimum(-40);
        jProgressBar_dereceC1.setDoubleBuffered(true);
        jProgressBar_dereceC1.setStringPainted(true);
        panel_Veri_ekle1.add(jProgressBar_dereceC1);
        jProgressBar_dereceC1.setBounds(160, 20, 120, 17);

        jProgressBar_dereceF1.setMaximum(300);
        jProgressBar_dereceF1.setStringPainted(true);
        panel_Veri_ekle1.add(jProgressBar_dereceF1);
        jProgressBar_dereceF1.setBounds(160, 40, 120, 17);

        jProgressBar_basinc1.setMaximum(1000000);
        jProgressBar_basinc1.setStringPainted(true);
        panel_Veri_ekle1.add(jProgressBar_basinc1);
        jProgressBar_basinc1.setBounds(160, 60, 120, 17);

        jProgressBar_rakimM1.setMaximum(100000);
        jProgressBar_rakimM1.setStringPainted(true);
        panel_Veri_ekle1.add(jProgressBar_rakimM1);
        jProgressBar_rakimM1.setBounds(160, 80, 120, 17);

        jProgressBar_rakimF1.setMaximum(300000);
        jProgressBar_rakimF1.setStringPainted(true);
        panel_Veri_ekle1.add(jProgressBar_rakimF1);
        jProgressBar_rakimF1.setBounds(160, 100, 120, 17);

        jProgressBar_nem1.setStringPainted(true);
        panel_Veri_ekle1.add(jProgressBar_nem1);
        jProgressBar_nem1.setBounds(160, 120, 120, 17);

        jProgressBar_gaz1.setMaximum(1000);
        jProgressBar_gaz1.setStringPainted(true);
        panel_Veri_ekle1.add(jProgressBar_gaz1);
        jProgressBar_gaz1.setBounds(160, 140, 120, 17);

        jProgressBar_yangin1.setMaximum(1);
        jProgressBar_yangin1.setStringPainted(true);
        panel_Veri_ekle1.add(jProgressBar_yangin1);
        jProgressBar_yangin1.setBounds(160, 160, 120, 17);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Sıcaklık Derece");
        panel_Veri_ekle1.add(jLabel22);
        jLabel22.setBounds(20, 20, 130, 15);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Sıcaklık Fahrenheit");
        panel_Veri_ekle1.add(jLabel23);
        jLabel23.setBounds(20, 40, 130, 15);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Basınç");
        panel_Veri_ekle1.add(jLabel24);
        jLabel24.setBounds(20, 60, 130, 15);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Rakım Metre");
        panel_Veri_ekle1.add(jLabel25);
        jLabel25.setBounds(20, 80, 130, 15);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Rakım Feet");
        panel_Veri_ekle1.add(jLabel26);
        jLabel26.setBounds(20, 100, 130, 15);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Nem");
        panel_Veri_ekle1.add(jLabel27);
        jLabel27.setBounds(20, 120, 130, 15);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Hava Kalitesi");
        panel_Veri_ekle1.add(jLabel28);
        jLabel28.setBounds(20, 140, 130, 15);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Yangın Durumu");
        panel_Veri_ekle1.add(jLabel29);
        jLabel29.setBounds(20, 160, 130, 15);

        label_minimum.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_minimum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Spotlight Blue Button.png"))); // NOI18N
        label_minimum.setText("Minimum Search");
        label_minimum.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_minimum.setDoubleBuffered(true);
        label_minimum.setName(""); // NOI18N
        label_minimum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_minimumclick(evt);
            }
        });
        panel_Veri_ekle1.add(label_minimum);
        label_minimum.setBounds(350, 50, 160, 40);

        label_ortalama.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_ortalama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Spotlight Blue Button.png"))); // NOI18N
        label_ortalama.setText("Avg Search");
        label_ortalama.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_ortalama.setDoubleBuffered(true);
        label_ortalama.setName(""); // NOI18N
        label_ortalama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_ortalamaclick(evt);
            }
        });
        panel_Veri_ekle1.add(label_ortalama);
        label_ortalama.setBounds(350, 150, 160, 40);
        label_ortalama.getAccessibleContext().setAccessibleName("");

        label_maximum.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_maximum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Spotlight Blue Button.png"))); // NOI18N
        label_maximum.setText("Maximum Search");
        label_maximum.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_maximum.setDoubleBuffered(true);
        label_maximum.setName(""); // NOI18N
        label_maximum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_maximumclick(evt);
            }
        });
        panel_Veri_ekle1.add(label_maximum);
        label_maximum.setBounds(350, 100, 160, 40);

        jCmb_Tarih.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCmb_Tarih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCmb_TarihMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jCmb_TarihMouseEntered(evt);
            }
        });
        jCmb_Tarih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmb_TarihActionPerformed(evt);
            }
        });
        panel_Veri_ekle1.add(jCmb_Tarih);
        jCmb_Tarih.setBounds(350, 20, 190, 20);

        AnaPanel.add(panel_Veri_ekle1);
        panel_Veri_ekle1.setBounds(48, 100, 590, 200);

        panel_Bilesen_Guncelle.setBackground(new java.awt.Color(255, 255, 255));
        panel_Bilesen_Guncelle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 10, true));
        panel_Bilesen_Guncelle.setLayout(null);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Sıcaklık Derece");
        panel_Bilesen_Guncelle.add(jLabel14);
        jLabel14.setBounds(110, 20, 130, 15);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Sıcaklık Fahrenheit");
        panel_Bilesen_Guncelle.add(jLabel15);
        jLabel15.setBounds(110, 40, 130, 15);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Basınç");
        panel_Bilesen_Guncelle.add(jLabel16);
        jLabel16.setBounds(110, 60, 130, 15);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Rakım Metre");
        panel_Bilesen_Guncelle.add(jLabel17);
        jLabel17.setBounds(110, 80, 130, 15);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Rakım Feet");
        panel_Bilesen_Guncelle.add(jLabel18);
        jLabel18.setBounds(110, 100, 130, 15);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Nem");
        panel_Bilesen_Guncelle.add(jLabel19);
        jLabel19.setBounds(110, 120, 130, 15);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Hava Kalitesi");
        panel_Bilesen_Guncelle.add(jLabel20);
        jLabel20.setBounds(110, 140, 130, 15);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Yangın Durumu");
        panel_Bilesen_Guncelle.add(jLabel21);
        jLabel21.setBounds(110, 160, 130, 15);

        jTextField_sicaklikC.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField_sicaklikC.setText("25");
        jTextField_sicaklikC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_sicaklikCFocusLost(evt);
            }
        });
        jTextField_sicaklikC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_sicaklikCKeyTyped(evt);
            }
        });
        panel_Bilesen_Guncelle.add(jTextField_sicaklikC);
        jTextField_sicaklikC.setBounds(260, 20, 150, 19);

        jTextField_sicaklikF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField_sicaklikF.setText("77");
        jTextField_sicaklikF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_sicaklikFFocusLost(evt);
            }
        });
        jTextField_sicaklikF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_sicaklikFKeyTyped(evt);
            }
        });
        panel_Bilesen_Guncelle.add(jTextField_sicaklikF);
        jTextField_sicaklikF.setBounds(260, 40, 150, 19);

        jTextField_basinc.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField_basinc.setText("90000");
        jTextField_basinc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_basincKeyTyped(evt);
            }
        });
        panel_Bilesen_Guncelle.add(jTextField_basinc);
        jTextField_basinc.setBounds(260, 60, 150, 19);

        jTextField_rakimM.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField_rakimM.setText("500");
        jTextField_rakimM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_rakimMFocusLost(evt);
            }
        });
        jTextField_rakimM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_rakimMKeyTyped(evt);
            }
        });
        panel_Bilesen_Guncelle.add(jTextField_rakimM);
        jTextField_rakimM.setBounds(260, 80, 150, 19);

        jTextField_rakimF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField_rakimF.setText("1640");
        jTextField_rakimF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_rakimFFocusLost(evt);
            }
        });
        jTextField_rakimF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_rakimFKeyTyped(evt);
            }
        });
        panel_Bilesen_Guncelle.add(jTextField_rakimF);
        jTextField_rakimF.setBounds(260, 100, 150, 19);

        jTextField_nem.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField_nem.setText("30");
        jTextField_nem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_nemKeyTyped(evt);
            }
        });
        panel_Bilesen_Guncelle.add(jTextField_nem);
        jTextField_nem.setBounds(260, 120, 150, 19);

        jTextField_gaz.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField_gaz.setText("50");
        jTextField_gaz.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_gazKeyTyped(evt);
            }
        });
        panel_Bilesen_Guncelle.add(jTextField_gaz);
        jTextField_gaz.setBounds(260, 140, 150, 19);

        jTextField_yangin.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField_yangin.setText("0");
        jTextField_yangin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_yanginKeyTyped(evt);
            }
        });
        panel_Bilesen_Guncelle.add(jTextField_yangin);
        jTextField_yangin.setBounds(260, 160, 150, 19);

        label_degiskenKaydet.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_degiskenKaydet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save.png"))); // NOI18N
        label_degiskenKaydet.setText("Save Variables");
        label_degiskenKaydet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_degiskenKaydet.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        label_degiskenKaydet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_degiskenKaydetMouseClicked(evt);
            }
        });
        panel_Bilesen_Guncelle.add(label_degiskenKaydet);
        label_degiskenKaydet.setBounds(430, 130, 140, 50);

        AnaPanel.add(panel_Bilesen_Guncelle);
        panel_Bilesen_Guncelle.setBounds(48, 100, 590, 200);

        label_baglan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_baglan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Play.png"))); // NOI18N
        label_baglan.setText("Başlat");
        label_baglan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_baglan.setDoubleBuffered(true);
        label_baglan.setName(""); // NOI18N
        label_baglan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelAlgoritmaBilesenleriClick(evt);
            }
        });
        AnaPanel.add(label_baglan);
        label_baglan.setBounds(50, 40, 80, 50);

        label_degiskenBelirle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_degiskenBelirle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save.png"))); // NOI18N
        label_degiskenBelirle.setText("İklimlendirme Ayarları");
        label_degiskenBelirle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_degiskenBelirle.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        label_degiskenBelirle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_degiskenBelirleMouseClicked(evt);
            }
        });
        AnaPanel.add(label_degiskenBelirle);
        label_degiskenBelirle.setBounds(490, 40, 190, 50);

        jLabel_kullanici_adi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_kullanici_adi.setText("Giriş Yapılmadı");
        AnaPanel.add(jLabel_kullanici_adi);
        jLabel_kullanici_adi.setBounds(10, 330, 180, 15);

        jLabel_sifre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_sifre.setText("Lütfen Giriş Yapnız");
        AnaPanel.add(jLabel_sifre);
        jLabel_sifre.setBounds(10, 350, 190, 15);

        label_arama.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_arama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Spotlight Blue Button.png"))); // NOI18N
        label_arama.setText("Ara ve İncele");
        label_arama.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_arama.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        label_arama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_aramaMouseClicked(evt);
            }
        });
        AnaPanel.add(label_arama);
        label_arama.setBounds(260, 40, 130, 50);

        label_kullaniciEkle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_kullaniciEkle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add Green Button.png"))); // NOI18N
        label_kullaniciEkle.setText("Kullanıcı Ekle");
        label_kullaniciEkle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_kullaniciEkle.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        label_kullaniciEkle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_kullaniciEkleMouseClicked(evt);
            }
        });
        AnaPanel.add(label_kullaniciEkle);
        label_kullaniciEkle.setBounds(490, 340, 130, 30);

        label_cikisYap.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label_cikisYap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Stop Red Button.png"))); // NOI18N
        label_cikisYap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_cikisYap.setDoubleBuffered(true);
        label_cikisYap.setName(""); // NOI18N
        label_cikisYap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_cikisYapclick(evt);
            }
        });
        AnaPanel.add(label_cikisYap);
        label_cikisYap.setBounds(650, 340, 30, 32);

        labelBackgraund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/arkaplan.png"))); // NOI18N
        labelBackgraund.setPreferredSize(getMaximumSize());
        labelBackgraund.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        AnaPanel.add(labelBackgraund);
        labelBackgraund.setBounds(0, 0, 690, 380);

        getContentPane().add(AnaPanel);
        AnaPanel.setBounds(0, 0, 690, 380);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    
    private void labelAlgoritmaBilesenleriClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelAlgoritmaBilesenleriClick
        if(giris == true){
            panel_Bilesen_Guncelle.setVisible(false);
            panel_Veri_ekle.setVisible(true);
            panel_Veri_ekle1.setVisible(false);
            setComPortName("COM6");
            DATA_RATE = 57600;
            TIME_OUT = 1000;
            initialize();
            model.clear();
        }
        else{
            panel_Giris_Yap.setVisible(true);
            JOptionPane.showMessageDialog(null, "Giriş Yapınız.");
        }
        
        
    }//GEN-LAST:event_labelAlgoritmaBilesenleriClick
    private void label_minimumclick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_minimumclick
        ArrayList<veriTabani> liste = new ArrayList<veriTabani>();
        try {
            liste = vt.vericek(String.valueOf(jCmb_Tarih.getSelectedItem()));
            jProgressBar_dereceC1.setValue((int)  Double.parseDouble(liste.get(0).getSicaklik_c_min()));
            jProgressBar_dereceC1.setString(liste.get(0).getSicaklik_c_min());
            
            jProgressBar_dereceF1.setValue((int)  Double.parseDouble(liste.get(0).getSicaklik_f_min()));
            jProgressBar_dereceF1.setString(liste.get(0).getSicaklik_f_min());
            
            jProgressBar_basinc1.setValue((int)  Double.parseDouble(liste.get(0).getBasinc_min()));
            jProgressBar_basinc1.setString(liste.get(0).getBasinc_min());
            
            jProgressBar_rakimM1.setValue((int)  Double.parseDouble(liste.get(0).getRakim_mt_min()));
            jProgressBar_rakimM1.setString(liste.get(0).getRakim_mt_min());
            
            jProgressBar_rakimF1.setValue((int)  Double.parseDouble(liste.get(0).getRakim_ft_min()));
            jProgressBar_rakimF1.setString(liste.get(0).getRakim_ft_min());
            
            jProgressBar_nem1.setValue((int)  Double.parseDouble(liste.get(0).getNem_min()));
            jProgressBar_nem1.setString(liste.get(0).getNem_min());
            
            jProgressBar_gaz1.setValue((int)  Double.parseDouble(liste.get(0).getHava_k_min()));
            jProgressBar_gaz1.setString(liste.get(0).getHava_k_min());
            
            jProgressBar_yangin1.setValue((int)  Double.parseDouble(liste.get(0).getHava_k_min()));

                        
                        if(liste.get(0).getYangin_min().equals("0")){
                            jProgressBar_yangin1.setString("Yangin Yok");
                        }
                        else
                            jProgressBar_yangin1.setString("Yangin Var");
                        
        } catch (SQLException ex) {
            Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_label_minimumclick

    private void label_degiskenBelirleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_degiskenBelirleMouseClicked
        if(giris==true){
            if(yetki.equals("yonetici")){
                panel_Bilesen_Guncelle.setVisible(true);
                panel_Veri_ekle.setVisible(false);
                panel_Veri_ekle1.setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(null, "Yetkiniz verileri değiştirmeye yeterli değil.");
            }
            
        }
        else{
            panel_Giris_Yap.setVisible(true);
            JOptionPane.showMessageDialog(null, "Giriş Yapınız.");
        }
        
    }//GEN-LAST:event_label_degiskenBelirleMouseClicked

    private void label_degiskenKaydetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_degiskenKaydetMouseClicked
        
        //jTextField_sicaklikF.setText(String.valueOf(df.format((Integer.parseInt(jTextField_sicaklikC.getText())*1.8)+32)));
        //jTextField_sicaklikC.setText(String.valueOf(df.format((Double.parseDouble(jTextField_sicaklikF.getText())-32)/1.8)));
        //jTextField_rakimF.setText(String.valueOf(df.format(Double.parseDouble(jTextField_rakimM.getText())*3.28)));
        //jTextField_rakimM.setText(String.valueOf(df.format(Double.parseDouble(jTextField_rakimF.getText())/3.28)));
        
        if(jTextField_sicaklikC.getText().equals("") || jTextField_sicaklikF.getText().equals("")
                || jTextField_basinc.getText().equals("")|| jTextField_rakimM.getText().equals("")
                || jTextField_rakimF.getText().equals("")|| jTextField_nem.getText().equals("")
                || jTextField_gaz.getText().equals("")|| jTextField_yangin.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bileşenler Boş Bırakılamaz!");
        }
       
        else{
         sicaklikC = Double.parseDouble(jTextField_sicaklikC.getText());
         sicaklikF = Double.parseDouble(jTextField_sicaklikF.getText());
         basinc = Double.parseDouble(jTextField_basinc.getText());
         rakimM = Double.parseDouble(jTextField_rakimM.getText());
         rakimF = Double.parseDouble(jTextField_rakimF.getText());
         nem = Double.parseDouble(jTextField_nem.getText());
         gaz = Double.parseDouble(jTextField_gaz.getText());
         yangin = Double.parseDouble(jTextField_yangin.getText());
        }



        
    }//GEN-LAST:event_label_degiskenKaydetMouseClicked

    private void jTextField_sicaklikCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_sicaklikCKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_jTextField_sicaklikCKeyTyped

    private void jTextField_sicaklikFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_sicaklikFKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_jTextField_sicaklikFKeyTyped

    private void jTextField_basincKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_basincKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_jTextField_basincKeyTyped

    private void jTextField_rakimMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_rakimMKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_jTextField_rakimMKeyTyped

    private void jTextField_rakimFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_rakimFKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_jTextField_rakimFKeyTyped

    private void jTextField_nemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nemKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_jTextField_nemKeyTyped

    private void jTextField_gazKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_gazKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_jTextField_gazKeyTyped

    private void jTextField_yanginKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_yanginKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_jTextField_yanginKeyTyped

    private void label_ortalamaclick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ortalamaclick
        ArrayList<veriTabani> liste = new ArrayList<veriTabani>();
        try {
            liste = vt.vericek(String.valueOf(jCmb_Tarih.getSelectedItem()));
            jProgressBar_dereceC1.setValue((int)  Double.parseDouble(liste.get(0).getSicaklik_c_ort()));
            jProgressBar_dereceC1.setString(liste.get(0).getSicaklik_c_ort());
            
            jProgressBar_dereceF1.setValue((int)  Double.parseDouble(liste.get(0).getSicaklik_f_ort()));
            jProgressBar_dereceF1.setString(liste.get(0).getSicaklik_f_ort());
            
            jProgressBar_basinc1.setValue((int)  Double.parseDouble(liste.get(0).getBasinc_ort()));
            jProgressBar_basinc1.setString(liste.get(0).getBasinc_ort());
            
            jProgressBar_rakimM1.setValue((int)  Double.parseDouble(liste.get(0).getRakim_mt_ort()));
            jProgressBar_rakimM1.setString(liste.get(0).getRakim_mt_ort());
            
            jProgressBar_rakimF1.setValue((int)  Double.parseDouble(liste.get(0).getRakim_ft_ort()));
            jProgressBar_rakimF1.setString(liste.get(0).getRakim_ft_ort());
            
            jProgressBar_nem1.setValue((int)  Double.parseDouble(liste.get(0).getNem_ort()));
            jProgressBar_nem1.setString(liste.get(0).getNem_ort());
            
            jProgressBar_gaz1.setValue((int)  Double.parseDouble(liste.get(0).getHava_k_ort()));
            jProgressBar_gaz1.setString(liste.get(0).getHava_k_ort());
            
            jProgressBar_yangin1.setValue((int)  Double.parseDouble(liste.get(0).getHava_k_ort()));

                        
                        if(liste.get(0).getYangin_ort().equals("0")){
                            jProgressBar_yangin1.setString("Yangin Yok");
                        }
                        else
                            jProgressBar_yangin1.setString("Yangin Var");
                        
        } catch (SQLException ex) {
            Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }//GEN-LAST:event_label_ortalamaclick

    private void label_maximumclick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_maximumclick
        ArrayList<veriTabani> liste = new ArrayList<veriTabani>();
        try {
            liste = vt.vericek(String.valueOf(jCmb_Tarih.getSelectedItem()));
            jProgressBar_dereceC1.setValue((int)  Double.parseDouble(liste.get(0).getSicaklik_c_max()));
            jProgressBar_dereceC1.setString(liste.get(0).getSicaklik_c_max());
            
            jProgressBar_dereceF1.setValue((int)  Double.parseDouble(liste.get(0).getSicaklik_f_max()));
            jProgressBar_dereceF1.setString(liste.get(0).getSicaklik_f_max());
            
            jProgressBar_basinc1.setValue((int)  Double.parseDouble(liste.get(0).getBasinc_max()));
            jProgressBar_basinc1.setString(liste.get(0).getBasinc_max());
            
            jProgressBar_rakimM1.setValue((int)  Double.parseDouble(liste.get(0).getRakim_mt_max()));
            jProgressBar_rakimM1.setString(liste.get(0).getRakim_mt_max());
            
            jProgressBar_rakimF1.setValue((int)  Double.parseDouble(liste.get(0).getRakim_ft_max()));
            jProgressBar_rakimF1.setString(liste.get(0).getRakim_ft_max());
            
            jProgressBar_nem1.setValue((int)  Double.parseDouble(liste.get(0).getNem_max()));
            jProgressBar_nem1.setString(liste.get(0).getNem_max());
            
            jProgressBar_gaz1.setValue((int)  Double.parseDouble(liste.get(0).getHava_k_max()));
            jProgressBar_gaz1.setString(liste.get(0).getHava_k_max());
            
            jProgressBar_yangin1.setValue((int)  Double.parseDouble(liste.get(0).getHava_k_max()));

                        
                        if(liste.get(0).getYangin_max().equals("0")){
                            jProgressBar_yangin1.setString("Yangin Yok");
                        }
                        else
                            jProgressBar_yangin1.setString("Yangin Var");
                        
        } catch (SQLException ex) {
            Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_label_maximumclick

    private void label_aramaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_aramaMouseClicked
        if(giris==true){
            panel_Bilesen_Guncelle.setVisible(false);
            panel_Veri_ekle.setVisible(false);
            panel_Veri_ekle1.setVisible(true);

            ArrayList<veriTabani> tliste = new ArrayList<veriTabani>();
            try {
                tliste = vt.tarih_cek();

            } catch (SQLException ex) {
                Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
            }
            jCmb_Tarih.removeAllItems();
            for (int j = 0; j < tliste.size(); j++) {
                jCmb_Tarih.addItem(tliste.get(j).getSicaklik_c_min());
            }
        }
        else{
            panel_Giris_Yap.setVisible(true);
            JOptionPane.showMessageDialog(null, "Giriş Yapınız.");
        }
        
        
        
        
    }//GEN-LAST:event_label_aramaMouseClicked

    private void jCmb_TarihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmb_TarihActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmb_TarihActionPerformed

    private void jCmb_TarihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmb_TarihMouseClicked
        // TODO add your handling code here:
        //System.out.println("test-001");
    }//GEN-LAST:event_jCmb_TarihMouseClicked

    private void jCmb_TarihMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmb_TarihMouseEntered
        // TODO add your handling code here:
        //System.out.println("test-001");
    }//GEN-LAST:event_jCmb_TarihMouseEntered

    private void jTextField_sicaklikCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_sicaklikCFocusLost
        // TODO add your handling code here:
        System.out.println("celcius-focus-lost");
        if(jTextField_sicaklikC.getText().equals(""))
            jTextField_sicaklikC.setText("25");
        jTextField_sicaklikF.setText(String.valueOf(df.format((Integer.parseInt(jTextField_sicaklikC.getText())*1.8)+32)));
    }//GEN-LAST:event_jTextField_sicaklikCFocusLost

    private void jTextField_sicaklikFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_sicaklikFFocusLost
        // TODO add your handling code here:
        System.out.println("fahrenheit-focus-lost");
        if(jTextField_sicaklikF.getText().equals(""))
            jTextField_sicaklikF.setText("77");
        jTextField_sicaklikC.setText(String.valueOf(df.format((Double.parseDouble(jTextField_sicaklikF.getText())-32)/1.8)));
    }//GEN-LAST:event_jTextField_sicaklikFFocusLost

    private void jTextField_rakimMFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_rakimMFocusLost
        // TODO add your handling code here:
        System.out.println("rakim-mt-focus-lost");
        if(jTextField_rakimM.getText().equals(""))
            jTextField_rakimM.setText("500");
        jTextField_rakimF.setText(String.valueOf(df.format(Double.parseDouble(jTextField_rakimM.getText())*3.28)));
    }//GEN-LAST:event_jTextField_rakimMFocusLost

    private void jTextField_rakimFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_rakimFFocusLost
        // TODO add your handling code here:
        System.out.println("rakim-ft-focus-lost");
        if(jTextField_rakimF.getText().equals(""))
            jTextField_rakimF.setText("1640");
        jTextField_rakimM.setText(String.valueOf(df.format(Double.parseDouble(jTextField_rakimF.getText())/3.28)));
    }//GEN-LAST:event_jTextField_rakimFFocusLost

    private void label_temizleclick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_temizleclick
        jTextField_kullaniciAdi.setText("");
        jTextField_sifre.setText("");
    }//GEN-LAST:event_label_temizleclick

    private void label_girisYapclick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_girisYapclick
        
        ArrayList<veriTabani> tliste = new ArrayList<veriTabani>();
        if(jTextField_kullaniciAdi.equals("")&&jTextField_sifre.equals("")){
            JOptionPane.showMessageDialog(null, "Kullanıcı adı ve şifre boş geçilemez.");
        }
        else{
            try {
            tliste = vt.giris_yap(jTextField_kullaniciAdi.getText(),jTextField_sifre.getText());
         } catch (SQLException ex) {
            Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(tliste.size()==0){
            JOptionPane.showMessageDialog(null, "Hatalı Giriş Yaptınız.");
        }
        else{
            JOptionPane.showMessageDialog(null, "Giriş Başarılı.");
            kullanici_adi = tliste.get(0).getKullanici_adi();
            yetki = tliste.get(0).getYetki();
            jLabel_kullanici_adi.setText("Hoşgeldin "+kullanici_adi);
            jLabel_sifre.setText("Yetkiniz "+yetki);
            giris=true;
            if(yetki.equals("Yonetici")){
                label_kullaniciEkle.setVisible(true);
            }
            jTextField_kullaniciAdi.setText("");
            jTextField_sifre.setText("");
            panel_Giris_Yap.setVisible(false);
        }
        
        }
    }//GEN-LAST:event_label_girisYapclick

    private void label_temizle1click(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_temizle1click
        jTextField_kullaniciAdi1.setText("");
        jTextField_sifre1.setText("");
    }//GEN-LAST:event_label_temizle1click

    private void label_kullanici_ekleclick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_kullanici_ekleclick
        if(jTextField_kullaniciAdi1.equals("") && jTextField_sifre1.equals("")){
            JOptionPane.showMessageDialog(null, "Kullanıcı adı ve şifre boş bırakılamaz.");
        }
        else {
        String[] kullaniciDizi = new String[3];
        kullaniciDizi[0]=jTextField_kullaniciAdi1.getText();
        kullaniciDizi[1]=jTextField_sifre1.getText();
        kullaniciDizi[2]=String.valueOf(jComboBox_yetki.getSelectedItem());
        try {
            vt.kullaniciEkle(kullaniciDizi);
        } catch (SQLException ex) {
            Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField_kullaniciAdi1.setText("");
        jTextField_sifre1.setText("");
        }
    }//GEN-LAST:event_label_kullanici_ekleclick

    private void label_kullaniciEkleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_kullaniciEkleMouseClicked
        panel_Kullanici_ekle.setVisible(true);
        panel_Bilesen_Guncelle.setVisible(false);
        panel_Veri_ekle.setVisible(false);
        panel_Veri_ekle1.setVisible(false);
        panel_Giris_Yap.setVisible(false);
    }//GEN-LAST:event_label_kullaniciEkleMouseClicked

    private void label_cikisYapclick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_cikisYapclick
        giris=false;
        panel_Kullanici_ekle.setVisible(false);
        panel_Bilesen_Guncelle.setVisible(false);
        panel_Veri_ekle.setVisible(false);
        panel_Veri_ekle1.setVisible(false);
        panel_Giris_Yap.setVisible(true);
        label_kullaniciEkle.setVisible(false);
        jLabel_kullanici_adi.setText("Girış Yapılmadı.");
        jLabel_sifre.setText("");
    }//GEN-LAST:event_label_cikisYapclick

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(anasayfa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(anasayfa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(anasayfa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(anasayfa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new anasayfa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AnaPanel;
    private javax.swing.JLabel basinc_D;
    private javax.swing.JLabel basinc_S;
    private javax.swing.JLabel basinc_Y;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel dereceC_D;
    private javax.swing.JLabel dereceC_S;
    private javax.swing.JLabel dereceC_Y;
    private javax.swing.JLabel dereceF_D;
    private javax.swing.JLabel dereceF_S;
    private javax.swing.JLabel dereceF_Y;
    private javax.swing.JLabel gaz_D;
    private javax.swing.JLabel gaz_S;
    private javax.swing.JLabel gaz_Y;
    private javax.swing.JComboBox<String> jCmb_Tarih;
    private javax.swing.JComboBox<String> jComboBox_yetki;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_kullanici_adi;
    private javax.swing.JLabel jLabel_sifre;
    private javax.swing.JProgressBar jProgressBar_basinc;
    private javax.swing.JProgressBar jProgressBar_basinc1;
    private javax.swing.JProgressBar jProgressBar_dereceC;
    private javax.swing.JProgressBar jProgressBar_dereceC1;
    private javax.swing.JProgressBar jProgressBar_dereceF;
    private javax.swing.JProgressBar jProgressBar_dereceF1;
    private javax.swing.JProgressBar jProgressBar_gaz;
    private javax.swing.JProgressBar jProgressBar_gaz1;
    private javax.swing.JProgressBar jProgressBar_nem;
    private javax.swing.JProgressBar jProgressBar_nem1;
    private javax.swing.JProgressBar jProgressBar_rakimF;
    private javax.swing.JProgressBar jProgressBar_rakimF1;
    private javax.swing.JProgressBar jProgressBar_rakimM;
    private javax.swing.JProgressBar jProgressBar_rakimM1;
    private javax.swing.JProgressBar jProgressBar_yangin;
    private javax.swing.JProgressBar jProgressBar_yangin1;
    private javax.swing.JTextField jTextField_basinc;
    private javax.swing.JTextField jTextField_gaz;
    private javax.swing.JTextField jTextField_kullaniciAdi;
    private javax.swing.JTextField jTextField_kullaniciAdi1;
    private javax.swing.JTextField jTextField_nem;
    private javax.swing.JTextField jTextField_rakimF;
    private javax.swing.JTextField jTextField_rakimM;
    private javax.swing.JTextField jTextField_sicaklikC;
    private javax.swing.JTextField jTextField_sicaklikF;
    private javax.swing.JPasswordField jTextField_sifre;
    private javax.swing.JPasswordField jTextField_sifre1;
    private javax.swing.JTextField jTextField_yangin;
    private javax.swing.JLabel labelBackgraund;
    private javax.swing.JLabel label_arama;
    private javax.swing.JLabel label_baglan;
    private javax.swing.JLabel label_cikisYap;
    private javax.swing.JLabel label_degiskenBelirle;
    private javax.swing.JLabel label_degiskenKaydet;
    private javax.swing.JLabel label_girisYap;
    private javax.swing.JLabel label_kullaniciEkle;
    private javax.swing.JLabel label_kullanici_ekle;
    private javax.swing.JLabel label_maximum;
    private javax.swing.JLabel label_minimum;
    private javax.swing.JLabel label_ortalama;
    private javax.swing.JLabel label_temizle;
    private javax.swing.JLabel label_temizle1;
    private javax.swing.JLabel nem_D;
    private javax.swing.JLabel nem_S;
    private javax.swing.JLabel nem_Y;
    private javax.swing.JPanel panel_Bilesen_Guncelle;
    private javax.swing.JPanel panel_Giris_Yap;
    private javax.swing.JPanel panel_Kullanici_ekle;
    private javax.swing.JPanel panel_Veri_ekle;
    private javax.swing.JPanel panel_Veri_ekle1;
    private javax.swing.JLabel rakimF_D;
    private javax.swing.JLabel rakimF_S;
    private javax.swing.JLabel rakimF_Y;
    private javax.swing.JLabel rakimM_D;
    private javax.swing.JLabel rakimM_S;
    private javax.swing.JLabel rakimM_Y;
    private javax.swing.JLabel yangin_D;
    private javax.swing.JLabel yangin_S;
    private javax.swing.JLabel yangin_Y;
    // End of variables declaration//GEN-END:variables

    
}
