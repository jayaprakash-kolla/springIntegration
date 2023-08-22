package com.springboot.springintegration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

import java.io.File;

@Configuration      // for creating beans.
@EnableIntegration  // for enabling integration.
public class Integration {

    @Bean
    @InboundChannelAdapter(value="fileInputChannel",poller = @Poller(fixedDelay = "5000"))
    public FileReadingMessageSource fileReadingMessageSource(){

        FileReadingMessageSource reader = new FileReadingMessageSource();

        CompositeFileListFilter<File> filter = new CompositeFileListFilter<>();
        filter.addFilter(new SimplePatternFileListFilter("*.png"));

        reader.setDirectory(new File("C:\\Users\\Prakash\\Desktop\\source"));
        reader.setFilter(filter);

        return reader;

    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel")
    public FileWritingMessageHandler fileWritingMessageHandler(){

        FileWritingMessageHandler writer = new FileWritingMessageHandler(new File("C:\\Users\\Prakash\\Desktop\\destination"));

        writer.setAutoCreateDirectory(true);
        writer.setExpectReply(false);

        return writer;

    }


}
