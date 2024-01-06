package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.service.PersonService;
import com.runtik.dbcoursework.response.Response;
import com.runtik.dbcoursework.response.ResponseWithCollection;
import com.runtik.dbcoursework.tables.pojos.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@org.springframework.web.bind.annotation.RestController()
@RequestMapping(path = "person")
public class PersonController {

    private final PersonService restService;

    @Autowired
    public PersonController(PersonService restService) {
        this.restService = restService;
    }

    @PostMapping(path = "create")
    @Secured("BOSS")
    public Response create(@RequestBody Person person) {
        restService.insertPerson(person);
        return new Response(HttpStatus.OK, "successfully added");
    }
    @GetMapping(path = "get")
    @Secured("ROLE_USER")
    public ResponseWithCollection<Person> get() {
        return new ResponseWithCollection<>(HttpStatus.OK, "successfully received", restService.getPersons());
    }
}
