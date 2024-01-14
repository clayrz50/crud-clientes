package com.example.crud.DTO;

import java.time.LocalDate;

import com.example.crud.entities.Client;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public class ClientDTO {
	private Long id;
	@NotBlank(message = "Campo Requerido")
	private String name;
	@NotBlank(message = "Campo Requerido")
	@Pattern(regexp = "[0-9]{11}", message = "CPF deve ter 11 dígitos numéricos")
	private String cpf;
	@NotNull(message = "Campo Requerido")
	@PositiveOrZero(message="O salário deve ser igual ou maior que R$0,00")
	private Double income;
	@NotNull(message = "Campo Requerido")
	@PastOrPresent(message = "Data de nascimento inválida")
	private LocalDate birthDate;
	@NotNull(message = "Campo Requerido")
	@Min(value = 0, message = "Número de filhos deve ser igual ou maior que 0")
	private Integer children;
	
	public ClientDTO(Long id, String name, String cpf, Double income, LocalDate birthDate, Integer children) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.children = children;
	}

	public ClientDTO(Client client) {
		this.id = client.getId();
		this.name = client.getName();
		this.cpf = client.getCpf();
		this.income = client.getIncome();
		this.birthDate = client.getBirthDate();
		this.children = client.getChildren();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public Double getIncome() {
		return income;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Integer getChildren() {
		return children;
	}
	
	
}
