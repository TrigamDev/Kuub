package dev.trigam.kuub.client.input.mouse;

import com.github.jafarlihi.eemit.EventEmitter;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

public class MouseListener implements NativeMouseInputListener {

    public EventEmitter<NativeMouseEvent> emitter = new EventEmitter<>();

    public void nativeMouseClicked ( NativeMouseEvent event ) { this.emitter.emit( "click", event ); }

    public void nativeMousePressed ( NativeMouseEvent event ) { this.emitter.emit( "press", event ); }

    public void nativeMouseReleased ( NativeMouseEvent event ) { this.emitter.emit( "release", event ); }

    public void nativeMouseMoved ( NativeMouseEvent event ) { this.emitter.emit( "move", event ); }

    public void nativeMouseDragged ( NativeMouseEvent event ) { this.emitter.emit( "drag", event ); }

}
