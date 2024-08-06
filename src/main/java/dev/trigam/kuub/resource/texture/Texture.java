package dev.trigam.kuub.resource.texture;

import de.matthiasmann.twl.utils.PNGDecoder;
import dev.trigam.kuub.resource.FileLoader;
import dev.trigam.kuub.resource.Identifier;
import dev.trigam.kuub.resource.ResourceType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.lwjgl.opengl.GL46.*;

public class Texture {

    private final Identifier resourceLocation;
    public int textureId;
    public int width;
    public int height;
    public ByteBuffer textureData;

    public Texture ( Identifier resourceLocation ) throws IOException, URISyntaxException {
        this.resourceLocation = resourceLocation;

        this.load();
        this.generate();
    }

    public void load () throws URISyntaxException, IOException {
        // Load file
        Optional<Path> texturePath = FileLoader.getResourcePath( ResourceType.ASSET, this.resourceLocation );
        if ( texturePath.isEmpty() ) return;

        InputStream textureFile = Files.newInputStream( texturePath.get() );
        PNGDecoder decoder = new PNGDecoder( textureFile );

        this.width = decoder.getWidth(); this.height = decoder.getHeight();
        // Decode texture
        ByteBuffer buffer = ByteBuffer.allocateDirect( 4 * this.width * this.height );
        decoder.decode( buffer, this.width * 4, PNGDecoder.Format.RGBA );
        buffer.flip();

        this.width = decoder.getWidth();
        this.height = decoder.getHeight();
        this.textureData = buffer;
    }

    public void generate () {
        this.textureId = glGenTextures();

        glBindTexture( GL_TEXTURE_2D, this.textureId );

        glPixelStorei( GL_UNPACK_ALIGNMENT, 1 );

        glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST );
        glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST );

        glTexImage2D( GL_TEXTURE_2D, 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, this.getTextureData() );

        glGenerateMipmap( GL_TEXTURE_2D );
    }

    public void bind () {
        glBindTexture( GL_TEXTURE_2D, this.textureId );
    }

    public int getWidth () { return this.width; }
    public int getHeight () { return this.height; }

    public ByteBuffer getTextureData () { return this.textureData; }

}
