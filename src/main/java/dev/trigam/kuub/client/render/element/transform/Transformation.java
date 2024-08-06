package dev.trigam.kuub.client.render.element.transform;

import dev.trigam.kuub.client.render.camera.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    private final Projection projectionTransform;
    private final World worldTransform;
    private final View viewTransform;

    public Transformation (Camera camera, int width, int height ) {
        this.projectionTransform = new Projection( camera, width, height );
        this.worldTransform = new World();
        this.viewTransform = new View( camera );
    }

    public Projection getProjectionTransform () { return projectionTransform; }
    public World getWorldTransform () { return this.worldTransform; }
    public View getViewTransform () { return this.viewTransform; }

    public Matrix4f getTransformMatrix () { return this.viewTransform.getViewMatrix().mul( this.worldTransform.getWorldMatrix() ); }

    public void updateWorldTransform (Vector3f translation, Vector3f rotation, float scale ) {
        this.worldTransform.updateWorldMatrix( translation, rotation, scale );
    }
}
