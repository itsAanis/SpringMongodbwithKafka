package com.example.microservice.Reader;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JsonReader {

    @Value("classpath:data/json-data.json")
    private Resource resourceFile;

    public String readJson() throws IOException {
        byte[] binaryData = FileCopyUtils.copyToByteArray(resourceFile.getInputStream());
        return new String(binaryData, StandardCharsets.UTF_8);
    }

    @Value("classpath:data/jsonataexpression.txt")
    private Resource expressionResource;
    public String readExpression() throws IOException {
        byte[] expressionData = FileCopyUtils.copyToByteArray(expressionResource.getInputStream());
        return new String(expressionData, StandardCharsets.UTF_8);
    }

}
