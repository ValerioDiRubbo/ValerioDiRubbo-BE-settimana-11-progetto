package it.epicode.be.catalogolibri.controller;

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
import it.epicode.be.catalogolibri.model.Categoria;
import it.epicode.be.catalogolibri.service.CategoriaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping(path = "/categoria")
	@Operation(description = "Stampa a video tutte le categorie.")
	public ResponseEntity<Page<Categoria>> findAll(Pageable pageable) {
		Page<Categoria> findAll = categoriaService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/categoria/{id}")
	@Operation(description = "Stampa a video la categoria con l'ID inserito.")
	public ResponseEntity<Categoria> findById(@PathVariable(required = true) Long id) {
		Optional<Categoria> find = categoriaService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/categoria")
	@Operation(description = "Crea una nuova categoria.(Disponibile solo per ruolo Admin.)")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Categoria> save(@RequestBody Categoria categoria) {
		Categoria save = categoriaService.save(categoria);
		return new ResponseEntity<>(save, HttpStatus.CREATED);

	}

	@PutMapping(path = "/categoria/{id}")
	@Operation(description = "Modifica la categoria con ID immesso.(Disponibile solo per ruolo Admin.)")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria) {
		Categoria save = categoriaService.update(id, categoria);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/categoria/{id}")
	@Operation(description = "Cancella la categoria con ID immesso. [Non cancella a cascata Libro e Autore.](Disponibile solo per ruolo Admin.)")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		categoriaService.delete(id);
		return new ResponseEntity<>("Categoria cancellata.", HttpStatus.OK);

	}

}
