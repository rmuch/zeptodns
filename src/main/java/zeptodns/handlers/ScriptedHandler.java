package zeptodns.handlers;

import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.Query;
import zeptodns.protocol.messages.Response;
import zeptodns.util.FileUtils;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

/**
 * Passes queries to any scripting language supported by the JSR 223 (Scripting for the Java Platform) API.
 */
public class ScriptedHandler implements QueryHandler {
    private final ScriptEngine scriptEngine;
    private final String script;

    public ScriptedHandler(String path, String engineName) throws IOException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName(engineName);

        script = FileUtils.readAll(path);
    }

    @Override
    public Response handle(Query query) {
        Bindings bindings = scriptEngine.createBindings();
        bindings.put("query", query);

        try {
            scriptEngine.eval(script, bindings);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return new Response((Message) bindings.get("response"));
    }
}
