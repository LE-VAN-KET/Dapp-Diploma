package io.ketlv.ediplomadapp.enumuration;

public enum DiplomaStatusEnum {
    PENDING("PEN"), NOTSERIAL("USE"),
    VERIFIED("VER"), REJECTED("REJ");
    private String value;

    DiplomaStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
