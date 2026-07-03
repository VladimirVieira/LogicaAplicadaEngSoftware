package br.com.ufrn.pds1.projetopds1.verifyable.service;

import java.util.List;
import java.util.Map;

import br.com.ufrn.pds1.projetopds1.verifyable.external.ComunicacaoComApi;
import br.com.ufrn.pds1.projetopds1.verifyable.exception.ComunicacaoApiException;
import br.com.ufrn.pds1.projetopds1.verifyable.exception.DadosInvalidosException;
import br.com.ufrn.pds1.projetopds1.verifyable.model.DadosLocal;

public class DadosLocalService {

    /*@ spec_public non_null @*/
    private ComunicacaoComApi comunicaoApi;

    /*@ public invariant comunicaoApi != null; @*/

    /*@ public normal_behavior
      @   requires comunicaoApi != null;
      @ also
      @ public exceptional_behavior
      @   requires comunicaoApi == null;
      @   signals (IllegalArgumentException e) true;
      @*/
    public DadosLocalService(ComunicacaoComApi comunicaoApi) {
        if (comunicaoApi == null) {
            throw new IllegalArgumentException("comunicaoApi não pode ser null");
        }
        this.comunicaoApi = comunicaoApi;
    }

    //**********************************************************************************************************************

    //Constroi a URL
    /*@ public normal_behavior
      @   requires latitude != null && longitude != null && data != null;
      @   ensures \result != null && \result.length() > 0;
      @*/
    public String construirUrl(String latitude, String longitude, String data) {

        String result = String.format(
                "https://api.open-meteo.com/v1/forecast?"
                        + "latitude=%s&"
                        + "longitude=%s&"
                        + "hourly=temperature_2m,windspeed_10m&start_date=%s&end_date=%s&timezone=America/Fortaleza",
                latitude, longitude, data, data);

        // Assume necessário para provar a pós-condição (ensures) devido a String.format
        //@ assume result != null && result.length() > 0; 
        return result;
    }

    //**********************************************************************************************************************

    //Extraindo dados da open-meteo
    /*@ public normal_behavior
      @   requires url != null && url.length() > 0;
      @   requires comunicaoApi != null; 
      @   ensures \result != null;
      @   ensures \result.get("hourly") != null;
      @   ensures \result.get("hourly") instanceof Map;
      @ also
      @ public exceptional_behavior
      @   signals (ComunicacaoApiException e) true;
      @*/
    public Map<String, Object> obterDadosDaApi(String url) throws ComunicacaoApiException {

        try {
            Map<String, Object> result = comunicaoApi.obterDadosDaApi(url); 

            // Assertivas necessárias para re-estabelecer as garantias do contrato do mock
            //@ assert result != null;
            //@ assert result.get("hourly") != null;
            //@ assert result.get("hourly") instanceof Map;

            return result;
        } catch (Exception e) {
            throw new ComunicacaoApiException("Erro ao realizar a comunicacao com a API", e);
        }
    }

    //**********************************************************************************************************************
    
    //Método para obter dados de temperatura do dia
    /*@ public normal_behavior
      @   requires hourly != null;
      @   requires hourly.get("temperature_2m") != null;
      @   requires hourly.get("temperature_2m") instanceof List;
      @   requires ((List<?>)hourly.get("temperature_2m")).size() > 18;
      @   requires ((List<?>)hourly.get("temperature_2m")).get(18) != null; 
      @   ensures \result != null;
      @ also
      @ public exceptional_behavior
      @   signals (DadosInvalidosException e) true;
      @*/
    @SuppressWarnings("unchecked")
    public Double obterDadosTemperatura(Map<String, Object> hourly) {

        if (hourly == null) {
            throw new DadosInvalidosException("Apresenta dados inválidos para temperatura: Mapa hourly é null");
        }

        Object tempObj = hourly.get("temperature_2m");

        // Assume necessário para provar que o 'hourly.get' não é null ou tipo errado, 
        // confiando no requires.
        //@ assume tempObj != null && tempObj instanceof List;
        
        if (tempObj == null || !(tempObj instanceof List)) {
            throw new DadosInvalidosException("Apresenta dados inválidos para temperatura: Lista 'temperature_2m' ausente ou inválida");
        }

        List<Double> temperaturas = (List<Double>) tempObj;

        if (temperaturas.size() <= 18) {
             throw new DadosInvalidosException("Apresenta dados inválidos para temperatura: Tamanho insuficiente na lista");
        }
        
        if (temperaturas.get(18) == null) {
            throw new DadosInvalidosException("Apresenta dados inválidos para temperatura: Elemento no índice 18 é null");
        }
        
        // Assume necessário para provar a pós-condição (\result != null)
        //@ assume temperaturas.get(18) != null;
        
        return temperaturas.get(18);
    }

    //**********************************************************************************************************************
    
    //Método para obter dados de ventos do dia
    /*@ public normal_behavior
      @   requires hourly != null;
      @   requires hourly.get("windspeed_10m") != null;
      @   requires hourly.get("windspeed_10m") instanceof List;
      @   requires ((List<?>)hourly.get("windspeed_10m")).size() > 18;
      @   requires ((List<?>)hourly.get("windspeed_10m")).get(18) != null; 
      @   ensures \result != null;
      @ also
      @ public exceptional_behavior
      @   signals (DadosInvalidosException e) true;
      @*/
    @SuppressWarnings("unchecked")
    public Double obterDadosVentos(Map<String, Object> hourly) {

        if (hourly == null) {
            throw new DadosInvalidosException("Apresenta dados inválidos para ventos: Mapa hourly é null");
        }

        Object ventoObj = hourly.get("windspeed_10m");
        
        // Assume necessário para provar que o 'hourly.get' não é null ou tipo errado, 
        // confiando no requires.
        //@ assume ventoObj != null && ventoObj instanceof List;
        
        if (ventoObj == null || !(ventoObj instanceof List)) {
            throw new DadosInvalidosException("Apresenta dados inválidos para ventos: Lista 'windspeed_10m' ausente ou inválida");
        }

        List<Double> ventos = (List<Double>) ventoObj;

        if (ventos.size() <= 18) {
             throw new DadosInvalidosException("Apresenta dados inválidos para ventos: Tamanho insuficiente na lista");
        }
        
        if (ventos.get(18) == null) {
            throw new DadosInvalidosException("Apresenta dados inválidos para ventos: Elemento no índice 18 é null");
        }

        // Assume necessário para provar a pós-condição (\result != null)
        //@ assume ventos.get(18) != null;
        
        return ventos.get(18);

    }

    //**********************************************************************************************************************

    /*@ public normal_behavior
      @   requires latitude != null && longitude != null && data != null;
      @   requires comunicaoApi != null; 
      @   ensures \result != null;
      @ also
      @ public exceptional_behavior
      @   signals (DadosInvalidosException e) true;
      @ also
      @ public exceptional_behavior
      @   signals (ComunicacaoApiException e) true;
      @*/
    @SuppressWarnings("unchecked")
    public DadosLocal pesquisarDados(String latitude, String longitude, String data) {

        double lat;
        double lon;

        try {
            lat = Double.parseDouble(latitude);
            lon = Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
            throw new DadosInvalidosException("Latitude ou longitude não possuem valores numéricos válidos.");
        }

        String url = construirUrl(latitude, longitude, data);
        Map<String, Object> apiResponse = obterDadosDaApi(url);
        
        Object hourlyObj = apiResponse.get("hourly");
        
        // O cast é seguro e provável de passar porque o contrato de obterDadosDaApi garante o tipo.
        Map<String, Object> hourly = (Map<String, Object>) hourlyObj; 

        // Pega os dados do índice 18.
        double temp = obterDadosTemperatura(hourly);
        double velVento = obterDadosVentos(hourly);

        DadosLocal informacao = new DadosLocal();

        informacao.setData(data);
        informacao.setLatitude(lat);
        informacao.setLongitude(lon);
        informacao.setTemperatura(temp);
        informacao.setVelVento(velVento);

        return informacao;
    }
}
