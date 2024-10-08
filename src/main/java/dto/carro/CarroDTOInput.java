package dto.carro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarroDTOInput {
    private Integer id;
    @JsonProperty("model")
    private String modelo;
    private String placa;
    private String chassi;
}
