// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g 2009-08-18 00:22:49

	package parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ExprLexer extends Lexer {
    public static final int WS=7;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int NEWLINE=6;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int FLOAT=5;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;

    // delegates
    // delegators

    public ExprLexer() {;} 
    public ExprLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ExprLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/kux/workspace/practice-java/functiondrawer/etc/Expr.g"; }

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:7:6: ( '+' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:7:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:8:6: ( '-' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:8:8: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:9:7: ( '*' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:9:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:10:7: ( '/' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:10:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:11:7: ( '(' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:11:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:12:7: ( ')' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:12:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:13:7: ( 'sin' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:13:9: 'sin'
            {
            match("sin"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:14:7: ( 'cos' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:14:9: 'cos'
            {
            match("cos"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:15:7: ( 'exp' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:15:9: 'exp'
            {
            match("exp"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:17:5: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:17:9: ( 'a' .. 'z' | 'A' .. 'Z' )+
            {
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:17:9: ( 'a' .. 'z' | 'A' .. 'Z' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='A' && LA1_0<='Z')||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:
            	    {
            	    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:19:7: ( ( '0' .. '9' )+ | ( '0' .. '9' )+ '.' ( '0' .. '9' )+ )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:19:9: ( '0' .. '9' )+
                    {
                    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:19:9: ( '0' .. '9' )+
                    int cnt2=0;
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:19:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt2 >= 1 ) break loop2;
                                EarlyExitException eee =
                                    new EarlyExitException(2, input);
                                throw eee;
                        }
                        cnt2++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:20:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )+
                    {
                    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:20:9: ( '0' .. '9' )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:20:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);

                    match('.'); 
                    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:20:23: ( '0' .. '9' )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:20:24: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:22:8: ( ( '\\r' )? '\\n' )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:22:9: ( '\\r' )? '\\n'
            {
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:22:9: ( '\\r' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\r') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:22:9: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:24:5: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:24:9: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:24:9: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\t' && LA7_0<='\n')||LA7_0=='\r'||LA7_0==' ') ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);

            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:8: ( T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | ID | FLOAT | NEWLINE | WS )
        int alt8=13;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:10: T__8
                {
                mT__8(); 

                }
                break;
            case 2 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:15: T__9
                {
                mT__9(); 

                }
                break;
            case 3 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:20: T__10
                {
                mT__10(); 

                }
                break;
            case 4 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:26: T__11
                {
                mT__11(); 

                }
                break;
            case 5 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:32: T__12
                {
                mT__12(); 

                }
                break;
            case 6 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:38: T__13
                {
                mT__13(); 

                }
                break;
            case 7 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:44: T__14
                {
                mT__14(); 

                }
                break;
            case 8 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:50: T__15
                {
                mT__15(); 

                }
                break;
            case 9 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:56: T__16
                {
                mT__16(); 

                }
                break;
            case 10 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:62: ID
                {
                mID(); 

                }
                break;
            case 11 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:65: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 12 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:71: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 13 :
                // /home/kux/workspace/practice-java/functiondrawer/etc/Expr.g:1:79: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA5 dfa5 = new DFA5(this);
    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA5_eotS =
        "\1\uffff\1\2\2\uffff";
    static final String DFA5_eofS =
        "\4\uffff";
    static final String DFA5_minS =
        "\1\60\1\56\2\uffff";
    static final String DFA5_maxS =
        "\2\71\2\uffff";
    static final String DFA5_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA5_specialS =
        "\4\uffff}>";
    static final String[] DFA5_transitionS = {
            "\12\1",
            "\1\3\1\uffff\12\1",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "19:1: FLOAT : ( ( '0' .. '9' )+ | ( '0' .. '9' )+ '.' ( '0' .. '9' )+ );";
        }
    }
    static final String DFA8_eotS =
        "\7\uffff\3\12\2\uffff\1\16\1\22\1\uffff\3\12\1\uffff\1\26\1\27\1"+
        "\30\3\uffff";
    static final String DFA8_eofS =
        "\31\uffff";
    static final String DFA8_minS =
        "\1\11\6\uffff\1\151\1\157\1\170\2\uffff\1\12\1\11\1\uffff\1\156"+
        "\1\163\1\160\1\uffff\3\101\3\uffff";
    static final String DFA8_maxS =
        "\1\172\6\uffff\1\151\1\157\1\170\2\uffff\1\12\1\40\1\uffff\1\156"+
        "\1\163\1\160\1\uffff\3\172\3\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\3\uffff\1\12\1\13\2\uffff\1\15"+
        "\3\uffff\1\14\3\uffff\1\7\1\10\1\11";
    static final String DFA8_specialS =
        "\31\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\16\1\15\2\uffff\1\14\22\uffff\1\16\7\uffff\1\5\1\6\1\3\1"+
            "\1\1\uffff\1\2\1\uffff\1\4\12\13\7\uffff\32\12\6\uffff\2\12"+
            "\1\10\1\12\1\11\15\12\1\7\7\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\17",
            "\1\20",
            "\1\21",
            "",
            "",
            "\1\15",
            "\2\16\2\uffff\1\16\22\uffff\1\16",
            "",
            "\1\23",
            "\1\24",
            "\1\25",
            "",
            "\32\12\6\uffff\32\12",
            "\32\12\6\uffff\32\12",
            "\32\12\6\uffff\32\12",
            "",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | ID | FLOAT | NEWLINE | WS );";
        }
    }
 

}