package br.com.cotiinformatica.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientePutDTO {

	private Long idCliente;
	private String nome;
	private String email;
	private String cpf;
	
}
