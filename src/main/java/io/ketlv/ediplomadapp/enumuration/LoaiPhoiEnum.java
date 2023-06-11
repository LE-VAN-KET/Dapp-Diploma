package io.ketlv.ediplomadapp.enumuration;

public enum LoaiPhoiEnum {
    BANCHINH('C'), BANSAO('S');
    private char value;

    LoaiPhoiEnum(char value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
