package zeptodns.handlers;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import zeptodns.protocol.fluent.MessageBuilder;
import zeptodns.protocol.messages.Message;
import zeptodns.protocol.messages.Query;
import zeptodns.protocol.messages.Response;
import zeptodns.util.FileUtils;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.io.IOException;

/**
 * Handles queries using a Nashorn script.
 */
public class NashornHandler implements QueryHandler {
    private final NashornScriptEngine nashornEngine;
    private final String scriptSource;
    private final CompiledScript compiledScript;

    public NashornHandler(String path) throws IOException, ScriptException {
        NashornScriptEngineFactory nashornEngineFactory = new NashornScriptEngineFactory();
        nashornEngine = (NashornScriptEngine) nashornEngineFactory.getScriptEngine();

        scriptSource = FileUtils.readAll(path);

        compiledScript=nashornEngine.compile(scriptSource);
    }

    @Override
    public Response handle(Query query) {
        Bindings bindings = nashornEngine.createBindings();
        bindings.put("query", query);

        try {
            compiledScript.eval(bindings);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return new Response((Message) bindings.get("response"));
    }
}
