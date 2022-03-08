package it.epicode.be.catalogolibri.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.catalogolibri.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	List<Categoria> findByNome(String nome);
	
	

}
