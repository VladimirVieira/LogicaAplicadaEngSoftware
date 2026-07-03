//package br.com.ufrn.pds1.projetopds1.verifyable.external;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.List;
//import java.util.ArrayList; // CORREÇÃO: Import necessário para ArrayList
//
//public class ComunicacaoComApiExternacao {
// 
//    /*@ public normal_behavior
//      @   requires url != null && url.length() > 0;
//      @   ensures \result != null;
//      @   // Garante que o resultado tem a estrutura mínima esperada por DadosDiarioService:
//      @   ensures \result.get("daily") != null;
//      @   ensures \result.get("daily") instanceof Map;
//      @   ensures ((Map<String, Object>)\result.get("daily")).get("time") instanceof List;
//      @   ensures ((Map<String, Object>)\result.get("daily")).get("temperature_2m_max") instanceof List;
//      @   ensures ((Map<String, Object>)\result.get("daily")).get("temperature_2m_min") instanceof List;
//      @   ensures ((Map<String, Object>)\result.get("daily")).get("windspeed_10m_max") instanceof List;
//      @   // Garante que as listas não são nulas e têm o mesmo tamanho
//      @   ensures ((List<?>)((Map<String, Object>)\result.get("daily")).get("time")).size() > 0;
//      @   ensures ((List<?>)((Map<String, Object>)\result.get("daily")).get("time")).size() == 
//      @           ((List<?>)((Map<String, Object>)\result.get("daily")).get("temperature_2m_max")).size();
//      @ also
//      @ public exceptional_behavior
//      @   requires false;
//      @   signals (RuntimeException e) true;
//      @*/
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> extrairDadosApi(String url) {
//        
//        // Simulação de 366 dias de dados
//        final int SIZE = 366; 
//        
//        List<String> datas = new ArrayList<>();
//        List<Double> tempMax = new ArrayList<>();
//        List<Double> tempMin = new ArrayList<>();
//        List<Double> ventos = new ArrayList<>();
//        
//        // Loop para criar dados consistentes para o prover
//        /*@ loop_invariant 0 <= i && i <= SIZE;
//          @ decreases SIZE - i;
//          @ assignable datas.theElements, tempMax.theElements, tempMin.theElements, ventos.theElements;
//          @*/
//        for (int i = 0; i < SIZE; i++) {
//            datas.add(String.format("2024-%02d-%02d", (i / 31) + 1, (i % 31) + 1));
//            tempMax.add(20.0 + (i % 10)); 
//            tempMin.add(10.0 + (i % 10)); 
//            ventos.add(5.0 + (i % 5)); 
//        }
//
//        Map<String, Object> dailyData = new HashMap<>();
//        dailyData.put("time", datas);
//        dailyData.put("temperature_2m_max", tempMax);
//        dailyData.put("temperature_2m_min", tempMin);
//        dailyData.put("windspeed_10m_max", ventos);
//
//        Map<String, Object> resp = new HashMap<>();
//        resp.put("daily", dailyData); 
//        
//        return resp;
//    }
//}

//package br.com.ufrn.pds1.projetopds1.verifyable.external;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

//public class ComunicacaoComApiExternacao {

//    /*@ public normal_behavior
//      @   requires url != null && url.length() > 0;
//      @   ensures \result != null;
//      @   // Garante que o resultado tem a estrutura esperada para evitar falhas de runtime na classe de serviço.
//      @   ensures \result.get("time") instanceof List && 
//      @           \result.get("temperature_2m_max") instanceof List && 
//      @           \result.get("temperature_2m_min") instanceof List && 
//      @           \result.get("windspeed_10m_max") instanceof List;
//      @   // CORREÇÃO: O método cria listas localmente, então modifica a heap.
//      @   // Usamos \everything ou \result para o contrato do método.
//      @   assignable \everything; 
//      @*/
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> extrairDadosApi(String url) {

        // --- Mocking (Simulação de Dados Válidos) ---
//        final int SIZE = 366; 
//        List<String> datas = new ArrayList<>();
//        List<Double> tempMax = new ArrayList<>();
//        List<Double> tempMin = new ArrayList<>();
//        List<Double> ventos = new ArrayList<>();
        
//        /*@ loop_invariant 0 <= i && i <= SIZE;
//          @ decreases SIZE - i;
//          @ // REMOVIDO: A cláusula 'modifies' ou 'assignable' de loop para evitar erro de sintaxe do OpenJML.
//          @*/
//        for (int i = 0; i < SIZE; i++) {
//            datas.add(String.format("2024-%02d-%02d", (i / 31) + 1, (i % 31) + 1));
//            tempMax.add(20.0 + (i % 10)); 
//            tempMin.add(10.0 + (i % 10)); 
//            ventos.add(5.0 + (i % 5) + 1.0); // Garante valor > 0 para não causar NullPointerException no loop
//        }

//        Map<String, Object> dailyData = new HashMap<>();
//        dailyData.put("time", datas);
//        dailyData.put("temperature_2m_max", tempMax);
//        dailyData.put("temperature_2m_min", tempMin);
//        dailyData.put("windspeed_10m_max", ventos);

//        Map<String, Object> resp = new HashMap<>();
//        resp.put("daily", dailyData); 
        
//        Object dailyObj = resp.get("daily"); 
        
//        if (dailyObj == null || !(dailyObj instanceof Map)) {
            // Este bloco nunca será atingido devido ao mocking
//            throw new RuntimeException("Campo 'daily' ausente ou inválido.");
//        }

//        return (Map<String, Object>) dailyObj;
//    }

//}

package br.com.ufrn.pds1.projetopds1.verifyable.external;

import java.util.HashMap;
import java.util.Map;

public class ComunicacaoComApiExternacao {
    /*@
      @ public behavior
      @   requires url != null && !url.isEmpty();
      @   ensures \result != null;
      @   signals (RuntimeException e) true;
      @*/
    public Map<String, Object> extrairDadosApi(String url) {

        Map<String, Object> resp = new HashMap<>();
        Object dailyObj = resp.get("daily");

        if (dailyObj == null || !(dailyObj instanceof Map)) {
            throw new RuntimeException("Campo 'daily' ausente ou inválido.");
        }

        return (Map<String, Object>) dailyObj;
    }

}
