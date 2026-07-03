package br.com.ufrn.pds1.projetopds1.verifyable.model;

import java.util.Date;
import java.util.List;

/*@ nullable_by_default */
public class PrevisaoTempo {

    //@ spec_public
    private Date data;
    //@ spec_public
    private String local;
    //@ spec_public
    private List<Double> previsaoTemperatura;
    //@ spec_public
    private List<Double> previsaoVento;
    //@ spec_public
    private List<Double> tempMax;
    //@ spec_public
    private List<Double> tempMaxPrevisao;
    //@ spec_public
    private List<Double> tempMedia;
    //@ spec_public
    private List<Double> tempMin;
    //@ spec_public
    private List<Double> tempMinPrevisao;
    //@ spec_public
    private List<Double> velVento10m;

    //@ spec_public
    private boolean initialized = false;

    /*@ public invariant initialized ==>
      @     data != null && local != null &&
      @     previsaoTemperatura != null && previsaoVento != null &&
      @     tempMax != null && tempMaxPrevisao != null &&
      @     tempMedia != null && tempMin != null &&
      @     tempMinPrevisao != null && velVento10m != null;
      @*/

    /*@ public normal_behavior
      @   ensures !initialized;
      @*/
    public PrevisaoTempo() {
    }

    /*@ public normal_behavior
      @   requires d != null && l != null &&
      @            tTemp != null && tVento != null &&
      @            tMax != null && tMaxPrev != null &&
      @            tMed != null && tMin != null &&
      @            tMinPrev != null && v10m != null;
      @   assignable \everything;
      @   ensures data == d && local == l &&
      @           previsaoTemperatura == tTemp && previsaoVento == tVento &&
      @           tempMax == tMax && tempMaxPrevisao == tMaxPrev &&
      @           tempMedia == tMed && tempMin == tMin &&
      @           tempMinPrevisao == tMinPrev && velVento10m == v10m;
      @   ensures initialized;
      @*/
    public PrevisaoTempo(Date d, String l,
                         List<Double> tTemp, List<Double> tVento,
                         List<Double> tMax, List<Double> tMaxPrev,
                         List<Double> tMed, List<Double> tMin,
                         List<Double> tMinPrev, List<Double> v10m) {
        this.data = d;
        this.local = l;
        this.previsaoTemperatura = tTemp;
        this.previsaoVento = tVento;
        this.tempMax = tMax;
        this.tempMaxPrevisao = tMaxPrev;
        this.tempMedia = tMed;
        this.tempMin = tMin;
        this.tempMinPrevisao = tMinPrev;
        this.velVento10m = v10m;
        this.initialized = true;
    }

    // Pure getters
    /*@ pure @*/ public Date                getData()                { return data; }
    /*@ pure @*/ public String               getLocal()               { return local; }
    /*@ pure @*/ public List<Double>         getPrevisaoTemperatura() { return previsaoTemperatura; }
    /*@ pure @*/ public List<Double>         getPrevisaoVento()       { return previsaoVento; }
    /*@ pure @*/ public List<Double>         getTempMax()             { return tempMax; }
    /*@ pure @*/ public List<Double>         getTempMaxPrevisao()     { return tempMaxPrevisao; }
    /*@ pure @*/ public List<Double>         getTempMedia()           { return tempMedia; }
    /*@ pure @*/ public List<Double>         getTempMin()             { return tempMin; }
    /*@ pure @*/ public List<Double>         getTempMinPrevisao()     { return tempMinPrevisao; }
    /*@ pure @*/ public List<Double>         getVelVento10m()         { return velVento10m; }

    /*@ normal_behavior
      @   requires d != null;
      @   assignable data;
      @   ensures data == d;
      @*/
    public void setData(Date d) { this.data = d; }

    /*@ normal_behavior
      @   requires l != null;
      @   assignable local;
      @   ensures local == l;
      @*/
    public void setLocal(String l) { this.local = l; }

    /*@ normal_behavior
      @   requires t != null;
      @   assignable previsaoTemperatura;
      @   ensures previsaoTemperatura == t;
      @*/
    public void setPrevisaoTemperatura(List<Double> t) { this.previsaoTemperatura = t; }

    /*@ normal_behavior
      @   requires v != null;
      @   assignable previsaoVento;
      @   ensures previsaoVento == v;
      @*/
    public void setPrevisaoVento(List<Double> v) { this.previsaoVento = v; }

    /*@ normal_behavior
      @   requires t != null;
      @   assignable tempMax;
      @   ensures tempMax == t;
      @*/
    public void setTempMax(List<Double> t) { this.tempMax = t; }

    /*@ normal_behavior
      @   requires t != null;
      @   assignable tempMaxPrevisao;
      @   ensures tempMaxPrevisao == t;
      @*/
    public void setTempMaxPrevisao(List<Double> t) { this.tempMaxPrevisao = t; }

    /*@ normal_behavior
      @   requires t != null;
      @   assignable tempMedia;
      @   ensures tempMedia == t;
      @*/
    public void setTempMedia(List<Double> t) { this.tempMedia = t; }

    /*@ normal_behavior
      @   requires t != null;
      @   assignable tempMin;
      @   ensures tempMin == t;
      @*/
    public void setTempMin(List<Double> t) { this.tempMin = t; }

    /*@ normal_behavior
      @   requires t != null;
      @   assignable tempMinPrevisao;
      @   ensures tempMinPrevisao == t;
      @*/
    public void setTempMinPrevisao(List<Double> t) { this.tempMinPrevisao = t; }

    /*@ normal_behavior
      @   requires v != null;
      @   assignable velVento10m;
      @   ensures velVento10m == v;
      @*/
    public void setVelVento10m(List<Double> v) { this.velVento10m = v; }
}