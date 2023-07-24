package com.nbk.news.news

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/cucumber/features"], glue = ["com.nbk.news.news"], plugin = ["pretty"])
class TestRunner {
}