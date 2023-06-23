package io.ketlv.ediplomadapp.enumuration;

public enum ModeOfStudyEnum {
    CHINH_QUY("Chính quy"), TAI_CHUC("Tại chức");
    private String value;

    ModeOfStudyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
