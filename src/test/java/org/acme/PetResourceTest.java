package org.acme;

import com.example.petstore.Pets;
import com.google.gson.Gson;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
@QuarkusTest
public class PetResourceTest {

	@Test
    public void testPetEndpoint() {
        given()
          .when().get("/v1/pets")
          .then()
             .statusCode(200);
//             .body(hasItem(
// 		            allOf(
//    		                hasEntry("pet_id", "1"),
//    		                hasEntry("pet_type", "Dog"),
//    		                hasEntry("pet_name", "Boola"),
//    		                hasEntry("pet_age", "3")
//    		            )
//    		      )
//    		 );
    }

    @Test
    public void testAddPetEndPoint(){
        final Pets pets = new Pets();
        pets.setName("Bella");
        pets.setAge("3");
        pets.setType("cat");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("name", is(pets.getName()))
                .body("age", is(pets.getAge()))
                .body("type", is(pets.getType()));
    }

    @Test
    public void testGetAllPets(){
        final Pets pets = new Pets();
        pets.setName("Luna");
        pets.setAge("4");
        pets.setType("dog");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("name", is(pets.getName()))
                .body("age", is(pets.getAge()))
                .body("type", is(pets.getType()));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets")
                .then()
                .statusCode(200)
                .body("size", notNullValue());
    }

    @Test
    public void testUpdatePet(){
        final Pets pets = new Pets();
        pets.setName("Lassi");
        pets.setAge("5");
        pets.setType("dog");

        final String response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("name", is(pets.getName()))
                .body("age", is(pets.getAge()))
                .body("type", is(pets.getType())).extract().asString();

        final Gson gson = new Gson();

        final Pets newPet = gson.fromJson(response,Pets.class);

        final Pets updatePets = new Pets("Coco", pets.getAge(), pets.getType());
        updatePets.setPetId(newPet.getPetId());

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatePets)
                .when().put("/v1/pets")
                .then()
                .statusCode(200)
                .body("name", is(updatePets.getName()));
    }

    @Test
    public void testDeletePet(){
        final Pets pets = new Pets();
        pets.setName("pouli");
        pets.setAge("2");
        pets.setType("parrot");

        final String response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("name", is(pets.getName()))
                .body("age", is(pets.getAge()))
                .body("type", is(pets.getType())).extract().asString();

        final Gson gson = new Gson();

        final Pets newPet = gson.fromJson(response,Pets.class);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().delete("/v1/pets/{id}", newPet.getPetId())
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindPetByName(){
        final Pets pets = new Pets();
        pets.setName("piggy");
        pets.setAge("4");
        pets.setType("cat");

        final String response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("name", is(pets.getName()))
                .body("age", is(pets.getAge()))
                .body("type", is(pets.getType())).extract().asString();

        final Gson gson = new Gson();

        final Pets newPet = gson.fromJson(response,Pets.class);


        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/name/{name}", newPet.getName())
                .then()
                .statusCode(200)
                .body("name", is(newPet.getName()));
    }

    @Test
    public void testFindPetsByType(){
        final Pets pets = new Pets();
        pets.setName("brown");
        pets.setAge("5");
        pets.setType("dog");

        final String response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("name", is(pets.getName()))
                .body("age", is(pets.getAge()))
                .body("type", is(pets.getType())).extract().asString();

        final Gson gson = new Gson();

        final Pets newPet = gson.fromJson(response,Pets.class);


        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/type/{type}", newPet.getType())
                .then()
                .statusCode(200)
                .body("size", notNullValue());
    }
}