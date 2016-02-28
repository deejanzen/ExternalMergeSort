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
        //get input file to string for cmd line args

//        //insertionSort testing
//        String [] temp = {"e","d","a","c","b"};
//        for (int i =0;i< temp.length;i++){
//            System.out.print(temp[i]);
//        }
//        System.out.println();
//        temp = insertionSort(temp);
//        for (int i =0;i< temp.length;i++){
//            System.out.print(temp[i]);
//        }

        File inputFile = new File("text.txt");
        Scanner fileScan;
        int chunkID;
        try {
            fileScan = new Scanner(inputFile);
            chunkID = sort(fileScan);
            //TODO if chunks are <2 write to output now
            merge(chunkID /*, outputfile*/);


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
            if (count % 16 == 0) {
                count = 0;
                aux = insertionSort(aux);
                //call? write to disk
                //numberChunks++
                //written = true;
                //reset array aux = new String[16];

            }
            if ( !fileScan.hasNext() && !written ){
                //if /length < 2 dont sort?
                //call insertionSort(aux);
                //call? write to disk
                //numberChunks++
            }

            written = false;
        }

        return numberChunks;
    }//sort

    public static String[] insertionSort(String[] aux){
        String key;
        int j;
        for (int i = 1; i < aux.length; i++){
            key = aux[i];
            j = i;
            while(j > 0 && key.compareTo(aux[j-1]) < 0 ){
                aux[j] = aux[j-1];
                j--;
            }
            aux[j] = key;
        }
        return aux;
    }//insertionSort

    public static void merge(int chunkID /*, outputfile*/){
        //TODO go through ids merging then write to output
    }//merge
}//class
