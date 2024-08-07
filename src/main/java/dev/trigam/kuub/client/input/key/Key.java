package dev.trigam.kuub.client.input.key;

public class Key {

    private boolean pressed = false;
    private final int code;

    public Key ( int code ) {
        this.code = code;
    }

    public void setPressed ( boolean pressed ) { this.pressed = pressed; }
    public boolean isPressed () { return this.pressed; }

    public int getCode () { return this.code; }

}
