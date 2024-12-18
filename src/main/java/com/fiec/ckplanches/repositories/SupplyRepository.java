package com.fiec.ckplanches.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.fiec.ckplanches.model.supply.Supply;
import com.fiec.ckplanches.model.enums.Status;


@Repository
public interface SupplyRepository extends JpaRepository<Supply, Integer>{

    Supply findByName(String name);
    List<Supply> findByStatus(Status status);

}
