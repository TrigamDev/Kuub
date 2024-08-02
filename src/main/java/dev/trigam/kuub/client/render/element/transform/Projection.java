package dev.trigam.kuub.client.render.element.transform;

import org.joml.Matrix4f;

public class Projection {

    public float fieldOfView = (float) Math.toRadians( 80F );
    public float zNear = 0.01f;
    public float zFar = 1024f;

    private final Matrix4f projectionMatrix;

    public Projection ( int width, int height ) {
        this.projectionMatrix = new Matrix4f();
        this.updateProjectionMatrix( width, height );
    }

    public void updateProjectionMatrix ( int width, int height ) {
        float aspectRatio = (float) width / height;
        this.projectionMatrix.identity();
        this.projectionMatrix.setPerspective( fieldOfView, aspectRatio, this.zNear, this.zFar );
    }

    public Matrix4f getProjectionMatrix () {
        return this.projectionMatrix;
    }

}
