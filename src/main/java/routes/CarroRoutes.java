package routes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.carro.CarroDTOInput;
import service.CarroService;
import spark.Request;
import spark.Response;
import util.CarroException;

public class CarroRoutes {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CarroService carroService = new CarroService();
    public String index(Request request, Response response) {
        response.type("application/json");
        try {
            response.status(200);
            return objectMapper.writeValueAsString(carroService.listarCarros());
        } catch (JsonProcessingException e) {
            response.status(500);
            return "\"Não foi possível listar carros\"";
        }
    }
    public String show(Request request, Response response) {
        response.type("application/json");
        String idParam = request.params("id");
        int id = Integer.parseInt(idParam);
        try {
            response.status(200);
            return objectMapper.writeValueAsString(carroService.buscarCarroPorId(id));
        } catch (CarroException e) {
            response.status(404);
            return "\"" + e.getMessage() + "\"";
        } catch (JsonProcessingException e) {
            response.status(500);
            return "\"Não foi possível encontrar o carro\"";
        }
    }
    public String create(Request request, Response response) {
        response.type("application/json");
        try {
            CarroDTOInput carroDTOInput = objectMapper.readValue(request.body(), CarroDTOInput.class);
            carroService.adicionarCarro(carroDTOInput);
            response.status(201);
            return "\"Carro criado com sucesso\"";
        } catch (JsonProcessingException e) {
            response.status(500);
            return "\"Não foi possível adicionar o carro\"";
        }
    }
    public String delete(Request request, Response response) {
        response.type("application/json");
        String idParam = request.params("id");
        int id = Integer.parseInt(idParam);
        try {
            carroService.removerCarro(id);
            response.status(204);
            return "\"Carro removido com sucesso\"";
        } catch (CarroException e) {
            response.status(404);
            return "\"" + e.getMessage() + "\"";
        }
    }
    public String update(Request request, Response response) {
        response.type("application/json");
        try {
            CarroDTOInput carroDTOInput = objectMapper.readValue(request.body(), CarroDTOInput.class);
            carroService.alterarCarro(carroDTOInput);
            response.status(200);
            return "\"Carro alterado com sucesso\"";
        } catch (CarroException e) {
            response.status(404);
            return "\"" + e.getMessage() + "\"";
        } catch (JsonProcessingException e) {
            response.status(500);
            return "\"Não foi possivel alterar o carro\"";
        }
    }
}