
package com.moacirjr.personapi.controller;

import com.moacirjr.personapi.dto.request.PersonDTO;
import com.moacirjr.personapi.dto.response.MessageResponseDTO;
import com.moacirjr.personapi.exception.PersonNotFoundException;
import com.moacirjr.personapi.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    @Autowired
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createPerson(@RequestBody @Valid PersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }

    @GetMapping
    public List<PersonDTO> listAll(){
        return personService.listAll();
    }

    @GetMapping("/{id}")
    public PersonDTO getById(@PathVariable Long id) throws PersonNotFoundException {
        return personService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws PersonNotFoundException {
         personService.deleteById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updareById(@RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException {
        return personService.updateById(personDTO);
    }

}
