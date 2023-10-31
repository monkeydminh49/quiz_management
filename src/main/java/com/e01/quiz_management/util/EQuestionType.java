package com.e01.quiz_management.util;

public enum EQuestionType {
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    FILL_IN_BLANK;

    public String toString() {
        switch (this) {
            case SINGLE_CHOICE:
                return "SINGLE_CHOICE";
            case MULTIPLE_CHOICE:
                return "MULTIPLE_CHOICE";
            case FILL_IN_BLANK:
                return "FILL_IN_BLANK";
            default:
                return "Unknown";
        }
    }

    public static EQuestionType fromString(String str) {
        switch (str) {
            case "SINGLE_CHOICE":
                return SINGLE_CHOICE;
            case "MULTIPLE_CHOICE":
                return MULTIPLE_CHOICE;
            case "FILL_IN_BLANK":
                return FILL_IN_BLANK;
            default:
                return null;
        }
    }
}
