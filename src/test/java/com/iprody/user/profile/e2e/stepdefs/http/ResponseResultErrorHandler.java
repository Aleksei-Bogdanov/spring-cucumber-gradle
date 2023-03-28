package com.iprody.user.profile.e2e.stepdefs.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
public class ResponseResultErrorHandler implements ResponseErrorHandler {
    private ResponseResults results = null;
    private Boolean hadError = false;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        hadError = response.getStatusCode().value() >= 400;
        return hadError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        results = new ResponseResults(response);
    }

    public ResponseResults getResults() {
        return results;
    }

    public Boolean getHadError() {
        return hadError;
    }
}
