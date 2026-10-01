package models;

public class LightModel {
    private final Integer light;
    private final Double contrast;

    public LightModel(Integer light, Double contrast) {
        this.light = light;
        this.contrast = contrast;
    }

    public Integer getLight() {
        return light;
    }

    public Double getContrast() {
        return contrast;
    }
}
