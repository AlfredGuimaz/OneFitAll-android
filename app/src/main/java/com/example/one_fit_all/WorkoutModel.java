package com.example.one_fit_all;

public class WorkoutModel {

    private int id;
    private String mGroup;
    private String eType;
    private int setAmount;
    private int repAmount;
    private int wAmount;

    public WorkoutModel(int id, String mGroup, String eType, int setAmount, int repAmount, int wAmount) {
        this.id = id;
        this.mGroup = mGroup;
        this.eType = eType;
        this.setAmount = setAmount;
        this.repAmount = repAmount;
        this.wAmount = wAmount;
    }

    public WorkoutModel() {
    }

    @Override
    public String toString() {
        return "WorkoutModel{" +
                "id=" + id +
                ", mGroup='" + mGroup + '\'' +
                ", eType='" + eType + '\'' +
                ", setAmount=" + setAmount +
                ", repAmount=" + repAmount +
                ", wAmount=" + wAmount +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmGroup() {
        return mGroup;
    }

    public void setmGroup(String mGroup) {
        this.mGroup = mGroup;
    }

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public int getSetAmount() {
        return setAmount;
    }

    public void setSetAmount(int setAmount) {
        this.setAmount = setAmount;
    }

    public int getRepAmount() {
        return repAmount;
    }

    public void setRepAmount(int repAmount) {
        this.repAmount = repAmount;
    }

    public int getwAmount() {
        return wAmount;
    }

    public void setwAmount(int wAmount) {
        this.wAmount = wAmount;
    }
}
