package parser;

/**
 * when parsing, when an error is found, the parsers generated with ANTLR only
 * print the error, recover and continue parsing. This is not the desired
 * behavior in my case, so
 * {@link ExprLexer#displayRecognitionError(String[], org.antlr.runtime.RecognitionException)}, 
 * 
 * {@link ExprParser#displayRecognitionError(String[], org.antlr.runtime.RecognitionException)} and
 * {@link Eval#emitErrorMessage(String) 
 * are overridden to throw this exception instead of only printing the error
 * 
 * @author kux
 * 
 */
public class UncheckedParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UncheckedParserException(String msg) {
		super(msg);
	}

}
