package gdx.liftoff.teavm

import java.io.File
import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import com.github.xpenatan.gdx.backends.teavm.config.plugins.TeaReflectionSupplier
import com.github.xpenatan.gdx.backends.teavm.gen.SkipClass
import org.teavm.vm.TeaVMOptimizationLevel

/** Builds the TeaVM/HTML application. */
@SkipClass
object TeaVMBuilder {
    @JvmStatic fun main(arguments: Array<String>) {
        val teaBuildConfiguration = TeaBuildConfiguration().apply {
            assetsPath.add(AssetFileHandle("../assets"))
            webappPath = File("build/dist").canonicalPath
            // Register any extra classpath assets here:
            // additionalAssetsClasspathFiles += "gdx/liftoff/asset.extension"
        }

        // Register any classes or packages that require reflection here:
        // TeaReflectionSupplier.addReflectionClass("gdx.liftoff.reflect")

        val tool = TeaBuilder.config(teaBuildConfiguration)
        tool.mainClass = "gdx.liftoff.teavm.TeaVMLauncher"
        tool.optimizationLevel = TeaVMOptimizationLevel.ADVANCED
        tool.setObfuscated(true)
        TeaBuilder.build(tool)
    }
}
