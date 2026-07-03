package br.com.ufrn.pds1.projetopds1.verifyable.model;

public class Alertas {

    //@ spec_public
    private String informacoesDia;

    //@ spec_public
    private String classificacaoAlerta;

    //@ public invariant informacoesDia != null;
    //@ public invariant classificacaoAlerta != null;

    /*@
      @ requires informacoesDia != null;
      @ requires classificacaoAlerta != null;
      @ ensures this.informacoesDia.equals(informacoesDia);
      @ ensures this.classificacaoAlerta.equals(classificacaoAlerta);
      @*/
    public Alertas(String informacoesDia, String classificacaoAlerta) {
        this.informacoesDia = informacoesDia;
        this.classificacaoAlerta = classificacaoAlerta;
    }

    //@ ensures \result == informacoesDia;
    public String getInformacoesDia() {
        return informacoesDia;
    }

    /*@
      @ requires informacoesDia != null;
      @ ensures this.informacoesDia.equals(informacoesDia);
      @*/
    public void setInformacoesDia(String informacoesDia) {
        this.informacoesDia = informacoesDia;
    }

    //@ ensures \result == classificacaoAlerta;
    public String getClassificacaoAlerta() {
        return classificacaoAlerta;
    }

    /*@
      @ requires classificacaoAlerta != null;
      @ ensures this.classificacaoAlerta.equals(classificacaoAlerta);
      @*/
    public void setClassificacaoAlerta(String classificacaoAlerta) {
        this.classificacaoAlerta = classificacaoAlerta;
    }
}
