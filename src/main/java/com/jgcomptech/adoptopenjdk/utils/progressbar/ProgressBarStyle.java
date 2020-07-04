package com.jgcomptech.adoptopenjdk.utils.progressbar;

import static com.jgcomptech.adoptopenjdk.utils.Literals.NEW_LINE;

/**
 * Represents the display style of a progress bar.
 * @author Tongfei Chen
 */
public enum ProgressBarStyle {
    COLORFUL_UNICODE_BLOCK(NEW_LINE, "\u001b[33m│", "│\u001b[0m", '█', ' ', " ▏▎▍▌▋▊▉"),

    /** Use Unicode block characters to draw the progress bar. */
    UNICODE_BLOCK(NEW_LINE, "│", "│", '█', ' ', " ▏▎▍▌▋▊▉"),

    /** Use only ASCII characters to draw the progress bar. */
    ASCII_LINUX(NEW_LINE, "[", "]", '=', ' ', ">"),

    /** Use only ASCII characters to draw the progress bar. */
    ASCII_POWERSHELL(NEW_LINE, "[", "]", '#', '.', ">");

    final String refreshPrompt;
    final String leftBracket;
    final String rightBracket;
    final char block;
    final char space;
    final String fractionSymbols;

    ProgressBarStyle(final String refreshPrompt, final String leftBracket, final String rightBracket,
                     final char block, final char space, final String fractionSymbols) {
        this.refreshPrompt = refreshPrompt;
        this.leftBracket = leftBracket;
        this.rightBracket = rightBracket;
        this.block = block;
        this.space = space;
        this.fractionSymbols = fractionSymbols;
    }
}
