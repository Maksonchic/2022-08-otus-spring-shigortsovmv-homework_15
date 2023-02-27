package ru.otus.chandelier.domain;

import java.util.List;

public class Chandelier {

    private int LAMP_CAPACITY = 5;
    private final List<Lamp> lamps;

    public Chandelier(List<Lamp> lamps) {
        this.lamps = lamps;
    }

    public boolean isFilled() {
        return lamps.stream().filter(Lamp::isNotBroken).count() >= this.LAMP_CAPACITY;
    }

    public void addNewLamp() {
        this.lamps.add(new Lamp());
    }

    public List<Lamp> getLamps() {
        return lamps;
    }

    @Override
    public String toString() {
        return "Chandelier{" +
                "LAMP_CAPACITY=" + LAMP_CAPACITY +
                ", lamps=" + lamps +
                '}';
    }
}
