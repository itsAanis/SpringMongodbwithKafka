package com.example.microservice.Service;

import com.example.microservice.Reader.JsonReader;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.dashjoin.jsonata.Jsonata.jsonata;

@Service
public class DataService {

   private JsonReader jsonReader;

   @Autowired
    public DataService(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    public Object evaluateJsonata() throws IOException {
        String jsonData = jsonReader.readJson();
        Gson gson = new Gson();
        var data = gson.fromJson(jsonData, Object.class);
        String expressionJson = jsonReader.readExpression();
        var expression = jsonata(expressionJson);
        var result = expression.evaluate(data);
        System.out.println(result);
        return result;
    }

}
