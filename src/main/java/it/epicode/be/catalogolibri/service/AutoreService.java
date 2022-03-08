package it.epicode.be.catalogolibri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.catalogolibri.common.util.exception.LibreriaException;
import it.epicode.be.catalogolibri.model.Autore;
import it.epicode.be.catalogolibri.model.Categoria;
import it.epicode.be.catalogolibri.model.Libro;
import it.epicode.be.catalogolibri.repository.AutoreRepository;
import it.epicode.be.catalogolibri.repository.LibroRepository;

@Service
public class AutoreService {

	@Autowired
	AutoreRepository autoreRepository;

	@Autowired
	LibroRepository libroRepository;

	public Optional<Autore> findById(Long id) {
		Optional<Autore> autoreResult = autoreRepository.findById(id);
		if (autoreResult.isPresent()) {

			return autoreResult;
		} else {
			throw new LibreriaException("Autore con id: " + id + "non trovato.");
		}

	}

	public Page<Autore> findAll(Pageable pageable) {
		if (!autoreRepository.findAll(pageable).isEmpty()) {
			return autoreRepository.findAll(pageable);
		} else {
			throw new LibreriaException("Nessun autore presente in lista, inserirne almeno uno.");
		}

	}

	public Autore save(Autore autore) {
		return autoreRepository.save(autore);
	}

	public Autore update(Long id, Autore autore) {
		Optional<Autore> autoreResult = autoreRepository.findById(id);

		if (autoreResult.isPresent()) {
			Autore autoreUpdate = autoreResult.get();
			autoreUpdate.setNome(autore.getNome());
			autoreUpdate.setCognome(autore.getCognome());
			autoreRepository.save(autoreUpdate);
			return autoreUpdate;
		} else {
			throw new LibreriaException("Autore non aggiornato.");
		}

	}

	public void delete(Long id) {
		Optional<Autore> a = autoreRepository.findById(id);
		if (a.isPresent()) {
			Autore autoreDelete = a.get();
			List<Libro> listalibri = libroRepository.findByAutoreCognome(autoreDelete.getCognome());

			for (Libro libro : listalibri) {
				libro.getAutore().remove(autoreDelete);
				libroRepository.save(libro);

			}
			autoreRepository.deleteById(id);
		} else {
			throw new LibreriaException("Cancellazione con: " + id + " non riuscita");
		}

	}
}
