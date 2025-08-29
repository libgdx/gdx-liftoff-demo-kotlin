package gdx.liftoff.teavm

import java.io.File
import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import com.github.xpenatan.gdx.backends.teavm.config.plugins.TeaReflectionSupplier
import org.teavm.tooling.TeaVMSourceFilePolicy
import org.teavm.tooling.TeaVMTargetType
import org.teavm.tooling.sources.DirectorySourceFileProvider
import org.teavm.vm.TeaVMOptimizationLevel

/** Builds the TeaVM/HTML application. */
object TeaVMBuilder {
    /**
     * A single point to configure most debug vs. release settings.
     * This defaults to false in new projects; set this to false when you want to release.
     * If this is true, the output will not be obfuscated, and debug information will usually be produced.
     * You can still set obfuscation to false in a release if you want the source to be at least a little legible.
     * This works well when the targetType is set to JAVASCRIPT, but you can still set the targetType to WEBASSEMBLY_GC
     * while this is true in order to test that higher-performance target before releasing.
     */
    private const val DEBUG = true

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

        // JavaScript is the default target type for TeaVM, and it works better during debugging.
        tool.targetType = TeaVMTargetType.JAVASCRIPT
        // You can choose to use the WebAssembly (WASM) GC target instead, which tends to perform better, but isn't
        // as easy to debug. It might be a good idea to alternate target types during development if you plan on using
        // WASM at release time.
//        tool.targetType = TeaVMTargetType.WEBASSEMBLY_GC

        tool.mainClass = "gdx.liftoff.teavm.TeaVMLauncher"
        // For many (or most) applications, using a high optimization won't add much to build time.
        // If your builds take too long, and runtime performance doesn't matter, you can change ADVANCED to SIMPLE .
        tool.optimizationLevel = TeaVMOptimizationLevel.ADVANCED
        // The line below should use tool.setObfuscated(false) if you want clear debugging info.
        // You can change it to tool.setObfuscated(true) when you are preparing to release, to try to hide your original code.
        tool.setObfuscated(!DEBUG)

        // If targetType is set to JAVASCRIPT, you can use the following lines to debug JVM languages from the browser,
        // setting breakpoints in Kotlin code and stopping in the appropriate place in generated JavaScript code.
        // These settings don't quite work currently if generating WebAssembly. They may in a future release.
        if(DEBUG && tool.targetType == TeaVMTargetType.JAVASCRIPT) {
            tool.isDebugInformationGenerated = true
            tool.isSourceMapsFileGenerated = true
            tool.setSourceFilePolicy(TeaVMSourceFilePolicy.COPY)
            tool.addSourceFileProvider(DirectorySourceFileProvider(File("../core/src/main/java/")))
        }

        TeaBuilder.build(tool)
    }
}
