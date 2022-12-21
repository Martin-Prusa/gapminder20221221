package cz.martin.models;

public class Country {
    private String country;
    private double lifeExp;

    public Country(String country, double lifeExp) {
        this.country = country;
        this.lifeExp = lifeExp;
    }

    public String getCountry() {
        return country;
    }

    public double getLifeExp() {
        return lifeExp;
    }
}
