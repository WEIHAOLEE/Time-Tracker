package com.sky.timetracker.pojo;

public class PieEntry {
    private float value;
    private String label;

    @Override
    public String toString() {
        return "PieEntry{" +
                "value=" + value +
                ", label='" + label + '\'' +
                '}';
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PieEntry(float value, String label) {
        this.value = value;
        this.label = label;
    }

    public PieEntry() {
    }
}
