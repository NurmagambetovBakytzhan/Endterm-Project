package kz.kbtu.sf.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import kz.kbtu.sf.orderservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
