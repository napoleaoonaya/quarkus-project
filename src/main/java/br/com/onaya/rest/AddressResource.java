package br.com.onaya.rest;

import br.com.onaya.domain.model.Address;
import br.com.onaya.domain.repository.AddressRepository;
import br.com.onaya.rest.dto.CreateAddressRequest;
import br.com.onaya.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/address")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    private AddressRepository addressRepository;
    private Validator validator;

    @Inject
    public AddressResource(AddressRepository repository, Validator validator){
        this.addressRepository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createAddress(CreateAddressRequest addressRequest) {

        Set<ConstraintViolation<CreateAddressRequest>> violations = validator.validate(addressRequest);

        if(!violations.isEmpty()){
            return ResponseError.createFromValidation(violations).withStatusCode(UNPROCESSABLE_ENTITY_STATUS);
        }

        Address address = new Address();
        address.setAddress(addressRequest.address());
        address.setNumberHouse(addressRequest.numberHouse());

        addressRepository.persist(address);

        return Response.ok(address).status(Response.Status.CREATED).build();
    }

    @GET
    public Response listAllAddress() {
        PanacheQuery<Address> address = addressRepository.findAll();
        return Response.ok(address.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteAddress(@PathParam("id") Long id) {
        Address address = addressRepository.findById(id);
        if(address!=null){
            addressRepository.delete(address);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateAddress(@PathParam("id") Long id, CreateAddressRequest addressRequest) {
        Address address = addressRepository.findById(id);
        if(address!=null){
            address.setAddress(addressRequest.address());
            address.setNumberHouse(addressRequest.numberHouse());
            addressRepository.persist(address);
            return Response.ok(address).status(Response.Status.CREATED).build();
        }
        return Response.ok(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id) {
        Address address = addressRepository.findById(id);
        if(address!=null){
            return Response.ok(address).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
