package dev.trigam.kuub.client.render.system.shader;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Uniform {

    public String name;
    public int location;
    public int programId;

    public Uniform(int programId, String name, Matrix4f value ) {
        this.programId = programId; this.name = name;

        this.location = glGetUniformLocation( this.programId, this.name );
        //if ( this.location < 0 ) throw new Exception( String.format( "Could not find uniform [%s] in shader program [%d]", name, this.programId ) );

        try (MemoryStack stack = MemoryStack.stackPush() ) {
            FloatBuffer buffer = stack.mallocFloat( 16 );
            value.get( buffer );
            glUniformMatrix4fv( this.location, false, buffer );
        }
    }

    public String getName () { return this.name; }
    public int getLocation () { return this.location; }

}
