import java.util.LinkedList;

public class Tree {

    private CharNode root;
    private int count = 0;

    private class CharNode {
        private char character;
        private boolean isFinal;
        private CharNode father;
        private LinkedList<CharNode> children;

        public CharNode(char character) {
            this.character = character;
            isFinal = false;
            father = null;
            children = new LinkedList<>();
        }

        public CharNode getSubtree(int i) {
            if ((i < 0) || (i >= children.size())) {
                throw new IndexOutOfBoundsException();
            }
            return children.get(character);
        }

        public int getSubtreesSize() {
            return children.size();
        }

        private void addSubtree(CharNode n) {
            n.father = this;
            children.add(n);
        }

    }

    public Tree() {
        count = 0;
        root = null;
    }

    public void clear() {
        count = 0;
        root = null;
    }

    public int size() {
        return count;
    }

    private CharNode searchNodeRef(char character, CharNode n) {
        if (n == null)
            return null;

        if (Character.compare(character, n.character) == 0) { // visita a raiz
            return n;
        } else { // visita os filhos
            CharNode aux = null;
            int i = 0;
            while ((aux == null) && (i < n.getSubtreesSize())) {
                aux = searchNodeRef(character, n.getSubtree(i));
                i++;
            }
            return aux;
        }
    }

    public void processing(String palavra) {
        char[] letters = palavra.toCharArray();
        int count = 0;
        boolean isFinal = false;
        int finalCharacter = 0;

        if (size() == 0) {

            for (int i = 0; i < letters.length; i++) {
                finalCharacter++;
                if (finalCharacter == letters.length) {
                    isFinal = true;
                }
                try {
                    add(letters[i], letters[0], isFinal);

                } catch (IndexOutOfBoundsException e) {
                    add(letters[i], '!', isFinal);
                }
            }
        } else {
            int cont = 0;
            if(root.getSubtreesSize() < letters.length) { //Caso seja adicionada com o pai root e não em linked list
                for(int k = 0; k < root.getSubtreesSize(); k++){
                    if (root.children.get(k).character == letters[k + 1]){
                        cont++;
                    }
                }
                if(cont == root.getSubtreesSize()){
                    for (int i = cont + 1; i < letters.length; i++) {
                        add(letters[i], letters[0], isFinal);
                    }
                }
            }

                else{

            int c = 0;
            int index = -1;

            while ((c + 1 < letters.length) && (root.children.get(c).character == letters[c + 1])) {
                c++;
                index++;
            }

            if ((root.getSubtreesSize() > letters.length) && c == letters.length) { // Palavra está contida na root
                root.children.get(c).isFinal = true; // Aquele nodo existe virou final, formando outra palavra
            }

            else if ((root.getSubtreesSize() == letters.length -1) && (c == letters.length - 1)) { // Palavra já foi adicionada
                System.out.println("Palavra já havia sido adicionada anteriormente");
            }

            else {
                for (int i = c + 1; i < letters.length; i++) {
                    if (i == letters.length - 1) {
                        isFinal = true;
                    }
                    addNodeOnLinkedList(letters[i], root.children.get(index).character, isFinal, index);
                }
            }
        }
        }
        }

    public boolean contains(CharNode node, char character, char fatherChar) {
        if (node.character == character) {
            return true;
        }
        for (CharNode child : node.children) {
            boolean f = contains(child, character, fatherChar);
            if (f) {
                return true;
            }
        }
        return false;
    }

    public void add(char character, char father, boolean isFinal) {
        CharNode n = new CharNode(character);
        if (size() == 0 || father == '!') { // insere elem como raiz da arvore
            if (root != null) { // se ja haviam elementos na arvores
                n.addSubtree(root);
                root.father = n;
            }
            root = n;
            count++;
            System.out.println(n.character);
            // return true;
        } else {
            CharNode aux = searchNodeRef(father, root);
            if (aux == null) { // nao encontrou o father
                System.out.println("Não foi possível fazer a adição");
            } else { // se encontrou father, insere n como filho
                aux.addSubtree(n);
                n.father = aux;
                count++;
                System.out.println(n.character);
            }
        }
    }

    public void addNodeOnLinkedList(char character, char father, boolean isFinal, int index) {
        CharNode n = new CharNode(character);
        CharNode aux = searchNodeRef(father, root.children.get(index));
        if (aux == null) { // nao encontrou o father
            System.out.println("Não foi possível fazer a adição");
        } else { // se encontrou father, insere n como filho
            aux.addSubtree(n);
            n.father = aux;
            count++;
            System.out.println(n.character);
        }
    }

    // Conta o numero de nodos da subarvore cuja raiz eh passada por parametro
    private int countNodes(CharNode n) {
        if (n == null)
            return 0;

        int c = 0;
        for (int i = 0; i < n.getSubtreesSize(); i++) {
            c = c + countNodes(n.getSubtree(i));
        }
        return c + 1;

    }

    public int countNodes() {
        return countNodes(root);
    }
}