package com.boots.repository;

import com.boots.entity.MedicineCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineCatalogRepository extends JpaRepository<MedicineCatalog, String> {
}
