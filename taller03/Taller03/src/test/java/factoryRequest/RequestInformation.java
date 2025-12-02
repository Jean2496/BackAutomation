package factoryRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestInformation {

    private String url;
    private String body;
    private Map<String,String> headers;

    public RequestInformation(){
        headers = new HashMap<>();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestInformation setHeader(String key, String value) {
        this.headers.put(key,value);
        return this;
    }

    public String getBody() {
        return body;
    }

    public RequestInformation setBody(String body) {
        this.body = body;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestInformation setUrl(String url) {
        this.url = url;
        return this;
    }
}
