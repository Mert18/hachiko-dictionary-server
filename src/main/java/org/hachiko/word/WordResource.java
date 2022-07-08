package org.hachiko.word;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/words")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Word Resource", description = "Word REST APIs")
public class WordResource {

  @Inject
  WordRepository wordRepository;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(operationId = "createWord", summary = "Create a word", description = "Used for creating word from a specific syntax.")
  @APIResponse(responseCode = "201", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  public Response create(Word w) {

    wordRepository.persist(w);
    return Response.created(URI.create("/words/" + w.id.toString())).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  @Operation(operationId = "getWord", summary = "Fetch a word", description = "Fetch a word by its id.")
  @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  public Word read(@PathParam("id") String id) {

    return wordRepository.findById(new ObjectId(id));
  }

  @DELETE
  @Path("{id}")
  @Operation(operationId = "deleteWord", summary = "Delete a word", description = "Delete a word by its id.")
  @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  public void delete(@PathParam("id") String id) {
    wordRepository.findByIdOptional(new ObjectId(id)).ifPresent(w -> wordRepository.delete(w));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(operationId = "getAllWords", summary = "Get all words", description = "Used for fetching all words from the database.")
  @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  public List<Word> list() {
    return wordRepository.listAll();
  }

}
