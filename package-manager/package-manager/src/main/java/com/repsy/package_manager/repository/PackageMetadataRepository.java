package com.repsy.package_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.repsy.package_manager.model.PackageMetadata;

public interface PackageMetadataRepository extends JpaRepository<PackageMetadata, Long> {
}