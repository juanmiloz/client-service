Feature: ClientController integration tests

  Background:
    * url 'http://localhost:8001/api'
    * configure headers = { 'Content-Type': 'application/json' }

  Scenario: Create, retrieve, update and delete a client
    Given path '/client'
    And request
      """
      {
        "name": "John Doe",
        "gender": "Male",
        "age": 30,
        "identification": "ABC123456",
        "address": "123 Main St",
        "number": "5551234567",
        "password": "securePass123",
        "status": "ACTIVE"
      }
      """
    When method post
    Then status 201
    And match response.id != null
    And match response.name == 'John Doe'
    * def clientId = response.id

    # 2) Retrieve all clients and verify our new client is in the list
    Given path '/client'
    When method get
    Then status 200
    And match response[*].id contains clientId

    # 3) Retrieve client by ID
    Given path '/client', clientId
    When method get
    Then status 200
    And match response.id == clientId
    And match response.identification == 'ABC123456'

    # 4) Update the client
    Given path '/client'
    And request
      """
      {
        "clientId": "#(clientId)",
        "name": "John Doe Updated",
        "gender": "Other",
        "age": 31,
        "identification": "XYZ987654",
        "address": "456 Elm St",
        "number": "5557654321",
        "password": "newSecurePass456",
        "status": "INACTIVE"
      }
      """
    When method put
    Then status 200
    And match response.id == clientId
    And match response.name == 'John Doe Updated'
    And match response.gender == 'Other'
    And match response.status == 'INACTIVE'

    # 5) Delete the client
    Given path '/client', clientId
    When method delete
    Then status 204

    # 6) Verify the client no longer exists
    Given path '/client', clientId
    When method get
    Then status 404