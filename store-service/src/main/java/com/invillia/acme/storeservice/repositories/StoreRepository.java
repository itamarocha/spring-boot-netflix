package com.invillia.acme.storeservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invillia.acme.storeservice.entities.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {


}
