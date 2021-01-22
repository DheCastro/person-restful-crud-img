package br.com.dhecastro.personrestfulcrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dhecastro.personrestfulcrud.adapter.DozerAdapter;
import br.com.dhecastro.personrestfulcrud.exceptions.ResourceNotFoundException;
import br.com.dhecastro.personrestfulcrud.model.Person;
import br.com.dhecastro.personrestfulcrud.repository.PersonRepository;
import br.com.dhecastro.personrestfulcrud.vo.v1.PersonVO;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;
	
	public PersonVO create(PersonVO person) {
		Person entity = DozerAdapter.parseObject(person, Person.class);
		PersonVO vo = DozerAdapter.parseObject(personRepository.save(entity), PersonVO.class);
		return vo;
	}
	
	public List<PersonVO> findAll() {
		return DozerAdapter.parseListObjects(personRepository.findAll(), PersonVO.class);
	}
	
	public PersonVO findById(Long id) {
		Person entity =  personRepository
						.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Resource not found in the database with this ID"));
		
		return DozerAdapter.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO update(PersonVO person) {
		Person entity = personRepository
						.findById(person.getId())
						.orElseThrow(() -> new ResourceNotFoundException("Resource not found in the database with this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		PersonVO vo = DozerAdapter.parseObject(personRepository.save(entity), PersonVO.class);
		
		return vo;
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		personRepository.disablePerson(id);
		Person entity =  personRepository
						.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Resource not found in the database with this ID"));
		
		return DozerAdapter.parseObject(entity, PersonVO.class);
	}
	
	public void delete(Long id) {
		Person entity = personRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found in the database with this ID"));
		
		personRepository.delete(entity);
	}
}
