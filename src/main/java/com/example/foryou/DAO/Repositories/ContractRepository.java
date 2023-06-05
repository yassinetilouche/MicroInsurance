package com.example.foryou.DAO.Repositories;

import com.example.foryou.DAO.Entities.Contracts;
import com.example.foryou.DAO.Entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contracts,Integer> {
    // ********** Trouver contrat par date d'expiration
    List<Contracts> findByExprirationDate(Date expirationDate);
    //*********** Filtrage des contracts par type de sinister
    @Query(value = "select c from Contracts c , Sinister s where s.contract.contract_id = c.contract_id and s.typeSinister=?1")
    List<Contracts> selectContractByType(Type type);
    // ********** Affichage des contracts renouvelable dont la date d'expiration et la date d'aujourdhui
    @Query(value = "select c from Contracts c where c.exprirationDate=current_date and c.renewable=True")
    List<Contracts> selectRenewableContract();
    // ********** Supprimer les contracts non renouvlable dont la date d'expiration et la date d'aujourdhui
    @Transactional
    @Modifying
    @Query(value = "delete from contracts where expriration_date=current_date " +
            "and renewable=False",nativeQuery = true)
    void deleteNonRenewableContract ();
}
