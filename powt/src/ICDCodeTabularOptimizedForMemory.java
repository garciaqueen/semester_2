import java.io.*;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular {
    private final String filePath;

    public ICDCodeTabularOptimizedForMemory(String path) {
        this.filePath = path;
    }

    @Override
    public String getDescription(String code) {
        // "Try-with-resources" ensures the file CLOSES even if the code crashes
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                // Skip the first 87 lines of "junk" text
                if (lineNumber < 88) continue;

                line = line.trim();
                // Same logic: Look for lines starting with a valid code
                if (line.matches("^[A-Z]\\d{2}.*")) {
                    String[] parts = line.split("\\s+", 2);

                    // Check if THIS line is the code the user is looking for
                    if (parts.length == 2 && parts[0].equals(code)) {
                        return parts[1].trim(); // Found it! Exit method immediately
                    }
                }
            }
        } catch (IOException e) {
            // Handle file errors (like file missing)
            e.printStackTrace();
        }

        // If the loop finished and we didn't 'return', the code wasn't found
        throw new IndexOutOfBoundsException("Code not found in file: " + code);
    }
}