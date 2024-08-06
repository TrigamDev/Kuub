package dev.trigam.kuub.client.render.element;

import dev.trigam.kuub.resource.texture.Texture;

public class MeshData {

    // Vertex data
    private float[] vertices;
    private int[] indices;

    // Material data
    private Texture texture;
    private float[] textureCoords;
    private float[] normals;

    // Bone data
    private int[] boneIndices;
    private float[] weights;

    public MeshData( ) {}

    public MeshData setVertices ( float[] vertices ) { this.vertices = vertices; return this; }
    public float[] getVertices () { return this.vertices; }

    public MeshData setIndices ( int[] indices ) { this.indices = indices; return this; }
    public int[] getIndices () { return this.indices; }

    public MeshData setTexture ( Texture texture ) { this.texture = texture; return this; }
    public Texture getTexture () { return this.texture; }

    public MeshData setTextureCoords ( float[] textureCoords ) { this.textureCoords = textureCoords; return this; }
    public float[] getTextureCoords () { return this.textureCoords; }

}
