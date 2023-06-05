package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Offer;

import java.util.List;

public interface IOfferService {


    Offer add(Offer o);

    Offer edit(Offer o);

    List<Offer> selectAll();

    Offer selectById(int OfferId);

    void deleteById(int OfferId);

    void delete(Offer o);

    List<Offer> addAll(List<Offer> list);

    void deleteAll(List<Offer> list);
}

