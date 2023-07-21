package com.redhat;

public class KafkaMessage {
    
    public static class Request {
        public static class RawRequestHeader {
            public String accept;
        }

        public RawRequestHeader rawRequestHeader = new RawRequestHeader();
    }

    public static class Response {
        public static class RawResponseBody {
            public String payload;
        }

        public RawResponseBody rawResponseBody = new RawResponseBody();
    }

    public Request request = new Request();
    public Response response = new Response();
}