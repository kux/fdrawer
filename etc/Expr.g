grammar Expr;
options {
    output=AST;
    ASTLabelType=CommonTree; // type of $stat.tree ref etc...
}

@lexer::header{
	package parser;
}

@header{
	package parser;
}

@members {
	@Override
    public void recover(IntStream is, RecognitionException re) {
        
    }
    
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {

        throw new parser.UncheckedParserException();
    }
    
}


// START:tokens

ID  :   ('a'..'z'|'A'..'Z')+ ;

FLOAT : ('0'..'9')+
      | ('0'..'9')+'.'('0'..'9')+ ;
      
NEWLINE:'\r'? '\n' ;

WS  :   (' '|'\t'|'\n'|'\r')+ {skip();} ;


// END:tokens

// START:entry
entry:   expr     {System.out.println($expr.tree==null?"null":$expr.tree.toStringTree());}    -> expr     
     ;
// END:entry

// START:expr
expr:   unaryExpr (('+'^ |'-'^ ) unaryExpr)*
    ; 
    
unaryExpr:  ('+'^ | '-'^ )* multExpr
            ;

multExpr
    :   atom (('*'|'/')^ atom)*
    ; 

atom:   FLOAT 
    |   ID
    |   '('! expr ')'!
    |   'sin'^ atom
    |   'cos'^ atom
    |   'exp'^ atom
	;

// END:expr




