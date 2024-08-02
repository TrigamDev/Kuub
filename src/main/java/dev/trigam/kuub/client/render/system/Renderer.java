package dev.trigam.kuub.client.render.system;

import dev.trigam.kuub.client.render.element.Element;
import dev.trigam.kuub.client.render.element.transform.Transformation;
import dev.trigam.kuub.client.render.element.transform.World;

import dev.trigam.kuub.client.render.system.shader.ShaderProgram;
import dev.trigam.kuub.client.render.system.shader.Uniform;
import dev.trigam.kuub.client.render.system.window.Window;

import dev.trigam.kuub.resource.FileLoader;
import dev.trigam.kuub.resource.Identifier;
import dev.trigam.kuub.resource.ResourceType;

import static org.lwjgl.opengl.GL46.*;

import java.util.Optional;

public class Renderer {
    public ShaderProgram shaderProgram;
    public Window window;

    private Transformation transformation;

    public Renderer (Window window) {
        this.window = window;
        this.transformation = new Transformation( this.window.getWidth(), this.window.getHeight() );
    }

    public void init() throws Throwable {
        // Config
        glEnable( GL_DEPTH_TEST );

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

    public void render ( Element[] elements ) throws Exception {
        this.shaderProgram.bind();

        // Transformation matrices
        Uniform projectionMatrixUniform = new Uniform(
            this.shaderProgram.getProgramId(),
            "projectionMatrix", this.transformation.getProjectionTransform().getProjectionMatrix()
        );
        this.shaderProgram.addUniform( projectionMatrixUniform );

        // Draw mesh
        for ( Element element : elements ) {
            // Set transformation
            World worldTransform = Transformation.createWorldTransform(
                element.getPosition(), element.getRotation(), element.getScale()
            );
            Uniform worldMatrixUniform = new Uniform(
                this.shaderProgram.getProgramId(),
                "worldMatrix", worldTransform.getWorldMatrix()
            );
            this.shaderProgram.addUniform( worldMatrixUniform );

            // Render
            element.render();
        }

        // Reset state
        glDisableVertexAttribArray( 0 );
        glBindVertexArray( 0 );

        this.shaderProgram.unbind();
    }

    public void cleanUp ( Element[] elements ) {
        if ( this.shaderProgram != null ) this.shaderProgram.cleanUp();
        for ( Element element : elements) {
            element.cleanUp();
        }
    }

}
