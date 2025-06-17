package com.relatos.ms_books_catalogue.stepdefs;

import io.cucumber.java.en.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorSteps {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private ResponseEntity<String> response;

    @Given("el sistema está funcionando")
    public void el_sistema_funciona() {}

    @When("creo un autor con nombre {string} y apellido {string}")
    public void creo_un_autor(String nombre, String apellido) {
        String json = String.format("{\"name\":\"%s\", \"lastName\":\"%s\"}", nombre, apellido);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        response = restTemplate.postForEntity("http://localhost:8081/api/v1/catalogue/authors", entity, String.class);
    }

    @Then("la respuesta debe tener código {int}")
    public void valida_codigo(int codigoEsperado) {
        assertEquals(codigoEsperado, response.getStatusCodeValue());
    }
}
