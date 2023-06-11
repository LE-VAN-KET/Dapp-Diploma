package io.ketlv.ediplomadapp.enumuration;

public enum SexEnum {
    MALE('M'), FERMALE('F');
    private Character value;

    SexEnum(char value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }
}
