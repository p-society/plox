package com.psociety.lox;

import java.util.*;
import static com.psociety.lox.TokenType.*;

public class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!this.isAtEnd()) {
            this.start = this.current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '.':
                addToken(DOT);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '+':
                addToken(PLUS);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case '*':
                addToken(STAR);
                break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if (match('/')) {
                    while (peek() != '\n' &&
                            !this.isAtEnd()) {
                        this.advance();
                    }
                } else {
                    this.addToken(SLASH);
                }
                break;

            // Ignore whitespace.
            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                this.line++;
                break;
            case '"':
                string();
                break;
            default:
                if (this.isDigit(c)) {
                    this.number();
                } else {
                    Lox.error(line, "Unexpected character.");
                }
                break;
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void number() {

        while (this.isDigit(this.peek())) {
            this.advance();
        }

        if (this.peek() == '.' && this.isDigit(this.peekNext())) {
            this.advance();

            while (isDigit(peek())) {
                advance();
            }
        }

        this.addToken(NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }

    private void string() {
        while (this.peek() != '"' && !this.isAtEnd()) {
            if (peek() == '\n') {
                this.line += 1; // very clever..
            }
            this.advance();
        }

        if (this.isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }

        // closing "
        this.advance();

        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private boolean match(char expected) {
        if (this.isAtEnd()) {
            return false;
        }

        if (this.source.charAt(current) != expected) {
            return false;
        }

        this.current++;
        return true;
    }

    // lookahead - 1character
    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return this.source.charAt(current);
    }

    // lookahead - 2character
    private char peekNext() {
        if (this.current + 1 >= source.length()) {
            return '\0';
        }
        return source.charAt(current + 1);
    }
}