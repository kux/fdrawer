tree grammar Eval;

options {
    tokenVocab=Expr;
    ASTLabelType=CommonTree;
    backtrack=true;
}

// START:members
@header {
package parser;
import java.util.HashMap;
}

@members {
/** Map variable name to Integer object holding value */
	private HashMap<String, Double> memory = new HashMap<String,Double>();
	
	public HashMap<String, Double> getMemory(){
		return memory;
	}
	
	public void setMemory(HashMap<String, Double> memory){
		this.memory=memory;
	}
	
	@Override
    public void recover(IntStream is, RecognitionException re) {
        
    }
    
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {

        throw new parser.UncheckedParserException();
    }

}
// END:members

// START:entry

entry returns [double value]
     :  v=expr {$value =v;} 
     ;
// END:entry


// START:expr
expr returns [double value]
    :   ^('+' a=expr b=expr) {$value = a+b;}
    |   ^('-' a=expr b=expr) {$value = a-b;}   
    |   ^('*' a=expr b=expr) {$value = a*b;}
    |   ^('/' a=expr b=expr) {$value = a/b;}
    |   ^('sin' v=expr )     {$value = Math.sin(v);}
    |   ^('cos' v=expr )     {$value = Math.cos(v);}
    |   ^('exp' v=expr )     {$value = Math.exp(v);}
    |   ^('-'   v=expr )     {$value = -v; }
    |   ^('+'   v=expr )     {$value = v; }
    |   ID 
        {
        	Double d = (Double)memory.get($ID.text);
        	if ( d!=null ) $value = d.doubleValue();
        	else System.err.println("undefined variable "+$ID.text);
        }
    |   FLOAT                  {$value = Double.parseDouble($FLOAT.text);}
    ;
// END:expr
