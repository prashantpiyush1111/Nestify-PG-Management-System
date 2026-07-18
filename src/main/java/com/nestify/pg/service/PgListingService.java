package com.nestify.pg.service;

import com.nestify.pg.entity.PgListing;
import com.nestify.pg.repository.PgListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PgListingService {

    private final PgListingRepository repo;

    public PgListingService(PgListingRepository repo) {
        this.repo = repo;
    }

    public List<PgListing> getAll() {
        return repo.findAll();
    }

    public List<PgListing> getByCity(String city) {
        return repo.findByCity(city);
    }

    public Optional<PgListing> getByOwner(String username) {
        return repo.findByOwnerUsername(username);
    }

    public PgListing create(PgListing listing) {
        return repo.save(listing);
    }

    public PgListing update(Long id, PgListing listing) {
        listing.setId(id);
        return repo.save(listing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}