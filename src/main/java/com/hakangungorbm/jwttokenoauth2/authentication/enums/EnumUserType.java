package com.hakangungorbm.jwttokenoauth2.authentication.enums;

public enum EnumUserType {
    ADMIN("Admin", 1),
    HEMSIRE("Hemsire", 2),
    DOKTOR("Doktor", 3);

    private final String label;

    private final int value;

    EnumUserType(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public String getValue() {
        return name();
    }

    public int getLevel() {
        return value;
    }

    public int compare(EnumUserType that) {
        return Integer.compare(this.value, that.value);
    }
}
