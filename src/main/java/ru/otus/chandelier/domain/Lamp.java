package ru.otus.chandelier.domain;

public class Lamp {

    private int health;

    public Lamp() {
        this.health = 100;
    }

    public Lamp(int health) {
        this.health = health;
    }

    public Lamp takeDamage(int percent) {
        this.health -= percent;
        return this;
    }

    public int getHealth() {
        return health;
    }

    public boolean isNotBroken() {
        return this.health > 0;
    }

    @Override
    public String toString() {
        return "{ h=" + health + " }";
    }
}
