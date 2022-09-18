package org.home.tooling.backend.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Summary for home page
 * <p>
 * This is just a sample of a DTO
 */
@Data
@Builder
public class Summary {

    private Long todoCount;
    private Long completedCount;
}
