package bobhtmlcompiler;

import bobthebuildtool.pojos.buildfile.Project;
import bobthebuildtool.services.Log;
import htmlcompiler.commands.Compile;
import htmlcompiler.commands.Dependencies;
import htmlcompiler.commands.Host;
import jcli.errors.InvalidCommandLine;

import java.io.IOException;
import java.util.Map;

import static bobhtmlcompiler.CliHtmlCompile.newCompileCommandConfig;
import static bobhtmlcompiler.CliHtmlHost.newHostCommandConfig;
import static htmlcompiler.tools.Logger.newLogger;

public enum Main {;

    private static final String
        DESCRIPTION_COMPILE = "Compiles templates and plain HTML to compiled HTML",
        DESCRIPTION_HOST = "Monitors frontend code for changes and compiles and hosts on localhost:8080",
        DESCRIPTION_CHECK = "Checks if various needed binaries are available in the path";

    public static void installPlugin(final Project project) {
        project.addCommand("compile-html", DESCRIPTION_COMPILE, Main::compileHtml);
        project.addCommand("host-frontend", DESCRIPTION_HOST, Main::hostFrontend);
        project.addTarget("check-frontend-dependencies", DESCRIPTION_CHECK, Main::checkDependencies);
    }

    private static int compileHtml(final Project project, final Map<String, String> environment, final String[] args)
            throws InvalidCommandLine, IOException {
        Compile.executeCompile(newLogger(Log::logInfo, Log::logWarning), newCompileCommandConfig(project, environment, args));
        return 0;
    }

    private static int hostFrontend(final Project project, final Map<String, String> environment, final String[] args)
            throws IOException, InterruptedException, InvalidCommandLine {
        Host.executeHost(newLogger(Log::logInfo, Log::logWarning), newHostCommandConfig(project, environment, args));
        return 0;
    }

    private static void checkDependencies(final Project project, final Map<String, String> environment) {
        Dependencies.executeDependencies(newLogger(Log::logInfo, Log::logWarning));
    }

}
