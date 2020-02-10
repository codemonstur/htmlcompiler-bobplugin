package htmlcompiler;

import bobthebuildtool.pojos.buildfile.Project;
import htmlcompiler.commands.Host.HostCommandConfig;
import htmlcompiler.pojos.compile.CompilerType;
import jcli.annotations.CliOption;
import jcli.errors.InvalidCommandLine;

import java.nio.file.Path;
import java.util.Map;

import static jcli.CliParserBuilder.newCliParser;

public class CliHtmlHost {

    @CliOption(name = 'c', longName = "validation-config", defaultValue = "src/main/websrc/validation.json")
    public String validation;
    @CliOption(name = 't', longName = "type", defaultValue = "jsoup")
    public CompilerType type;
    @CliOption(name = 'e', longName = "keep-extension")
    public boolean keepExtensions;
    @CliOption(name = 'w', longName = "dont-walk-tree")
    public boolean dontWalkTree;

    @CliOption(name = 'p', longName = "port", defaultValue = "8080")
    public int port;
    @CliOption(longName = "disable-mock-api")
    public boolean disableMockApi;
    @CliOption(longName = "mock-api-spec", defaultValue = "src/main/websrc/requests.json")
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
        config.type = arguments.type;
        config.watchedDirectories = arguments.watchedDirectories;

        return config;
    }

    private static Path toStaticFilesPath(final Project project) {
        return project.getBuildTarget().resolve("classes").resolve("wwwroot");
    }
    private static Path toCompiledFilesPath(final Project project) {
        return project.getBuildTarget().resolve("classes").resolve("webbin");
    }

}
