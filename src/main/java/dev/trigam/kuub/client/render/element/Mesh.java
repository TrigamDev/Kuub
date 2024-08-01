package dev.trigam.kuub.client.render.element;

import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class Mesh {

    private final List<Integer> vboIdList; // Vertex buffer objects
    private final int vaoId; // Vertex array object
    private final int vertexCount;

    public Mesh ( float[] positions, int[] indices, float[] colors ) {
        try (MemoryStack stack = MemoryStack.stackPush() ) {
            this.vertexCount = indices.length;
            this.vboIdList = new ArrayList<>();

            this.vaoId = glGenVertexArrays();
            glBindVertexArray( this.vaoId );

            // Positions VBO
            int posVboId = glGenBuffers();
            this.vboIdList.add( posVboId );
            FloatBuffer positionsBuffer = stack.callocFloat( positions.length );
            positionsBuffer.put( 0, positions );
            glBindBuffer( GL_ARRAY_BUFFER, posVboId );
            glBufferData( GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW );
            glEnableVertexAttribArray( 0 );
            glVertexAttribPointer( 0, 3, GL_FLOAT, false, 0, 0 );

            // Color VBO
            int colVboId = glGenBuffers();
            vboIdList.add( colVboId );
            FloatBuffer colorsBuffer = stack.callocFloat( colors.length );
            colorsBuffer.put( 0, colors );
            glBindBuffer( GL_ARRAY_BUFFER, colVboId );
            glBufferData( GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW );
            glEnableVertexAttribArray( 1 );
            glVertexAttribPointer( 1, 3, GL_FLOAT, false, 0, 0 );

            // Index VBO
            int idxVboId = glGenBuffers();
            vboIdList.add( idxVboId );
            IntBuffer indicesBuffer = stack.callocInt( indices.length );
            indicesBuffer.put( 0, indices );
            glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, idxVboId );
            glBufferData( GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW );

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

}
