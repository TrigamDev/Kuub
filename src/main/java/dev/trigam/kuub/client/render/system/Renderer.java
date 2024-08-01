package dev.trigam.kuub.client.render.system;

import dev.trigam.kuub.client.render.element.Mesh;
import dev.trigam.kuub.client.render.system.shader.ShaderProgram;
import dev.trigam.kuub.client.render.system.window.Window;
import dev.trigam.kuub.resource.FileLoader;
import dev.trigam.kuub.resource.Identifier;
import dev.trigam.kuub.resource.ResourceType;

import static org.lwjgl.opengl.GL46.*;

import java.util.Optional;

public class Renderer {
    public ShaderProgram shaderProgram;
    public Window window;

    public Renderer (Window window) {
        this.window = window;
    }

    public void init() throws Throwable {
        // Fetch shaders
        Optional<String> vertexShader = FileLoader.loadTextBasedResource(
            ResourceType.ASSET, new Identifier( "shader/vertex/vertex.glsl" )
        );
        Optional<String> fragmentShader = FileLoader.loadTextBasedResource(
            ResourceType.ASSET, new Identifier( "shader/fragment/fragment.glsl" )
        );

        // Create shader program
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
    }

    public void render ( Mesh mesh ) {
        this.shaderProgram.bind();

        // Draw mesh
        glBindVertexArray( mesh.getVaoId() );
        glEnableVertexAttribArray( 0 );
        glDrawElements( GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0 );

        // Reset state
        glDisableVertexAttribArray( 0 );
        glBindVertexArray( 0 );

        this.shaderProgram.unbind();
    }

    public void cleanUp ( Mesh mesh ) {
        if ( this.shaderProgram != null ) this.shaderProgram.cleanUp();
        mesh.cleanUp();
    }

}
