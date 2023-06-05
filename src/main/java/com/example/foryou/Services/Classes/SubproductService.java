package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.Subproduct;
import com.example.foryou.DAO.Repositories.SubproductRepository;
import com.example.foryou.Services.Interfaces.IsubproductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SubproductService implements IsubproductService {
    private SubproductRepository subproductRepository;
    @Override
    public Subproduct add(Subproduct a) {
        return subproductRepository.save(a);
    }

    @Override
    public Subproduct edit(Subproduct a) {
        return subproductRepository.save(a);
    }

    @Override
    public List<Subproduct> selectAll() {
        return subproductRepository.findAll();
    }

    @Override
    public Subproduct selectById(int id) {
        return subproductRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        subproductRepository.deleteById(id);
    }

    @Override
    public void delete(Subproduct a) {
    subproductRepository.delete(a);
    }

    @Override
    public List<Subproduct> addAll(List<Subproduct> list) {
        return subproductRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<Subproduct> list) {
        subproductRepository.deleteAll(list);
    }
    @Override
    public void deleteAll() {
        subproductRepository.deleteAll();
    }
}
