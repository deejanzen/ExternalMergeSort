import com.sun.javafx.binding.StringFormatter;

import java.io.File;
import java.io.FileWriter;
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
        List<String> files= new ArrayList<>();

        File inputFile = new File("text.txt");
        Scanner fileScan;
        int chunkID;
        try {
            fileScan = new Scanner(inputFile);
            files = sort(fileScan);
            if (files != null) merge(files,  new String("outputMerge") );
        } catch(IOException io){
            System.out.print("File not found main");
        }

    }//main

    public static List<String> sort(Scanner fileScan){
        List<String> temps= new ArrayList<>();
        String tempFile;
        String output;
        String [] aux = new String[16];
        boolean written = false;
        int numberChunks = 0;
        int count = 0;
        while(fileScan.hasNext()){
            aux[count] = fileScan.next();
            count += 1;
            if (count % 16 == 0) {
                aux = insertionSort(aux, count);
                tempFile = String.format("xms.tmp.pass_0000.chunk_%04d", numberChunks);
                temps.add(tempFile);
                //TODO if !fileScan.hasNext() && numberChunks < 2 write directly to output
                if (!fileScan.hasNext() && numberChunks < 1) {
                    writeSort(new String("output"), aux, count);
                    return null;
                }
                writeSort(tempFile, aux, count);
                numberChunks++;
                written = true;
                aux = new String[16];
                count = 0;


            }
            if ( !fileScan.hasNext() && !written ){
                if (count > 1) aux = insertionSort(aux, count);
                tempFile = String.format("xms.tmp.pass_0000.chunk_%04d", numberChunks);
                temps.add(tempFile);
                //TODO if !fileScan.hasNext() && numberChunks < 2 write directly to output
                if (!fileScan.hasNext() && numberChunks < 1) {
                    writeSort(new String("output"), aux, count);
                    return null;
                }
                writeSort(tempFile, aux, count);
                numberChunks++;
            }

            written = false;
        }

        return temps;
    }//sort

    public static String[] insertionSort(String[] aux, int count){
        String key;
        int j;
        for (int i = 1; i < count; i++){
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

    public static void writeSort(String fileName, String [] aux, int count){
        //String s = String.format("Duke's Birthday: %1$tm %1$te,%1$tY", c);
//        String fileName = String.format("xms.tmp.pass_0000.chunk_%04d", chunkID);
        try {
            FileWriter writer = new FileWriter(new File(fileName), true);
            for (int i = 0; i < count;i++){
                writer.write(aux[i]);
                writer.write(System.lineSeparator());

            }
            writer.close();

        }catch (IOException e){
           System.out.print("No FileWriter file");
        }
    }

    public static void merge(List<String> files, String output) throws IOException {
        //TODO go through ids merging then write to output
        List<String> temp = new ArrayList<String>();
        String tempFile;
        int passID = 1;
        int filesToMerge = files.size();
        int i = 0;

        while(true){
            if (filesToMerge % 2 != 0 && temp.isEmpty()){
                temp.add(files.remove(filesToMerge-1));
                filesToMerge-=1;
            }
            if (filesToMerge % 2 != 0 && !temp.isEmpty()){
                files.add(temp.remove(0));
                filesToMerge+=1;
            }
            if (filesToMerge == 2 && temp.isEmpty()){
                writeMerge(files.get(i),files.get(i+1), output);

                break;
            }
            if (filesToMerge == 2 && !temp.isEmpty()){
                String Last = String.format("xms.tmp.pass_%04d.chunk_%04d", passID, i/2);
                files.set(0, writeMerge(files.get(i),files.get(i+1), Last));
                writeMerge(files.get(0), temp.get(0), output);
                for (String s: files) System.out.print("files: "+s+"\n");
                for (String s: temp) System.out.print("temp: "+s+"\n");
                break;
            }

            tempFile = String.format("xms.tmp.pass_%04d.chunk_%04d", passID, i/2);
            String toWrite = writeMerge(files.get(i),files.get(i+1), tempFile);
            files.set(i/2, toWrite );

            if (i == filesToMerge-2){
                i = 0;
                passID+=1;
                filesToMerge = filesToMerge / 2;
            }
            else i+=2;

        }
    }//merge

    public static String writeMerge(String merge0, String merge1, String output) throws IOException{
        Scanner fileScan0 = new Scanner(new File(merge0));
        Scanner fileScan1 = new Scanner(new File(merge1));
        FileWriter writer = new FileWriter(new File(output), true);

        String string0, string1;
        string0 = string1 = null;
        while (fileScan0.hasNext() || fileScan1.hasNext() ){

            if (string0 == null && fileScan0.hasNext()){
                string0 = fileScan0.next();
            }

            if (string1 == null && fileScan1.hasNext()){
                string1 = fileScan1.next();
            }


            if (string0 != null && string1 != null){
                if      ( string0.compareTo(string1) < 0)  {
                    writer.write(string0);
                    writer.write(System.lineSeparator());
                    string0 = null;
                }
                else if ( string0.compareTo(string1) == 0) {
                    writer.write(string0);
                    writer.write(System.lineSeparator());
                    writer.write(string1);
                    writer.write(System.lineSeparator());
                    string0 = string1 = null;
                }
                else   {
                    writer.write(string1);
                    writer.write(System.lineSeparator());
                    string1 = null;
                }
            }
            else {
                if (string0 != null && string1 == null) {
                    writer.write(string0);
                    writer.write(System.lineSeparator());
                    string0 = null;
                } else if (string0 == null && string1 != null) {//redun
                    writer.write(string1);
                    writer.write(System.lineSeparator());
                    string1 = null;
                }
            }
        }
        if (!fileScan0.hasNext() && !fileScan1.hasNext() ){
            if      (string0 != null)  writer.write(string0);
            else if (string1 != null)  writer.write(string1);
        }
        writer.close();
        fileScan0.close();
        fileScan1.close();
        return output;
    }//writeMerge
}//class

//TESTING
//insertionSort testing
//        String [] temp_old = {"e","d","a","c","b"};
//        String [] temp = {"e", "a"};
//
//
//
//        for (int i =0;i< temp.length;i++){
//            System.out.print(temp[i]);
//        }
//        System.out.println();
//        temp = insertionSort(temp);
//        for (int i =0;i< temp.length;i++){
//            System.out.print(temp[i]);
//        }
//        int chunkI = 700;
//        int passi = 1;
//        String s = String.format("xms.tmp.pass_%04d.chunk_%04d", passi, chunkI);
//        System.out.print(s);
//         writeMerge(files.get(0),files.get(1), new String("outputTest"));


