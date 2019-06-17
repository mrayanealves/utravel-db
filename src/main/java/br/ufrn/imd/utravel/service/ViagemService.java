package br.ufrn.imd.utravel.service;

import br.ufrn.imd.utravel.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.utravel.model.Viagem;
import br.ufrn.imd.utravel.model.ViagemDestino;
import br.ufrn.imd.utravel.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ViagemService implements GenericService<Viagem> {
    private final ViagemRepository viagemRepository;

    @Autowired
    public ViagemService(ViagemRepository viagemRepository) {
        this.viagemRepository = viagemRepository;
    }

    @Override
    public List<Viagem> findAll() {
        return viagemRepository.findAll();
    }

    @Override
    public Optional<Viagem> findById(Integer id) {
        return viagemRepository.findById(id);
    }

    @Override
    public Viagem save(Viagem viagem) {
        return viagemRepository.save(viagem);
    }

    @Override
    public Viagem update(Integer id, Viagem viagem) {
        viagem.setId(id);
        return viagemRepository.update(viagem);
    }

    @Override
    public String delete(Integer id) {
        viagemRepository.delete(id);
        return "Sucesso";
    }

    public Viagem adicionarDestino(Integer id, ViagemDestino viagemDestino){
        Optional<Viagem> viagemFind = viagemRepository.findById(id);

        if (!viagemFind.isPresent()){
            throw new EntidadeNaoEncontradaException("Não foi possível encontrar uma viagem com este id.");
        }

        viagemDestino.setViagem(viagemFind.get());

        return viagemRepository.adicionarDestino(viagemDestino);
    }

    public Viagem removerDestino(ViagemDestino viagemDestino){
        return viagemRepository.removerDestino(viagemDestino.getId());
    }
}
