use bevy::prelude::*;

#[derive(Resource)]
pub struct MovementSettings {
	pub sensitivity: f32,
	pub speed: f32,
	pub max_pitch: f32,
}

impl Default for MovementSettings {
	fn default() -> Self {
		Self {
			sensitivity: 0.00012,
			speed: 12.0,
			max_pitch: 89.9,
		}
	}
}