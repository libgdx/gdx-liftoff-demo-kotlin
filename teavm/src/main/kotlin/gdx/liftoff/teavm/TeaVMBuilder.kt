package gdx.liftoff.teavm

import com.github.xpenatan.gdx.teavm.backends.shared.config.AssetFileHandle
import com.github.xpenatan.gdx.teavm.backends.shared.config.compiler.TeaCompiler
import com.github.xpenatan.gdx.teavm.backends.web.config.backend.WebBackend
import org.teavm.tooling.TeaVMSourceFilePolicy
import org.teavm.tooling.sources.DirectorySourceFileProvider
import org.teavm.vm.TeaVMOptimizationLevel
import java.io.File

/** Builds the TeaVM/HTML application. */
object TeaVMBuilder {
    @JvmStatic fun main(arguments: Array<String>) {
        val debug = "debug" in arguments
        val startJetty = "run" in arguments

        val webBackend = WebBackend()
            .setHtmlTitle("gdx-liftoff-demo-kotlin")
            .setHtmlWidth(800) // Change this to fit your game's requirements.
            .setHtmlHeight(600) // Change this to fit your game's requirements.
            .setStartJettyAfterBuild(startJetty)
            .setJettyPort(8080)
//            .setWebAssembly(true) // Uncomment this line to use WASM output instead of JavaScript output.

        TeaCompiler(webBackend)
            .addAssets(AssetFileHandle("../assets"))
            .setOptimizationLevel(if (debug) TeaVMOptimizationLevel.SIMPLE else TeaVMOptimizationLevel.ADVANCED)
            .setMainClass("gdx.liftoff.teavm.TeaVMLauncher")
            .setObfuscated(!debug)
            .setDebugInformationGenerated(debug)
            .setSourceMapsFileGenerated(debug)
            .setSourceFilePolicy(if (debug) TeaVMSourceFilePolicy.COPY else TeaVMSourceFilePolicy.DO_NOTHING)
            .addSourceFileProvider(DirectorySourceFileProvider(File("../core/src/main/kotlin")))
            // Register any classes or packages that require reflection here.
            //.addReflectionClass("gdx.liftoff.reflect")
            .build(File("build/dist"))
    }
}