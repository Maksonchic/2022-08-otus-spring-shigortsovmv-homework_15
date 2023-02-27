package ru.otus.chandelier.messages;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.chandelier.domain.Chandelier;
import ru.otus.chandelier.domain.Lamp;

import java.util.List;

@MessagingGateway
public interface ElectricMan {

    @Gateway(requestChannel = "lampChannel", replyChannel = "chandelierChannel")
    Chandelier fillChandelier(List<Lamp> lamps);

}
