package edu.famu.cop3060.resources.controller;

import edu.famu.cop3060.resources.dto.ResourceDTO;
import edu.famu.cop3060.resources.service.ResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resources")
public class ResourcesController {

    private static final Logger log = LoggerFactory.getLogger(ResourcesController.class);

    private final ResourcesService service;

    public ResourcesController(ResourcesService service) {
        this.service = service;
        log.info("ResourcesController initialized");
    }

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> getAllResources(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String q
    ) {
        log.info("GET /api/resources - category: {}, q: {}", category != null ? category : "null", q != null ? q : "null");
        List<ResourceDTO> resources = service.getAllResources(Optional.ofNullable(category), Optional.ofNullable(q));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable String id) {
        log.info("GET /api/resources/{}", id);
        return service.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
