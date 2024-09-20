package service;

import dao.GenericDAO;
import dto.carro.CarroDTOInput;
import dto.carro.CarroDTOOutput;
import model.Carro;
import org.modelmapper.ModelMapper;
import util.CarroException;
import static util.CarroException.*;
import java.util.ArrayList;
import java.util.Collection;

public class CarroService {
    private final GenericDAO<Carro> carroDAO = new GenericDAO<>(Carro.class);
    private final ModelMapper modelMapper = new ModelMapper();
    public Collection<CarroDTOOutput> listarCarros() {
        carroDAO.begin();
        Iterable<Carro> carros = carroDAO.findAll();
        carroDAO.end();
        Collection<CarroDTOOutput> carroDTOOutputs = new ArrayList<>();
        carros.forEach(carro -> carroDTOOutputs.add(modelMapper.map(carro, CarroDTOOutput.class)));
        return carroDTOOutputs;
    }
    public CarroDTOOutput buscarCarroPorId(int id) throws CarroException {
        carroDAO.begin();
        Carro carro = carroDAO.findById(id);
        carroDAO.end();
        if (carro == null) throw notFound();
        return modelMapper.map(carro, CarroDTOOutput.class);
    }
    public void adicionarCarro(CarroDTOInput carroDTOInput) {
        carroDTOInput.setId(null);
        Carro carro = modelMapper.map(carroDTOInput, Carro.class);
        carroDAO.begin();
        carroDAO.create(carro);
        carroDAO.end();
    }
    public void alterarCarro(CarroDTOInput carroDTOInput) throws CarroException {
        Carro carro = modelMapper.map(carroDTOInput, Carro.class);
        carroDAO.begin();
        if (carroDAO.findById(carro.getId()) == null) {
            carroDAO.end();
            throw notFound();
        }
        carroDAO.update(carro);
        carroDAO.end();
    }
    public void removerCarro(int id) throws CarroException {
        carroDAO.begin();
        Carro carro = carroDAO.findById(id);
        if (carro == null) {
            carroDAO.end();
            throw notFound();
        }
        carroDAO.delete(id);
        carroDAO.end();
    }
}
