package org.home.tooling.backend.model.typed;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Todo types
 * <p>
 * Sample typed or enumeration entity
 */
@AllArgsConstructor
public enum TodoType {
    PERSONAL("Personal"), WORK("WORK");

    @Getter
    private String value;

}
