package it.epicode.be.catalogolibri.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private @NonNull Long id;
	private @NonNull String nome;

}
