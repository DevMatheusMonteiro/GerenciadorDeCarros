package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.carro.CarroDTOInput;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CarroControllerTest {
    HttpURLConnection conexao = null;
    @Test
    void validarListagemDeCarros() throws IOException {
        URL url = new URL("http://127.0.0.1:4567/carros");
        conexao = (HttpURLConnection) url.openConnection();
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, conexao.getResponseCode());
    }
    @Test
    void validarInsercaoDeCarroComJsonObject() throws IOException {
        URL urlApiCars = new URL("https://freetestapi.com/api/v1/cars/1");
        HttpURLConnection conexaoApiCars = (HttpURLConnection) urlApiCars.openConnection();
        if (conexaoApiCars.getResponseCode() == 200) {
            CarroDTOInput carroDTOInput = getCarroDTOInputJsonObject(conexaoApiCars);
            URL url = new URL("http://127.0.0.1:4567/carros");
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);
            conexao.setRequestMethod("POST");
            JSONObject json = new JSONObject();
            json.put("model", carroDTOInput.getModelo());
            json.put("placa", carroDTOInput.getPlaca());
            json.put("chassi", carroDTOInput.getChassi());
            conexao.getOutputStream().write(json.toString().getBytes());
            Assertions.assertEquals(HttpURLConnection.HTTP_CREATED, conexao.getResponseCode());
        }
    }
    @Test
    void validarInsercaoDeCarroComObjectMapper() throws IOException {
        URL urlApiCars = new URL("https://freetestapi.com/api/v1/cars/2");
        HttpURLConnection conexaoApiCars = (HttpURLConnection) urlApiCars.openConnection();
        if (conexaoApiCars.getResponseCode() == 200) {
            CarroDTOInput carroDTOInput = getCarroDTOInputObjectMapper(conexaoApiCars);
            URL url = new URL("http://127.0.0.1:4567/carros");
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);
            conexao.setRequestMethod("POST");
            ObjectMapper object  = new ObjectMapper();
            String json = object.writeValueAsString(carroDTOInput);
            conexao.getOutputStream().write(json.getBytes());
            Assertions.assertEquals(HttpURLConnection.HTTP_CREATED, conexao.getResponseCode());
        }
    }
    static CarroDTOInput getCarroDTOInputJsonObject(HttpURLConnection conexaoApiCars) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(conexaoApiCars.getInputStream()));
        String linha;
        StringBuffer res = new StringBuffer();
        while ((linha = br.readLine()) != null) {
            res.append(linha);
        }
        br.close();
        JSONObject json = new JSONObject(res.toString());
        CarroDTOInput carroDTOInput = new CarroDTOInput();
        carroDTOInput.setModelo(json.getString("model"));
        carroDTOInput.setPlaca("KND9D21");
        carroDTOInput.setChassi("1111111111");
        return carroDTOInput;
    }
    static CarroDTOInput getCarroDTOInputObjectMapper(HttpURLConnection conexaoApiCars) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(conexaoApiCars.getInputStream()));
        String linha;
        StringBuffer res = new StringBuffer();
        while ((linha = br.readLine()) != null) {
            res.append(linha);
        }
        br.close();
        ObjectMapper object = new ObjectMapper();
        CarroDTOInput carroDTOInput = object.readValue(res.toString(), CarroDTOInput.class);
        carroDTOInput.setPlaca("KND9D77");
        carroDTOInput.setChassi("1111111111");
        return carroDTOInput;
    }
}
