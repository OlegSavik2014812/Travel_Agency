package com.epam.core.entity;

/**
 * The enum User role.
 */
public enum UserRole {
    /**
     * Admin user role.
     */
    ADMIN,
    /**
     * Member user role.
     */
    MEMBER;

    /**
     * From string user role.
     *
     * @param s the s
     * @return the user role
     */
    public static UserRole fromString(String s) {
        for (UserRole userRole : values()) {
            if (userRole.name().equalsIgnoreCase(s)) {
                return userRole;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
