package it.epicode.be.catalogolibri.common.runner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import it.epicode.be.catalogolibri.model.Autore;
import it.epicode.be.catalogolibri.model.Categoria;
import it.epicode.be.catalogolibri.model.Libro;
import it.epicode.be.catalogolibri.model.security.Role;
import it.epicode.be.catalogolibri.model.security.Roles;
import it.epicode.be.catalogolibri.model.security.User;
import it.epicode.be.catalogolibri.repository.AutoreRepository;
import it.epicode.be.catalogolibri.repository.CategoriaRepository;
import it.epicode.be.catalogolibri.repository.LibroRepository;
import it.epicode.be.catalogolibri.repository.security.RoleRepository;
import it.epicode.be.catalogolibri.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe utility per l'inizializzazione del database all'avvio
 * dell'applicazione
 *
 */
@Component
@Slf4j
public class ApplicationStartupRunner implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutoreRepository autoreRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

		Role roleA = new Role();
		roleA.setRoleName(Roles.ROLE_ADMIN);
		roleRepository.save(roleA);

		Role roleU = new Role();
		roleU.setRoleName(Roles.ROLE_USER);
		roleRepository.save(roleU);

		// ***** ADMIN *****

		User userA = new User();
		userA.setUserName("admin");
		userA.setPassword(bCrypt.encode("admin"));
		userA.setEmail("admin@email.it");
		userA.setActive(true);
		Set<Role> rolesA = new HashSet<>();
		rolesA.add(roleA);
		rolesA.add(roleU);
		userA.setRoles(rolesA);
		userRepository.save(userA);

		// ****** USER ******
		User userU = new User();
		userU.setUserName("user");
		userU.setPassword(bCrypt.encode("user"));
		userU.setEmail("user@email.it");
		userU.setActive(true);
		Set<Role> rolesU = new HashSet<>();
		rolesU.add(roleU);
		userU.setRoles(rolesU);
		userRepository.save(userU);

		
		
		
		
		List<Autore> autori = new ArrayList<>();
		Autore autore = new Autore();
		autore.setNome("Feodor");
		autore.setCognome("Dostoevskij");
		autoreRepository.save(autore);
		autori.add(autore);
		log.info("Saved Autore: {}", autore.getNome());

		List<Categoria> categorie = new ArrayList<>();
		Categoria categoria = new Categoria();
		categoria.setNome("Fantasy");
		categoriaRepository.save(categoria);
		categorie.add(categoria);

		Libro libro = new Libro();
		libro.setTitolo("Picatrix");
		libro.setAnnoPubblicazione("2001");
		libro.setPrezzo(16.80);
		libro.setAutore(autori);
		libro.setCategoria(categorie);
		libroRepository.save(libro);

	}

	List<Autore> loaderAutore() {

		List<Autore> autori = new ArrayList<>();
		Autore autore = new Autore();
		autore.setNome("Feodor");
		autore.setCognome("Dostoevskij");
		autoreRepository.save(autore);
		autori.add(autore);
		log.info("Saved Autore: {}", autore.getNome());

		autore = new Autore();
		autore.setNome("Valerio");
		autore.setCognome("Evangelisti");
		autoreRepository.save(autore);
		autori.add(autore);
		log.info("Saved Autore: {}", autore.getNome());

		return autori;

	}

	List<Categoria> loaderCategoria() {

		List<Categoria> categorie = new ArrayList<>();
		Categoria categoria = new Categoria();
		categoria.setNome("Fantasy");
		categoriaRepository.save(categoria);
		categorie.add(categoria);

		categoria = new Categoria();
		categoria.setNome("Romanzo");
		categoriaRepository.save(categoria);
		categorie.add(categoria);

		return categorie;
	}

	Libro loaderLibro1() {

		Libro libro = new Libro();
		libro.setTitolo("Picatrix");
		libro.setAnnoPubblicazione("2001");
		libro.setPrezzo(16.80);
		libro.setAutore(loaderAutore());
		libro.setCategoria(loaderCategoria());
		libroRepository.save(libro);

		return libro;

	}

//	Libro loaderLibro2(List<Autore> autori, List<Categoria> categorie) {
//
//		Libro libro = new Libro();
//		libro.setTitolo("Delitto e Castigo");
//		libro.setAnnoPubblicazione("1993");
//		libro.setPrezzo(12.80);
//		libro.setAutore(autori);
//		libro.setCategoria(categorie);
//		libroRepository.save(libro);
//
//		return libro;
//
//	}
}
