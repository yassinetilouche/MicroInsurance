package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.Sinister;
import com.example.foryou.DAO.Repositories.SinisterRepository;
import com.example.foryou.Services.Interfaces.ISinisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service


public class SinisterService implements ISinisterService {
    private SinisterRepository sinisterRepository;


    @Override
    public Sinister add(Sinister s) {
        return sinisterRepository.save(s);
    }

    @Override
    public Sinister edit(Sinister s) {
        return sinisterRepository.save(s);
    }

    @Override
    public List<Sinister> selectAll() {
        return sinisterRepository.findAll();
    }

    @Override
    public Sinister selectById(int sinisterId) {
        return sinisterRepository.findById(sinisterId).get();
    }

    @Override
    public void deleteById(int sinisterId) {
        sinisterRepository.deleteById(sinisterId);
    }

    @Override
    public void delete(Sinister s) {
        sinisterRepository.delete(s);
    }

    @Override
    public List<Sinister> addAll(List<Sinister> list) {
        return sinisterRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<Sinister> list) { sinisterRepository.deleteAll(list);
    }
}
