import java.util.Collections;
import java.util.List;
import java.util.*;

public class LexicalAnalyser {

    public static List<Token> analyse(String input) throws NumberException, ExpressionException {
        List<Token> tokenList = new LinkedList<Token>();

        //for checking operators at the start of an expression.
        List<Character> operators = new LinkedList<>();
        operators.add('+');
        operators.add('-');
        operators.add('*');
        operators.add('/');

        State state = State.A;

        //for checking whitespaces in between a number.
        List<Character> chara = new LinkedList();
        chara.add('1');
        chara.add('2');
        chara.add('3');
        chara.add('4');
        chara.add('5');
        chara.add('6');
        chara.add('7');
        chara.add('8');
        chara.add('9');

        System.out.println(input);

       //Deleting whitespace from input.
        String s = "";
        for (int i = 0; i< input.length(); i++) {
            if (input.charAt(i) != ' '){
                s += input.charAt(i);
            }
            else {
                chara.add('0');
                System.out.println(input.charAt(i));
                if ( i != 0 && chara.contains(input.charAt(i - 1)) && chara.contains(input.charAt(i + 1))) {
                    throw new ExpressionException();
                }
                chara.remove(chara.indexOf('0'));
            }
        }

        input = s;
        System.out.println(s);
        boolean checkLastDpt = input.endsWith(".");

        String nzd = "";

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '0') {
                switch(state) {
                    case A:
                        state = State.B;
                        nzd += 0;
                        System.out.println(nzd);
                        break;

                    case E:
                        state = State.B;
                        nzd += 0;
                        if (i == input.length() - 1) {
                            Token as = new Token(Integer.parseInt(nzd));
                            tokenList.add(as);
                        }
                        break;

                    case C:
                        state = State.F;
                        System.out.println(nzd);
                        if ( i== input.length()-1 || operators.contains(input.charAt(i+1))) {
                            nzd += 0;
                            tokenList.add(new Token(Double.parseDouble(nzd)));
                        }
                        else {
                            nzd+=0;
                        }
                        break;

                    case D:
                        state = State.D;
                        nzd += 0;
                        if (i == input.length() - 1) {
                            Token as = new Token(Integer.parseInt(nzd));
                            tokenList.add(as);
                        }
                        break;

                    case B:
                        state = State.G;
                        throw new NumberException();

                    case F:
                        state = State.F;
                        nzd += 0;
                        if (i == input.length() - 1  || operators.contains(input.charAt(i + 1))) {
                            tokenList.add(new Token(Double.parseDouble(nzd)));
                        }
                        break;
                }
            }

            else if (input.charAt(i) == '.') {
                switch(state) {
                    case A:
                    state = State.G;
                    throw new NumberException();

                    case B:
                    if (!checkLastDpt) {
                        state = State.C;
                        nzd += '.';
                        System.out.println(nzd);
                        break;
                    }
                    else {
                        throw new NumberException();
                    }

                    case D:
                    state = State.G;
                    throw new NumberException();

                    case G:
                    state = State.G;
                    break;
                }
            }

            else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/' ) {
                Token a = new Token();
                switch(input.charAt(i)) {
                    case '+':
                    a = new Token(Token.TokenType.PLUS);
                    break;
                    case '-':
                    a = new Token(Token.TokenType.MINUS);
                    break;
                    case '*':
                    a = new Token(Token.TokenType.TIMES);
                    break;
                    case '/':
                    a = new Token(Token.TokenType.DIVIDE);
                    break;
                }
                switch(state) {
                    case A:
                        state = State.G;
                        throw new ExpressionException();

                    case B:
                    state = State.E;
                    if (i != input.length() - 1) {
                        tokenList.add(a);
                    }
                    else {
                        throw new ExpressionException();
                    }
                    break;

                    case D:
                    state = State.E;
                    if (i != input.length() - 1) {
                        Token myToken = new Token(Integer.parseInt(nzd));
                        tokenList.add(myToken);
                        nzd = "";
                        tokenList.add(a);
                    }
                    else {
                        throw new ExpressionException();
                    }
                    break;

                    case F:
                    state = State.E;
                        if (i != input.length() - 1) {
                            Token my1Token = new Token(Double.parseDouble(nzd));
                            tokenList.add(my1Token);
                            nzd = "";
                            tokenList.add(a);
                        }
                        else {
                            throw new ExpressionException();
                        }
                    break;

                    case E:
                    state = State.G;
                    throw new ExpressionException();

                    case G:
                    state = State.G;
                    break;
                }
            }

            else if (chara.contains(input.charAt(i))){
                switch(state) {
                    case A:
                        state = State.D;
                        nzd += input.charAt(i);
                        if (i == input.length() - 1) {
                            Token as = new Token(Integer.parseInt(nzd));
                            tokenList.add(as);
                        }
                        break;


                    case C:
                        state = State.F;
                        if (i == input.length()-1 || operators.contains(input.charAt(i+1))) {
                            nzd += input.charAt(i);
                            tokenList.add(new Token(Double.parseDouble(nzd)));
                        }
                        else {
                            nzd+=input.charAt(i);
                        }
                        break;

                    case D:
                        state = State.D;
                        nzd += input.charAt(i);
                        if (i == input.length() - 1) {
                                Token as = new Token(Integer.parseInt(nzd));
                                tokenList.add(as);
                        }
                        break;

                    case F:
                        state = State.F;
                        nzd += input.charAt(i);
                        System.out.println(nzd);
                        if (i == input.length() - 1) {
                            Token as = new Token(Double.parseDouble(nzd));
                            tokenList.add(as);
                        }
                        break;

                    case E:
                        state = State.D;
                        nzd += input.charAt(i);
                        if (i == input.length() - 1) {
                            Token as = new Token(Integer.parseInt(nzd));
                            tokenList.add(as);
                        }
                        break;

                    case B:
                    state = State.G;
                    throw new NumberException();

                    case G:
                    state = State.G;
                    break;
                }
            }
            else {
                state = State.G;
                throw new ExpressionException();
            }

        }

        return tokenList;
    }

    private enum State {
        /** 
         * A: start state
         * B: single 0 possibly before an operator
         * C: 0.
         * D: one or more digits that starts with a non zero digit possibly before an operator
         * E: valid expression(s) followed by an operator
         * F: 0. followed by one or more digits
         * G: sink state, invalid numbers or two connected operators.
         * 
         * accept states: B, D, F
         **/

        A, B, C, D, E, F, G
    }

}
