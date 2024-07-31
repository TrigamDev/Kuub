package dev.trigam.kuub.resource;

public enum ResourceType {

    ASSET ( "assets" ),
    DATA ( "data" );

    private final String type;
    ResourceType ( String type ) {
        this.type = type;
    }

    public String getType () { return this.type; }
}
