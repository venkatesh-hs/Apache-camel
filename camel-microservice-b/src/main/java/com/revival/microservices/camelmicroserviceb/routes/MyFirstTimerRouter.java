package com.revival.microservices.camelmicroserviceb.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component // to disable route
public class MyFirstTimerRouter extends RouteBuilder {

    //@Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    //@Autowired
    private SimpleLoggingComponent loggingComponent;

    @Override
    public void configure() throws Exception {
        //Queue : Timer
        //transform
        //Database : log
        from("timer:first-timer") //null
                .log("${body}")
                //.transform().constant("Time now is : " + LocalDate.now())
                //.bean("getCurrentTimeBean") // using bean name
                .bean(getCurrentTimeBean, "getCurrentTime") // using dependency Injection
                //2024-04-21T13:51:12.526+05:30  INFO 24280 --- [camel-microservice-b] [r://first-timer] first-timer : Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time now is : 2024-04-21]
                .log("${body}")
                .bean(loggingComponent, "process") //processing body without changing the body
                .log("${body}")
                .process(new SimpleLoggingProcessor()) // processing by using camel Process interface
                .to("log:first-timer");
    }
}

@Component
class GetCurrentTimeBean {

    public String getCurrentTime() {
        return "Time now is : " + LocalDateTime.now();
    }
}

@Component
@Slf4j
class SimpleLoggingComponent {

    public void process(String message) {
        log.info("Simple Logging : {}", message);
    }
}

@Slf4j
class SimpleLoggingProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Simple Logging using camel processor : {}", exchange.getMessage().getBody());
    }
}
