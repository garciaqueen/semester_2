import java.util.*;
import java.io.*;
import java.nio.file.*;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular {
    // This is our "Dictionary". Code -> Description
    private final Map<String, String> cache = new HashMap<>();

    public ICDCodeTabularOptimizedForTime(String path) throws IOException {
        // Read the whole file into a list of strings
        List<String> lines = Files.readAllLines(Paths.get(path));

        // Loop starting from line 88 (index 87)
        for (int i = 87; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            // REGEX: ^[A-Z] (Starts with Letter), \\d{2} (Followed by 2 digits)
            if (line.matches("^[A-Z]\\d{2}.*")) {
                // Split by whitespace. Limit 2 means: [0] is Code, [1] is everything else
                String[] parts = line.split("\\s+", 2);

                if (parts.length == 2) {
                    // Store in Map for later instant retrieval
                    cache.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }

    @Override
    public String getDescription(String code) {
        // Map.get is O(1) - essentially zero wait time
        String desc = cache.get(code);

        // If the map returns null, the code doesn't exist in our dictionary
        if (desc == null) {
            throw new IndexOutOfBoundsException("Invalid ICD-10 code: " + code);
        }
        return desc;
    }
}