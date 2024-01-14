package com.example.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.crud.DTO.ClientDTO;
import com.example.crud.entities.Client;
import com.example.crud.exceptions.DatabaseException;
import com.example.crud.exceptions.DuplicateCpfException;
import com.example.crud.exceptions.ResourceNotFoundException;
import com.example.crud.repositories.ClientRepository;

@Service
public class ClientService {
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Client client = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
		return new ClientDTO(client);
	}

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(Pageable pageable) {
		Page<Client> result = repository.findAll(pageable);
		return result.map(x -> new ClientDTO(x));
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		validateUniqueCpf(dto.getCpf());
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		Client entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
		String newCpf = dto.getCpf();
        if (!entity.getCpf().equals(newCpf)) {
            if (repository.existsByCpf(newCpf)) {
                throw new DuplicateCpfException("CPF já cadastrado para outro cliente");
            }
        }
		copyDtoToEntity(dto, entity);
		entity=repository.save(entity);
		return new ClientDTO(entity);
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}


	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setIncome(dto.getIncome());
		entity.setChildren(dto.getChildren());
	}
	
	private void validateUniqueCpf(String cpf) {
        if (repository.existsByCpf(cpf)) {
            throw new DuplicateCpfException("CPF já cadastrado");
        }
    }
	
}
