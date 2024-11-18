package decorator;

public class TimedDocument extends DocumentDecorator {

    public TimedDocument(Document document) {
        super(document);
    }

    @Override
    public String parse(String filePath) {
        long startTime = System.currentTimeMillis();
        String result = super.parse(filePath);
        long endTime = System.currentTimeMillis();

        System.out.println(
            "Time taken: " + (endTime - startTime) + " milliseconds.\n"
            );

        return result;
    }
}