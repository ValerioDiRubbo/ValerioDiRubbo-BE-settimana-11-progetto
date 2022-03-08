package it.epicode.be.catalogolibri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.catalogolibri.common.util.exception.LibreriaException;
import it.epicode.be.catalogolibri.model.Libro;
import it.epicode.be.catalogolibri.repository.LibroRepository;

@Service
public class LibroService {

	@Autowired
	LibroRepository libroRepository;

	public Optional<Libro> findById(Long id) {
		Optional<Libro> libroResult = libroRepository.findById(id);

		if (libroResult.isPresent()) {
			
			return libroResult;
		} else {
			throw new LibreriaException("Libro non trovato con id: " + id + " , inserire un ID di un libro presente.");
		}
	}

	public List<Libro> findByAutoreCognome(String cognome) {
		List<Libro> libroResult = libroRepository.findByAutoreCognome(cognome);

		if (!libroResult.isEmpty()) {
			
			return libroResult;
		} else {
			throw new LibreriaException("Non ci sono libri scritti dall'autore con cognome : "  +  cognome + " presenti in database.");
		}
	}
	
	public List<Libro> findByCategoriaNome(String nomecategoria) {
		List<Libro> libroResult = libroRepository.findByCategoriaNome(nomecategoria);

		if (!libroResult.isEmpty()) {
			
			return libroResult;
		} else {
			throw new LibreriaException("Non ci sono libri nella categoria : "  +  nomecategoria + " presenti in database.");
		}
	}
	
	public Page<Libro> findAll(Pageable pageable) {
		if (!libroRepository.findAll(pageable).isEmpty()) {
			return libroRepository.findAll(pageable);
		} else {
			throw new LibreriaException("Nessuna categoria presente in lista, inserirne almeno una.");
		}
		
	}

	public Libro save(Libro libro) {
		return libroRepository.save(libro);
	}

	public Libro update(Long id, Libro libro) {
		Optional<Libro> libroResult = libroRepository.findById(id);

		if (libroResult.isPresent()) {
			Libro libroUpdate = libroResult.get();
			libroUpdate.setTitolo(libro.getTitolo());
			libroUpdate.setAnnoPubblicazione(libro.getAnnoPubblicazione());
			libroUpdate.setPrezzo(libro.getPrezzo());
			libroUpdate.setCategoria(libro.getCategoria());
			libroUpdate.setAutore(libro.getAutore());
			libroRepository.save(libroUpdate);
			return libroUpdate;
		} else {
			throw new LibreriaException("Libro non aggiornato.");
		}

	}

	public void delete(Long id) {
		Optional<Libro> libroResult = libroRepository.findById(id);

		if (libroResult.isPresent()) {
			
			libroRepository.deleteById(id);
		} else {
			throw new LibreriaException("Libro non trovato con id: " + id + " , inserire un ID di un libro presente.");
		}
		
	}

}
