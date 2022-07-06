package org.hachiko.word;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

@Path("/words")
public class WordResource {
  @Inject
  WordRepository repository;

  @GET
  @Path("/{id}")
  public Response get(@PathParam("id") String id) {
    Word word = repository.findById(new ObjectId(id));
    return Response.ok(word).build();
  }

  @GET
  public Response get() {
    return Response.ok(repository.findAll()).build();
  }

  @GET
  @Path("/search/{title}")
  public Response search(@PathParam("title") String title) {
    Word word = repository.findByTitle(title);
    return word != null ? Response.ok(word).build()
        : Response.status(javax.ws.rs.core.Response.Status.NOT_FOUND).build();
  }

  @POST
  public Response create(Word word) throws URISyntaxException {
    repository.persist(word);
    return Response.created(new URI("/" + word.id)).build();
  }

  @PUT
  @Path("/{id}")
  public Response update(@PathParam("id") String id, Word word) {
    word.id = new ObjectId(id);
    repository.update(word);
    return Response.ok(word).build();
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") String id) {
    Word word = repository.findById(new ObjectId(id));
    repository.delete(word);
    return Response.noContent().build();
  }
}
