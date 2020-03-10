package g-alot{basePackage}.controller;

import g-alot{basePackage}.model.g-alot{Entity};
import g-alot{basePackage}.service.g-alot{Entity}Service;
import g-alot{basePackage}.controller.g-alot{Entity}Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/g-alot{entity}s")
public class g-alot{Entity}Controller {

    private final g-alot{Entity}Service service;

    public g-alot{Entity}Controller(g-alot{Entity}Service service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<g-alot{Entity}> create(@RequestBody g-alot{Entity} g-alot{entity}) {
        return ResponseEntity.ok().body(
                service.create(g-alot{entity})
        );
    }

    @GetMapping
    public ResponseEntity<List<g-alot{Entity}>> findAll() {
        return ResponseEntity.ok().body(
                service.findAll()
        );
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<g-alot{Entity}>> findAll(@RequestParam int pageIndex, @RequestParam int size) {
        return ResponseEntity.ok().body(
                service.findAll(pageIndex, size)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<g-alot{Entity}>> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                service.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<g-alot{Entity}> update(@RequestBody g-alot{Entity} g-alot{entity}, @PathVariable Long id) {
        g-alot{entity}.setId(id);
        return ResponseEntity.ok().body(
                service.save(g-alot{entity})
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
