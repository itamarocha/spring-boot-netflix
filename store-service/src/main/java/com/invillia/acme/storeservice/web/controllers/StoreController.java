package com.invillia.acme.storeservice.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.storeservice.entities.Store;
import com.invillia.acme.storeservice.repositories.StoreRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/store")
@Slf4j
@Api(value = "Store", description = "Store ")
public class StoreController {
	/** Data repository storeRepository **/
    private final StoreRepository storeRepository;

    @Autowired
    public StoreController(StoreRepository inventoryItemRepository) {
        this.storeRepository = inventoryItemRepository;
    }
    
    @ApiOperation(value = "List all stores")
    @GetMapping
    @HystrixCommand
    public List<Store> get() {
        log.info("Finding all stores ");
        return storeRepository.findAll();
    }
    @ApiOperation(value = "Get details of Store by id")
    @GetMapping("/{id}")
    @HystrixCommand
    public ResponseEntity<Optional<Store>> find(
    		@ApiParam(value = "Store Id", required=true) 
    		@PathVariable(name="id",required=true) Long id) {
        log.info("Finding id :"+ id);
        Optional<Store> store = storeRepository.findById(id);
        if(store.isPresent()) {
            return ResponseEntity.ok(store);
        } else {
        	return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value = "Update a store")
    @PutMapping
    @HystrixCommand
    public ResponseEntity<Object> put(@RequestBody Store store) {
        log.info("Update  :"+ store.toString());
        Optional<Store> find = storeRepository.findById(store.getId());
        if (!find.isPresent())
    		return ResponseEntity.notFound().build();
        storeRepository.save(store);
        return ResponseEntity.noContent().build();
    }
}
