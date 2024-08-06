package dev.trigam.kuub.client;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import dev.trigam.kuub.client.input.MouseListener;
import dev.trigam.kuub.client.render.element.Element;
import dev.trigam.kuub.client.render.element.Mesh;
import dev.trigam.kuub.client.render.element.MeshData;
import dev.trigam.kuub.client.render.camera.Camera;
import dev.trigam.kuub.client.render.scene.Scene;
import dev.trigam.kuub.client.render.window.DisplayMode;
import dev.trigam.kuub.client.render.window.DisplaySettings;
import dev.trigam.kuub.client.render.window.Window;
import dev.trigam.kuub.resource.Identifier;
import dev.trigam.kuub.resource.texture.Texture;
import dev.trigam.kuub.tick.Tick;
import dev.trigam.kuub.version.GameVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client extends GameLoop {
    public static final Logger log = LogManager.getLogger(Client.class);

    public Client() {
        super( 0, Tick.TickRate );
    }

    MouseListener mouseListener;

    Scene scene;
    Camera camera = new Camera().setFieldOfView( 60F );

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

        this.startInputListeners();

        this.scene = new Scene();

        float[] positions = new float[] {
            // V0
            -0.5f, 0.5f, 0.5f,
            // V1
            -0.5f, -0.5f, 0.5f,
            // V2
            0.5f, -0.5f, 0.5f,
            // V3
            0.5f, 0.5f, 0.5f,
            // V4
            -0.5f, 0.5f, -0.5f,
            // V5
            0.5f, 0.5f, -0.5f,
            // V6
            -0.5f, -0.5f, -0.5f,
            // V7
            0.5f, -0.5f, -0.5f,

            // For text coords in top face
            // V8: V4 repeated
            -0.5f, 0.5f, -0.5f,
            // V9: V5 repeated
            0.5f, 0.5f, -0.5f,
            // V10: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V11: V3 repeated
            0.5f, 0.5f, 0.5f,

            // For text coords in right face
            // V12: V3 repeated
            0.5f, 0.5f, 0.5f,
            // V13: V2 repeated
            0.5f, -0.5f, 0.5f,

            // For text coords in left face
            // V14: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V15: V1 repeated
            -0.5f, -0.5f, 0.5f,

            // For text coords in bottom face
            // V16: V6 repeated
            -0.5f, -0.5f, -0.5f,
            // V17: V7 repeated
            0.5f, -0.5f, -0.5f,
            // V18: V1 repeated
            -0.5f, -0.5f, 0.5f,
            // V19: V2 repeated
            0.5f, -0.5f, 0.5f,
        };
        int[] indices = new int[] {
            0, 1, 3, 3, 1, 2, // Front
            8, 10, 11, 9, 8, 11, // Top
            12, 13, 7, 5, 12, 7, // Right
            14, 15, 6, 4, 14, 6, // Left
            16, 18, 19, 17, 16, 19, // Bottom
            4, 6, 7, 5, 4, 7 // Back
        };
        float[] textureCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,

            // Top
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,

            // Right
            0.0f, 0.0f,
            0.0f, 0.5f,

            // Left
            0.5f, 0.0f,
            0.5f, 0.5f,

            // Bottom
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
        };

        Texture testTexture = new Texture( new Identifier( "texture/block/deepslate.png" ) );

        MeshData testCubeMeshData = new MeshData()
            .setVertices( positions ).setIndices( indices )
            .setTexture( testTexture ).setTextureCoords( textureCoords );
        Mesh testCubeMesh = new Mesh( testCubeMeshData );

        Element testCube = new Element( testCubeMesh );
        testCube.setZ( -3f );

        this.scene.addElement( 0, testCube );

        this.window.setScene( this.scene );
        this.window.setCamera( this.camera );

        start();
    }

    public void close () {
        this.running = false;

        this.stopInputListeners();
        this.window.close();

        System.exit( 0 );
    }

    int tickCount = 1;
    public void tick () {
        if ( this.window != null ) this.window.tick();

        float rotationX = (float) Math.sin( (double) tickCount / 40 ) / 2;
        float rotationY = (float) Math.cos( (double) tickCount / 70 ) / 2;
        this.camera.setPitch( rotationX );
        this.camera.setYaw( rotationY );
//        Element testCube = this.scene.getElement( 0 );
//
//        float rotation = (float) tickCount / 75;
//        if ( rotation > 360 ) rotation = 0;
//        testCube.setRotation( rotation, rotation, rotation );
//
//        this.scene.updateElement( tickCount, testCube );
        tickCount++;
    }

    public void render () throws Exception {
        if ( this.window != null ) this.window.render();
    }

    public void startInputListeners () {
        try {
            GlobalScreen.registerNativeHook();
        } catch ( NativeHookException exception ) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(exception.getMessage());
            System.exit( 1 );
        }

        this.mouseListener = new MouseListener();

        GlobalScreen.addNativeMouseListener( this.mouseListener );
        GlobalScreen.addNativeMouseMotionListener( this.mouseListener );
    }
    public void stopInputListeners () {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch ( NativeHookException exception ) {
            System.err.println("There was a problem unregistering the native hook.");
            System.err.println(exception.getMessage());
            System.exit( 1 );
        }

        GlobalScreen.removeNativeMouseListener( this.mouseListener );
        GlobalScreen.removeNativeMouseMotionListener( this.mouseListener );
    }
}
