package tr.havelsan.ueransim.app.app.entry;

import picocli.CommandLine;
import tr.havelsan.ueransim.app.app.cli.CliOpt;
import tr.havelsan.ueransim.app.common.GnbId;
import tr.havelsan.ueransim.app.common.Supi;
import tr.havelsan.ueransim.app.common.sw.SwCommandMetadata;
import tr.havelsan.ueransim.app.common.sw.SwConfigMetadata;
import tr.havelsan.ueransim.app.common.sw.SwIntervalMetadata;
import tr.havelsan.ueransim.app.common.sw.SwLogMetadata;
import tr.havelsan.ueransim.utils.Severity;
import tr.havelsan.ueransim.utils.Tag;
import tr.havelsan.ueransim.utils.octets.OctetString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Metadata {

    public static final SwCommandMetadata COMMAND_METADATA = new SwCommandMetadata(new CommandMetadata());
    public static final SwConfigMetadata CONFIG_METADATA = new SwConfigMetadata(new ConfigMetadata());
    public static final SwIntervalMetadata INTERVAL_METADATA = new SwIntervalMetadata(new IntervalMetadata());
    public static final SwLogMetadata LOG_METADATA = new SwLogMetadata(Severity.values(), Tag.values());

    public static class CommandMetadata {
        public final List<CommandInfo> commands;

        public CommandMetadata() {
            commands = new ArrayList<>();

            var rootCommand = new CommandLine(new CliOpt.RootCommand());
            for (var subCommand : rootCommand.getSubcommands().values()) {
                if (!subCommand.getSubcommands().isEmpty())
                    continue;

                var commandCls = subCommand.getCommand().getClass();
                var name = subCommand.getCommandName();
                var spec = subCommand.getCommandSpec();
                var command = (CommandLine.Command) commandCls.getAnnotation(CommandLine.Command.class);
                var description = String.join(" ", command.description());
                var parameters = new ArrayList<ParameterInfo>();

                for (var param : spec.positionalParameters()) {
                    parameters.add(getParameterInfo(param));
                }

                for (var option : spec.options()) {
                    if (option.usageHelp() || option.versionHelp())
                        continue;
                    parameters.add(getParameterInfo(option));
                }

                boolean isQuery = commandCls.getAnnotation(CliOpt.CommandInfo.class).isQuery();

                // TODO: help
                commands.add(new CommandInfo(name, description, isQuery, parameters));
            }
        }

        private ParameterInfo getParameterInfo(CommandLine.Model.ArgSpec arg) {
            var name = arg.paramLabel();
            if (name.startsWith("<"))
                name = name.substring(1);
            if (name.endsWith(">"))
                name = name.substring(0, name.length() - 1);
            var description = String.join(" ", arg.description());
            var type = getParamType(arg.type());
            var isPositional = arg.isPositional();
            var isRequired = arg.required();
            return new ParameterInfo(name, description, type, isPositional, isRequired);
        }

        private ParameterType getParamType(Class<?> cls) {
            if (cls == boolean.class)
                return ParameterType.BOOL;
            if (cls == int.class || cls == long.class)
                return ParameterType.INTEGER;
            if (cls == float.class || cls == double.class)
                return ParameterType.FLOAT;
            if (cls == String.class)
                return ParameterType.TEXT;
            if (cls == OctetString.class)
                return ParameterType.OCTET_STRING;
            if (cls == File.class)
                return ParameterType.FILE;
            if (cls == Supi.class)
                return ParameterType.IMSI;
            if (cls == GnbId.class)
                return ParameterType.GNB_ID;
            throw new IllegalArgumentException();
        }
    }

    private static class CommandInfo {
        public final String name;
        public final String description;
        public final boolean isQuery;
        public final List<ParameterInfo> parameters;

        public CommandInfo(String name, String description, boolean isQuery, List<ParameterInfo> parameters) {
            this.name = name;
            this.description = description;
            this.isQuery = isQuery;
            this.parameters = parameters;
        }
    }

    private static class ParameterInfo {
        public final String name;
        public final String description;
        public final ParameterType type;
        public final boolean isPositional;
        public final boolean isRequired;

        public ParameterInfo(String name, String description, ParameterType type, boolean isPositional, boolean isRequired) {
            this.name = name;
            this.description = description;
            this.type = type;
            this.isPositional = isPositional;
            this.isRequired = isRequired;
        }
    }

    private enum ParameterType {
        BOOL,
        INTEGER,
        FLOAT,
        TEXT,
        IMSI,
        GNB_ID,
        OCTET_STRING,
        FILE,
    }

    public static class ConfigMetadata {
        private final ConfigNode root;

        public ConfigMetadata() {
            this.root = new ConfigNode();
            var file = new File("config");
            buildTree(this.root, file);
        }

        private void buildTree(ConfigNode cursor, File file) {
            cursor.path = file.getPath();
            cursor.name = file.getName();

            if (file.isDirectory()) {
                cursor.children = new ArrayList<>();
                var children = Objects.requireNonNull(file.listFiles());
                Arrays.sort(children);
                for (var child: children) {
                    var node = new ConfigNode();
                    cursor.children.add(node);
                    buildTree(node, child);
                }
            } else {
                try {
                    cursor.content = Files.readString(Path.of(file.getPath()));
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static class ConfigNode {
        public String path;
        public String name;
        public ArrayList<ConfigNode> children;
        public String content;
    }

    public static class IntervalMetadata {
        public final IntervalInfo registration;
        public final IntervalInfo phase1;
        public final IntervalInfo authentication;
        public final IntervalInfo phase2;
        public final IntervalInfo securityModeControl;
        public final IntervalInfo phase3;
        public final IntervalInfo deregistration;

        private IntervalMetadata() {
            this.registration = new IntervalInfo("registration", null, getIntervalDisplay("registration"));
            this.phase1 = new IntervalInfo("phase1", "registration", getIntervalDisplay("phase1"));
            this.authentication = new IntervalInfo("authentication", "registration", getIntervalDisplay("authentication"));
            this.phase2 = new IntervalInfo("phase2", "registration", getIntervalDisplay("phase2"));
            this.securityModeControl = new IntervalInfo("securityModeControl", "registration", getIntervalDisplay("securityModeControl"));
            this.phase3 = new IntervalInfo("phase3", "registration", getIntervalDisplay("phase3"));
            this.deregistration = new IntervalInfo("deregistration", null, getIntervalDisplay("deregistration"));
        }

        public static String getIntervalDisplay(String id) {
            switch (id) {
                case "registration":
                    return "Registration";
                case "phase1":
                    return "Phase 1 (Registration-Authentication)";
                case "authentication":
                    return "Authentication";
                case "phase2":
                    return "Phase 2 (Authentication-SecurityModeControl)";
                case "securityModeControl":
                    return "Security Mode Control";
                case "phase3":
                    return "Phase 3 (SecurityModeControl-RegistrationAccept)";
                case "deregistration":
                    return "De-Registration";
                default:
                    return id;
            }
        }
    }

    private static class IntervalInfo {
        public final String id;
        public final String parent;
        public final String display;

        public IntervalInfo(String id, String parent, String display) {
            this.id = id;
            this.parent = parent;
            this.display = display;
        }
    }
}
