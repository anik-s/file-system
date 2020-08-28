package xyz.aniksarker.fs;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import xyz.aniksarker.fs.exception.InvalidOperationException;
import xyz.aniksarker.fs.exception.NameExistsException;
import xyz.aniksarker.fs.exception.NodeExistsException;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class FolderTest {

    private  final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private  final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    public FolderTest() {

    }

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void size_EmptyFolder_ShouldReturnZero() {
        Folder folder1 = new Folder("folder1");
        assertEquals(0, folder1.size());
    }

    @Test
    void size_HasAnEmptyFolder_ShouldReturnZero() {
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        folder1.add(folder2);
        assertEquals(0, folder1.size());
    }

    @Test
    void size_HasAnEmptyFile_ShouldReturnZero() {
        Folder folder1 = new Folder("folder1");
        File file1 = new File("file.txt");
        folder1.add(file1);
        assertEquals(0, folder1.size());
    }

    @Test
    void size_HasAFileWithContent_ShouldReturnCorrectSize() {
        Folder folder1 = new Folder("folder1");
        File file1 = new File("file.txt");
        file1.write("some text");
        folder1.add(file1);
        assertEquals(9, folder1.size());
    }

    @Test
    void size_FolderContainsAnEmptyFolderAndAFileWithContent_ShouldReturnCorrectSize() {
        Folder folder1 = new Folder("folder1");
        File file1 = new File("file.txt");
        file1.write("some text");
        folder1.add(file1);
        Folder folder2 = new Folder("folder2");
        folder1.add(folder2);
        assertEquals(9, folder1.size());
    }

    @Test
    void size_FolderContainsAFileWithContentAndAFolderThatHasANonEmptyFile_ShouldReturnCorrectSize() {
        Folder folder1 = new Folder("folder1");
        File file1 = new File("file.txt");
        file1.write("some text");
        folder1.add(file1);

        Folder folder2 = new Folder("folder2");
        folder1.add(folder2);

        File file2 = new File("file2.txt");
        file2.write("abcd");
        folder2.add(file2);

        assertEquals(13, folder1.size());
    }


    @Test
    void add_FolderIsAdded_ShouldBeInChildrenListAndHaveCorrectParent() {
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        folder1.add(folder2);
        assertEquals(true, folder1.getChildren().contains(folder2));
        assertEquals(folder1, folder2.getParent());
    }

    @Test
    void add_AFileIsAdded_ShouldBeInChildrenListAndHaveCorrectParent() {
        Folder folder1 = new Folder("folder1");
        File file1 = new File("fil1.txt");
        folder1.add(file1);
        assertEquals(true, folder1.getChildren().contains(file1));
        assertEquals(folder1, file1.getParent());
    }

    @Test
    void add_AFileIsAddedToAFolderWhichPreviouslyBelongedToAnotherFolder_ShouldBeInChildrenListAndHaveCorrectParent() {
        Folder folder1 = new Folder("folder1");
        File file1 = new File("fil1.txt");
        folder1.add(file1);

        Folder folder2 = new Folder("folder2");

        folder2.add(file1);

        assertEquals(false, folder1.getChildren().contains(file1));
        assertEquals(true, folder2.getChildren().contains(file1));
        assertEquals(folder2, file1.getParent());
    }

    @Test
    void add_DuplicateNameGiven_ShouldThrowException() {
        assertThrows(NodeExistsException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Folder folder1 = new Folder("folder1");
                Folder folder2 = new Folder("folder2");
                folder1.add(folder2);
                Folder folder3 = new Folder("folder2");
                folder1.add(folder3);
            }
        });

    }

    @Test
    void add_ExistingNodeGiven_ShouldThrowException() {
        assertThrows(NodeExistsException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Folder folder1 = new Folder("folder1");
                Folder folder2 = new Folder("folder2");
                folder1.add(folder2);
                folder1.add(folder2);
            }
        });

    }

    @Test
    void add_ParentChildSameNodeGiven_ShouldThrowException() {
        assertThrows(InvalidOperationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Folder folder1 = new Folder("folder1");
                folder1.add(folder1);
            }
        });

    }

    @Test
    void list_FolderContainsNothing_ShouldPrintEmpty() {
        captureOut();
        Folder folder1 = new Folder("folder1");
        folder1.list();
        String theOutput = getOut();
        assertEquals("", theOutput.trim());
    }

    @Test
    void list_FolderContainsAFileAndAFolder_ShouldPrintTheirNames() {
        captureOut();
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        File file1 = new File("file1.txt");
        file1.write("Anik");
        folder1.add(folder2);
        folder1.add(file1);
        folder1.list();
        String theOutput = getOut();
        assertEquals("file1.txt\nfolder2", theOutput.trim());
    }

    @Test
    void list_FolderContainsAFileAndAFolder_ShouldPrintTheirNames2() {
        captureOut();
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        File file1 = new File("file1.txt");
        file1.write("Anik");
        folder1.add(folder2);
        folder1.add(file1);
        File file2 = new File("file2.txt");
        folder2.add(file2);
        folder1.list();
        String theOutput = getOut();
        assertEquals("file1.txt\nfolder2", theOutput.trim());
    }

    @Test
    void tree__FolderContainsAFileAndASubFoldersWhichContainsFile_ShouldPrintTheirNamesInTreeStructure() {
        captureOut();
        Folder folder1 = new Folder("folder1");
        Folder folderXyz = new Folder("xyz");
        Folder folderAbc = new Folder("abc");

        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        folder1.add(folderXyz);
        folder1.add(folderAbc);
        folder1.add(file1);

        folderAbc.add(file2);

        folder1.tree();

        String theOutput = getOut();
        assertEquals("|__abc\n" +
                "|  |__file2.txt\n" +
                "|__file1.txt\n" +
                "|__xyz", theOutput.trim());
    }


    @Test
    void tree__FolderContainsFilesAndFolders_ShouldPrintTheirNamesInTreeStructure() {
        captureOut();
        Folder folder1 = new Folder("folder1");
        Folder folderXyz = new Folder("xyz");
        Folder folderAbc = new Folder("abc");

        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");

        folder1.add(folderXyz);
        folder1.add(folderAbc);
        folder1.add(file1);
        folder1.add(file2);

        folder1.tree();

        String theOutput = getOut();
        assertEquals("|__abc\n" +
                "|__file1.txt\n" +
                "|__file2.txt\n" +
                "|__xyz", theOutput.trim());
    }

    @Test
    void tree_FolderContainsNothing_ShouldPrintEmpty() {
        captureOut();
        Folder folder1 = new Folder("folder1");
        folder1.list();
        String theOutput = getOut();
        assertEquals("", theOutput.trim());
    }

    @Test
    void nodeExists_FolderContainsAnotherFolder_shouldReturnTrue() {
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        folder1.add(folder2);
        assertEquals(true, folder1.getChildren().contains(folder2));
    }

    @Test
    void nodeExists_FolderDoesNotContainsFolder_shouldReturnFalse() {
        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");
        assertEquals(false, folder1.getChildren().contains(folder2));
    }


    /**
     * Turns on stdOut output capture
     */
    private void captureOut() {
        System.setOut( new PrintStream( outContent ) );
    }

    /**
     * Turns on stdErr output capture
     */
    private void captureErr() {
        System.setErr( new PrintStream( errContent ) );
    }

    /**
     * Turns off stdOut capture and returns the contents
     * that have been captured
     *
     * @return
     */
    private String getOut() {
        System.setOut( new PrintStream( new FileOutputStream( FileDescriptor.out ) ) );
        return outContent.toString().replaceAll( "\r", "" );

    }

    /**
     * Turns off stdErr capture and returns the contents
     * that have been captured
     *
     * @return
     */
    private String getErr() {
        System.setErr( new PrintStream( new FileOutputStream( FileDescriptor.out ) ) );
        return errContent.toString().replaceAll( "\r", "" );
    }




}