#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec4 color;

out vec4 vertexColor;

uniform mat4 translation;
uniform mat4 projection;

void main() {
	vertexColor = color;
	gl_Position = projection * translation * vec4(position, 0.0, 1.0);
}
