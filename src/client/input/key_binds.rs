use bevy::prelude::*;

#[derive(Resource)]
pub struct KeyBindings {
	pub move_forward: KeyCode,
	pub move_backward: KeyCode,
	pub move_left: KeyCode,
	pub move_right: KeyCode,
	pub move_up: KeyCode,
	pub move_down: KeyCode,
	pub toggle_cursor_lock: KeyCode,
}

impl Default for KeyBindings {
	fn default() -> Self {
		Self {
			move_forward: KeyCode::KeyW,
			move_backward: KeyCode::KeyS,
			move_left: KeyCode::KeyA,
			move_right: KeyCode::KeyD,
			move_up: KeyCode::Space,
			move_down: KeyCode::ShiftLeft,
			toggle_cursor_lock: KeyCode::Escape,
		}
	}
}