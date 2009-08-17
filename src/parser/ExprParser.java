// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 etc/Expr.g 2009-08-17 20:43:05

	package parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class ExprParser extends Parser {
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


        public ExprParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ExprParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ExprParser.tokenNames; }
    public String getGrammarFileName() { return "etc/Expr.g"; }


    public static class entry_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "entry"
    // etc/Expr.g:30:1: entry : ( expr NEWLINE -> expr | NEWLINE ->);
    public final ExprParser.entry_return entry() throws RecognitionException {
        ExprParser.entry_return retval = new ExprParser.entry_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NEWLINE2=null;
        Token NEWLINE3=null;
        ExprParser.expr_return expr1 = null;


        CommonTree NEWLINE2_tree=null;
        CommonTree NEWLINE3_tree=null;
        RewriteRuleTokenStream stream_NEWLINE=new RewriteRuleTokenStream(adaptor,"token NEWLINE");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // etc/Expr.g:30:6: ( expr NEWLINE -> expr | NEWLINE ->)
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>=ID && LA1_0<=FLOAT)||(LA1_0>=8 && LA1_0<=9)||LA1_0==12||(LA1_0>=14 && LA1_0<=16)) ) {
                alt1=1;
            }
            else if ( (LA1_0==NEWLINE) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // etc/Expr.g:30:10: expr NEWLINE
                    {
                    pushFollow(FOLLOW_expr_in_entry149);
                    expr1=expr();

                    state._fsp--;

                    stream_expr.add(expr1.getTree());
                    NEWLINE2=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_entry151);  
                    stream_NEWLINE.add(NEWLINE2);

                    System.out.println((expr1!=null?((CommonTree)expr1.tree):null)==null?"null":(expr1!=null?((CommonTree)expr1.tree):null).toStringTree());


                    // AST REWRITE
                    // elements: expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 30:102: -> expr
                    {
                        adaptor.addChild(root_0, stream_expr.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // etc/Expr.g:31:10: NEWLINE
                    {
                    NEWLINE3=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_entry174);  
                    stream_NEWLINE.add(NEWLINE3);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 31:30: ->
                    {
                        root_0 = null;
                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "entry"

    public static class expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // etc/Expr.g:36:1: expr : unaryExpr ( ( '+' | '-' ) unaryExpr )* ;
    public final ExprParser.expr_return expr() throws RecognitionException {
        ExprParser.expr_return retval = new ExprParser.expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal5=null;
        Token char_literal6=null;
        ExprParser.unaryExpr_return unaryExpr4 = null;

        ExprParser.unaryExpr_return unaryExpr7 = null;


        CommonTree char_literal5_tree=null;
        CommonTree char_literal6_tree=null;

        try {
            // etc/Expr.g:36:5: ( unaryExpr ( ( '+' | '-' ) unaryExpr )* )
            // etc/Expr.g:36:9: unaryExpr ( ( '+' | '-' ) unaryExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_unaryExpr_in_expr205);
            unaryExpr4=unaryExpr();

            state._fsp--;

            adaptor.addChild(root_0, unaryExpr4.getTree());
            // etc/Expr.g:36:19: ( ( '+' | '-' ) unaryExpr )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=8 && LA3_0<=9)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // etc/Expr.g:36:20: ( '+' | '-' ) unaryExpr
            	    {
            	    // etc/Expr.g:36:20: ( '+' | '-' )
            	    int alt2=2;
            	    int LA2_0 = input.LA(1);

            	    if ( (LA2_0==8) ) {
            	        alt2=1;
            	    }
            	    else if ( (LA2_0==9) ) {
            	        alt2=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 2, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt2) {
            	        case 1 :
            	            // etc/Expr.g:36:21: '+'
            	            {
            	            char_literal5=(Token)match(input,8,FOLLOW_8_in_expr209); 
            	            char_literal5_tree = (CommonTree)adaptor.create(char_literal5);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal5_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // etc/Expr.g:36:27: '-'
            	            {
            	            char_literal6=(Token)match(input,9,FOLLOW_9_in_expr213); 
            	            char_literal6_tree = (CommonTree)adaptor.create(char_literal6);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal6_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_unaryExpr_in_expr218);
            	    unaryExpr7=unaryExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, unaryExpr7.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class unaryExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryExpr"
    // etc/Expr.g:39:1: unaryExpr : ( '+' | '-' )* multExpr ;
    public final ExprParser.unaryExpr_return unaryExpr() throws RecognitionException {
        ExprParser.unaryExpr_return retval = new ExprParser.unaryExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal8=null;
        Token char_literal9=null;
        ExprParser.multExpr_return multExpr10 = null;


        CommonTree char_literal8_tree=null;
        CommonTree char_literal9_tree=null;

        try {
            // etc/Expr.g:39:10: ( ( '+' | '-' )* multExpr )
            // etc/Expr.g:39:13: ( '+' | '-' )* multExpr
            {
            root_0 = (CommonTree)adaptor.nil();

            // etc/Expr.g:39:13: ( '+' | '-' )*
            loop4:
            do {
                int alt4=3;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==8) ) {
                    alt4=1;
                }
                else if ( (LA4_0==9) ) {
                    alt4=2;
                }


                switch (alt4) {
            	case 1 :
            	    // etc/Expr.g:39:14: '+'
            	    {
            	    char_literal8=(Token)match(input,8,FOLLOW_8_in_unaryExpr239); 
            	    char_literal8_tree = (CommonTree)adaptor.create(char_literal8);
            	    root_0 = (CommonTree)adaptor.becomeRoot(char_literal8_tree, root_0);


            	    }
            	    break;
            	case 2 :
            	    // etc/Expr.g:39:21: '-'
            	    {
            	    char_literal9=(Token)match(input,9,FOLLOW_9_in_unaryExpr244); 
            	    char_literal9_tree = (CommonTree)adaptor.create(char_literal9);
            	    root_0 = (CommonTree)adaptor.becomeRoot(char_literal9_tree, root_0);


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            pushFollow(FOLLOW_multExpr_in_unaryExpr250);
            multExpr10=multExpr();

            state._fsp--;

            adaptor.addChild(root_0, multExpr10.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unaryExpr"

    public static class multExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multExpr"
    // etc/Expr.g:42:1: multExpr : atom ( ( '*' | '/' ) atom )* ;
    public final ExprParser.multExpr_return multExpr() throws RecognitionException {
        ExprParser.multExpr_return retval = new ExprParser.multExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set12=null;
        ExprParser.atom_return atom11 = null;

        ExprParser.atom_return atom13 = null;


        CommonTree set12_tree=null;

        try {
            // etc/Expr.g:43:5: ( atom ( ( '*' | '/' ) atom )* )
            // etc/Expr.g:43:9: atom ( ( '*' | '/' ) atom )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_atom_in_multExpr277);
            atom11=atom();

            state._fsp--;

            adaptor.addChild(root_0, atom11.getTree());
            // etc/Expr.g:43:14: ( ( '*' | '/' ) atom )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>=10 && LA5_0<=11)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // etc/Expr.g:43:15: ( '*' | '/' ) atom
            	    {
            	    set12=(Token)input.LT(1);
            	    set12=(Token)input.LT(1);
            	    if ( (input.LA(1)>=10 && input.LA(1)<=11) ) {
            	        input.consume();
            	        root_0 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(set12), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_atom_in_multExpr287);
            	    atom13=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, atom13.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "multExpr"

    public static class atom_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // etc/Expr.g:46:1: atom : ( FLOAT | ID | '(' expr ')' | 'sin' atom | 'cos' atom | 'exp' atom );
    public final ExprParser.atom_return atom() throws RecognitionException {
        ExprParser.atom_return retval = new ExprParser.atom_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token FLOAT14=null;
        Token ID15=null;
        Token char_literal16=null;
        Token char_literal18=null;
        Token string_literal19=null;
        Token string_literal21=null;
        Token string_literal23=null;
        ExprParser.expr_return expr17 = null;

        ExprParser.atom_return atom20 = null;

        ExprParser.atom_return atom22 = null;

        ExprParser.atom_return atom24 = null;


        CommonTree FLOAT14_tree=null;
        CommonTree ID15_tree=null;
        CommonTree char_literal16_tree=null;
        CommonTree char_literal18_tree=null;
        CommonTree string_literal19_tree=null;
        CommonTree string_literal21_tree=null;
        CommonTree string_literal23_tree=null;

        try {
            // etc/Expr.g:46:5: ( FLOAT | ID | '(' expr ')' | 'sin' atom | 'cos' atom | 'exp' atom )
            int alt6=6;
            switch ( input.LA(1) ) {
            case FLOAT:
                {
                alt6=1;
                }
                break;
            case ID:
                {
                alt6=2;
                }
                break;
            case 12:
                {
                alt6=3;
                }
                break;
            case 14:
                {
                alt6=4;
                }
                break;
            case 15:
                {
                alt6=5;
                }
                break;
            case 16:
                {
                alt6=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // etc/Expr.g:46:9: FLOAT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    FLOAT14=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_atom304); 
                    FLOAT14_tree = (CommonTree)adaptor.create(FLOAT14);
                    adaptor.addChild(root_0, FLOAT14_tree);


                    }
                    break;
                case 2 :
                    // etc/Expr.g:47:9: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    ID15=(Token)match(input,ID,FOLLOW_ID_in_atom315); 
                    ID15_tree = (CommonTree)adaptor.create(ID15);
                    adaptor.addChild(root_0, ID15_tree);


                    }
                    break;
                case 3 :
                    // etc/Expr.g:48:9: '(' expr ')'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    char_literal16=(Token)match(input,12,FOLLOW_12_in_atom325); 
                    pushFollow(FOLLOW_expr_in_atom328);
                    expr17=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr17.getTree());
                    char_literal18=(Token)match(input,13,FOLLOW_13_in_atom330); 

                    }
                    break;
                case 4 :
                    // etc/Expr.g:49:9: 'sin' atom
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    string_literal19=(Token)match(input,14,FOLLOW_14_in_atom341); 
                    string_literal19_tree = (CommonTree)adaptor.create(string_literal19);
                    root_0 = (CommonTree)adaptor.becomeRoot(string_literal19_tree, root_0);

                    pushFollow(FOLLOW_atom_in_atom344);
                    atom20=atom();

                    state._fsp--;

                    adaptor.addChild(root_0, atom20.getTree());

                    }
                    break;
                case 5 :
                    // etc/Expr.g:50:9: 'cos' atom
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    string_literal21=(Token)match(input,15,FOLLOW_15_in_atom354); 
                    string_literal21_tree = (CommonTree)adaptor.create(string_literal21);
                    root_0 = (CommonTree)adaptor.becomeRoot(string_literal21_tree, root_0);

                    pushFollow(FOLLOW_atom_in_atom357);
                    atom22=atom();

                    state._fsp--;

                    adaptor.addChild(root_0, atom22.getTree());

                    }
                    break;
                case 6 :
                    // etc/Expr.g:51:9: 'exp' atom
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    string_literal23=(Token)match(input,16,FOLLOW_16_in_atom367); 
                    string_literal23_tree = (CommonTree)adaptor.create(string_literal23);
                    root_0 = (CommonTree)adaptor.becomeRoot(string_literal23_tree, root_0);

                    pushFollow(FOLLOW_atom_in_atom370);
                    atom24=atom();

                    state._fsp--;

                    adaptor.addChild(root_0, atom24.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_expr_in_entry149 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NEWLINE_in_entry151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_entry174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpr_in_expr205 = new BitSet(new long[]{0x0000000000000302L});
    public static final BitSet FOLLOW_8_in_expr209 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_9_in_expr213 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_unaryExpr_in_expr218 = new BitSet(new long[]{0x0000000000000302L});
    public static final BitSet FOLLOW_8_in_unaryExpr239 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_9_in_unaryExpr244 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_multExpr_in_unaryExpr250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_multExpr277 = new BitSet(new long[]{0x0000000000000C02L});
    public static final BitSet FOLLOW_set_in_multExpr280 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_atom_in_multExpr287 = new BitSet(new long[]{0x0000000000000C02L});
    public static final BitSet FOLLOW_FLOAT_in_atom304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_atom325 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_expr_in_atom328 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_atom330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_atom341 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_atom_in_atom344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_atom354 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_atom_in_atom357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_atom367 = new BitSet(new long[]{0x000000000001D330L});
    public static final BitSet FOLLOW_atom_in_atom370 = new BitSet(new long[]{0x0000000000000002L});

}