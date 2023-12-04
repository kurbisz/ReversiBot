package com.kurbisz.parser;

import com.kurbisz.parser.commands.*;
import com.kurbisz.exceptions.ParserException;

/**
 * Class responsible for parsing arguments from console and executing appropriate methods.
 */
public class ArgumentParser {

    /**
     * Execute triggered command with given input arguments
     * @param args input arguments to parse
     * @throws ParserException when arguments are incorrect or given command does not exist
     */
    public void executeCommand(String[] args) throws ParserException {
        try {
            CommandExecutor executor = null;
            if (args[0].equalsIgnoreCase("pvp")) {
                executor = new PlayerVsPlayer();
            } else if (args[0].equalsIgnoreCase("pvc")) {
                int depth = parseIntArgument(args[1], "First argument should be depth of first bot!");
                executor = new PlayerVsBot(depth);
            } else if (args[0].equalsIgnoreCase("cvc")) {
                int depth1 = parseIntArgument(args[1], "First argument should be depth of first bot!");
                int depth2 = parseIntArgument(args[2], "Second argument should be depth of second bot!");
                executor = new BotVsBot(depth1, depth2);
            } else if (args[0].equalsIgnoreCase("genetic")) {
                int depth = parseIntArgument(args[1], "First argument should be depth of minimax algorithm!");
                int threads = parseIntArgument(args[2], "Second argument should be number of available threads!");
                int loops = parseIntArgument(args[3], "Third argument should be number of loops for genetic algorithm!");
                if (depth < 1 || threads < 1 || loops < 2) {
                    sendError("Error! These requirements must be met: depth >= 1 and threads >= 1 and loop >= 2");
                }
                executor = new GeneticTesting(depth, threads, loops);
            } else if (args[0].equalsIgnoreCase("help")) {
                this.printHelp();
            }

            if (executor == null) {
                sendError("Incorrect first argument!");
            }

            executor.execute();


        } catch (IndexOutOfBoundsException e) {
            sendError("Incorrect number of arguments! Type 'help' as 1st argument to show list of commands.");
        }
    }

    /**
     * Try to parse argument in integer format or send error otherwise.
     * @param str string to parse to int
     * @param errorMsg message that will be printed to standard output when error occurs
     * @return integer value of given string
     * @throws ParserException when 'str' cannot be parsed to number
     */
    protected int parseIntArgument(String str, String errorMsg) throws ParserException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            sendError(errorMsg);
        }
        return 0;
    }

    /**
     * Send error message to standard output and throw exception.
     * @param msg message to be sent
     * @throws ParserException after sending message
     */
    protected void sendError(String msg) throws ParserException {
        System.out.println("[ERROR] " + msg);
        throw new ParserException();
    }

    /**
     * Show list of all commands on standard output.
     * @throws ParserException after printing help
     */
    private void printHelp() throws ParserException {
        System.out.println("List of all available commands:");
        System.out.println("\tpvp - play game player vs player");
        System.out.println("\tpvc <DEPTH> - play game player vs random bot with specified depth");
        System.out.println("\tpvc <DEPTH1> <DEPTH2> - play game with 2 bots with given depth");
        System.out.println("\tgenetic <DEPTH> <THREADS> <LOOPS> - run genetic algorithm with given parameters");
        throw new ParserException();
    }


}
