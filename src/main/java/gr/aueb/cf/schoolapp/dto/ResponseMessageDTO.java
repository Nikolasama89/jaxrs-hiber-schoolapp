package gr.aueb.cf.schoolapp.dto;

public record ResponseMessageDTO(String code, String description) {         // Canonical constructor

    // ΣΤΟΥΣ AUXILIARY CONSTRUCTORS ΚΑΛΟΥΜΕ ΜΕ THIS TOYS CANONICAL
    public ResponseMessageDTO(String code) {
        this(code, "");     // auxiliary constructor
    }
}
