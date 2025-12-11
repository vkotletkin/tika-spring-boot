package com.example.testtikalucene.config;

import org.apache.tika.langdetect.opennlp.OpenNLPDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class TikaConfig {

    @Bean
    public LanguageDetector languageDetector() throws IOException {
        LanguageDetector detector = new OpenNLPDetector();
        detector.loadModels();
        return detector;
    }

    @Bean
    public Parser parser() {
        return new AutoDetectParser();
    }
}