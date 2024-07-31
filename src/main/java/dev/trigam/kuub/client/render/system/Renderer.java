package dev.trigam.kuub.client.render.system;

import dev.trigam.kuub.client.render.system.shader.ShaderProgram;
import dev.trigam.kuub.client.render.system.window.Window;
import dev.trigam.kuub.resource.FileLoader;
import dev.trigam.kuub.resource.Identifier;
import dev.trigam.kuub.resource.ResourceType;

import java.net.URISyntaxException;
import java.util.Optional;

public class Renderer {
    public ShaderProgram shaderProgram;
    public Window window;

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
    }

    public void render () {
        this.window.render();
    }

}
