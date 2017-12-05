
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;

public class Manager {

    public ArrayList<File> createListOfFiles(String[] filenames) {
        ArrayList<File> files = new ArrayList<>();

        for(int i = 0; i < filenames.length; i++) {
            File file = new File();

            file.file_id = i;
            file.name = filenames[i];

            files.add(file);
        }

        return files;
    }

    public TreeRB saveWords(ArrayList<File> files){
        TreeRB tree = new TreeRB();

        for(File file : files) {
            FileReader fr = file.openFile();

            BufferedReader br = file.readFile(fr);

            try {
                String line = br.readLine();

                while(br.ready()) {
                    String[] words = line.split("[ ,.!;:?]");

                    for(String word: words){
                       if(word.length() >= 4){
                            Word w = new Word(Normalizer.normalize(word.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""), file);
                            if(!this.exists(tree, w)) {
                                tree.incluir(w);

                            }
                       }
                    }

                    line = br.readLine();
                }

                String[] words = line.split("[ ,.!;:?]");

                for(String word: words){
                    if(word.length() >= 4){
                         Word w = new Word(Normalizer.normalize(word.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""), file);
                         if(!this.exists(tree, w)) {
                             tree.incluir(w);

                         }
                    }
                 }

            } catch (IOException ex) {
                System.out.println("NÃ£o foi possivel ler a linha em '" + file.name + "'");
            }
        }
        return tree;

    }

    public boolean exists(TreeRB tree, Word word) {
        Node node = tree.search(tree.getRaiz(), word);
        if(node == null) {
            tree.keys.add(word.content);
            return false;
        } else {
            if(node.getElementoW().file == word.file) {
                node.getElementoW().number++;
                return true;
            } else {
                return false;
            }
        }
    }

    public int count(File file, TreeRB tree, String key) {
        int counter = 0;

        Node foundNode = tree.search(tree.getRaiz(), new Word(key, file));

        if(foundNode != null) {
            return foundNode.getElementoW().number;
        }

        return counter;
    }

    public void show_occurs(TreeRB tree, ArrayList<File> files) {
        for(String key : tree.keys) {
            System.out.println();
            System.out.print(key);
            for(File file : files) {
                System.out.print(", " + file.file_id);
                System.out.print(" " + this.count(file, tree, key));
            }

        }
        System.out.println("");
        System.out.println("");
    }
}
