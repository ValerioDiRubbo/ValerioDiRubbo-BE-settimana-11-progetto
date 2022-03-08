package it.epicode.be.catalogolibri.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.be.catalogolibri.model.Autore;
import it.epicode.be.catalogolibri.model.Categoria;
import it.epicode.be.catalogolibri.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

	List<Libro> findByAutoreCognome(String cognome);
	List<Libro> findByCategoriaNome(String nomecategoria);
	

}
