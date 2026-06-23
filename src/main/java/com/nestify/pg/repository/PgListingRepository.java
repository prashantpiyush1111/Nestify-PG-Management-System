package com.nestify.pg.repository;

import com.nestify.pg.entity.PgListing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PgListingRepository extends JpaRepository<PgListing, Long> {
    Optional<PgListing> findByOwnerUsername(String ownerUsername);
    List<PgListing> findByCity(String city);
}