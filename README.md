### What's this?

A really simple 2D physics engine in Java tossed together in a couple
of short evening sessions while bored.

![Example gif](http://i.imgur.com/DjlzD8K.gif)

Note that this was built to illustrate a few points only, and not to
build an actual physics engine. It's currently rendering using OpenGL
through LWJGF3. Note that there's still a couple of odd bugs in here
that I haven't really cared to address yet. Most notably that the
separation algorithm seems a bit flawed (boxes tend to be resolved
by jumping on top of each other instead of colliding along the x
axis)

It doesn't handle *anything* except basic gravity. There's also no
real optimizations, but rather we're brute-forcing our collision
pairs (meaning a lot of redundant checks in the engine).

Again, solely built to demonstrate some basic physics engine concepts.
If you feel like something in here is useful, feel free to nab it and
use it in other software. Released under the MIT license.

You're welcome to submit PR's, but I'll most likely not continue working
on this :)

### How do I run it?

Assuming you have Gradle installed:

        gradle run

And that should pretty much do it.
