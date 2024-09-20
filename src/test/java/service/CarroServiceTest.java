package service;

import dto.carro.CarroDTOInput;
import dto.carro.CarroDTOOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Collection;

public class CarroServiceTest {
    CarroService carroService = new CarroService();
    @Test
    void validarInsercaoDeCarro() {
        CarroDTOInput carroDTOInput = new CarroDTOInput();
        carroDTOInput.setChassi("1111111");
        carroDTOInput.setModelo("Tesla");
        carroDTOInput.setPlaca("34566");
        carroService.adicionarCarro(carroDTOInput);
        Collection<CarroDTOOutput> carros = carroService.listarCarros();
        Assertions.assertEquals(1, carros.size());
    }
}
