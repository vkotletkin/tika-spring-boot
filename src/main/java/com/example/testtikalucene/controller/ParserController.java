package com.example.testtikalucene.controller;

import com.example.testtikalucene.dto.DetectionResult;
import com.example.testtikalucene.service.DocumentParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ParserController {

    private final DocumentParserService documentParserService;

    @PostMapping(
            value = "/parse",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DetectionResult parseDocument(@RequestPart("file") MultipartFile file) {
        return documentParserService.parse(file);
    }
}
