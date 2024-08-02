package dev.trigam.kuub.client;

import dev.trigam.kuub.client.render.element.Element;
import dev.trigam.kuub.client.render.element.Mesh;
import dev.trigam.kuub.client.render.system.scene.Scene;
import dev.trigam.kuub.client.render.system.window.DisplayMode;
import dev.trigam.kuub.client.render.system.window.DisplaySettings;
import dev.trigam.kuub.client.render.system.window.Window;
import dev.trigam.kuub.tick.Tick;
import dev.trigam.kuub.version.GameVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Client extends GameLoop {
    public static final Logger log = LogManager.getLogger(Client.class);

    public Client() {
        super( 0, Tick.TickRate );
    }

    Scene scene;

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

        this.scene = new Scene();

        float[] positions = new float[] {
            -0.5f,  0.5f,  0.5f, // V1
            -0.5f, -0.5f,  0.5f, // V2
             0.5f, -0.5f,  0.5f, // V3
             0.5f,  0.5f,  0.5f, // V4
            -0.5f,  0.5f, -0.5f, // V5
             0.5f,  0.5f, -0.5f, // V6
            -0.5f, -0.5f, -0.5f, // V7
             0.5f, -0.5f, -0.5f, // V8
        };
        int[] indices = new int[] {
            0, 1, 3, 3, 1, 2, // Front
            4, 0, 3, 5, 4, 3, // Top
            3, 2, 7, 5, 3, 7, // Right
            6, 1, 0, 6, 0, 4, // Left
            2, 1, 6, 2, 6, 7, // Bottom
            7, 6, 4, 7, 4, 5, // Back
        };
        float[] colors = new float[]{
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
        };

        Mesh testCubeMesh = new Mesh( positions, indices, colors );
        Element testCube = new Element( testCubeMesh );
        testCube.setX( -6f );
        testCube.setZ( -5f );

        this.scene.addElement( 0, testCube );

        this.window.setScene( this.scene );

        start();
    }

    int tickCount = 1;
    public void tick () {
        if ( this.window != null ) this.window.tick();

        Element testCube = this.scene.getElement( tickCount - 1 ).copy();

        float rotation = (float) tickCount / 75;
        if ( rotation > 360 ) rotation = 0;
        testCube.setRotation( 0, rotation, 0 );

        testCube.setX( testCube.getPosition().x + 0.005f );
        testCube.setY((float) Math.sin( tickCount / 5f ));

        this.scene.addElement( tickCount, testCube );
        tickCount++;
//        System.out.println( this.scene.getElements().size() );
    }

    public void render () throws Exception {
        if ( this.window != null ) this.window.render();
    }
}
