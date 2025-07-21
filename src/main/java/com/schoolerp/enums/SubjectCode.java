package com.schoolerp.enums;

public enum SubjectCode {

    // Pre-Primary
    PP1("Hindi Rhymes"),
    PP2("English Rhymes"),
    PP3("Math Basics"),
    PP4("Drawing / Art"),
    PP5("General Awareness"),

    // Primary & Upper Primary
    HIN("Hindi"),
    ENG("English"),
    MAT("Mathematics"),
    EVS("Environmental Studies"),
    SAN("Sanskrit"),
    ART("Art & Craft"),
    GK("General Knowledge"),
    COMP("Computer Basics"),
    MOR("Moral Science"),
    PHYEDU("Physical Education"),

    // Secondary & Sr. Secondary
    PHY("Physics"),
    CHE("Chemistry"),
    BIO("Biology"),
    HIS("History"),
    POL("Political Science"),
    ECO("Economics"),
    ACC("Accountancy"),
    BST("Business Studies"),
    PE("Physical Education"),
    CS("Computer Science");

    private final String subjectName;

    SubjectCode(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
