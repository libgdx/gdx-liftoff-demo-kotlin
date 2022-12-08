@file:JvmName("TeaVMLauncher")

package gdx.liftoff.teavm

import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration
import com.github.xpenatan.gdx.backends.web.WebApplication
import gdx.liftoff.Main

/** Launches the TeaVM/HTML application. */
fun main() {
	val config = TeaApplicationConfiguration("canvas").apply {
		width = 640
		height = 480
	}
	WebApplication(Main(), config)
}
