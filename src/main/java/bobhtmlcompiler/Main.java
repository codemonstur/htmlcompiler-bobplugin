package bobhtmlcompiler;

import bobthebuildtool.pojos.buildfile.Project;
import bobthebuildtool.pojos.internal.DescriptionAndInterface;

public enum Main {;

    public static void installPlugin(final Project project) {
        project.commands.put("hc", new DescriptionAndInterface<>("Compiles templates and plain HTML to compiled HTML"
                , (project1, environment, args) -> {

        }));
    }

}
