package dev.trigam.kuub.client.render.shader;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL46.*;

public class ShaderProgram {

    private final int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    private final Map< String, Integer > uniforms;

    public ShaderProgram () throws Exception {
        programId = glCreateProgram();
        if ( programId == 0 ) throw new Exception( "Couldn't create Shader" );

        uniforms = new HashMap<>();
    }

    public void createVertexShader ( String shaderCode ) throws Exception {
        vertexShaderId = createShader( shaderCode, GL_VERTEX_SHADER );
    }

    public void createFragmentShader ( String shaderCode ) throws Exception {
        fragmentShaderId = createShader( shaderCode, GL_FRAGMENT_SHADER );
    }

    protected int createShader ( String shaderCode, int shaderType ) throws Exception {
        int shaderId = glCreateShader( shaderType );
        if ( shaderId == 0 ) throw new Exception( String.format("Error creating Shader of type: %o", shaderType) );

        glShaderSource( shaderId, shaderCode );
        glCompileShader( shaderId );

        if ( glGetShaderi( shaderId, GL_COMPILE_STATUS ) == 0 )
            throw new Exception( "Error compiling Shader code: " + glGetShaderInfoLog( shaderId, 1024 ) );

        glAttachShader( programId, shaderId );
        return shaderId;
    }

    public void link () throws Exception {
        glLinkProgram( programId );
        if ( glGetProgrami( programId, GL_LINK_STATUS ) == 0 )
            throw new Exception( "Error linking Shader code: " + glGetProgramInfoLog( programId, 1024 ) );

        if ( vertexShaderId != 0 ) glDetachShader( programId, vertexShaderId );
        if ( fragmentShaderId != 0 ) glDetachShader( programId, fragmentShaderId );

        glValidateProgram( programId );
        if ( glGetProgrami( programId, GL_VALIDATE_STATUS ) == 0 )
            System.err.println( "Warning validating Shader code: " + glGetProgramInfoLog( programId, 1024 ) );
    }

    public void bind () { glUseProgram( programId ); }
    public void unbind () { glUseProgram(0); }

    public void cleanUp () {
        unbind();
        if ( programId != 0 ) glDeleteProgram( programId );
    }

    public int getProgramId () { return this.programId; }
    public int getVertexShaderId () { return this.vertexShaderId; }
    public int getFragmentShaderId () { return this.fragmentShaderId; }

    public void addUniform ( Uniform uniform ) {
        this.uniforms.put( uniform.getName(), uniform.getLocation() );
    }

}
