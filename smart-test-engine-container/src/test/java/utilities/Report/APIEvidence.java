package utilities.Report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIEvidence {

    @JsonPropertyOrder({ "request", "response" })

    @JsonProperty("request")
    private RequestDetails request;

    @JsonProperty("response")
    private ResponseDetails response;

    public APIEvidence() {
    }

    public APIEvidence(RequestDetails request, ResponseDetails response) {
        this.request = request;
        this.response = response;
    }

    public RequestDetails getRequest() {
        return request;
    }

    public void setRequest(RequestDetails request) {
        this.request = request;
    }

    public ResponseDetails getResponse() {
        return response;
    }

    public void setResponse(ResponseDetails response) {
        this.response = response;
    }

    @JsonPropertyOrder({ "httpMethod", "baseUri", "header", "cookies", "body" })
    public static class RequestDetails {
        private String httpMethod;
        private String baseUri;
        private String header;
        private String cookies;
        private Object body;

        public RequestDetails() {
        }

        public RequestDetails(String httpMethod, String baseUri, String header, String cookies, String bodyAsString) {
            this.httpMethod = httpMethod;
            this.baseUri = baseUri;
            this.header = header;
            this.cookies = cookies;
            this.body = parseBody(bodyAsString);
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        public Object getBody() {
            return body;
        }

        public void setBody(String bodyAsString) {
            this.body = parseBody(bodyAsString);
        }

        public String getbaseUri() {
            return baseUri;
        }

        public void setbaseUri(String baseUri) {
            this.baseUri = baseUri;
        }

        public String getCookies() {
            return cookies;
        }

        public void setCookies(String cookies) {
            this.cookies = cookies;
        }
    }

    @JsonPropertyOrder({ "httpStatus", "headers", "body" })
    public static class ResponseDetails {
        private String httpStatus;
        private String headers;
        private Object body;

        public ResponseDetails() {
        }

        public ResponseDetails(String httpStatus, String header, String bodyAsString) {
            this.httpStatus = httpStatus;
            this.headers = header;
            this.body = parseBody(bodyAsString);
        }

        public String getHeader() {
            return headers;
        }

        public void setHeader(String headers) {
            this.headers = headers;
        }

        public String getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(String httpStatus) {
            this.httpStatus = httpStatus;
        }

        public Object getBody() {
            return body;
        }

        public void setBody(String bodyAsString) {
            this.body = parseBody(bodyAsString);
        }

    }

    private static Object parseBody(String bodyAsString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(bodyAsString);
            return jsonNode;
        } catch (JsonProcessingException e) {
            return bodyAsString;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"request\": {" +
                "\"httpMethod\": \"" + request.getHttpMethod() + "\"," +
                "\"baseUri\": \"" + request.getbaseUri() + "\"," +
                "\"headers\": \"" + request.getHeader() + "\"," +
                "\"cookies\": \"" + request.getCookies() + "\"," +
                "\"body\": \"" + request.getBody() + "\"" +
                "}," +
                "\"response\": {" +
                "\"httpStatus\": \"" + response.getHttpStatus() + "\"," +
                "\"headers\": \"" + response.getHeader() + "\"," +
                "\"body\": \"" + response.getBody() + "\"" +
                "}" +
                "}";
    }

}