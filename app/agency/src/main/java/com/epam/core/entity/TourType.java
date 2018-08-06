package com.epam.core.entity;

/**
 * The enum Tour type.
 */
public enum TourType {

    /**
     * Cruise tour type.
     */
    CRUISE,
    /**
     * Excursion tour type.
     */
    EXCURSION,
    /**
     * Exotic tour type.
     */
    EXOTIC,
    /**
     * Healing tour type.
     */
    HEALING;

    /**
     * From string tour type.
     *
     * @param s the s
     * @return the tour type
     */
    public static TourType fromString(String s) {
        for (TourType type : values()) {
            if (type.name().equalsIgnoreCase(s)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
