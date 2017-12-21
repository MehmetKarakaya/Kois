/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kois.pkg3;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ersn
 */
public class veriTabani {
    private String sicaklik_c_min;
    private String sicaklik_f_min;
    private String basinc_min;
    private String rakim_mt_min;
    private String rakim_ft_min;
    private String nem_min;
    private String hava_k_min;
    private String yangin_min;
    
    private String sicaklik_c_ort;
    private String sicaklik_f_ort;
    private String basinc_ort;
    private String rakim_mt_ort;
    private String rakim_ft_ort;
    private String nem_ort;
    private String hava_k_ort;
    private String yangin_ort;
    
    private String sicaklik_c_max;
    private String sicaklik_f_max;
    private String basinc_max;
    private String rakim_mt_max;
    private String rakim_ft_max;
    private String nem_max;
    private String hava_k_max;
    private String yangin_max;
    
    private String kullanici_adi;
    private String sifre;
    private String yetki;

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getYetki() {
        return yetki;
    }

    public void setYetki(String yetki) {
        this.yetki = yetki;
    }
    
    
    public String getSicaklik_c_min() {
        return sicaklik_c_min;
    }

    public void setSicaklik_c_min(String sicaklik_c_min) {
        this.sicaklik_c_min = sicaklik_c_min;
    }

    public String getSicaklik_f_min() {
        return sicaklik_f_min;
    }

    public void setSicaklik_f_min(String sicaklik_f_min) {
        this.sicaklik_f_min = sicaklik_f_min;
    }

    public String getBasinc_min() {
        return basinc_min;
    }

    public void setBasinc_min(String basinc_min) {
        this.basinc_min = basinc_min;
    }

    public String getRakim_mt_min() {
        return rakim_mt_min;
    }

    public void setRakim_mt_min(String rakim_mt_min) {
        this.rakim_mt_min = rakim_mt_min;
    }

    public String getRakim_ft_min() {
        return rakim_ft_min;
    }

    public void setRakim_ft_min(String rakim_ft_min) {
        this.rakim_ft_min = rakim_ft_min;
    }

    public String getNem_min() {
        return nem_min;
    }

    public void setNem_min(String nem_min) {
        this.nem_min = nem_min;
    }

    public String getHava_k_min() {
        return hava_k_min;
    }

    public void setHava_k_min(String hava_k_min) {
        this.hava_k_min = hava_k_min;
    }

    public String getYangin_min() {
        return yangin_min;
    }

    public void setYangin_min(String yangin_min) {
        this.yangin_min = yangin_min;
    }

    public String getSicaklik_c_ort() {
        return sicaklik_c_ort;
    }

    public void setSicaklik_c_ort(String sicaklik_c_ort) {
        this.sicaklik_c_ort = sicaklik_c_ort;
    }

    public String getSicaklik_f_ort() {
        return sicaklik_f_ort;
    }

    public void setSicaklik_f_ort(String sicaklik_f_ort) {
        this.sicaklik_f_ort = sicaklik_f_ort;
    }

    public String getBasinc_ort() {
        return basinc_ort;
    }

    public void setBasinc_ort(String basinc_ort) {
        this.basinc_ort = basinc_ort;
    }

    public String getRakim_mt_ort() {
        return rakim_mt_ort;
    }

    public void setRakim_mt_ort(String rakim_mt_ort) {
        this.rakim_mt_ort = rakim_mt_ort;
    }

    public String getRakim_ft_ort() {
        return rakim_ft_ort;
    }

    public void setRakim_ft_ort(String rakim_ft_ort) {
        this.rakim_ft_ort = rakim_ft_ort;
    }

    public String getNem_ort() {
        return nem_ort;
    }

    public void setNem_ort(String nem_ort) {
        this.nem_ort = nem_ort;
    }

    public String getHava_k_ort() {
        return hava_k_ort;
    }

    public void setHava_k_ort(String hava_k_ort) {
        this.hava_k_ort = hava_k_ort;
    }

    public String getYangin_ort() {
        return yangin_ort;
    }

    public void setYangin_ort(String yangin_ort) {
        this.yangin_ort = yangin_ort;
    }

    public String getSicaklik_c_max() {
        return sicaklik_c_max;
    }

    public void setSicaklik_c_max(String sicaklik_c_max) {
        this.sicaklik_c_max = sicaklik_c_max;
    }

    public String getSicaklik_f_max() {
        return sicaklik_f_max;
    }

    public void setSicaklik_f_max(String sicaklik_f_max) {
        this.sicaklik_f_max = sicaklik_f_max;
    }

    public String getBasinc_max() {
        return basinc_max;
    }

    public void setBasinc_max(String basinc_max) {
        this.basinc_max = basinc_max;
    }

    public String getRakim_mt_max() {
        return rakim_mt_max;
    }

    public void setRakim_mt_max(String rakim_mt_max) {
        this.rakim_mt_max = rakim_mt_max;
    }

    public String getRakim_ft_max() {
        return rakim_ft_max;
    }

    public void setRakim_ft_max(String rakim_ft_max) {
        this.rakim_ft_max = rakim_ft_max;
    }

    public String getNem_max() {
        return nem_max;
    }

    public void setNem_max(String nem_max) {
        this.nem_max = nem_max;
    }

    public String getHava_k_max() {
        return hava_k_max;
    }

    public void setHava_k_max(String hava_k_max) {
        this.hava_k_max = hava_k_max;
    }

    public String getYangin_max() {
        return yangin_max;
    }

    public void setYangin_max(String yangin_max) {
        this.yangin_max = yangin_max;
    }

     public void veriEkle(String dizi[])throws SQLException{
        try
        {
            //JOptionPane.showMessageDialog(null, dizi[0]);
            baglanti vb=new baglanti();
            vb.baglan();  
            //JOptionPane.showMessageDialog(null, "Burdasdın.");
            //ResultSet rs;  //butoon işlemini yaptı
            String sorgu="insert into veriler(sicaklik_c,sicaklik_f,basinc,rakim_mt,rakim_ft,nem,hava_k,yangin,kayit_tarihi) values (?,?,?,?,?,?,?,?,NOW());";
            
            PreparedStatement ps= vb.con.prepareStatement(sorgu);
            
            ps.setString(1, dizi[0]);
            ps.setString(2, dizi[1]);
            ps.setString(3, dizi[2]);
            ps.setString(4, dizi[3]);
            ps.setString(5, dizi[4]);
            ps.setString(6, dizi[5]);
            ps.setString(7, dizi[6]);
            ps.setString(8, dizi[7]);
            ps.execute();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);   
        }
    }
     public void kullaniciEkle(String dizi[])throws SQLException{
        try
        {
            baglanti vb=new baglanti();
            vb.baglan();  
            String sorgu="insert into giris(kullanici_adi,sifre,yetki) values (?,?,?);";
            PreparedStatement ps= vb.con.prepareStatement(sorgu);  
            ps.setString(1, dizi[0]);
            ps.setString(2, dizi[1]);
            ps.setString(3, dizi[2]);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Kullanıcı Başarıyla Eklendi.");
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);   
        }
    }
 public ArrayList<veriTabani> vericek(String tarih) throws SQLException
    {
        baglanti vb=new baglanti();
        vb.baglan();
        String sorgu="SELECT\n" +
"min(sicaklik_c) as sicaklik_c_min, min(sicaklik_f) as sicaklik_f_min, min(basinc) as basinc_min, min(rakim_mt) as rakim_mt_min,min(rakim_ft) as rakim_ft_min, min(nem) as nem_min, min(hava_k) as hava_k_min, min(yangin) as yangin_min,\n" +
"avg(sicaklik_c) as sicaklik_c_ort, avg(sicaklik_f) as sicaklik_f_ort, avg(basinc) as basinc_ort, avg(rakim_mt) as rakim_mt_ort,avg(rakim_ft) as rakim_ft_ort, avg(nem) as nem_ort, avg(hava_k) as hava_k_ort, avg(yangin) as yangin_ort,\n" +
"max(sicaklik_c) as sicaklik_c_max, max(sicaklik_f) as sicaklik_f_max, max(basinc) as basinc_max, max(rakim_mt) as rakim_mt_max,max(rakim_ft) as rakim_ft_max, max(nem) as nem_max, max(hava_k) as hava_k_max, max(yangin) as yangin_max\n" +
"FROM veriler v where kayit_tarihi like '"+tarih+"%';";
        ArrayList<veriTabani> liste = new ArrayList<veriTabani>();
        
        PreparedStatement ps=vb.con.prepareStatement(sorgu);
        //ps.setInt(1, a);
        ResultSet rs=ps.executeQuery();
        try 
        {        
            while (rs.next()) {
                    veriTabani vt=new veriTabani();
                    vt.setSicaklik_c_min(rs.getString("sicaklik_c_min"));
                    vt.setSicaklik_f_min(rs.getString("sicaklik_f_min"));
                    vt.setBasinc_min(rs.getString("basinc_min"));
                    vt.setRakim_mt_min(rs.getString("rakim_mt_min"));
                    vt.setRakim_ft_min(rs.getString("rakim_ft_min"));
                    vt.setNem_min(rs.getString("nem_min"));
                    vt.setHava_k_min(rs.getString("hava_k_min"));
                    vt.setYangin_min(rs.getString("yangin_min"));
                    
                    vt.setSicaklik_c_ort(rs.getString("sicaklik_c_ort"));
                    vt.setSicaklik_f_ort(rs.getString("sicaklik_f_ort"));
                    vt.setBasinc_ort(rs.getString("basinc_ort"));
                    vt.setRakim_mt_ort(rs.getString("rakim_mt_ort"));
                    vt.setRakim_ft_ort(rs.getString("rakim_ft_ort"));
                    vt.setNem_ort(rs.getString("nem_ort"));
                    vt.setHava_k_ort(rs.getString("hava_k_ort"));
                    vt.setYangin_ort(rs.getString("yangin_ort"));
                    
                    vt.setSicaklik_c_max(rs.getString("sicaklik_c_max"));
                    vt.setSicaklik_f_max(rs.getString("sicaklik_f_max"));
                    vt.setBasinc_max(rs.getString("basinc_max"));
                    vt.setRakim_mt_max(rs.getString("rakim_mt_max"));
                    vt.setRakim_ft_max(rs.getString("rakim_ft_max"));
                    vt.setNem_max(rs.getString("nem_max"));
                    vt.setHava_k_max(rs.getString("hava_k_max"));
                    vt.setYangin_max(rs.getString("yangin_max"));
                    
                    liste.add(vt);                    
                    }
        }
            catch(Exception ex)
            {
                    JOptionPane.showMessageDialog(null, ex);
                    
            }
         return liste;
    }

    public ArrayList<veriTabani> tarih_cek() throws SQLException
       {
           baglanti vb=new baglanti();
           vb.baglan();
           String sorgu="SELECT DISTINCT DATE_FORMAT(kayit_tarihi, '%Y-%m-%d') AS TARIH FROM veriler;";
           ArrayList<veriTabani> tliste = new ArrayList<veriTabani>();

           PreparedStatement ps=vb.con.prepareStatement(sorgu);
           //ps.setInt(1, a);
           ResultSet rs=ps.executeQuery();
           try 
           {        
                while (rs.next()) {
                    veriTabani vt=new veriTabani();
                    vt.setSicaklik_c_min(rs.getString("TARIH"));
                    tliste.add(vt);                    
                }
           }
               catch(Exception ex)
               {
                       JOptionPane.showMessageDialog(null, ex);

               }
            return tliste;
       }
    
    public ArrayList<veriTabani> giris_yap(String kullanici_adi, String sifre) throws SQLException
       {
           baglanti vb=new baglanti();
           vb.baglan();
           String sorgu="SELECT * FROM giris where kullanici_adi = ? and sifre = ?;";
           ArrayList<veriTabani> tliste = new ArrayList<veriTabani>();
           PreparedStatement ps=vb.con.prepareStatement(sorgu);
           ps.setString(1, kullanici_adi);
           ps.setString(2, sifre);
           ResultSet rs=ps.executeQuery();
           
           try 
           {        
                while (rs.next()) {
                    veriTabani vt=new veriTabani();
                    vt.setKullanici_adi(rs.getString("kullanici_adi"));
                    vt.setSifre(rs.getString("sifre"));
                    vt.setYetki(rs.getString("yetki"));
                    tliste.add(vt);                    
                }
           }
               catch(Exception ex)
               {
                       JOptionPane.showMessageDialog(null, ex);

               }
            return tliste;
       }
 }
    
