package dev.trigam.kuub.client.render.system.window;

import dev.trigam.kuub.color.Color;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    public long window;

    public DisplaySettings displaySettings;
    public long refreshRate;

    public Window ( DisplaySettings displaySettings ) {
        this.displaySettings = displaySettings;
    }


    public void open () {
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

        // Background color
        Color trigamBlue = Color.fromHex("#3f48cc");
        glClearColor(
            Color.floatFromChannel( trigamBlue.red ),
            Color.floatFromChannel( trigamBlue.green ),
            Color.floatFromChannel( trigamBlue.blue ),
            Color.floatFromChannel( trigamBlue.alpha )
        );
    }

    public void render () {
        glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
        glfwSwapBuffers( this.window );
        glfwPollEvents();
    }
}
