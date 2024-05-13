package com.example.microservice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/com.example",
        glue = "com.example.microservice",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class RunCucumberTest {
}
