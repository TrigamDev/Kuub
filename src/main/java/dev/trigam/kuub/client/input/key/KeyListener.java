package dev.trigam.kuub.client.input.key;

import com.github.jafarlihi.eemit.EventEmitter;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;
import java.util.Map;

public class KeyListener implements NativeKeyListener {

    public EventEmitter<NativeKeyEvent> emitter = new EventEmitter<>();
    private final Map< Integer, Key> keys = new HashMap<>();

    public void nativeKeyPressed ( NativeKeyEvent event ) {
        this.setKeyPressed( event, true );
        this.emitter.emit( "press", event );
    }

    public void nativeKeyReleased ( NativeKeyEvent event ) {
        this.setKeyPressed( event, false );
        this.emitter.emit( "release", event );
    }

    public void nativeKeyTyped ( NativeKeyEvent event ) { this.emitter.emit( "type", event ); }

    private void setKeyPressed ( NativeKeyEvent event, boolean pressed ) {
        int keyCode = event.getRawCode();
        Key pressedKey = getKey( keyCode );
        pressedKey.setPressed( pressed );
        keys.put( keyCode, pressedKey );
    }

    public Key getKey ( int keyCode ) {
        Key key = keys.get( keyCode );
        if ( key == null ) key = new Key( keyCode );
        return key;
    }

    public boolean isKeyPressed ( int keyCode ) {
        Key keyInQuestion = keys.get( keyCode );
        return keyInQuestion != null && keyInQuestion.isPressed();
    }

}
