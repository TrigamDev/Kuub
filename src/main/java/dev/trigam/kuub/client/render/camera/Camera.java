package dev.trigam.kuub.client.render.camera;

import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;

    private float fieldOfView = 80f;

    public Camera () {
        this.position = new Vector3f( 0, 0, 0 );
        this.rotation = new Vector3f( 0, 0, 0 );
    }

    public Vector3f getDirection () {
        return new Vector3f( 0, 0, -1 )
            .rotateX( this.rotation.x )
            .rotateY( this.rotation.y )
            .rotateZ( this.rotation.z );
    }

    public float getFieldOfView () { return this.fieldOfView; }
    public Camera setFieldOfView ( float fieldOfView ) { this.fieldOfView = fieldOfView; return this; }

    public Vector3f getPosition () { return this.position; }
    public Camera setPosition ( Vector3f position ) { this.position = position; return this; }
    public Camera setPosition ( float x, float y, float z ) { this.position = new Vector3f( x, y, z ); return this; }

    public float getX () { return this.position.x; }
    public float getY () { return this.position.y; }
    public float getZ () { return this.position.z; }

    public Camera setX ( float x ) { this.position.x = x; return this; }
    public Camera setY ( float y ) { this.position.y = y; return this; }
    public Camera setZ ( float z ) { this.position.z = z; return this; }


    public Vector3f getRotation () { return this.rotation; }
    public Camera setRotation ( Vector3f rotation ) { this.rotation = rotation; return this; }
    public Camera setRotation ( float pitch, float yaw, float roll ) { this.rotation = new Vector3f( pitch, yaw, roll ); return this; }

    public float getPitch () { return this.rotation.x; }
    public float getYaw () { return this.rotation.y; }
    public float getRoll () { return this.rotation.z; }


    public Camera setPitch ( float pitch ) { this.rotation.x = pitch; return this; }
    public Camera setYaw ( float yaw ) { this.rotation.y = yaw; return this; }
    public Camera setRoll ( float roll ) { this.rotation.z = roll; return this; }
}
