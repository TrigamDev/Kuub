package dev.trigam.kuub.client.render.element.transform;

import dev.trigam.kuub.client.render.camera.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class View {

    private final Matrix4f viewMatrix;

    public static Vector3f xAxis = new Vector3f( 1, 0, 0 );
    public static Vector3f yAxis = new Vector3f( 0, 1, 0 );
    public static Vector3f zAxis = new Vector3f( 0, 0, 1 );

    public View ( Camera camera ) {
        this.viewMatrix = new Matrix4f();
        this.updateViewMatrix( camera );
    }

    public void updateViewMatrix ( Camera camera ) {
        if ( camera != null ) {
            this.viewMatrix.identity()
                .rotate( camera.getRotationX(), xAxis )
                .rotate( camera.getRotationY(), yAxis )
                .rotate( camera.getRotationZ(), zAxis )
                .translate( -camera.getX(),-camera.getY(), -camera.getZ() );
        }
    }

    public Matrix4f getViewMatrix () { return this.viewMatrix; }

}
