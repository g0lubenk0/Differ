package hexlet.code;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")

public final class App implements Callable<Integer> {
    @Parameters(
            index = "0",
            description = "path to first file",
            paramLabel = "filepath1"
    )
    private String filepath1;

    @Parameters(
            index = "1",
            description = "path to second file",
            paramLabel = "filepath2"
    )
    private String filepath2;

    @Option(
            names = {"-f", "--format"},
            description = "output format",
            paramLabel = "format",
            defaultValue = "stylish",
            showDefaultValue = CommandLine.Help.Visibility.ALWAYS
    )
    private String format;

    @Option(
            names = { "-h", "--help" },
            usageHelp = true,
            description = "Show this help message and exit."
    )
    private boolean helpRequested = false;

    @Option(
            names = { "-v", "--version" },
            versionHelp = true,
            description = "Print version information and exit."
    )
    private boolean versionRequested = false;
    

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        try {
            Differ.generate(filepath1, filepath2);
            return 1;
        } catch (Exception e) {
            throw new Exception("There is no file on provided path");
        }
    }
}
