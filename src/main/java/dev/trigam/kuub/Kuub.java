package dev.trigam.kuub;

import dev.trigam.kuub.loader.Launcher;

public class Kuub {
    public static void main( String[] args ) throws Throwable {
        Launcher launcher = new Launcher();
        launcher.launchClient();
    }
}