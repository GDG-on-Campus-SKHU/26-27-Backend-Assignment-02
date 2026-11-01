package leesak.jpa;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibraryApiIntegrationTests {

    @LocalServerPort
    private int port;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void bookAndGenreCrudPreservesRelationships() throws Exception {
        HttpResponse<String> genreResponse = request("POST", "/genres", "{\"name\":\"Novel\"}");
        assertEquals(201, genreResponse.statusCode());
        long genreId = json(genreResponse).get("id").asLong();
        assertTrue(genreResponse.headers().firstValue("Location").orElse("").endsWith("/genres/" + genreId));

        HttpResponse<String> bookResponse = request("POST", "/books",
                "{\"title\":\"The Little Prince\",\"author\":\"Antoine de Saint-Exupery\",\"genreId\":" + genreId + "}");
        assertEquals(201, bookResponse.statusCode());
        long bookId = json(bookResponse).get("id").asLong();
        assertEquals(genreId, json(bookResponse).get("genreId").asLong());
        assertTrue(bookResponse.headers().firstValue("Location").orElse("").endsWith("/books/" + bookId));

        HttpResponse<String> detail = request("GET", "/genres/" + genreId, null);
        assertEquals(200, detail.statusCode());
        assertEquals(bookId, json(detail).get("books").get(0).get("id").asLong());
        assertTrue(json(request("GET", "/books", null)).isArray());
        assertTrue(json(request("GET", "/genres", null)).isArray());

        HttpResponse<String> changedBook = request("PATCH", "/books/" + bookId, "{\"title\":\"Updated title\"}");
        assertEquals(200, changedBook.statusCode());
        assertEquals("Updated title", json(changedBook).get("title").asString());
        assertEquals("Antoine de Saint-Exupery", json(changedBook).get("author").asString());

        HttpResponse<String> changedGenre = request("PATCH", "/genres/" + genreId, "{\"name\":\"Fiction\"}");
        assertEquals(200, changedGenre.statusCode());
        assertEquals("Fiction", json(changedGenre).get("name").asString());
        assertEquals("Fiction", json(request("GET", "/books/" + bookId, null)).get("genreName").asString());
        assertEquals(409, request("DELETE", "/genres/" + genreId, null).statusCode());

        assertEquals(204, request("DELETE", "/books/" + bookId, null).statusCode());
        assertEquals(404, request("GET", "/books/" + bookId, null).statusCode());
        assertEquals(0, json(request("GET", "/genres/" + genreId, null)).get("books").size());
        assertEquals(204, request("DELETE", "/genres/" + genreId, null).statusCode());
        assertEquals(404, request("GET", "/genres/" + genreId, null).statusCode());
    }

    @Test
    void movingBookChangesBothGenreCollections() throws Exception {
        long firstId = json(request("POST", "/genres", "{\"name\":\"Science\"}")).get("id").asLong();
        long secondId = json(request("POST", "/genres", "{\"name\":\"History\"}")).get("id").asLong();
        long bookId = json(request("POST", "/books", "{\"title\":\"A book\",\"author\":\"An author\",\"genreId\":" + firstId + "}")).get("id").asLong();

        HttpResponse<String> moved = request("PATCH", "/books/" + bookId, "{\"genreId\":" + secondId + "}");
        assertEquals(200, moved.statusCode());
        assertEquals(secondId, json(moved).get("genreId").asLong());
        assertEquals(0, json(request("GET", "/genres/" + firstId, null)).get("books").size());
        assertEquals(bookId, json(request("GET", "/genres/" + secondId, null)).get("books").get(0).get("id").asLong());

        assertEquals(204, request("DELETE", "/genres/" + firstId, null).statusCode());
        assertEquals(409, request("DELETE", "/genres/" + secondId, null).statusCode());
        assertEquals(204, request("DELETE", "/books/" + bookId, null).statusCode());
        assertEquals(204, request("DELETE", "/genres/" + secondId, null).statusCode());
    }

    @Test
    void invalidCreationAndMalformedJsonReturnBadRequest() throws Exception {
        assertEquals(400, request("POST", "/genres", "{\"name\":\" \"}").statusCode());
        assertEquals(400, request("POST", "/genres", "{}").statusCode());
        assertEquals(400, request("POST", "/books", "{\"title\":\" \"}").statusCode());
        assertEquals(400, request("POST", "/books", "{\"title\":\"Book\",\"author\":\"Writer\"}").statusCode());
        assertEquals(400, request("POST", "/books", "{").statusCode());
        assertEquals(404, request("POST", "/books", "{\"title\":\"Book\",\"author\":\"Writer\",\"genreId\":9223372036854775807}").statusCode());
    }

    @Test
    void invalidPatchDoesNotPersistChanges() throws Exception {
        long genreId = json(request("POST", "/genres", "{\"name\":\"Essay\"}")).get("id").asLong();
        long bookId = json(request("POST", "/books", "{\"title\":\"Original\",\"author\":\"Writer\",\"genreId\":" + genreId + "}")).get("id").asLong();
        assertEquals(400, request("PATCH", "/books/" + bookId, "{\"title\":\"Changed\",\"author\":\" \"}").statusCode());
        assertEquals("Original", json(request("GET", "/books/" + bookId, null)).get("title").asString());
        assertEquals(404, request("PATCH", "/books/" + bookId, "{\"title\":\"Changed\",\"genreId\":9223372036854775807}").statusCode());
        assertEquals("Original", json(request("GET", "/books/" + bookId, null)).get("title").asString());
        assertEquals(400, request("PATCH", "/genres/" + genreId, "{\"name\":\" \"}").statusCode());
        assertEquals("Essay", json(request("GET", "/genres/" + genreId, null)).get("name").asString());
        assertEquals(204, request("DELETE", "/books/" + bookId, null).statusCode());
        assertEquals(204, request("DELETE", "/genres/" + genreId, null).statusCode());
    }

    @Test
    void missingResourcesReturnNotFound() throws Exception {
        for (String resource : new String[]{"books", "genres"}) {
            assertEquals(404, request("GET", "/" + resource + "/9223372036854775807", null).statusCode());
            assertEquals(404, request("DELETE", "/" + resource + "/9223372036854775807", null).statusCode());
            assertEquals(404, request("PATCH", "/" + resource + "/9223372036854775807", "{}").statusCode());
        }
    }

    private JsonNode json(HttpResponse<String> response) {
        return mapper.readTree(response.body());
    }

    private HttpResponse<String> request(String method, String path, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .method(method, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
