package dev.trigam.kuub.client.render.element;

import dev.trigam.kuub.resource.texture.Texture;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class Mesh {

    private final List<Integer> vboIdList; // Vertex buffer objects
    private final int vaoId; // Vertex array object
    private final int vertexCount;

    private Texture texture;

    public Mesh (MeshData meshData) {
        if (meshData.getTexture() != null) {
            this.texture = meshData.getTexture();
            glBindTexture( GL_TEXTURE_2D, this.texture.textureId );
        }

        try (MemoryStack stack = MemoryStack.stackPush() ) {
            this.vertexCount = meshData.getIndices().length;
            this.vboIdList = new ArrayList<>();

            this.vaoId = glGenVertexArrays();
            glBindVertexArray( this.vaoId );

            // Positions VBO
            int posVboId = glGenBuffers();
            this.vboIdList.add( posVboId );
            FloatBuffer positionsBuffer = stack.callocFloat( meshData.getVertices().length );
            positionsBuffer.put( 0, meshData.getVertices() );
            glBindBuffer( GL_ARRAY_BUFFER, posVboId );
            glBufferData( GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW );
            glEnableVertexAttribArray( 0 );
            glVertexAttribPointer( 0, 3, GL_FLOAT, false, 0, 0 );

            // Index VBO
            int idxVboId = glGenBuffers();
            vboIdList.add( idxVboId );
            IntBuffer indicesBuffer = stack.callocInt( meshData.getIndices().length );
            indicesBuffer.put( 0, meshData.getIndices() );
            glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, idxVboId );
            glBufferData( GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW );

            // Texture VBO
            if (meshData.getTexture() != null) {
                int texVboId = glGenBuffers();
                vboIdList.add( texVboId );
                FloatBuffer textureCoordsBuffer = MemoryUtil.memAllocFloat( meshData.getTextureCoords().length );
                textureCoordsBuffer.put( meshData.getTextureCoords() ).flip();
                glBindBuffer( GL_ARRAY_BUFFER, texVboId );
                glBufferData( GL_ARRAY_BUFFER, textureCoordsBuffer, GL_STATIC_DRAW );
                glVertexAttribPointer( 1, 2, GL_FLOAT, false, 0, 0 );
            }

            // Binding
            glBindBuffer( GL_ARRAY_BUFFER, 0 );
            glBindVertexArray( 0 );
        }
    }

    public void cleanUp () {
        vboIdList.forEach(GL46::glDeleteBuffers);
        glDeleteVertexArrays( this.vaoId );
    }

    public int getVaoId () { return this.vaoId; }
    public int getVertexCount () { return this.vertexCount; }

    public Texture getTexture () { return this.texture; }
    public void setTexture ( Texture texture ) { this.texture = texture; }

}
