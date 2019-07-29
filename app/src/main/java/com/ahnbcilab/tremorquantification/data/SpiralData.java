package com.ahnbcilab.tremorquantification.data;

public class SpiralData {
    double amplitude;
    double hz;
    double fitting_ratio;
    String path;

    public SpiralData(double amplitude, double hz, double fitting_ratio, String path) {
        this.amplitude = amplitude;
        this.hz = hz;
        this.fitting_ratio = fitting_ratio;
        this.path = path;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public double getHz() {
        return hz;
    }

    public double getFitting_ratio() {
        return fitting_ratio;
    }

    public String getpath() {
        return path;
    }
}
