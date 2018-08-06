package com.epam.core.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Sequence.
 */
@Document(collection = "sequence")
@Data
public class Sequence {
    @Id
    private String id;
    private Long sequence;

    /**
     * Instantiates a new Sequence.
     *
     * @param id       the id
     * @param sequence the sequence
     */
    public Sequence(String id, Long sequence) {
        this.id = id;
        this.sequence = sequence;
    }
}
