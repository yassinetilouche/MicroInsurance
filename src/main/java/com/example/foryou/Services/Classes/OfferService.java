package com.example.foryou.Services.Classes;
import com.example.foryou.DAO.Entities.Offer;
import com.example.foryou.DAO.Repositories.OfferRepository;
import com.example.foryou.Services.Interfaces.IOfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service

public class OfferService implements IOfferService {
    private OfferRepository offerRepository;
    @Override
    public Offer add(Offer o) {
        return offerRepository.save(o);
    }
    @Override

    public Offer edit(Offer o) {
        return offerRepository.save(o);
    }

    @Override
    public List<Offer> selectAll() {
        return offerRepository.findAll();
    }

    @Override
    public Offer selectById(int offerId) {
        return offerRepository.findById(offerId).get();
    }

    @Override
    public void deleteById(int offerId) {
        offerRepository.deleteById(offerId);
    }

    @Override
    public void delete(Offer o) {

        offerRepository.delete(o);
    }

    @Override
    public List<Offer> addAll(List<Offer> list) {
        return offerRepository.saveAll(list);
    }

    @Override
    public void deleteAll(List<Offer> list) {
        offerRepository.deleteAll(list);
    }

}
