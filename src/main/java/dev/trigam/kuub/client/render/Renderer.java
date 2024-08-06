package dev.trigam.kuub.client.render;

import dev.trigam.kuub.client.render.element.Element;
import dev.trigam.kuub.client.render.element.transform.Transformation;

import dev.trigam.kuub.client.render.camera.Camera;
import dev.trigam.kuub.client.render.shader.ShaderProgram;
import dev.trigam.kuub.client.render.shader.Uniform;
import dev.trigam.kuub.client.render.window.Window;

import dev.trigam.kuub.resource.FileLoader;
import dev.trigam.kuub.resource.Identifier;
import dev.trigam.kuub.resource.ResourceType;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL46.*;

import java.util.Optional;

public class Renderer {
    public ShaderProgram shaderProgram;
    public Window window;
    public Camera camera;

    private Transformation transformation;

    public Renderer ( Window window ) {
        this.window = window;
        this.updateTransformation();
    }

    public void init() throws Throwable {
        // Config
        glEnable( GL_DEPTH_TEST );
        glEnable( GL_TEXTURE_2D );

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

    public void updateTransformation () {
        this.camera = this.window.getCamera();
        this.transformation = new Transformation( this.camera, this.window.getWidth(), this.window.getHeight() );
    }

    public void render ( Element[] elements ) throws Exception {
        this.updateTransformation();

        this.shaderProgram.bind();

        // Uniforms
        this.addUniform(
            this.shaderProgram, "projectionMatrix",
            this.transformation.getProjectionTransform().getProjectionMatrix()
        );
        this.addUniform( this.shaderProgram, "textureSampler", 0 );

        // Draw mesh
        for ( Element element : elements ) {
            // Set transformation
            this.transformation.updateWorldTransform(
                element.getPosition(), element.getRotation(), element.getScale()
            );
            this.addUniform( this.shaderProgram, "transformMatrix", this.transformation.getTransformMatrix() );

            // Render
            element.render();
        }

        // Reset state
        glDisableVertexAttribArray( 0 );
        glBindVertexArray( 0 );

        this.shaderProgram.unbind();
    }

    public Uniform addUniform ( ShaderProgram shaderProgram, String name, Matrix4f value ) {
        Uniform uniform = new Uniform( shaderProgram.getProgramId(), name, value );
        shaderProgram.addUniform( uniform );
        return uniform;
    }
    public Uniform addUniform ( ShaderProgram shaderProgram, String name, int value ) {
        Uniform uniform = new Uniform( shaderProgram.getProgramId(), name, value );
        shaderProgram.addUniform( uniform );
        return uniform;
    }

    public void cleanUp ( Element[] elements ) {
        if ( this.shaderProgram != null ) this.shaderProgram.cleanUp();
        for ( Element element : elements) {
            element.cleanUp();
        }
    }

}
