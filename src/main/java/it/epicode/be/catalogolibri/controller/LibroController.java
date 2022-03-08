package it.epicode.be.catalogolibri.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.be.catalogolibri.model.Autore;
import it.epicode.be.catalogolibri.model.Categoria;
import it.epicode.be.catalogolibri.model.Libro;
import it.epicode.be.catalogolibri.service.LibroService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class LibroController {

	@Autowired
	private LibroService libroService;

	@GetMapping(path = "/libro")
	@Operation(description = "Stampa a video tutti i libri.")
	public ResponseEntity<Page<Libro>> findAll(Pageable pageable) {
		Page<Libro> findAll = libroService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/libro/{id}")
	@Operation(description = "Stampa a video il libro con l'ID immesso.")
	public ResponseEntity<Libro> findById(@PathVariable(required = true) Long id) {
		Optional<Libro> find = libroService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping(path = "/libro/autore/{cognome}")
	@Operation(description = "Stampa a video i libri scritti dall'Autore con il Cognome inserito.")
	public ResponseEntity<List<Libro>> findByAutoreCognome(@PathVariable(required = true) String cognome) {
		List<Libro> findAut = libroService.findByAutoreCognome(cognome);

		if (findAut != null) {
			return new ResponseEntity<>(findAut, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping(path = "/libro/categoria/{nomecategoria}")
	@Operation(description = "Stampa a video i libri per nome di Categoria ricercata.")
	public ResponseEntity<List<Libro>> findByCategoriaNome(@PathVariable(required = true) String nomecategoria) {
		List<Libro> findCategoria = libroService.findByCategoriaNome(nomecategoria);

		if (findCategoria != null) {
			return new ResponseEntity<>(findCategoria, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/libro")
	@Operation(description = "Inserisce un nuovo libro. Necessita di un Autore e una Categoria già inseriti nel sistema. (Disponibile solo per ruolo Admin.)")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Libro> save(@RequestBody Libro libro) {
		Libro save = libroService.save(libro);
		return new ResponseEntity<>(save, HttpStatus.CREATED);

	}

	@PutMapping(path = "/libro/{id}")
	@Operation(description = "Modifica il libro di cui si passa l'ID. (Disponibile solo per ruolo Admin.)")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody Libro libro) {
		Libro save = libroService.update(id, libro);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/libro/{id}")
	@Operation(description = "Cancella un libro presente nella libreria.(Disponibile solo per ruolo Admin.)")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		libroService.delete(id);
		return new ResponseEntity<>("Libro cancellato.", HttpStatus.OK);

	}

}
