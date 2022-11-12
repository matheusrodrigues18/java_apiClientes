package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.EmailComponent;
import br.com.cotiinformatica.dtos.ClienteGetDTO;
import br.com.cotiinformatica.dtos.ClientePostDTO;
import br.com.cotiinformatica.dtos.ClientePutDTO;
import br.com.cotiinformatica.dtos.ClienteResponseDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.repositories.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags= "Clientes")
@RestController
public class ClienteController {
	
	@Autowired
	EmailComponent emailComponent;

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para cadastro de clientes.")
	@PostMapping("/api/clientes")
	public ClienteResponseDTO post(@RequestBody ClientePostDTO dto) {

		ClienteResponseDTO response = new ClienteResponseDTO();

		try {
			
			ClienteRepository clienteRepo = new ClienteRepository();

			Cliente cliente = new Cliente();

			cliente.setNome(dto.getNome());
			cliente.setEmail(dto.getEmail());
			cliente.setCpf(dto.getCpf());

			clienteRepo.create(cliente);

			response.setMensagem("Cliente cadastrado com sucesso.");
			this.sendEmailWelcome(cliente);

		} catch (Exception e) {

			response.setMensagem("Erro: " + e.getMessage());
			e.printStackTrace();

		}

		return response;

	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para atualização de clientes.")
	@PutMapping("/api/clientes")
	public ClienteResponseDTO put(@RequestBody ClientePutDTO dto) {

		ClienteResponseDTO response = new ClienteResponseDTO();

		try {
			
			ClienteRepository clienteRepo = new ClienteRepository();

			Cliente cliente = new Cliente();

			cliente.setIdCliente(dto.getIdCliente());
			cliente.setNome(dto.getNome());
			cliente.setEmail(dto.getEmail());
			cliente.setCpf(dto.getCpf());

			clienteRepo.update(cliente);

			response.setMensagem("Cliente atualizado com sucesso.");

		} catch (Exception e) {

			response.setMensagem("Erro: " + e.getMessage());
			e.printStackTrace();

		}

		return response;

	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para exclusão de clientes.")
	@DeleteMapping("/api/clientes/{id}")
	public ClienteResponseDTO delete(@PathVariable("id") Long idCliente) {

		ClienteResponseDTO response = new ClienteResponseDTO();

		try {
			
			ClienteRepository clienteRepo = new ClienteRepository();

			clienteRepo.delete(idCliente);

			response.setMensagem("Cliente deletado com sucesso.");

		} catch (Exception e) {

			response.setMensagem("Erro: " + e.getMessage());
			e.printStackTrace();

		}

		return response;

	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para busca de todos clientes.")
	@GetMapping("/api/clientes")
	public List<ClienteGetDTO> getAll() {

		List<ClienteGetDTO> response = new ArrayList<>();
		
		try {
			
			ClienteRepository clienteRepo = new ClienteRepository();
			
			for(Cliente c : clienteRepo.getAll()) {
				
				ClienteGetDTO dto = new ClienteGetDTO();
				
				dto.setIdCliente(c.getIdCliente());
				dto.setNome(c.getNome());
				dto.setEmail(c.getEmail());
				dto.setCpf(c.getCpf());
				
				response.add(dto);
				
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return response;

	}

	@CrossOrigin("http://localhost:4200")
	@ApiOperation("Serviço para busca de um cliente pelo id.")
	@GetMapping("/api/clientes/{id}")
	public ClienteGetDTO getById(@PathVariable("id") Long idCliente) {

		ClienteGetDTO response = null;

		try {
			
			ClienteRepository clienteRepo = new ClienteRepository();
			
			Cliente cliente = clienteRepo.getById(idCliente);
			
			if(cliente!=null) {
			
				response = new ClienteGetDTO();
				
				response.setIdCliente(cliente.getIdCliente());
				response.setNome(cliente.getNome());
				response.setEmail(cliente.getEmail());
				response.setCpf(cliente.getCpf());
			
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return response;

	}
	
	private void sendEmailWelcome(Cliente cliente)throws Exception{
		
		String email = cliente.getEmail();
		
		String assunto = "Sua conta de cliente foi criada com sucesso! COTI Informática";
		String corpo = "Olá, "+cliente.getNome()+
				"\n\nSua conta de cliente foi cadastrada com sucesso em nosso sistema seguem os dados:\n"+
				"Email: "+cliente.getEmail()+
				"\nCPF: "+cliente.getCpf()+
				"\n\nAtt,"+
				"\nEquipe COTI Informática";
		
		emailComponent.mailSend(email, assunto, corpo);
		
	}

}
