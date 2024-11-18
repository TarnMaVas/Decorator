package decorator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CachedDocument extends DocumentDecorator {
    private static final String DB_URL = 
        "jdbc:sqlite:src/main/resources/cache.db";

    public CachedDocument(Document document) {
        super(document);
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql =
"CREATE TABLE IF NOT EXISTS cache (filePath TEXT PRIMARY KEY, content TEXT)";
            stmt.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }

    @Override
    public String parse(String filePath) {
        String cachedContent = getCachedContent(filePath);
        if (cachedContent != null) {
            System.out.println("Retrieved cached content:\n");
            return cachedContent;
        }

        System.out.println("Document not found in cache:\n");
        String content = super.parse(filePath);
        cacheContent(filePath, content);
        return content;
    }

    private String getCachedContent(String filePath) {
        String sql = "SELECT content FROM cache WHERE filePath = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, filePath);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("content");
            }
        } catch (Exception e) {
            throw new RuntimeException(
                "Error retrieving cached content", e
                );
        }
        return null;
    }

    private void cacheContent(String filePath, String content) {
        String sql = 
        "INSERT OR REPLACE INTO cache (filePath, content) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, filePath);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error caching content", e);
        }
    }
}