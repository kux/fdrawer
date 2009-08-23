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

@lexer::members {
	public void displayRecognitionError(String[] tokenNames,
										RecognitionException e)
	{
		String hdr = getErrorHeader(e);
		String msg = getErrorMessage(e, tokenNames);
		throw new UncheckedParserException(msg);
	}	   
}

@members {
	public void displayRecognitionError(String[] tokenNames,
										RecognitionException e)
	{
		String hdr = getErrorHeader(e);
		String msg = getErrorMessage(e, tokenNames);
		throw new UncheckedParserException(msg);
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




