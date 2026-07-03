//package br.com.ufrn.pds1.projetopds1.verifyable.external;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

// Esta classe agora é um mock (simulação) para permitir a verificação de DadosLocalService.
//public class ComunicacaoComApi {

//    /*@ public normal_behavior
//      @   requires url != null && !url.isEmpty();
//      @   // Garante a estrutura mínima: um mapa com a chave "hourly" sendo um Map.
//      @   ensures \result != null;
//      @   ensures \result.get("hourly") != null;
//      @   ensures \result.get("hourly") instanceof Map;
//      @ also
//      @ public exceptional_behavior
//      @   // CORREÇÃO 1: Colocando o requires antes do signals
//      @   requires false; 
//      @   signals (RuntimeException e) true; 
//      @*/
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> obterDadosDaApi(String url) {

//        // Dados de mock: precisamos de pelo menos 19 elementos para o índice 18 ser válido
//        final int NUM_HOURLY_POINTS = 19; 

//        List<Double> temperaturas = new ArrayList<>();
//        List<Double> ventos = new ArrayList<>();

        // Usando loop para criar as listas (mais fácil para o prover)
//        /*@ loop_invariant 0 <= i && i <= NUM_HOURLY_POINTS;
//          @ // CORREÇÃO 2: A cláusula assignable foi removida, pois não é uma loop specification
//          @ decreases NUM_HOURLY_POINTS - i;
//          @*/
//        for (int i = 0; i < NUM_HOURLY_POINTS; i++) {
//            temperaturas.add((double) (10 + i)); // Simulação de dados
//            ventos.add((double) (5 + i));        // Simulação de dados
//        }

//        Map<String, Object> hourlyData = new HashMap<>();
//        hourlyData.put("temperature_2m", temperaturas);
//        hourlyData.put("windspeed_10m", ventos);

//        Map<String, Object> resposta = new HashMap<>();
//        resposta.put("hourly", hourlyData);

//        return resposta;
//    }
//}

package br.com.ufrn.pds1.projetopds1.verifyable.external;

import java.util.HashMap;
import java.util.Map;

public class ComunicacaoComApi {
    /*@
      @ public behavior
      @   requires url != null && !url.isEmpty();
      @   ensures \result != null;
      @   signals (RuntimeException e) true;
      @*/
    public Map<String, Object> obterDadosDaApi(String url) {

        Map<String, Object> resposta = new HashMap<>();
        Object hourlyObj = resposta.get("hourly");

        if (hourlyObj == null || !(hourlyObj instanceof Map)) {
            throw new RuntimeException("Campo 'hourly' ausente ou inválido.");
        }
        return (Map<String, Object>) hourlyObj;
    }

}
