package xyz.aniksarker.fs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    @Test
    void size_EmptyFile_ShouldReturnZero() {
        File file1 = new File("file1.txt");
        assertEquals(0, file1.size());
    }

    @Test
    void size_ContentWrittenOnFile_ShouldReturnCorrectSize() {
        File file1 = new File("file1.txt");
        file1.write("Test text");
        assertEquals(9, file1.size());
    }

    @Test
    void write_ContentWritten_ContentShouldSetToCorrectValue() {
        File file1 = new File("file1.txt");
        file1.write("Test text 1");
        assertEquals("Test text 1", file1.getContent());
    }

    @Test
    void write_EmptyContentWritten_ContentShouldSetToCorrectValue() {
        File file1 = new File("file1.txt");
        file1.write("");
        assertEquals("", file1.getContent());
    }

    @Test
    void read_NothingWrittenShouldReturnNull() {
        File file1 = new File("file1.txt");
        assertEquals(null, file1.read());
    }

    @Test
    void read_ContentWrittenShouldReturnCorrectContent() {
        File file1 = new File("file1.txt");
        file1.write("Test text");
        assertEquals("Test text", file1.read());
    }

    @Test
    void read_EmptyContentWrittenShouldReturnEmptyContent() {
        File file1 = new File("file1.txt");
        file1.write("");
        assertEquals("", file1.read());
    }

    @Test
    void clear_ClearNullContentFile_FileContentShouldBeNull() {
            File file1 = new File("file1.txt");
            file1.clear();
            assertEquals(null, file1.getContent());
    }

    @Test
    void clear_ClearWrittenFile_FileContentShouldBeNull() {
        File file1 = new File("file1.txt");
        file1.write("some text");
        file1.clear();
        assertEquals(null, file1.getContent());
    }

}