package com.revival.microservices.camelmicroservicea.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReceiver extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //active mq
        from("activemq:my-active-mq")
                .to("log:received-from-active-mq");
        //log
    }
}
