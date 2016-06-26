#version 330 core

layout(location = 0) in vec2 position;
uniform mat4 translation;
uniform mat4 projection;

void main() {
	gl_Position = projection * translation * vec4(position, 0.0, 1.0);
}
