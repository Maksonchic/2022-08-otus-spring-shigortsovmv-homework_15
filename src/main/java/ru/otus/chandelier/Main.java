package ru.otus.chandelier;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.chandelier.domain.Chandelier;
import ru.otus.chandelier.domain.Lamp;
import ru.otus.chandelier.messages.ElectricMan;

import java.util.ArrayList;
import java.util.List;

@ComponentScan
@IntegrationComponentScan
@EnableIntegration
public class Main {

    private static boolean itWorks = true;

    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        ElectricMan electricMan = ctx.getBean(ElectricMan.class);

        // Если не заполнить, не выполняется handle между split и aggregate
        // Пока не понял как быть, люстра-то гореть должна)
        List<Lamp> lamps = new ArrayList<Lamp>() {{
            add(new Lamp(2));
            add(new Lamp());
        }};

        while (true) {
            Thread.sleep(1000);
            Chandelier chandelier = electricMan.fillChandelier(lamps);
            lamps = chandelier.getLamps();
            System.err.println(chandelier);
        }
    }
}