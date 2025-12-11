package com.example.testtikalucene.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.metadata.Metadata;

import java.util.Map;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetectionResult {
    String text;
    Map<String, String[]> metadata;
    LanguageResult languageResult;
}
