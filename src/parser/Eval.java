// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 D:\\practice\\functiondrawer\\etc/Eval.g 2009-08-19 18:56:50

package parser;
import java.util.HashMap;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class Eval extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "FLOAT", "NEWLINE", "WS", "'+'", "'-'", "'*'", "'/'", "'('", "')'", "'sin'", "'cos'", "'exp'"
    };
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


        public Eval(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public Eval(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return Eval.tokenNames; }
    public String getGrammarFileName() { return "D:\\practice\\functiondrawer\\etc/Eval.g"; }


    /** Map variable name to Integer object holding value */
    	private HashMap<String, Double> memory = new HashMap<String,Double>();
    	
    	public HashMap<String, Double> getMemory(){
    		return memory;
    	}
    	
    	public void setMemory(HashMap<String, Double> memory){
    		this.memory=memory;
    	}
    	




    // $ANTLR start "entry"
    // D:\\practice\\functiondrawer\\etc/Eval.g:33:1: entry returns [double value] : v= expr ;
    public final double entry() throws RecognitionException {
        double value = 0.0;

        double v = 0.0;


        try {
            // D:\\practice\\functiondrawer\\etc/Eval.g:34:6: (v= expr )
            // D:\\practice\\functiondrawer\\etc/Eval.g:34:9: v= expr
            {
            pushFollow(FOLLOW_expr_in_entry72);
            v=expr();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
              value =v;
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "entry"


    // $ANTLR start "expr"
    // D:\\practice\\functiondrawer\\etc/Eval.g:40:1: expr returns [double value] : ( ^( '+' a= expr b= expr ) | ^( '-' a= expr b= expr ) | ^( '*' a= expr b= expr ) | ^( '/' a= expr b= expr ) | ^( 'sin' v= expr ) | ^( 'cos' v= expr ) | ^( 'exp' v= expr ) | ^( '-' v= expr ) | ^( '+' v= expr ) | ID | FLOAT );
    public final double expr() throws RecognitionException {
        double value = 0.0;

        CommonTree ID1=null;
        CommonTree FLOAT2=null;
        double a = 0.0;

        double b = 0.0;

        double v = 0.0;


        try {
            // D:\\practice\\functiondrawer\\etc/Eval.g:41:5: ( ^( '+' a= expr b= expr ) | ^( '-' a= expr b= expr ) | ^( '*' a= expr b= expr ) | ^( '/' a= expr b= expr ) | ^( 'sin' v= expr ) | ^( 'cos' v= expr ) | ^( 'exp' v= expr ) | ^( '-' v= expr ) | ^( '+' v= expr ) | ID | FLOAT )
            int alt1=11;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:41:9: ^( '+' a= expr b= expr )
                    {
                    match(input,8,FOLLOW_8_in_expr103); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr107);
                    a=expr();

                    state._fsp--;
                    if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr111);
                    b=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = a+b;
                    }

                    }
                    break;
                case 2 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:42:9: ^( '-' a= expr b= expr )
                    {
                    match(input,9,FOLLOW_9_in_expr125); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr129);
                    a=expr();

                    state._fsp--;
                    if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr133);
                    b=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = a-b;
                    }

                    }
                    break;
                case 3 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:43:9: ^( '*' a= expr b= expr )
                    {
                    match(input,10,FOLLOW_10_in_expr150); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr154);
                    a=expr();

                    state._fsp--;
                    if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr158);
                    b=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = a*b;
                    }

                    }
                    break;
                case 4 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:44:9: ^( '/' a= expr b= expr )
                    {
                    match(input,11,FOLLOW_11_in_expr172); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr176);
                    a=expr();

                    state._fsp--;
                    if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr180);
                    b=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = a/b;
                    }

                    }
                    break;
                case 5 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:45:9: ^( 'sin' v= expr )
                    {
                    match(input,14,FOLLOW_14_in_expr194); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr198);
                    v=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = Math.sin(v);
                    }

                    }
                    break;
                case 6 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:46:9: ^( 'cos' v= expr )
                    {
                    match(input,15,FOLLOW_15_in_expr217); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr221);
                    v=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = Math.cos(v);
                    }

                    }
                    break;
                case 7 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:47:9: ^( 'exp' v= expr )
                    {
                    match(input,16,FOLLOW_16_in_expr240); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr244);
                    v=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = Math.exp(v);
                    }

                    }
                    break;
                case 8 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:48:9: ^( '-' v= expr )
                    {
                    match(input,9,FOLLOW_9_in_expr263); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr269);
                    v=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = -v; 
                    }

                    }
                    break;
                case 9 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:49:9: ^( '+' v= expr )
                    {
                    match(input,8,FOLLOW_8_in_expr288); if (state.failed) return value;

                    match(input, Token.DOWN, null); if (state.failed) return value;
                    pushFollow(FOLLOW_expr_in_expr294);
                    v=expr();

                    state._fsp--;
                    if (state.failed) return value;

                    match(input, Token.UP, null); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = v; 
                    }

                    }
                    break;
                case 10 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:50:9: ID
                    {
                    ID1=(CommonTree)match(input,ID,FOLLOW_ID_in_expr312); if (state.failed) return value;
                    if ( state.backtracking==0 ) {

                              	Double d = (Double)memory.get((ID1!=null?ID1.getText():null));
                              	if ( d!=null ) value = d.doubleValue();
                              	else System.err.println("undefined variable "+(ID1!=null?ID1.getText():null));
                              
                    }

                    }
                    break;
                case 11 :
                    // D:\\practice\\functiondrawer\\etc/Eval.g:56:9: FLOAT
                    {
                    FLOAT2=(CommonTree)match(input,FLOAT,FOLLOW_FLOAT_in_expr333); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                      value = Double.parseDouble((FLOAT2!=null?FLOAT2.getText():null));
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "expr"

    // $ANTLR start synpred1_Eval
    public final void synpred1_Eval_fragment() throws RecognitionException {   
        double a = 0.0;

        double b = 0.0;


        // D:\\practice\\functiondrawer\\etc/Eval.g:41:9: ( ^( '+' a= expr b= expr ) )
        // D:\\practice\\functiondrawer\\etc/Eval.g:41:9: ^( '+' a= expr b= expr )
        {
        match(input,8,FOLLOW_8_in_synpred1_Eval103); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred1_Eval107);
        a=expr();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred1_Eval111);
        b=expr();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Eval

    // $ANTLR start synpred2_Eval
    public final void synpred2_Eval_fragment() throws RecognitionException {   
        double a = 0.0;

        double b = 0.0;


        // D:\\practice\\functiondrawer\\etc/Eval.g:42:9: ( ^( '-' a= expr b= expr ) )
        // D:\\practice\\functiondrawer\\etc/Eval.g:42:9: ^( '-' a= expr b= expr )
        {
        match(input,9,FOLLOW_9_in_synpred2_Eval125); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred2_Eval129);
        a=expr();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred2_Eval133);
        b=expr();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Eval

    // $ANTLR start synpred8_Eval
    public final void synpred8_Eval_fragment() throws RecognitionException {   
        double v = 0.0;


        // D:\\practice\\functiondrawer\\etc/Eval.g:48:9: ( ^( '-' v= expr ) )
        // D:\\practice\\functiondrawer\\etc/Eval.g:48:9: ^( '-' v= expr )
        {
        match(input,9,FOLLOW_9_in_synpred8_Eval263); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred8_Eval269);
        v=expr();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_Eval

    // $ANTLR start synpred9_Eval
    public final void synpred9_Eval_fragment() throws RecognitionException {   
        double v = 0.0;


        // D:\\practice\\functiondrawer\\etc/Eval.g:49:9: ( ^( '+' v= expr ) )
        // D:\\practice\\functiondrawer\\etc/Eval.g:49:9: ^( '+' v= expr )
        {
        match(input,8,FOLLOW_8_in_synpred9_Eval288); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred9_Eval294);
        v=expr();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Eval

    // Delegated rules

    public final boolean synpred8_Eval() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_Eval_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Eval() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Eval_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Eval() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Eval_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_Eval() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_Eval_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\16\uffff";
    static final String DFA1_eofS =
        "\16\uffff";
    static final String DFA1_minS =
        "\1\4\2\0\13\uffff";
    static final String DFA1_maxS =
        "\1\20\2\0\13\uffff";
    static final String DFA1_acceptS =
        "\3\uffff\1\3\1\4\1\5\1\6\1\7\1\12\1\13\1\1\1\11\1\2\1\10";
    static final String DFA1_specialS =
        "\1\uffff\1\0\1\1\13\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\10\1\11\2\uffff\1\1\1\2\1\3\1\4\2\uffff\1\5\1\6\1\7",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "40:1: expr returns [double value] : ( ^( '+' a= expr b= expr ) | ^( '-' a= expr b= expr ) | ^( '*' a= expr b= expr ) | ^( '/' a= expr b= expr ) | ^( 'sin' v= expr ) | ^( 'cos' v= expr ) | ^( 'exp' v= expr ) | ^( '-' v= expr ) | ^( '+' v= expr ) | ID | FLOAT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TreeNodeStream input = (TreeNodeStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA1_1 = input.LA(1);

                         
                        int index1_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Eval()) ) {s = 10;}

                        else if ( (synpred9_Eval()) ) {s = 11;}

                         
                        input.seek(index1_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA1_2 = input.LA(1);

                         
                        int index1_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Eval()) ) {s = 12;}

                        else if ( (synpred8_Eval()) ) {s = 13;}

                         
                        input.seek(index1_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 1, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_expr_in_entry72 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_8_in_expr103 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr107 = new BitSet(new long[]{0x000000000001CF30L});
    public static final BitSet FOLLOW_expr_in_expr111 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_9_in_expr125 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr129 = new BitSet(new long[]{0x000000000001CF30L});
    public static final BitSet FOLLOW_expr_in_expr133 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_10_in_expr150 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr154 = new BitSet(new long[]{0x000000000001CF30L});
    public static final BitSet FOLLOW_expr_in_expr158 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_11_in_expr172 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr176 = new BitSet(new long[]{0x000000000001CF30L});
    public static final BitSet FOLLOW_expr_in_expr180 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_14_in_expr194 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr198 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_15_in_expr217 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr221 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_16_in_expr240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr244 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_9_in_expr263 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr269 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_8_in_expr288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr294 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_expr312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_expr333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_8_in_synpred1_Eval103 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_synpred1_Eval107 = new BitSet(new long[]{0x000000000001CF30L});
    public static final BitSet FOLLOW_expr_in_synpred1_Eval111 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_9_in_synpred2_Eval125 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_synpred2_Eval129 = new BitSet(new long[]{0x000000000001CF30L});
    public static final BitSet FOLLOW_expr_in_synpred2_Eval133 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_9_in_synpred8_Eval263 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_synpred8_Eval269 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_8_in_synpred9_Eval288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_synpred9_Eval294 = new BitSet(new long[]{0x0000000000000008L});

}