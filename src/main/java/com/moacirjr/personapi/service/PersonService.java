package com.moacirjr.personapi.service;

import com.moacirjr.personapi.dto.request.PersonDTO;
import com.moacirjr.personapi.dto.response.MessageResponseDTO;
import com.moacirjr.personapi.entity.Person;
import com.moacirjr.personapi.exception.PersonNotFoundException;
import com.moacirjr.personapi.mapper.PersonMapper;
import com.moacirjr.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;


    public MessageResponseDTO createPerson(@RequestBody PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");

    }

    public List<PersonDTO> listAll() {
        List<Person> allPerson = personRepository.findAll();
        return allPerson.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO getById(Long id) throws PersonNotFoundException {
//        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public void deleteById(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }


    public MessageResponseDTO updateById(PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(personDTO.getId());
        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Update person with ID ");
    }


    private static MessageResponseDTO createMessageResponse(Long savedPerson, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + savedPerson)
                .build();
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

}
