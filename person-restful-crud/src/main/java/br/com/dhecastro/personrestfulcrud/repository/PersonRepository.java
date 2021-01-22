package br.com.dhecastro.personrestfulcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dhecastro.personrestfulcrud.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	@Query("SELECT p FROM Person p WHERE p.firstName = :firstName")
	Person findByFirstName(@Param("firstName") String firstName);
	
	@Modifying
	@Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
	void disablePerson(@Param("id") Long id);
}
