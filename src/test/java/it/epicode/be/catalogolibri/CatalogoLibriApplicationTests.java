package it.epicode.be.catalogolibri;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.epicode.be.catalogolibri.model.Categoria;





@SpringBootTest
@AutoConfigureMockMvc
class CatalogoLibriApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	/**
	 * End point di login senza autenticazione.
	 * 
	 */
	
	@Test
	@WithAnonymousUser
	public void loginNoBody() throws Exception {
		this.mockMvc.perform(post("/auth/login")).andExpect(status().isBadRequest());
	}

	/**
	 * End point di visualizzazione tutti i libri senza autenticazione.
	 * 
	 */
	@Test
	@WithAnonymousUser
	public void getAllLibri() throws Exception {
		this.mockMvc.perform(get("/api/libro")).andExpect(status().isUnauthorized());
	}

	/**
	 * End point di visualizzazione tutti i libri con autenticazione USER.
	 * 
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void listaLibriWhenUtenteMockIsAuthenticated() throws Exception {
		this.mockMvc.perform(get("/api/libro")).andExpect(status().isOk());
	}
	
	
	/**
	 * End point di creazione nuova categoria testato senza autenticazione.
	 * 
	 */
	@Test
	@WithAnonymousUser
	public void addNewCategoriaNotAuthenticated() throws Exception {
		
		Categoria categoria = new Categoria();
		categoria.setId(1l);
		categoria.setNome("Fantasy");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(categoria);

		MvcResult result = mockMvc.perform(post("/api/categoria").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isUnauthorized()).andReturn();

	}

	/**
	 * End point di creazione nuova categoria testato con autenticazione ADMIN.
	 * 
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void addNewCategoria() throws Exception {
		
		Categoria categoria = new Categoria();
		categoria.setId(1l);
		categoria.setNome("Fantasy");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(categoria);

		MvcResult result = mockMvc.perform(post("/api/categoria").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(content().json("{'nome':'Fantasy'}"))
				.andReturn();

	}
}
