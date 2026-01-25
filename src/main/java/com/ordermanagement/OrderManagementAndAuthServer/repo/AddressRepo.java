package com.ordermanagement.OrderManagementAndAuthServer.repo;

import com.ordermanagement.OrderManagementAndAuthServer.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {

    List<Address> findByUserId(Long userId);
    Page<Address> findAll(Pageable pageable);

}
