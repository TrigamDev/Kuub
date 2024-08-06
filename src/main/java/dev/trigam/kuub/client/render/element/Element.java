package dev.trigam.kuub.client.render.element;

import dev.trigam.kuub.resource.texture.Texture;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Element {

    private final Mesh mesh;

    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;

    public Element ( Mesh mesh ) {
        this.mesh = mesh;

        this.position = new Vector3f( 0, 0, 0 );
        this.rotation = new Vector3f( 0, 0, 0 );
        this.scale = 1;
    }

    public void render () {
        glBindVertexArray( mesh.getVaoId() );
        glEnableVertexAttribArray( 0 );
        glEnableVertexAttribArray( 1 );

        // Texture
        Texture elementTexture = this.getMesh().getTexture();
        glActiveTexture( GL_TEXTURE0 );
        elementTexture.bind();

        glDrawElements( GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0 );
    }

    public void cleanUp () {
        this.mesh.cleanUp();
    }

    public void setPosition ( float x, float y, float z ) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setX ( float x ) { this.position.x = x; }
    public void setY ( float y ) { this.position.y = y; }
    public void setZ ( float z ) { this.position.z = z; }

    public void setRotation ( float x, float y, float z ) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void setRotationX ( float x ) { this.rotation.x = x; }
    public void setRotationY ( float y ) { this.rotation.y = y; }
    public void setRotationZ ( float z ) { this.rotation.z = z; }

    public void setScale ( float scale ) { this.scale = scale; }

    public Mesh getMesh () { return this.mesh; }
    public Vector3f getPosition () { return this.position; }
    public Vector3f getRotation () { return this.rotation; }
    public float getScale () { return this.scale; }

    public Element copy () {
        Element faker = new Element( this.mesh );
        faker.setPosition( this.position.x, this.position.y, this.position.z );
        faker.setRotation( this.rotation.x, this.rotation.y, this.rotation.z );
        faker.setScale( this.scale );

        return faker;
    }

}
