package com.tamercapital.tamercapital.repository;

import com.tamercapital.tamercapital.model.concretes.ERole;
import com.tamercapital.tamercapital.model.concretes.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(ERole name);
}
