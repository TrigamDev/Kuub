package dev.trigam.kuub.client.render.element.transform;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class World {

    private final Matrix4f worldMatrix;

    public World () {
        this.worldMatrix = new Matrix4f();
    }

    public void updateWorldMatrix ( Vector3f translation, Vector3f rotation, float scale ) {
        this.worldMatrix
            .translate( translation )
            .rotateX( rotation.x )
            .rotateY( rotation.y )
            .rotateZ( rotation.z )
            .scale( scale );
    }
    public Matrix4f getWorldMatrix () { return this.worldMatrix; }

}
