package dev.trigam.kuub.client.render.shader;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Uniform {

    public String name;
    public int location;
    public int programId;

    public Uniform ( int programId, String name, int value ) {
        this.programId = programId; this.name = name;
        this.location = glGetUniformLocation( this.programId, this.name );

        glUniform1i( this.location, value );
    }

    public Uniform ( int programId, String name, Matrix4f value ) {
        this.programId = programId; this.name = name;
        this.location = glGetUniformLocation( this.programId, this.name );

        try (MemoryStack stack = MemoryStack.stackPush() ) {
            FloatBuffer buffer = stack.mallocFloat( 16 );
            value.get( buffer );
            glUniformMatrix4fv( this.location, false, buffer );
        }
    }

    public String getName () { return this.name; }
    public int getLocation () { return this.location; }

}
