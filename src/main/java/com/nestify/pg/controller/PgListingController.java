package com.nestify.pg.controller;

import com.nestify.pg.entity.PgListing;
import com.nestify.pg.service.PgListingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pg-listings")
public class PgListingController {

    private final PgListingService pgListingService;

    public PgListingController(PgListingService pgListingService) {
        this.pgListingService = pgListingService;
    }

    @GetMapping
    public List<PgListing> getAll() {
        return pgListingService.getAll();
    }

    @GetMapping("/city/{city}")
    public List<PgListing> getByCity(@PathVariable String city) {
        return pgListingService.getByCity(city);
    }

    @GetMapping("/owner/{username}")
    public ResponseEntity<?> getByOwner(@PathVariable String username) {
        return pgListingService.getByOwner(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PgListing create(@Valid @RequestBody PgListing listing) {
        return pgListingService.create(listing);
    }

    @PutMapping("/{id}")
    public PgListing update(@PathVariable Long id, @Valid @RequestBody PgListing listing) {
        return pgListingService.update(id, listing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        pgListingService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Listing deleted"));
    }
}