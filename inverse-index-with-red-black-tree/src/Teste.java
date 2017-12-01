
public class Teste {
    public static void main(String[] args) {
        TreeRB tree = new TreeRB();
        
        tree.incluir("inicio");
        tree.incluir("texto");
        
               
        tree.exibirArvore(tree.getRaiz());
    }
}
