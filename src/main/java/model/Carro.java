package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Carro {
    @Id
    @GeneratedValue
    private int id;
    private String modelo;
    private String placa;
    private String chassi;
}
