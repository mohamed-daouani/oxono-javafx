package g62275.dev3.oxono.console;

import g62275.dev3.oxono.controller.Controller;
import g62275.dev3.oxono.model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue sur Oxono : Version console");
        System.out.println("=====================================");
        System.out.println("Choisissez la taille du plateau :");
        System.out.println("1. 4x4");
        System.out.println("2. 6x6");
        System.out.println("3. 8x8");

        int boardSize = getBoardSize(scanner);

        Game game = new Game(boardSize);
        AI ai = new AI(game, new EasyStrategy());

        System.out.println("Règles du jeu :");
        System.out.println("- Chaque joueur joue à tour de rôle.");
        System.out.println("- Vous pouvez déplacer un totem ou insérer un jeton.");
        System.out.println("- Vous pouvez également annuler (Undo) ou refaire (Redo) une action.");
        System.out.println("- Le but est de former une ligne, colonne ou diagonale avec vos symboles.");
        System.out.println();

        boolean playAgain = true;

        while (playAgain) {
            while (!game.won() && !game.draw() && !game.isGameOver()) {
                displayBoard(game);

                if (game.getCurrentPlayer() == Color.PINK) {
                    System.out.println("--------------------------------");
                    System.out.println("Au tour de " + game.getCurrentPlayer());
                    System.out.println("Choisissez une action :");
                    System.out.println("1. Déplacer un totem");
                    System.out.println("2. Insérer un jeton");
                    System.out.println("3. Undo");
                    System.out.println("4. Redo");
                    System.out.println("5. Quitter la partie");

                    int choice = getUserChoice(scanner, 1, 5);

                    if (choice == 5) {
                        System.out.println("Vous avez quitté la partie.");
                        return;
                    }

                    try {
                        switch (choice) {
                            case 1 -> handleMove(scanner, game);
                            case 2 -> handleInsert(scanner, game);
                            case 3 -> game.undo();
                            case 4 -> game.redo();
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }

                    System.out.println("--------------------------------");
                } else {
                    System.out.println("L'IA joue son tour...");
                    try {
                        ai.playMove();
                        ai.playInsert();
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                }
            }

            displayBoard(game);
            if (game.won()) {
                System.out.println("Félicitations ! Le gagnant est " + game.getCurrentPlayer());
            } else if (game.draw()) {
                System.out.println("Match nul !");
            }

            System.out.println("Voulez-vous rejouer ? (oui/non)");
            playAgain = scanner.nextLine().trim().equalsIgnoreCase("oui");
        }

        scanner.close();
        System.out.println("Merci d'avoir joué à Oxono !");
    }

    private static int getBoardSize(Scanner scanner) {
        int size = -1;
        while (size != 4 && size != 6 && size != 8) {
            System.out.print("Entrez le numéro correspondant (1 pour 4x4, 2 pour 6x6, 3 pour 8x8) : ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) size = 4;
                else if (choice == 2) size = 6;
                else if (choice == 3) size = 8;
                else System.out.println("Choix invalide. Veuillez choisir entre 4, 6 ou 8.");
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.nextLine();
            }
        }
        return size;
    }

    private static void displayBoard(Game game) {
        System.out.println("Plateau de jeu :");
        for (int i = 0; i < game.getBoardSize(); i++) {
            for (int j = 0; j < game.getBoardSize(); j++) {
                game.getPawnAt(new Position(i,j)).getSymbol();
            }
            System.out.println();
        }
    }

    private static int getUserChoice(Scanner scanner, int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            try {
                System.out.print("Entrez votre choix (" + min + "-" + max + ") : ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consomme la fin de ligne
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.nextLine(); // Consomme l'entrée invalide
            }
        }
        return choice;
    }

    private static void handleMove(Scanner scanner, Game game) {
        System.out.println("Quel totem voulez-vous déplacer ? (X/O)");
        String symbolInput = scanner.nextLine().trim();
        Symbol symbol = symbolInput.equalsIgnoreCase("X") ? Symbol.X : Symbol.O;

        System.out.println("À quelle position voulez-vous le déplacer ? (ligne colonne)");
        int row = getUserCoordinate(scanner, game.getBoardSize());
        int col = getUserCoordinate(scanner, game.getBoardSize());

        Position movePosition = new Position(row, col);
        game.executeMove(symbol, movePosition);
    }

    private static void handleInsert(Scanner scanner, Game game) {
        System.out.println("À quelle position voulez-vous insérer le jeton ? (ligne colonne)");
        int row = getUserCoordinate(scanner, game.getBoardSize());
        int col = getUserCoordinate(scanner, game.getBoardSize());

        Position tokenPosition = new Position(row, col);
        game.executeInsert(tokenPosition);
    }

    private static int getUserCoordinate(Scanner scanner, int maxSize) {
        int coord = -1;
        while (coord < 0 || coord >= maxSize) {
            try {
                System.out.print("Entrez une coordonnée (1-" + maxSize + ") : ");
                coord = scanner.nextInt() - 1; // Convertit en index 0-based
                scanner.nextLine(); // Consomme la fin de ligne
                if (coord < 0 || coord >= maxSize) {
                    System.out.println("Coordonnée invalide. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.nextLine(); // Consomme l'entrée invalide
            }
        }
        return coord;
    }
}
