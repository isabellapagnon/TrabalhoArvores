import java.util.*;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Tree t = new Tree();
        int countNodes = 0;
        System.out.println("Digite a palavra que deseja adicionar na árvore: ");
        String palavra = sc.next();

        while(palavra != "123") { // 
            t.processing(palavra);
            countNodes = t.size();
            System.out.println("Nodos totais: " + countNodes);
            System.out.println("-------------------");

            // Iniciando um novo processo
            System.out.println("Digite a palavra que deseja adicionar na árvore: ");
             palavra = sc.next();
          
        }
        
}
}