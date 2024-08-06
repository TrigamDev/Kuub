#version 460

layout ( location = 0 ) in vec3 position;
layout ( location = 1 ) in vec2 inputTexCoord;

out vec2 texCoord;

uniform mat4 projectionMatrix;
uniform mat4 transformMatrix;

void main () {
    gl_Position = projectionMatrix * transformMatrix * vec4( position, 1.0 );
    texCoord = inputTexCoord;
}