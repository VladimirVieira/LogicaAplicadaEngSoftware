package br.com.ufrn.pds1.projetopds1.verifyable.model;

import java.util.ArrayList;
import java.util.List;

public class DadosDiariosHistorico {

    /*@
      @ public invariant data == null || local != null;
      @
      @ public invariant tempMax == null || tempMax.size() > 0;
      @ public invariant tempMin == null || tempMin.size() > 0;
      @ public invariant tempMedia == null || tempMedia.size() > 0;
      @ public invariant velVento10m == null || velVento10m.size() > 0;
      @
      @ public invariant
      @   (tempMax == null || tempMin == null || tempMax.size() == tempMin.size());
      @
      @ public invariant
      @   (tempMax == null || tempMedia == null || tempMax.size() == tempMedia.size());
      @
      @ public invariant
      @   (tempMin == null || tempMedia == null || tempMin.size() == tempMedia.size());
      @*/

    //@ spec_public
    private List<String> data = new ArrayList<String>();

    //@ spec_public
    private String local = "";

    //@ spec_public
    private List<Double> tempMax= new ArrayList<Double>();

    //@ spec_public
    private List<Double> tempMin= new ArrayList<Double>();

    //@ spec_public
    private List<Double> tempMedia= new ArrayList<Double>();

    //@ spec_public
    private List<Double> velVento10m= new ArrayList<Double>();

    //@ spec_public
    private Double primeiroTrimestre = 0.0;

    //@ spec_public
    private Double segundoTrimestre = 0.0;

    //@ spec_public
    private Double terceiroTrimestre = 0.0;

    //@ spec_public
    private Double quartoSemestre = 0.0;


    /*@ ensures \result == primeiroTrimestre; @*/
    public Double getPrimeiroTrimestre() { return primeiroTrimestre; }

    /*@ requires primeiroTrimestre != null;
        ensures this.primeiroTrimestre == primeiroTrimestre; @*/
    public void setPrimeiroTrimestre(Double primeiroTrimestre) {
        this.primeiroTrimestre = primeiroTrimestre;
    }


    /*@ ensures \result == segundoTrimestre; @*/
    public Double getSegundoTrimestre() { return segundoTrimestre; }

    /*@ requires segundoTrimestre != null;
        ensures this.segundoTrimestre == segundoTrimestre; @*/
    public void setSegundoTrimestre(Double segundoTrimestre) {
        this.segundoTrimestre = segundoTrimestre;
    }


    /*@ ensures \result == terceiroTrimestre; @*/
    public Double getTerceiroTrimestre() { return terceiroTrimestre; }

    /*@ requires terceiroTrimestre != null;
        ensures this.terceiroTrimestre == terceiroTrimestre; @*/
    public void setTerceiroTrimestre(Double terceiroTrimestre) {
        this.terceiroTrimestre = terceiroTrimestre;
    }


    /*@ ensures \result == quartoSemestre; @*/
    public Double getQuartoSemestre() { return quartoSemestre; }

    /*@ requires quartoSemestre != null;
        ensures this.quartoSemestre == quartoSemestre; @*/
    public void setQuartoSemestre(Double quartoSemestre) {
        this.quartoSemestre = quartoSemestre;
    }


    /*@ ensures \result == data; @*/
    public List<String> getData() { return data; }

    /*@
      requires data != null;
      requires (\forall int i; 0 <= i && i < data.size(); data.get(i) != null);
      ensures this.data == data; @*/
    public void setData(List<String> data) { this.data = data; }


    /*@ ensures \result == local; @*/
    public String getLocal() { return local; }

    /*@ requires local != null;
        ensures this.local == local; @*/
    public void setLocal(String local) { this.local = local; }


    /*@ ensures \result == tempMax; @*/
    public List<Double> getTempMax() { return tempMax; }

    /*@
      requires tempMax != null && tempMax.size() > 0;
      requires (\forall int i; 0 <= i && i < tempMax.size(); tempMax.get(i) != null);
      // mantêm consistência com listas já existentes
      requires tempMin == null || tempMin.size() == tempMax.size();
      requires tempMedia == null || tempMedia.size() == tempMax.size();
      ensures this.tempMax == tempMax;
    @*/
    public void setTempMax(List<Double> tempMax) { this.tempMax = tempMax; }


    /*@ ensures \result == tempMin; @*/
    public List<Double> getTempMin() { return tempMin; }

    /*@
      requires tempMin != null && tempMin.size() > 0;
      requires (\forall int i; 0 <= i && i < tempMin.size(); tempMin.get(i) != null);
      requires tempMax == null || tempMax.size() == tempMin.size();
      requires tempMedia == null || tempMedia.size() == tempMin.size();
      ensures this.tempMin == tempMin;
    @*/
    public void setTempMin(List<Double> tempMin) { this.tempMin = tempMin; }


    /*@ ensures \result == tempMedia; @*/
    public List<Double> getTempMedia() { return tempMedia; }

    /*@
      requires tempMedia != null && tempMedia.size() > 0;
      requires (\forall int i; 0 <= i && i < tempMedia.size(); tempMedia.get(i) != null);
      requires tempMax == null || tempMax.size() == tempMedia.size();
      requires tempMin == null || tempMin.size() == tempMedia.size();
      ensures this.tempMedia == tempMedia;
    @*/
    public void setTempMedia(List<Double> tempMedia) { this.tempMedia = tempMedia; }


    /*@ ensures \result == velVento10m; @*/
    public List<Double> getVelVento10m() { return velVento10m; }

    /*@
      requires velVento10m != null && velVento10m.size() > 0;
      requires (\forall int i; 0 <= i && i < velVento10m.size(); velVento10m.get(i) != null);
      ensures this.velVento10m == velVento10m;
    @*/
    public void setVelVento10m(List<Double> velVento10m) { this.velVento10m = velVento10m; }
}
