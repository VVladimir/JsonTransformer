package org.example;



public class Tableron {
    private String name;
    private ColorMe color;

    public String getName() {
        return name;
    }

    public ColorMe getColor() {
        return color;
    }

    public void setColor(ColorMe color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Tableron{" +
                "name='" + name + '\'' +
                ", color=" + color +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
