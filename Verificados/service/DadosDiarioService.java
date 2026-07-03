package br.com.ufrn.pds1.projetopds1.verifyable.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.ufrn.pds1.projetopds1.verifyable.external.ComunicacaoComApiExternacao;
import br.com.ufrn.pds1.projetopds1.verifyable.exception.ComunicacaoApiException;
import br.com.ufrn.pds1.projetopds1.verifyable.exception.DadosInvalidosException;
import br.com.ufrn.pds1.projetopds1.verifyable.model.DadosDiariosHistorico;

public class DadosDiarioService {

    //@ non_null
    //@ spec_public
    private ComunicacaoComApiExternacao apiExterna;


    //@ requires apiExterna != null;
    //@ ensures this.apiExterna == apiExterna;
    public DadosDiarioService(ComunicacaoComApiExternacao apiExterna) {
        if (apiExterna == null) throw new IllegalArgumentException("apiExterna não pode ser nula");
        this.apiExterna = apiExterna;
    }

    /*@
      @ ensures \result != null;
      @ ensures \result.size() == 2;
      @ ensures (\forall int i; 0 <= i && i < \result.size(); \result.get(i) != null);
      @ assignable \nothing;
      @*/
    public List<String> obterIntervaloDeData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate coletaDataAtual = LocalDate.now();

        LocalDate anoPassado = LocalDate.of(coletaDataAtual.getYear() - 1, 1, 1);
        LocalDate fimAnoPassado = LocalDate.of(coletaDataAtual.getYear() - 1, 12, 31);

        List<String> intervalo = new ArrayList<>();
        intervalo.add(anoPassado.format(formatter));
        intervalo.add(fimAnoPassado.format(formatter));

        return intervalo;
    }

    /*@
      @ requires latitude >= -90 && latitude <= 90;
      @ requires longitude >= -180 && longitude <= 180;
      @ requires dataInicio != null;
      @ requires dataFim != null;
      @ ensures \result != null;
      @ assignable \nothing;
      @*/
    public String montarUrl(double latitude, double longitude, String dataInicio, String dataFim) {
        return String.format(
            "https://archive-api.open-meteo.com/v1/archive?latitude=%s&longitude=%s&start_date=%s&end_date=%s&daily=temperature_2m_max,temperature_2m_min,windspeed_10m_max&timezone=America/Fortaleza",
             latitude, longitude, dataInicio, dataFim);
    }

    /*@
      @ requires url != null;
      @ ensures \result != null;
      @ ensures \result.get("temperature_2m_max") instanceof java.util.List;
      @ ensures \result.get("temperature_2m_min") instanceof java.util.List;
      @ ensures \result.get("windspeed_10m_max") instanceof java.util.List;
      @ ensures \result.get("time") instanceof java.util.List;
      @*/
    public Map<String, Object> extrairDadosApi(String url) {
        if (url == null || url.length() == 0) {
            throw new DadosInvalidosException("URL inválida.");
        }
        try {
            return apiExterna.extrairDadosApi(url);
        } catch (Exception e) {
            throw new ComunicacaoApiException("Erro ao realizar a comunicacao com a API", e);
        }
    }

    /*@
      @ requires daily != null;
      @ ensures \result != null;
      @ ensures ((java.util.List) daily.get("temperature_2m_max")).size() > 0;
      @ ensures ((java.util.List) daily.get("temperature_2m_min")).size() > 0;
      @*/
    @SuppressWarnings("unchecked")
    public DadosDiariosHistorico instanciarDadosDiario(Double latitude, Double longitude, Map<String, Object> daily) {

        if (daily == null) {
            throw new DadosInvalidosException("Apresenta dados inválidos");
        }

        DadosDiariosHistorico armazemDados = new DadosDiariosHistorico();

        armazemDados.setData((List<String>) daily.get("time"));
        armazemDados.setLocal("Latitude:" + latitude + " Longitude:" + longitude);
        armazemDados.setTempMax((List<Double>) daily.get("temperature_2m_max"));
        armazemDados.setTempMin((List<Double>) daily.get("temperature_2m_min"));
        armazemDados.setVelVento10m((List<Double>) daily.get("windspeed_10m_max"));

        return armazemDados;
    }

    /*@
      @ requires tempMaior != null && tempMenor != null;
      @ requires tempMaior.size() == tempMenor.size();
      @ requires tempMaior.size() > 0 && tempMenor.size() > 0;
      @ ensures \result.size() == tempMaior.size();
      @ assignable \nothing;
      @*/
    public List<Double> calcularTemperaturaMedia(List<Double> tempMaior, List<Double> tempMenor) {

        if (tempMaior == null || tempMenor == null) {
            throw new DadosInvalidosException("As listas são inválidas");
        }
        if (tempMaior.size() != tempMenor.size() || tempMaior.isEmpty()) {
            throw new DadosInvalidosException("Listas inválidas ou tamanho diferente");
        }

        List<Double> mediasPorDia = new ArrayList<>();

        /*@ loop_invariant 0 <= i && i <= tempMaior.size();
          @ loop_invariant mediasPorDia.size() == i;
          @ decreases tempMaior.size() - i;
          @*/
        for (int i = 0; i < tempMaior.size(); i++) {
            mediasPorDia.add((tempMaior.get(i) + tempMenor.get(i)) / 2.0);
        }
        return mediasPorDia;
    }

    /*@
      @ requires datasAno != null;
      @ requires ventos != null;
      @ requires armazemDados != null;
      @ requires datasAno.size() == ventos.size();
      @ ensures \result == armazemDados;
      @ assignable \everything;
      @*/
    public DadosDiariosHistorico calcularVelocidadeVento(List<String> datasAno, List<Double> ventos, DadosDiariosHistorico armazemDados) {

        if (datasAno == null || ventos == null) {
            throw new DadosInvalidosException("As listas são inválidas/vazias");
        }
        if (armazemDados == null) {
            throw new DadosInvalidosException("Dados inválidos");
        }
        if (datasAno.size() != ventos.size() || datasAno.isEmpty()) {
            throw new DadosInvalidosException("Listas de datas e ventos incompatíveis");
        }

        double primeiroTri = 0, segundoTri = 0, terceiroTri = 0, quartoTri = 0;
        int cont1Tri = 0, cont2Tri = 0, cont3Tri = 0, cont4Tri = 0;

        /*@ loop_invariant 0 <= i && i <= datasAno.size();
          @ decreases datasAno.size() - i;
          @*/
        for (int i = 0; i < datasAno.size(); i++) {
            LocalDate data = LocalDate.parse(datasAno.get(i));
            int mesAtual = data.getMonthValue();

            if (mesAtual >= 1 && mesAtual <= 3) {
                primeiroTri += ventos.get(i);
                cont1Tri++;
            } else if (mesAtual >= 4 && mesAtual <= 6) {
                segundoTri += ventos.get(i);
                cont2Tri++;
            } else if (mesAtual >= 7 && mesAtual <= 9) {
                terceiroTri += ventos.get(i);
                cont3Tri++;
            } else {
                quartoTri += ventos.get(i);
                cont4Tri++;
            }
        }

        //@ assert cont1Tri > 0;
        //@ assert cont2Tri > 0;
        //@ assert cont3Tri > 0;
        //@ assert cont4Tri > 0;

        armazemDados.setPrimeiroTrimestre(primeiroTri / cont1Tri);
        armazemDados.setSegundoTrimestre(segundoTri / cont2Tri);
        armazemDados.setTerceiroTrimestre(terceiroTri / cont3Tri);
        armazemDados.setQuartoSemestre(quartoTri / cont4Tri);
        return armazemDados;
    }

    /*@
      @ requires latitude >= -90 && latitude <= 90;
      @ requires longitude >= -180 && longitude <= 180;
      @ ensures \result != null;
      @ assignable \everything;
      @*/
    @SuppressWarnings("unchecked")
    public DadosDiariosHistorico armazenarDados(double latitude, double longitude) {

        if (Double.isNaN(latitude) || Double.isNaN(longitude)) {
            throw new DadosInvalidosException("Valores de latitude ou longitude não são numéricos.");
        }
        if (latitude < -90 || latitude > 90) {
            throw new DadosInvalidosException("Latitude fora do intervalo [-90,90].");
        }
        if (longitude < -180 || longitude > 180) {
            throw new DadosInvalidosException("Longitude fora do intervalo [-180,180].");
        }

        List<String> dataIntervalo = obterIntervaloDeData();
        String dataInicio = dataIntervalo.get(0);
        String dataFim = dataIntervalo.get(1);

        String url = montarUrl(latitude, longitude, dataInicio, dataFim);

        Map<String, Object> daily = extrairDadosApi(url);
        DadosDiariosHistorico armazemDados = instanciarDadosDiario(latitude, longitude, daily);

        List<Double> tempMaior = (List<Double>) daily.get("temperature_2m_max");
        List<Double> tempMenor = (List<Double>) daily.get("temperature_2m_min");

        //@ assume tempMaior != null && tempMenor != null;
        //@ assume tempMaior.size() > 0 && tempMenor.size() > 0;

        List<Double> ventos = (List<Double>) daily.get("windspeed_10m_max");
        List<String> datasAno = (List<String>) daily.get("time");

        return calcularVelocidadeVento(datasAno, ventos, armazemDados);
    }
}


