package dev.trigam.kuub.client.render.camera;

import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f( 0, 0, 0 );
    private Vector3f rotation = new Vector3f( 0, 0, 0 );

    private float fieldOfView = 80f;

    public Camera () { }

    public Vector3f getDirection () {
        return new Vector3f( 1, 0, 1 )
            .rotateX( Math.toRadians( this.rotation.x ) )
            .rotateY( Math.toRadians( this.rotation.y ) )
            .rotateZ( Math.toRadians( this.rotation.z ) );
    }

    public void move ( Vector3f movementVector ) {
        Quaternionf rotationQuaternion = new Quaternionf()
            .rotationXYZ( this.rotation.x, this.rotation.y, this.rotation.z )
            .invert()
            .normalize();
        Vector3f move = movementVector.rotate( rotationQuaternion );

        System.out.println( this.rotation );

        this.addX( move.x ); this.addY( move.y ); this.addZ( move.z );
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

    public Camera addX ( float x ) { this.position.x = this.position.x + x; return this; }
    public Camera addY ( float y ) { this.position.y = this.position.y + y; return this; }
    public Camera addZ ( float z ) { this.position.z = this.position.z + z; return this; }


    public Vector3f getRotation () { return this.rotation; }
    public Camera setRotation ( Vector3f rotation ) { this.rotation = rotation; return this; }
    public Camera setRotation ( float pitch, float yaw, float roll ) { this.rotation = new Vector3f( pitch, yaw, roll ); return this; }

    public float getRotationX () { return this.rotation.x; }
    public float getRotationY () { return this.rotation.y; }
    public float getRotationZ () { return this.rotation.z; }


    public Camera setRotationX ( float rotationX ) { this.rotation.x = rotationX; return this; }
    public Camera setRotationY ( float rotationY ) { this.rotation.y = rotationY; return this; }
    public Camera setRotationZ ( float rotationZ ) { this.rotation.z = rotationZ; return this; }
}
