package decorator;

public class Main {
    public static void main(String[] args) {
        Document document = new SmartDocument();
        Document timedDocument = new TimedDocument(document);
        Document cachedDocument = new CachedDocument(document);

        System.out.println(timedDocument.parse("src/main/resources/images.png"));

        System.out.println(cachedDocument.parse("src/main/resources/images.png"));
    }
}