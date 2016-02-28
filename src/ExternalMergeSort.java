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
        //output file from args
        //change file to string for cmd line args
        File inputFile = new File("text.txt");
        Scanner fileScan;
        int chunckID;
        try {
            fileScan = new Scanner(inputFile);
            chunckID = sort(fileScan);
            //TODO if chunks are <2 write to output now


        } catch(IOException io){
            System.out.print("File not found");
        }




    }//main

    //xms.tmp.pass_0001.chunk_0000
    public static int sort(Scanner fileScan){
        String [] aux = new String[16];
        boolean written = false;
        int numberChunks = 0;
        int count = 0;
        while(fileScan.hasNext()){
            aux[count] = fileScan.next();
            count += 1;
            if (count % 15 == 0) {
                count = 0;
                //call insertionSort(aux);
                //call? write to disk
                //numberChunks++
                //written = true;
                //reset array aux = new String[16];

            }
            if ( !fileScan.hasNext() && !written ){
                //call insertionSort(aux);
                //call? write to disk
                //numberChunks++
            }

            written = false;
        }

        return numberChunks;
    }
}//class
