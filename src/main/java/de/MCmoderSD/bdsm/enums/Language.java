package de.MCmoderSD.bdsm.enums;

import java.io.Serializable;

public enum Language implements Serializable {

    // Enum constants
    English("en"),
    Spanish("es"),
    Portuguese("pt"),
    French("fr"),
    German("de"),
    Italian("it"),
    Polish("pl"),
    Dutch("nl"),
    Russian("ru"),
    Turkish("tr"),
    Chinese("zh"),
    Japanese("ja"),
    Ukrainian("uk"),
    Hungarian("hu"),
    Czech("cs"),
    Korean("ko"),
    Thai("th"),
    Vietnamese("vi");

    // Fields
    private final String code;

    // Constructor
    Language(String code) {
        this.code = code;
    }

    // Getters
    public String getCode() {
        return code;
    }

    // Static method to get Language from code
    public static Language fromCode(String code) {
        for (var lang : Language.values()) {
            if (lang.getCode().equalsIgnoreCase(code)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("Invalid Language code: " + code);
    }
}