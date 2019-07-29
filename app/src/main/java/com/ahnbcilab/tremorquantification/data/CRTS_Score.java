package com.ahnbcilab.tremorquantification.data;

public class CRTS_Score {
    int crts_PartA_score;
    int crts_PartB_score;
    int crts_PartC_score;

    public CRTS_Score(int crts_PartA_score, int crts_PartB_score, int crts_PartC_score) {
        this.crts_PartA_score = crts_PartA_score;
        this.crts_PartB_score = crts_PartB_score;
        this.crts_PartC_score = crts_PartC_score;
    }

    public int getCrts_PartA_score() {
        return crts_PartA_score;
    }

    public int getCrts_PartB_score() {
        return crts_PartB_score;
    }

    public int getCrts_PartC_score() {
        return crts_PartC_score;
    }
}
