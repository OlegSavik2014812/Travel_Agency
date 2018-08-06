package com.epam.core.util;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Search criteria.
 */
@Getter
@Setter
public class SearchCriteria {
    private String key;
    private String operation;
    private String value;

    /**
     * Instantiates a new Search criteria.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     */
    public SearchCriteria(String key, String operation, String value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
