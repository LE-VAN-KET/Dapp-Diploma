package io.ketlv.ediplomadapp.enumuration;

public enum ModeOfStudyEnum {
    CHINH_QUY("CHINH QUY"), TAI_CHUC("TAI CHUC");
    private String value;

    ModeOfStudyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
