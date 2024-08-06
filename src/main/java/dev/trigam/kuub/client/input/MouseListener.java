package dev.trigam.kuub.client.input;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

public class MouseListener implements NativeMouseInputListener {

    public void nativeMouseClicked ( NativeMouseEvent event ) {
        System.out.printf( "\nMouse clicked: X: %d, Y: %d", event.getX(), event.getY() );
    }

    public void nativeMousePressed ( NativeMouseEvent event ) {
        System.out.printf( "\nMouse Pressed: %d", event.getButton());
    }

    public void nativeMouseReleased ( NativeMouseEvent event ) {
        System.out.printf( "\nMouse Released: %d", event.getButton() );
    }

    public void nativeMouseMoved ( NativeMouseEvent event ) {
        System.out.printf ( "\nMouse Moved: %d, %d", event.getX(), event.getY() );
    }

    public void nativeMouseDragged ( NativeMouseEvent event ) {
        System.out.printf ( "\nMouse Dragged: %d, %d", event.getX(), event.getY() );
    }

}
