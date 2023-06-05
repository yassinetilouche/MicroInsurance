package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Response;

import java.util.List;

public interface IResponseService {
    Response addResp(Response rec);

    Response editResp(Response resp);

    List<Response> selectAllResp();

    Response selectRespById(int responseId);

    void deleteRespById(int ResponseId);

    void deleteResp(Response rec);

    List<Response> addAll(List<Response> listrec);

    void deleteRespAll(List<Response> listresp);

    void deleteRespAll();
}
