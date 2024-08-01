package dev.trigam.kuub.client.render.system;

import dev.trigam.kuub.client.render.system.shader.ShaderProgram;
import dev.trigam.kuub.client.render.system.window.Window;
import dev.trigam.kuub.resource.FileLoader;
import dev.trigam.kuub.resource.Identifier;
import dev.trigam.kuub.resource.ResourceType;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;
import java.util.Optional;

public class Renderer {
    public ShaderProgram shaderProgram;
    public Window window;

    private int vaoId; // Vertex Array Object
    private int vboId; // Vertex Buffer Object

    private float[] vertices = {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
    };

    public Renderer (Window window) {
        this.window = window;
    }

    public void init() throws Throwable {
        Optional<String> vertexShader = FileLoader.loadTextBasedResource(
            ResourceType.ASSET, new Identifier( "shader/vertex/vertex.glsl" )
        );
        Optional<String> fragmentShader = FileLoader.loadTextBasedResource(
            ResourceType.ASSET, new Identifier( "shader/fragment/fragment.glsl" )
        );
        try {
            this.shaderProgram = new ShaderProgram();
            // Create shaders
            if (vertexShader.isPresent() )
                this.shaderProgram.createVertexShader( vertexShader.get() );
            if (fragmentShader.isPresent() )
                this.shaderProgram.createFragmentShader( fragmentShader.get() );
            // Link
            this.shaderProgram.link();
        } catch (Exception e) { e.printStackTrace(); }

        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat( vertices.length );
        verticesBuffer.put( vertices ).flip();

        this.vaoId = glGenVertexArrays();
        glBindVertexArray( this.vaoId );

        this.vboId = glGenBuffers();
        glBindBuffer( GL_ARRAY_BUFFER, vboId );
        glBufferData( GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW );
        MemoryUtil.memFree( verticesBuffer );

        glVertexAttribPointer( 0, 3, GL_FLOAT, false, 0, 0 );

        glBindBuffer( GL_ARRAY_BUFFER, 0 );
        glBindVertexArray( 0 );
    }

    public void render () {
        this.shaderProgram.bind();

        glBindVertexArray( this.vaoId );
        glEnableVertexAttribArray( 0 );

        glDrawArrays( GL_TRIANGLES, 0, 3 );

        glDisableVertexAttribArray( 0 );
        glBindVertexArray( 0 );

        this.shaderProgram.unbind();
    }

    public void cleanUp () {
        if ( this.shaderProgram != null ) this.shaderProgram.cleanUp();
        glDisableVertexAttribArray( 0 );

        glBindBuffer( GL_ARRAY_BUFFER, 0 );
        glDeleteBuffers( this.vboId );

        glBindVertexArray( 0 );
        glDeleteBuffers( this.vaoId );
    }

}
