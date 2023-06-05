package com.example.foryou.Services.Classes;

import com.example.foryou.DAO.Entities.Product;
import com.example.foryou.DAO.Repositories.ProductRepository;
import com.example.foryou.Services.Interfaces.IproductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class ProductService implements IproductService {


    private ProductRepository productRepository;
    @Override
    public Product add(Product a) {
        return productRepository.save(a);
    }

    @Override
    public Product edit(Product a) {
        return productRepository.save(a);
    }

    @Override
    public List<Product> selectAll() {
        return productRepository.findAll();
    }

    @Override
    public Product selectById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public void delete(Product a) {
        productRepository.delete(a);
    }

    @Override
    public List<Product> addAll(List<Product> list) {
        return productRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<Product> list) {
        productRepository.deleteAll(list);
    }
    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }



}
