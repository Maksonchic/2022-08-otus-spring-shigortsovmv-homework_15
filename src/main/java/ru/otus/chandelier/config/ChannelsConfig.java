package ru.otus.chandelier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.chandelier.domain.Chandelier;
import ru.otus.chandelier.domain.Lamp;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ChannelsConfig {

    @Bean
    public QueueChannel lampChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public PublishSubscribeChannel chandelierChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel chandelierEmptyChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).get();
    }

    @Bean
    public IntegrationFlow lampFlow() {
        return IntegrationFlows
                .from("lampChannel")
                .split()
                .handle("lampService", "hit")
                .aggregate()
                .handle((GenericHandler<List<Lamp>>) (lamps, messageHeaders)
                        -> new Chandelier(lamps.stream().filter(Lamp::isNotBroken).collect(Collectors.toList())))
                .<Chandelier, Boolean>route(Chandelier::isFilled
                        , m -> m
                                .channelMapping(true, "chandelierChannel")
                                .channelMapping(false, "chandelierEmptyChannel"))
                .get();
    }

    @Bean
    public IntegrationFlow lampFlowBuyNewLamps() {
        return IntegrationFlows
                .from("chandelierEmptyChannel")
                .handle((GenericHandler<Chandelier>) (chandelier, messageHeaders) -> {
                    while(!chandelier.isFilled()) {
                        chandelier.addNewLamp();
                        System.err.println("bought new lamp");
                    }
                    return chandelier;
                })
                .channel("chandelierChannel")
                .get();
    }
}
