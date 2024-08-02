package dev.trigam.kuub.client.render.element.transform;

import org.joml.Vector3f;

public class Transformation {

    private final Projection projectionTransform;
    private final World worldTransform;

    public Transformation ( int width, int height ) {
        this.projectionTransform = new Projection( width, height );
        this.worldTransform = new World();
    }

    public Projection getProjectionTransform () { return projectionTransform; }
    public World getWorldTransform () { return this.worldTransform; }

    public static World createWorldTransform ( Vector3f translation, Vector3f rotation, float scale ) {
        World worldTransform = new World();
        worldTransform.updateWorldMatrix( translation, rotation, scale );
        return worldTransform;
    }
}
