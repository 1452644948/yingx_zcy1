package com.baizhi.zcy.entity;

public class Month implements Comparable<Month> {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Month() {
    }

    public Month(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Month{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public int compareTo(Month o) {
        if (this.getName().charAt(0) > o.getName().charAt(0)) {
            return 1;
        } else if (this.getName().charAt(0) < o.getName().charAt(0)) {
            return -1;
        } else {
            return 0;
        }
    }
}
