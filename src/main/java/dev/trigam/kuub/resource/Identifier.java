package dev.trigam.kuub.resource;

import dev.trigam.kuub.error.InvalidIdentifierException;
import dev.trigam.kuub.util.Regex;

public final class Identifier {
    public static final char namespaceSeperator = ':';
    public static final String vanillaNamespace = "vanilla";
    public String namespace;
    public String path;

    // Initialization
    public Identifier ( String namespace, String path ) {
        this.namespace = validateNamespace( namespace, path );
        this.path = validatePath( namespace, path );
    }
    public Identifier ( String path ) {
        this.namespace = vanillaNamespace;
        this.path = validatePath( vanillaNamespace, path );
    }

    // Validation
    public static final String namespaceRegex = "[^a-zA-Z0-9_.]";
    public static final String pathRegex = "[^a-zA-Z0-9/_.]";

    private static String validateNamespace ( String namespace, String path ) {
        if ( Regex.hasMatch( namespaceRegex, namespace ) ) throw new InvalidIdentifierException(
            String.format( "Non [a-z0-9_.-] character in namespace of location: %s:%s", namespace, path )
        );
        else return namespace;
    }

    private static String validatePath ( String namespace, String path ) {
        if ( Regex.hasMatch(pathRegex, path ) ) throw new InvalidIdentifierException(
            String.format( "Non [a-z0-9_.-] character in path of location: %s:%s", namespace, path )
        );
        else return path;
    }

    // Getters
    public String getNamespace () { return this.namespace; }
    public String getPath () { return this.path; }

    // Util
    public String toString () { return String.format("%s%s%s", this.namespace, namespaceSeperator, this.path); }

    public boolean equals ( Object compareObj ) {
        if ( this == compareObj ) return true;
        else if ( !(compareObj instanceof Identifier compareId) ) return false;
        else return this.namespace.equals(compareId.namespace) && this.path.equals(compareId.path);
    }
}
