package br.com.ufrn.pds1.projetopds1.verifyable.model;

public class DadosLocal {

    //@ public invariant latitude >= -90 && latitude <= 90;
    //@ public invariant longitude >= -180 && longitude <= 180;

    //@ spec_public
    private double temperatura;
    //@ spec_public
    private double velVento;
    //@ spec_public
    private double latitude;
    //@ spec_public
    private double longitude;
    //@ spec_public
    private String data = "";

    //@ ensures \result == temperatura;
    public double getTemperatura() { return temperatura; }

    //@ requires !Double.isNaN(temperatura);
    //@ ensures this.temperatura == temperatura;
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    //@ ensures \result == velVento;
    public double getVelVento() { return velVento; }

    //@ requires velVento >= 0;
    //@ ensures this.velVento == velVento;
    public void setVelVento(double velVento) {
        this.velVento = velVento;
    }

    //@ ensures \result == latitude;
    public double getLatitude() { return latitude; }

    //@ requires latitude >= -90 && latitude <= 90;
    //@ ensures this.latitude == latitude;
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //@ ensures \result == longitude;
    public double getLongitude() { return longitude; }

    //@ requires longitude >= -180 && longitude <= 180;
    //@ ensures this.longitude == longitude;
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //@ ensures \result == data;
    public String getData() { return data; }

    //@ requires data != null;
    //@ ensures this.data == data;
    public void setData(String data) {
        this.data = data;
    }
}
