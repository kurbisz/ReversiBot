package com.kurbisz;

import com.kurbisz.exceptions.ParserException;
import com.kurbisz.parser.ArgumentParser;

/**
 * Main class for executing parser, selected as main project class for maven.
 */
public class Main {

    public static void main(String[] args) {
        try {
            ArgumentParser argumentParser = new ArgumentParser();
            argumentParser.executeCommand(args);
        } catch (ParserException e) {}
    }

}