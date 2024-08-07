package dev.trigam.kuub.client.render.window;

import dev.trigam.kuub.client.render.element.Element;
import dev.trigam.kuub.client.render.Renderer;
import dev.trigam.kuub.client.render.camera.Camera;
import dev.trigam.kuub.client.render.scene.Scene;
import dev.trigam.kuub.color.Color;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    public long window;
    public Renderer renderer;

    int x; int y; int width; int height;

    public DisplaySettings displaySettings;
    public long refreshRate;

    private Scene scene;
    private Camera camera;

    public Window ( DisplaySettings displaySettings ) {
        this.displaySettings = displaySettings;
    }

    public void open () throws Throwable {
        if ( !glfwInit() ) throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint( GLFW_VISIBLE, GLFW_FALSE );
        glfwWindowHint( GLFW_RESIZABLE, GLFW_TRUE );

        this.width = this.displaySettings.width; this.height = this.displaySettings.height;

        // Create window
        this.window = glfwCreateWindow( this.width, this.height, this.displaySettings.title, NULL, NULL );
        if ( this.window == NULL ) throw new RuntimeException("Failed to create the GLFW window");

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            // Get window size
            glfwGetWindowSize( this.window, pWidth, pHeight );

            // Get resolution of primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode( glfwGetPrimaryMonitor() );
            assert vidMode != null;

            this.refreshRate = vidMode.refreshRate();

            if ( this.displaySettings.centerWindow ) {
                this.x = ( vidMode.width() - pWidth.get( 0 ) ) / 2;
                this.y = ( vidMode.height() - pHeight.get( 0 ) ) / 2;
            } else { this.x = this.displaySettings.x; this.y = this.displaySettings.y; }

            glfwSetWindowPos( window, this.x, this.y );

            glfwMakeContextCurrent( this.window );
            glfwSwapInterval(1);

            glfwShowWindow( this.window );
        }

        GL.createCapabilities();

        this.scene = new Scene();
        this.camera = new Camera();

        // Renderer
        this.renderer = new Renderer( this );
        this.renderer.init();

        // Background color
        Color backgroundColor = Color.fromHex("#1B1725");
        glClearColor(
            Color.floatFromChannel( backgroundColor.red ),
            Color.floatFromChannel( backgroundColor.green ),
            Color.floatFromChannel( backgroundColor.blue ),
            Color.floatFromChannel( backgroundColor.alpha )
        );
    }

    public void render () throws Exception {
        glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

        List< Element > sceneElements = this.scene.getElements();
        this.renderer.render( sceneElements.toArray( Element[]::new ) );

        glfwSwapBuffers( this.window );
        glfwPollEvents();
    }

    public void tick () {  }

    public void close () {
        List< Element > sceneElements = this.scene.getElements();
        this.renderer.cleanUp( sceneElements.toArray( Element[]::new ) );
    }

    public int getWidth () { return this.width; }
    public int getHeight () { return this.height; }

    public int getX () { return this.x; }
    public int getY () { return this.y; }

    public void setScene ( Scene scene ) { this.scene = scene; }
    public void setCamera ( Camera camera ) { this.camera = camera; }

    public Scene getScene () { return this.scene; }
    public Camera getCamera () { return this.camera; }
}
