package br.com.springboot.projeto_spring_boot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.projeto_spring_boot.model.Usuario;
import br.com.springboot.projeto_spring_boot.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired // IC/CD OU CDI
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Hello " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario);

		return "Olá Mundo " + nome;

	}

	@GetMapping(value = "listatodos")
	@ResponseBody // retorna os dados para o corpo da resposta
	public ResponseEntity<List<Usuario>> listaUsuario() { // Primeiro método de API, buscar todos
		List<Usuario> usuarios = usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // retorna um JSON
	}

	@PostMapping(value = "salvar") //value->url, mapeia a url
	@ResponseBody //descrição da resposta
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { //@RequestBody recebe os dados para salvar
		Usuario user=usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "delete") //value->url, mapeia a url
	@ResponseBody //descrição da resposta
	public ResponseEntity<String> delete(@RequestParam Long idUser) { //@RequestBody recebe os dados para salvar
		//requestparam pega o param da url
		usuarioRepository.deleteById(idUser);
		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping(value = "buscaruserid") //value->url, mapeia a url
	@ResponseBody //descrição da resposta
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "idUser") Long idUser) { //@RequestBody recebe os dados para salvar
		Usuario user=usuarioRepository.findById(idUser).get();
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@PutMapping(value = "atualizar") //value->url, mapeia a url
	@ResponseBody //descrição da resposta
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { //@RequestBody recebe os dados para salvar
		if(usuario.getId()==null) {
			return new ResponseEntity<String>("ID não foi informado", HttpStatus.OK);
		}
		Usuario user=usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@GetMapping(value = "buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {
		List<Usuario> usuarios=usuarioRepository.buscarPorNome(name.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
}
