package com.example.foryou.Services.Interfaces;

import com.example.foryou.DAO.Entities.Contracts;
import com.example.foryou.DAO.Entities.Type;
import com.example.foryou.DAO.Entities.User;
import com.itextpdf.text.DocumentException;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IContractService {
    Contracts addContract (Contracts contract);
    Contracts editContract (Contracts contract);
    List<Contracts> selectAllContracts();
    Contracts SelectContractById(int contractId);
    void deleteContractById(int ContractId);
    void deleteContract (Contracts contract);
    List<Contracts> addAllContracts(List<Contracts> contractList);
    void deleteAllContracts (List<Contracts> contractList);
    void deleteAllContracts();
    // ************** Filtrage des contracts par type de sinister
    List<Contracts> FilterContract(Type type);
    // ************** Affichage des contracts renouvelable dont la date d'expiration est aujourdhui
    List<Contracts> selectRenewableContract();
    // ********** Supprimer les contracts non renouvlable dont la date d'expiration est aujourdhui
    void deleteNonRenewableContract ();
     void verifierContrats() throws MessagingException , javax.mail.MessagingException ;
    public File genererContratPDF(Contracts contract) throws IOException, DocumentException ;
    public void envoyerContratParEmail(User user, Contracts  contract) throws javax.mail.MessagingException ;

    String getCurrentUserName();

    User getUser(String email);
}
