package com.nestify.pg.controller;

import com.nestify.pg.entity.PgListing;
import com.nestify.pg.repository.PgListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pg-listings")
public class PgListingController {

    @Autowired
    private PgListingRepository repo;

    // Sabhi PGs dekho (tenant ke liye)
    @GetMapping
    public List<PgListing> getAll() {
        return repo.findAll();
    }

    // City se filter karo
    @GetMapping("/city/{city}")
    public List<PgListing> getByCity(@PathVariable String city) {
        return repo.findByCity(city);
    }

    // Admin ka apna PG dekho
    @GetMapping("/owner/{username}")
    public ResponseEntity<?> getByOwner(@PathVariable String username) {
        return repo.findByOwnerUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Admin apna PG register/update kare
    @PostMapping
    public PgListing create(@RequestBody PgListing listing) {
        return repo.save(listing);
    }

    @PutMapping("/{id}")
    public PgListing update(@PathVariable Long id, @RequestBody PgListing listing) {
        listing.setId(id);
        return repo.save(listing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}