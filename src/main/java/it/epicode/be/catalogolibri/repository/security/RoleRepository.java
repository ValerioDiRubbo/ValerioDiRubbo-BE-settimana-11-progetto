package it.epicode.be.catalogolibri.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.catalogolibri.model.security.Role;
import it.epicode.be.catalogolibri.model.security.Roles;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(Roles role);
}
