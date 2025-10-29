package edu.famu.cop3060.resources.dto;

import java.util.List;

/**
 * Data Transfer Object for Campus Resources.
 * Immutable record with required fields.
 */
public record ResourceDTO(
    String id,
    String name,
    String category,
    String location,
    String url,
    List<String> tags
) {}
