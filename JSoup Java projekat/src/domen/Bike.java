/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

/**
 *
 * @author Dusan
 */
public class Bike {

    private String price;
    private String polovan;
    private String marka;
    private String tip;
    private String model;
    private String godiste;
    private String kubikaza;
    private String snaga;
    private String brojCilindara;
    private String menjac;
    private String boja;
    private String registrovanDo;
    private String poreklo;
    private String hladjenje;
    private String ostecenje;
    private String kilometraza;

    
    public Bike() {
        kubikaza = "nepoznato";
        snaga = "nepoznato";
        brojCilindara = "nepoznato";
        menjac = "nepoznato";
        boja = "nepoznato";
        registrovanDo = "nepoznato";
        poreklo = "nepoznato";
        hladjenje = "nepoznato";
        ostecenje = "nepoznato";
    }
    public String getKilometraza() {
        return kilometraza;
    }

    public void setKilometraza(String kilometraza) {
        this.kilometraza = kilometraza;
    }

    public String getKubikaza() {
        return kubikaza;
    }

    public void setKubikaza(String kubikaza) {
        this.kubikaza = kubikaza;
    }

    public String getSnaga() {
        return snaga;
    }

    public void setSnaga(String snaga) {
        this.snaga = snaga;
    }

    public String getBrojCilindara() {
        return brojCilindara;
    }

    public void setBrojCilindara(String brojCilindara) {
        this.brojCilindara = brojCilindara;
    }

    public String getMenjac() {
        return menjac;
    }

    public void setMenjac(String menjac) {
        this.menjac = menjac;
    }

    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }

    public String getRegistrovanDo() {
        return registrovanDo;
    }

    public void setRegistrovanDo(String registrovanDo) {
        this.registrovanDo = registrovanDo;
    }

    public String getPoreklo() {
        return poreklo;
    }

    public void setPoreklo(String poreklo) {
        this.poreklo = poreklo;
    }

    public String getHladjenje() {
        return hladjenje;
    }

    public void setHladjenje(String hladjenje) {
        this.hladjenje = hladjenje;
    }

    public String getOstecenje() {
        return ostecenje;
    }

    public void setOstecenje(String ostecenje) {
        this.ostecenje = ostecenje;
    }
    
    

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPolovan() {
        return polovan;
    }

    public void setPolovan(String polovan) {
        this.polovan = polovan;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGodiste() {
        return godiste;
    }

    public void setGodiste(String godiste) {
        this.godiste = godiste;
    }
    
    @Override
    public String toString() {
        return price+","+polovan+","+marka+","+tip+","+model+","+godiste+","+kilometraza+","+kubikaza+","+snaga+","+brojCilindara+","+menjac+","+boja+","+registrovanDo+","+poreklo+","+hladjenje+","+ostecenje;
    }

}
