import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class File {
   String name;
   int file_id;

   public File() {}

   public File(String name, int file_id) {
       this.name = name;
       this.file_id = file_id;
   }

   public FileReader openFile() {
        try {
            return new FileReader("../inverse_index/inverse-index-with-red-black-tree-master/inverse-index-with-red-black-tree/src/" + this.name);
        } catch (FileNotFoundException ex) {
            System.out.println("'" + this.name + "' nao foi encontrado!");
            return null;
        }
   }

    public BufferedReader readFile(FileReader fr) {
        return new BufferedReader(fr);
    }
}
