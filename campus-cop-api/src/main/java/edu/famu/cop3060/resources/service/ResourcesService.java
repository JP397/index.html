package edu.famu.cop3060.resources.service;

import edu.famu.cop3060.resources.dto.ResourceDTO;
import edu.famu.cop3060.resources.store.InMemoryResourceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourcesService {

    private static final Logger log = LoggerFactory.getLogger(ResourcesService.class);

    private final InMemoryResourceStore store;

    public ResourcesService(InMemoryResourceStore store) {
        this.store = store;
        log.info("ResourcesService initialized");
    }

    public List<ResourceDTO> getAllResources(Optional<String> category, Optional<String> q) {
        log.info("Fetching resources - category: {}, q: {}", category.orElse("none"), q.orElse("none"));
        if (category.isEmpty() && q.isEmpty()) {
            return store.findAll();
        }
        return store.findByFilters(category, q);
    }

    public Optional<ResourceDTO> getResourceById(String id) {
        log.info("Fetching resource by id: {}", id);
        return store.findById(id);
    }
}
