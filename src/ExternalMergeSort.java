import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by djanzen on 2/27/16.
 */
public class ExternalMergeSort {
    public static void main(String [] args){
        List<String> tempFileNames = new ArrayList<>();
        File inputFile = new File("text.txt");
        Scanner fileScan;
        try {
            fileScan = new Scanner(inputFile);
            chunk(fileScan);
        } catch(IOException io){
            System.out.print("File not found");
        }



    }//main

    public static void chunk(Scanner fileScan){
        
    }
}//class
