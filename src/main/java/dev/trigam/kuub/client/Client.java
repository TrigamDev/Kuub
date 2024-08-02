package dev.trigam.kuub.client;

import dev.trigam.kuub.client.render.system.window.DisplayMode;
import dev.trigam.kuub.client.render.system.window.DisplaySettings;
import dev.trigam.kuub.client.render.system.window.Window;
import dev.trigam.kuub.tick.Tick;
import dev.trigam.kuub.version.GameVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client extends GameLoop {
    public static final Logger log = LogManager.getLogger(Client.class);

    public Client() {
        super( 0, Tick.TickRate );
    }

    public void run () throws Throwable {
        // Open window
        String title = String.format( "Kuub | %s %o.%o", GameVersion.phase, GameVersion.major, GameVersion.minor );
        log.info(title);
        DisplaySettings displaySettings = new DisplaySettings(
            1920, 1080, title,
            DisplayMode.WINDOWED, 0
        );
        this.window = new Window( displaySettings );
        this.window.open();

        start();
    }

    public void tick () {
        if ( this.window != null ) this.window.tick();
    }

    public void render () throws Exception {
        if ( this.window != null ) this.window.render();
    }
}
