package br.com.cotiinformatica.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClienteGetDTO {
	
	private Long idCliente;
	private String nome;
	private String cpf;
	private String email;

}
