Feature: Gestión de autores

  Scenario: Crear un autor exitosamente
    Given el sistema de catálogo está funcionando
    When creo un autor con nombre "Friedrich" y apellido "Nietzsche"
    Then la respuesta debe tener código 200
