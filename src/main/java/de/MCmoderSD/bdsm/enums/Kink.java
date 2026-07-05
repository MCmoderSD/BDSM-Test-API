package de.MCmoderSD.bdsm.enums;

import java.io.Serializable;

import static de.MCmoderSD.bdsm.utils.Documentation.data;

@SuppressWarnings("unused")
public enum Kink implements Serializable {

    // Enum constants
    Ageplayer(1),
    // 2 missing
    Brat(3),
    BratTamer(4),
    DaddyMommy(5),
    Degrader(6),
    Dominant(7),
    Degradee(8),
    // 9 missing
    Little(10),
    Masochist(11),
    MasterMistress(12),
    NonMonogamist(13),
    Owner(14),
    // 15 missing
    // 16 missing
    PrimalHunter(17),
    Pet(18),
    PrimalPrey(19),
    Rigger(20),
    RopeBunny(21),
    Sadist(22),
    Slave(23),
    Submissive(24),
    Switch(25),
    Vanilla(26),
    Voyeur(27),
    Exhibitionist(28),
    Experimentalist(29);

    // Attributes
    private final int id;

    // Constructor
    Kink(int id) {
        this.id = id;
    }

    // Methods
    public String getName(Language language) {
        return data.get(language.getCode()).get(String.valueOf(id)).get("name").asString();
    }

    public String getPairDesc(Language language) {
        return data.get(language.getCode()).get(String.valueOf(id)).get("pairdesc").asString();
    }

    public String getDescription(Language language) {
        return data.get(language.getCode()).get(String.valueOf(id)).get("description").asString();
    }

    // Getters
    public int getId() {
        return id;
    }

    // Static method to get Kink from ID
    public static Kink fromId(int id) {
        for (var kink : Kink.values()) {
            if (kink.getId() == id) {
                return kink;
            }
        }
        throw new IllegalArgumentException("Invalid Kink ID: " + id);
    }
}