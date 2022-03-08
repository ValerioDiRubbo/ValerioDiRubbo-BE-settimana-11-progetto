package it.epicode.be.catalogolibri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.catalogolibri.common.util.exception.LibreriaException;
import it.epicode.be.catalogolibri.model.Categoria;
import it.epicode.be.catalogolibri.model.Libro;
import it.epicode.be.catalogolibri.repository.CategoriaRepository;
import it.epicode.be.catalogolibri.repository.LibroRepository;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	LibroRepository libroRepository;

	public Optional<Categoria> findById(Long id) {
		Optional<Categoria> categoriaResult = categoriaRepository.findById(id);

		if (categoriaResult.isPresent()) {

			return categoriaResult;
		} else {
			throw new LibreriaException("Categoria con id : " + id + " non trovata.");
		}
	}

	public Page<Categoria> findAll(Pageable pageable) {
		if (!categoriaRepository.findAll(pageable).isEmpty()) {
			return categoriaRepository.findAll(pageable);
		} else {
			throw new LibreriaException("Nessuna categoria presente in lista, inserirne almeno una.");
		}
		
	}

	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public Categoria update(Long id, Categoria categoria) {
		Optional<Categoria> categoriaResult = categoriaRepository.findById(id);

		if (categoriaResult.isPresent()) {
			Categoria categoriaUpdate = categoriaResult.get();
			categoriaUpdate.setNome(categoria.getNome());
			categoriaRepository.save(categoriaUpdate);
			return categoriaUpdate;
		} else {
			throw new LibreriaException("Categoria non aggiornata.");
		}

	}

	public void delete(Long id) {
		Optional<Categoria> c = categoriaRepository.findById(id);
		if (c.isPresent()) {
			Categoria categoriaDelete = c.get();
			List<Libro> listalibri = libroRepository.findByCategoriaNome(categoriaDelete.getNome());
			for (Libro libro : listalibri) {
				libro.getCategoria().remove(categoriaDelete);
				libroRepository.save(libro);
			}
			categoriaRepository.deleteById(id);
		} else {
			throw new LibreriaException("Cancellazione con: " + id + " non riuscita");
		}
		
		

	}

}
