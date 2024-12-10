// https://github.com/sburris0/bevy_flycam/blob/master/src/lib.rs

use bevy::input::mouse::MouseMotion;
use bevy::prelude::*;
use bevy::window::{CursorGrabMode, PrimaryWindow};

#[path = "../input/key_binds.rs"] mod key_binds;
#[path = "../player/movement_settings.rs"] mod movement_settings;

pub struct PlayerPlugin;
impl Plugin for PlayerPlugin {
	fn build( &self, app: &mut App ) {
		app
			.init_resource::<movement_settings::MovementSettings>()
			.init_resource::<key_binds::KeyBindings>()
			.add_systems( Startup, setup_camera )
			.add_systems( Startup, initial_cursor_lock )
			.add_systems( Update, camera_move )
			.add_systems( Update, camera_look )
			.add_systems( Update, cursor_lock );
	}
}

#[derive(Component)]
pub struct FlyCam;

// Spawn camera
fn setup_camera( mut commands: Commands ) {
	commands.spawn(
		(
			Camera3d::default(),
			FlyCam
		)
	);
}

// Cursor locking	
fn toggle_cursor_lock( window: &mut Window ) {
	match window.cursor_options.grab_mode {
		CursorGrabMode::None => {
			window.cursor_options.grab_mode = CursorGrabMode::Confined;
			window.cursor_options.visible = false;
		}
		_ => {
			window.cursor_options.grab_mode = CursorGrabMode::None;
			window.cursor_options.visible = true;
		}
	}
}
fn initial_cursor_lock( mut primary_window: Query< &mut Window, With<PrimaryWindow> > ) {
	if let Ok( mut window ) = primary_window.get_single_mut() {
		toggle_cursor_lock( &mut window );
	}
}
fn cursor_lock(
	keys: Res<ButtonInput<KeyCode>>,
	key_bindings: Res<key_binds::KeyBindings>,
	mut primary_window: Query<&mut Window, With<PrimaryWindow>>,
) {
	if let Ok( mut window ) = primary_window.get_single_mut() {
		if keys.just_pressed( key_bindings.toggle_cursor_lock ) {
			toggle_cursor_lock( &mut window );
		}
	}
}

// Move camera from key inputs
fn camera_move(
	keys: Res<ButtonInput<KeyCode>>,
	time: Res<Time>,
	primary_window: Query<&Window, With<PrimaryWindow>>,
	settings: Res<movement_settings::MovementSettings>,
	key_bindings: Res<key_binds::KeyBindings>,
	mut query: Query<(&FlyCam, &mut Transform)>
) {
	if let Ok( window ) = primary_window.get_single() {
		for ( _camera, mut transform ) in query.iter_mut() {
			let mut velocity = Vec3::ZERO;
			let local_z = transform.local_z();
			let forward = -Vec3::new( local_z.x, 0.0, local_z.z );
			let right = Vec3::new( local_z.z, 0.0, -local_z.x );

			// Add velocity from key inputs
			for key in keys.get_pressed() {
				match window.cursor_options.grab_mode {
					CursorGrabMode::None => (),
					_ => {
						let key = *key;
						if key == key_bindings.move_forward { velocity += forward; }
						else if key == key_bindings.move_backward { velocity -= forward; }
						else if key == key_bindings.move_left { velocity -= right; }
						else if key == key_bindings.move_right { velocity += right; }
						else if key == key_bindings.move_up { velocity += Vec3::Y; }
						else if key == key_bindings.move_down { velocity -= Vec3::Y; }
					}
				}
			}

			velocity = velocity.normalize_or_zero();
			transform.translation += velocity * time.delta_secs() * settings.speed
		}
	}
}

// Rotate camera from mouse input
fn camera_look(
	settings: Res<movement_settings::MovementSettings>,
	primary_window: Query<&Window, With<PrimaryWindow>>,
	mut state: EventReader<MouseMotion>,
	mut query: Query<&mut Transform, With<FlyCam>>,
) {
	if let Ok( window ) = primary_window.get_single() {
		for mut transform in query.iter_mut() {
			for mouse_motion in state.read() {
				let ( mut yaw, mut pitch, _ ) = transform.rotation.to_euler( EulerRot::YXZ );
				match window.cursor_options.grab_mode {
					CursorGrabMode::None => (),
					_ => {
						// Using smallest of height or width ensures equal vertical and horizontal sensitivity
						let window_scale = window.height().min( window.width() );
						pitch -= ( settings.sensitivity * mouse_motion.delta.y * window_scale ).to_radians();
						yaw -= ( settings.sensitivity * mouse_motion.delta.x * window_scale ).to_radians();
					}
				}

				// Clamp pitch to straight up and straight down
				pitch = pitch.clamp(
					( -settings.max_pitch ).to_radians(),
					( settings.max_pitch ).to_radians()
				);

				// Order is important to prevent unintended roll
				transform.rotation =
					Quat::from_axis_angle( Vec3::Y, yaw ) *
					Quat::from_axis_angle( Vec3::X, pitch );
			}
		}
	}
}