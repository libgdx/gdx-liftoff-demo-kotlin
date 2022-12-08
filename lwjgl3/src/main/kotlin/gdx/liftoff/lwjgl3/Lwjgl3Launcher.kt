@file:JvmName("Lwjgl3Launcher")

package gdx.liftoff.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import gdx.liftoff.Main

/** Launches the desktop (LWJGL3) application. */
fun main() {
	Lwjgl3Application(Main(), Lwjgl3ApplicationConfiguration().apply {
		setTitle("gdx-liftoff-demo-kotlin")
		setWindowedMode(640, 480)
		setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
	})
}
