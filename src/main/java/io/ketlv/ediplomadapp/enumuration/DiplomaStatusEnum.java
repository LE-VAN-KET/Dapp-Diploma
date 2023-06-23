package io.ketlv.ediplomadapp.enumuration;

public enum DiplomaStatusEnum {
    PENDING("PENDING"), NOTSERIAL("NOTSERIAL"),
    VERIFIED("VERIFIED"), REJECTED("VERIFIED"), DAHONG("DAHONG");
    private String value;

    DiplomaStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
