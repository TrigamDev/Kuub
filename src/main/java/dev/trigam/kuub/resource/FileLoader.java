package dev.trigam.kuub.resource;

import dev.trigam.kuub.resource.Identifier;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class FileLoader {

    public static Optional<File> loadResource ( ResourceType type, Identifier id ) throws URISyntaxException {
        Path resourcePath = Paths.get( type.getType(), id.getNamespace(), id.getPath() );
        URL resourceUrl = FileLoader.class.getClassLoader().getResource( resourcePath.toString() );

        if ( resourceUrl == null ) return Optional.empty();
        return Optional.of( Paths.get( resourceUrl.toURI() ).toFile() );
    }

    public static Optional<String> loadTextBasedResource ( ResourceType type, Identifier id ) throws URISyntaxException {
        Optional<File> textBasedFile = loadResource( type, id );
        if ( textBasedFile.isEmpty() ) return Optional.empty();

        List<String> lines;
        try {
            lines = Files.readAllLines( textBasedFile.get().toPath(), StandardCharsets.UTF_8 );
            return Optional.of( String.join( "\n", lines ) );
        } catch ( IOException e ) { e.printStackTrace(); }

        return Optional.empty();
    }

}
