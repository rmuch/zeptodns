
var MessageBuilder = Java.type("zeptodns.protocol.fluent.MessageBuilder");

var response = [];

var responseBuilder;

responseBuilder = MessageBuilder
    .begin()
    .asResponse(query.getMessage());

function queryHandler(q) {
    if (q.getName() === "crm.example.com.") {
        responseBuilder
            .withARecord("crm.example.com.", "192.168.0.100");
    }
}

query.getMessage().getQuestions().forEach(queryHandler);

response = responseBuilder.end();