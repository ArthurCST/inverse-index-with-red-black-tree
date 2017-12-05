import java.util.ArrayList;


public class TreeRB{
    private Node raiz = null;
    ArrayList<String> keys = new ArrayList<>();

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
    public Node seek(Word key){
        return search(this.raiz, key);
    }

    public Node search(Node raiz, Word key) {
        if (raiz == null || (raiz.getElementoW().content.compareTo(key.content) == 0 && raiz.getElementoW().file == key.file))
            return raiz;

        if (raiz.getElementoW().content.compareTo(key.content) > 0)
            return search(raiz.getFilhoE(), key);

        return search(raiz.getFilhoD(), key);
    }

    public Node searchToAdd(Node root, Node nodeToAdd) {
		// in this case goes left nodeToAdd.getElemento().compareTo(root.getElemento()) < 0
		if (nodeToAdd.getElemento().compareTo(root.getElemento()) < 0) {
			// here the node is added if the spot is right.
			if (root.getFilhoE()== null) {
				nodeToAdd.setPai(root);
				root.setFilhoE(nodeToAdd);
				return root.getFilhoE();
			}
			// if not, continues search.
			else {
				return searchToAdd(root.getFilhoE(), nodeToAdd);
			}
		}
		// in this case goes to right.
		else {
			// here the node is added if the spot is right.
			if (root.getFilhoD() == null) {
				nodeToAdd.setPai(root);
				root.setFilhoD(nodeToAdd);
				return root.getFilhoD();
			}
			// if not, continues search.
			else {
				return searchToAdd(root.getFilhoD(), nodeToAdd);
			}
		}
	}

    public void incluir(Word key){

    		Node node = new Node(null, key);

    		if (getRaiz() == null) {
    			node.setCor("Negro");
    			setRaiz(node);
    		} else {
    			Node added = searchToAdd(getRaiz(), node);
    			balanceCaseOne(added);
    		}
    }

   public void RotacaoSimplesEsquerda(Node node) {
        System.out.println("Rotacao Simples Esquerda " + node.getElemento());

        Node aux = node.getFilhoD();
        node.setFilhoD(aux.getFilhoE());
        if(aux.getFilhoE()!=null){
        	aux.getFilhoE().setPai(node);
        }
        aux.setPai(node.getPai());
        if(node.getPai() == null){
        	setRaiz(aux);
        }else if(node.ehFilhoE()){
        	node.getPai().setFilhoE(aux);;
        }else{
        	node.getPai().setFilhoD(aux);
        }

        aux.setFilhoE(node);
        node.setPai(aux);

    }

    public void RotacaoSimplesDireita(Node node) {
        System.out.println("Rotacao Simples Direita " + node.getElemento());

        Node aux = node.getFilhoE();
        node.setFilhoE(aux.getFilhoD());
        if(aux.getFilhoD()!=null){
        	aux.getFilhoD().setPai(node);
        }
        aux.setPai(node.getPai());
        if(node.getPai() == null){
        	setRaiz(aux);
        }else if(node.ehFilhoD()){
        	node.getPai().setFilhoD(aux);;
        }else{
        	node.getPai().setFilhoE(aux);
        }

        aux.setFilhoD(node);
        node.setPai(aux);
    }

    //Metodo que mostra as caracteristicas do no *OBS: Faz a verificaÃ§Ãµes para nÃ£o dar erro de referencia nula
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


    public Node getAvo(Node node){
    	Node pai = node.getPai();

    	if (pai != null) {
    		Node avo = pai.getPai();

    		if (avo != null) {
    			return avo;
    		} else {
    			return null;
    		}
    	} else {
    		return null;
    	}

    }

    public Node getTio(Node node){
    	Node pai = node.getPai();
    	Node avo = getAvo(node);

    	if(pai!=null && avo != null){
    		if (avo.getFilhoD()!= null && avo.getFilhoE()!= null){
    			if (avo.getFilhoE().getElemento().compareTo(pai.getElemento()) == 0) {
    				return avo.getFilhoD();
    			} else {
    				return avo.getFilhoE();
    			}
        	}else{
        		return null;
        	}
    	}else{
    		return null;
    	}
    }

    public void balanceCaseOne(Node added) {
    	if (added.isRoot()) {
    		added.setCor("Negro");
    	}else{
    		balanceCaseTwo(added);
    	}

    }
    public void balanceCaseTwo(Node added) {

    	if (added.getPai().getCor().compareTo("Vermelho") == 0) {
    		balanceCaseThree(added);
    	}

    }

    public void balanceCaseThree(Node added){
    	Node tio = getTio(added);

	    if(tio != null && tio.getCor().compareTo("Vermelho")==0){
	    	Node pai = added.getPai();
	    	pai.setCor("Negro");
	    	tio.setCor("Negro");

	    	Node avo = getAvo(added);
	    	if(!avo.isRoot()){
	    		avo.setCor("Vermelho");
	    	}
	    	balanceCaseOne(added);
	    }else{
	    	balanceCaseFour(added);
	    }
	}

    public void balanceCaseFour(Node added){
    	Node pai = added.getPai();

    	if (added.ehFilhoD()&& pai.ehFilhoE()) {
    		RotacaoSimplesEsquerda(pai);
    		added = added.getFilhoE();
    	} else if(added.ehFilhoE()&& pai.ehFilhoD()) {
    		RotacaoSimplesDireita(pai);
    		added = added.getFilhoD();
    	}else{
    		balanceCaseFive(added);
    	}

    }

    public void balanceCaseFive(Node added){

    	Node pai = added.getPai();
    	Node avo = getAvo(added);

    	pai.setCor("Negro");
    	avo.setCor("Vermelho");

    	if (added.ehFilhoE() && pai.ehFilhoE()) {
    		RotacaoSimplesDireita(avo);
    	} else {
    		RotacaoSimplesEsquerda(avo);
    	}
    }

    public void changeColor(Node node) {
    	if (node.getCor().compareTo("Negro")==0) {
			node.setCor("Vermelho");
		} else {
			node.setCor("Negro");
		}
	}
}
