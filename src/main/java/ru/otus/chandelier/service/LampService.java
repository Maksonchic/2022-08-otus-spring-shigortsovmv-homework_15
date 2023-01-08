package ru.otus.chandelier.service;

import org.springframework.stereotype.Service;
import ru.otus.chandelier.domain.Lamp;

@Service
public class LampService {
    public Lamp hit(Lamp lamp) {
        Lamp lamp1 = lamp.takeDamage((int) Math.round(Math.random() * 50));
        System.err.println("Lamp has health=" + lamp1.getHealth() + (lamp1.isNotBroken() ? "" : " (broken)"));
        return lamp1;
    }
}
