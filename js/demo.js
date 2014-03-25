
// We can use the Nashorn method Java.type() to import a Java type.
var MessageBuilder = Java.type("zeptodns.protocol.fluent.MessageBuilder");

var response = MessageBuilder
    .begin()
    .asResponse(query.getMessage())
    .authoritative(true)
    .withARecord(query.getMessage().getQuestions().get(0).getName(), "192.168.13.37")
    .withARecord(query.getMessage().getQuestions().get(0).getName(), "192.168.13.38")
    .end();
