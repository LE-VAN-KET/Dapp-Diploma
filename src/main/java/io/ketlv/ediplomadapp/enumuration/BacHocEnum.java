package io.ketlv.ediplomadapp.enumuration;

public enum BacHocEnum {
    DAIHOC(1), CAODANG(2);
    private int value;

    BacHocEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
