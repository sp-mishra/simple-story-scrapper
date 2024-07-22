package org.groklabs.simplestoryscrapper.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ScrapScheduler {

    //    @Scheduled(fixedDelayString = "${groklabs.task.fixedDelay}") // Use fixedDelayString for expression support
    public void executeTask() {
        log.info("Executing task at {}", LocalDateTime.now());
    }
}