package com.lcv.elverapi.apis;

import com.lcv.elverapi.util.ElverHttpRequest;

import java.net.http.HttpResponse;

public abstract class Api extends ApiReader {

    protected int httpTries;

    protected HttpResponse<String> response;

    protected boolean noData;

    public abstract String getApiEndpoint();

    public boolean hasData() {
        return !noData;
    }

    public HttpResponse<String> getResponse() {
        return response;
    }

    public void doHttp() {
        if (httpTries++ > 3) {
            throw new RuntimeException("Too http attempts");
        }

        try {
            response = ElverHttpRequest.makeRequest(getApiEndpoint(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int status = response.statusCode();
        int generalStatus = status/100;

        // TODO: more stuff? and maybe actually handle these somehow. also maybe move this into the http request class
        switch(generalStatus) {
            case 4 -> {
                noData = true;

                switch (status) {
                    case 400 -> throw new RuntimeException("Couldn't access api: Bad Request");
                    case 401 -> throw new RuntimeException("Couldn't access api: Unauthorized");
                    case 402 -> throw new RuntimeException("Couldn't access api: Payment Required");
                    case 403 -> throw new RuntimeException("Couldn't access api: Forbidden");
                    case 404 -> throw new RuntimeException("Couldn't access api: Not Found");
                    case 429 -> throw new RuntimeException("Couldn't access api: Too Many Requests");
                }
            }

            case 5 -> {
                switch(status) {
                    case 500 -> throw new RuntimeException("Couldn't access api: Internal Server Error");
                    case 501 -> throw new RuntimeException("Couldn't access api: Not Implemented");
                    case 502 -> throw new RuntimeException("Couldn't access api: Bad Gateway");
                    case 503 -> throw new RuntimeException("Couldn't access api: Service Unavailable");
                }
            }
        }

        if (generalStatus != 2) {
            throw new RuntimeException("Couldn't access api: ?");
        }
    }
}
