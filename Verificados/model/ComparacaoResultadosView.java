package br.com.ufrn.pds1.projetopds1.verifyable.model;

public class ComparacaoResultadosView {

    //@ spec_public
    private DadosLocalComparativo local1;

    //@ spec_public
    private DadosLocalComparativo local2;

    /*@ public invariant local1 != null;
      @ public invariant local2 != null;
      @*/

    /*@
      @ requires local1 != null;
      @ requires local2 != null;
      @ ensures this.local1 == local1;
      @ ensures this.local2 == local2;
      @*/
    public ComparacaoResultadosView(DadosLocalComparativo local1, DadosLocalComparativo local2) {
        this.local1 = local1;
        this.local2 = local2;
    }

    //@ assignable \nothing;
    //@ ensures \result == local1;
    //@ pure
    public DadosLocalComparativo getLocal1() {
        return local1;
    }

    //@ assignable \nothing;
    //@ ensures \result == local2;
    //@ pure
    public DadosLocalComparativo getLocal2() {
        return local2;
    }
}
