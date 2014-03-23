package zeptodns.handlers;

import zeptodns.protocol.messages.Query;
import zeptodns.protocol.messages.Response;
import zeptodns.util.FileUtils;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Handles queries using a Java Script Engine script.
 */
public class ScriptedHandler implements QueryHandler {
    private final ScriptEngineManager scriptEngineManager;
    private final ScriptEngine scriptEngine;
    private final String script;

    public ScriptedHandler(String path, String engineName) throws IOException {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName(engineName);

        script = FileUtils.readAll(path, Charset.defaultCharset());
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

        return null;
    }
}
