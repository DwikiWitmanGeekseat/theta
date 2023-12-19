package co.flexidev.theta.controller;

import co.flexidev.theta.model.Person;
import co.flexidev.theta.service.PersonService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Path("/api/person")
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@Produces(MediaType.APPLICATION_JSON)
public class PersonController {

    private final PersonService personService;

    @Inject
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PUT
    public Response update(Person person) throws SQLException {
        if (person.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(personService.save(person)).build();
    }

    @GET
    public Response findAll() {
        Iterable<Person> personList = personService.findAll();
        return Response.ok(personList).build();
    }

    @GET
    @Path("/datatables")
    public Response datatables(
            @QueryParam("itemsPerPage") Long itemsPerPage,
            @QueryParam("page") Long page,
            @QueryParam("sortBy") List<String> sortBy,
            @QueryParam("sortDesc") List<Boolean> sortDesc) {
        return Response.ok(personService.datatables(page, itemsPerPage, sortBy, sortDesc)).build();
    }

    @GET
    @Path("/count")
    public Response count() {
        return Response.ok(personService.count()).build();
    }

    @GET
    @Path("/email")
    public Response findByEmail(@QueryParam("email") String email) {
        Person person = personService.findByEmail(email);
        return Response.ok(person).build();
    }

    @POST
    @Path("/save")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Response save(Person person) throws Exception {
        Person dbPerson = personService.savePerson(person);
        return Response.ok(dbPerson).build();
    }

    @GET
    @Path("/active")
    public Response findByActive(@QueryParam("active") Boolean active) {
        return Response.ok(personService.findByActive(active)).build();
    }

    @POST
    @Path("/trx/demo")
    public Response trxDemo(Person person) throws Exception {
        return Response.ok(personService.trxDemo(person)).build();
    }

    @PUT
    @Path("/edit")
    public Response edit(Person person) throws Exception {
        return Response.ok(personService.save(person)).build();
    }

    @DELETE
    @Path("/delete")
    public Response delete(@QueryParam("id") Long id) {
        personService.deleteById(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/inactive/delete")
    public Response deleteInactivePerson() {
        return Response.ok(personService.deleteInactivePerson()).build();
    }
}