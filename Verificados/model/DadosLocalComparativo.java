package br.com.ufrn.pds1.projetopds1.verifyable.model;

public class DadosLocalComparativo {

    //@ public invariant identificador != null;
    //@ public invariant mediaVentoTrimestre1 != null;
    //@ public invariant mediaVentoTrimestre2 != null;
    //@ public invariant mediaVentoTrimestre3 != null;
    //@ public invariant mediaVentoTrimestre4 != null;

    /*@ public normal_behavior
      @   requires identificador != null;
      @   requires mediaVentoTrimestre1 != null;
      @   requires mediaVentoTrimestre2 != null;
      @   requires mediaVentoTrimestre3 != null;
      @   requires mediaVentoTrimestre4 != null;
      @   assignable \everything;
      @   ensures this.identificador == identificador;
      @   ensures this.mediaVentoTrimestre1 == mediaVentoTrimestre1;
      @   ensures this.mediaVentoTrimestre2 == mediaVentoTrimestre2;
      @   ensures this.mediaVentoTrimestre3 == mediaVentoTrimestre3;
      @   ensures this.mediaVentoTrimestre4 == mediaVentoTrimestre4;
      @ also
      @ public exceptional_behavior
      @   requires identificador == null ||
      @            mediaVentoTrimestre1 == null ||
      @            mediaVentoTrimestre2 == null ||
      @            mediaVentoTrimestre3 == null ||
      @            mediaVentoTrimestre4 == null;
      @   assignable \nothing;
      @   signals_only NullPointerException;
      @*/
    public DadosLocalComparativo(String identificador,
                                 Double mediaVentoTrimestre1,
                                 Double mediaVentoTrimestre2,
                                 Double mediaVentoTrimestre3,
                                 Double mediaVentoTrimestre4) {
        this.identificador = identificador;
        this.mediaVentoTrimestre1 = mediaVentoTrimestre1;
        this.mediaVentoTrimestre2 = mediaVentoTrimestre2;
        this.mediaVentoTrimestre3 = mediaVentoTrimestre3;
        this.mediaVentoTrimestre4 = mediaVentoTrimestre4;
    }

    //@ spec_public
    private String identificador;
    //@ spec_public
    private Double mediaVentoTrimestre1;
    //@ spec_public
    private Double mediaVentoTrimestre2;
    //@ spec_public
    private Double mediaVentoTrimestre3;
    //@ spec_public
    private Double mediaVentoTrimestre4;

    /*@ public normal_behavior
      @   ensures \result == identificador;
      @   ensures \result != null;
      @   pure
      @*/
    public String getIdentificador() {
        return identificador;
    }

    /*@ public normal_behavior
      @   ensures \result == mediaVentoTrimestre1;
      @   ensures \result != null;
      @   pure
      @*/
    public Double getMediaVentoTrimestre1() {
        return mediaVentoTrimestre1;
    }

    /*@ public normal_behavior
      @   ensures \result == mediaVentoTrimestre2;
      @   ensures \result != null;
      @   pure
      @*/
    public Double getMediaVentoTrimestre2() {
        return mediaVentoTrimestre2;
    }

    /*@ public normal_behavior
      @   ensures \result == mediaVentoTrimestre3;
      @   ensures \result != null;
      @   pure
      @*/
    public Double getMediaVentoTrimestre3() {
        return mediaVentoTrimestre3;
    }

    /*@ public normal_behavior
      @   ensures \result == mediaVentoTrimestre4;
      @   ensures \result != null;
      @   pure
      @*/
    public Double getMediaVentoTrimestre4() {
        return mediaVentoTrimestre4;
    }
}