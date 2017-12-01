public class Node {
    private String elemento;
    private Node parent;
    private Node left;
    private Node right;
    private String color;
    private Boolean duplo;

    public Node() {}

    public Node(Node pai, String elemento) {
        this.parent = pai;
        this.elemento = elemento;
        this.color = "Vermelho";
        this.duplo = false;
    }

    public Node getIrmao()
    {
        if(this.ehFilhoD())
            return parent.getFilhoE();
        else
            return parent.getFilhoD();
    }

    public Node getPai() {
        return parent;
    }

    public void setPai(Node pai) {
        this.parent = pai;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public Node getFilhoE() {
        return left;
    }

    public void setFilhoE(Node filhoE) {
        this.left = filhoE;
    }

    public Node getFilhoD() {
        return right;
    }

    public void setFilhoD(Node filhoD) {
        this.right = filhoD;
    }

    //Retorna se o No é raiz (não tem parent)
    public boolean isRoot()
    {
        return this.parent == null;
    }

    public boolean hasFilhoE(){
        return this.left != null;
    }

    public boolean hasFilhoD(){
        return this.right != null;
    }

    //Retorna se o No é interno (tem filho)
    public boolean isInternal()
    {
        return this.left != null || this.right != null;
    }
    //Retorna se o No é externo (não tem filho)
    public boolean isExternal()
    {
        return this.right == null && this.left == null;
    }

    //Retorna se o No é filho esquerdo
    public boolean ehFilhoE()
    {
        return this.elemento.compareTo(this.parent.getElemento()) <= 0;
    }

    //Retorna se o No é filho direito
    public boolean ehFilhoD()
    {
        return this.elemento.compareTo(this.parent.getElemento()) < 0;
    }

    public String getCor() {
        return color;
    }

    public void setCor(String cor) {
        this.color = cor;
    }

    public Boolean getDuplo() {
        return duplo;
    }

    public void setDuplo(Boolean duplo) {
        this.duplo = duplo;
    }

}