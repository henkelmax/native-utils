package de.maxhenkel.nativeutils;

import java.io.IOException;

public class NativeInitializer {

    private static boolean loaded;
    private static Exception error;

    /**
     * Loads the native library.
     * If the library is already loaded, this method does nothing.
     * If the library could not be loaded, consecutive calls to this method will throw the same exception without reattempting to load the native library.
     *
     * @param libraryName the name of the native library
     * @throws UnknownPlatformException if the operating system is not supported
     * @throws IOException              if the native library could not be extracted
     */
    public static void load(String libraryName) throws UnknownPlatformException, IOException {
        if (loaded) {
            if (error != null) {
                if (error instanceof IOException) {
                    throw new IOException(error.getMessage());
                } else if (error instanceof UnknownPlatformException) {
                    throw new UnknownPlatformException(error.getMessage());
                }
                throw new RuntimeException(error.getMessage());
            }
            return;
        }
        try {
            LibraryLoader.load("opus4j");
            loaded = true;
        } catch (UnknownPlatformException | IOException e) {
            error = e;
            throw e;
        }
    }

    /**
     * Checks if the native library is loaded.
     *
     * @return true if the native library is loaded
     */
    public static boolean isLoaded() {
        return loaded && error == null;
    }

}
