package dev.trigam.kuub.loader;

import dev.trigam.kuub.client.Client;
import dev.trigam.kuub.version.GameVersion;

import java.io.FileNotFoundException;

public class Launcher {

    Client client;

    public void launchClient () throws Throwable {
        this.client = new Client();
        this.client.run();
    }

    public boolean isDevelopment () { return GameVersion.development; }

}
