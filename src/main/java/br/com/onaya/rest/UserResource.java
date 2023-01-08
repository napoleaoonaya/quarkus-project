package br.com.onaya.rest;

import br.com.onaya.domain.model.User;
import br.com.onaya.rest.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @POST
    @Transactional
    public Response createUser(@RequestBody CreateUserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name());
        user.setAge(userRequest.age());

        user.persist();

        return Response.ok(user).status(Response.Status.CREATED).build();
    }

    @GET
    public Response listAllUsers() {
        PanacheQuery<User> query = User.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        User user = User.findById(id);
        if(user!=null){
            user.delete();
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest) {
        User user = User.findById(id);
        if(user!=null){
            user.setName(userRequest.name());
            user.setAge(userRequest.age());
            user.persist();
            return Response.ok(user).status(Response.Status.CREATED).build();
        }
        return Response.ok(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id) {
        User user = User.findById(id);
        if(user!=null){
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
