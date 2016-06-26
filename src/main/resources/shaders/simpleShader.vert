#version 330 core

layout(location = 0) in vec2 position;
uniform mat4 translate;

void main() {
	gl_Position = translate * vec4(position, 0.0, 1.0);
}
