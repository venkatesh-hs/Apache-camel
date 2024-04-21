package com.revival.microservices.camelmicroserviceb.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSender extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //timer
        from("timer:active-mq-timer?period=10000")
                .transform().constant("My message for active mq")
                .log("${body}")
                .to("activemq:my-active-mq");
        //activemq
    }
}
