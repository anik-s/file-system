package xyz.aniksarker.fs;


public class File extends Node {

    private String content;

    /**
     * Constructs a new File object with the specified name
     * @param name the name of the file
     */
    public File(String name) {
        super(name);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    /**
     * Returns the size of the string content for the file
     * @return the size of the string, if null 0 is returned
     */
    @Override
    public int size() {
        return (this.content == null) ? 0 : content.length();
    }

    /**
     * Writes string content to the file
     * @param content the string to be written in the file
     */
    public void write(String content) {
        this.content = content;
    }

    /**
     * Returns the string content
     * @return the string content
     */
    public String read() {
        return content;
    }

    /**
     * Clears the string content by setting it to null
     */
    public void clear() {
        content = null;
    }
}
