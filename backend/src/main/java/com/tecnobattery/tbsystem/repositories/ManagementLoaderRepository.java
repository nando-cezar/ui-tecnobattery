package com.tecnobattery.tbsystem.repositories;

import com.tecnobattery.tbsystem.entities.ManagementLoader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ManagementLoaderRepository extends JpaRepository<ManagementLoader, Long> {

}
