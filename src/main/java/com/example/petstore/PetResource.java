package com.example.petstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {

	private final PetRepository petRepository;
	public PetResource(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

//	@APIResponses(value = {
//			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
//	@GET
//	public Response getPets() {
//		List<Pet> pets = new ArrayList<Pet>();
//		Pet pet1 = new Pet();
//		pet1.setPetId(1);
//		pet1.setPetAge(3);
//		pet1.setPetName("Boola");
//		pet1.setPetType("Dog");
//
//		Pet pet2 = new Pet();
//		pet2.setPetId(2);
//		pet2.setPetAge(4);
//		pet2.setPetName("Sudda");
//		pet2.setPetType("Cat");
//
//		Pet pet3 = new Pet();
//		pet3.setPetId(3);
//		pet3.setPetAge(2);
//		pet3.setPetName("Peththappu");
//		pet3.setPetType("Bird");
//
//		pets.add(pet1);
//		pets.add(pet2);
//		pets.add(pet3);
//		return Response.ok(pets).build();
//	}
//
//	@APIResponses(value = {
//			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
//			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
//	@GET
//	@Path("{petId}")
//	public Response getPet(@PathParam("petId") int petId) {
//		if (petId < 0) {
//			return Response.status(Status.NOT_FOUND).build();
//		}
//		Pet pet = new Pet();
//		pet.setPetId(petId);
//		pet.setPetAge(3);
//		pet.setPetName("Buula");
//		pet.setPetType("Dog");
//
//		return Response.ok(pet).build();
//
//	}


	//add pet
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Add Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pets"))) })
	@POST
	@Path("")
	public Response savePet(@RequestBody Pets pets){

		final Pets newPet = petRepository.save(pets);
		return Response.ok(newPet).build();
	}

	//get all pet list
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Get Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pets"))) })
	@GET
	@Path("")
	public Response getAllPets(){
		ArrayList<Pets> petsArrayList = new ArrayList<>();
		petsArrayList = (ArrayList<Pets>) petRepository.findAll();
		return Response.ok(petsArrayList).build();
	}

	//update pet
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Update Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pets"))) })
	@PUT
	@Path("")
	public Response updatePets(@RequestBody Pets pets){
		Optional<Pets> pets1 = Optional.ofNullable(petRepository.findById(pets.getPetId())
				.orElseThrow(() -> new NotFoundException("Could not pet with given id")));
		pets1.get().setName(pets.getName());
		pets1.get().setAge(pets.getAge());
		pets1.get().setType(pets.getType());
		return Response.ok(petRepository.save(pets1.get())).build();
	}

	//delete pet
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Delete Pet By Id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@DELETE
	@Path("/{id}")
	public Response findPetsByPetType(@PathParam("id") Long id){
		petRepository.deleteById(id);
		return Response.status(204).build();

	}

	//search by pet name
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search Pet by name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("/name/{name}")
	public Response findPetsByName(@PathParam("name") String name){
		return Response.ok(petRepository.findByName(name).get()).build();

	}

	//search by pet type
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("type/{type}")
	public Response findPetsByPetType(@PathParam("type") String type){
		List<Pets> petList = petRepository.findByType(type);
		return Response.ok(petList).build();

	}

}
