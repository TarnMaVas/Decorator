package decorator;


public abstract class DocumentDecorator implements Document {
    private final Document document;

    public DocumentDecorator(Document document) {
        this.document = document;
    }

    @Override
    public String parse(String filePath) {
        return document.parse(filePath);
    };
    
}
