package micnusz.backend;

public enum SortField {
    PRICE, RATING, TITLE;

    public static SortField fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return SortField.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}