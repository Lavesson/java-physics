### What's this?

A really simple 2D physics engine in Java tossed together in two very
short evening sessions.

Note that this was built to illustrate a few points only, and not to
build an actual physics engine. It's currently rendering using Swing
because I didn't have any energy to look anything else up, but it
shouldn't be that much work to get it to render with something
like OpenGL (if a renderer is already in place, that is).

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
