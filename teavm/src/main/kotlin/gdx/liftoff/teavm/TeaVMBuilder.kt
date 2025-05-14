package gdx.liftoff.teavm

import java.io.File
import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import com.github.xpenatan.gdx.backends.teavm.config.plugins.TeaReflectionSupplier
import org.teavm.tooling.TeaVMTargetType
import org.teavm.vm.TeaVMOptimizationLevel

/** Builds the TeaVM/HTML application. */
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
        // You can uncomment the line below to use WASM instead of JavaScript as a target.
        // Some code can see very significant performance benefits from WASM, and some won't.
//        tool.targetType = TeaVMTargetType.WEBASSEMBLY_GC
        tool.mainClass = "gdx.liftoff.teavm.TeaVMLauncher"
        // For many (or most) applications, using a high optimization won't add much to build time.
        // If your builds take too long, and runtime performance doesn't matter, you can change ADVANCED to SIMPLE .
        tool.optimizationLevel = TeaVMOptimizationLevel.ADVANCED
        // The line below should use tool.setObfuscated(false) if you want clear debugging info.
        // You can change it to tool.setObfuscated(true) when you are preparing to release, to try to hide your original code.
        tool.setObfuscated(false)
        TeaBuilder.build(tool)
    }
}
