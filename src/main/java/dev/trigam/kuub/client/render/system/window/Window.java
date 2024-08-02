package dev.trigam.kuub.client.render.system.window;

import dev.trigam.kuub.client.render.element.Element;
import dev.trigam.kuub.client.render.element.Mesh;
import dev.trigam.kuub.client.render.system.Renderer;
import dev.trigam.kuub.color.Color;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    public long window;
    public Renderer renderer;

    public DisplaySettings displaySettings;
    public long refreshRate;

    private Element[] testElements;

    public Window ( DisplaySettings displaySettings ) {
        this.displaySettings = displaySettings;
    }

    public void open () throws Throwable {
        if ( !glfwInit() ) throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint( GLFW_VISIBLE, GLFW_FALSE );
        glfwWindowHint( GLFW_RESIZABLE, GLFW_TRUE );

        // Create window
        this.window = glfwCreateWindow( this.displaySettings.width, this.displaySettings.height, this.displaySettings.title, NULL, NULL );
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

            // If centerWindow, get monitor center, otherwise use specified position
            int windowX = this.displaySettings.centerWindow ? ( vidMode.width() - pWidth.get(0) ) / 2 : this.displaySettings.x;
            int windowY = this.displaySettings.centerWindow ? ( vidMode.height() - pHeight.get(0) ) / 2 : this.displaySettings.y;
            glfwSetWindowPos( window, windowX, windowY );

            glfwMakeContextCurrent( this.window );
            glfwSwapInterval(1);

            glfwShowWindow( this.window );
        }

        GL.createCapabilities();

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

        // Test mesh
        float[] positions = new float[]{
            -0.5f,  0.5f, -1.05f,
            -0.5f, -0.5f, -1.05f,
            0.5f, -0.5f, -1.05f,
            0.5f,  0.5f, -1.05f,
        };
        int[] indices = new int[]{
            0, 1, 3, 3, 1, 2,
        };
        float[] colors = new float[]{
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
        };
        Mesh testMesh = new Mesh( positions, indices, colors );
        Element testElement = new Element( testMesh );
        //testElement.setX(0.5f);
        testElement.setZ(-0.5f);
        //testElement.setRotationY(this.testRot);
        this.testElements = new Element[]{ testElement };
    }

    public void render () throws Exception {
        glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );


        this.renderer.render( this.testElements );

        glfwSwapBuffers( this.window );
        glfwPollEvents();
    }

    int tickCount = 0;
    public void tick () {
        float testRot = (float) Math.sin((double) tickCount / 25) * 1.35f;
        this.testElements[0].setRotationY(testRot);
        tickCount++;
    }

    public void close() {
        this.renderer.cleanUp( this.testElements );
    }

    public int getWidth() {
        return this.displaySettings.width;
    }
    public int getHeight() {
        return this.displaySettings.height;
    }
}
