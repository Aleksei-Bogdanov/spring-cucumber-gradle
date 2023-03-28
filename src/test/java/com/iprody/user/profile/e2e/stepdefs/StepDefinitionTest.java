package com.iprody.user.profile.e2e.stepdefs;

import com.iprody.user.profile.e2e.stepdefs.http.HeaderSettingRequestCallback;
import com.iprody.user.profile.e2e.stepdefs.http.ResponseResultErrorHandler;
import com.iprody.user.profile.e2e.stepdefs.http.ResponseResults;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefinitionTest {

    static ResponseResults latestResponse = null;

    private final RestTemplate restTemplate = new RestTemplate();

    @When("^the client calls /users$")
    public void the_client_issues_POST_user() throws Throwable {
        String urlPost = "http://localhost:8082/users";

        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);

        latestResponse = restTemplate.execute(urlPost, HttpMethod.POST, requestCallback, response -> {
                    if (errorHandler.getHadError()) {
                        return (errorHandler.getResults());
                    } else {
                        return (new ResponseResults(response));
                    }
                });
    }

    @Given("^the client calls /userProfile$")
    public void the_client_issues_GET_user_profile() throws Throwable {
        String urlGet = "http://localhost:8082/userProfile";

        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);

        latestResponse = restTemplate.execute(urlGet, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.getHadError()) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
    }

    @When("^the client calls /version$")
    public void the_client_issues_GET_version() throws Throwable {
        String urlGet = "http://localhost:8082/version";

        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);

        latestResponse = restTemplate.execute(urlGet, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.getHadError()) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });

    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = (HttpStatus) latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version_body(String version) throws Throwable {
        assertThat(latestResponse.getBody(), is(version));
    }
}