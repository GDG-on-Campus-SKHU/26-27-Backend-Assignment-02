package leesak.jpa.controller;

import java.net.URI;
import java.util.List;
import leesak.jpa.dto.GenreRequest;
import leesak.jpa.dto.GenreResponse;
import leesak.jpa.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GenreResponse> create(@RequestBody GenreRequest request) {
        GenreResponse response = service.create(request);
        return ResponseEntity.created(URI.create("/genres/" + response.id())).body(response);
    }

    @GetMapping
    public List<GenreResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public GenreResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PatchMapping("/{id}")
    public GenreResponse update(@PathVariable Long id, @RequestBody GenreRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
