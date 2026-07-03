package br.com.ufrn.pds1.projetopds1.verifyable.exception;

public class DadosInvalidosException extends RuntimeException {

    /*@
      @ requires mensagem != null;
      @ ensures getMessage().equals(mensagem);
      @*/
    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }

    /*@
      @ requires mensagem != null;
      @ requires causa != null;
      @ ensures getMessage().equals(mensagem);
      @*/
    public DadosInvalidosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

