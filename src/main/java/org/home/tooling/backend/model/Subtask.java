package org.home.tooling.backend.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.home.tooling.backend.model.spec.AbstractEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(callSuper = true)
public class Subtask extends AbstractEntity {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 1000)
    private String description;
}
