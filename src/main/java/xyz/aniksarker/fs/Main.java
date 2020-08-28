package xyz.aniksarker.fs;


public class Main {

    public static void main(String[] args) {

        // Creating a folder
        Folder folder1 = new Folder("folder1");
        Folder folderXyz = new Folder("folderXyz");
        Folder folderAbc = new Folder("folderAbc");

        // Creating a file
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        File file3 = new File("file3.txt");

        // Writing to a file
        file1.write("Some text");
        file2.write("Some important text");
        file3.write("Some more important text");

        // Adding folder to a folder
        folder1.add(folderXyz);
        folder1.add(folderAbc);

        // Adding file to a folder
        folder1.add(file1);
        folderXyz.add(file2);
        folderAbc.add(file3);

        // Reading content of a file
        System.out.println("Content of file1: " + file1.read());

        // Clearing content of a file
        file1.clear();

        // Getting the size of a file
        System.out.println("Size of file2: " + file2.size());

        // Getting the size of a folder
        System.out.println("Size of folder1: " + folder1.size());

        // Printing the names of files and folders inside a folder
        System.out.println("List for folder 1:");
        folder1.list();

        // Printing tree command like structure for a folder
        System.out.println("Tree for folder 1:");
        folder1.tree();

    }
}
