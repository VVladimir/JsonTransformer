package org.example;

public class ColorMe {
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private int code;
    private String color;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ColorMe{" +
                "code=" + code +
                ", color='" + color + '\'' +
                '}';
    }
}
