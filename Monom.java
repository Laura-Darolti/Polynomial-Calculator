package org.example;

public class Monom {
    private Integer exp;
    private Double coef;

    public Monom(Integer exp,double coef) {
        this.coef=coef;
        this.exp=exp;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }
}
