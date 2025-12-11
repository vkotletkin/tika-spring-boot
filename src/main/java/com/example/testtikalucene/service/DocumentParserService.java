package com.example.testtikalucene.service;

import com.example.testtikalucene.dto.DetectionResult;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentParserService {

    private static final int BODY_LENGTH = -1;

    private final LanguageDetector languageDetector;
    private final Parser parser;

    public DetectionResult parse(MultipartFile file) {

        ContentHandler handler = new BodyContentHandler(BODY_LENGTH);
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        try (InputStream is = file.getInputStream()) {
            parser.parse(is, handler, metadata, context);

            String text = handler.toString();

            Map<String, String[]> metadataRes = new HashMap<>();
            String[] metadataNames = metadata.names();
            for (String metadataElement : metadataNames) {
                metadataRes.put(metadataElement, metadata.getValues(metadataElement));
            }

            // Детекция языка
            LanguageResult languageResult;
            synchronized (languageDetector) {
                languageDetector.reset();
                languageDetector.addText(text.replace("\n", " "));
                languageResult = languageDetector.detect();
                languageDetector.reset();
            }

            return DetectionResult.builder().text(text).metadata(metadataRes).languageResult(languageResult).build();
        } catch (TikaException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }


}
