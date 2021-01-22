package br.com.dhecastro.personrestfulcrud.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dhecastro.personrestfulcrud.service.PersonService;
import br.com.dhecastro.personrestfulcrud.vo.v1.PersonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Person Endpoint", tags = {"PersonEndpoint"})
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@ApiOperation(value="Find all person records")
	@GetMapping(produces={"application/json", "application/xml"})
	public List<PersonVO> findAll() {
		List<PersonVO> persons = new ArrayList<PersonVO>();
		
		for (PersonVO personVO : personService.findAll()) {
			personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getId())).withSelfRel());
			persons.add(personVO);
		}
		
		return persons;
	}
	
	@ApiOperation(value="Find person by id")
	@GetMapping(value="/{id}", produces={"application/json", "application/xml"})
	public PersonVO findById(@PathVariable("id") Long id) {
		return personService.findById(id).add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
	}
	
	@ApiOperation(value="Create person in database")
	@PostMapping(produces={"application/json", "application/xml"},
				 consumes={"application/json", "application/xml"})
	public PersonVO create(@RequestBody PersonVO person) {
		return personService.create(person).add(linkTo(methodOn(PersonController.class).create(person)).withSelfRel());
	}
	
	@ApiOperation(value="Update person in database")
	@PutMapping(produces={"application/json", "application/xml"},
			 	consumes={"application/json", "application/xml"})
	public PersonVO update(@RequestBody PersonVO person) {
		return personService.update(person).add(linkTo(methodOn(PersonController.class).update(person)).withSelfRel());
	}
	
	@ApiOperation(value="Disable person by id")
	@PatchMapping(value="/{id}", produces={"application/json", "application/xml"})
	public PersonVO disablePerson(@PathVariable("id") Long id) {
		return personService.disablePerson(id).add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
	}
	
	@ApiOperation(value="Delete person by id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		personService.delete(id);
		return ResponseEntity.ok().build();
	}
}
