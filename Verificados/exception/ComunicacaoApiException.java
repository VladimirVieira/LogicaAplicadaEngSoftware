package br.com.ufrn.pds1.projetopds1.verifyable.exception;

public class ComunicacaoApiException extends RuntimeException {

    /*@
      @ requires mensagem != null;
      @ ensures getMessage().equals(mensagem);
      @*/
    public ComunicacaoApiException(String mensagem) {
        super(mensagem);
    }

    /*@
      @ requires mensagem != null;
      @ requires causa != null;
      @ ensures getMessage().equals(mensagem);
      @*/
    public ComunicacaoApiException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

