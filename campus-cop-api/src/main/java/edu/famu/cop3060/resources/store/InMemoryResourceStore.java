package edu.famu.cop3060.resources.store;

import edu.famu.cop3060.resources.dto.ResourceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * In-memory data store for campus resources.
 * Seeded with sample data on construction.
 */
@Component
public class InMemoryResourceStore {
    
    private static final Logger log = LoggerFactory.getLogger(InMemoryResourceStore.class);
    
    private final Map<String, ResourceDTO> byId = new HashMap<>();
    private final List<ResourceDTO> all = new ArrayList<>();
    
    public InMemoryResourceStore() {
        seedData();
        log.info("InMemoryResourceStore initialized with {} resources", all.size());
    }
    
    private void seedData() {
        List<ResourceDTO> resources = List.of(
            new ResourceDTO(
                "1",
                "FAMU Tutorial Center",
                "Tutoring",
                "Tucker Hall, Room 201",
                "https://famu.edu/tutoring",
                List.of("tutoring", "academic-support", "free")
            ),
            new ResourceDTO(
                "2",
                "Computer Science Lab",
                "Lab",
                "Bond Engineering Building, Room 105",
                "https://famu.edu/cis-lab",
                List.of("lab", "computers", "24/7")
            ),
            new ResourceDTO(
                "3",
                "Academic Advising Office",
                "Advising",
                "Foote-Hilyer Administration Center, Suite 300",
                "https://famu.edu/advising",
                List.of("advising", "registration", "appointments")
            ),
            new ResourceDTO(
                "4",
                "Writing Center",
                "Tutoring",
                "Coleman Library, 2nd Floor",
                "https://famu.edu/writing-center",
                List.of("writing", "tutoring", "essays", "free")
            ),
            new ResourceDTO(
                "5",
                "Math Tutoring Lab",
                "Tutoring",
                "Banneker Building, Room 104",
                "https://famu.edu/math-tutoring",
                List.of("math", "tutoring", "calculus", "statistics")
            ),
            new ResourceDTO(
                "6",
                "Career Development Center",
                "Advising",
                "Cropper-Johnson Hall, 1st Floor",
                "https://famu.edu/career-center",
                List.of("career", "jobs", "internships", "resume")
            ),
            new ResourceDTO(
                "7",
                "Engineering Study Room",
                "Lab",
                "Bond Engineering Building, Room 210",
                "https://famu.edu/eng-study",
                List.of("lab", "study-room", "group-work", "engineering")
            ),
            new ResourceDTO(
                "8",
                "Student Wellness Center",
                "Advising",
                "Paddyfote Complex",
                "https://famu.edu/wellness",
                List.of("health", "counseling", "wellness", "appointments")
            )
        );
        
        for (ResourceDTO resource : resources) {
            byId.put(resource.id(), resource);
            all.add(resource);
        }
    }
    
    /**
     * Returns all resources.
     * @return unmodifiable copy of all resources
     */
    public List<ResourceDTO> findAll() {
        return List.copyOf(all);
    }
    
    /**
     * Finds a resource by ID.
     * @param id the resource ID
     * @return Optional containing the resource if found
     */
    public Optional<ResourceDTO> findById(String id) {
        return Optional.ofNullable(byId.get(id));
    }
    
    /**
     * Finds resources matching the given filters.
     * @param category optional category filter (case-insensitive)
     * @param q optional query string (case-insensitive substring match on name or tags)
     * @return list of matching resources
     */
    public List<ResourceDTO> findByFilters(Optional<String> category, Optional<String> q) {
        return all.stream()
            .filter(resource -> {
                // Category filter
                if (category.isPresent()) {
                    if (!resource.category().equalsIgnoreCase(category.get())) {
                        return false;
                    }
                }
                
                // Query filter (search in name and tags)
                if (q.isPresent()) {
                    String query = q.get().toLowerCase();
                    boolean matchesName = resource.name().toLowerCase().contains(query);
                    boolean matchesTags = resource.tags().stream()
                        .anyMatch(tag -> tag.toLowerCase().contains(query));
                    
                    if (!matchesName && !matchesTags) {
                        return false;
                    }
                }
                
                return true;
            })
            .collect(Collectors.toList());
    }
}
