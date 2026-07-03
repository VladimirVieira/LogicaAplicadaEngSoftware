package br.com.ufrn.pds1.projetopds1.verifyable.service;

import br.com.ufrn.pds1.projetopds1.verifyable.model.DadosDiariosHistorico;
import br.com.ufrn.pds1.projetopds1.verifyable.model.DadosLocalComparativo;
import br.com.ufrn.pds1.projetopds1.verifyable.model.ComparacaoResultadosView;

import br.com.ufrn.pds1.projetopds1.verifyable.exception.ComunicacaoApiException;
import br.com.ufrn.pds1.projetopds1.verifyable.exception.DadosInvalidosException;


public class ComparacaoMeteorologicaService {

    /*@ non_null @*/
    /*@ spec_public @*/
    private final DadosDiarioService dadosDiarioService;

    /*@ public invariant dadosDiarioService != null; @*/

    /*@ public normal_behavior
      @   requires dadosDiarioService != null;
      @   ensures this.dadosDiarioService == dadosDiarioService;
      @   // CORREÇÃO FINAL: Usamos \nothing. Para campos final inicializados em construtores,
      @   // o OpenJML permite \nothing se não houver outras atribuições, evitando referência
      @   // ao parâmetro formal na cláusula assignable.
      @   assignable \nothing; 
      @ also
      @ public exceptional_behavior
      @   requires dadosDiarioService == null;
      @   signals (IllegalArgumentException e) true;
      @*/
    public ComparacaoMeteorologicaService(DadosDiarioService dadosDiarioService) {
        if (dadosDiarioService == null) throw new IllegalArgumentException("DadosDiarioService não pode ser nulo.");
        this.dadosDiarioService = dadosDiarioService;
    }

    /*@ public normal_behavior
      @   requires -90.0 <= lat1 && lat1 <= 90.0;
      @   requires -180.0 <= lon1 && lon1 <= 180.0;
      @   requires -90.0 <= lat2 && lat2 <= 90.0;
      @   requires -180.0 <= lon2 && lon2 <= 180.0;
      @
      @   ensures \result != null;
      @   ensures \result.getLocal1() != null; // Aviso: método não-puro (requer /*@ pure @*/ em ComparacaoResultadosView)
      @   ensures \result.getLocal2() != null; // Aviso: método não-puro (requer /*@ pure @*/ em ComparacaoResultadosView)
      @   ensures \fresh(\result);
      @   assignable \everything;
      @ also
      @ public exceptional_behavior
      @   requires Double.isNaN(lat1) || Double.isNaN(lon1) || lat1 < -90.0 || lat1 > 90.0 || lon1 < -180.0 || lon1 > 180.0
      @         || Double.isNaN(lat2) || Double.isNaN(lon2) || lat2 < -90.0 || lat2 > 90.0 || lon2 < -180.0 || lon2 > 180.0;
      @   signals (DadosInvalidosException e) true;
      @ also
      @ public exceptional_behavior
      @   signals (ComunicacaoApiException e) true;
      @*/
    public ComparacaoResultadosView compararDadosTrimestrais(double lat1, double lon1, double lat2, double lon2) 
            throws ComunicacaoApiException, DadosInvalidosException { 

        DadosDiariosHistorico historicoLocal1 = dadosDiarioService.armazenarDados(lat1, lon1);
        DadosDiariosHistorico historicoLocal2 = dadosDiarioService.armazenarDados(lat2, lon2);

        DadosLocalComparativo comparativoLocal1 = new DadosLocalComparativo(
                String.format("Local 1 (Lat: %.2f, Lon: %.2f)", lat1, lon1),
                historicoLocal1.getPrimeiroTrimestre(),
                historicoLocal1.getSegundoTrimestre(),
                historicoLocal1.getTerceiroTrimestre(),
                historicoLocal1.getQuartoSemestre()
        );

        DadosLocalComparativo comparativoLocal2 = new DadosLocalComparativo(
                String.format("Local 2 (Lat: %.2f, Lon: %.2f)", lat2, lon2),
                historicoLocal2.getPrimeiroTrimestre(),
                historicoLocal2.getSegundoTrimestre(),
                historicoLocal2.getTerceiroTrimestre(),
                historicoLocal2.getQuartoSemestre()
        );

        return new ComparacaoResultadosView(comparativoLocal1, comparativoLocal2);
    }
}
