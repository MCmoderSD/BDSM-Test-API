package de.MCmoderSD.bdsm.enums;

import java.io.Serializable;

import static java.lang.Integer.MAX_VALUE;

@SuppressWarnings("unused")
public enum AgeGroup implements Serializable {

    // Enum constants
    UNDER_20(0, 0, 19),
    AGE_20_TO_22(1, 20, 22),
    AGE_23_TO_25(2, 23, 25),
    AGE_26_TO_30(3, 26, 30),
    AGE_31_TO_35(4, 31, 35),
    AGE_36_TO_40(5, 36, 40),
    AGE_41_TO_50(6, 41, 50),
    AGE_51_TO_60(7, 51, 60),
    AGE_61_TO_75(8, 61, 75),
    ABOVE_75(9, 76, MAX_VALUE);

    // Fields
    private final int id;
    private final int minAge;
    private final int maxAge;

    // Constructor
    AgeGroup(int id, int minAge, int maxAge) {
        this.id = id;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    // Static methods
    public static AgeGroup fromId(int id) {
        for (AgeGroup ageGroup : AgeGroup.values()) {
            if (ageGroup.getId() == id) {
                return ageGroup;
            }
        }
        throw new IllegalArgumentException("Invalid AgeGroup ID: " + id);
    }

    public static AgeGroup fromAge(int age) {
        for (var ageGroup : AgeGroup.values()) {
            if (age >= ageGroup.getMinAge() && age <= ageGroup.getMaxAge()) {
                return ageGroup;
            }
        }
        throw new IllegalArgumentException("Invalid Age: " + age);
    }
}