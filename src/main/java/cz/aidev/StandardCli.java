package cz.aidev;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class StandardCli {

    private static final ObjectMapper JSON = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private static final Pattern GROUP_ID_PATTERN = Pattern.compile(
            "[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*");

    private static final Pattern ARTIFACT_ID_PATTERN = Pattern.compile(
            "[A-Za-z0-9][A-Za-z0-9._-]*");

    private record Artifact(String source, String target) {}

    private static final List<Artifact> ARTIFACTS = List.of(
            new Artifact("rules/GLOBAL_RULES.md", "rules/GLOBAL_RULES.md"),
            new Artifact("rules/SECURITY.md", "rules/SECURITY.md"),
            new Artifact("rules/CHANGE_POLICY.md", "rules/CHANGE_POLICY.md"),
            new Artifact(
                    "skills/project-analysis/SKILL.md",
                    ".devoxxgenie/skills/project-analysis/SKILL.md"),
            new Artifact(
                    "skills/java-implementation/SKILL.md",
                    ".devoxxgenie/skills/java-implementation/SKILL.md"),
            new Artifact(
                    "skills/testing/SKILL.md",
                    ".devoxxgenie/skills/testing/SKILL.md"),
            new Artifact(
                    "skills/code-review/SKILL.md",
                    ".devoxxgenie/skills/code-review/SKILL.md"),
            new Artifact(
                    "profiles/java-21-maven/project-rules.md",
                    "profiles/java-21-maven/project-rules.md"),
            new Artifact(
                    "profiles/java-21-maven/quality-gates.md",
                    "profiles/java-21-maven/quality-gates.md"),
            new Artifact("templates/java-maven/AGENTS.md", "AGENTS.md")
    );

    private StandardCli() {
    }

    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void run(String[] args) throws Exception {
        if (args.length == 0) {
            usage();
            return;
        }

        String command = args[0];
        Map<String, String> options = parseOptions(args);

        switch (command) {
            case "init" -> {
                Path root = requiredPath(options, "standard-root");
                Path target = requiredPath(options, "target");
                String name = options.getOrDefault(
                        "name", target.getFileName().toString());
                String groupId = options.getOrDefault(
                        "group-id", "cz.example");

                initialize(root, target, name, groupId);
            }
            case "install" -> {
                Path root = requiredPath(options, "standard-root");
                Path target = requiredPath(options, "target");
                install(root, target);
            }
            case "verify" -> {
                Path target = requiredPath(options, "target");
                verify(target);
            }
            default -> throw new IllegalArgumentException(
                    "Neznámý příkaz: " + command);
        }
    }

    private static Map<String, String> parseOptions(String[] args) {
        Map<String, String> result = new LinkedHashMap<>();

        for (int i = 1; i < args.length; i++) {
            String argument = args[i];

            if (!argument.startsWith("--") || argument.length() == 2) {
                throw new IllegalArgumentException(
                        "Neplatný argument: " + argument);
            }

            if (i + 1 >= args.length || args[i + 1].startsWith("--")) {
                throw new IllegalArgumentException(
                        "Chybí hodnota parametru: " + argument);
            }

            String key = argument.substring(2);

            if (result.containsKey(key)) {
                throw new IllegalArgumentException(
                        "Duplicitní parametr: " + argument);
            }

            result.put(key, args[++i]);
        }

        return result;
    }

    private static Path requiredPath(
            Map<String, String> options, String key) {
        String value = options.get(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Chybí parametr --" + key);
        }

        return Path.of(value).toAbsolutePath().normalize();
    }

    private static void initialize(
            Path standardRoot,
            Path target,
            String name,
            String groupId) throws Exception {

        Path root = standardRoot.toRealPath();

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Název projektu nesmí být prázdný.");
        }

        validateGroupId(groupId);

        String artifactId = toArtifactId(name);
        validateArtifactId(artifactId);

        if (Files.exists(target) && !Files.isDirectory(target)) {
            throw new IOException(
                    "Cílová cesta není adresář: " + target);
        }

        if (Files.exists(target) && directoryHasContent(target)) {
            throw new IOException(
                    "Cílový adresář není prázdný: " + target);
        }

        validateSources(root);
        Files.createDirectories(target);
        install(root, target);
        generatePom(root, target, name, groupId, artifactId);

        Path readme = target.resolve("README.md");
        if (!Files.exists(readme)) {
            Files.writeString(
                    readme,
                    "# " + name + "\n\n"
                            + "Java 21 project managed by "
                            + "AI Development Standard.\n");
        }

        registerGeneratedFile(target, "pom.xml");

        System.out.println("Projekt inicializován: " + target);
        System.out.println("Group ID: " + groupId);
        System.out.println("Artifact ID: " + artifactId);
    }
    private static void validateSources(Path root) throws IOException {
        for (Artifact artifact : ARTIFACTS) {
            Path source = resolveSafe(root, artifact.source());

            if (!Files.isRegularFile(source)) {
                throw new IOException(
                        "Chybějící zdrojový soubor: "
                                + artifact.source());
            }
        }

        Path template = resolveSafe(
                root, "templates/java-maven/pom.xml.template");

        if (!Files.isRegularFile(template)) {
            throw new IOException(
                    "Chybí Maven šablona: " + template);
        }

        Path version = resolveSafe(root, "VERSION");

        if (!Files.isRegularFile(version)) {
            throw new IOException("Chybí soubor VERSION.");
        }
    }

    private static void install(
            Path standardRoot, Path target) throws Exception {

        Path root = standardRoot.toRealPath();

        if (!Files.isDirectory(target)) {
            throw new IOException(
                    "Cílový adresář neexistuje: " + target);
        }

        Path project = target.toRealPath();

        validateSources(root);

        // Nejprve ověříme všechny cílové soubory.
        // Při konfliktu se tak nezačne s částečnou instalací.
        for (Artifact artifact : ARTIFACTS) {
            Path destination = resolveSafe(
                    project, artifact.target());

            rejectSymlinkParents(project, destination);

            if (Files.exists(destination)) {
                throw new IOException(
                        "Soubor již existuje; přepis odmítnut: "
                                + destination);
            }
        }

        Map<String, String> hashes = new LinkedHashMap<>();

        for (Artifact artifact : ARTIFACTS) {
            Path source = resolveSafe(root, artifact.source());
            Path destination = resolveSafe(
                    project, artifact.target());

            Files.createDirectories(destination.getParent());

            Files.copy(
                    source,
                    destination,
                    StandardCopyOption.COPY_ATTRIBUTES);

            hashes.put(
                    artifact.target(),
                    sha256(destination));

            System.out.println(
                    "INSTALLED: " + artifact.target());
        }

        writeMetadata(root, project, hashes);

        System.out.println(
                "Standard nainstalován do: " + project);
    }

    private static Path resolveSafe(
            Path root, String relative) throws IOException {

        Path path = root.resolve(relative).normalize();

        if (!path.startsWith(root)) {
            throw new IOException(
                    "Cesta opouští povolený adresář: " + relative);
        }

        return path;
    }

    private static void rejectSymlinkParents(
            Path root, Path destination) throws IOException {

        Path current = root;

        for (Path part : root.relativize(destination)) {
            current = current.resolve(part);

            if (Files.isSymbolicLink(current)) {
                throw new IOException(
                        "Symbolický odkaz není povolen: " + current);
            }
        }
    }

    private static void generatePom(
            Path root,
            Path target,
            String name,
            String groupId,
            String artifactId) throws IOException {

        Path template = root.resolve(
                "templates/java-maven/pom.xml.template");

        String pom = Files.readString(template)
                .replace("{{GROUP_ID}}", xmlEscape(groupId))
                .replace("{{ARTIFACT_ID}}", xmlEscape(artifactId))
                .replace("{{PROJECT_NAME}}", xmlEscape(name));

        if (pom.contains("{{GROUP_ID}}")
                || pom.contains("{{ARTIFACT_ID}}")
                || pom.contains("{{PROJECT_NAME}}")) {
            throw new IOException(
                    "Maven šablona obsahuje nevyřešené placeholdery.");
        }

        Path destination = target.resolve("pom.xml");

        if (Files.exists(destination)) {
            throw new IOException(
                    "pom.xml již existuje: " + destination);
        }

        Files.writeString(destination, pom);

        System.out.println("GENERATED: pom.xml");
    }

    private static void registerGeneratedFile(
            Path target, String relative) throws Exception {

        Path metadata = target.resolve(".ai-standard");
        Path checksumsFile = metadata.resolve("checksums.json");
        Path manifestFile = metadata.resolve("manifest.json");

        ObjectNode checksums = (ObjectNode)
                JSON.readTree(checksumsFile.toFile());

        checksums.put(
                relative,
                sha256(target.resolve(relative)));

        JSON.writeValue(
                checksumsFile.toFile(),
                checksums);

        ObjectNode manifest = (ObjectNode)
                JSON.readTree(manifestFile.toFile());

        JsonNode managedFiles = manifest.get("managedFiles");

        if (!(managedFiles instanceof ArrayNode files)) {
            throw new IOException(
                    "Manifest neobsahuje pole managedFiles.");
        }

        boolean exists = false;

        for (JsonNode file : files) {
            if (relative.equals(file.asText())) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            files.add(relative);
        }

        JSON.writeValue(
                manifestFile.toFile(),
                manifest);
    }

    private static void writeMetadata(
            Path root,
            Path target,
            Map<String, String> hashes) throws Exception {

        Path metadata = target.resolve(".ai-standard");

        Files.createDirectories(metadata);

        Map<String, Object> manifest = new LinkedHashMap<>();

        manifest.put("schemaVersion", 1);
        manifest.put("standard", "ai-development-standard");
        manifest.put(
                "standardVersion",
                Files.readString(
                        root.resolve("VERSION")).trim());
        manifest.put("profile", "java-21-maven");
        manifest.put("agentIntegration", "devoxxgenie");
        manifest.put("skills", List.of(
                "project-analysis",
                "java-implementation",
                "testing",
                "code-review"));
        manifest.put(
                "managedFiles",
                List.copyOf(hashes.keySet()));

        JSON.writeValue(
                metadata.resolve("manifest.json").toFile(),
                manifest);

        JSON.writeValue(
                metadata.resolve("checksums.json").toFile(),
                hashes);
    }

    private static void verify(Path target) throws Exception {
        Path project = target.toRealPath();
        Path metadata = project.resolve(".ai-standard");

        Path manifestFile = metadata.resolve("manifest.json");
        Path checksumsFile = metadata.resolve("checksums.json");

        if (!Files.isRegularFile(manifestFile)
                || !Files.isRegularFile(checksumsFile)) {
            throw new IOException(
                    "Chybí manifest nebo checksums.json.");
        }

        JsonNode manifest = JSON.readTree(
                manifestFile.toFile());

        JsonNode checksums = JSON.readTree(
                checksumsFile.toFile());

        if (!checksums.isObject()
                || !manifest.path("managedFiles").isArray()) {
            throw new IOException(
                    "Neplatná struktura manifestu "
                            + "nebo checksums.json.");
        }

        boolean failed = false;

        for (JsonNode managed : manifest.path("managedFiles")) {
            String relative = managed.asText();

            if (!checksums.has(relative)) {
                System.err.println(
                        "MISSING CHECKSUM: " + relative);
                failed = true;
            }
        }

        for (var entry : checksums.properties()) {
            String relative = entry.getKey();
            String expected = entry.getValue().asText();

            Path file = resolveSafe(project, relative);

            if (Files.isSymbolicLink(file)
                    || !Files.isRegularFile(file)) {
                System.err.println("MISSING: " + relative);
                failed = true;
                continue;
            }

            String actual = sha256(file);

            if (!actual.equals(expected)) {
                System.err.println("MODIFIED: " + relative);
                failed = true;
            } else {
                System.out.println("OK: " + relative);
            }
        }

        if (failed) {
            throw new IllegalStateException(
                    "Kontrola integrity selhala.");
        }

        System.out.println(
                "Standard "
                        + manifest.path("standardVersion").asText()
                        + " je integritně v pořádku.");
    }

    private static boolean directoryHasContent(Path path)
            throws IOException {

        try (Stream<Path> files = Files.list(path)) {
            return files.findAny().isPresent();
        }
    }

    private static void validateGroupId(String groupId) {
        if (groupId == null
                || !GROUP_ID_PATTERN.matcher(groupId).matches()) {
            throw new IllegalArgumentException(
                    "Neplatný Maven groupId: " + groupId);
        }
    }

    private static void validateArtifactId(String artifactId) {
        if (artifactId == null
                || !ARTIFACT_ID_PATTERN.matcher(artifactId).matches()) {
            throw new IllegalArgumentException(
                    "Neplatný Maven artifactId: " + artifactId);
        }
    }

    private static String toArtifactId(String name) {
        return name.trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9._-]+", "-")
                .replaceAll("^[^a-z0-9]+", "")
                .replaceAll("[.-]+$", "");
    }

    private static String xmlEscape(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private static String sha256(Path file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        return HexFormat.of().formatHex(
                digest.digest(Files.readAllBytes(file)));
    }

    private static void usage() {
        System.out.println("""
                AI Development Standard CLI

                init --standard-root PATH --target PATH
                     [--name NAME] [--group-id GROUP_ID]

                install --standard-root PATH --target PATH

                verify --target PATH

                Default groupId: cz.example
                """);
    }
}