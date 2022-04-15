package htmlcompiler;

import bobthebuildtool.pojos.buildfile.Project;
import htmlcompiler.commands.Host.HostCommandConfig;
import jcli.annotations.CliOption;
import jcli.errors.InvalidCommandLine;

import java.nio.file.Path;
import java.util.Map;

import static jcli.CliParserBuilder.newCliParser;

public class CliHtmlHost {

    @CliOption(name = 'c', longName = "validation-config", defaultValue = "src/main/webcnf/validation.json")
    public String validation;
    @CliOption(name = 'e', longName = "keep-extension")
    public boolean keepExtensions;
    @CliOption(name = 'w', longName = "dont-walk-tree")
    public boolean dontWalkTree;
    @CliOption(name = 'j', longName = "js-compressor", defaultValue = "gcc-simple", description = "Options: gcc-simple, gcc-bundle, gcc-whitespace, gcc-advanced, yui")
    public String jsCompiler;

    @CliOption(name = 'p', longName = "port", defaultValue = "8080")
    public int port;
    @CliOption(longName = "disable-mock-api")
    public boolean disableMockApi;
    @CliOption(longName = "mock-api-spec", defaultValue = "src/main/webcnf/requests.json")
    public String mockApiSpecification;

    @CliOption(name = 'd', longName = "source-dirs", defaultValue = "src/main/webinc")
    public String watchedDirectories;

    public static HostCommandConfig newHostCommandConfig(final Project project, final Map<String, String> environment
            , final String[] args) throws InvalidCommandLine {
        final CliHtmlHost arguments = newCliParser(CliHtmlHost::new).parse(args);

        final var config = new HostCommandConfig();

        config.inputDir = project.parentDir.resolve("src").resolve("main").resolve("websrc");
        config.outputDir = project.getBuildTarget().resolve("classes").resolve("webbin");
        config.variables = environment;
        config.baseDir = project.parentDir;
        config.requestApiEnabled = !arguments.disableMockApi;
        config.replaceExtension = !arguments.keepExtensions;
        config.port = arguments.port;
        config.hostedPaths = new Path[] { toStaticFilesPath(project), toCompiledFilesPath(project) };
        config.requestApiSpecification = arguments.mockApiSpecification;
        config.validation = arguments.validation;
        config.watchedDirectories = arguments.watchedDirectories;
        config.jsCompressorType = arguments.jsCompiler;

        return config;
    }

    private static Path toStaticFilesPath(final Project project) {
        return project.getBuildTarget().resolve("classes").resolve("wwwroot");
    }
    private static Path toCompiledFilesPath(final Project project) {
        return project.getBuildTarget().resolve("classes").resolve("webbin");
    }

}
