use bevy::prelude::*;
#[path = "client/render/fly_cam.rs"] mod fly_cam;

fn main() {
    App::new()
		.add_plugins( DefaultPlugins )
		.add_plugins( fly_cam::PlayerPlugin )
		.add_systems(Startup, init)
		.run();
}

fn init(mut commands: Commands, mut meshes: ResMut<Assets<Mesh>>, mut materials: ResMut<Assets<StandardMaterial>>) {
	println!("haaaaiiiii !!! ! :3 :3 :3");
	commands.spawn(
		(
			Mesh3d( meshes.add(Cuboid::default()) ),
			MeshMaterial3d( materials.add( Color::srgb( 1.0, 1.0, 1.0 ) ) )
		)
	);

	commands.spawn(
		(
			PointLight::default(),
			Transform::from_xyz( 0.0, 10.0, 0.0 )
		)
	);
}