package org.hachiko.word;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

@Path("/words")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WordResource {

  @Inject
  WordRepository wordRepository;

  @POST
  public Response create(Word w) {
    wordRepository.persist(w);
    return Response.created(URI.create("/words/" + w.id.toString())).build();
  }

  @GET
  @Path("{id}")
  public Word read(@PathParam("id") String id) {
    return wordRepository.findById(new ObjectId(id));
  }

  @PUT
  @Path("{id}")
  public void update(Word w) {
    wordRepository.update(w);
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") String id) {
    wordRepository.findByIdOptional(new ObjectId(id)).ifPresent(w -> wordRepository.delete(w));
  }

  @GET
  public List<Word> list() {
    return wordRepository.listAll();
  }

  @GET
  @Path("search")
  public List<Word> search(@QueryParam("title") String title) {
    return wordRepository.list("title", title);
  }
}
