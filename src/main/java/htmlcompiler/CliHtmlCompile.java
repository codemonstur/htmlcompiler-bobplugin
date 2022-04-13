package htmlcompiler;

import bobthebuildtool.pojos.buildfile.Project;
import htmlcompiler.commands.Compile.CompileCommandConfig;
import htmlcompiler.compilers.JsCompiler;
import jcli.annotations.CliOption;
import jcli.errors.InvalidCommandLine;

import java.util.Map;

import static jcli.CliParserBuilder.newCliParser;

public class CliHtmlCompile {

    @CliOption(name = 'c', longName = "validation-config", defaultValue = "src/main/webcnf/validation.json")
    public String validation;
    @CliOption(name = 'e', longName = "keep-extension")
    public boolean keepExtensions;
    @CliOption(name = 'w', longName = "dont-walk-tree")
    public boolean dontWalkTree;
    @CliOption(name = 'j', longName = "js-compressor", defaultValue = "gcc-advanced", description = "Options: gcc-simple, gcc-bundle, gcc-whitespace, gcc-advanced, yui")
    public String jsCompiler;

    public static CompileCommandConfig newCompileCommandConfig(final Project project, final Map<String, String> environment, final String[] args) throws InvalidCommandLine {
        final CliHtmlCompile arguments = newCliParser(CliHtmlCompile::new).parse(args);

        final var config = new CompileCommandConfig();

        config.inputDir = project.parentDir.resolve("src").resolve("main").resolve("websrc");
        config.outputDir = project.getBuildTarget().resolve("classes").resolve("webbin");
        config.variables = environment;
        config.baseDir = project.parentDir;
        config.replaceExtension = !arguments.keepExtensions;
        config.validation = arguments.validation;
        config.recursive = !arguments.dontWalkTree;
        config.jsCompressorType =  arguments.jsCompiler;

        return config;
    }

}
