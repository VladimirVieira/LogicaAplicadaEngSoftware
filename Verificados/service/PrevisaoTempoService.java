package br.com.ufrn.pds1.projetopds1.verifyable.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import br.com.ufrn.pds1.projetopds1.verifyable.external.ComunicacaoComApiExternacao;

import br.com.ufrn.pds1.projetopds1.verifyable.exception.ComunicacaoApiException;
import br.com.ufrn.pds1.projetopds1.verifyable.exception.DadosInvalidosException;
import br.com.ufrn.pds1.projetopds1.verifyable.model.Alertas;
import br.com.ufrn.pds1.projetopds1.verifyable.model.PrevisaoTempo;

public class PrevisaoTempoService {
    //@ nullable
    //@ spec_public
    private ComunicacaoComApiExternacao apiExterna;

    public PrevisaoTempoService(ComunicacaoComApiExternacao apiExterna) {
        this.apiExterna = apiExterna;
    }

    //*********************************************************************************************************************
    //Definindo o intervalo de datas

    //@ ensures \result != null;
    //@ ensures \result.size() == 2;
    public ArrayList<String> obterData() {

        LocalDate dataHoje = LocalDate.now();
        LocalDate data15AnosAtras = dataHoje.minusYears(15);
        String dataAtual = dataHoje.toString();
        String dataNormalizada = data15AnosAtras.toString();
        ArrayList<String> data = new ArrayList<String>();
        data.add(dataAtual);
        data.add(dataNormalizada);

        return data;
    }

    //**********************************************************************************************************************
    //Constroi a URL

    //@ requires latitude >= -90 && latitude <= 90;
    //@ requires longitude >= -180 && longitude <= 180;
    //@ requires dataNormalizada != null && dataAtual != null;
    //@ ensures \result != null;
    public String obterUrl(double latitude, double longitude, String dataNormalizada, String dataAtual) {

        return  String.format("https://archive-api.open-meteo.com/v1/archive"
                + "?latitude=%s"
                + "&longitude=%s"
                + "&start_date=%s"
                + "&end_date=%s"
                + "&daily=temperature_2m_max,temperature_2m_min,windspeed_10m_max"
                + "&timezone=America/Fortaleza",latitude, longitude, dataNormalizada, dataAtual);
    }

    //***********************************************************************************************************************
    //Extraindo dados da open-meteo

    //@ requires url != null;
    //@ ensures \result != null;
    public Map<String, Object> obterDadosApi(String url){

        try {
            return apiExterna.extrairDadosApi(url);
        } catch (Exception e) {
            throw new ComunicacaoApiException("Erro ao realizar a comunicacao com a API",e);
        }

    }

    //************************************************************************************************************************

    //@ requires !Double.isNaN(latitude) && !Double.isNaN(longitude);
    //@ requires dailyPrevisao != null;
    //@ ensures \result != null;
    public PrevisaoTempo instanciarPrevisaoDoTempo(double latitude, double longitude, Map<String, Object> dailyPrevisao) {

        if (dailyPrevisao == null) {
            throw new DadosInvalidosException("Apresenta dados inválidos");
        }

        PrevisaoTempo armazemDadosPrevisao = new PrevisaoTempo();

        armazemDadosPrevisao.setData((Date) dailyPrevisao.get("time"));
        armazemDadosPrevisao.setLocal("Latitude:" + latitude + "Longitude:" + longitude);
        armazemDadosPrevisao.setTempMax((List<Double>) dailyPrevisao.get("temperature_2m_max"));
        armazemDadosPrevisao.setTempMin((List<Double>) dailyPrevisao.get("temperature_2m_min"));
        armazemDadosPrevisao.setVelVento10m((List<Double>) dailyPrevisao.get("windspeed_10m_max"));

        return armazemDadosPrevisao;
    }

    //************************************************************************************************************************
    //Previsão de vento para 7 dias

    //@ requires armazemDadosPrevisao != null;
    //@ requires dailyPrevisao != null;
    //@ ensures \result != null;
    public Map<String, List<Double>> processarDados(PrevisaoTempo armazemDadosPrevisao, Map<String, Object> dailyPrevisao) {

        if (dailyPrevisao == null) {
            throw new DadosInvalidosException("Apresenta dados inválidos");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate coletaData = LocalDate.now();

        List<String> historicoVento = (List<String>) dailyPrevisao.get("time");
        List<Double> tempMaior = (List<Double>) dailyPrevisao.get("temperature_2m_max");
        List<Double> tempMenor = (List<Double>) dailyPrevisao.get("temperature_2m_min");

        List<Double> mediasVentPorDia = new ArrayList<>();
        List<Double> mediasTempPorDia = new ArrayList<>();
        List<Double> tempMaiorPrev = new ArrayList<>();
        List<Double> tempMenorPrev = new ArrayList<>();

        for(int i = 1; i < 8; i++) {
            LocalDate coletaDataCopia = coletaData.plusDays(i);

            Double somatorioVento = 0.0;
            Double somatorioTemp = 0.0;
            Double somatorioTempMax = 0.0;
            Double somatorioTempMin = 0.0;

            for(int j = 1; j <= 15; j++) {
                LocalDate datacopia = coletaDataCopia.minusYears(j);
                String dataModificada = datacopia.format(formatter);

                int indiceData = historicoVento.indexOf(dataModificada);

                Double vento = armazemDadosPrevisao.getVelVento10m().get(indiceData);
                Double tempSup = armazemDadosPrevisao.getTempMax().get(indiceData);
                Double tempInf = armazemDadosPrevisao.getTempMin().get(indiceData);

                somatorioVento += vento;
                somatorioTemp += ((tempSup + tempInf)/2);
                somatorioTempMax+=tempSup;
                somatorioTempMin+=tempInf;
            }

            mediasVentPorDia.add((somatorioVento/15));
            tempMaiorPrev.add(somatorioTempMax/15);
            tempMenorPrev.add(somatorioTempMin/15);
            mediasTempPorDia.add(somatorioTemp/15);
        }

        Map<String, List<Double>> resultado = new HashMap<>();
        resultado.put("temperaturaMaior", tempMaiorPrev);
        resultado.put("temperaturaMenor", tempMenorPrev);
        resultado.put("mediasTemperatura", mediasTempPorDia);
        resultado.put("vento", mediasVentPorDia);

        return resultado;
    }

    //***************************************************************************************************************************

    //@ requires !Double.isNaN(latitude) && !Double.isNaN(longitude);
    //@ ensures \result != null;
    public PrevisaoTempo armazenarDadosPrevisao(double latitude, double longitude) {

        if (Double.isNaN(latitude) || Double.isNaN(longitude)) {
            throw new DadosInvalidosException("os valores de Latitude ou longitude não são numéricos.");
        } else if(latitude < -90 || latitude > 90) {
            throw new DadosInvalidosException("Os dados de latitude são inválidos, pois o intervalo válido compreende:[-90,90].");
        } else if(longitude < -180 || longitude > 180) {
            throw new DadosInvalidosException("Os dados de longitude são inválidos, pois o intervalo válido compreende:[-180,180].");
        }

        List<String> data = obterData();
        String dataAtual = data.get(0);
        String dataNormalizada = data.get(1);
        String url = obterUrl(latitude, longitude, dataNormalizada, dataAtual);
        Map<String, Object> dailyPrevisao = obterDadosApi(url);
        PrevisaoTempo armazemDadosPrevisao = instanciarPrevisaoDoTempo(latitude, longitude, dailyPrevisao);
        Map<String, List<Double>> resultado = processarDados(armazemDadosPrevisao, dailyPrevisao);

        armazemDadosPrevisao.setPrevisaoVento(resultado.get("vento"));
        armazemDadosPrevisao.setTempMaxPrevisao(resultado.get("temperaturaMaior"));
        armazemDadosPrevisao.setTempMinPrevisao(resultado.get("temperaturaMenor"));
        armazemDadosPrevisao.setPrevisaoTemperatura(resultado.get("mediasTemperatura"));

        return armazemDadosPrevisao;
    }

    //******************************************Previsão do tempo***************************************************************

    //@ requires infoPrev != null;
    //@ ensures \result != null;
    public List<Alertas> verificarAlertas(PrevisaoTempo infoPrev) {

        List<Alertas> informacao = new ArrayList<>();
        List<Double> tempMaxPrevisao = infoPrev.getTempMaxPrevisao();
        List<Double> tempMinPrevisao = infoPrev.getTempMinPrevisao();
        List<Double> ventos = infoPrev.getPrevisaoVento();

        for (int i = 0; i < tempMaxPrevisao.size(); i++) {

            Double temperaturaMax = tempMaxPrevisao.get(i);
            Double temperaturaMin = tempMinPrevisao.get(i);
            Double velVento = ventos.get(i);

            if(temperaturaMax >= 38 && velVento < 19) {
                informacao.add(new Alertas((i+1)+"° dia", "Temperatura muito alta prevista e ventos fraco"));
            } else if(temperaturaMax > 31 && velVento < 39) {
                informacao.add(new Alertas((i+1)+"° dia", "Temperatura elevada e vento moderado"));
            } else if(temperaturaMax < 12 && velVento > 39) {
                informacao.add(new Alertas((i+1)+"° dia", "Temperatura baixa e vento forte"));
            } else if(temperaturaMax > 13 && velVento > 20) {
                informacao.add(new Alertas((i+1)+"° dia", "Temperatura dentro da normalidade e vento forte"));
            } else {
                informacao.add(new Alertas((i+1)+"° dia", "Temperatura dentro da normalidade e vento moderado"));
            }

        }

        return informacao;
    }

    //*****************************************Emissao de alertas**************************************************************
}
