package zeptodns.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Provides helper methods for reading text from files.
 */
public class FileUtils {
    /**
     * Reads the entire file at path to a string using the default system encoding.
     *
     * @param path Location of the file to read.
     * @return File from path as a string.
     * @throws IOException if an I/O error occurs reading from the stream
     */
    public static String readAll(String path) throws IOException {
        return readAll(path, Charset.defaultCharset());
    }

    /**
     * Reads the entire file at path using the specified charset encoding.
     *
     * @param path     Location of the file to read.
     * @param encoding Character encoding of the file.
     * @return File from path as a string.
     * @throws IOException if an I/O error occurs reading from the stream
     */
    public static String readAll(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
