import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equalsIgnoreCase("Rock")
                && computerMove.equalsIgnoreCase("Scissors"))
                || (playerMove.equalsIgnoreCase("Paper")
                && computerMove.equalsIgnoreCase("Rock"))
                || (playerMove.equalsIgnoreCase("Scissors")
                && computerMove.equalsIgnoreCase("Paper"))) {
            return "Player Wins";
        }

        return "Computer Wins";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String[] moves = {"Rock", "Paper", "Scissors"};

        int wins = 0;
        int losses = 0;
        int draws = 0;
        int rounds = 5;

        String[] playerMoves = new String[rounds];
        String[] computerMoves = new String[rounds];
        String[] results = new String[rounds];

        RockPaperScissors game = new RockPaperScissors();

        for (int i = 0; i < rounds; i++) {
            System.out.print("Enter your move: ");
            String playerMove = sc.nextLine();

            String computerMove = moves[random.nextInt(3)];

            String result = game.playRound(playerMove, computerMove);

            playerMoves[i] = playerMove;
            computerMoves[i] = computerMove;
            results[i] = result;

            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }

            System.out.println("Computer: " + computerMove);
            System.out.println("Result: " + result);
        }

        double winPercentage = (wins * 100.0) / rounds;

        System.out.println();
        System.out.println("Final Summary");
        System.out.println("Round | Player Move | Computer Move | Result");

        for (int i = 0; i < rounds; i++) {
            System.out.println((i + 1) + " | " + playerMoves[i]
                    + " | " + computerMoves[i]
                    + " | " + results[i]);
        }

        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Draws: " + draws);
        System.out.printf("Win %% = %.1f%%%n", winPercentage);

        sc.close();
    }
}
