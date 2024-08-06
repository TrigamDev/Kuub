package dev.trigam.kuub.client.render.element.transform;

import dev.trigam.kuub.client.render.camera.Camera;
import org.joml.Matrix4f;

public class Projection {

    private final Camera camera;
    public float zNear = 0.01f;
    public float zFar = 1024f;

    private final Matrix4f projectionMatrix;

    public Projection (Camera camera, int width, int height ) {
        this.camera = camera;
        this.projectionMatrix = new Matrix4f();
        this.updateProjectionMatrix( width, height );
    }

    public void updateProjectionMatrix ( int width, int height ) {
        float aspectRatio = (float) width / height;
        float fov = (float) Math.toRadians( this.camera.getFieldOfView() );
        this.projectionMatrix.identity().setPerspective( fov, aspectRatio, this.zNear, this.zFar );
    }

    public Matrix4f getProjectionMatrix () {
        return this.projectionMatrix;
    }

}
