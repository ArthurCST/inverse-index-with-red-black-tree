public class TreeRB{
    private Node raiz;
    private int qnt;

    /*Geteres e Seteres*/
    public void setRaiz(Node node){
        raiz = node;
        raiz.setCor("Negro");
    }
    public Node getRaiz(){
        return raiz;
    }
    /*FIM - Geteres e Seteres*/

    //Diminuir parametros para reuso
    public Node seek(String key){
        return search(raiz, key);
    }

    public Node search(Node node, String key){
        //Verifica se eh folha
        if(node.isExternal() == true){
            return node;
        }
        //Verifica se foi encontrado
        if(node.getElemento().compareTo(key) == 0){
            return node;
        }
        //Verifica se esta do lado esquerdo
        else if(node.getElemento().compareTo(key) > 0){
            return search(node.getFilhoE(), key);
        }
        //Verifica se esta do lado direito
        else{
            return search(node.getFilhoD(), key);
        }
    }

    private Node AtualizarCores(Node node)
    {
        Node pai = node.getPai();
        if(pai.getCor().equals("Vermelho"))
        {
            Node tio = null;
            //se pai é filho direito então tio é filho esquerdo de vô
            if(pai.ehFilhoD())
                tio = pai.getPai().getFilhoE();
            else
                tio = pai.getPai().getFilhoD();

            //tio eh negro
            if(tio == null || tio.getCor().equals("Negro"))
            {
                System.out.println("Incluir: Situação 3 no noh: " + node.getElemento());
                node = Rotacionar(node);
            }
            //se o tio eh rubro
            else
            {
                System.out.println("Incluir: Situação 2 no noh:" + node.getElemento());
                tio.setCor("Negro");
                pai.setCor("Negro");
                if(!pai.getPai().isRoot()){
                    pai.getPai().setCor("Vermelho");
                    node = AtualizarCores(node.getPai().getPai());
                }
            }
        }

        return node;
    }

    private Node buscarPai(Node node, String key) {
        //Verifica se o no não tem filho
        if (node.isExternal()) {
            return node;
        }
        //Verifica se o no que procura esta do lado esquerdo
        if (node.getElemento().compareTo(key) > 0)
        {
            if(node.getFilhoE() == null)
                return node;
            else
                return buscarPai(node.getFilhoE(), key);
        }
        //Verifica se o no que procura esta do lado direito
        else
        {
            if(node.getFilhoD() == null)
                return node;
            else
                return buscarPai(node.getFilhoD(), key);
        }
    }

    public Node incluir(String key) {
        
        if(raiz == null){
            Node raiz = new Node(null, key); 
            setRaiz(raiz);
            
            return (raiz);
        }else{
            //Busca o pai do no que vai ser inserido
            Node pai = buscarPai(raiz, key);
            //Cria o novo no
            Node novo = new Node(pai, key);

            //Verifica se o novo no eh filho esquerdo do pai buscado
            if (novo.getElemento().compareTo(pai.getElemento())<=0)
                pai.setFilhoE(novo);
            //Verifica se o novo no eh filho direito do pai buscado
            else
                pai.setFilhoD(novo);
            qnt++;

            AtualizarCores(novo);

            return novo;
        }
    }

   private Node Rotacionar(Node node)
       {
           Node retorno = null;
           Node avo = node.getPai().getPai();
           Node pai = node.getPai();

           //Rotacao simples a direita
           if(node.ehFilhoE() && pai.ehFilhoE())
           {
               retorno = node.getPai();
               avo.setCor("Vermelho");
               pai.setCor("Negro");

               RotacaoSimplesDireita(avo);
           }
           //Rotacao simples a esquerda
           else if(node.ehFilhoD() && pai.ehFilhoD())
           {
               retorno = node.getPai();
               avo.setCor("Vermelho");
               pai.setCor("Negro");

               RotacaoSimplesEsquerda(avo);

           }
           //Rotacao dupla esquerda
           else if(pai.ehFilhoD())
           {
               retorno = node;
               avo.setCor("Vermelho");
               node.setCor("Negro");

               RotacaoDuplaEsquerda(avo);
           }
           //Rotacao dupla direita
           else
           {
               retorno = node;
               avo.setCor("Vermelho");
               node.setCor("Negro");

               RotacaoDuplaDireita(avo);
           }

           return retorno;
       }
   public void RotacaoSimplesEsquerda(Node node) {
        System.out.println("Rotacao Simples Esquerda " + node.getElemento());

        Node netoE = null;

        //se necessario, atualiza a raiz
        if(node.isRoot())
            raiz = node.getFilhoD();

        //guarda o netoE e atualiza suas referencia para o pai
        if(node.getFilhoD().getFilhoE() != null) {
            netoE = node.getFilhoD().getFilhoE();
            netoE.setPai(node);
        }

        //Atualiza as referencias do filho direito do no
        node.getFilhoD().setPai(node.getPai());
        node.getFilhoD().setFilhoE(node);

        //Atualiza as referencias do pai do no se existir
        if(node.getPai() != null) {
            if(node.getElemento().compareTo(node.getPai().getElemento()) > 0)
                node.getPai().setFilhoD(node.getFilhoD());
            else
                node.getPai().setFilhoE(node.getFilhoD());
        }

        //Atualiza as referencias do no
        node.setPai(node.getFilhoD());
        //if(netoE != null)
            node.setFilhoD(netoE);
        //else
        //   no.setFilhoD(null);

        //exibirArvore(raiz);
    }

    public void RotacaoSimplesDireita(Node node) {
        System.out.println("Rotacao Simples Direita " + node.getElemento());

        Node netoD = null;

        //se necessario, atuliza a raiz
        if(node.isRoot())
            raiz = node.getFilhoE();

        //guarda o netoD
        if(node.getFilhoE().getFilhoD() != null) {
            netoD = node.getFilhoE().getFilhoD();
            netoD.setPai(node);
        }

        //Atualiza as referencias do filho esquerdo do no
        node.getFilhoE().setPai(node.getPai());
        node.getFilhoE().setFilhoD(node);

        //Atualiza as referencias do pai do no, se existir
        if(node.getPai() != null){
            if(node.getElemento().compareTo(node.getPai().getElemento()) > 0)
                node.getPai().setFilhoD(node.getFilhoE());
            else
                node.getPai().setFilhoE(node.getFilhoE());
        }

        //Atualiza as referencias do no
        node.setPai(node.getFilhoE());
        //if(netoD != null)
            node.setFilhoE(netoD);
        //else
        //    no.setFilhoE(null);

        //exibirArvore(raiz);
    }

    public void RotacaoDuplaEsquerda(Node node) {
        RotacaoSimplesDireita(node.getFilhoD());
        RotacaoSimplesEsquerda(node);
    }

    public void RotacaoDuplaDireita(Node node) {
        RotacaoSimplesEsquerda(node.getFilhoE());
        RotacaoSimplesDireita(node);
    }


    //Metodo que mostra as caracteristicas do no *OBS: Faz a verificações para não dar erro de referencia nula
    public void Visite(Node node) {
        System.out.print("Elemento:" + node.getElemento() + " Cor: " + node.getCor());
        if (!node.isRoot())
            System.out.print(" Pai:" + node.getPai().getElemento());

        if (node.getFilhoE() != null)
            System.out.print(" FilhoE:" + node.getFilhoE().getElemento());

        if (node.getFilhoD() != null)
            System.out.print(" FilhoD:" + node.getFilhoD().getElemento());

        System.out.println();
    }

    public void exibirArvore(Node node) {
        if (node.isInternal() && node.getFilhoE() != null) {
            exibirArvore(node.getFilhoE());
        }

        Visite(node);

        if (node.isInternal() && node.getFilhoD() != null) {
            exibirArvore(node.getFilhoD());
        }
    }

}