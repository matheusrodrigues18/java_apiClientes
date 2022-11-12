package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ClienteRepository {

	public void create(Cliente cliente) throws Exception {

		Connection connection = ConnectionFactory.createConnection();

		PreparedStatement statement = connection
				.prepareStatement("insert into clientes(nome, cpf, email) values(?,?,?)");
		statement.setString(1, cliente.getNome());
		statement.setString(2, cliente.getCpf());
		statement.setString(3, cliente.getEmail());
		statement.execute();

		connection.close();
	}

	public void update(Cliente cliente) throws Exception {

		Connection connection = ConnectionFactory.createConnection();

		PreparedStatement statement = connection
				.prepareStatement("update clientes set nome=?, cpf=?, email=? where idcliente=?");
		statement.setString(1, cliente.getNome());
		statement.setString(2, cliente.getCpf());
		statement.setString(3, cliente.getEmail());
		statement.setLong(4, cliente.getIdCliente());
		statement.execute();

		connection.close();
	}

	public void delete(Long idCliente) throws Exception {

		Connection connection = ConnectionFactory.createConnection();

		PreparedStatement statement = connection.prepareStatement("delete from clientes where idcliente=?");
		statement.setLong(1, idCliente);
		statement.execute();

		connection.close();

	}

	public List<Cliente> getAll() throws Exception {

		Connection connection = ConnectionFactory.createConnection();

		PreparedStatement statement = connection.prepareStatement("select * from clientes");

		ResultSet rs = statement.executeQuery();

		List<Cliente> lista = new ArrayList<>();

		while (rs.next()) {

			Cliente cliente = new Cliente();

			cliente.setIdCliente(rs.getLong("idCliente"));
			cliente.setNome(rs.getString("nome"));
			cliente.setCpf(rs.getString("cpf"));
			cliente.setEmail(rs.getString("email"));

			lista.add(cliente);

		}

		connection.close();

		return lista;

	}

	public Cliente getById(Long idCliente) throws Exception {

		Connection connection = ConnectionFactory.createConnection();

		PreparedStatement statement = connection.prepareStatement("select * from clientes where idCliente = ?");
		statement.setLong(1, idCliente);
		ResultSet rs = statement.executeQuery();

		Cliente cliente = null;

		if (rs.next()) {

			cliente = new Cliente();
			
			cliente.setIdCliente(rs.getLong("idCliente"));
			cliente.setNome(rs.getString("nome"));
			cliente.setCpf(rs.getString("cpf"));
			cliente.setEmail(rs.getString("email"));

		}

		connection.close();

		return cliente;

	}

}
