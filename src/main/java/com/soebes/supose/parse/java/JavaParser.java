// $ANTLR 3.0.1 C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g 2007-12-28 20:13:11

	package com.soebes.supose.parse.java;
	import java.util.HashMap;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
/** A Java 1.5 grammar for ANTLR v3 derived from the spec
 *
 *  This is a very close representation of the spec; the changes
 *  are comestic (remove left recursion) and also fixes (the spec
 *  isn't exactly perfect).  I have run this on the 1.4.2 source
 *  and some nasty looking enums from 1.5, but have not really
 *  tested for 1.5 compatibility.
 *
 *  I built this with: java -Xmx100M org.antlr.Tool java.g 
 *  and got two errors that are ok (for now):
 *  java.g:691:9: Decision can match input such as
 *    "'0'..'9'{'E', 'e'}{'+', '-'}'0'..'9'{'D', 'F', 'd', 'f'}"
 *    using multiple alternatives: 3, 4
 *  As a result, alternative(s) 4 were disabled for that input
 *  java.g:734:35: Decision can match input such as "{'$', 'A'..'Z',
 *    '_', 'a'..'z', '\u00C0'..'\u00D6', '\u00D8'..'\u00F6',
 *    '\u00F8'..'\u1FFF', '\u3040'..'\u318F', '\u3300'..'\u337F',
 *    '\u3400'..'\u3D2D', '\u4E00'..'\u9FFF', '\uF900'..'\uFAFF'}"
 *    using multiple alternatives: 1, 2
 *  As a result, alternative(s) 2 were disabled for that input
 *
 *  You can turn enum on/off as a keyword :)
 *
 *  Version 1.0 -- initial release July 5, 2006 (requires 3.0b2 or higher)
 *
 *  Primary author: Terence Parr, July 2006
 *
 *  Version 1.0.1 -- corrections by Koen Vanderkimpen & Marko van Dooren,
 *      October 25, 2006;
 *      fixed normalInterfaceDeclaration: now uses typeParameters instead
 *          of typeParameter (according to JLS, 3rd edition)
 *      fixed castExpression: no longer allows expression next to type
 *          (according to semantics in JLS, in contrast with syntax in JLS)
 *
 *  Version 1.0.2 -- Terence Parr, Nov 27, 2006
 *      java spec I built this from had some bizarre for-loop control.
 *          Looked weird and so I looked elsewhere...Yep, it's messed up.
 *          simplified.
 *
 *  Version 1.0.3 -- Chris Hogue, Feb 26, 2007
 *      Factored out an annotationName rule and used it in the annotation rule.
 *          Not sure why, but typeName wasn't recognizing references to inner
 *          annotations (e.g. @InterfaceName.InnerAnnotation())
 *      Factored out the elementValue section of an annotation reference.  Created 
 *          elementValuePair and elementValuePairs rules, then used them in the 
 *          annotation rule.  Allows it to recognize annotation references with 
 *          multiple, comma separated attributes.
 *      Updated elementValueArrayInitializer so that it allows multiple elements.
 *          (It was only allowing 0 or 1 element).
 *      Updated localVariableDeclaration to allow annotations.  Interestingly the JLS
 *          doesn't appear to indicate this is legal, but it does work as of at least
 *          JDK 1.5.0_06.
 *      Moved the Identifier portion of annotationTypeElementRest to annotationMethodRest.
 *          Because annotationConstantRest already references variableDeclarator which 
 *          has the Identifier portion in it, the parser would fail on constants in 
 *          annotation definitions because it expected two identifiers.  
 *      Added optional trailing ';' to the alternatives in annotationTypeElementRest.
 *          Wouldn't handle an inner interface that has a trailing ';'.
 *      Swapped the expression and type rule reference order in castExpression to 
 *          make it check for genericized casts first.  It was failing to recognize a
 *          statement like  "Class<Byte> TYPE = (Class<Byte>)...;" because it was seeing
 *          'Class<Byte' in the cast expression as a less than expression, then failing 
 *          on the '>'.
 *      Changed createdName to use typeArguments instead of nonWildcardTypeArguments.
 *          Again, JLS doesn't seem to allow this, but java.lang.Class has an example of
 *          of this construct.
 *      Changed the 'this' alternative in primary to allow 'identifierSuffix' rather than
 *          just 'arguments'.  The case it couldn't handle was a call to an explicit
 *          generic method invocation (e.g. this.<E>doSomething()).  Using identifierSuffix
 *          may be overly aggressive--perhaps should create a more constrained thisSuffix rule?
 * 		
 *  Version 1.0.4 -- Hiroaki Nakamura, May 3, 2007
 *
 *	Fixed formalParameterDecls, localVariableDeclaration, forInit,
 *	and forVarControl to use variableModifier* not 'final'? (annotation)?
 *
 *  Version 1.0.5 -- Terence, June 21, 2007
 *	--a[i].foo didn't work. Fixed unaryExpression
 */
public class JavaParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Identifier", "ENUM", "FloatingPointLiteral", "CharacterLiteral", "StringLiteral", "HexLiteral", "OctalLiteral", "DecimalLiteral", "HexDigit", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "EscapeSequence", "UnicodeEscape", "OctalEscape", "Letter", "JavaIDDigit", "WS", "COMMENT", "LINE_COMMENT", "'package'", "';'", "'import'", "'static'", "'.'", "'*'", "'class'", "'extends'", "'implements'", "'<'", "','", "'>'", "'&'", "'{'", "'}'", "'interface'", "'void'", "'['", "']'", "'throws'", "'='", "'public'", "'protected'", "'private'", "'abstract'", "'final'", "'native'", "'synchronized'", "'transient'", "'volatile'", "'strictfp'", "'boolean'", "'char'", "'byte'", "'short'", "'int'", "'long'", "'float'", "'double'", "'?'", "'super'", "'('", "')'", "'...'", "'null'", "'true'", "'false'", "'@'", "'default'", "'assert'", "':'", "'if'", "'else'", "'for'", "'while'", "'do'", "'try'", "'finally'", "'switch'", "'return'", "'throw'", "'break'", "'continue'", "'catch'", "'case'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'||'", "'&&'", "'|'", "'^'", "'=='", "'!='", "'instanceof'", "'+'", "'-'", "'/'", "'%'", "'++'", "'--'", "'~'", "'!'", "'this'", "'new'"
    };
    public static final int HexLiteral=9;
    public static final int LINE_COMMENT=23;
    public static final int FloatTypeSuffix=15;
    public static final int OctalLiteral=10;
    public static final int IntegerTypeSuffix=13;
    public static final int CharacterLiteral=7;
    public static final int Exponent=14;
    public static final int EOF=-1;
    public static final int DecimalLiteral=11;
    public static final int HexDigit=12;
    public static final int Identifier=4;
    public static final int StringLiteral=8;
    public static final int WS=21;
    public static final int ENUM=5;
    public static final int UnicodeEscape=17;
    public static final int FloatingPointLiteral=6;
    public static final int JavaIDDigit=20;
    public static final int COMMENT=22;
    public static final int EscapeSequence=16;
    public static final int OctalEscape=18;
    public static final int Letter=19;

        public JavaParser(TokenStream input) {
            super(input);
            ruleMemo = new HashMap[403+1];
         }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g"; }

    
    	public enum MethodType {
    		METHOD,
    		METHOD_VOID,
    		METHOD_STATIC
    	}
    	private HashMap methods = new HashMap();
    
    	public HashMap getMethods () {
    		return methods;
    	}
    	private void addMethod(String name, MethodType type) {
    		methods.put(name, type.toString());
    	}
    	



    // $ANTLR start compilationUnit
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:121:1: compilationUnit : ( annotations )? ( packageDeclaration )? ( importDeclaration )* ( typeDeclaration )* ;
    public final void compilationUnit() throws RecognitionException {
        int compilationUnit_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 1) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:122:2: ( ( annotations )? ( packageDeclaration )? ( importDeclaration )* ( typeDeclaration )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:122:4: ( annotations )? ( packageDeclaration )? ( importDeclaration )* ( typeDeclaration )*
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:122:4: ( annotations )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==71) ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1==Identifier) ) {
                    int LA1_21 = input.LA(3);

                    if ( (synpred1()) ) {
                        alt1=1;
                    }
                }
            }
            switch (alt1) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: annotations
                    {
                    pushFollow(FOLLOW_annotations_in_compilationUnit54);
                    annotations();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:123:3: ( packageDeclaration )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==24) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: packageDeclaration
                    {
                    pushFollow(FOLLOW_packageDeclaration_in_compilationUnit59);
                    packageDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:124:9: ( importDeclaration )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==26) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: importDeclaration
            	    {
            	    pushFollow(FOLLOW_importDeclaration_in_compilationUnit70);
            	    importDeclaration();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:125:9: ( typeDeclaration )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==ENUM||LA4_0==25||LA4_0==27||LA4_0==30||LA4_0==39||(LA4_0>=45 && LA4_0<=54)||LA4_0==71) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: typeDeclaration
            	    {
            	    pushFollow(FOLLOW_typeDeclaration_in_compilationUnit81);
            	    typeDeclaration();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 1, compilationUnit_StartIndex); }
        }
        return ;
    }
    // $ANTLR end compilationUnit


    // $ANTLR start packageDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:128:1: packageDeclaration : 'package' qualifiedName ';' ;
    public final void packageDeclaration() throws RecognitionException {
        int packageDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 2) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:129:2: ( 'package' qualifiedName ';' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:129:4: 'package' qualifiedName ';'
            {
            match(input,24,FOLLOW_24_in_packageDeclaration93); if (failed) return ;
            pushFollow(FOLLOW_qualifiedName_in_packageDeclaration95);
            qualifiedName();
            _fsp--;
            if (failed) return ;
            match(input,25,FOLLOW_25_in_packageDeclaration97); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 2, packageDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end packageDeclaration


    // $ANTLR start importDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:132:1: importDeclaration : 'import' ( 'static' )? Identifier ( '.' Identifier )* ( '.' '*' )? ';' ;
    public final void importDeclaration() throws RecognitionException {
        int importDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 3) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:133:2: ( 'import' ( 'static' )? Identifier ( '.' Identifier )* ( '.' '*' )? ';' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:133:4: 'import' ( 'static' )? Identifier ( '.' Identifier )* ( '.' '*' )? ';'
            {
            match(input,26,FOLLOW_26_in_importDeclaration109); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:133:13: ( 'static' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==27) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: 'static'
                    {
                    match(input,27,FOLLOW_27_in_importDeclaration111); if (failed) return ;

                    }
                    break;

            }

            match(input,Identifier,FOLLOW_Identifier_in_importDeclaration114); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:133:34: ( '.' Identifier )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==28) ) {
                    int LA6_1 = input.LA(2);

                    if ( (LA6_1==Identifier) ) {
                        alt6=1;
                    }


                }


                switch (alt6) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:133:35: '.' Identifier
            	    {
            	    match(input,28,FOLLOW_28_in_importDeclaration117); if (failed) return ;
            	    match(input,Identifier,FOLLOW_Identifier_in_importDeclaration119); if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:133:52: ( '.' '*' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==28) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:133:53: '.' '*'
                    {
                    match(input,28,FOLLOW_28_in_importDeclaration124); if (failed) return ;
                    match(input,29,FOLLOW_29_in_importDeclaration126); if (failed) return ;

                    }
                    break;

            }

            match(input,25,FOLLOW_25_in_importDeclaration130); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 3, importDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end importDeclaration


    // $ANTLR start typeDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:136:1: typeDeclaration : ( classOrInterfaceDeclaration | ';' );
    public final void typeDeclaration() throws RecognitionException {
        int typeDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 4) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:137:2: ( classOrInterfaceDeclaration | ';' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==ENUM||LA8_0==27||LA8_0==30||LA8_0==39||(LA8_0>=45 && LA8_0<=54)||LA8_0==71) ) {
                alt8=1;
            }
            else if ( (LA8_0==25) ) {
                alt8=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("136:1: typeDeclaration : ( classOrInterfaceDeclaration | ';' );", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:137:4: classOrInterfaceDeclaration
                    {
                    pushFollow(FOLLOW_classOrInterfaceDeclaration_in_typeDeclaration142);
                    classOrInterfaceDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:138:9: ';'
                    {
                    match(input,25,FOLLOW_25_in_typeDeclaration152); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 4, typeDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end typeDeclaration


    // $ANTLR start classOrInterfaceDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:141:1: classOrInterfaceDeclaration : ( modifier )* ( classDeclaration | interfaceDeclaration ) ;
    public final void classOrInterfaceDeclaration() throws RecognitionException {
        int classOrInterfaceDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 5) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:142:2: ( ( modifier )* ( classDeclaration | interfaceDeclaration ) )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:142:4: ( modifier )* ( classDeclaration | interfaceDeclaration )
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:142:4: ( modifier )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==71) ) {
                    int LA9_4 = input.LA(2);

                    if ( (LA9_4==Identifier) ) {
                        alt9=1;
                    }


                }
                else if ( (LA9_0==27||(LA9_0>=45 && LA9_0<=54)) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: modifier
            	    {
            	    pushFollow(FOLLOW_modifier_in_classOrInterfaceDeclaration164);
            	    modifier();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:142:14: ( classDeclaration | interfaceDeclaration )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ENUM||LA10_0==30) ) {
                alt10=1;
            }
            else if ( (LA10_0==39||LA10_0==71) ) {
                alt10=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("142:14: ( classDeclaration | interfaceDeclaration )", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:142:15: classDeclaration
                    {
                    pushFollow(FOLLOW_classDeclaration_in_classOrInterfaceDeclaration168);
                    classDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:142:34: interfaceDeclaration
                    {
                    pushFollow(FOLLOW_interfaceDeclaration_in_classOrInterfaceDeclaration172);
                    interfaceDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 5, classOrInterfaceDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end classOrInterfaceDeclaration


    // $ANTLR start classDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:145:1: classDeclaration : ( normalClassDeclaration | enumDeclaration );
    public final void classDeclaration() throws RecognitionException {
        int classDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 6) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:146:2: ( normalClassDeclaration | enumDeclaration )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==30) ) {
                alt11=1;
            }
            else if ( (LA11_0==ENUM) ) {
                alt11=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("145:1: classDeclaration : ( normalClassDeclaration | enumDeclaration );", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:146:4: normalClassDeclaration
                    {
                    pushFollow(FOLLOW_normalClassDeclaration_in_classDeclaration185);
                    normalClassDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:147:9: enumDeclaration
                    {
                    pushFollow(FOLLOW_enumDeclaration_in_classDeclaration195);
                    enumDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 6, classDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end classDeclaration


    // $ANTLR start normalClassDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:150:1: normalClassDeclaration : 'class' Identifier ( typeParameters )? ( 'extends' type )? ( 'implements' typeList )? classBody ;
    public final void normalClassDeclaration() throws RecognitionException {
        int normalClassDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 7) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:151:2: ( 'class' Identifier ( typeParameters )? ( 'extends' type )? ( 'implements' typeList )? classBody )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:151:4: 'class' Identifier ( typeParameters )? ( 'extends' type )? ( 'implements' typeList )? classBody
            {
            match(input,30,FOLLOW_30_in_normalClassDeclaration207); if (failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_normalClassDeclaration209); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:151:23: ( typeParameters )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==33) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:151:24: typeParameters
                    {
                    pushFollow(FOLLOW_typeParameters_in_normalClassDeclaration212);
                    typeParameters();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:152:9: ( 'extends' type )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==31) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:152:10: 'extends' type
                    {
                    match(input,31,FOLLOW_31_in_normalClassDeclaration225); if (failed) return ;
                    pushFollow(FOLLOW_type_in_normalClassDeclaration227);
                    type();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:153:9: ( 'implements' typeList )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==32) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:153:10: 'implements' typeList
                    {
                    match(input,32,FOLLOW_32_in_normalClassDeclaration240); if (failed) return ;
                    pushFollow(FOLLOW_typeList_in_normalClassDeclaration242);
                    typeList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            pushFollow(FOLLOW_classBody_in_normalClassDeclaration254);
            classBody();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 7, normalClassDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end normalClassDeclaration


    // $ANTLR start typeParameters
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:157:1: typeParameters : '<' typeParameter ( ',' typeParameter )* '>' ;
    public final void typeParameters() throws RecognitionException {
        int typeParameters_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 8) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:158:2: ( '<' typeParameter ( ',' typeParameter )* '>' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:158:4: '<' typeParameter ( ',' typeParameter )* '>'
            {
            match(input,33,FOLLOW_33_in_typeParameters266); if (failed) return ;
            pushFollow(FOLLOW_typeParameter_in_typeParameters268);
            typeParameter();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:158:22: ( ',' typeParameter )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==34) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:158:23: ',' typeParameter
            	    {
            	    match(input,34,FOLLOW_34_in_typeParameters271); if (failed) return ;
            	    pushFollow(FOLLOW_typeParameter_in_typeParameters273);
            	    typeParameter();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            match(input,35,FOLLOW_35_in_typeParameters277); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 8, typeParameters_StartIndex); }
        }
        return ;
    }
    // $ANTLR end typeParameters


    // $ANTLR start typeParameter
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:161:1: typeParameter : Identifier ( 'extends' bound )? ;
    public final void typeParameter() throws RecognitionException {
        int typeParameter_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 9) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:162:2: ( Identifier ( 'extends' bound )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:162:4: Identifier ( 'extends' bound )?
            {
            match(input,Identifier,FOLLOW_Identifier_in_typeParameter288); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:162:15: ( 'extends' bound )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==31) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:162:16: 'extends' bound
                    {
                    match(input,31,FOLLOW_31_in_typeParameter291); if (failed) return ;
                    pushFollow(FOLLOW_bound_in_typeParameter293);
                    bound();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 9, typeParameter_StartIndex); }
        }
        return ;
    }
    // $ANTLR end typeParameter


    // $ANTLR start bound
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:165:1: bound : type ( '&' type )* ;
    public final void bound() throws RecognitionException {
        int bound_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 10) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:166:2: ( type ( '&' type )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:166:4: type ( '&' type )*
            {
            pushFollow(FOLLOW_type_in_bound308);
            type();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:166:9: ( '&' type )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==36) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:166:10: '&' type
            	    {
            	    match(input,36,FOLLOW_36_in_bound311); if (failed) return ;
            	    pushFollow(FOLLOW_type_in_bound313);
            	    type();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 10, bound_StartIndex); }
        }
        return ;
    }
    // $ANTLR end bound


    // $ANTLR start enumDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:169:1: enumDeclaration : ENUM Identifier ( 'implements' typeList )? enumBody ;
    public final void enumDeclaration() throws RecognitionException {
        int enumDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 11) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:170:2: ( ENUM Identifier ( 'implements' typeList )? enumBody )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:170:4: ENUM Identifier ( 'implements' typeList )? enumBody
            {
            match(input,ENUM,FOLLOW_ENUM_in_enumDeclaration326); if (failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_enumDeclaration328); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:170:20: ( 'implements' typeList )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==32) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:170:21: 'implements' typeList
                    {
                    match(input,32,FOLLOW_32_in_enumDeclaration331); if (failed) return ;
                    pushFollow(FOLLOW_typeList_in_enumDeclaration333);
                    typeList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            pushFollow(FOLLOW_enumBody_in_enumDeclaration337);
            enumBody();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 11, enumDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end enumDeclaration


    // $ANTLR start enumBody
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:173:1: enumBody : '{' ( enumConstants )? ( ',' )? ( enumBodyDeclarations )? '}' ;
    public final void enumBody() throws RecognitionException {
        int enumBody_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 12) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:174:2: ( '{' ( enumConstants )? ( ',' )? ( enumBodyDeclarations )? '}' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:174:4: '{' ( enumConstants )? ( ',' )? ( enumBodyDeclarations )? '}'
            {
            match(input,37,FOLLOW_37_in_enumBody349); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:174:8: ( enumConstants )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==Identifier||LA19_0==71) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: enumConstants
                    {
                    pushFollow(FOLLOW_enumConstants_in_enumBody351);
                    enumConstants();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:174:23: ( ',' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==34) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: ','
                    {
                    match(input,34,FOLLOW_34_in_enumBody354); if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:174:28: ( enumBodyDeclarations )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==25) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: enumBodyDeclarations
                    {
                    pushFollow(FOLLOW_enumBodyDeclarations_in_enumBody357);
                    enumBodyDeclarations();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            match(input,38,FOLLOW_38_in_enumBody360); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 12, enumBody_StartIndex); }
        }
        return ;
    }
    // $ANTLR end enumBody


    // $ANTLR start enumConstants
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:177:1: enumConstants : enumConstant ( ',' enumConstant )* ;
    public final void enumConstants() throws RecognitionException {
        int enumConstants_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 13) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:178:2: ( enumConstant ( ',' enumConstant )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:178:4: enumConstant ( ',' enumConstant )*
            {
            pushFollow(FOLLOW_enumConstant_in_enumConstants371);
            enumConstant();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:178:17: ( ',' enumConstant )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==34) ) {
                    int LA22_1 = input.LA(2);

                    if ( (LA22_1==Identifier||LA22_1==71) ) {
                        alt22=1;
                    }


                }


                switch (alt22) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:178:18: ',' enumConstant
            	    {
            	    match(input,34,FOLLOW_34_in_enumConstants374); if (failed) return ;
            	    pushFollow(FOLLOW_enumConstant_in_enumConstants376);
            	    enumConstant();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 13, enumConstants_StartIndex); }
        }
        return ;
    }
    // $ANTLR end enumConstants


    // $ANTLR start enumConstant
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:181:1: enumConstant : ( annotations )? Identifier ( arguments )? ( classBody )? ;
    public final void enumConstant() throws RecognitionException {
        int enumConstant_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 14) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:182:2: ( ( annotations )? Identifier ( arguments )? ( classBody )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:182:4: ( annotations )? Identifier ( arguments )? ( classBody )?
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:182:4: ( annotations )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==71) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: annotations
                    {
                    pushFollow(FOLLOW_annotations_in_enumConstant390);
                    annotations();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            match(input,Identifier,FOLLOW_Identifier_in_enumConstant393); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:182:28: ( arguments )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==65) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:182:29: arguments
                    {
                    pushFollow(FOLLOW_arguments_in_enumConstant396);
                    arguments();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:182:41: ( classBody )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==37) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:182:42: classBody
                    {
                    pushFollow(FOLLOW_classBody_in_enumConstant401);
                    classBody();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 14, enumConstant_StartIndex); }
        }
        return ;
    }
    // $ANTLR end enumConstant


    // $ANTLR start enumBodyDeclarations
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:185:1: enumBodyDeclarations : ';' ( classBodyDeclaration )* ;
    public final void enumBodyDeclarations() throws RecognitionException {
        int enumBodyDeclarations_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 15) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:186:2: ( ';' ( classBodyDeclaration )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:186:4: ';' ( classBodyDeclaration )*
            {
            match(input,25,FOLLOW_25_in_enumBodyDeclarations415); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:186:8: ( classBodyDeclaration )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>=Identifier && LA26_0<=ENUM)||LA26_0==25||LA26_0==27||LA26_0==30||LA26_0==33||LA26_0==37||(LA26_0>=39 && LA26_0<=40)||(LA26_0>=45 && LA26_0<=62)||LA26_0==71) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:186:9: classBodyDeclaration
            	    {
            	    pushFollow(FOLLOW_classBodyDeclaration_in_enumBodyDeclarations418);
            	    classBodyDeclaration();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 15, enumBodyDeclarations_StartIndex); }
        }
        return ;
    }
    // $ANTLR end enumBodyDeclarations


    // $ANTLR start interfaceDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:189:1: interfaceDeclaration : ( normalInterfaceDeclaration | annotationTypeDeclaration );
    public final void interfaceDeclaration() throws RecognitionException {
        int interfaceDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 16) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:190:2: ( normalInterfaceDeclaration | annotationTypeDeclaration )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==39) ) {
                alt27=1;
            }
            else if ( (LA27_0==71) ) {
                alt27=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("189:1: interfaceDeclaration : ( normalInterfaceDeclaration | annotationTypeDeclaration );", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:190:4: normalInterfaceDeclaration
                    {
                    pushFollow(FOLLOW_normalInterfaceDeclaration_in_interfaceDeclaration432);
                    normalInterfaceDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:191:5: annotationTypeDeclaration
                    {
                    pushFollow(FOLLOW_annotationTypeDeclaration_in_interfaceDeclaration438);
                    annotationTypeDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 16, interfaceDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end interfaceDeclaration


    // $ANTLR start normalInterfaceDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:194:1: normalInterfaceDeclaration : 'interface' Identifier ( typeParameters )? ( 'extends' typeList )? interfaceBody ;
    public final void normalInterfaceDeclaration() throws RecognitionException {
        int normalInterfaceDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 17) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:195:2: ( 'interface' Identifier ( typeParameters )? ( 'extends' typeList )? interfaceBody )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:195:4: 'interface' Identifier ( typeParameters )? ( 'extends' typeList )? interfaceBody
            {
            match(input,39,FOLLOW_39_in_normalInterfaceDeclaration450); if (failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_normalInterfaceDeclaration452); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:195:27: ( typeParameters )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==33) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: typeParameters
                    {
                    pushFollow(FOLLOW_typeParameters_in_normalInterfaceDeclaration454);
                    typeParameters();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:195:43: ( 'extends' typeList )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==31) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:195:44: 'extends' typeList
                    {
                    match(input,31,FOLLOW_31_in_normalInterfaceDeclaration458); if (failed) return ;
                    pushFollow(FOLLOW_typeList_in_normalInterfaceDeclaration460);
                    typeList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            pushFollow(FOLLOW_interfaceBody_in_normalInterfaceDeclaration464);
            interfaceBody();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 17, normalInterfaceDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end normalInterfaceDeclaration


    // $ANTLR start typeList
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:198:1: typeList : type ( ',' type )* ;
    public final void typeList() throws RecognitionException {
        int typeList_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 18) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:199:2: ( type ( ',' type )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:199:4: type ( ',' type )*
            {
            pushFollow(FOLLOW_type_in_typeList476);
            type();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:199:9: ( ',' type )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==34) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:199:10: ',' type
            	    {
            	    match(input,34,FOLLOW_34_in_typeList479); if (failed) return ;
            	    pushFollow(FOLLOW_type_in_typeList481);
            	    type();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 18, typeList_StartIndex); }
        }
        return ;
    }
    // $ANTLR end typeList


    // $ANTLR start classBody
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:202:1: classBody : '{' ( classBodyDeclaration )* '}' ;
    public final void classBody() throws RecognitionException {
        int classBody_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 19) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:203:2: ( '{' ( classBodyDeclaration )* '}' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:203:4: '{' ( classBodyDeclaration )* '}'
            {
            match(input,37,FOLLOW_37_in_classBody495); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:203:8: ( classBodyDeclaration )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( ((LA31_0>=Identifier && LA31_0<=ENUM)||LA31_0==25||LA31_0==27||LA31_0==30||LA31_0==33||LA31_0==37||(LA31_0>=39 && LA31_0<=40)||(LA31_0>=45 && LA31_0<=62)||LA31_0==71) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: classBodyDeclaration
            	    {
            	    pushFollow(FOLLOW_classBodyDeclaration_in_classBody497);
            	    classBodyDeclaration();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            match(input,38,FOLLOW_38_in_classBody500); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 19, classBody_StartIndex); }
        }
        return ;
    }
    // $ANTLR end classBody


    // $ANTLR start interfaceBody
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:206:1: interfaceBody : '{' ( interfaceBodyDeclaration )* '}' ;
    public final void interfaceBody() throws RecognitionException {
        int interfaceBody_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 20) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:207:2: ( '{' ( interfaceBodyDeclaration )* '}' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:207:4: '{' ( interfaceBodyDeclaration )* '}'
            {
            match(input,37,FOLLOW_37_in_interfaceBody512); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:207:8: ( interfaceBodyDeclaration )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( ((LA32_0>=Identifier && LA32_0<=ENUM)||LA32_0==25||LA32_0==27||LA32_0==30||LA32_0==33||(LA32_0>=39 && LA32_0<=40)||(LA32_0>=45 && LA32_0<=62)||LA32_0==71) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: interfaceBodyDeclaration
            	    {
            	    pushFollow(FOLLOW_interfaceBodyDeclaration_in_interfaceBody514);
            	    interfaceBodyDeclaration();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);

            match(input,38,FOLLOW_38_in_interfaceBody517); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 20, interfaceBody_StartIndex); }
        }
        return ;
    }
    // $ANTLR end interfaceBody


    // $ANTLR start classBodyDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:210:1: classBodyDeclaration : ( ';' | ( 'static' )? block | ( modifier )* memberDecl );
    public final void classBodyDeclaration() throws RecognitionException {
        int classBodyDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 21) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:211:2: ( ';' | ( 'static' )? block | ( modifier )* memberDecl )
            int alt35=3;
            switch ( input.LA(1) ) {
            case 25:
                {
                alt35=1;
                }
                break;
            case 27:
                {
                int LA35_2 = input.LA(2);

                if ( (LA35_2==37) ) {
                    alt35=2;
                }
                else if ( ((LA35_2>=Identifier && LA35_2<=ENUM)||LA35_2==27||LA35_2==30||LA35_2==33||(LA35_2>=39 && LA35_2<=40)||(LA35_2>=45 && LA35_2<=62)||LA35_2==71) ) {
                    alt35=3;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("210:1: classBodyDeclaration : ( ';' | ( 'static' )? block | ( modifier )* memberDecl );", 35, 2, input);

                    throw nvae;
                }
                }
                break;
            case 37:
                {
                alt35=2;
                }
                break;
            case Identifier:
            case ENUM:
            case 30:
            case 33:
            case 39:
            case 40:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 71:
                {
                alt35=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("210:1: classBodyDeclaration : ( ';' | ( 'static' )? block | ( modifier )* memberDecl );", 35, 0, input);

                throw nvae;
            }

            switch (alt35) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:211:4: ';'
                    {
                    match(input,25,FOLLOW_25_in_classBodyDeclaration528); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:212:4: ( 'static' )? block
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:212:4: ( 'static' )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==27) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: 'static'
                            {
                            match(input,27,FOLLOW_27_in_classBodyDeclaration533); if (failed) return ;

                            }
                            break;

                    }

                    pushFollow(FOLLOW_block_in_classBodyDeclaration536);
                    block();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:213:4: ( modifier )* memberDecl
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:213:4: ( modifier )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==71) ) {
                            int LA34_6 = input.LA(2);

                            if ( (LA34_6==Identifier) ) {
                                alt34=1;
                            }


                        }
                        else if ( (LA34_0==27||(LA34_0>=45 && LA34_0<=54)) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: modifier
                    	    {
                    	    pushFollow(FOLLOW_modifier_in_classBodyDeclaration541);
                    	    modifier();
                    	    _fsp--;
                    	    if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);

                    pushFollow(FOLLOW_memberDecl_in_classBodyDeclaration544);
                    memberDecl();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 21, classBodyDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end classBodyDeclaration


    // $ANTLR start memberDecl
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );
    public final void memberDecl() throws RecognitionException {
        int memberDecl_StartIndex = input.index();
        Token Identifier1=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 22) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:217:2: ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration )
            int alt36=7;
            switch ( input.LA(1) ) {
            case 33:
                {
                alt36=1;
                }
                break;
            case Identifier:
                {
                switch ( input.LA(2) ) {
                case 33:
                    {
                    int LA36_9 = input.LA(3);

                    if ( (synpred38()) ) {
                        alt36=2;
                    }
                    else if ( (synpred39()) ) {
                        alt36=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 9, input);

                        throw nvae;
                    }
                    }
                    break;
                case 28:
                    {
                    int LA36_10 = input.LA(3);

                    if ( (synpred38()) ) {
                        alt36=2;
                    }
                    else if ( (synpred39()) ) {
                        alt36=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 10, input);

                        throw nvae;
                    }
                    }
                    break;
                case 41:
                    {
                    int LA36_11 = input.LA(3);

                    if ( (synpred38()) ) {
                        alt36=2;
                    }
                    else if ( (synpred39()) ) {
                        alt36=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 11, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                    {
                    int LA36_12 = input.LA(3);

                    if ( (synpred38()) ) {
                        alt36=2;
                    }
                    else if ( (synpred39()) ) {
                        alt36=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 12, input);

                        throw nvae;
                    }
                    }
                    break;
                case 65:
                    {
                    alt36=5;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 2, input);

                    throw nvae;
                }

                }
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
                {
                int LA36_3 = input.LA(2);

                if ( (LA36_3==41) ) {
                    int LA36_14 = input.LA(3);

                    if ( (synpred38()) ) {
                        alt36=2;
                    }
                    else if ( (synpred39()) ) {
                        alt36=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 14, input);

                        throw nvae;
                    }
                }
                else if ( (LA36_3==Identifier) ) {
                    int LA36_15 = input.LA(3);

                    if ( (synpred38()) ) {
                        alt36=2;
                    }
                    else if ( (synpred39()) ) {
                        alt36=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 15, input);

                        throw nvae;
                    }
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 3, input);

                    throw nvae;
                }
                }
                break;
            case 40:
                {
                alt36=4;
                }
                break;
            case 39:
            case 71:
                {
                alt36=6;
                }
                break;
            case ENUM:
            case 30:
                {
                alt36=7;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("216:1: memberDecl : ( genericMethodOrConstructorDecl | methodDeclaration | fieldDeclaration | 'void' Identifier voidMethodDeclaratorRest | Identifier constructorDeclaratorRest | interfaceDeclaration | classDeclaration );", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:217:4: genericMethodOrConstructorDecl
                    {
                    pushFollow(FOLLOW_genericMethodOrConstructorDecl_in_memberDecl556);
                    genericMethodOrConstructorDecl();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:218:4: methodDeclaration
                    {
                    pushFollow(FOLLOW_methodDeclaration_in_memberDecl561);
                    methodDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:219:4: fieldDeclaration
                    {
                    pushFollow(FOLLOW_fieldDeclaration_in_memberDecl566);
                    fieldDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:220:4: 'void' Identifier voidMethodDeclaratorRest
                    {
                    match(input,40,FOLLOW_40_in_memberDecl571); if (failed) return ;
                    Identifier1=(Token)input.LT(1);
                    match(input,Identifier,FOLLOW_Identifier_in_memberDecl573); if (failed) return ;
                    pushFollow(FOLLOW_voidMethodDeclaratorRest_in_memberDecl575);
                    voidMethodDeclaratorRest();
                    _fsp--;
                    if (failed) return ;
                    if ( backtracking==0 ) {
                       addMethod(Identifier1.getText(), MethodType.METHOD_VOID);
                    }

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:221:4: Identifier constructorDeclaratorRest
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_memberDecl582); if (failed) return ;
                    pushFollow(FOLLOW_constructorDeclaratorRest_in_memberDecl584);
                    constructorDeclaratorRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 6 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:222:4: interfaceDeclaration
                    {
                    pushFollow(FOLLOW_interfaceDeclaration_in_memberDecl589);
                    interfaceDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 7 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:223:4: classDeclaration
                    {
                    pushFollow(FOLLOW_classDeclaration_in_memberDecl594);
                    classDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 22, memberDecl_StartIndex); }
        }
        return ;
    }
    // $ANTLR end memberDecl


    // $ANTLR start genericMethodOrConstructorDecl
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:226:1: genericMethodOrConstructorDecl : typeParameters genericMethodOrConstructorRest ;
    public final void genericMethodOrConstructorDecl() throws RecognitionException {
        int genericMethodOrConstructorDecl_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 23) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:227:2: ( typeParameters genericMethodOrConstructorRest )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:227:4: typeParameters genericMethodOrConstructorRest
            {
            pushFollow(FOLLOW_typeParameters_in_genericMethodOrConstructorDecl606);
            typeParameters();
            _fsp--;
            if (failed) return ;
            pushFollow(FOLLOW_genericMethodOrConstructorRest_in_genericMethodOrConstructorDecl608);
            genericMethodOrConstructorRest();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 23, genericMethodOrConstructorDecl_StartIndex); }
        }
        return ;
    }
    // $ANTLR end genericMethodOrConstructorDecl


    // $ANTLR start genericMethodOrConstructorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:230:1: genericMethodOrConstructorRest : ( ( type | 'void' ) Identifier methodDeclaratorRest | Identifier constructorDeclaratorRest );
    public final void genericMethodOrConstructorRest() throws RecognitionException {
        int genericMethodOrConstructorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 24) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:231:2: ( ( type | 'void' ) Identifier methodDeclaratorRest | Identifier constructorDeclaratorRest )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==Identifier) ) {
                int LA38_1 = input.LA(2);

                if ( (LA38_1==Identifier||LA38_1==28||LA38_1==33||LA38_1==41) ) {
                    alt38=1;
                }
                else if ( (LA38_1==65) ) {
                    alt38=2;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("230:1: genericMethodOrConstructorRest : ( ( type | 'void' ) Identifier methodDeclaratorRest | Identifier constructorDeclaratorRest );", 38, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA38_0==40||(LA38_0>=55 && LA38_0<=62)) ) {
                alt38=1;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("230:1: genericMethodOrConstructorRest : ( ( type | 'void' ) Identifier methodDeclaratorRest | Identifier constructorDeclaratorRest );", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:231:4: ( type | 'void' ) Identifier methodDeclaratorRest
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:231:4: ( type | 'void' )
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==Identifier||(LA37_0>=55 && LA37_0<=62)) ) {
                        alt37=1;
                    }
                    else if ( (LA37_0==40) ) {
                        alt37=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("231:4: ( type | 'void' )", 37, 0, input);

                        throw nvae;
                    }
                    switch (alt37) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:231:5: type
                            {
                            pushFollow(FOLLOW_type_in_genericMethodOrConstructorRest621);
                            type();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;
                        case 2 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:231:12: 'void'
                            {
                            match(input,40,FOLLOW_40_in_genericMethodOrConstructorRest625); if (failed) return ;

                            }
                            break;

                    }

                    match(input,Identifier,FOLLOW_Identifier_in_genericMethodOrConstructorRest628); if (failed) return ;
                    pushFollow(FOLLOW_methodDeclaratorRest_in_genericMethodOrConstructorRest630);
                    methodDeclaratorRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:232:4: Identifier constructorDeclaratorRest
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_genericMethodOrConstructorRest635); if (failed) return ;
                    pushFollow(FOLLOW_constructorDeclaratorRest_in_genericMethodOrConstructorRest637);
                    constructorDeclaratorRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 24, genericMethodOrConstructorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end genericMethodOrConstructorRest


    // $ANTLR start methodDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:235:1: methodDeclaration : type Identifier methodDeclaratorRest ;
    public final void methodDeclaration() throws RecognitionException {
        int methodDeclaration_StartIndex = input.index();
        Token Identifier2=null;

        try {
            if ( backtracking>0 && alreadyParsedRule(input, 25) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:236:2: ( type Identifier methodDeclaratorRest )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:236:4: type Identifier methodDeclaratorRest
            {
            pushFollow(FOLLOW_type_in_methodDeclaration648);
            type();
            _fsp--;
            if (failed) return ;
            Identifier2=(Token)input.LT(1);
            match(input,Identifier,FOLLOW_Identifier_in_methodDeclaration650); if (failed) return ;
            pushFollow(FOLLOW_methodDeclaratorRest_in_methodDeclaration652);
            methodDeclaratorRest();
            _fsp--;
            if (failed) return ;
            if ( backtracking==0 ) {
               addMethod(Identifier2.getText(), MethodType.METHOD);
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 25, methodDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end methodDeclaration


    // $ANTLR start fieldDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:239:1: fieldDeclaration : type variableDeclarators ';' ;
    public final void fieldDeclaration() throws RecognitionException {
        int fieldDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 26) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:240:2: ( type variableDeclarators ';' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:240:4: type variableDeclarators ';'
            {
            pushFollow(FOLLOW_type_in_fieldDeclaration665);
            type();
            _fsp--;
            if (failed) return ;
            pushFollow(FOLLOW_variableDeclarators_in_fieldDeclaration667);
            variableDeclarators();
            _fsp--;
            if (failed) return ;
            match(input,25,FOLLOW_25_in_fieldDeclaration669); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 26, fieldDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end fieldDeclaration


    // $ANTLR start interfaceBodyDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:243:1: interfaceBodyDeclaration : ( ( modifier )* interfaceMemberDecl | ';' );
    public final void interfaceBodyDeclaration() throws RecognitionException {
        int interfaceBodyDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 27) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:244:2: ( ( modifier )* interfaceMemberDecl | ';' )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( ((LA40_0>=Identifier && LA40_0<=ENUM)||LA40_0==27||LA40_0==30||LA40_0==33||(LA40_0>=39 && LA40_0<=40)||(LA40_0>=45 && LA40_0<=62)||LA40_0==71) ) {
                alt40=1;
            }
            else if ( (LA40_0==25) ) {
                alt40=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("243:1: interfaceBodyDeclaration : ( ( modifier )* interfaceMemberDecl | ';' );", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:244:4: ( modifier )* interfaceMemberDecl
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:244:4: ( modifier )*
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( (LA39_0==71) ) {
                            int LA39_6 = input.LA(2);

                            if ( (LA39_6==Identifier) ) {
                                alt39=1;
                            }


                        }
                        else if ( (LA39_0==27||(LA39_0>=45 && LA39_0<=54)) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: modifier
                    	    {
                    	    pushFollow(FOLLOW_modifier_in_interfaceBodyDeclaration682);
                    	    modifier();
                    	    _fsp--;
                    	    if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop39;
                        }
                    } while (true);

                    pushFollow(FOLLOW_interfaceMemberDecl_in_interfaceBodyDeclaration685);
                    interfaceMemberDecl();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:245:6: ';'
                    {
                    match(input,25,FOLLOW_25_in_interfaceBodyDeclaration692); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 27, interfaceBodyDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end interfaceBodyDeclaration


    // $ANTLR start interfaceMemberDecl
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:248:1: interfaceMemberDecl : ( interfaceMethodOrFieldDecl | interfaceGenericMethodDecl | 'void' Identifier voidInterfaceMethodDeclaratorRest | interfaceDeclaration | classDeclaration );
    public final void interfaceMemberDecl() throws RecognitionException {
        int interfaceMemberDecl_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 28) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:249:2: ( interfaceMethodOrFieldDecl | interfaceGenericMethodDecl | 'void' Identifier voidInterfaceMethodDeclaratorRest | interfaceDeclaration | classDeclaration )
            int alt41=5;
            switch ( input.LA(1) ) {
            case Identifier:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
                {
                alt41=1;
                }
                break;
            case 33:
                {
                alt41=2;
                }
                break;
            case 40:
                {
                alt41=3;
                }
                break;
            case 39:
            case 71:
                {
                alt41=4;
                }
                break;
            case ENUM:
            case 30:
                {
                alt41=5;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("248:1: interfaceMemberDecl : ( interfaceMethodOrFieldDecl | interfaceGenericMethodDecl | 'void' Identifier voidInterfaceMethodDeclaratorRest | interfaceDeclaration | classDeclaration );", 41, 0, input);

                throw nvae;
            }

            switch (alt41) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:249:4: interfaceMethodOrFieldDecl
                    {
                    pushFollow(FOLLOW_interfaceMethodOrFieldDecl_in_interfaceMemberDecl703);
                    interfaceMethodOrFieldDecl();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:250:4: interfaceGenericMethodDecl
                    {
                    pushFollow(FOLLOW_interfaceGenericMethodDecl_in_interfaceMemberDecl708);
                    interfaceGenericMethodDecl();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:251:4: 'void' Identifier voidInterfaceMethodDeclaratorRest
                    {
                    match(input,40,FOLLOW_40_in_interfaceMemberDecl713); if (failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_interfaceMemberDecl715); if (failed) return ;
                    pushFollow(FOLLOW_voidInterfaceMethodDeclaratorRest_in_interfaceMemberDecl717);
                    voidInterfaceMethodDeclaratorRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:252:4: interfaceDeclaration
                    {
                    pushFollow(FOLLOW_interfaceDeclaration_in_interfaceMemberDecl722);
                    interfaceDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:253:4: classDeclaration
                    {
                    pushFollow(FOLLOW_classDeclaration_in_interfaceMemberDecl727);
                    classDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 28, interfaceMemberDecl_StartIndex); }
        }
        return ;
    }
    // $ANTLR end interfaceMemberDecl


    // $ANTLR start interfaceMethodOrFieldDecl
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:256:1: interfaceMethodOrFieldDecl : type Identifier interfaceMethodOrFieldRest ;
    public final void interfaceMethodOrFieldDecl() throws RecognitionException {
        int interfaceMethodOrFieldDecl_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 29) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:257:2: ( type Identifier interfaceMethodOrFieldRest )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:257:4: type Identifier interfaceMethodOrFieldRest
            {
            pushFollow(FOLLOW_type_in_interfaceMethodOrFieldDecl739);
            type();
            _fsp--;
            if (failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_interfaceMethodOrFieldDecl741); if (failed) return ;
            pushFollow(FOLLOW_interfaceMethodOrFieldRest_in_interfaceMethodOrFieldDecl743);
            interfaceMethodOrFieldRest();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 29, interfaceMethodOrFieldDecl_StartIndex); }
        }
        return ;
    }
    // $ANTLR end interfaceMethodOrFieldDecl


    // $ANTLR start interfaceMethodOrFieldRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:260:1: interfaceMethodOrFieldRest : ( constantDeclaratorsRest ';' | interfaceMethodDeclaratorRest );
    public final void interfaceMethodOrFieldRest() throws RecognitionException {
        int interfaceMethodOrFieldRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 30) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:261:2: ( constantDeclaratorsRest ';' | interfaceMethodDeclaratorRest )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==41||LA42_0==44) ) {
                alt42=1;
            }
            else if ( (LA42_0==65) ) {
                alt42=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("260:1: interfaceMethodOrFieldRest : ( constantDeclaratorsRest ';' | interfaceMethodDeclaratorRest );", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:261:4: constantDeclaratorsRest ';'
                    {
                    pushFollow(FOLLOW_constantDeclaratorsRest_in_interfaceMethodOrFieldRest755);
                    constantDeclaratorsRest();
                    _fsp--;
                    if (failed) return ;
                    match(input,25,FOLLOW_25_in_interfaceMethodOrFieldRest757); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:262:4: interfaceMethodDeclaratorRest
                    {
                    pushFollow(FOLLOW_interfaceMethodDeclaratorRest_in_interfaceMethodOrFieldRest762);
                    interfaceMethodDeclaratorRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 30, interfaceMethodOrFieldRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end interfaceMethodOrFieldRest


    // $ANTLR start methodDeclaratorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:265:1: methodDeclaratorRest : formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ( methodBody | ';' ) ;
    public final void methodDeclaratorRest() throws RecognitionException {
        int methodDeclaratorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 31) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:266:2: ( formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ( methodBody | ';' ) )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:266:4: formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ( methodBody | ';' )
            {
            pushFollow(FOLLOW_formalParameters_in_methodDeclaratorRest774);
            formalParameters();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:266:21: ( '[' ']' )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==41) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:266:22: '[' ']'
            	    {
            	    match(input,41,FOLLOW_41_in_methodDeclaratorRest777); if (failed) return ;
            	    match(input,42,FOLLOW_42_in_methodDeclaratorRest779); if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:267:9: ( 'throws' qualifiedNameList )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==43) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:267:10: 'throws' qualifiedNameList
                    {
                    match(input,43,FOLLOW_43_in_methodDeclaratorRest792); if (failed) return ;
                    pushFollow(FOLLOW_qualifiedNameList_in_methodDeclaratorRest794);
                    qualifiedNameList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:268:9: ( methodBody | ';' )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==37) ) {
                alt45=1;
            }
            else if ( (LA45_0==25) ) {
                alt45=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("268:9: ( methodBody | ';' )", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:268:13: methodBody
                    {
                    pushFollow(FOLLOW_methodBody_in_methodDeclaratorRest810);
                    methodBody();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:269:13: ';'
                    {
                    match(input,25,FOLLOW_25_in_methodDeclaratorRest824); if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 31, methodDeclaratorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end methodDeclaratorRest


    // $ANTLR start voidMethodDeclaratorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:273:1: voidMethodDeclaratorRest : formalParameters ( 'throws' qualifiedNameList )? ( methodBody | ';' ) ;
    public final void voidMethodDeclaratorRest() throws RecognitionException {
        int voidMethodDeclaratorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 32) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:274:2: ( formalParameters ( 'throws' qualifiedNameList )? ( methodBody | ';' ) )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:274:4: formalParameters ( 'throws' qualifiedNameList )? ( methodBody | ';' )
            {
            pushFollow(FOLLOW_formalParameters_in_voidMethodDeclaratorRest846);
            formalParameters();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:274:21: ( 'throws' qualifiedNameList )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==43) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:274:22: 'throws' qualifiedNameList
                    {
                    match(input,43,FOLLOW_43_in_voidMethodDeclaratorRest849); if (failed) return ;
                    pushFollow(FOLLOW_qualifiedNameList_in_voidMethodDeclaratorRest851);
                    qualifiedNameList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:275:9: ( methodBody | ';' )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==37) ) {
                alt47=1;
            }
            else if ( (LA47_0==25) ) {
                alt47=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("275:9: ( methodBody | ';' )", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:275:13: methodBody
                    {
                    pushFollow(FOLLOW_methodBody_in_voidMethodDeclaratorRest867);
                    methodBody();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:276:13: ';'
                    {
                    match(input,25,FOLLOW_25_in_voidMethodDeclaratorRest881); if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 32, voidMethodDeclaratorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end voidMethodDeclaratorRest


    // $ANTLR start interfaceMethodDeclaratorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:280:1: interfaceMethodDeclaratorRest : formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ';' ;
    public final void interfaceMethodDeclaratorRest() throws RecognitionException {
        int interfaceMethodDeclaratorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 33) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:281:2: ( formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ';' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:281:4: formalParameters ( '[' ']' )* ( 'throws' qualifiedNameList )? ';'
            {
            pushFollow(FOLLOW_formalParameters_in_interfaceMethodDeclaratorRest903);
            formalParameters();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:281:21: ( '[' ']' )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==41) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:281:22: '[' ']'
            	    {
            	    match(input,41,FOLLOW_41_in_interfaceMethodDeclaratorRest906); if (failed) return ;
            	    match(input,42,FOLLOW_42_in_interfaceMethodDeclaratorRest908); if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:281:32: ( 'throws' qualifiedNameList )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==43) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:281:33: 'throws' qualifiedNameList
                    {
                    match(input,43,FOLLOW_43_in_interfaceMethodDeclaratorRest913); if (failed) return ;
                    pushFollow(FOLLOW_qualifiedNameList_in_interfaceMethodDeclaratorRest915);
                    qualifiedNameList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            match(input,25,FOLLOW_25_in_interfaceMethodDeclaratorRest919); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 33, interfaceMethodDeclaratorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end interfaceMethodDeclaratorRest


    // $ANTLR start interfaceGenericMethodDecl
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:284:1: interfaceGenericMethodDecl : typeParameters ( type | 'void' ) Identifier interfaceMethodDeclaratorRest ;
    public final void interfaceGenericMethodDecl() throws RecognitionException {
        int interfaceGenericMethodDecl_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 34) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:285:2: ( typeParameters ( type | 'void' ) Identifier interfaceMethodDeclaratorRest )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:285:4: typeParameters ( type | 'void' ) Identifier interfaceMethodDeclaratorRest
            {
            pushFollow(FOLLOW_typeParameters_in_interfaceGenericMethodDecl931);
            typeParameters();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:285:19: ( type | 'void' )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==Identifier||(LA50_0>=55 && LA50_0<=62)) ) {
                alt50=1;
            }
            else if ( (LA50_0==40) ) {
                alt50=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("285:19: ( type | 'void' )", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:285:20: type
                    {
                    pushFollow(FOLLOW_type_in_interfaceGenericMethodDecl934);
                    type();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:285:27: 'void'
                    {
                    match(input,40,FOLLOW_40_in_interfaceGenericMethodDecl938); if (failed) return ;

                    }
                    break;

            }

            match(input,Identifier,FOLLOW_Identifier_in_interfaceGenericMethodDecl941); if (failed) return ;
            pushFollow(FOLLOW_interfaceMethodDeclaratorRest_in_interfaceGenericMethodDecl951);
            interfaceMethodDeclaratorRest();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 34, interfaceGenericMethodDecl_StartIndex); }
        }
        return ;
    }
    // $ANTLR end interfaceGenericMethodDecl


    // $ANTLR start voidInterfaceMethodDeclaratorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:289:1: voidInterfaceMethodDeclaratorRest : formalParameters ( 'throws' qualifiedNameList )? ';' ;
    public final void voidInterfaceMethodDeclaratorRest() throws RecognitionException {
        int voidInterfaceMethodDeclaratorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 35) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:290:2: ( formalParameters ( 'throws' qualifiedNameList )? ';' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:290:4: formalParameters ( 'throws' qualifiedNameList )? ';'
            {
            pushFollow(FOLLOW_formalParameters_in_voidInterfaceMethodDeclaratorRest963);
            formalParameters();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:290:21: ( 'throws' qualifiedNameList )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==43) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:290:22: 'throws' qualifiedNameList
                    {
                    match(input,43,FOLLOW_43_in_voidInterfaceMethodDeclaratorRest966); if (failed) return ;
                    pushFollow(FOLLOW_qualifiedNameList_in_voidInterfaceMethodDeclaratorRest968);
                    qualifiedNameList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            match(input,25,FOLLOW_25_in_voidInterfaceMethodDeclaratorRest972); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 35, voidInterfaceMethodDeclaratorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end voidInterfaceMethodDeclaratorRest


    // $ANTLR start constructorDeclaratorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:293:1: constructorDeclaratorRest : formalParameters ( 'throws' qualifiedNameList )? methodBody ;
    public final void constructorDeclaratorRest() throws RecognitionException {
        int constructorDeclaratorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 36) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:294:2: ( formalParameters ( 'throws' qualifiedNameList )? methodBody )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:294:4: formalParameters ( 'throws' qualifiedNameList )? methodBody
            {
            pushFollow(FOLLOW_formalParameters_in_constructorDeclaratorRest984);
            formalParameters();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:294:21: ( 'throws' qualifiedNameList )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==43) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:294:22: 'throws' qualifiedNameList
                    {
                    match(input,43,FOLLOW_43_in_constructorDeclaratorRest987); if (failed) return ;
                    pushFollow(FOLLOW_qualifiedNameList_in_constructorDeclaratorRest989);
                    qualifiedNameList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            pushFollow(FOLLOW_methodBody_in_constructorDeclaratorRest993);
            methodBody();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 36, constructorDeclaratorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end constructorDeclaratorRest


    // $ANTLR start constantDeclarator
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:297:1: constantDeclarator : Identifier constantDeclaratorRest ;
    public final void constantDeclarator() throws RecognitionException {
        int constantDeclarator_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 37) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:298:2: ( Identifier constantDeclaratorRest )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:298:4: Identifier constantDeclaratorRest
            {
            match(input,Identifier,FOLLOW_Identifier_in_constantDeclarator1004); if (failed) return ;
            pushFollow(FOLLOW_constantDeclaratorRest_in_constantDeclarator1006);
            constantDeclaratorRest();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 37, constantDeclarator_StartIndex); }
        }
        return ;
    }
    // $ANTLR end constantDeclarator


    // $ANTLR start variableDeclarators
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:301:1: variableDeclarators : variableDeclarator ( ',' variableDeclarator )* ;
    public final void variableDeclarators() throws RecognitionException {
        int variableDeclarators_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 38) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:302:2: ( variableDeclarator ( ',' variableDeclarator )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:302:4: variableDeclarator ( ',' variableDeclarator )*
            {
            pushFollow(FOLLOW_variableDeclarator_in_variableDeclarators1018);
            variableDeclarator();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:302:23: ( ',' variableDeclarator )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==34) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:302:24: ',' variableDeclarator
            	    {
            	    match(input,34,FOLLOW_34_in_variableDeclarators1021); if (failed) return ;
            	    pushFollow(FOLLOW_variableDeclarator_in_variableDeclarators1023);
            	    variableDeclarator();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 38, variableDeclarators_StartIndex); }
        }
        return ;
    }
    // $ANTLR end variableDeclarators


    // $ANTLR start variableDeclarator
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:305:1: variableDeclarator : Identifier variableDeclaratorRest ;
    public final void variableDeclarator() throws RecognitionException {
        int variableDeclarator_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 39) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:306:2: ( Identifier variableDeclaratorRest )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:306:4: Identifier variableDeclaratorRest
            {
            match(input,Identifier,FOLLOW_Identifier_in_variableDeclarator1036); if (failed) return ;
            pushFollow(FOLLOW_variableDeclaratorRest_in_variableDeclarator1038);
            variableDeclaratorRest();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 39, variableDeclarator_StartIndex); }
        }
        return ;
    }
    // $ANTLR end variableDeclarator


    // $ANTLR start variableDeclaratorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:309:1: variableDeclaratorRest : ( ( '[' ']' )+ ( '=' variableInitializer )? | '=' variableInitializer | );
    public final void variableDeclaratorRest() throws RecognitionException {
        int variableDeclaratorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 40) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:310:2: ( ( '[' ']' )+ ( '=' variableInitializer )? | '=' variableInitializer | )
            int alt56=3;
            switch ( input.LA(1) ) {
            case 41:
                {
                alt56=1;
                }
                break;
            case 44:
                {
                alt56=2;
                }
                break;
            case EOF:
            case 25:
            case 34:
                {
                alt56=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("309:1: variableDeclaratorRest : ( ( '[' ']' )+ ( '=' variableInitializer )? | '=' variableInitializer | );", 56, 0, input);

                throw nvae;
            }

            switch (alt56) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:310:4: ( '[' ']' )+ ( '=' variableInitializer )?
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:310:4: ( '[' ']' )+
                    int cnt54=0;
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( (LA54_0==41) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:310:5: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_variableDeclaratorRest1051); if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_variableDeclaratorRest1053); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt54 >= 1 ) break loop54;
                    	    if (backtracking>0) {failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(54, input);
                                throw eee;
                        }
                        cnt54++;
                    } while (true);

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:310:15: ( '=' variableInitializer )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==44) ) {
                        alt55=1;
                    }
                    switch (alt55) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:310:16: '=' variableInitializer
                            {
                            match(input,44,FOLLOW_44_in_variableDeclaratorRest1058); if (failed) return ;
                            pushFollow(FOLLOW_variableInitializer_in_variableDeclaratorRest1060);
                            variableInitializer();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:311:4: '=' variableInitializer
                    {
                    match(input,44,FOLLOW_44_in_variableDeclaratorRest1067); if (failed) return ;
                    pushFollow(FOLLOW_variableInitializer_in_variableDeclaratorRest1069);
                    variableInitializer();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:313:2: 
                    {
                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 40, variableDeclaratorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end variableDeclaratorRest


    // $ANTLR start constantDeclaratorsRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:315:1: constantDeclaratorsRest : constantDeclaratorRest ( ',' constantDeclarator )* ;
    public final void constantDeclaratorsRest() throws RecognitionException {
        int constantDeclaratorsRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 41) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:316:5: ( constantDeclaratorRest ( ',' constantDeclarator )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:316:9: constantDeclaratorRest ( ',' constantDeclarator )*
            {
            pushFollow(FOLLOW_constantDeclaratorRest_in_constantDeclaratorsRest1089);
            constantDeclaratorRest();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:316:32: ( ',' constantDeclarator )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==34) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:316:33: ',' constantDeclarator
            	    {
            	    match(input,34,FOLLOW_34_in_constantDeclaratorsRest1092); if (failed) return ;
            	    pushFollow(FOLLOW_constantDeclarator_in_constantDeclaratorsRest1094);
            	    constantDeclarator();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 41, constantDeclaratorsRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end constantDeclaratorsRest


    // $ANTLR start constantDeclaratorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:319:1: constantDeclaratorRest : ( '[' ']' )* '=' variableInitializer ;
    public final void constantDeclaratorRest() throws RecognitionException {
        int constantDeclaratorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 42) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:320:2: ( ( '[' ']' )* '=' variableInitializer )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:320:4: ( '[' ']' )* '=' variableInitializer
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:320:4: ( '[' ']' )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==41) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:320:5: '[' ']'
            	    {
            	    match(input,41,FOLLOW_41_in_constantDeclaratorRest1111); if (failed) return ;
            	    match(input,42,FOLLOW_42_in_constantDeclaratorRest1113); if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);

            match(input,44,FOLLOW_44_in_constantDeclaratorRest1117); if (failed) return ;
            pushFollow(FOLLOW_variableInitializer_in_constantDeclaratorRest1119);
            variableInitializer();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 42, constantDeclaratorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end constantDeclaratorRest


    // $ANTLR start variableDeclaratorId
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:323:1: variableDeclaratorId : Identifier ( '[' ']' )* ;
    public final void variableDeclaratorId() throws RecognitionException {
        int variableDeclaratorId_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 43) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:324:2: ( Identifier ( '[' ']' )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:324:4: Identifier ( '[' ']' )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_variableDeclaratorId1131); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:324:15: ( '[' ']' )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==41) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:324:16: '[' ']'
            	    {
            	    match(input,41,FOLLOW_41_in_variableDeclaratorId1134); if (failed) return ;
            	    match(input,42,FOLLOW_42_in_variableDeclaratorId1136); if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 43, variableDeclaratorId_StartIndex); }
        }
        return ;
    }
    // $ANTLR end variableDeclaratorId


    // $ANTLR start variableInitializer
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:327:1: variableInitializer : ( arrayInitializer | expression );
    public final void variableInitializer() throws RecognitionException {
        int variableInitializer_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 44) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:328:2: ( arrayInitializer | expression )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==37) ) {
                alt60=1;
            }
            else if ( (LA60_0==Identifier||(LA60_0>=FloatingPointLiteral && LA60_0<=DecimalLiteral)||LA60_0==33||LA60_0==40||(LA60_0>=55 && LA60_0<=62)||(LA60_0>=64 && LA60_0<=65)||(LA60_0>=68 && LA60_0<=70)||(LA60_0>=104 && LA60_0<=105)||(LA60_0>=108 && LA60_0<=113)) ) {
                alt60=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("327:1: variableInitializer : ( arrayInitializer | expression );", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:328:4: arrayInitializer
                    {
                    pushFollow(FOLLOW_arrayInitializer_in_variableInitializer1149);
                    arrayInitializer();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:329:9: expression
                    {
                    pushFollow(FOLLOW_expression_in_variableInitializer1159);
                    expression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 44, variableInitializer_StartIndex); }
        }
        return ;
    }
    // $ANTLR end variableInitializer


    // $ANTLR start arrayInitializer
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:332:1: arrayInitializer : '{' ( variableInitializer ( ',' variableInitializer )* ( ',' )? )? '}' ;
    public final void arrayInitializer() throws RecognitionException {
        int arrayInitializer_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 45) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:333:2: ( '{' ( variableInitializer ( ',' variableInitializer )* ( ',' )? )? '}' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:333:4: '{' ( variableInitializer ( ',' variableInitializer )* ( ',' )? )? '}'
            {
            match(input,37,FOLLOW_37_in_arrayInitializer1171); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:333:8: ( variableInitializer ( ',' variableInitializer )* ( ',' )? )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==Identifier||(LA63_0>=FloatingPointLiteral && LA63_0<=DecimalLiteral)||LA63_0==33||LA63_0==37||LA63_0==40||(LA63_0>=55 && LA63_0<=62)||(LA63_0>=64 && LA63_0<=65)||(LA63_0>=68 && LA63_0<=70)||(LA63_0>=104 && LA63_0<=105)||(LA63_0>=108 && LA63_0<=113)) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:333:9: variableInitializer ( ',' variableInitializer )* ( ',' )?
                    {
                    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1174);
                    variableInitializer();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:333:29: ( ',' variableInitializer )*
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0==34) ) {
                            int LA61_1 = input.LA(2);

                            if ( (LA61_1==Identifier||(LA61_1>=FloatingPointLiteral && LA61_1<=DecimalLiteral)||LA61_1==33||LA61_1==37||LA61_1==40||(LA61_1>=55 && LA61_1<=62)||(LA61_1>=64 && LA61_1<=65)||(LA61_1>=68 && LA61_1<=70)||(LA61_1>=104 && LA61_1<=105)||(LA61_1>=108 && LA61_1<=113)) ) {
                                alt61=1;
                            }


                        }


                        switch (alt61) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:333:30: ',' variableInitializer
                    	    {
                    	    match(input,34,FOLLOW_34_in_arrayInitializer1177); if (failed) return ;
                    	    pushFollow(FOLLOW_variableInitializer_in_arrayInitializer1179);
                    	    variableInitializer();
                    	    _fsp--;
                    	    if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop61;
                        }
                    } while (true);

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:333:56: ( ',' )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==34) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:333:57: ','
                            {
                            match(input,34,FOLLOW_34_in_arrayInitializer1184); if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;

            }

            match(input,38,FOLLOW_38_in_arrayInitializer1191); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 45, arrayInitializer_StartIndex); }
        }
        return ;
    }
    // $ANTLR end arrayInitializer


    // $ANTLR start modifier
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:336:1: modifier : ( annotation | 'public' | 'protected' | 'private' | 'static' | 'abstract' | 'final' | 'native' | 'synchronized' | 'transient' | 'volatile' | 'strictfp' );
    public final void modifier() throws RecognitionException {
        int modifier_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 46) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:337:5: ( annotation | 'public' | 'protected' | 'private' | 'static' | 'abstract' | 'final' | 'native' | 'synchronized' | 'transient' | 'volatile' | 'strictfp' )
            int alt64=12;
            switch ( input.LA(1) ) {
            case 71:
                {
                alt64=1;
                }
                break;
            case 45:
                {
                alt64=2;
                }
                break;
            case 46:
                {
                alt64=3;
                }
                break;
            case 47:
                {
                alt64=4;
                }
                break;
            case 27:
                {
                alt64=5;
                }
                break;
            case 48:
                {
                alt64=6;
                }
                break;
            case 49:
                {
                alt64=7;
                }
                break;
            case 50:
                {
                alt64=8;
                }
                break;
            case 51:
                {
                alt64=9;
                }
                break;
            case 52:
                {
                alt64=10;
                }
                break;
            case 53:
                {
                alt64=11;
                }
                break;
            case 54:
                {
                alt64=12;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("336:1: modifier : ( annotation | 'public' | 'protected' | 'private' | 'static' | 'abstract' | 'final' | 'native' | 'synchronized' | 'transient' | 'volatile' | 'strictfp' );", 64, 0, input);

                throw nvae;
            }

            switch (alt64) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:337:9: annotation
                    {
                    pushFollow(FOLLOW_annotation_in_modifier1207);
                    annotation();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:338:9: 'public'
                    {
                    match(input,45,FOLLOW_45_in_modifier1217); if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:339:9: 'protected'
                    {
                    match(input,46,FOLLOW_46_in_modifier1227); if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:340:9: 'private'
                    {
                    match(input,47,FOLLOW_47_in_modifier1237); if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:341:9: 'static'
                    {
                    match(input,27,FOLLOW_27_in_modifier1247); if (failed) return ;

                    }
                    break;
                case 6 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:342:9: 'abstract'
                    {
                    match(input,48,FOLLOW_48_in_modifier1257); if (failed) return ;

                    }
                    break;
                case 7 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:343:9: 'final'
                    {
                    match(input,49,FOLLOW_49_in_modifier1267); if (failed) return ;

                    }
                    break;
                case 8 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:344:9: 'native'
                    {
                    match(input,50,FOLLOW_50_in_modifier1277); if (failed) return ;

                    }
                    break;
                case 9 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:345:9: 'synchronized'
                    {
                    match(input,51,FOLLOW_51_in_modifier1287); if (failed) return ;

                    }
                    break;
                case 10 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:346:9: 'transient'
                    {
                    match(input,52,FOLLOW_52_in_modifier1297); if (failed) return ;

                    }
                    break;
                case 11 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:347:9: 'volatile'
                    {
                    match(input,53,FOLLOW_53_in_modifier1307); if (failed) return ;

                    }
                    break;
                case 12 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:348:9: 'strictfp'
                    {
                    match(input,54,FOLLOW_54_in_modifier1317); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 46, modifier_StartIndex); }
        }
        return ;
    }
    // $ANTLR end modifier


    // $ANTLR start packageOrTypeName
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:351:1: packageOrTypeName : Identifier ( '.' Identifier )* ;
    public final void packageOrTypeName() throws RecognitionException {
        int packageOrTypeName_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 47) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:352:2: ( Identifier ( '.' Identifier )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:352:4: Identifier ( '.' Identifier )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_packageOrTypeName1331); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:352:15: ( '.' Identifier )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==28) ) {
                    int LA65_1 = input.LA(2);

                    if ( (LA65_1==Identifier) ) {
                        int LA65_2 = input.LA(3);

                        if ( (synpred85()) ) {
                            alt65=1;
                        }


                    }


                }


                switch (alt65) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:352:16: '.' Identifier
            	    {
            	    match(input,28,FOLLOW_28_in_packageOrTypeName1334); if (failed) return ;
            	    match(input,Identifier,FOLLOW_Identifier_in_packageOrTypeName1336); if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 47, packageOrTypeName_StartIndex); }
        }
        return ;
    }
    // $ANTLR end packageOrTypeName


    // $ANTLR start enumConstantName
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:355:1: enumConstantName : Identifier ;
    public final void enumConstantName() throws RecognitionException {
        int enumConstantName_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 48) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:356:5: ( Identifier )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:356:9: Identifier
            {
            match(input,Identifier,FOLLOW_Identifier_in_enumConstantName1354); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 48, enumConstantName_StartIndex); }
        }
        return ;
    }
    // $ANTLR end enumConstantName


    // $ANTLR start typeName
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:359:1: typeName : ( Identifier | packageOrTypeName '.' Identifier );
    public final void typeName() throws RecognitionException {
        int typeName_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 49) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:360:2: ( Identifier | packageOrTypeName '.' Identifier )
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==Identifier) ) {
                int LA66_1 = input.LA(2);

                if ( (LA66_1==EOF) ) {
                    alt66=1;
                }
                else if ( (LA66_1==28) ) {
                    alt66=2;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("359:1: typeName : ( Identifier | packageOrTypeName '.' Identifier );", 66, 1, input);

                    throw nvae;
                }
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("359:1: typeName : ( Identifier | packageOrTypeName '.' Identifier );", 66, 0, input);

                throw nvae;
            }
            switch (alt66) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:360:6: Identifier
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_typeName1370); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:361:9: packageOrTypeName '.' Identifier
                    {
                    pushFollow(FOLLOW_packageOrTypeName_in_typeName1380);
                    packageOrTypeName();
                    _fsp--;
                    if (failed) return ;
                    match(input,28,FOLLOW_28_in_typeName1382); if (failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_typeName1384); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 49, typeName_StartIndex); }
        }
        return ;
    }
    // $ANTLR end typeName


    // $ANTLR start type
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:364:1: type : ( Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* ( '[' ']' )* | primitiveType ( '[' ']' )* );
    public final void type() throws RecognitionException {
        int type_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 50) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:2: ( Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* ( '[' ']' )* | primitiveType ( '[' ']' )* )
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==Identifier) ) {
                alt72=1;
            }
            else if ( ((LA72_0>=55 && LA72_0<=62)) ) {
                alt72=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("364:1: type : ( Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* ( '[' ']' )* | primitiveType ( '[' ']' )* );", 72, 0, input);

                throw nvae;
            }
            switch (alt72) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:4: Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* ( '[' ']' )*
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_type1395); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:15: ( typeArguments )?
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==33) ) {
                        int LA67_1 = input.LA(2);

                        if ( (LA67_1==Identifier||(LA67_1>=55 && LA67_1<=63)) ) {
                            alt67=1;
                        }
                    }
                    switch (alt67) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:16: typeArguments
                            {
                            pushFollow(FOLLOW_typeArguments_in_type1398);
                            typeArguments();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:32: ( '.' Identifier ( typeArguments )? )*
                    loop69:
                    do {
                        int alt69=2;
                        int LA69_0 = input.LA(1);

                        if ( (LA69_0==28) ) {
                            alt69=1;
                        }


                        switch (alt69) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:33: '.' Identifier ( typeArguments )?
                    	    {
                    	    match(input,28,FOLLOW_28_in_type1403); if (failed) return ;
                    	    match(input,Identifier,FOLLOW_Identifier_in_type1405); if (failed) return ;
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:48: ( typeArguments )?
                    	    int alt68=2;
                    	    int LA68_0 = input.LA(1);

                    	    if ( (LA68_0==33) ) {
                    	        int LA68_1 = input.LA(2);

                    	        if ( (LA68_1==Identifier||(LA68_1>=55 && LA68_1<=63)) ) {
                    	            alt68=1;
                    	        }
                    	    }
                    	    switch (alt68) {
                    	        case 1 :
                    	            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:49: typeArguments
                    	            {
                    	            pushFollow(FOLLOW_typeArguments_in_type1408);
                    	            typeArguments();
                    	            _fsp--;
                    	            if (failed) return ;

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop69;
                        }
                    } while (true);

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:68: ( '[' ']' )*
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( (LA70_0==41) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:365:69: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_type1416); if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_type1418); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop70;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:366:4: primitiveType ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_primitiveType_in_type1425);
                    primitiveType();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:366:18: ( '[' ']' )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( (LA71_0==41) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:366:19: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_type1428); if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_type1430); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop71;
                        }
                    } while (true);


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 50, type_StartIndex); }
        }
        return ;
    }
    // $ANTLR end type


    // $ANTLR start primitiveType
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:369:1: primitiveType : ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' );
    public final void primitiveType() throws RecognitionException {
        int primitiveType_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 51) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:370:5: ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:
            {
            if ( (input.LA(1)>=55 && input.LA(1)<=62) ) {
                input.consume();
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_primitiveType0);    throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 51, primitiveType_StartIndex); }
        }
        return ;
    }
    // $ANTLR end primitiveType


    // $ANTLR start variableModifier
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:380:1: variableModifier : ( 'final' | annotation );
    public final void variableModifier() throws RecognitionException {
        int variableModifier_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 52) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:381:2: ( 'final' | annotation )
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==49) ) {
                alt73=1;
            }
            else if ( (LA73_0==71) ) {
                alt73=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("380:1: variableModifier : ( 'final' | annotation );", 73, 0, input);

                throw nvae;
            }
            switch (alt73) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:381:4: 'final'
                    {
                    match(input,49,FOLLOW_49_in_variableModifier1518); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:382:9: annotation
                    {
                    pushFollow(FOLLOW_annotation_in_variableModifier1528);
                    annotation();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 52, variableModifier_StartIndex); }
        }
        return ;
    }
    // $ANTLR end variableModifier


    // $ANTLR start typeArguments
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:385:1: typeArguments : '<' typeArgument ( ',' typeArgument )* '>' ;
    public final void typeArguments() throws RecognitionException {
        int typeArguments_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 53) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:386:2: ( '<' typeArgument ( ',' typeArgument )* '>' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:386:4: '<' typeArgument ( ',' typeArgument )* '>'
            {
            match(input,33,FOLLOW_33_in_typeArguments1539); if (failed) return ;
            pushFollow(FOLLOW_typeArgument_in_typeArguments1541);
            typeArgument();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:386:21: ( ',' typeArgument )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==34) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:386:22: ',' typeArgument
            	    {
            	    match(input,34,FOLLOW_34_in_typeArguments1544); if (failed) return ;
            	    pushFollow(FOLLOW_typeArgument_in_typeArguments1546);
            	    typeArgument();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop74;
                }
            } while (true);

            match(input,35,FOLLOW_35_in_typeArguments1550); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 53, typeArguments_StartIndex); }
        }
        return ;
    }
    // $ANTLR end typeArguments


    // $ANTLR start typeArgument
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:389:1: typeArgument : ( type | '?' ( ( 'extends' | 'super' ) type )? );
    public final void typeArgument() throws RecognitionException {
        int typeArgument_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 54) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:390:2: ( type | '?' ( ( 'extends' | 'super' ) type )? )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==Identifier||(LA76_0>=55 && LA76_0<=62)) ) {
                alt76=1;
            }
            else if ( (LA76_0==63) ) {
                alt76=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("389:1: typeArgument : ( type | '?' ( ( 'extends' | 'super' ) type )? );", 76, 0, input);

                throw nvae;
            }
            switch (alt76) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:390:4: type
                    {
                    pushFollow(FOLLOW_type_in_typeArgument1562);
                    type();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:391:4: '?' ( ( 'extends' | 'super' ) type )?
                    {
                    match(input,63,FOLLOW_63_in_typeArgument1567); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:391:8: ( ( 'extends' | 'super' ) type )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==31||LA75_0==64) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:391:9: ( 'extends' | 'super' ) type
                            {
                            if ( input.LA(1)==31||input.LA(1)==64 ) {
                                input.consume();
                                errorRecovery=false;failed=false;
                            }
                            else {
                                if (backtracking>0) {failed=true; return ;}
                                MismatchedSetException mse =
                                    new MismatchedSetException(null,input);
                                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_typeArgument1570);    throw mse;
                            }

                            pushFollow(FOLLOW_type_in_typeArgument1578);
                            type();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

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
            if ( backtracking>0 ) { memoize(input, 54, typeArgument_StartIndex); }
        }
        return ;
    }
    // $ANTLR end typeArgument


    // $ANTLR start qualifiedNameList
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:394:1: qualifiedNameList : qualifiedName ( ',' qualifiedName )* ;
    public final void qualifiedNameList() throws RecognitionException {
        int qualifiedNameList_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 55) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:395:2: ( qualifiedName ( ',' qualifiedName )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:395:4: qualifiedName ( ',' qualifiedName )*
            {
            pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList1592);
            qualifiedName();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:395:18: ( ',' qualifiedName )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==34) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:395:19: ',' qualifiedName
            	    {
            	    match(input,34,FOLLOW_34_in_qualifiedNameList1595); if (failed) return ;
            	    pushFollow(FOLLOW_qualifiedName_in_qualifiedNameList1597);
            	    qualifiedName();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop77;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 55, qualifiedNameList_StartIndex); }
        }
        return ;
    }
    // $ANTLR end qualifiedNameList


    // $ANTLR start formalParameters
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:398:1: formalParameters : '(' ( formalParameterDecls )? ')' ;
    public final void formalParameters() throws RecognitionException {
        int formalParameters_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 56) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:399:2: ( '(' ( formalParameterDecls )? ')' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:399:4: '(' ( formalParameterDecls )? ')'
            {
            match(input,65,FOLLOW_65_in_formalParameters1611); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:399:8: ( formalParameterDecls )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==Identifier||LA78_0==49||(LA78_0>=55 && LA78_0<=62)||LA78_0==71) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: formalParameterDecls
                    {
                    pushFollow(FOLLOW_formalParameterDecls_in_formalParameters1613);
                    formalParameterDecls();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            match(input,66,FOLLOW_66_in_formalParameters1616); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 56, formalParameters_StartIndex); }
        }
        return ;
    }
    // $ANTLR end formalParameters


    // $ANTLR start formalParameterDecls
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:402:1: formalParameterDecls : ( variableModifier )* type ( formalParameterDeclsRest )? ;
    public final void formalParameterDecls() throws RecognitionException {
        int formalParameterDecls_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 57) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:403:2: ( ( variableModifier )* type ( formalParameterDeclsRest )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:403:4: ( variableModifier )* type ( formalParameterDeclsRest )?
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:403:4: ( variableModifier )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==49||LA79_0==71) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: variableModifier
            	    {
            	    pushFollow(FOLLOW_variableModifier_in_formalParameterDecls1628);
            	    variableModifier();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop79;
                }
            } while (true);

            pushFollow(FOLLOW_type_in_formalParameterDecls1631);
            type();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:403:27: ( formalParameterDeclsRest )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==Identifier||LA80_0==67) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: formalParameterDeclsRest
                    {
                    pushFollow(FOLLOW_formalParameterDeclsRest_in_formalParameterDecls1633);
                    formalParameterDeclsRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 57, formalParameterDecls_StartIndex); }
        }
        return ;
    }
    // $ANTLR end formalParameterDecls


    // $ANTLR start formalParameterDeclsRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:406:1: formalParameterDeclsRest : ( variableDeclaratorId ( ',' formalParameterDecls )? | '...' variableDeclaratorId );
    public final void formalParameterDeclsRest() throws RecognitionException {
        int formalParameterDeclsRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 58) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:407:2: ( variableDeclaratorId ( ',' formalParameterDecls )? | '...' variableDeclaratorId )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==Identifier) ) {
                alt82=1;
            }
            else if ( (LA82_0==67) ) {
                alt82=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("406:1: formalParameterDeclsRest : ( variableDeclaratorId ( ',' formalParameterDecls )? | '...' variableDeclaratorId );", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:407:4: variableDeclaratorId ( ',' formalParameterDecls )?
                    {
                    pushFollow(FOLLOW_variableDeclaratorId_in_formalParameterDeclsRest1646);
                    variableDeclaratorId();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:407:25: ( ',' formalParameterDecls )?
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==34) ) {
                        alt81=1;
                    }
                    switch (alt81) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:407:26: ',' formalParameterDecls
                            {
                            match(input,34,FOLLOW_34_in_formalParameterDeclsRest1649); if (failed) return ;
                            pushFollow(FOLLOW_formalParameterDecls_in_formalParameterDeclsRest1651);
                            formalParameterDecls();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:408:6: '...' variableDeclaratorId
                    {
                    match(input,67,FOLLOW_67_in_formalParameterDeclsRest1660); if (failed) return ;
                    pushFollow(FOLLOW_variableDeclaratorId_in_formalParameterDeclsRest1662);
                    variableDeclaratorId();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 58, formalParameterDeclsRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end formalParameterDeclsRest


    // $ANTLR start methodBody
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:411:1: methodBody : block ;
    public final void methodBody() throws RecognitionException {
        int methodBody_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 59) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:412:2: ( block )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:412:4: block
            {
            pushFollow(FOLLOW_block_in_methodBody1674);
            block();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 59, methodBody_StartIndex); }
        }
        return ;
    }
    // $ANTLR end methodBody


    // $ANTLR start qualifiedName
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:415:1: qualifiedName : Identifier ( '.' Identifier )* ;
    public final void qualifiedName() throws RecognitionException {
        int qualifiedName_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 60) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:416:2: ( Identifier ( '.' Identifier )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:416:4: Identifier ( '.' Identifier )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_qualifiedName1685); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:416:15: ( '.' Identifier )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==28) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:416:16: '.' Identifier
            	    {
            	    match(input,28,FOLLOW_28_in_qualifiedName1688); if (failed) return ;
            	    match(input,Identifier,FOLLOW_Identifier_in_qualifiedName1690); if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 60, qualifiedName_StartIndex); }
        }
        return ;
    }
    // $ANTLR end qualifiedName


    // $ANTLR start literal
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:419:1: literal : ( integerLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | booleanLiteral | 'null' );
    public final void literal() throws RecognitionException {
        int literal_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 61) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:420:2: ( integerLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | booleanLiteral | 'null' )
            int alt84=6;
            switch ( input.LA(1) ) {
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
                {
                alt84=1;
                }
                break;
            case FloatingPointLiteral:
                {
                alt84=2;
                }
                break;
            case CharacterLiteral:
                {
                alt84=3;
                }
                break;
            case StringLiteral:
                {
                alt84=4;
                }
                break;
            case 69:
            case 70:
                {
                alt84=5;
                }
                break;
            case 68:
                {
                alt84=6;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("419:1: literal : ( integerLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | booleanLiteral | 'null' );", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:420:6: integerLiteral
                    {
                    pushFollow(FOLLOW_integerLiteral_in_literal1707);
                    integerLiteral();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:421:9: FloatingPointLiteral
                    {
                    match(input,FloatingPointLiteral,FOLLOW_FloatingPointLiteral_in_literal1717); if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:422:9: CharacterLiteral
                    {
                    match(input,CharacterLiteral,FOLLOW_CharacterLiteral_in_literal1727); if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:423:9: StringLiteral
                    {
                    match(input,StringLiteral,FOLLOW_StringLiteral_in_literal1737); if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:424:9: booleanLiteral
                    {
                    pushFollow(FOLLOW_booleanLiteral_in_literal1747);
                    booleanLiteral();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 6 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:425:9: 'null'
                    {
                    match(input,68,FOLLOW_68_in_literal1757); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 61, literal_StartIndex); }
        }
        return ;
    }
    // $ANTLR end literal


    // $ANTLR start integerLiteral
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:428:1: integerLiteral : ( HexLiteral | OctalLiteral | DecimalLiteral );
    public final void integerLiteral() throws RecognitionException {
        int integerLiteral_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 62) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:429:5: ( HexLiteral | OctalLiteral | DecimalLiteral )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:
            {
            if ( (input.LA(1)>=HexLiteral && input.LA(1)<=DecimalLiteral) ) {
                input.consume();
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_integerLiteral0);    throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 62, integerLiteral_StartIndex); }
        }
        return ;
    }
    // $ANTLR end integerLiteral


    // $ANTLR start booleanLiteral
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:434:1: booleanLiteral : ( 'true' | 'false' );
    public final void booleanLiteral() throws RecognitionException {
        int booleanLiteral_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 63) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:435:5: ( 'true' | 'false' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:
            {
            if ( (input.LA(1)>=69 && input.LA(1)<=70) ) {
                input.consume();
                errorRecovery=false;failed=false;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_booleanLiteral0);    throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 63, booleanLiteral_StartIndex); }
        }
        return ;
    }
    // $ANTLR end booleanLiteral


    // $ANTLR start annotations
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:441:1: annotations : ( annotation )+ ;
    public final void annotations() throws RecognitionException {
        int annotations_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 64) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:442:2: ( ( annotation )+ )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:442:4: ( annotation )+
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:442:4: ( annotation )+
            int cnt85=0;
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==71) ) {
                    int LA85_3 = input.LA(2);

                    if ( (LA85_3==Identifier) ) {
                        int LA85_22 = input.LA(3);

                        if ( (synpred120()) ) {
                            alt85=1;
                        }


                    }


                }


                switch (alt85) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: annotation
            	    {
            	    pushFollow(FOLLOW_annotation_in_annotations1838);
            	    annotation();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt85 >= 1 ) break loop85;
            	    if (backtracking>0) {failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(85, input);
                        throw eee;
                }
                cnt85++;
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 64, annotations_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotations


    // $ANTLR start annotation
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:445:1: annotation : '@' annotationName ( '(' ( elementValuePairs )? ')' )? ;
    public final void annotation() throws RecognitionException {
        int annotation_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 65) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:446:2: ( '@' annotationName ( '(' ( elementValuePairs )? ')' )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:446:4: '@' annotationName ( '(' ( elementValuePairs )? ')' )?
            {
            match(input,71,FOLLOW_71_in_annotation1850); if (failed) return ;
            pushFollow(FOLLOW_annotationName_in_annotation1852);
            annotationName();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:446:23: ( '(' ( elementValuePairs )? ')' )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==65) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:446:24: '(' ( elementValuePairs )? ')'
                    {
                    match(input,65,FOLLOW_65_in_annotation1855); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:446:28: ( elementValuePairs )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==Identifier||(LA86_0>=FloatingPointLiteral && LA86_0<=DecimalLiteral)||LA86_0==33||LA86_0==37||LA86_0==40||(LA86_0>=55 && LA86_0<=62)||(LA86_0>=64 && LA86_0<=65)||(LA86_0>=68 && LA86_0<=71)||(LA86_0>=104 && LA86_0<=105)||(LA86_0>=108 && LA86_0<=113)) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: elementValuePairs
                            {
                            pushFollow(FOLLOW_elementValuePairs_in_annotation1857);
                            elementValuePairs();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    match(input,66,FOLLOW_66_in_annotation1860); if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 65, annotation_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotation


    // $ANTLR start annotationName
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:449:1: annotationName : Identifier ( '.' Identifier )* ;
    public final void annotationName() throws RecognitionException {
        int annotationName_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 66) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:450:2: ( Identifier ( '.' Identifier )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:450:4: Identifier ( '.' Identifier )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_annotationName1874); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:450:15: ( '.' Identifier )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==28) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:450:16: '.' Identifier
            	    {
            	    match(input,28,FOLLOW_28_in_annotationName1877); if (failed) return ;
            	    match(input,Identifier,FOLLOW_Identifier_in_annotationName1879); if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 66, annotationName_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationName


    // $ANTLR start elementValuePairs
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:453:1: elementValuePairs : elementValuePair ( ',' elementValuePair )* ;
    public final void elementValuePairs() throws RecognitionException {
        int elementValuePairs_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 67) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:454:2: ( elementValuePair ( ',' elementValuePair )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:454:4: elementValuePair ( ',' elementValuePair )*
            {
            pushFollow(FOLLOW_elementValuePair_in_elementValuePairs1893);
            elementValuePair();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:454:21: ( ',' elementValuePair )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==34) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:454:22: ',' elementValuePair
            	    {
            	    match(input,34,FOLLOW_34_in_elementValuePairs1896); if (failed) return ;
            	    pushFollow(FOLLOW_elementValuePair_in_elementValuePairs1898);
            	    elementValuePair();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 67, elementValuePairs_StartIndex); }
        }
        return ;
    }
    // $ANTLR end elementValuePairs


    // $ANTLR start elementValuePair
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:457:1: elementValuePair : ( Identifier '=' )? elementValue ;
    public final void elementValuePair() throws RecognitionException {
        int elementValuePair_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 68) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:458:2: ( ( Identifier '=' )? elementValue )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:458:4: ( Identifier '=' )? elementValue
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:458:4: ( Identifier '=' )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==Identifier) ) {
                int LA90_1 = input.LA(2);

                if ( (LA90_1==44) ) {
                    alt90=1;
                }
            }
            switch (alt90) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:458:5: Identifier '='
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_elementValuePair1913); if (failed) return ;
                    match(input,44,FOLLOW_44_in_elementValuePair1915); if (failed) return ;

                    }
                    break;

            }

            pushFollow(FOLLOW_elementValue_in_elementValuePair1919);
            elementValue();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 68, elementValuePair_StartIndex); }
        }
        return ;
    }
    // $ANTLR end elementValuePair


    // $ANTLR start elementValue
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:461:1: elementValue : ( conditionalExpression | annotation | elementValueArrayInitializer );
    public final void elementValue() throws RecognitionException {
        int elementValue_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 69) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:462:2: ( conditionalExpression | annotation | elementValueArrayInitializer )
            int alt91=3;
            switch ( input.LA(1) ) {
            case Identifier:
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 33:
            case 40:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 64:
            case 65:
            case 68:
            case 69:
            case 70:
            case 104:
            case 105:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
                {
                alt91=1;
                }
                break;
            case 71:
                {
                alt91=2;
                }
                break;
            case 37:
                {
                alt91=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("461:1: elementValue : ( conditionalExpression | annotation | elementValueArrayInitializer );", 91, 0, input);

                throw nvae;
            }

            switch (alt91) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:462:4: conditionalExpression
                    {
                    pushFollow(FOLLOW_conditionalExpression_in_elementValue1931);
                    conditionalExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:463:6: annotation
                    {
                    pushFollow(FOLLOW_annotation_in_elementValue1938);
                    annotation();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:464:6: elementValueArrayInitializer
                    {
                    pushFollow(FOLLOW_elementValueArrayInitializer_in_elementValue1945);
                    elementValueArrayInitializer();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 69, elementValue_StartIndex); }
        }
        return ;
    }
    // $ANTLR end elementValue


    // $ANTLR start elementValueArrayInitializer
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:467:1: elementValueArrayInitializer : '{' ( elementValue ( ',' elementValue )* )? '}' ;
    public final void elementValueArrayInitializer() throws RecognitionException {
        int elementValueArrayInitializer_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 70) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:468:2: ( '{' ( elementValue ( ',' elementValue )* )? '}' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:468:4: '{' ( elementValue ( ',' elementValue )* )? '}'
            {
            match(input,37,FOLLOW_37_in_elementValueArrayInitializer1957); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:468:8: ( elementValue ( ',' elementValue )* )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==Identifier||(LA93_0>=FloatingPointLiteral && LA93_0<=DecimalLiteral)||LA93_0==33||LA93_0==37||LA93_0==40||(LA93_0>=55 && LA93_0<=62)||(LA93_0>=64 && LA93_0<=65)||(LA93_0>=68 && LA93_0<=71)||(LA93_0>=104 && LA93_0<=105)||(LA93_0>=108 && LA93_0<=113)) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:468:9: elementValue ( ',' elementValue )*
                    {
                    pushFollow(FOLLOW_elementValue_in_elementValueArrayInitializer1960);
                    elementValue();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:468:22: ( ',' elementValue )*
                    loop92:
                    do {
                        int alt92=2;
                        int LA92_0 = input.LA(1);

                        if ( (LA92_0==34) ) {
                            alt92=1;
                        }


                        switch (alt92) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:468:23: ',' elementValue
                    	    {
                    	    match(input,34,FOLLOW_34_in_elementValueArrayInitializer1963); if (failed) return ;
                    	    pushFollow(FOLLOW_elementValue_in_elementValueArrayInitializer1965);
                    	    elementValue();
                    	    _fsp--;
                    	    if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop92;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,38,FOLLOW_38_in_elementValueArrayInitializer1972); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 70, elementValueArrayInitializer_StartIndex); }
        }
        return ;
    }
    // $ANTLR end elementValueArrayInitializer


    // $ANTLR start annotationTypeDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:471:1: annotationTypeDeclaration : '@' 'interface' Identifier annotationTypeBody ;
    public final void annotationTypeDeclaration() throws RecognitionException {
        int annotationTypeDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 71) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:472:2: ( '@' 'interface' Identifier annotationTypeBody )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:472:4: '@' 'interface' Identifier annotationTypeBody
            {
            match(input,71,FOLLOW_71_in_annotationTypeDeclaration1984); if (failed) return ;
            match(input,39,FOLLOW_39_in_annotationTypeDeclaration1986); if (failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_annotationTypeDeclaration1988); if (failed) return ;
            pushFollow(FOLLOW_annotationTypeBody_in_annotationTypeDeclaration1990);
            annotationTypeBody();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 71, annotationTypeDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationTypeDeclaration


    // $ANTLR start annotationTypeBody
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:475:1: annotationTypeBody : '{' ( annotationTypeElementDeclarations )? '}' ;
    public final void annotationTypeBody() throws RecognitionException {
        int annotationTypeBody_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 72) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:476:2: ( '{' ( annotationTypeElementDeclarations )? '}' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:476:4: '{' ( annotationTypeElementDeclarations )? '}'
            {
            match(input,37,FOLLOW_37_in_annotationTypeBody2002); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:476:8: ( annotationTypeElementDeclarations )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( ((LA94_0>=Identifier && LA94_0<=ENUM)||LA94_0==27||LA94_0==30||LA94_0==39||(LA94_0>=45 && LA94_0<=62)||LA94_0==71) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:476:9: annotationTypeElementDeclarations
                    {
                    pushFollow(FOLLOW_annotationTypeElementDeclarations_in_annotationTypeBody2005);
                    annotationTypeElementDeclarations();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            match(input,38,FOLLOW_38_in_annotationTypeBody2009); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 72, annotationTypeBody_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationTypeBody


    // $ANTLR start annotationTypeElementDeclarations
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:479:1: annotationTypeElementDeclarations : ( annotationTypeElementDeclaration ) ( annotationTypeElementDeclaration )* ;
    public final void annotationTypeElementDeclarations() throws RecognitionException {
        int annotationTypeElementDeclarations_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 73) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:480:2: ( ( annotationTypeElementDeclaration ) ( annotationTypeElementDeclaration )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:480:4: ( annotationTypeElementDeclaration ) ( annotationTypeElementDeclaration )*
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:480:4: ( annotationTypeElementDeclaration )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:480:5: annotationTypeElementDeclaration
            {
            pushFollow(FOLLOW_annotationTypeElementDeclaration_in_annotationTypeElementDeclarations2022);
            annotationTypeElementDeclaration();
            _fsp--;
            if (failed) return ;

            }

            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:480:39: ( annotationTypeElementDeclaration )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( ((LA95_0>=Identifier && LA95_0<=ENUM)||LA95_0==27||LA95_0==30||LA95_0==39||(LA95_0>=45 && LA95_0<=62)||LA95_0==71) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:480:40: annotationTypeElementDeclaration
            	    {
            	    pushFollow(FOLLOW_annotationTypeElementDeclaration_in_annotationTypeElementDeclarations2026);
            	    annotationTypeElementDeclaration();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop95;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 73, annotationTypeElementDeclarations_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationTypeElementDeclarations


    // $ANTLR start annotationTypeElementDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:483:1: annotationTypeElementDeclaration : ( modifier )* annotationTypeElementRest ;
    public final void annotationTypeElementDeclaration() throws RecognitionException {
        int annotationTypeElementDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 74) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:484:2: ( ( modifier )* annotationTypeElementRest )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:484:4: ( modifier )* annotationTypeElementRest
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:484:4: ( modifier )*
            loop96:
            do {
                int alt96=2;
                int LA96_0 = input.LA(1);

                if ( (LA96_0==71) ) {
                    int LA96_6 = input.LA(2);

                    if ( (LA96_6==Identifier) ) {
                        alt96=1;
                    }


                }
                else if ( (LA96_0==27||(LA96_0>=45 && LA96_0<=54)) ) {
                    alt96=1;
                }


                switch (alt96) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:484:5: modifier
            	    {
            	    pushFollow(FOLLOW_modifier_in_annotationTypeElementDeclaration2041);
            	    modifier();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop96;
                }
            } while (true);

            pushFollow(FOLLOW_annotationTypeElementRest_in_annotationTypeElementDeclaration2045);
            annotationTypeElementRest();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 74, annotationTypeElementDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationTypeElementDeclaration


    // $ANTLR start annotationTypeElementRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:487:1: annotationTypeElementRest : ( type annotationMethodOrConstantRest ';' | classDeclaration ( ';' )? | interfaceDeclaration ( ';' )? | enumDeclaration ( ';' )? | annotationTypeDeclaration ( ';' )? );
    public final void annotationTypeElementRest() throws RecognitionException {
        int annotationTypeElementRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 75) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:488:2: ( type annotationMethodOrConstantRest ';' | classDeclaration ( ';' )? | interfaceDeclaration ( ';' )? | enumDeclaration ( ';' )? | annotationTypeDeclaration ( ';' )? )
            int alt101=5;
            switch ( input.LA(1) ) {
            case Identifier:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
                {
                alt101=1;
                }
                break;
            case 30:
                {
                alt101=2;
                }
                break;
            case ENUM:
                {
                int LA101_4 = input.LA(2);

                if ( (LA101_4==Identifier) ) {
                    int LA101_7 = input.LA(3);

                    if ( (synpred135()) ) {
                        alt101=2;
                    }
                    else if ( (synpred139()) ) {
                        alt101=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("487:1: annotationTypeElementRest : ( type annotationMethodOrConstantRest ';' | classDeclaration ( ';' )? | interfaceDeclaration ( ';' )? | enumDeclaration ( ';' )? | annotationTypeDeclaration ( ';' )? );", 101, 7, input);

                        throw nvae;
                    }
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("487:1: annotationTypeElementRest : ( type annotationMethodOrConstantRest ';' | classDeclaration ( ';' )? | interfaceDeclaration ( ';' )? | enumDeclaration ( ';' )? | annotationTypeDeclaration ( ';' )? );", 101, 4, input);

                    throw nvae;
                }
                }
                break;
            case 39:
                {
                alt101=3;
                }
                break;
            case 71:
                {
                int LA101_6 = input.LA(2);

                if ( (LA101_6==39) ) {
                    int LA101_8 = input.LA(3);

                    if ( (synpred137()) ) {
                        alt101=3;
                    }
                    else if ( (true) ) {
                        alt101=5;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("487:1: annotationTypeElementRest : ( type annotationMethodOrConstantRest ';' | classDeclaration ( ';' )? | interfaceDeclaration ( ';' )? | enumDeclaration ( ';' )? | annotationTypeDeclaration ( ';' )? );", 101, 8, input);

                        throw nvae;
                    }
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("487:1: annotationTypeElementRest : ( type annotationMethodOrConstantRest ';' | classDeclaration ( ';' )? | interfaceDeclaration ( ';' )? | enumDeclaration ( ';' )? | annotationTypeDeclaration ( ';' )? );", 101, 6, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("487:1: annotationTypeElementRest : ( type annotationMethodOrConstantRest ';' | classDeclaration ( ';' )? | interfaceDeclaration ( ';' )? | enumDeclaration ( ';' )? | annotationTypeDeclaration ( ';' )? );", 101, 0, input);

                throw nvae;
            }

            switch (alt101) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:488:4: type annotationMethodOrConstantRest ';'
                    {
                    pushFollow(FOLLOW_type_in_annotationTypeElementRest2057);
                    type();
                    _fsp--;
                    if (failed) return ;
                    pushFollow(FOLLOW_annotationMethodOrConstantRest_in_annotationTypeElementRest2059);
                    annotationMethodOrConstantRest();
                    _fsp--;
                    if (failed) return ;
                    match(input,25,FOLLOW_25_in_annotationTypeElementRest2061); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:489:6: classDeclaration ( ';' )?
                    {
                    pushFollow(FOLLOW_classDeclaration_in_annotationTypeElementRest2068);
                    classDeclaration();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:489:23: ( ';' )?
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==25) ) {
                        alt97=1;
                    }
                    switch (alt97) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: ';'
                            {
                            match(input,25,FOLLOW_25_in_annotationTypeElementRest2070); if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:490:6: interfaceDeclaration ( ';' )?
                    {
                    pushFollow(FOLLOW_interfaceDeclaration_in_annotationTypeElementRest2078);
                    interfaceDeclaration();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:490:27: ( ';' )?
                    int alt98=2;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==25) ) {
                        alt98=1;
                    }
                    switch (alt98) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: ';'
                            {
                            match(input,25,FOLLOW_25_in_annotationTypeElementRest2080); if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:491:6: enumDeclaration ( ';' )?
                    {
                    pushFollow(FOLLOW_enumDeclaration_in_annotationTypeElementRest2088);
                    enumDeclaration();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:491:22: ( ';' )?
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==25) ) {
                        alt99=1;
                    }
                    switch (alt99) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: ';'
                            {
                            match(input,25,FOLLOW_25_in_annotationTypeElementRest2090); if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:492:6: annotationTypeDeclaration ( ';' )?
                    {
                    pushFollow(FOLLOW_annotationTypeDeclaration_in_annotationTypeElementRest2098);
                    annotationTypeDeclaration();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:492:32: ( ';' )?
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==25) ) {
                        alt100=1;
                    }
                    switch (alt100) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: ';'
                            {
                            match(input,25,FOLLOW_25_in_annotationTypeElementRest2100); if (failed) return ;

                            }
                            break;

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
            if ( backtracking>0 ) { memoize(input, 75, annotationTypeElementRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationTypeElementRest


    // $ANTLR start annotationMethodOrConstantRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:495:1: annotationMethodOrConstantRest : ( annotationMethodRest | annotationConstantRest );
    public final void annotationMethodOrConstantRest() throws RecognitionException {
        int annotationMethodOrConstantRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 76) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:496:2: ( annotationMethodRest | annotationConstantRest )
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==Identifier) ) {
                int LA102_1 = input.LA(2);

                if ( (LA102_1==65) ) {
                    alt102=1;
                }
                else if ( (LA102_1==25||LA102_1==34||LA102_1==41||LA102_1==44) ) {
                    alt102=2;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("495:1: annotationMethodOrConstantRest : ( annotationMethodRest | annotationConstantRest );", 102, 1, input);

                    throw nvae;
                }
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("495:1: annotationMethodOrConstantRest : ( annotationMethodRest | annotationConstantRest );", 102, 0, input);

                throw nvae;
            }
            switch (alt102) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:496:4: annotationMethodRest
                    {
                    pushFollow(FOLLOW_annotationMethodRest_in_annotationMethodOrConstantRest2113);
                    annotationMethodRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:497:6: annotationConstantRest
                    {
                    pushFollow(FOLLOW_annotationConstantRest_in_annotationMethodOrConstantRest2120);
                    annotationConstantRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 76, annotationMethodOrConstantRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationMethodOrConstantRest


    // $ANTLR start annotationMethodRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:500:1: annotationMethodRest : Identifier '(' ')' ( defaultValue )? ;
    public final void annotationMethodRest() throws RecognitionException {
        int annotationMethodRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 77) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:501:3: ( Identifier '(' ')' ( defaultValue )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:501:5: Identifier '(' ')' ( defaultValue )?
            {
            match(input,Identifier,FOLLOW_Identifier_in_annotationMethodRest2133); if (failed) return ;
            match(input,65,FOLLOW_65_in_annotationMethodRest2135); if (failed) return ;
            match(input,66,FOLLOW_66_in_annotationMethodRest2137); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:501:24: ( defaultValue )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==72) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:501:25: defaultValue
                    {
                    pushFollow(FOLLOW_defaultValue_in_annotationMethodRest2140);
                    defaultValue();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 77, annotationMethodRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationMethodRest


    // $ANTLR start annotationConstantRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:504:1: annotationConstantRest : variableDeclarators ;
    public final void annotationConstantRest() throws RecognitionException {
        int annotationConstantRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 78) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:505:3: ( variableDeclarators )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:505:5: variableDeclarators
            {
            pushFollow(FOLLOW_variableDeclarators_in_annotationConstantRest2157);
            variableDeclarators();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 78, annotationConstantRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end annotationConstantRest


    // $ANTLR start defaultValue
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:508:1: defaultValue : 'default' elementValue ;
    public final void defaultValue() throws RecognitionException {
        int defaultValue_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 79) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:509:3: ( 'default' elementValue )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:509:5: 'default' elementValue
            {
            match(input,72,FOLLOW_72_in_defaultValue2172); if (failed) return ;
            pushFollow(FOLLOW_elementValue_in_defaultValue2174);
            elementValue();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 79, defaultValue_StartIndex); }
        }
        return ;
    }
    // $ANTLR end defaultValue


    // $ANTLR start block
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:514:1: block : '{' ( blockStatement )* '}' ;
    public final void block() throws RecognitionException {
        int block_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 80) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:515:2: ( '{' ( blockStatement )* '}' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:515:4: '{' ( blockStatement )* '}'
            {
            match(input,37,FOLLOW_37_in_block2188); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:515:8: ( blockStatement )*
            loop104:
            do {
                int alt104=2;
                int LA104_0 = input.LA(1);

                if ( ((LA104_0>=Identifier && LA104_0<=DecimalLiteral)||LA104_0==25||LA104_0==27||LA104_0==30||LA104_0==33||LA104_0==37||(LA104_0>=39 && LA104_0<=40)||(LA104_0>=45 && LA104_0<=62)||(LA104_0>=64 && LA104_0<=65)||(LA104_0>=68 && LA104_0<=71)||LA104_0==73||LA104_0==75||(LA104_0>=77 && LA104_0<=80)||(LA104_0>=82 && LA104_0<=86)||(LA104_0>=104 && LA104_0<=105)||(LA104_0>=108 && LA104_0<=113)) ) {
                    alt104=1;
                }


                switch (alt104) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: blockStatement
            	    {
            	    pushFollow(FOLLOW_blockStatement_in_block2190);
            	    blockStatement();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop104;
                }
            } while (true);

            match(input,38,FOLLOW_38_in_block2193); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 80, block_StartIndex); }
        }
        return ;
    }
    // $ANTLR end block


    // $ANTLR start blockStatement
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );
    public final void blockStatement() throws RecognitionException {
        int blockStatement_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 81) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:519:2: ( localVariableDeclaration | classOrInterfaceDeclaration | statement )
            int alt105=3;
            switch ( input.LA(1) ) {
            case 49:
                {
                switch ( input.LA(2) ) {
                case ENUM:
                case 27:
                case 30:
                case 39:
                case 45:
                case 46:
                case 47:
                case 48:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                    {
                    alt105=2;
                    }
                    break;
                case 71:
                    {
                    int LA105_52 = input.LA(3);

                    if ( (synpred144()) ) {
                        alt105=1;
                    }
                    else if ( (synpred145()) ) {
                        alt105=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 52, input);

                        throw nvae;
                    }
                    }
                    break;
                case 49:
                    {
                    int LA105_58 = input.LA(3);

                    if ( (synpred144()) ) {
                        alt105=1;
                    }
                    else if ( (synpred145()) ) {
                        alt105=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 58, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                    {
                    alt105=1;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 1, input);

                    throw nvae;
                }

                }
                break;
            case 71:
                {
                int LA105_2 = input.LA(2);

                if ( (LA105_2==39) ) {
                    alt105=2;
                }
                else if ( (LA105_2==Identifier) ) {
                    int LA105_67 = input.LA(3);

                    if ( (synpred144()) ) {
                        alt105=1;
                    }
                    else if ( (synpred145()) ) {
                        alt105=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 67, input);

                        throw nvae;
                    }
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 2, input);

                    throw nvae;
                }
                }
                break;
            case Identifier:
                {
                switch ( input.LA(2) ) {
                case 25:
                case 29:
                case 35:
                case 36:
                case 44:
                case 63:
                case 65:
                case 74:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                    {
                    alt105=3;
                    }
                    break;
                case 28:
                    {
                    int LA105_69 = input.LA(3);

                    if ( (synpred144()) ) {
                        alt105=1;
                    }
                    else if ( (true) ) {
                        alt105=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 69, input);

                        throw nvae;
                    }
                    }
                    break;
                case 41:
                    {
                    int LA105_70 = input.LA(3);

                    if ( (synpred144()) ) {
                        alt105=1;
                    }
                    else if ( (true) ) {
                        alt105=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 70, input);

                        throw nvae;
                    }
                    }
                    break;
                case 33:
                    {
                    int LA105_75 = input.LA(3);

                    if ( (synpred144()) ) {
                        alt105=1;
                    }
                    else if ( (true) ) {
                        alt105=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 75, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                    {
                    alt105=1;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 3, input);

                    throw nvae;
                }

                }
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
                {
                switch ( input.LA(2) ) {
                case 41:
                    {
                    int LA105_96 = input.LA(3);

                    if ( (synpred144()) ) {
                        alt105=1;
                    }
                    else if ( (true) ) {
                        alt105=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 96, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                    {
                    alt105=1;
                    }
                    break;
                case 28:
                    {
                    alt105=3;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 4, input);

                    throw nvae;
                }

                }
                break;
            case ENUM:
            case 27:
            case 30:
            case 39:
            case 45:
            case 46:
            case 47:
            case 48:
            case 50:
            case 52:
            case 53:
            case 54:
                {
                alt105=2;
                }
                break;
            case 51:
                {
                int LA105_11 = input.LA(2);

                if ( (LA105_11==65) ) {
                    alt105=3;
                }
                else if ( (LA105_11==ENUM||LA105_11==27||LA105_11==30||LA105_11==39||(LA105_11>=45 && LA105_11<=54)||LA105_11==71) ) {
                    alt105=2;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 11, input);

                    throw nvae;
                }
                }
                break;
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 25:
            case 33:
            case 37:
            case 40:
            case 64:
            case 65:
            case 68:
            case 69:
            case 70:
            case 73:
            case 75:
            case 77:
            case 78:
            case 79:
            case 80:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 104:
            case 105:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
                {
                alt105=3;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("518:1: blockStatement : ( localVariableDeclaration | classOrInterfaceDeclaration | statement );", 105, 0, input);

                throw nvae;
            }

            switch (alt105) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:519:4: localVariableDeclaration
                    {
                    pushFollow(FOLLOW_localVariableDeclaration_in_blockStatement2205);
                    localVariableDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:520:4: classOrInterfaceDeclaration
                    {
                    pushFollow(FOLLOW_classOrInterfaceDeclaration_in_blockStatement2210);
                    classOrInterfaceDeclaration();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:521:8: statement
                    {
                    pushFollow(FOLLOW_statement_in_blockStatement2219);
                    statement();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 81, blockStatement_StartIndex); }
        }
        return ;
    }
    // $ANTLR end blockStatement


    // $ANTLR start localVariableDeclaration
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:524:1: localVariableDeclaration : ( variableModifier )* type variableDeclarators ';' ;
    public final void localVariableDeclaration() throws RecognitionException {
        int localVariableDeclaration_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 82) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:525:2: ( ( variableModifier )* type variableDeclarators ';' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:525:4: ( variableModifier )* type variableDeclarators ';'
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:525:4: ( variableModifier )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( (LA106_0==49||LA106_0==71) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: variableModifier
            	    {
            	    pushFollow(FOLLOW_variableModifier_in_localVariableDeclaration2231);
            	    variableModifier();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop106;
                }
            } while (true);

            pushFollow(FOLLOW_type_in_localVariableDeclaration2234);
            type();
            _fsp--;
            if (failed) return ;
            pushFollow(FOLLOW_variableDeclarators_in_localVariableDeclaration2236);
            variableDeclarators();
            _fsp--;
            if (failed) return ;
            match(input,25,FOLLOW_25_in_localVariableDeclaration2238); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 82, localVariableDeclaration_StartIndex); }
        }
        return ;
    }
    // $ANTLR end localVariableDeclaration


    // $ANTLR start statement
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:528:1: statement : ( block | 'assert' expression ( ':' expression )? ';' | 'if' parExpression statement ( options {k=1; } : 'else' statement )? | 'for' '(' forControl ')' statement | 'while' parExpression statement | 'do' statement 'while' parExpression ';' | 'try' block ( catches 'finally' block | catches | 'finally' block ) | 'switch' parExpression '{' switchBlockStatementGroups '}' | 'synchronized' parExpression block | 'return' ( expression )? ';' | 'throw' expression ';' | 'break' ( Identifier )? ';' | 'continue' ( Identifier )? ';' | ';' | statementExpression ';' | Identifier ':' statement );
    public final void statement() throws RecognitionException {
        int statement_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 83) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:529:2: ( block | 'assert' expression ( ':' expression )? ';' | 'if' parExpression statement ( options {k=1; } : 'else' statement )? | 'for' '(' forControl ')' statement | 'while' parExpression statement | 'do' statement 'while' parExpression ';' | 'try' block ( catches 'finally' block | catches | 'finally' block ) | 'switch' parExpression '{' switchBlockStatementGroups '}' | 'synchronized' parExpression block | 'return' ( expression )? ';' | 'throw' expression ';' | 'break' ( Identifier )? ';' | 'continue' ( Identifier )? ';' | ';' | statementExpression ';' | Identifier ':' statement )
            int alt113=16;
            switch ( input.LA(1) ) {
            case 37:
                {
                alt113=1;
                }
                break;
            case 73:
                {
                alt113=2;
                }
                break;
            case 75:
                {
                alt113=3;
                }
                break;
            case 77:
                {
                alt113=4;
                }
                break;
            case 78:
                {
                alt113=5;
                }
                break;
            case 79:
                {
                alt113=6;
                }
                break;
            case 80:
                {
                alt113=7;
                }
                break;
            case 82:
                {
                alt113=8;
                }
                break;
            case 51:
                {
                alt113=9;
                }
                break;
            case 83:
                {
                alt113=10;
                }
                break;
            case 84:
                {
                alt113=11;
                }
                break;
            case 85:
                {
                alt113=12;
                }
                break;
            case 86:
                {
                alt113=13;
                }
                break;
            case 25:
                {
                alt113=14;
                }
                break;
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 33:
            case 40:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 64:
            case 65:
            case 68:
            case 69:
            case 70:
            case 104:
            case 105:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
                {
                alt113=15;
                }
                break;
            case Identifier:
                {
                int LA113_32 = input.LA(2);

                if ( (LA113_32==74) ) {
                    alt113=16;
                }
                else if ( (LA113_32==25||(LA113_32>=28 && LA113_32<=29)||LA113_32==33||(LA113_32>=35 && LA113_32<=36)||LA113_32==41||LA113_32==44||LA113_32==63||LA113_32==65||(LA113_32>=89 && LA113_32<=109)) ) {
                    alt113=15;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("528:1: statement : ( block | 'assert' expression ( ':' expression )? ';' | 'if' parExpression statement ( options {k=1; } : 'else' statement )? | 'for' '(' forControl ')' statement | 'while' parExpression statement | 'do' statement 'while' parExpression ';' | 'try' block ( catches 'finally' block | catches | 'finally' block ) | 'switch' parExpression '{' switchBlockStatementGroups '}' | 'synchronized' parExpression block | 'return' ( expression )? ';' | 'throw' expression ';' | 'break' ( Identifier )? ';' | 'continue' ( Identifier )? ';' | ';' | statementExpression ';' | Identifier ':' statement );", 113, 32, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("528:1: statement : ( block | 'assert' expression ( ':' expression )? ';' | 'if' parExpression statement ( options {k=1; } : 'else' statement )? | 'for' '(' forControl ')' statement | 'while' parExpression statement | 'do' statement 'while' parExpression ';' | 'try' block ( catches 'finally' block | catches | 'finally' block ) | 'switch' parExpression '{' switchBlockStatementGroups '}' | 'synchronized' parExpression block | 'return' ( expression )? ';' | 'throw' expression ';' | 'break' ( Identifier )? ';' | 'continue' ( Identifier )? ';' | ';' | statementExpression ';' | Identifier ':' statement );", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:529:4: block
                    {
                    pushFollow(FOLLOW_block_in_statement2250);
                    block();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:530:7: 'assert' expression ( ':' expression )? ';'
                    {
                    match(input,73,FOLLOW_73_in_statement2258); if (failed) return ;
                    pushFollow(FOLLOW_expression_in_statement2260);
                    expression();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:530:27: ( ':' expression )?
                    int alt107=2;
                    int LA107_0 = input.LA(1);

                    if ( (LA107_0==74) ) {
                        alt107=1;
                    }
                    switch (alt107) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:530:28: ':' expression
                            {
                            match(input,74,FOLLOW_74_in_statement2263); if (failed) return ;
                            pushFollow(FOLLOW_expression_in_statement2265);
                            expression();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    match(input,25,FOLLOW_25_in_statement2269); if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:531:7: 'if' parExpression statement ( options {k=1; } : 'else' statement )?
                    {
                    match(input,75,FOLLOW_75_in_statement2277); if (failed) return ;
                    pushFollow(FOLLOW_parExpression_in_statement2279);
                    parExpression();
                    _fsp--;
                    if (failed) return ;
                    pushFollow(FOLLOW_statement_in_statement2281);
                    statement();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:531:36: ( options {k=1; } : 'else' statement )?
                    int alt108=2;
                    int LA108_0 = input.LA(1);

                    if ( (LA108_0==76) ) {
                        int LA108_1 = input.LA(2);

                        if ( (synpred150()) ) {
                            alt108=1;
                        }
                    }
                    switch (alt108) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:531:52: 'else' statement
                            {
                            match(input,76,FOLLOW_76_in_statement2291); if (failed) return ;
                            pushFollow(FOLLOW_statement_in_statement2293);
                            statement();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:532:7: 'for' '(' forControl ')' statement
                    {
                    match(input,77,FOLLOW_77_in_statement2303); if (failed) return ;
                    match(input,65,FOLLOW_65_in_statement2305); if (failed) return ;
                    pushFollow(FOLLOW_forControl_in_statement2307);
                    forControl();
                    _fsp--;
                    if (failed) return ;
                    match(input,66,FOLLOW_66_in_statement2309); if (failed) return ;
                    pushFollow(FOLLOW_statement_in_statement2311);
                    statement();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:533:7: 'while' parExpression statement
                    {
                    match(input,78,FOLLOW_78_in_statement2319); if (failed) return ;
                    pushFollow(FOLLOW_parExpression_in_statement2321);
                    parExpression();
                    _fsp--;
                    if (failed) return ;
                    pushFollow(FOLLOW_statement_in_statement2323);
                    statement();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 6 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:534:7: 'do' statement 'while' parExpression ';'
                    {
                    match(input,79,FOLLOW_79_in_statement2331); if (failed) return ;
                    pushFollow(FOLLOW_statement_in_statement2333);
                    statement();
                    _fsp--;
                    if (failed) return ;
                    match(input,78,FOLLOW_78_in_statement2335); if (failed) return ;
                    pushFollow(FOLLOW_parExpression_in_statement2337);
                    parExpression();
                    _fsp--;
                    if (failed) return ;
                    match(input,25,FOLLOW_25_in_statement2339); if (failed) return ;

                    }
                    break;
                case 7 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:535:7: 'try' block ( catches 'finally' block | catches | 'finally' block )
                    {
                    match(input,80,FOLLOW_80_in_statement2347); if (failed) return ;
                    pushFollow(FOLLOW_block_in_statement2349);
                    block();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:536:7: ( catches 'finally' block | catches | 'finally' block )
                    int alt109=3;
                    int LA109_0 = input.LA(1);

                    if ( (LA109_0==87) ) {
                        int LA109_1 = input.LA(2);

                        if ( (LA109_1==65) ) {
                            int LA109_3 = input.LA(3);

                            if ( (synpred155()) ) {
                                alt109=1;
                            }
                            else if ( (synpred156()) ) {
                                alt109=2;
                            }
                            else {
                                if (backtracking>0) {failed=true; return ;}
                                NoViableAltException nvae =
                                    new NoViableAltException("536:7: ( catches 'finally' block | catches | 'finally' block )", 109, 3, input);

                                throw nvae;
                            }
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("536:7: ( catches 'finally' block | catches | 'finally' block )", 109, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA109_0==81) ) {
                        alt109=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("536:7: ( catches 'finally' block | catches | 'finally' block )", 109, 0, input);

                        throw nvae;
                    }
                    switch (alt109) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:536:9: catches 'finally' block
                            {
                            pushFollow(FOLLOW_catches_in_statement2359);
                            catches();
                            _fsp--;
                            if (failed) return ;
                            match(input,81,FOLLOW_81_in_statement2361); if (failed) return ;
                            pushFollow(FOLLOW_block_in_statement2363);
                            block();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;
                        case 2 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:537:9: catches
                            {
                            pushFollow(FOLLOW_catches_in_statement2373);
                            catches();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;
                        case 3 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:538:9: 'finally' block
                            {
                            match(input,81,FOLLOW_81_in_statement2383); if (failed) return ;
                            pushFollow(FOLLOW_block_in_statement2385);
                            block();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 8 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:540:7: 'switch' parExpression '{' switchBlockStatementGroups '}'
                    {
                    match(input,82,FOLLOW_82_in_statement2401); if (failed) return ;
                    pushFollow(FOLLOW_parExpression_in_statement2403);
                    parExpression();
                    _fsp--;
                    if (failed) return ;
                    match(input,37,FOLLOW_37_in_statement2405); if (failed) return ;
                    pushFollow(FOLLOW_switchBlockStatementGroups_in_statement2407);
                    switchBlockStatementGroups();
                    _fsp--;
                    if (failed) return ;
                    match(input,38,FOLLOW_38_in_statement2409); if (failed) return ;

                    }
                    break;
                case 9 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:541:7: 'synchronized' parExpression block
                    {
                    match(input,51,FOLLOW_51_in_statement2417); if (failed) return ;
                    pushFollow(FOLLOW_parExpression_in_statement2419);
                    parExpression();
                    _fsp--;
                    if (failed) return ;
                    pushFollow(FOLLOW_block_in_statement2421);
                    block();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 10 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:542:7: 'return' ( expression )? ';'
                    {
                    match(input,83,FOLLOW_83_in_statement2429); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:542:16: ( expression )?
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==Identifier||(LA110_0>=FloatingPointLiteral && LA110_0<=DecimalLiteral)||LA110_0==33||LA110_0==40||(LA110_0>=55 && LA110_0<=62)||(LA110_0>=64 && LA110_0<=65)||(LA110_0>=68 && LA110_0<=70)||(LA110_0>=104 && LA110_0<=105)||(LA110_0>=108 && LA110_0<=113)) ) {
                        alt110=1;
                    }
                    switch (alt110) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: expression
                            {
                            pushFollow(FOLLOW_expression_in_statement2431);
                            expression();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    match(input,25,FOLLOW_25_in_statement2434); if (failed) return ;

                    }
                    break;
                case 11 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:543:7: 'throw' expression ';'
                    {
                    match(input,84,FOLLOW_84_in_statement2442); if (failed) return ;
                    pushFollow(FOLLOW_expression_in_statement2444);
                    expression();
                    _fsp--;
                    if (failed) return ;
                    match(input,25,FOLLOW_25_in_statement2446); if (failed) return ;

                    }
                    break;
                case 12 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:544:7: 'break' ( Identifier )? ';'
                    {
                    match(input,85,FOLLOW_85_in_statement2454); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:544:15: ( Identifier )?
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==Identifier) ) {
                        alt111=1;
                    }
                    switch (alt111) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: Identifier
                            {
                            match(input,Identifier,FOLLOW_Identifier_in_statement2456); if (failed) return ;

                            }
                            break;

                    }

                    match(input,25,FOLLOW_25_in_statement2459); if (failed) return ;

                    }
                    break;
                case 13 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:545:7: 'continue' ( Identifier )? ';'
                    {
                    match(input,86,FOLLOW_86_in_statement2467); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:545:18: ( Identifier )?
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==Identifier) ) {
                        alt112=1;
                    }
                    switch (alt112) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: Identifier
                            {
                            match(input,Identifier,FOLLOW_Identifier_in_statement2469); if (failed) return ;

                            }
                            break;

                    }

                    match(input,25,FOLLOW_25_in_statement2472); if (failed) return ;

                    }
                    break;
                case 14 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:546:7: ';'
                    {
                    match(input,25,FOLLOW_25_in_statement2480); if (failed) return ;

                    }
                    break;
                case 15 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:547:7: statementExpression ';'
                    {
                    pushFollow(FOLLOW_statementExpression_in_statement2488);
                    statementExpression();
                    _fsp--;
                    if (failed) return ;
                    match(input,25,FOLLOW_25_in_statement2490); if (failed) return ;

                    }
                    break;
                case 16 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:548:7: Identifier ':' statement
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_statement2498); if (failed) return ;
                    match(input,74,FOLLOW_74_in_statement2500); if (failed) return ;
                    pushFollow(FOLLOW_statement_in_statement2502);
                    statement();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 83, statement_StartIndex); }
        }
        return ;
    }
    // $ANTLR end statement


    // $ANTLR start catches
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:551:1: catches : catchClause ( catchClause )* ;
    public final void catches() throws RecognitionException {
        int catches_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 84) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:552:2: ( catchClause ( catchClause )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:552:4: catchClause ( catchClause )*
            {
            pushFollow(FOLLOW_catchClause_in_catches2514);
            catchClause();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:552:16: ( catchClause )*
            loop114:
            do {
                int alt114=2;
                int LA114_0 = input.LA(1);

                if ( (LA114_0==87) ) {
                    alt114=1;
                }


                switch (alt114) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:552:17: catchClause
            	    {
            	    pushFollow(FOLLOW_catchClause_in_catches2517);
            	    catchClause();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop114;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 84, catches_StartIndex); }
        }
        return ;
    }
    // $ANTLR end catches


    // $ANTLR start catchClause
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:555:1: catchClause : 'catch' '(' formalParameter ')' block ;
    public final void catchClause() throws RecognitionException {
        int catchClause_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 85) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:556:2: ( 'catch' '(' formalParameter ')' block )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:556:4: 'catch' '(' formalParameter ')' block
            {
            match(input,87,FOLLOW_87_in_catchClause2531); if (failed) return ;
            match(input,65,FOLLOW_65_in_catchClause2533); if (failed) return ;
            pushFollow(FOLLOW_formalParameter_in_catchClause2535);
            formalParameter();
            _fsp--;
            if (failed) return ;
            match(input,66,FOLLOW_66_in_catchClause2537); if (failed) return ;
            pushFollow(FOLLOW_block_in_catchClause2539);
            block();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 85, catchClause_StartIndex); }
        }
        return ;
    }
    // $ANTLR end catchClause


    // $ANTLR start formalParameter
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:559:1: formalParameter : ( variableModifier )* type variableDeclaratorId ;
    public final void formalParameter() throws RecognitionException {
        int formalParameter_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 86) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:560:2: ( ( variableModifier )* type variableDeclaratorId )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:560:4: ( variableModifier )* type variableDeclaratorId
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:560:4: ( variableModifier )*
            loop115:
            do {
                int alt115=2;
                int LA115_0 = input.LA(1);

                if ( (LA115_0==49||LA115_0==71) ) {
                    alt115=1;
                }


                switch (alt115) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: variableModifier
            	    {
            	    pushFollow(FOLLOW_variableModifier_in_formalParameter2550);
            	    variableModifier();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop115;
                }
            } while (true);

            pushFollow(FOLLOW_type_in_formalParameter2553);
            type();
            _fsp--;
            if (failed) return ;
            pushFollow(FOLLOW_variableDeclaratorId_in_formalParameter2555);
            variableDeclaratorId();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 86, formalParameter_StartIndex); }
        }
        return ;
    }
    // $ANTLR end formalParameter


    // $ANTLR start switchBlockStatementGroups
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:563:1: switchBlockStatementGroups : ( switchBlockStatementGroup )* ;
    public final void switchBlockStatementGroups() throws RecognitionException {
        int switchBlockStatementGroups_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 87) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:564:2: ( ( switchBlockStatementGroup )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:564:4: ( switchBlockStatementGroup )*
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:564:4: ( switchBlockStatementGroup )*
            loop116:
            do {
                int alt116=2;
                int LA116_0 = input.LA(1);

                if ( (LA116_0==72||LA116_0==88) ) {
                    alt116=1;
                }


                switch (alt116) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:564:5: switchBlockStatementGroup
            	    {
            	    pushFollow(FOLLOW_switchBlockStatementGroup_in_switchBlockStatementGroups2569);
            	    switchBlockStatementGroup();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop116;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 87, switchBlockStatementGroups_StartIndex); }
        }
        return ;
    }
    // $ANTLR end switchBlockStatementGroups


    // $ANTLR start switchBlockStatementGroup
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:567:1: switchBlockStatementGroup : switchLabel ( blockStatement )* ;
    public final void switchBlockStatementGroup() throws RecognitionException {
        int switchBlockStatementGroup_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 88) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:568:2: ( switchLabel ( blockStatement )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:568:4: switchLabel ( blockStatement )*
            {
            pushFollow(FOLLOW_switchLabel_in_switchBlockStatementGroup2583);
            switchLabel();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:568:16: ( blockStatement )*
            loop117:
            do {
                int alt117=2;
                int LA117_0 = input.LA(1);

                if ( ((LA117_0>=Identifier && LA117_0<=DecimalLiteral)||LA117_0==25||LA117_0==27||LA117_0==30||LA117_0==33||LA117_0==37||(LA117_0>=39 && LA117_0<=40)||(LA117_0>=45 && LA117_0<=62)||(LA117_0>=64 && LA117_0<=65)||(LA117_0>=68 && LA117_0<=71)||LA117_0==73||LA117_0==75||(LA117_0>=77 && LA117_0<=80)||(LA117_0>=82 && LA117_0<=86)||(LA117_0>=104 && LA117_0<=105)||(LA117_0>=108 && LA117_0<=113)) ) {
                    alt117=1;
                }


                switch (alt117) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: blockStatement
            	    {
            	    pushFollow(FOLLOW_blockStatement_in_switchBlockStatementGroup2585);
            	    blockStatement();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop117;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 88, switchBlockStatementGroup_StartIndex); }
        }
        return ;
    }
    // $ANTLR end switchBlockStatementGroup


    // $ANTLR start switchLabel
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:571:1: switchLabel : ( 'case' constantExpression ':' | 'case' enumConstantName ':' | 'default' ':' );
    public final void switchLabel() throws RecognitionException {
        int switchLabel_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 89) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:572:2: ( 'case' constantExpression ':' | 'case' enumConstantName ':' | 'default' ':' )
            int alt118=3;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==88) ) {
                int LA118_1 = input.LA(2);

                if ( ((LA118_1>=FloatingPointLiteral && LA118_1<=DecimalLiteral)||LA118_1==33||LA118_1==40||(LA118_1>=55 && LA118_1<=62)||(LA118_1>=64 && LA118_1<=65)||(LA118_1>=68 && LA118_1<=70)||(LA118_1>=104 && LA118_1<=105)||(LA118_1>=108 && LA118_1<=113)) ) {
                    alt118=1;
                }
                else if ( (LA118_1==Identifier) ) {
                    int LA118_20 = input.LA(3);

                    if ( (synpred173()) ) {
                        alt118=1;
                    }
                    else if ( (synpred174()) ) {
                        alt118=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("571:1: switchLabel : ( 'case' constantExpression ':' | 'case' enumConstantName ':' | 'default' ':' );", 118, 20, input);

                        throw nvae;
                    }
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("571:1: switchLabel : ( 'case' constantExpression ':' | 'case' enumConstantName ':' | 'default' ':' );", 118, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA118_0==72) ) {
                alt118=3;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("571:1: switchLabel : ( 'case' constantExpression ':' | 'case' enumConstantName ':' | 'default' ':' );", 118, 0, input);

                throw nvae;
            }
            switch (alt118) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:572:4: 'case' constantExpression ':'
                    {
                    match(input,88,FOLLOW_88_in_switchLabel2598); if (failed) return ;
                    pushFollow(FOLLOW_constantExpression_in_switchLabel2600);
                    constantExpression();
                    _fsp--;
                    if (failed) return ;
                    match(input,74,FOLLOW_74_in_switchLabel2602); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:573:6: 'case' enumConstantName ':'
                    {
                    match(input,88,FOLLOW_88_in_switchLabel2609); if (failed) return ;
                    pushFollow(FOLLOW_enumConstantName_in_switchLabel2611);
                    enumConstantName();
                    _fsp--;
                    if (failed) return ;
                    match(input,74,FOLLOW_74_in_switchLabel2613); if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:574:6: 'default' ':'
                    {
                    match(input,72,FOLLOW_72_in_switchLabel2620); if (failed) return ;
                    match(input,74,FOLLOW_74_in_switchLabel2622); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 89, switchLabel_StartIndex); }
        }
        return ;
    }
    // $ANTLR end switchLabel


    // $ANTLR start moreStatementExpressions
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:577:1: moreStatementExpressions : ( ',' statementExpression )* ;
    public final void moreStatementExpressions() throws RecognitionException {
        int moreStatementExpressions_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 90) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:578:2: ( ( ',' statementExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:578:4: ( ',' statementExpression )*
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:578:4: ( ',' statementExpression )*
            loop119:
            do {
                int alt119=2;
                int LA119_0 = input.LA(1);

                if ( (LA119_0==34) ) {
                    alt119=1;
                }


                switch (alt119) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:578:5: ',' statementExpression
            	    {
            	    match(input,34,FOLLOW_34_in_moreStatementExpressions2635); if (failed) return ;
            	    pushFollow(FOLLOW_statementExpression_in_moreStatementExpressions2637);
            	    statementExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop119;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 90, moreStatementExpressions_StartIndex); }
        }
        return ;
    }
    // $ANTLR end moreStatementExpressions


    // $ANTLR start forControl
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );
    public final void forControl() throws RecognitionException {
        int forControl_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 91) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:583:2: ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? )
            int alt123=2;
            switch ( input.LA(1) ) {
            case 49:
                {
                switch ( input.LA(2) ) {
                case Identifier:
                    {
                    switch ( input.LA(3) ) {
                    case 33:
                        {
                        int LA123_60 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 60, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 28:
                        {
                        int LA123_61 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 61, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 41:
                        {
                        int LA123_62 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 62, input);

                            throw nvae;
                        }
                        }
                        break;
                    case Identifier:
                        {
                        int LA123_63 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 63, input);

                            throw nvae;
                        }
                        }
                        break;
                    default:
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 24, input);

                        throw nvae;
                    }

                    }
                    break;
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                    {
                    int LA123_25 = input.LA(3);

                    if ( (LA123_25==41) ) {
                        int LA123_64 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 64, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA123_25==Identifier) ) {
                        int LA123_65 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 65, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 25, input);

                        throw nvae;
                    }
                    }
                    break;
                case 49:
                    {
                    switch ( input.LA(3) ) {
                    case Identifier:
                        {
                        int LA123_66 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 66, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                        {
                        int LA123_67 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 67, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 49:
                        {
                        int LA123_68 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 68, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 71:
                        {
                        int LA123_69 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 69, input);

                            throw nvae;
                        }
                        }
                        break;
                    default:
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 26, input);

                        throw nvae;
                    }

                    }
                    break;
                case 71:
                    {
                    int LA123_27 = input.LA(3);

                    if ( (LA123_27==Identifier) ) {
                        int LA123_70 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 70, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 27, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 1, input);

                    throw nvae;
                }

                }
                break;
            case 71:
                {
                int LA123_2 = input.LA(2);

                if ( (LA123_2==Identifier) ) {
                    switch ( input.LA(3) ) {
                    case 28:
                        {
                        int LA123_71 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 71, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 65:
                        {
                        int LA123_72 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 72, input);

                            throw nvae;
                        }
                        }
                        break;
                    case Identifier:
                        {
                        int LA123_73 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 73, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                        {
                        int LA123_74 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 74, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 49:
                        {
                        int LA123_75 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 75, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 71:
                        {
                        int LA123_76 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 76, input);

                            throw nvae;
                        }
                        }
                        break;
                    default:
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 28, input);

                        throw nvae;
                    }

                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 2, input);

                    throw nvae;
                }
                }
                break;
            case Identifier:
                {
                switch ( input.LA(2) ) {
                case 33:
                    {
                    switch ( input.LA(3) ) {
                    case FloatingPointLiteral:
                    case CharacterLiteral:
                    case StringLiteral:
                    case HexLiteral:
                    case OctalLiteral:
                    case DecimalLiteral:
                    case 33:
                    case 40:
                    case 44:
                    case 64:
                    case 65:
                    case 68:
                    case 69:
                    case 70:
                    case 104:
                    case 105:
                    case 108:
                    case 109:
                    case 110:
                    case 111:
                    case 112:
                    case 113:
                        {
                        alt123=2;
                        }
                        break;
                    case Identifier:
                        {
                        int LA123_79 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 79, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                        {
                        int LA123_80 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 80, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 63:
                        {
                        int LA123_81 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 81, input);

                            throw nvae;
                        }
                        }
                        break;
                    default:
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 29, input);

                        throw nvae;
                    }

                    }
                    break;
                case 28:
                    {
                    int LA123_30 = input.LA(3);

                    if ( (LA123_30==Identifier) ) {
                        int LA123_99 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 99, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA123_30==30||LA123_30==33||LA123_30==64||(LA123_30>=112 && LA123_30<=113)) ) {
                        alt123=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 30, input);

                        throw nvae;
                    }
                    }
                    break;
                case 41:
                    {
                    int LA123_31 = input.LA(3);

                    if ( (LA123_31==42) ) {
                        int LA123_105 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 105, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA123_31==Identifier||(LA123_31>=FloatingPointLiteral && LA123_31<=DecimalLiteral)||LA123_31==33||LA123_31==40||(LA123_31>=55 && LA123_31<=62)||(LA123_31>=64 && LA123_31<=65)||(LA123_31>=68 && LA123_31<=70)||(LA123_31>=104 && LA123_31<=105)||(LA123_31>=108 && LA123_31<=113)) ) {
                        alt123=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 31, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                    {
                    int LA123_32 = input.LA(3);

                    if ( (LA123_32==74) ) {
                        alt123=1;
                    }
                    else if ( (LA123_32==25||LA123_32==34||LA123_32==41||LA123_32==44) ) {
                        alt123=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 32, input);

                        throw nvae;
                    }
                    }
                    break;
                case 25:
                case 29:
                case 34:
                case 35:
                case 36:
                case 44:
                case 63:
                case 65:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                    {
                    alt123=2;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 3, input);

                    throw nvae;
                }

                }
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
                {
                switch ( input.LA(2) ) {
                case 41:
                    {
                    int LA123_57 = input.LA(3);

                    if ( (LA123_57==42) ) {
                        int LA123_131 = input.LA(4);

                        if ( (synpred176()) ) {
                            alt123=1;
                        }
                        else if ( (true) ) {
                            alt123=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 131, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 57, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                    {
                    int LA123_58 = input.LA(3);

                    if ( (LA123_58==74) ) {
                        alt123=1;
                    }
                    else if ( (LA123_58==25||LA123_58==34||LA123_58==41||LA123_58==44) ) {
                        alt123=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 58, input);

                        throw nvae;
                    }
                    }
                    break;
                case 28:
                    {
                    alt123=2;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 4, input);

                    throw nvae;
                }

                }
                break;
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 25:
            case 33:
            case 40:
            case 64:
            case 65:
            case 68:
            case 69:
            case 70:
            case 104:
            case 105:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
                {
                alt123=2;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("581:1: forControl options {k=3; } : ( forVarControl | ( forInit )? ';' ( expression )? ';' ( forUpdate )? );", 123, 0, input);

                throw nvae;
            }

            switch (alt123) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:583:4: forVarControl
                    {
                    pushFollow(FOLLOW_forVarControl_in_forControl2658);
                    forVarControl();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:584:4: ( forInit )? ';' ( expression )? ';' ( forUpdate )?
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:584:4: ( forInit )?
                    int alt120=2;
                    int LA120_0 = input.LA(1);

                    if ( (LA120_0==Identifier||(LA120_0>=FloatingPointLiteral && LA120_0<=DecimalLiteral)||LA120_0==33||LA120_0==40||LA120_0==49||(LA120_0>=55 && LA120_0<=62)||(LA120_0>=64 && LA120_0<=65)||(LA120_0>=68 && LA120_0<=71)||(LA120_0>=104 && LA120_0<=105)||(LA120_0>=108 && LA120_0<=113)) ) {
                        alt120=1;
                    }
                    switch (alt120) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: forInit
                            {
                            pushFollow(FOLLOW_forInit_in_forControl2663);
                            forInit();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    match(input,25,FOLLOW_25_in_forControl2666); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:584:17: ( expression )?
                    int alt121=2;
                    int LA121_0 = input.LA(1);

                    if ( (LA121_0==Identifier||(LA121_0>=FloatingPointLiteral && LA121_0<=DecimalLiteral)||LA121_0==33||LA121_0==40||(LA121_0>=55 && LA121_0<=62)||(LA121_0>=64 && LA121_0<=65)||(LA121_0>=68 && LA121_0<=70)||(LA121_0>=104 && LA121_0<=105)||(LA121_0>=108 && LA121_0<=113)) ) {
                        alt121=1;
                    }
                    switch (alt121) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: expression
                            {
                            pushFollow(FOLLOW_expression_in_forControl2668);
                            expression();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    match(input,25,FOLLOW_25_in_forControl2671); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:584:33: ( forUpdate )?
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( (LA122_0==Identifier||(LA122_0>=FloatingPointLiteral && LA122_0<=DecimalLiteral)||LA122_0==33||LA122_0==40||(LA122_0>=55 && LA122_0<=62)||(LA122_0>=64 && LA122_0<=65)||(LA122_0>=68 && LA122_0<=70)||(LA122_0>=104 && LA122_0<=105)||(LA122_0>=108 && LA122_0<=113)) ) {
                        alt122=1;
                    }
                    switch (alt122) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: forUpdate
                            {
                            pushFollow(FOLLOW_forUpdate_in_forControl2673);
                            forUpdate();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

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
            if ( backtracking>0 ) { memoize(input, 91, forControl_StartIndex); }
        }
        return ;
    }
    // $ANTLR end forControl


    // $ANTLR start forInit
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:587:1: forInit : ( ( variableModifier )* type variableDeclarators | expressionList );
    public final void forInit() throws RecognitionException {
        int forInit_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 92) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:588:2: ( ( variableModifier )* type variableDeclarators | expressionList )
            int alt125=2;
            switch ( input.LA(1) ) {
            case 49:
            case 71:
                {
                alt125=1;
                }
                break;
            case Identifier:
                {
                switch ( input.LA(2) ) {
                case 28:
                    {
                    int LA125_23 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt125=1;
                    }
                    else if ( (true) ) {
                        alt125=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("587:1: forInit : ( ( variableModifier )* type variableDeclarators | expressionList );", 125, 23, input);

                        throw nvae;
                    }
                    }
                    break;
                case 41:
                    {
                    int LA125_24 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt125=1;
                    }
                    else if ( (true) ) {
                        alt125=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("587:1: forInit : ( ( variableModifier )* type variableDeclarators | expressionList );", 125, 24, input);

                        throw nvae;
                    }
                    }
                    break;
                case EOF:
                case 25:
                case 29:
                case 34:
                case 35:
                case 36:
                case 44:
                case 63:
                case 65:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                    {
                    alt125=2;
                    }
                    break;
                case 33:
                    {
                    int LA125_29 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt125=1;
                    }
                    else if ( (true) ) {
                        alt125=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("587:1: forInit : ( ( variableModifier )* type variableDeclarators | expressionList );", 125, 29, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                    {
                    alt125=1;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("587:1: forInit : ( ( variableModifier )* type variableDeclarators | expressionList );", 125, 3, input);

                    throw nvae;
                }

                }
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
                {
                switch ( input.LA(2) ) {
                case 41:
                    {
                    int LA125_52 = input.LA(3);

                    if ( (synpred181()) ) {
                        alt125=1;
                    }
                    else if ( (true) ) {
                        alt125=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("587:1: forInit : ( ( variableModifier )* type variableDeclarators | expressionList );", 125, 52, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                    {
                    alt125=1;
                    }
                    break;
                case 28:
                    {
                    alt125=2;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("587:1: forInit : ( ( variableModifier )* type variableDeclarators | expressionList );", 125, 4, input);

                    throw nvae;
                }

                }
                break;
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 33:
            case 40:
            case 64:
            case 65:
            case 68:
            case 69:
            case 70:
            case 104:
            case 105:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
                {
                alt125=2;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("587:1: forInit : ( ( variableModifier )* type variableDeclarators | expressionList );", 125, 0, input);

                throw nvae;
            }

            switch (alt125) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:588:4: ( variableModifier )* type variableDeclarators
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:588:4: ( variableModifier )*
                    loop124:
                    do {
                        int alt124=2;
                        int LA124_0 = input.LA(1);

                        if ( (LA124_0==49||LA124_0==71) ) {
                            alt124=1;
                        }


                        switch (alt124) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: variableModifier
                    	    {
                    	    pushFollow(FOLLOW_variableModifier_in_forInit2685);
                    	    variableModifier();
                    	    _fsp--;
                    	    if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop124;
                        }
                    } while (true);

                    pushFollow(FOLLOW_type_in_forInit2688);
                    type();
                    _fsp--;
                    if (failed) return ;
                    pushFollow(FOLLOW_variableDeclarators_in_forInit2690);
                    variableDeclarators();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:589:4: expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_forInit2695);
                    expressionList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 92, forInit_StartIndex); }
        }
        return ;
    }
    // $ANTLR end forInit


    // $ANTLR start forVarControl
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:592:1: forVarControl : ( variableModifier )* type Identifier ':' expression ;
    public final void forVarControl() throws RecognitionException {
        int forVarControl_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 93) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:593:2: ( ( variableModifier )* type Identifier ':' expression )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:593:4: ( variableModifier )* type Identifier ':' expression
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:593:4: ( variableModifier )*
            loop126:
            do {
                int alt126=2;
                int LA126_0 = input.LA(1);

                if ( (LA126_0==49||LA126_0==71) ) {
                    alt126=1;
                }


                switch (alt126) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: variableModifier
            	    {
            	    pushFollow(FOLLOW_variableModifier_in_forVarControl2707);
            	    variableModifier();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop126;
                }
            } while (true);

            pushFollow(FOLLOW_type_in_forVarControl2710);
            type();
            _fsp--;
            if (failed) return ;
            match(input,Identifier,FOLLOW_Identifier_in_forVarControl2712); if (failed) return ;
            match(input,74,FOLLOW_74_in_forVarControl2714); if (failed) return ;
            pushFollow(FOLLOW_expression_in_forVarControl2716);
            expression();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 93, forVarControl_StartIndex); }
        }
        return ;
    }
    // $ANTLR end forVarControl


    // $ANTLR start forUpdate
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:596:1: forUpdate : expressionList ;
    public final void forUpdate() throws RecognitionException {
        int forUpdate_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 94) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:597:2: ( expressionList )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:597:4: expressionList
            {
            pushFollow(FOLLOW_expressionList_in_forUpdate2727);
            expressionList();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 94, forUpdate_StartIndex); }
        }
        return ;
    }
    // $ANTLR end forUpdate


    // $ANTLR start parExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:602:1: parExpression : '(' expression ')' ;
    public final void parExpression() throws RecognitionException {
        int parExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 95) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:603:2: ( '(' expression ')' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:603:4: '(' expression ')'
            {
            match(input,65,FOLLOW_65_in_parExpression2740); if (failed) return ;
            pushFollow(FOLLOW_expression_in_parExpression2742);
            expression();
            _fsp--;
            if (failed) return ;
            match(input,66,FOLLOW_66_in_parExpression2744); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 95, parExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end parExpression


    // $ANTLR start expressionList
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:606:1: expressionList : expression ( ',' expression )* ;
    public final void expressionList() throws RecognitionException {
        int expressionList_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 96) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:607:5: ( expression ( ',' expression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:607:9: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_expressionList2761);
            expression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:607:20: ( ',' expression )*
            loop127:
            do {
                int alt127=2;
                int LA127_0 = input.LA(1);

                if ( (LA127_0==34) ) {
                    alt127=1;
                }


                switch (alt127) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:607:21: ',' expression
            	    {
            	    match(input,34,FOLLOW_34_in_expressionList2764); if (failed) return ;
            	    pushFollow(FOLLOW_expression_in_expressionList2766);
            	    expression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop127;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 96, expressionList_StartIndex); }
        }
        return ;
    }
    // $ANTLR end expressionList


    // $ANTLR start statementExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:610:1: statementExpression : expression ;
    public final void statementExpression() throws RecognitionException {
        int statementExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 97) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:611:2: ( expression )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:611:4: expression
            {
            pushFollow(FOLLOW_expression_in_statementExpression2782);
            expression();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 97, statementExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end statementExpression


    // $ANTLR start constantExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:614:1: constantExpression : expression ;
    public final void constantExpression() throws RecognitionException {
        int constantExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 98) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:615:2: ( expression )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:615:4: expression
            {
            pushFollow(FOLLOW_expression_in_constantExpression2794);
            expression();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 98, constantExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end constantExpression


    // $ANTLR start expression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:618:1: expression : conditionalExpression ( assignmentOperator expression )? ;
    public final void expression() throws RecognitionException {
        int expression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 99) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:619:2: ( conditionalExpression ( assignmentOperator expression )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:619:4: conditionalExpression ( assignmentOperator expression )?
            {
            pushFollow(FOLLOW_conditionalExpression_in_expression2806);
            conditionalExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:619:26: ( assignmentOperator expression )?
            int alt128=2;
            switch ( input.LA(1) ) {
                case 44:
                    {
                    int LA128_1 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 89:
                    {
                    int LA128_2 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 90:
                    {
                    int LA128_3 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 91:
                    {
                    int LA128_4 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 92:
                    {
                    int LA128_5 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 93:
                    {
                    int LA128_6 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 94:
                    {
                    int LA128_7 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 95:
                    {
                    int LA128_8 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 96:
                    {
                    int LA128_9 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 33:
                    {
                    int LA128_10 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
                case 35:
                    {
                    int LA128_11 = input.LA(2);

                    if ( (synpred184()) ) {
                        alt128=1;
                    }
                    }
                    break;
            }

            switch (alt128) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:619:27: assignmentOperator expression
                    {
                    pushFollow(FOLLOW_assignmentOperator_in_expression2809);
                    assignmentOperator();
                    _fsp--;
                    if (failed) return ;
                    pushFollow(FOLLOW_expression_in_expression2811);
                    expression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 99, expression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end expression


    // $ANTLR start assignmentOperator
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:622:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '=' | '>' '>' '>' '=' );
    public final void assignmentOperator() throws RecognitionException {
        int assignmentOperator_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 100) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:623:2: ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '=' | '>' '>' '>' '=' )
            int alt129=12;
            switch ( input.LA(1) ) {
            case 44:
                {
                alt129=1;
                }
                break;
            case 89:
                {
                alt129=2;
                }
                break;
            case 90:
                {
                alt129=3;
                }
                break;
            case 91:
                {
                alt129=4;
                }
                break;
            case 92:
                {
                alt129=5;
                }
                break;
            case 93:
                {
                alt129=6;
                }
                break;
            case 94:
                {
                alt129=7;
                }
                break;
            case 95:
                {
                alt129=8;
                }
                break;
            case 96:
                {
                alt129=9;
                }
                break;
            case 33:
                {
                alt129=10;
                }
                break;
            case 35:
                {
                int LA129_11 = input.LA(2);

                if ( (LA129_11==35) ) {
                    int LA129_12 = input.LA(3);

                    if ( (synpred195()) ) {
                        alt129=11;
                    }
                    else if ( (true) ) {
                        alt129=12;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("622:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '=' | '>' '>' '>' '=' );", 129, 12, input);

                        throw nvae;
                    }
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("622:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '=' | '>' '>' '>' '=' );", 129, 11, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("622:1: assignmentOperator : ( '=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '<' '<' '=' | '>' '>' '=' | '>' '>' '>' '=' );", 129, 0, input);

                throw nvae;
            }

            switch (alt129) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:623:4: '='
                    {
                    match(input,44,FOLLOW_44_in_assignmentOperator2825); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:624:9: '+='
                    {
                    match(input,89,FOLLOW_89_in_assignmentOperator2835); if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:625:9: '-='
                    {
                    match(input,90,FOLLOW_90_in_assignmentOperator2845); if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:626:9: '*='
                    {
                    match(input,91,FOLLOW_91_in_assignmentOperator2855); if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:627:9: '/='
                    {
                    match(input,92,FOLLOW_92_in_assignmentOperator2865); if (failed) return ;

                    }
                    break;
                case 6 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:628:9: '&='
                    {
                    match(input,93,FOLLOW_93_in_assignmentOperator2875); if (failed) return ;

                    }
                    break;
                case 7 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:629:9: '|='
                    {
                    match(input,94,FOLLOW_94_in_assignmentOperator2885); if (failed) return ;

                    }
                    break;
                case 8 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:630:9: '^='
                    {
                    match(input,95,FOLLOW_95_in_assignmentOperator2895); if (failed) return ;

                    }
                    break;
                case 9 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:631:9: '%='
                    {
                    match(input,96,FOLLOW_96_in_assignmentOperator2905); if (failed) return ;

                    }
                    break;
                case 10 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:632:9: '<' '<' '='
                    {
                    match(input,33,FOLLOW_33_in_assignmentOperator2915); if (failed) return ;
                    match(input,33,FOLLOW_33_in_assignmentOperator2917); if (failed) return ;
                    match(input,44,FOLLOW_44_in_assignmentOperator2919); if (failed) return ;

                    }
                    break;
                case 11 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:633:9: '>' '>' '='
                    {
                    match(input,35,FOLLOW_35_in_assignmentOperator2929); if (failed) return ;
                    match(input,35,FOLLOW_35_in_assignmentOperator2931); if (failed) return ;
                    match(input,44,FOLLOW_44_in_assignmentOperator2933); if (failed) return ;

                    }
                    break;
                case 12 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:634:9: '>' '>' '>' '='
                    {
                    match(input,35,FOLLOW_35_in_assignmentOperator2943); if (failed) return ;
                    match(input,35,FOLLOW_35_in_assignmentOperator2945); if (failed) return ;
                    match(input,35,FOLLOW_35_in_assignmentOperator2947); if (failed) return ;
                    match(input,44,FOLLOW_44_in_assignmentOperator2949); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 100, assignmentOperator_StartIndex); }
        }
        return ;
    }
    // $ANTLR end assignmentOperator


    // $ANTLR start conditionalExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:637:1: conditionalExpression : conditionalOrExpression ( '?' expression ':' expression )? ;
    public final void conditionalExpression() throws RecognitionException {
        int conditionalExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 101) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:638:5: ( conditionalOrExpression ( '?' expression ':' expression )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:638:9: conditionalOrExpression ( '?' expression ':' expression )?
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalExpression2965);
            conditionalOrExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:638:33: ( '?' expression ':' expression )?
            int alt130=2;
            int LA130_0 = input.LA(1);

            if ( (LA130_0==63) ) {
                alt130=1;
            }
            switch (alt130) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:638:35: '?' expression ':' expression
                    {
                    match(input,63,FOLLOW_63_in_conditionalExpression2969); if (failed) return ;
                    pushFollow(FOLLOW_expression_in_conditionalExpression2971);
                    expression();
                    _fsp--;
                    if (failed) return ;
                    match(input,74,FOLLOW_74_in_conditionalExpression2973); if (failed) return ;
                    pushFollow(FOLLOW_expression_in_conditionalExpression2975);
                    expression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 101, conditionalExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end conditionalExpression


    // $ANTLR start conditionalOrExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:641:1: conditionalOrExpression : conditionalAndExpression ( '||' conditionalAndExpression )* ;
    public final void conditionalOrExpression() throws RecognitionException {
        int conditionalOrExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 102) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:642:5: ( conditionalAndExpression ( '||' conditionalAndExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:642:9: conditionalAndExpression ( '||' conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression2994);
            conditionalAndExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:642:34: ( '||' conditionalAndExpression )*
            loop131:
            do {
                int alt131=2;
                int LA131_0 = input.LA(1);

                if ( (LA131_0==97) ) {
                    alt131=1;
                }


                switch (alt131) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:642:36: '||' conditionalAndExpression
            	    {
            	    match(input,97,FOLLOW_97_in_conditionalOrExpression2998); if (failed) return ;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalOrExpression3000);
            	    conditionalAndExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop131;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 102, conditionalOrExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end conditionalOrExpression


    // $ANTLR start conditionalAndExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:645:1: conditionalAndExpression : inclusiveOrExpression ( '&&' inclusiveOrExpression )* ;
    public final void conditionalAndExpression() throws RecognitionException {
        int conditionalAndExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 103) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:646:5: ( inclusiveOrExpression ( '&&' inclusiveOrExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:646:9: inclusiveOrExpression ( '&&' inclusiveOrExpression )*
            {
            pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression3019);
            inclusiveOrExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:646:31: ( '&&' inclusiveOrExpression )*
            loop132:
            do {
                int alt132=2;
                int LA132_0 = input.LA(1);

                if ( (LA132_0==98) ) {
                    alt132=1;
                }


                switch (alt132) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:646:33: '&&' inclusiveOrExpression
            	    {
            	    match(input,98,FOLLOW_98_in_conditionalAndExpression3023); if (failed) return ;
            	    pushFollow(FOLLOW_inclusiveOrExpression_in_conditionalAndExpression3025);
            	    inclusiveOrExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop132;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 103, conditionalAndExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end conditionalAndExpression


    // $ANTLR start inclusiveOrExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:649:1: inclusiveOrExpression : exclusiveOrExpression ( '|' exclusiveOrExpression )* ;
    public final void inclusiveOrExpression() throws RecognitionException {
        int inclusiveOrExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 104) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:650:5: ( exclusiveOrExpression ( '|' exclusiveOrExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:650:9: exclusiveOrExpression ( '|' exclusiveOrExpression )*
            {
            pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression3044);
            exclusiveOrExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:650:31: ( '|' exclusiveOrExpression )*
            loop133:
            do {
                int alt133=2;
                int LA133_0 = input.LA(1);

                if ( (LA133_0==99) ) {
                    alt133=1;
                }


                switch (alt133) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:650:33: '|' exclusiveOrExpression
            	    {
            	    match(input,99,FOLLOW_99_in_inclusiveOrExpression3048); if (failed) return ;
            	    pushFollow(FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression3050);
            	    exclusiveOrExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop133;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 104, inclusiveOrExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end inclusiveOrExpression


    // $ANTLR start exclusiveOrExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:653:1: exclusiveOrExpression : andExpression ( '^' andExpression )* ;
    public final void exclusiveOrExpression() throws RecognitionException {
        int exclusiveOrExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 105) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:654:5: ( andExpression ( '^' andExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:654:9: andExpression ( '^' andExpression )*
            {
            pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression3069);
            andExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:654:23: ( '^' andExpression )*
            loop134:
            do {
                int alt134=2;
                int LA134_0 = input.LA(1);

                if ( (LA134_0==100) ) {
                    alt134=1;
                }


                switch (alt134) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:654:25: '^' andExpression
            	    {
            	    match(input,100,FOLLOW_100_in_exclusiveOrExpression3073); if (failed) return ;
            	    pushFollow(FOLLOW_andExpression_in_exclusiveOrExpression3075);
            	    andExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop134;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 105, exclusiveOrExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end exclusiveOrExpression


    // $ANTLR start andExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:657:1: andExpression : equalityExpression ( '&' equalityExpression )* ;
    public final void andExpression() throws RecognitionException {
        int andExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 106) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:658:5: ( equalityExpression ( '&' equalityExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:658:9: equalityExpression ( '&' equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_andExpression3094);
            equalityExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:658:28: ( '&' equalityExpression )*
            loop135:
            do {
                int alt135=2;
                int LA135_0 = input.LA(1);

                if ( (LA135_0==36) ) {
                    alt135=1;
                }


                switch (alt135) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:658:30: '&' equalityExpression
            	    {
            	    match(input,36,FOLLOW_36_in_andExpression3098); if (failed) return ;
            	    pushFollow(FOLLOW_equalityExpression_in_andExpression3100);
            	    equalityExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop135;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 106, andExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end andExpression


    // $ANTLR start equalityExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:661:1: equalityExpression : instanceOfExpression ( ( '==' | '!=' ) instanceOfExpression )* ;
    public final void equalityExpression() throws RecognitionException {
        int equalityExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 107) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:662:5: ( instanceOfExpression ( ( '==' | '!=' ) instanceOfExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:662:9: instanceOfExpression ( ( '==' | '!=' ) instanceOfExpression )*
            {
            pushFollow(FOLLOW_instanceOfExpression_in_equalityExpression3119);
            instanceOfExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:662:30: ( ( '==' | '!=' ) instanceOfExpression )*
            loop136:
            do {
                int alt136=2;
                int LA136_0 = input.LA(1);

                if ( ((LA136_0>=101 && LA136_0<=102)) ) {
                    alt136=1;
                }


                switch (alt136) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:662:32: ( '==' | '!=' ) instanceOfExpression
            	    {
            	    if ( (input.LA(1)>=101 && input.LA(1)<=102) ) {
            	        input.consume();
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return ;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_equalityExpression3123);    throw mse;
            	    }

            	    pushFollow(FOLLOW_instanceOfExpression_in_equalityExpression3131);
            	    instanceOfExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop136;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 107, equalityExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end equalityExpression


    // $ANTLR start instanceOfExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:665:1: instanceOfExpression : relationalExpression ( 'instanceof' type )? ;
    public final void instanceOfExpression() throws RecognitionException {
        int instanceOfExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 108) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:666:5: ( relationalExpression ( 'instanceof' type )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:666:9: relationalExpression ( 'instanceof' type )?
            {
            pushFollow(FOLLOW_relationalExpression_in_instanceOfExpression3150);
            relationalExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:666:30: ( 'instanceof' type )?
            int alt137=2;
            int LA137_0 = input.LA(1);

            if ( (LA137_0==103) ) {
                alt137=1;
            }
            switch (alt137) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:666:31: 'instanceof' type
                    {
                    match(input,103,FOLLOW_103_in_instanceOfExpression3153); if (failed) return ;
                    pushFollow(FOLLOW_type_in_instanceOfExpression3155);
                    type();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 108, instanceOfExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end instanceOfExpression


    // $ANTLR start relationalExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:669:1: relationalExpression : shiftExpression ( relationalOp shiftExpression )* ;
    public final void relationalExpression() throws RecognitionException {
        int relationalExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 109) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:670:5: ( shiftExpression ( relationalOp shiftExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:670:9: shiftExpression ( relationalOp shiftExpression )*
            {
            pushFollow(FOLLOW_shiftExpression_in_relationalExpression3173);
            shiftExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:670:25: ( relationalOp shiftExpression )*
            loop138:
            do {
                int alt138=2;
                int LA138_0 = input.LA(1);

                if ( (LA138_0==33) ) {
                    int LA138_23 = input.LA(2);

                    if ( (LA138_23==33) ) {
                        int LA138_27 = input.LA(3);

                        if ( (synpred205()) ) {
                            alt138=1;
                        }


                    }
                    else if ( (LA138_23==Identifier||(LA138_23>=FloatingPointLiteral && LA138_23<=DecimalLiteral)||LA138_23==40||LA138_23==44||(LA138_23>=55 && LA138_23<=62)||(LA138_23>=64 && LA138_23<=65)||(LA138_23>=68 && LA138_23<=70)||(LA138_23>=104 && LA138_23<=105)||(LA138_23>=108 && LA138_23<=113)) ) {
                        alt138=1;
                    }


                }
                else if ( (LA138_0==35) ) {
                    int LA138_24 = input.LA(2);

                    if ( (LA138_24==Identifier||(LA138_24>=FloatingPointLiteral && LA138_24<=DecimalLiteral)||LA138_24==33||LA138_24==40||LA138_24==44||(LA138_24>=55 && LA138_24<=62)||(LA138_24>=64 && LA138_24<=65)||(LA138_24>=68 && LA138_24<=70)||(LA138_24>=104 && LA138_24<=105)||(LA138_24>=108 && LA138_24<=113)) ) {
                        alt138=1;
                    }


                }


                switch (alt138) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:670:27: relationalOp shiftExpression
            	    {
            	    pushFollow(FOLLOW_relationalOp_in_relationalExpression3177);
            	    relationalOp();
            	    _fsp--;
            	    if (failed) return ;
            	    pushFollow(FOLLOW_shiftExpression_in_relationalExpression3179);
            	    shiftExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop138;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 109, relationalExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end relationalExpression


    // $ANTLR start relationalOp
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:673:1: relationalOp : ( '<' '=' | '>' '=' | '<' | '>' ) ;
    public final void relationalOp() throws RecognitionException {
        int relationalOp_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 110) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:674:2: ( ( '<' '=' | '>' '=' | '<' | '>' ) )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:674:4: ( '<' '=' | '>' '=' | '<' | '>' )
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:674:4: ( '<' '=' | '>' '=' | '<' | '>' )
            int alt139=4;
            int LA139_0 = input.LA(1);

            if ( (LA139_0==33) ) {
                int LA139_1 = input.LA(2);

                if ( (LA139_1==44) ) {
                    alt139=1;
                }
                else if ( (LA139_1==Identifier||(LA139_1>=FloatingPointLiteral && LA139_1<=DecimalLiteral)||LA139_1==33||LA139_1==40||(LA139_1>=55 && LA139_1<=62)||(LA139_1>=64 && LA139_1<=65)||(LA139_1>=68 && LA139_1<=70)||(LA139_1>=104 && LA139_1<=105)||(LA139_1>=108 && LA139_1<=113)) ) {
                    alt139=3;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("674:4: ( '<' '=' | '>' '=' | '<' | '>' )", 139, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA139_0==35) ) {
                int LA139_2 = input.LA(2);

                if ( (LA139_2==44) ) {
                    alt139=2;
                }
                else if ( (LA139_2==Identifier||(LA139_2>=FloatingPointLiteral && LA139_2<=DecimalLiteral)||LA139_2==33||LA139_2==40||(LA139_2>=55 && LA139_2<=62)||(LA139_2>=64 && LA139_2<=65)||(LA139_2>=68 && LA139_2<=70)||(LA139_2>=104 && LA139_2<=105)||(LA139_2>=108 && LA139_2<=113)) ) {
                    alt139=4;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("674:4: ( '<' '=' | '>' '=' | '<' | '>' )", 139, 2, input);

                    throw nvae;
                }
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("674:4: ( '<' '=' | '>' '=' | '<' | '>' )", 139, 0, input);

                throw nvae;
            }
            switch (alt139) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:674:5: '<' '='
                    {
                    match(input,33,FOLLOW_33_in_relationalOp3195); if (failed) return ;
                    match(input,44,FOLLOW_44_in_relationalOp3197); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:674:15: '>' '='
                    {
                    match(input,35,FOLLOW_35_in_relationalOp3201); if (failed) return ;
                    match(input,44,FOLLOW_44_in_relationalOp3203); if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:674:25: '<'
                    {
                    match(input,33,FOLLOW_33_in_relationalOp3207); if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:674:31: '>'
                    {
                    match(input,35,FOLLOW_35_in_relationalOp3211); if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 110, relationalOp_StartIndex); }
        }
        return ;
    }
    // $ANTLR end relationalOp


    // $ANTLR start shiftExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:677:1: shiftExpression : additiveExpression ( shiftOp additiveExpression )* ;
    public final void shiftExpression() throws RecognitionException {
        int shiftExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 111) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:678:5: ( additiveExpression ( shiftOp additiveExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:678:9: additiveExpression ( shiftOp additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_shiftExpression3228);
            additiveExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:678:28: ( shiftOp additiveExpression )*
            loop140:
            do {
                int alt140=2;
                int LA140_0 = input.LA(1);

                if ( (LA140_0==33) ) {
                    int LA140_1 = input.LA(2);

                    if ( (LA140_1==33) ) {
                        int LA140_27 = input.LA(3);

                        if ( (synpred209()) ) {
                            alt140=1;
                        }


                    }


                }
                else if ( (LA140_0==35) ) {
                    int LA140_2 = input.LA(2);

                    if ( (LA140_2==35) ) {
                        int LA140_48 = input.LA(3);

                        if ( (synpred209()) ) {
                            alt140=1;
                        }


                    }


                }


                switch (alt140) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:678:30: shiftOp additiveExpression
            	    {
            	    pushFollow(FOLLOW_shiftOp_in_shiftExpression3232);
            	    shiftOp();
            	    _fsp--;
            	    if (failed) return ;
            	    pushFollow(FOLLOW_additiveExpression_in_shiftExpression3234);
            	    additiveExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop140;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 111, shiftExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end shiftExpression


    // $ANTLR start shiftOp
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:682:1: shiftOp : ( '<' '<' | '>' '>' '>' | '>' '>' ) ;
    public final void shiftOp() throws RecognitionException {
        int shiftOp_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 112) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:683:2: ( ( '<' '<' | '>' '>' '>' | '>' '>' ) )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:683:4: ( '<' '<' | '>' '>' '>' | '>' '>' )
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:683:4: ( '<' '<' | '>' '>' '>' | '>' '>' )
            int alt141=3;
            int LA141_0 = input.LA(1);

            if ( (LA141_0==33) ) {
                alt141=1;
            }
            else if ( (LA141_0==35) ) {
                int LA141_2 = input.LA(2);

                if ( (LA141_2==35) ) {
                    int LA141_3 = input.LA(3);

                    if ( (synpred211()) ) {
                        alt141=2;
                    }
                    else if ( (true) ) {
                        alt141=3;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("683:4: ( '<' '<' | '>' '>' '>' | '>' '>' )", 141, 3, input);

                        throw nvae;
                    }
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("683:4: ( '<' '<' | '>' '>' '>' | '>' '>' )", 141, 2, input);

                    throw nvae;
                }
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("683:4: ( '<' '<' | '>' '>' '>' | '>' '>' )", 141, 0, input);

                throw nvae;
            }
            switch (alt141) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:683:5: '<' '<'
                    {
                    match(input,33,FOLLOW_33_in_shiftOp3258); if (failed) return ;
                    match(input,33,FOLLOW_33_in_shiftOp3260); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:683:15: '>' '>' '>'
                    {
                    match(input,35,FOLLOW_35_in_shiftOp3264); if (failed) return ;
                    match(input,35,FOLLOW_35_in_shiftOp3266); if (failed) return ;
                    match(input,35,FOLLOW_35_in_shiftOp3268); if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:683:29: '>' '>'
                    {
                    match(input,35,FOLLOW_35_in_shiftOp3272); if (failed) return ;
                    match(input,35,FOLLOW_35_in_shiftOp3274); if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 112, shiftOp_StartIndex); }
        }
        return ;
    }
    // $ANTLR end shiftOp


    // $ANTLR start additiveExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:687:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
    public final void additiveExpression() throws RecognitionException {
        int additiveExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 113) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:688:5: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:688:9: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3292);
            multiplicativeExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:688:34: ( ( '+' | '-' ) multiplicativeExpression )*
            loop142:
            do {
                int alt142=2;
                int LA142_0 = input.LA(1);

                if ( ((LA142_0>=104 && LA142_0<=105)) ) {
                    alt142=1;
                }


                switch (alt142) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:688:36: ( '+' | '-' ) multiplicativeExpression
            	    {
            	    if ( (input.LA(1)>=104 && input.LA(1)<=105) ) {
            	        input.consume();
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return ;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_additiveExpression3296);    throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3304);
            	    multiplicativeExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop142;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 113, additiveExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end additiveExpression


    // $ANTLR start multiplicativeExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:691:1: multiplicativeExpression : unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* ;
    public final void multiplicativeExpression() throws RecognitionException {
        int multiplicativeExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 114) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:692:5: ( unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:692:9: unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3323);
            unaryExpression();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:692:25: ( ( '*' | '/' | '%' ) unaryExpression )*
            loop143:
            do {
                int alt143=2;
                int LA143_0 = input.LA(1);

                if ( (LA143_0==29||(LA143_0>=106 && LA143_0<=107)) ) {
                    alt143=1;
                }


                switch (alt143) {
            	case 1 :
            	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:692:27: ( '*' | '/' | '%' ) unaryExpression
            	    {
            	    if ( input.LA(1)==29||(input.LA(1)>=106 && input.LA(1)<=107) ) {
            	        input.consume();
            	        errorRecovery=false;failed=false;
            	    }
            	    else {
            	        if (backtracking>0) {failed=true; return ;}
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recoverFromMismatchedSet(input,mse,FOLLOW_set_in_multiplicativeExpression3327);    throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3341);
            	    unaryExpression();
            	    _fsp--;
            	    if (failed) return ;

            	    }
            	    break;

            	default :
            	    break loop143;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 114, multiplicativeExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end multiplicativeExpression


    // $ANTLR start unaryExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:695:1: unaryExpression : ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus );
    public final void unaryExpression() throws RecognitionException {
        int unaryExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 115) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:696:5: ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus )
            int alt144=5;
            switch ( input.LA(1) ) {
            case 104:
                {
                alt144=1;
                }
                break;
            case 105:
                {
                alt144=2;
                }
                break;
            case 108:
                {
                alt144=3;
                }
                break;
            case 109:
                {
                alt144=4;
                }
                break;
            case Identifier:
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 33:
            case 40:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 64:
            case 65:
            case 68:
            case 69:
            case 70:
            case 110:
            case 111:
            case 112:
            case 113:
                {
                alt144=5;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("695:1: unaryExpression : ( '+' unaryExpression | '-' unaryExpression | '++' unaryExpression | '--' unaryExpression | unaryExpressionNotPlusMinus );", 144, 0, input);

                throw nvae;
            }

            switch (alt144) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:696:9: '+' unaryExpression
                    {
                    match(input,104,FOLLOW_104_in_unaryExpression3361); if (failed) return ;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3363);
                    unaryExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:697:7: '-' unaryExpression
                    {
                    match(input,105,FOLLOW_105_in_unaryExpression3371); if (failed) return ;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3373);
                    unaryExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:698:9: '++' unaryExpression
                    {
                    match(input,108,FOLLOW_108_in_unaryExpression3383); if (failed) return ;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3385);
                    unaryExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:699:9: '--' unaryExpression
                    {
                    match(input,109,FOLLOW_109_in_unaryExpression3395); if (failed) return ;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3397);
                    unaryExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:700:9: unaryExpressionNotPlusMinus
                    {
                    pushFollow(FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression3407);
                    unaryExpressionNotPlusMinus();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 115, unaryExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end unaryExpression


    // $ANTLR start unaryExpressionNotPlusMinus
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );
    public final void unaryExpressionNotPlusMinus() throws RecognitionException {
        int unaryExpressionNotPlusMinus_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 116) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:704:5: ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? )
            int alt147=4;
            switch ( input.LA(1) ) {
            case 110:
                {
                alt147=1;
                }
                break;
            case 111:
                {
                alt147=2;
                }
                break;
            case 65:
                {
                switch ( input.LA(2) ) {
                case 104:
                    {
                    int LA147_17 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 17, input);

                        throw nvae;
                    }
                    }
                    break;
                case 105:
                    {
                    int LA147_18 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 18, input);

                        throw nvae;
                    }
                    }
                    break;
                case 108:
                    {
                    int LA147_19 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 19, input);

                        throw nvae;
                    }
                    }
                    break;
                case 109:
                    {
                    int LA147_20 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 20, input);

                        throw nvae;
                    }
                    }
                    break;
                case 110:
                    {
                    int LA147_21 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 21, input);

                        throw nvae;
                    }
                    }
                    break;
                case 111:
                    {
                    int LA147_22 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 22, input);

                        throw nvae;
                    }
                    }
                    break;
                case 65:
                    {
                    int LA147_23 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 23, input);

                        throw nvae;
                    }
                    }
                    break;
                case 33:
                    {
                    int LA147_24 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 24, input);

                        throw nvae;
                    }
                    }
                    break;
                case 112:
                    {
                    int LA147_25 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 25, input);

                        throw nvae;
                    }
                    }
                    break;
                case 64:
                    {
                    int LA147_26 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 26, input);

                        throw nvae;
                    }
                    }
                    break;
                case HexLiteral:
                case OctalLiteral:
                case DecimalLiteral:
                    {
                    int LA147_27 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 27, input);

                        throw nvae;
                    }
                    }
                    break;
                case FloatingPointLiteral:
                    {
                    int LA147_28 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 28, input);

                        throw nvae;
                    }
                    }
                    break;
                case CharacterLiteral:
                    {
                    int LA147_29 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 29, input);

                        throw nvae;
                    }
                    }
                    break;
                case StringLiteral:
                    {
                    int LA147_30 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 30, input);

                        throw nvae;
                    }
                    }
                    break;
                case 69:
                case 70:
                    {
                    int LA147_31 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 31, input);

                        throw nvae;
                    }
                    }
                    break;
                case 68:
                    {
                    int LA147_32 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 32, input);

                        throw nvae;
                    }
                    }
                    break;
                case 113:
                    {
                    int LA147_33 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 33, input);

                        throw nvae;
                    }
                    }
                    break;
                case Identifier:
                    {
                    int LA147_34 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 34, input);

                        throw nvae;
                    }
                    }
                    break;
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                    {
                    int LA147_35 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 35, input);

                        throw nvae;
                    }
                    }
                    break;
                case 40:
                    {
                    int LA147_36 = input.LA(3);

                    if ( (synpred223()) ) {
                        alt147=3;
                    }
                    else if ( (true) ) {
                        alt147=4;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 36, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 3, input);

                    throw nvae;
                }

                }
                break;
            case Identifier:
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 33:
            case 40:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 64:
            case 68:
            case 69:
            case 70:
            case 112:
            case 113:
                {
                alt147=4;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("703:1: unaryExpressionNotPlusMinus : ( '~' unaryExpression | '!' unaryExpression | castExpression | primary ( selector )* ( '++' | '--' )? );", 147, 0, input);

                throw nvae;
            }

            switch (alt147) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:704:9: '~' unaryExpression
                    {
                    match(input,110,FOLLOW_110_in_unaryExpressionNotPlusMinus3426); if (failed) return ;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus3428);
                    unaryExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:705:8: '!' unaryExpression
                    {
                    match(input,111,FOLLOW_111_in_unaryExpressionNotPlusMinus3437); if (failed) return ;
                    pushFollow(FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus3439);
                    unaryExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:706:9: castExpression
                    {
                    pushFollow(FOLLOW_castExpression_in_unaryExpressionNotPlusMinus3449);
                    castExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:707:9: primary ( selector )* ( '++' | '--' )?
                    {
                    pushFollow(FOLLOW_primary_in_unaryExpressionNotPlusMinus3459);
                    primary();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:707:17: ( selector )*
                    loop145:
                    do {
                        int alt145=2;
                        int LA145_0 = input.LA(1);

                        if ( (LA145_0==28||LA145_0==41) ) {
                            alt145=1;
                        }


                        switch (alt145) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: selector
                    	    {
                    	    pushFollow(FOLLOW_selector_in_unaryExpressionNotPlusMinus3461);
                    	    selector();
                    	    _fsp--;
                    	    if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop145;
                        }
                    } while (true);

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:707:27: ( '++' | '--' )?
                    int alt146=2;
                    int LA146_0 = input.LA(1);

                    if ( ((LA146_0>=108 && LA146_0<=109)) ) {
                        alt146=1;
                    }
                    switch (alt146) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:
                            {
                            if ( (input.LA(1)>=108 && input.LA(1)<=109) ) {
                                input.consume();
                                errorRecovery=false;failed=false;
                            }
                            else {
                                if (backtracking>0) {failed=true; return ;}
                                MismatchedSetException mse =
                                    new MismatchedSetException(null,input);
                                recoverFromMismatchedSet(input,mse,FOLLOW_set_in_unaryExpressionNotPlusMinus3464);    throw mse;
                            }


                            }
                            break;

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
            if ( backtracking>0 ) { memoize(input, 116, unaryExpressionNotPlusMinus_StartIndex); }
        }
        return ;
    }
    // $ANTLR end unaryExpressionNotPlusMinus


    // $ANTLR start castExpression
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:710:1: castExpression : ( '(' primitiveType ')' unaryExpression | '(' ( type | expression ) ')' unaryExpressionNotPlusMinus );
    public final void castExpression() throws RecognitionException {
        int castExpression_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 117) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:711:5: ( '(' primitiveType ')' unaryExpression | '(' ( type | expression ) ')' unaryExpressionNotPlusMinus )
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==65) ) {
                int LA149_1 = input.LA(2);

                if ( ((LA149_1>=55 && LA149_1<=62)) ) {
                    int LA149_2 = input.LA(3);

                    if ( (synpred227()) ) {
                        alt149=1;
                    }
                    else if ( (true) ) {
                        alt149=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("710:1: castExpression : ( '(' primitiveType ')' unaryExpression | '(' ( type | expression ) ')' unaryExpressionNotPlusMinus );", 149, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA149_1==Identifier||(LA149_1>=FloatingPointLiteral && LA149_1<=DecimalLiteral)||LA149_1==33||LA149_1==40||(LA149_1>=64 && LA149_1<=65)||(LA149_1>=68 && LA149_1<=70)||(LA149_1>=104 && LA149_1<=105)||(LA149_1>=108 && LA149_1<=113)) ) {
                    alt149=2;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("710:1: castExpression : ( '(' primitiveType ')' unaryExpression | '(' ( type | expression ) ')' unaryExpressionNotPlusMinus );", 149, 1, input);

                    throw nvae;
                }
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("710:1: castExpression : ( '(' primitiveType ')' unaryExpression | '(' ( type | expression ) ')' unaryExpressionNotPlusMinus );", 149, 0, input);

                throw nvae;
            }
            switch (alt149) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:711:8: '(' primitiveType ')' unaryExpression
                    {
                    match(input,65,FOLLOW_65_in_castExpression3487); if (failed) return ;
                    pushFollow(FOLLOW_primitiveType_in_castExpression3489);
                    primitiveType();
                    _fsp--;
                    if (failed) return ;
                    match(input,66,FOLLOW_66_in_castExpression3491); if (failed) return ;
                    pushFollow(FOLLOW_unaryExpression_in_castExpression3493);
                    unaryExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:712:8: '(' ( type | expression ) ')' unaryExpressionNotPlusMinus
                    {
                    match(input,65,FOLLOW_65_in_castExpression3502); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:712:12: ( type | expression )
                    int alt148=2;
                    switch ( input.LA(1) ) {
                    case Identifier:
                        {
                        int LA148_1 = input.LA(2);

                        if ( (synpred228()) ) {
                            alt148=1;
                        }
                        else if ( (true) ) {
                            alt148=2;
                        }
                        else {
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("712:12: ( type | expression )", 148, 1, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                        {
                        switch ( input.LA(2) ) {
                        case 41:
                            {
                            int LA148_48 = input.LA(3);

                            if ( (synpred228()) ) {
                                alt148=1;
                            }
                            else if ( (true) ) {
                                alt148=2;
                            }
                            else {
                                if (backtracking>0) {failed=true; return ;}
                                NoViableAltException nvae =
                                    new NoViableAltException("712:12: ( type | expression )", 148, 48, input);

                                throw nvae;
                            }
                            }
                            break;
                        case 66:
                            {
                            alt148=1;
                            }
                            break;
                        case 28:
                            {
                            alt148=2;
                            }
                            break;
                        default:
                            if (backtracking>0) {failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("712:12: ( type | expression )", 148, 2, input);

                            throw nvae;
                        }

                        }
                        break;
                    case FloatingPointLiteral:
                    case CharacterLiteral:
                    case StringLiteral:
                    case HexLiteral:
                    case OctalLiteral:
                    case DecimalLiteral:
                    case 33:
                    case 40:
                    case 64:
                    case 65:
                    case 68:
                    case 69:
                    case 70:
                    case 104:
                    case 105:
                    case 108:
                    case 109:
                    case 110:
                    case 111:
                    case 112:
                    case 113:
                        {
                        alt148=2;
                        }
                        break;
                    default:
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("712:12: ( type | expression )", 148, 0, input);

                        throw nvae;
                    }

                    switch (alt148) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:712:13: type
                            {
                            pushFollow(FOLLOW_type_in_castExpression3505);
                            type();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;
                        case 2 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:712:20: expression
                            {
                            pushFollow(FOLLOW_expression_in_castExpression3509);
                            expression();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    match(input,66,FOLLOW_66_in_castExpression3512); if (failed) return ;
                    pushFollow(FOLLOW_unaryExpressionNotPlusMinus_in_castExpression3514);
                    unaryExpressionNotPlusMinus();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 117, castExpression_StartIndex); }
        }
        return ;
    }
    // $ANTLR end castExpression


    // $ANTLR start primary
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:715:1: primary : ( parExpression | nonWildcardTypeArguments ( explicitGenericInvocationSuffix | 'this' arguments ) | 'this' ( '.' Identifier )* ( identifierSuffix )? | 'super' superSuffix | literal | 'new' creator | Identifier ( '.' Identifier )* ( identifierSuffix )? | primitiveType ( '[' ']' )* '.' 'class' | 'void' '.' 'class' );
    public final void primary() throws RecognitionException {
        int primary_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 118) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:716:5: ( parExpression | nonWildcardTypeArguments ( explicitGenericInvocationSuffix | 'this' arguments ) | 'this' ( '.' Identifier )* ( identifierSuffix )? | 'super' superSuffix | literal | 'new' creator | Identifier ( '.' Identifier )* ( identifierSuffix )? | primitiveType ( '[' ']' )* '.' 'class' | 'void' '.' 'class' )
            int alt156=9;
            switch ( input.LA(1) ) {
            case 65:
                {
                alt156=1;
                }
                break;
            case 33:
                {
                alt156=2;
                }
                break;
            case 112:
                {
                alt156=3;
                }
                break;
            case 64:
                {
                alt156=4;
                }
                break;
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case HexLiteral:
            case OctalLiteral:
            case DecimalLiteral:
            case 68:
            case 69:
            case 70:
                {
                alt156=5;
                }
                break;
            case 113:
                {
                alt156=6;
                }
                break;
            case Identifier:
                {
                alt156=7;
                }
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
                {
                alt156=8;
                }
                break;
            case 40:
                {
                alt156=9;
                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("715:1: primary : ( parExpression | nonWildcardTypeArguments ( explicitGenericInvocationSuffix | 'this' arguments ) | 'this' ( '.' Identifier )* ( identifierSuffix )? | 'super' superSuffix | literal | 'new' creator | Identifier ( '.' Identifier )* ( identifierSuffix )? | primitiveType ( '[' ']' )* '.' 'class' | 'void' '.' 'class' );", 156, 0, input);

                throw nvae;
            }

            switch (alt156) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:716:7: parExpression
                    {
                    pushFollow(FOLLOW_parExpression_in_primary3531);
                    parExpression();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:717:9: nonWildcardTypeArguments ( explicitGenericInvocationSuffix | 'this' arguments )
                    {
                    pushFollow(FOLLOW_nonWildcardTypeArguments_in_primary3541);
                    nonWildcardTypeArguments();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:718:9: ( explicitGenericInvocationSuffix | 'this' arguments )
                    int alt150=2;
                    int LA150_0 = input.LA(1);

                    if ( (LA150_0==Identifier||LA150_0==64) ) {
                        alt150=1;
                    }
                    else if ( (LA150_0==112) ) {
                        alt150=2;
                    }
                    else {
                        if (backtracking>0) {failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("718:9: ( explicitGenericInvocationSuffix | 'this' arguments )", 150, 0, input);

                        throw nvae;
                    }
                    switch (alt150) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:718:10: explicitGenericInvocationSuffix
                            {
                            pushFollow(FOLLOW_explicitGenericInvocationSuffix_in_primary3552);
                            explicitGenericInvocationSuffix();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;
                        case 2 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:718:44: 'this' arguments
                            {
                            match(input,112,FOLLOW_112_in_primary3556); if (failed) return ;
                            pushFollow(FOLLOW_arguments_in_primary3558);
                            arguments();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:9: 'this' ( '.' Identifier )* ( identifierSuffix )?
                    {
                    match(input,112,FOLLOW_112_in_primary3569); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:16: ( '.' Identifier )*
                    loop151:
                    do {
                        int alt151=2;
                        int LA151_0 = input.LA(1);

                        if ( (LA151_0==28) ) {
                            int LA151_3 = input.LA(2);

                            if ( (LA151_3==Identifier) ) {
                                int LA151_33 = input.LA(3);

                                if ( (synpred232()) ) {
                                    alt151=1;
                                }


                            }


                        }


                        switch (alt151) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:17: '.' Identifier
                    	    {
                    	    match(input,28,FOLLOW_28_in_primary3572); if (failed) return ;
                    	    match(input,Identifier,FOLLOW_Identifier_in_primary3574); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop151;
                        }
                    } while (true);

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:34: ( identifierSuffix )?
                    int alt152=2;
                    switch ( input.LA(1) ) {
                        case 41:
                            {
                            switch ( input.LA(2) ) {
                                case 42:
                                    {
                                    alt152=1;
                                    }
                                    break;
                                case 104:
                                    {
                                    int LA152_34 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 105:
                                    {
                                    int LA152_35 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 108:
                                    {
                                    int LA152_36 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 109:
                                    {
                                    int LA152_37 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 110:
                                    {
                                    int LA152_38 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 111:
                                    {
                                    int LA152_39 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 65:
                                    {
                                    int LA152_40 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 33:
                                    {
                                    int LA152_41 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 112:
                                    {
                                    int LA152_42 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 64:
                                    {
                                    int LA152_43 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case HexLiteral:
                                case OctalLiteral:
                                case DecimalLiteral:
                                    {
                                    int LA152_44 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case FloatingPointLiteral:
                                    {
                                    int LA152_45 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case CharacterLiteral:
                                    {
                                    int LA152_46 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case StringLiteral:
                                    {
                                    int LA152_47 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 69:
                                case 70:
                                    {
                                    int LA152_48 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 68:
                                    {
                                    int LA152_49 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 113:
                                    {
                                    int LA152_50 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case Identifier:
                                    {
                                    int LA152_51 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 55:
                                case 56:
                                case 57:
                                case 58:
                                case 59:
                                case 60:
                                case 61:
                                case 62:
                                    {
                                    int LA152_52 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 40:
                                    {
                                    int LA152_53 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                            }

                            }
                            break;
                        case 65:
                            {
                            alt152=1;
                            }
                            break;
                        case 28:
                            {
                            switch ( input.LA(2) ) {
                                case 112:
                                    {
                                    int LA152_55 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 64:
                                    {
                                    int LA152_56 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 113:
                                    {
                                    int LA152_57 = input.LA(3);

                                    if ( (synpred233()) ) {
                                        alt152=1;
                                    }
                                    }
                                    break;
                                case 30:
                                case 33:
                                    {
                                    alt152=1;
                                    }
                                    break;
                            }

                            }
                            break;
                    }

                    switch (alt152) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:35: identifierSuffix
                            {
                            pushFollow(FOLLOW_identifierSuffix_in_primary3579);
                            identifierSuffix();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:720:9: 'super' superSuffix
                    {
                    match(input,64,FOLLOW_64_in_primary3591); if (failed) return ;
                    pushFollow(FOLLOW_superSuffix_in_primary3593);
                    superSuffix();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:721:9: literal
                    {
                    pushFollow(FOLLOW_literal_in_primary3603);
                    literal();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 6 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:722:9: 'new' creator
                    {
                    match(input,113,FOLLOW_113_in_primary3613); if (failed) return ;
                    pushFollow(FOLLOW_creator_in_primary3615);
                    creator();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 7 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:9: Identifier ( '.' Identifier )* ( identifierSuffix )?
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_primary3625); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:20: ( '.' Identifier )*
                    loop153:
                    do {
                        int alt153=2;
                        int LA153_0 = input.LA(1);

                        if ( (LA153_0==28) ) {
                            int LA153_3 = input.LA(2);

                            if ( (LA153_3==Identifier) ) {
                                int LA153_33 = input.LA(3);

                                if ( (synpred238()) ) {
                                    alt153=1;
                                }


                            }


                        }


                        switch (alt153) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:21: '.' Identifier
                    	    {
                    	    match(input,28,FOLLOW_28_in_primary3628); if (failed) return ;
                    	    match(input,Identifier,FOLLOW_Identifier_in_primary3630); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop153;
                        }
                    } while (true);

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:38: ( identifierSuffix )?
                    int alt154=2;
                    switch ( input.LA(1) ) {
                        case 41:
                            {
                            switch ( input.LA(2) ) {
                                case 42:
                                    {
                                    alt154=1;
                                    }
                                    break;
                                case 104:
                                    {
                                    int LA154_34 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 105:
                                    {
                                    int LA154_35 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 108:
                                    {
                                    int LA154_36 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 109:
                                    {
                                    int LA154_37 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 110:
                                    {
                                    int LA154_38 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 111:
                                    {
                                    int LA154_39 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 65:
                                    {
                                    int LA154_40 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 33:
                                    {
                                    int LA154_41 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 112:
                                    {
                                    int LA154_42 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 64:
                                    {
                                    int LA154_43 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case HexLiteral:
                                case OctalLiteral:
                                case DecimalLiteral:
                                    {
                                    int LA154_44 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case FloatingPointLiteral:
                                    {
                                    int LA154_45 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case CharacterLiteral:
                                    {
                                    int LA154_46 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case StringLiteral:
                                    {
                                    int LA154_47 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 69:
                                case 70:
                                    {
                                    int LA154_48 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 68:
                                    {
                                    int LA154_49 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 113:
                                    {
                                    int LA154_50 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case Identifier:
                                    {
                                    int LA154_51 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 55:
                                case 56:
                                case 57:
                                case 58:
                                case 59:
                                case 60:
                                case 61:
                                case 62:
                                    {
                                    int LA154_52 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 40:
                                    {
                                    int LA154_53 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                            }

                            }
                            break;
                        case 65:
                            {
                            alt154=1;
                            }
                            break;
                        case 28:
                            {
                            switch ( input.LA(2) ) {
                                case 112:
                                    {
                                    int LA154_55 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 64:
                                    {
                                    int LA154_56 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 113:
                                    {
                                    int LA154_57 = input.LA(3);

                                    if ( (synpred239()) ) {
                                        alt154=1;
                                    }
                                    }
                                    break;
                                case 30:
                                case 33:
                                    {
                                    alt154=1;
                                    }
                                    break;
                            }

                            }
                            break;
                    }

                    switch (alt154) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:39: identifierSuffix
                            {
                            pushFollow(FOLLOW_identifierSuffix_in_primary3635);
                            identifierSuffix();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 8 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:724:9: primitiveType ( '[' ']' )* '.' 'class'
                    {
                    pushFollow(FOLLOW_primitiveType_in_primary3647);
                    primitiveType();
                    _fsp--;
                    if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:724:23: ( '[' ']' )*
                    loop155:
                    do {
                        int alt155=2;
                        int LA155_0 = input.LA(1);

                        if ( (LA155_0==41) ) {
                            alt155=1;
                        }


                        switch (alt155) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:724:24: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_primary3650); if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_primary3652); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop155;
                        }
                    } while (true);

                    match(input,28,FOLLOW_28_in_primary3656); if (failed) return ;
                    match(input,30,FOLLOW_30_in_primary3658); if (failed) return ;

                    }
                    break;
                case 9 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:725:9: 'void' '.' 'class'
                    {
                    match(input,40,FOLLOW_40_in_primary3668); if (failed) return ;
                    match(input,28,FOLLOW_28_in_primary3670); if (failed) return ;
                    match(input,30,FOLLOW_30_in_primary3672); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 118, primary_StartIndex); }
        }
        return ;
    }
    // $ANTLR end primary


    // $ANTLR start identifierSuffix
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:728:1: identifierSuffix : ( ( '[' ']' )+ '.' 'class' | ( '[' expression ']' )+ | arguments | '.' 'class' | '.' explicitGenericInvocation | '.' 'this' | '.' 'super' arguments | '.' 'new' ( nonWildcardTypeArguments )? innerCreator );
    public final void identifierSuffix() throws RecognitionException {
        int identifierSuffix_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 119) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:729:2: ( ( '[' ']' )+ '.' 'class' | ( '[' expression ']' )+ | arguments | '.' 'class' | '.' explicitGenericInvocation | '.' 'this' | '.' 'super' arguments | '.' 'new' ( nonWildcardTypeArguments )? innerCreator )
            int alt160=8;
            switch ( input.LA(1) ) {
            case 41:
                {
                int LA160_1 = input.LA(2);

                if ( (LA160_1==42) ) {
                    alt160=1;
                }
                else if ( (LA160_1==Identifier||(LA160_1>=FloatingPointLiteral && LA160_1<=DecimalLiteral)||LA160_1==33||LA160_1==40||(LA160_1>=55 && LA160_1<=62)||(LA160_1>=64 && LA160_1<=65)||(LA160_1>=68 && LA160_1<=70)||(LA160_1>=104 && LA160_1<=105)||(LA160_1>=108 && LA160_1<=113)) ) {
                    alt160=2;
                }
                else {
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("728:1: identifierSuffix : ( ( '[' ']' )+ '.' 'class' | ( '[' expression ']' )+ | arguments | '.' 'class' | '.' explicitGenericInvocation | '.' 'this' | '.' 'super' arguments | '.' 'new' ( nonWildcardTypeArguments )? innerCreator );", 160, 1, input);

                    throw nvae;
                }
                }
                break;
            case 65:
                {
                alt160=3;
                }
                break;
            case 28:
                {
                switch ( input.LA(2) ) {
                case 30:
                    {
                    alt160=4;
                    }
                    break;
                case 112:
                    {
                    alt160=6;
                    }
                    break;
                case 64:
                    {
                    alt160=7;
                    }
                    break;
                case 113:
                    {
                    alt160=8;
                    }
                    break;
                case 33:
                    {
                    alt160=5;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("728:1: identifierSuffix : ( ( '[' ']' )+ '.' 'class' | ( '[' expression ']' )+ | arguments | '.' 'class' | '.' explicitGenericInvocation | '.' 'this' | '.' 'super' arguments | '.' 'new' ( nonWildcardTypeArguments )? innerCreator );", 160, 3, input);

                    throw nvae;
                }

                }
                break;
            default:
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("728:1: identifierSuffix : ( ( '[' ']' )+ '.' 'class' | ( '[' expression ']' )+ | arguments | '.' 'class' | '.' explicitGenericInvocation | '.' 'this' | '.' 'super' arguments | '.' 'new' ( nonWildcardTypeArguments )? innerCreator );", 160, 0, input);

                throw nvae;
            }

            switch (alt160) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:729:4: ( '[' ']' )+ '.' 'class'
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:729:4: ( '[' ']' )+
                    int cnt157=0;
                    loop157:
                    do {
                        int alt157=2;
                        int LA157_0 = input.LA(1);

                        if ( (LA157_0==41) ) {
                            alt157=1;
                        }


                        switch (alt157) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:729:5: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_identifierSuffix3684); if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_identifierSuffix3686); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt157 >= 1 ) break loop157;
                    	    if (backtracking>0) {failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(157, input);
                                throw eee;
                        }
                        cnt157++;
                    } while (true);

                    match(input,28,FOLLOW_28_in_identifierSuffix3690); if (failed) return ;
                    match(input,30,FOLLOW_30_in_identifierSuffix3692); if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:730:4: ( '[' expression ']' )+
                    {
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:730:4: ( '[' expression ']' )+
                    int cnt158=0;
                    loop158:
                    do {
                        int alt158=2;
                        int LA158_0 = input.LA(1);

                        if ( (LA158_0==41) ) {
                            switch ( input.LA(2) ) {
                            case 104:
                                {
                                int LA158_32 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 105:
                                {
                                int LA158_33 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 108:
                                {
                                int LA158_34 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 109:
                                {
                                int LA158_35 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 110:
                                {
                                int LA158_36 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 111:
                                {
                                int LA158_37 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 65:
                                {
                                int LA158_38 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 33:
                                {
                                int LA158_39 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 112:
                                {
                                int LA158_40 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 64:
                                {
                                int LA158_41 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case HexLiteral:
                            case OctalLiteral:
                            case DecimalLiteral:
                                {
                                int LA158_42 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case FloatingPointLiteral:
                                {
                                int LA158_43 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case CharacterLiteral:
                                {
                                int LA158_44 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case StringLiteral:
                                {
                                int LA158_45 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 69:
                            case 70:
                                {
                                int LA158_46 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 68:
                                {
                                int LA158_47 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 113:
                                {
                                int LA158_48 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case Identifier:
                                {
                                int LA158_49 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                            case 59:
                            case 60:
                            case 61:
                            case 62:
                                {
                                int LA158_50 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;
                            case 40:
                                {
                                int LA158_51 = input.LA(3);

                                if ( (synpred245()) ) {
                                    alt158=1;
                                }


                                }
                                break;

                            }

                        }


                        switch (alt158) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:730:5: '[' expression ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_identifierSuffix3698); if (failed) return ;
                    	    pushFollow(FOLLOW_expression_in_identifierSuffix3700);
                    	    expression();
                    	    _fsp--;
                    	    if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_identifierSuffix3702); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt158 >= 1 ) break loop158;
                    	    if (backtracking>0) {failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(158, input);
                                throw eee;
                        }
                        cnt158++;
                    } while (true);


                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:731:9: arguments
                    {
                    pushFollow(FOLLOW_arguments_in_identifierSuffix3715);
                    arguments();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:732:9: '.' 'class'
                    {
                    match(input,28,FOLLOW_28_in_identifierSuffix3725); if (failed) return ;
                    match(input,30,FOLLOW_30_in_identifierSuffix3727); if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:733:9: '.' explicitGenericInvocation
                    {
                    match(input,28,FOLLOW_28_in_identifierSuffix3737); if (failed) return ;
                    pushFollow(FOLLOW_explicitGenericInvocation_in_identifierSuffix3739);
                    explicitGenericInvocation();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 6 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:734:9: '.' 'this'
                    {
                    match(input,28,FOLLOW_28_in_identifierSuffix3749); if (failed) return ;
                    match(input,112,FOLLOW_112_in_identifierSuffix3751); if (failed) return ;

                    }
                    break;
                case 7 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:735:9: '.' 'super' arguments
                    {
                    match(input,28,FOLLOW_28_in_identifierSuffix3761); if (failed) return ;
                    match(input,64,FOLLOW_64_in_identifierSuffix3763); if (failed) return ;
                    pushFollow(FOLLOW_arguments_in_identifierSuffix3765);
                    arguments();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 8 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:736:9: '.' 'new' ( nonWildcardTypeArguments )? innerCreator
                    {
                    match(input,28,FOLLOW_28_in_identifierSuffix3775); if (failed) return ;
                    match(input,113,FOLLOW_113_in_identifierSuffix3777); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:736:19: ( nonWildcardTypeArguments )?
                    int alt159=2;
                    int LA159_0 = input.LA(1);

                    if ( (LA159_0==33) ) {
                        alt159=1;
                    }
                    switch (alt159) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:736:20: nonWildcardTypeArguments
                            {
                            pushFollow(FOLLOW_nonWildcardTypeArguments_in_identifierSuffix3780);
                            nonWildcardTypeArguments();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    pushFollow(FOLLOW_innerCreator_in_identifierSuffix3784);
                    innerCreator();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 119, identifierSuffix_StartIndex); }
        }
        return ;
    }
    // $ANTLR end identifierSuffix


    // $ANTLR start creator
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:739:1: creator : ( nonWildcardTypeArguments )? createdName ( arrayCreatorRest | classCreatorRest ) ;
    public final void creator() throws RecognitionException {
        int creator_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 120) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:740:2: ( ( nonWildcardTypeArguments )? createdName ( arrayCreatorRest | classCreatorRest ) )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:740:4: ( nonWildcardTypeArguments )? createdName ( arrayCreatorRest | classCreatorRest )
            {
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:740:4: ( nonWildcardTypeArguments )?
            int alt161=2;
            int LA161_0 = input.LA(1);

            if ( (LA161_0==33) ) {
                alt161=1;
            }
            switch (alt161) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: nonWildcardTypeArguments
                    {
                    pushFollow(FOLLOW_nonWildcardTypeArguments_in_creator3796);
                    nonWildcardTypeArguments();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            pushFollow(FOLLOW_createdName_in_creator3799);
            createdName();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:741:9: ( arrayCreatorRest | classCreatorRest )
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==41) ) {
                alt162=1;
            }
            else if ( (LA162_0==65) ) {
                alt162=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("741:9: ( arrayCreatorRest | classCreatorRest )", 162, 0, input);

                throw nvae;
            }
            switch (alt162) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:741:10: arrayCreatorRest
                    {
                    pushFollow(FOLLOW_arrayCreatorRest_in_creator3810);
                    arrayCreatorRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:741:29: classCreatorRest
                    {
                    pushFollow(FOLLOW_classCreatorRest_in_creator3814);
                    classCreatorRest();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 120, creator_StartIndex); }
        }
        return ;
    }
    // $ANTLR end creator


    // $ANTLR start createdName
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:744:1: createdName : ( Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* | primitiveType );
    public final void createdName() throws RecognitionException {
        int createdName_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 121) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:745:2: ( Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* | primitiveType )
            int alt166=2;
            int LA166_0 = input.LA(1);

            if ( (LA166_0==Identifier) ) {
                alt166=1;
            }
            else if ( ((LA166_0>=55 && LA166_0<=62)) ) {
                alt166=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("744:1: createdName : ( Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )* | primitiveType );", 166, 0, input);

                throw nvae;
            }
            switch (alt166) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:745:4: Identifier ( typeArguments )? ( '.' Identifier ( typeArguments )? )*
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_createdName3826); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:745:15: ( typeArguments )?
                    int alt163=2;
                    int LA163_0 = input.LA(1);

                    if ( (LA163_0==33) ) {
                        alt163=1;
                    }
                    switch (alt163) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: typeArguments
                            {
                            pushFollow(FOLLOW_typeArguments_in_createdName3828);
                            typeArguments();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:746:9: ( '.' Identifier ( typeArguments )? )*
                    loop165:
                    do {
                        int alt165=2;
                        int LA165_0 = input.LA(1);

                        if ( (LA165_0==28) ) {
                            alt165=1;
                        }


                        switch (alt165) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:746:10: '.' Identifier ( typeArguments )?
                    	    {
                    	    match(input,28,FOLLOW_28_in_createdName3840); if (failed) return ;
                    	    match(input,Identifier,FOLLOW_Identifier_in_createdName3842); if (failed) return ;
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:746:25: ( typeArguments )?
                    	    int alt164=2;
                    	    int LA164_0 = input.LA(1);

                    	    if ( (LA164_0==33) ) {
                    	        alt164=1;
                    	    }
                    	    switch (alt164) {
                    	        case 1 :
                    	            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: typeArguments
                    	            {
                    	            pushFollow(FOLLOW_typeArguments_in_createdName3844);
                    	            typeArguments();
                    	            _fsp--;
                    	            if (failed) return ;

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop165;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:747:7: primitiveType
                    {
                    pushFollow(FOLLOW_primitiveType_in_createdName3855);
                    primitiveType();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 121, createdName_StartIndex); }
        }
        return ;
    }
    // $ANTLR end createdName


    // $ANTLR start innerCreator
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:750:1: innerCreator : Identifier classCreatorRest ;
    public final void innerCreator() throws RecognitionException {
        int innerCreator_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 122) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:751:2: ( Identifier classCreatorRest )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:751:4: Identifier classCreatorRest
            {
            match(input,Identifier,FOLLOW_Identifier_in_innerCreator3867); if (failed) return ;
            pushFollow(FOLLOW_classCreatorRest_in_innerCreator3869);
            classCreatorRest();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 122, innerCreator_StartIndex); }
        }
        return ;
    }
    // $ANTLR end innerCreator


    // $ANTLR start arrayCreatorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:754:1: arrayCreatorRest : '[' ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* ) ;
    public final void arrayCreatorRest() throws RecognitionException {
        int arrayCreatorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 123) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:755:2: ( '[' ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* ) )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:755:4: '[' ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* )
            {
            match(input,41,FOLLOW_41_in_arrayCreatorRest3880); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:756:9: ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* )
            int alt170=2;
            int LA170_0 = input.LA(1);

            if ( (LA170_0==42) ) {
                alt170=1;
            }
            else if ( (LA170_0==Identifier||(LA170_0>=FloatingPointLiteral && LA170_0<=DecimalLiteral)||LA170_0==33||LA170_0==40||(LA170_0>=55 && LA170_0<=62)||(LA170_0>=64 && LA170_0<=65)||(LA170_0>=68 && LA170_0<=70)||(LA170_0>=104 && LA170_0<=105)||(LA170_0>=108 && LA170_0<=113)) ) {
                alt170=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("756:9: ( ']' ( '[' ']' )* arrayInitializer | expression ']' ( '[' expression ']' )* ( '[' ']' )* )", 170, 0, input);

                throw nvae;
            }
            switch (alt170) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:756:13: ']' ( '[' ']' )* arrayInitializer
                    {
                    match(input,42,FOLLOW_42_in_arrayCreatorRest3894); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:756:17: ( '[' ']' )*
                    loop167:
                    do {
                        int alt167=2;
                        int LA167_0 = input.LA(1);

                        if ( (LA167_0==41) ) {
                            alt167=1;
                        }


                        switch (alt167) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:756:18: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_arrayCreatorRest3897); if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_arrayCreatorRest3899); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop167;
                        }
                    } while (true);

                    pushFollow(FOLLOW_arrayInitializer_in_arrayCreatorRest3903);
                    arrayInitializer();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:757:13: expression ']' ( '[' expression ']' )* ( '[' ']' )*
                    {
                    pushFollow(FOLLOW_expression_in_arrayCreatorRest3917);
                    expression();
                    _fsp--;
                    if (failed) return ;
                    match(input,42,FOLLOW_42_in_arrayCreatorRest3919); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:757:28: ( '[' expression ']' )*
                    loop168:
                    do {
                        int alt168=2;
                        int LA168_0 = input.LA(1);

                        if ( (LA168_0==41) ) {
                            switch ( input.LA(2) ) {
                            case 104:
                                {
                                int LA168_33 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 105:
                                {
                                int LA168_34 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 108:
                                {
                                int LA168_35 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 109:
                                {
                                int LA168_36 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 110:
                                {
                                int LA168_37 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 111:
                                {
                                int LA168_38 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 65:
                                {
                                int LA168_39 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 33:
                                {
                                int LA168_40 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 112:
                                {
                                int LA168_41 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 64:
                                {
                                int LA168_42 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case HexLiteral:
                            case OctalLiteral:
                            case DecimalLiteral:
                                {
                                int LA168_43 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case FloatingPointLiteral:
                                {
                                int LA168_44 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case CharacterLiteral:
                                {
                                int LA168_45 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case StringLiteral:
                                {
                                int LA168_46 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 69:
                            case 70:
                                {
                                int LA168_47 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 68:
                                {
                                int LA168_48 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 113:
                                {
                                int LA168_49 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case Identifier:
                                {
                                int LA168_50 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                            case 59:
                            case 60:
                            case 61:
                            case 62:
                                {
                                int LA168_51 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;
                            case 40:
                                {
                                int LA168_52 = input.LA(3);

                                if ( (synpred261()) ) {
                                    alt168=1;
                                }


                                }
                                break;

                            }

                        }


                        switch (alt168) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:757:29: '[' expression ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_arrayCreatorRest3922); if (failed) return ;
                    	    pushFollow(FOLLOW_expression_in_arrayCreatorRest3924);
                    	    expression();
                    	    _fsp--;
                    	    if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_arrayCreatorRest3926); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop168;
                        }
                    } while (true);

                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:757:50: ( '[' ']' )*
                    loop169:
                    do {
                        int alt169=2;
                        int LA169_0 = input.LA(1);

                        if ( (LA169_0==41) ) {
                            int LA169_2 = input.LA(2);

                            if ( (LA169_2==42) ) {
                                alt169=1;
                            }


                        }


                        switch (alt169) {
                    	case 1 :
                    	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:757:51: '[' ']'
                    	    {
                    	    match(input,41,FOLLOW_41_in_arrayCreatorRest3931); if (failed) return ;
                    	    match(input,42,FOLLOW_42_in_arrayCreatorRest3933); if (failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop169;
                        }
                    } while (true);


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 123, arrayCreatorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end arrayCreatorRest


    // $ANTLR start classCreatorRest
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:761:1: classCreatorRest : arguments ( classBody )? ;
    public final void classCreatorRest() throws RecognitionException {
        int classCreatorRest_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 124) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:762:2: ( arguments ( classBody )? )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:762:4: arguments ( classBody )?
            {
            pushFollow(FOLLOW_arguments_in_classCreatorRest3956);
            arguments();
            _fsp--;
            if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:762:14: ( classBody )?
            int alt171=2;
            int LA171_0 = input.LA(1);

            if ( (LA171_0==37) ) {
                alt171=1;
            }
            switch (alt171) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: classBody
                    {
                    pushFollow(FOLLOW_classBody_in_classCreatorRest3958);
                    classBody();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 124, classCreatorRest_StartIndex); }
        }
        return ;
    }
    // $ANTLR end classCreatorRest


    // $ANTLR start explicitGenericInvocation
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:765:1: explicitGenericInvocation : nonWildcardTypeArguments explicitGenericInvocationSuffix ;
    public final void explicitGenericInvocation() throws RecognitionException {
        int explicitGenericInvocation_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 125) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:766:2: ( nonWildcardTypeArguments explicitGenericInvocationSuffix )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:766:4: nonWildcardTypeArguments explicitGenericInvocationSuffix
            {
            pushFollow(FOLLOW_nonWildcardTypeArguments_in_explicitGenericInvocation3971);
            nonWildcardTypeArguments();
            _fsp--;
            if (failed) return ;
            pushFollow(FOLLOW_explicitGenericInvocationSuffix_in_explicitGenericInvocation3973);
            explicitGenericInvocationSuffix();
            _fsp--;
            if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 125, explicitGenericInvocation_StartIndex); }
        }
        return ;
    }
    // $ANTLR end explicitGenericInvocation


    // $ANTLR start nonWildcardTypeArguments
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:769:1: nonWildcardTypeArguments : '<' typeList '>' ;
    public final void nonWildcardTypeArguments() throws RecognitionException {
        int nonWildcardTypeArguments_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 126) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:770:2: ( '<' typeList '>' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:770:4: '<' typeList '>'
            {
            match(input,33,FOLLOW_33_in_nonWildcardTypeArguments3985); if (failed) return ;
            pushFollow(FOLLOW_typeList_in_nonWildcardTypeArguments3987);
            typeList();
            _fsp--;
            if (failed) return ;
            match(input,35,FOLLOW_35_in_nonWildcardTypeArguments3989); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 126, nonWildcardTypeArguments_StartIndex); }
        }
        return ;
    }
    // $ANTLR end nonWildcardTypeArguments


    // $ANTLR start explicitGenericInvocationSuffix
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:773:1: explicitGenericInvocationSuffix : ( 'super' superSuffix | Identifier arguments );
    public final void explicitGenericInvocationSuffix() throws RecognitionException {
        int explicitGenericInvocationSuffix_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 127) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:774:2: ( 'super' superSuffix | Identifier arguments )
            int alt172=2;
            int LA172_0 = input.LA(1);

            if ( (LA172_0==64) ) {
                alt172=1;
            }
            else if ( (LA172_0==Identifier) ) {
                alt172=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("773:1: explicitGenericInvocationSuffix : ( 'super' superSuffix | Identifier arguments );", 172, 0, input);

                throw nvae;
            }
            switch (alt172) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:774:4: 'super' superSuffix
                    {
                    match(input,64,FOLLOW_64_in_explicitGenericInvocationSuffix4001); if (failed) return ;
                    pushFollow(FOLLOW_superSuffix_in_explicitGenericInvocationSuffix4003);
                    superSuffix();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:775:6: Identifier arguments
                    {
                    match(input,Identifier,FOLLOW_Identifier_in_explicitGenericInvocationSuffix4010); if (failed) return ;
                    pushFollow(FOLLOW_arguments_in_explicitGenericInvocationSuffix4012);
                    arguments();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 127, explicitGenericInvocationSuffix_StartIndex); }
        }
        return ;
    }
    // $ANTLR end explicitGenericInvocationSuffix


    // $ANTLR start selector
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:778:1: selector : ( '.' Identifier ( arguments )? | '.' 'this' | '.' 'super' superSuffix | '.' 'new' ( nonWildcardTypeArguments )? innerCreator | '[' expression ']' );
    public final void selector() throws RecognitionException {
        int selector_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 128) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:779:2: ( '.' Identifier ( arguments )? | '.' 'this' | '.' 'super' superSuffix | '.' 'new' ( nonWildcardTypeArguments )? innerCreator | '[' expression ']' )
            int alt175=5;
            int LA175_0 = input.LA(1);

            if ( (LA175_0==28) ) {
                switch ( input.LA(2) ) {
                case Identifier:
                    {
                    alt175=1;
                    }
                    break;
                case 113:
                    {
                    alt175=4;
                    }
                    break;
                case 112:
                    {
                    alt175=2;
                    }
                    break;
                case 64:
                    {
                    alt175=3;
                    }
                    break;
                default:
                    if (backtracking>0) {failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("778:1: selector : ( '.' Identifier ( arguments )? | '.' 'this' | '.' 'super' superSuffix | '.' 'new' ( nonWildcardTypeArguments )? innerCreator | '[' expression ']' );", 175, 1, input);

                    throw nvae;
                }

            }
            else if ( (LA175_0==41) ) {
                alt175=5;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("778:1: selector : ( '.' Identifier ( arguments )? | '.' 'this' | '.' 'super' superSuffix | '.' 'new' ( nonWildcardTypeArguments )? innerCreator | '[' expression ']' );", 175, 0, input);

                throw nvae;
            }
            switch (alt175) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:779:4: '.' Identifier ( arguments )?
                    {
                    match(input,28,FOLLOW_28_in_selector4024); if (failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_selector4026); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:779:19: ( arguments )?
                    int alt173=2;
                    int LA173_0 = input.LA(1);

                    if ( (LA173_0==65) ) {
                        alt173=1;
                    }
                    switch (alt173) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:779:20: arguments
                            {
                            pushFollow(FOLLOW_arguments_in_selector4029);
                            arguments();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:780:6: '.' 'this'
                    {
                    match(input,28,FOLLOW_28_in_selector4038); if (failed) return ;
                    match(input,112,FOLLOW_112_in_selector4040); if (failed) return ;

                    }
                    break;
                case 3 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:781:6: '.' 'super' superSuffix
                    {
                    match(input,28,FOLLOW_28_in_selector4047); if (failed) return ;
                    match(input,64,FOLLOW_64_in_selector4049); if (failed) return ;
                    pushFollow(FOLLOW_superSuffix_in_selector4051);
                    superSuffix();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 4 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:782:6: '.' 'new' ( nonWildcardTypeArguments )? innerCreator
                    {
                    match(input,28,FOLLOW_28_in_selector4058); if (failed) return ;
                    match(input,113,FOLLOW_113_in_selector4060); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:782:16: ( nonWildcardTypeArguments )?
                    int alt174=2;
                    int LA174_0 = input.LA(1);

                    if ( (LA174_0==33) ) {
                        alt174=1;
                    }
                    switch (alt174) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:782:17: nonWildcardTypeArguments
                            {
                            pushFollow(FOLLOW_nonWildcardTypeArguments_in_selector4063);
                            nonWildcardTypeArguments();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

                    }

                    pushFollow(FOLLOW_innerCreator_in_selector4067);
                    innerCreator();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 5 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:783:6: '[' expression ']'
                    {
                    match(input,41,FOLLOW_41_in_selector4074); if (failed) return ;
                    pushFollow(FOLLOW_expression_in_selector4076);
                    expression();
                    _fsp--;
                    if (failed) return ;
                    match(input,42,FOLLOW_42_in_selector4078); if (failed) return ;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 128, selector_StartIndex); }
        }
        return ;
    }
    // $ANTLR end selector


    // $ANTLR start superSuffix
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:786:1: superSuffix : ( arguments | '.' Identifier ( arguments )? );
    public final void superSuffix() throws RecognitionException {
        int superSuffix_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 129) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:787:2: ( arguments | '.' Identifier ( arguments )? )
            int alt177=2;
            int LA177_0 = input.LA(1);

            if ( (LA177_0==65) ) {
                alt177=1;
            }
            else if ( (LA177_0==28) ) {
                alt177=2;
            }
            else {
                if (backtracking>0) {failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("786:1: superSuffix : ( arguments | '.' Identifier ( arguments )? );", 177, 0, input);

                throw nvae;
            }
            switch (alt177) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:787:4: arguments
                    {
                    pushFollow(FOLLOW_arguments_in_superSuffix4090);
                    arguments();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;
                case 2 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:788:6: '.' Identifier ( arguments )?
                    {
                    match(input,28,FOLLOW_28_in_superSuffix4097); if (failed) return ;
                    match(input,Identifier,FOLLOW_Identifier_in_superSuffix4099); if (failed) return ;
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:788:21: ( arguments )?
                    int alt176=2;
                    int LA176_0 = input.LA(1);

                    if ( (LA176_0==65) ) {
                        alt176=1;
                    }
                    switch (alt176) {
                        case 1 :
                            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:788:22: arguments
                            {
                            pushFollow(FOLLOW_arguments_in_superSuffix4102);
                            arguments();
                            _fsp--;
                            if (failed) return ;

                            }
                            break;

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
            if ( backtracking>0 ) { memoize(input, 129, superSuffix_StartIndex); }
        }
        return ;
    }
    // $ANTLR end superSuffix


    // $ANTLR start arguments
    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:791:1: arguments : '(' ( expressionList )? ')' ;
    public final void arguments() throws RecognitionException {
        int arguments_StartIndex = input.index();
        try {
            if ( backtracking>0 && alreadyParsedRule(input, 130) ) { return ; }
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:792:2: ( '(' ( expressionList )? ')' )
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:792:4: '(' ( expressionList )? ')'
            {
            match(input,65,FOLLOW_65_in_arguments4118); if (failed) return ;
            // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:792:8: ( expressionList )?
            int alt178=2;
            int LA178_0 = input.LA(1);

            if ( (LA178_0==Identifier||(LA178_0>=FloatingPointLiteral && LA178_0<=DecimalLiteral)||LA178_0==33||LA178_0==40||(LA178_0>=55 && LA178_0<=62)||(LA178_0>=64 && LA178_0<=65)||(LA178_0>=68 && LA178_0<=70)||(LA178_0>=104 && LA178_0<=105)||(LA178_0>=108 && LA178_0<=113)) ) {
                alt178=1;
            }
            switch (alt178) {
                case 1 :
                    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_arguments4120);
                    expressionList();
                    _fsp--;
                    if (failed) return ;

                    }
                    break;

            }

            match(input,66,FOLLOW_66_in_arguments4123); if (failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( backtracking>0 ) { memoize(input, 130, arguments_StartIndex); }
        }
        return ;
    }
    // $ANTLR end arguments

    // $ANTLR start synpred1
    public final void synpred1_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:122:4: ( annotations )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:122:4: annotations
        {
        pushFollow(FOLLOW_annotations_in_synpred154);
        annotations();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred1

    // $ANTLR start synpred38
    public final void synpred38_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:218:4: ( methodDeclaration )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:218:4: methodDeclaration
        {
        pushFollow(FOLLOW_methodDeclaration_in_synpred38561);
        methodDeclaration();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred38

    // $ANTLR start synpred39
    public final void synpred39_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:219:4: ( fieldDeclaration )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:219:4: fieldDeclaration
        {
        pushFollow(FOLLOW_fieldDeclaration_in_synpred39566);
        fieldDeclaration();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred39

    // $ANTLR start synpred85
    public final void synpred85_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:352:16: ( '.' Identifier )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:352:16: '.' Identifier
        {
        match(input,28,FOLLOW_28_in_synpred851334); if (failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred851336); if (failed) return ;

        }
    }
    // $ANTLR end synpred85

    // $ANTLR start synpred120
    public final void synpred120_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:442:4: ( annotation )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:442:4: annotation
        {
        pushFollow(FOLLOW_annotation_in_synpred1201838);
        annotation();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred120

    // $ANTLR start synpred135
    public final void synpred135_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:489:6: ( classDeclaration ( ';' )? )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:489:6: classDeclaration ( ';' )?
        {
        pushFollow(FOLLOW_classDeclaration_in_synpred1352068);
        classDeclaration();
        _fsp--;
        if (failed) return ;
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:489:23: ( ';' )?
        int alt194=2;
        int LA194_0 = input.LA(1);

        if ( (LA194_0==25) ) {
            alt194=1;
        }
        switch (alt194) {
            case 1 :
                // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: ';'
                {
                match(input,25,FOLLOW_25_in_synpred1352070); if (failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred135

    // $ANTLR start synpred137
    public final void synpred137_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:490:6: ( interfaceDeclaration ( ';' )? )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:490:6: interfaceDeclaration ( ';' )?
        {
        pushFollow(FOLLOW_interfaceDeclaration_in_synpred1372078);
        interfaceDeclaration();
        _fsp--;
        if (failed) return ;
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:490:27: ( ';' )?
        int alt195=2;
        int LA195_0 = input.LA(1);

        if ( (LA195_0==25) ) {
            alt195=1;
        }
        switch (alt195) {
            case 1 :
                // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: ';'
                {
                match(input,25,FOLLOW_25_in_synpred1372080); if (failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred137

    // $ANTLR start synpred139
    public final void synpred139_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:491:6: ( enumDeclaration ( ';' )? )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:491:6: enumDeclaration ( ';' )?
        {
        pushFollow(FOLLOW_enumDeclaration_in_synpred1392088);
        enumDeclaration();
        _fsp--;
        if (failed) return ;
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:491:22: ( ';' )?
        int alt196=2;
        int LA196_0 = input.LA(1);

        if ( (LA196_0==25) ) {
            alt196=1;
        }
        switch (alt196) {
            case 1 :
                // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: ';'
                {
                match(input,25,FOLLOW_25_in_synpred1392090); if (failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred139

    // $ANTLR start synpred144
    public final void synpred144_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:519:4: ( localVariableDeclaration )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:519:4: localVariableDeclaration
        {
        pushFollow(FOLLOW_localVariableDeclaration_in_synpred1442205);
        localVariableDeclaration();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred144

    // $ANTLR start synpred145
    public final void synpred145_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:520:4: ( classOrInterfaceDeclaration )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:520:4: classOrInterfaceDeclaration
        {
        pushFollow(FOLLOW_classOrInterfaceDeclaration_in_synpred1452210);
        classOrInterfaceDeclaration();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred145

    // $ANTLR start synpred150
    public final void synpred150_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:531:52: ( 'else' statement )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:531:52: 'else' statement
        {
        match(input,76,FOLLOW_76_in_synpred1502291); if (failed) return ;
        pushFollow(FOLLOW_statement_in_synpred1502293);
        statement();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred150

    // $ANTLR start synpred155
    public final void synpred155_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:536:9: ( catches 'finally' block )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:536:9: catches 'finally' block
        {
        pushFollow(FOLLOW_catches_in_synpred1552359);
        catches();
        _fsp--;
        if (failed) return ;
        match(input,81,FOLLOW_81_in_synpred1552361); if (failed) return ;
        pushFollow(FOLLOW_block_in_synpred1552363);
        block();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred155

    // $ANTLR start synpred156
    public final void synpred156_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:537:9: ( catches )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:537:9: catches
        {
        pushFollow(FOLLOW_catches_in_synpred1562373);
        catches();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred156

    // $ANTLR start synpred173
    public final void synpred173_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:572:4: ( 'case' constantExpression ':' )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:572:4: 'case' constantExpression ':'
        {
        match(input,88,FOLLOW_88_in_synpred1732598); if (failed) return ;
        pushFollow(FOLLOW_constantExpression_in_synpred1732600);
        constantExpression();
        _fsp--;
        if (failed) return ;
        match(input,74,FOLLOW_74_in_synpred1732602); if (failed) return ;

        }
    }
    // $ANTLR end synpred173

    // $ANTLR start synpred174
    public final void synpred174_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:573:6: ( 'case' enumConstantName ':' )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:573:6: 'case' enumConstantName ':'
        {
        match(input,88,FOLLOW_88_in_synpred1742609); if (failed) return ;
        pushFollow(FOLLOW_enumConstantName_in_synpred1742611);
        enumConstantName();
        _fsp--;
        if (failed) return ;
        match(input,74,FOLLOW_74_in_synpred1742613); if (failed) return ;

        }
    }
    // $ANTLR end synpred174

    // $ANTLR start synpred176
    public final void synpred176_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:583:4: ( forVarControl )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:583:4: forVarControl
        {
        pushFollow(FOLLOW_forVarControl_in_synpred1762658);
        forVarControl();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred176

    // $ANTLR start synpred181
    public final void synpred181_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:588:4: ( ( variableModifier )* type variableDeclarators )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:588:4: ( variableModifier )* type variableDeclarators
        {
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:588:4: ( variableModifier )*
        loop203:
        do {
            int alt203=2;
            int LA203_0 = input.LA(1);

            if ( (LA203_0==49||LA203_0==71) ) {
                alt203=1;
            }


            switch (alt203) {
        	case 1 :
        	    // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:0:0: variableModifier
        	    {
        	    pushFollow(FOLLOW_variableModifier_in_synpred1812685);
        	    variableModifier();
        	    _fsp--;
        	    if (failed) return ;

        	    }
        	    break;

        	default :
        	    break loop203;
            }
        } while (true);

        pushFollow(FOLLOW_type_in_synpred1812688);
        type();
        _fsp--;
        if (failed) return ;
        pushFollow(FOLLOW_variableDeclarators_in_synpred1812690);
        variableDeclarators();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred181

    // $ANTLR start synpred184
    public final void synpred184_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:619:27: ( assignmentOperator expression )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:619:27: assignmentOperator expression
        {
        pushFollow(FOLLOW_assignmentOperator_in_synpred1842809);
        assignmentOperator();
        _fsp--;
        if (failed) return ;
        pushFollow(FOLLOW_expression_in_synpred1842811);
        expression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred184

    // $ANTLR start synpred195
    public final void synpred195_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:633:9: ( '>' '>' '=' )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:633:9: '>' '>' '='
        {
        match(input,35,FOLLOW_35_in_synpred1952929); if (failed) return ;
        match(input,35,FOLLOW_35_in_synpred1952931); if (failed) return ;
        match(input,44,FOLLOW_44_in_synpred1952933); if (failed) return ;

        }
    }
    // $ANTLR end synpred195

    // $ANTLR start synpred205
    public final void synpred205_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:670:27: ( relationalOp shiftExpression )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:670:27: relationalOp shiftExpression
        {
        pushFollow(FOLLOW_relationalOp_in_synpred2053177);
        relationalOp();
        _fsp--;
        if (failed) return ;
        pushFollow(FOLLOW_shiftExpression_in_synpred2053179);
        shiftExpression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred205

    // $ANTLR start synpred209
    public final void synpred209_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:678:30: ( shiftOp additiveExpression )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:678:30: shiftOp additiveExpression
        {
        pushFollow(FOLLOW_shiftOp_in_synpred2093232);
        shiftOp();
        _fsp--;
        if (failed) return ;
        pushFollow(FOLLOW_additiveExpression_in_synpred2093234);
        additiveExpression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred209

    // $ANTLR start synpred211
    public final void synpred211_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:683:15: ( '>' '>' '>' )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:683:15: '>' '>' '>'
        {
        match(input,35,FOLLOW_35_in_synpred2113264); if (failed) return ;
        match(input,35,FOLLOW_35_in_synpred2113266); if (failed) return ;
        match(input,35,FOLLOW_35_in_synpred2113268); if (failed) return ;

        }
    }
    // $ANTLR end synpred211

    // $ANTLR start synpred223
    public final void synpred223_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:706:9: ( castExpression )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:706:9: castExpression
        {
        pushFollow(FOLLOW_castExpression_in_synpred2233449);
        castExpression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred223

    // $ANTLR start synpred227
    public final void synpred227_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:711:8: ( '(' primitiveType ')' unaryExpression )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:711:8: '(' primitiveType ')' unaryExpression
        {
        match(input,65,FOLLOW_65_in_synpred2273487); if (failed) return ;
        pushFollow(FOLLOW_primitiveType_in_synpred2273489);
        primitiveType();
        _fsp--;
        if (failed) return ;
        match(input,66,FOLLOW_66_in_synpred2273491); if (failed) return ;
        pushFollow(FOLLOW_unaryExpression_in_synpred2273493);
        unaryExpression();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred227

    // $ANTLR start synpred228
    public final void synpred228_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:712:13: ( type )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:712:13: type
        {
        pushFollow(FOLLOW_type_in_synpred2283505);
        type();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred228

    // $ANTLR start synpred232
    public final void synpred232_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:17: ( '.' Identifier )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:17: '.' Identifier
        {
        match(input,28,FOLLOW_28_in_synpred2323572); if (failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred2323574); if (failed) return ;

        }
    }
    // $ANTLR end synpred232

    // $ANTLR start synpred233
    public final void synpred233_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:35: ( identifierSuffix )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:719:35: identifierSuffix
        {
        pushFollow(FOLLOW_identifierSuffix_in_synpred2333579);
        identifierSuffix();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred233

    // $ANTLR start synpred238
    public final void synpred238_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:21: ( '.' Identifier )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:21: '.' Identifier
        {
        match(input,28,FOLLOW_28_in_synpred2383628); if (failed) return ;
        match(input,Identifier,FOLLOW_Identifier_in_synpred2383630); if (failed) return ;

        }
    }
    // $ANTLR end synpred238

    // $ANTLR start synpred239
    public final void synpred239_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:39: ( identifierSuffix )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:723:39: identifierSuffix
        {
        pushFollow(FOLLOW_identifierSuffix_in_synpred2393635);
        identifierSuffix();
        _fsp--;
        if (failed) return ;

        }
    }
    // $ANTLR end synpred239

    // $ANTLR start synpred245
    public final void synpred245_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:730:5: ( '[' expression ']' )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:730:5: '[' expression ']'
        {
        match(input,41,FOLLOW_41_in_synpred2453698); if (failed) return ;
        pushFollow(FOLLOW_expression_in_synpred2453700);
        expression();
        _fsp--;
        if (failed) return ;
        match(input,42,FOLLOW_42_in_synpred2453702); if (failed) return ;

        }
    }
    // $ANTLR end synpred245

    // $ANTLR start synpred261
    public final void synpred261_fragment() throws RecognitionException {   
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:757:29: ( '[' expression ']' )
        // C:\\projekte5\\supose\\src\\main\\antlr\\grammars\\Java.g:757:29: '[' expression ']'
        {
        match(input,41,FOLLOW_41_in_synpred2613922); if (failed) return ;
        pushFollow(FOLLOW_expression_in_synpred2613924);
        expression();
        _fsp--;
        if (failed) return ;
        match(input,42,FOLLOW_42_in_synpred2613926); if (failed) return ;

        }
    }
    // $ANTLR end synpred261

    public final boolean synpred144() {
        backtracking++;
        int start = input.mark();
        try {
            synpred144_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred145() {
        backtracking++;
        int start = input.mark();
        try {
            synpred145_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred85() {
        backtracking++;
        int start = input.mark();
        try {
            synpred85_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred120() {
        backtracking++;
        int start = input.mark();
        try {
            synpred120_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred181() {
        backtracking++;
        int start = input.mark();
        try {
            synpred181_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred211() {
        backtracking++;
        int start = input.mark();
        try {
            synpred211_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred184() {
        backtracking++;
        int start = input.mark();
        try {
            synpred184_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred239() {
        backtracking++;
        int start = input.mark();
        try {
            synpred239_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred238() {
        backtracking++;
        int start = input.mark();
        try {
            synpred238_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred233() {
        backtracking++;
        int start = input.mark();
        try {
            synpred233_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred232() {
        backtracking++;
        int start = input.mark();
        try {
            synpred232_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred135() {
        backtracking++;
        int start = input.mark();
        try {
            synpred135_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred139() {
        backtracking++;
        int start = input.mark();
        try {
            synpred139_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred39() {
        backtracking++;
        int start = input.mark();
        try {
            synpred39_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred38() {
        backtracking++;
        int start = input.mark();
        try {
            synpred38_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred137() {
        backtracking++;
        int start = input.mark();
        try {
            synpred137_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred1() {
        backtracking++;
        int start = input.mark();
        try {
            synpred1_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred209() {
        backtracking++;
        int start = input.mark();
        try {
            synpred209_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred205() {
        backtracking++;
        int start = input.mark();
        try {
            synpred205_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred223() {
        backtracking++;
        int start = input.mark();
        try {
            synpred223_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred174() {
        backtracking++;
        int start = input.mark();
        try {
            synpred174_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred261() {
        backtracking++;
        int start = input.mark();
        try {
            synpred261_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred173() {
        backtracking++;
        int start = input.mark();
        try {
            synpred173_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred150() {
        backtracking++;
        int start = input.mark();
        try {
            synpred150_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred176() {
        backtracking++;
        int start = input.mark();
        try {
            synpred176_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred227() {
        backtracking++;
        int start = input.mark();
        try {
            synpred227_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred228() {
        backtracking++;
        int start = input.mark();
        try {
            synpred228_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred195() {
        backtracking++;
        int start = input.mark();
        try {
            synpred195_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred156() {
        backtracking++;
        int start = input.mark();
        try {
            synpred156_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred155() {
        backtracking++;
        int start = input.mark();
        try {
            synpred155_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }
    public final boolean synpred245() {
        backtracking++;
        int start = input.mark();
        try {
            synpred245_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !failed;
        input.rewind(start);
        backtracking--;
        failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_annotations_in_compilationUnit54 = new BitSet(new long[]{0x007FE0804F000022L,0x0000000000000080L});
    public static final BitSet FOLLOW_packageDeclaration_in_compilationUnit59 = new BitSet(new long[]{0x007FE0804E000022L,0x0000000000000080L});
    public static final BitSet FOLLOW_importDeclaration_in_compilationUnit70 = new BitSet(new long[]{0x007FE0804E000022L,0x0000000000000080L});
    public static final BitSet FOLLOW_typeDeclaration_in_compilationUnit81 = new BitSet(new long[]{0x007FE0804A000022L,0x0000000000000080L});
    public static final BitSet FOLLOW_24_in_packageDeclaration93 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedName_in_packageDeclaration95 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_packageDeclaration97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_importDeclaration109 = new BitSet(new long[]{0x0000000008000010L});
    public static final BitSet FOLLOW_27_in_importDeclaration111 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_importDeclaration114 = new BitSet(new long[]{0x0000000012000000L});
    public static final BitSet FOLLOW_28_in_importDeclaration117 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_importDeclaration119 = new BitSet(new long[]{0x0000000012000000L});
    public static final BitSet FOLLOW_28_in_importDeclaration124 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_importDeclaration126 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_importDeclaration130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classOrInterfaceDeclaration_in_typeDeclaration142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_typeDeclaration152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_modifier_in_classOrInterfaceDeclaration164 = new BitSet(new long[]{0x007FE08048000020L,0x0000000000000080L});
    public static final BitSet FOLLOW_classDeclaration_in_classOrInterfaceDeclaration168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceDeclaration_in_classOrInterfaceDeclaration172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_normalClassDeclaration_in_classDeclaration185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enumDeclaration_in_classDeclaration195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_normalClassDeclaration207 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_normalClassDeclaration209 = new BitSet(new long[]{0x0000002380000000L});
    public static final BitSet FOLLOW_typeParameters_in_normalClassDeclaration212 = new BitSet(new long[]{0x0000002180000000L});
    public static final BitSet FOLLOW_31_in_normalClassDeclaration225 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_type_in_normalClassDeclaration227 = new BitSet(new long[]{0x0000002100000000L});
    public static final BitSet FOLLOW_32_in_normalClassDeclaration240 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_typeList_in_normalClassDeclaration242 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_classBody_in_normalClassDeclaration254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_typeParameters266 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_typeParameter_in_typeParameters268 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_34_in_typeParameters271 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_typeParameter_in_typeParameters273 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_35_in_typeParameters277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_typeParameter288 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_31_in_typeParameter291 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_bound_in_typeParameter293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_bound308 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_36_in_bound311 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_type_in_bound313 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_ENUM_in_enumDeclaration326 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_enumDeclaration328 = new BitSet(new long[]{0x0000002100000000L});
    public static final BitSet FOLLOW_32_in_enumDeclaration331 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_typeList_in_enumDeclaration333 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_enumBody_in_enumDeclaration337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_enumBody349 = new BitSet(new long[]{0x0000004402000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_enumConstants_in_enumBody351 = new BitSet(new long[]{0x0000004402000000L});
    public static final BitSet FOLLOW_34_in_enumBody354 = new BitSet(new long[]{0x0000004002000000L});
    public static final BitSet FOLLOW_enumBodyDeclarations_in_enumBody357 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_enumBody360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enumConstant_in_enumConstants371 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_enumConstants374 = new BitSet(new long[]{0x0000000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_enumConstant_in_enumConstants376 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_annotations_in_enumConstant390 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_enumConstant393 = new BitSet(new long[]{0x0000002000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_enumConstant396 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_classBody_in_enumConstant401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_enumBodyDeclarations415 = new BitSet(new long[]{0x7FFFE1A24A000032L,0x0000000000000080L});
    public static final BitSet FOLLOW_classBodyDeclaration_in_enumBodyDeclarations418 = new BitSet(new long[]{0x7FFFE1A24A000032L,0x0000000000000080L});
    public static final BitSet FOLLOW_normalInterfaceDeclaration_in_interfaceDeclaration432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationTypeDeclaration_in_interfaceDeclaration438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_normalInterfaceDeclaration450 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_normalInterfaceDeclaration452 = new BitSet(new long[]{0x0000002280000000L});
    public static final BitSet FOLLOW_typeParameters_in_normalInterfaceDeclaration454 = new BitSet(new long[]{0x0000002080000000L});
    public static final BitSet FOLLOW_31_in_normalInterfaceDeclaration458 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_typeList_in_normalInterfaceDeclaration460 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_interfaceBody_in_normalInterfaceDeclaration464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeList476 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_typeList479 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_type_in_typeList481 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_37_in_classBody495 = new BitSet(new long[]{0x7FFFE1E24A000030L,0x0000000000000080L});
    public static final BitSet FOLLOW_classBodyDeclaration_in_classBody497 = new BitSet(new long[]{0x7FFFE1E24A000030L,0x0000000000000080L});
    public static final BitSet FOLLOW_38_in_classBody500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_interfaceBody512 = new BitSet(new long[]{0x7FFFE1C24A000030L,0x0000000000000080L});
    public static final BitSet FOLLOW_interfaceBodyDeclaration_in_interfaceBody514 = new BitSet(new long[]{0x7FFFE1C24A000030L,0x0000000000000080L});
    public static final BitSet FOLLOW_38_in_interfaceBody517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_classBodyDeclaration528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_classBodyDeclaration533 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_classBodyDeclaration536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_modifier_in_classBodyDeclaration541 = new BitSet(new long[]{0x7FFFE18248000030L,0x0000000000000080L});
    public static final BitSet FOLLOW_memberDecl_in_classBodyDeclaration544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericMethodOrConstructorDecl_in_memberDecl556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodDeclaration_in_memberDecl561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fieldDeclaration_in_memberDecl566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_memberDecl571 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_memberDecl573 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_voidMethodDeclaratorRest_in_memberDecl575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_memberDecl582 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_constructorDeclaratorRest_in_memberDecl584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceDeclaration_in_memberDecl589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classDeclaration_in_memberDecl594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeParameters_in_genericMethodOrConstructorDecl606 = new BitSet(new long[]{0x7F80010000000010L});
    public static final BitSet FOLLOW_genericMethodOrConstructorRest_in_genericMethodOrConstructorDecl608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_genericMethodOrConstructorRest621 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_40_in_genericMethodOrConstructorRest625 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_genericMethodOrConstructorRest628 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_methodDeclaratorRest_in_genericMethodOrConstructorRest630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_genericMethodOrConstructorRest635 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_constructorDeclaratorRest_in_genericMethodOrConstructorRest637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_methodDeclaration648 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_methodDeclaration650 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_methodDeclaratorRest_in_methodDeclaration652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_fieldDeclaration665 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclarators_in_fieldDeclaration667 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_fieldDeclaration669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_modifier_in_interfaceBodyDeclaration682 = new BitSet(new long[]{0x7FFFE18248000030L,0x0000000000000080L});
    public static final BitSet FOLLOW_interfaceMemberDecl_in_interfaceBodyDeclaration685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_interfaceBodyDeclaration692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceMethodOrFieldDecl_in_interfaceMemberDecl703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceGenericMethodDecl_in_interfaceMemberDecl708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_interfaceMemberDecl713 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_interfaceMemberDecl715 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_voidInterfaceMethodDeclaratorRest_in_interfaceMemberDecl717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceDeclaration_in_interfaceMemberDecl722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classDeclaration_in_interfaceMemberDecl727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_interfaceMethodOrFieldDecl739 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_interfaceMethodOrFieldDecl741 = new BitSet(new long[]{0x0000120000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceMethodOrFieldRest_in_interfaceMethodOrFieldDecl743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constantDeclaratorsRest_in_interfaceMethodOrFieldRest755 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_interfaceMethodOrFieldRest757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceMethodDeclaratorRest_in_interfaceMethodOrFieldRest762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_methodDeclaratorRest774 = new BitSet(new long[]{0x00000A2002000000L});
    public static final BitSet FOLLOW_41_in_methodDeclaratorRest777 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_methodDeclaratorRest779 = new BitSet(new long[]{0x00000A2002000000L});
    public static final BitSet FOLLOW_43_in_methodDeclaratorRest792 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedNameList_in_methodDeclaratorRest794 = new BitSet(new long[]{0x0000002002000000L});
    public static final BitSet FOLLOW_methodBody_in_methodDeclaratorRest810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_methodDeclaratorRest824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_voidMethodDeclaratorRest846 = new BitSet(new long[]{0x0000082002000000L});
    public static final BitSet FOLLOW_43_in_voidMethodDeclaratorRest849 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedNameList_in_voidMethodDeclaratorRest851 = new BitSet(new long[]{0x0000002002000000L});
    public static final BitSet FOLLOW_methodBody_in_voidMethodDeclaratorRest867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_voidMethodDeclaratorRest881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_interfaceMethodDeclaratorRest903 = new BitSet(new long[]{0x00000A0002000000L});
    public static final BitSet FOLLOW_41_in_interfaceMethodDeclaratorRest906 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_interfaceMethodDeclaratorRest908 = new BitSet(new long[]{0x00000A0002000000L});
    public static final BitSet FOLLOW_43_in_interfaceMethodDeclaratorRest913 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedNameList_in_interfaceMethodDeclaratorRest915 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_interfaceMethodDeclaratorRest919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeParameters_in_interfaceGenericMethodDecl931 = new BitSet(new long[]{0x7F80010000000010L});
    public static final BitSet FOLLOW_type_in_interfaceGenericMethodDecl934 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_40_in_interfaceGenericMethodDecl938 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_interfaceGenericMethodDecl941 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceMethodDeclaratorRest_in_interfaceGenericMethodDecl951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_voidInterfaceMethodDeclaratorRest963 = new BitSet(new long[]{0x0000080002000000L});
    public static final BitSet FOLLOW_43_in_voidInterfaceMethodDeclaratorRest966 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedNameList_in_voidInterfaceMethodDeclaratorRest968 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_voidInterfaceMethodDeclaratorRest972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameters_in_constructorDeclaratorRest984 = new BitSet(new long[]{0x0000082000000000L});
    public static final BitSet FOLLOW_43_in_constructorDeclaratorRest987 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedNameList_in_constructorDeclaratorRest989 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_methodBody_in_constructorDeclaratorRest993 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_constantDeclarator1004 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_constantDeclaratorRest_in_constantDeclarator1006 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclarator_in_variableDeclarators1018 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_variableDeclarators1021 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclarator_in_variableDeclarators1023 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclarator1036 = new BitSet(new long[]{0x0000120000000002L});
    public static final BitSet FOLLOW_variableDeclaratorRest_in_variableDeclarator1038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_variableDeclaratorRest1051 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_variableDeclaratorRest1053 = new BitSet(new long[]{0x0000120000000002L});
    public static final BitSet FOLLOW_44_in_variableDeclaratorRest1058 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_variableInitializer_in_variableDeclaratorRest1060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_variableDeclaratorRest1067 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_variableInitializer_in_variableDeclaratorRest1069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constantDeclaratorRest_in_constantDeclaratorsRest1089 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_constantDeclaratorsRest1092 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_constantDeclarator_in_constantDeclaratorsRest1094 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_41_in_constantDeclaratorRest1111 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_constantDeclaratorRest1113 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_44_in_constantDeclaratorRest1117 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_variableInitializer_in_constantDeclaratorRest1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclaratorId1131 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_variableDeclaratorId1134 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_variableDeclaratorId1136 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_arrayInitializer_in_variableInitializer1149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_variableInitializer1159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_arrayInitializer1171 = new BitSet(new long[]{0x7F80016200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1174 = new BitSet(new long[]{0x0000004400000000L});
    public static final BitSet FOLLOW_34_in_arrayInitializer1177 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_variableInitializer_in_arrayInitializer1179 = new BitSet(new long[]{0x0000004400000000L});
    public static final BitSet FOLLOW_34_in_arrayInitializer1184 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_arrayInitializer1191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_modifier1207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_modifier1217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_modifier1227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_modifier1237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_modifier1247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_modifier1257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_modifier1267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_modifier1277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_modifier1287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_modifier1297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_modifier1307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_modifier1317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_packageOrTypeName1331 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_packageOrTypeName1334 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_packageOrTypeName1336 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_Identifier_in_enumConstantName1354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_typeName1370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_packageOrTypeName_in_typeName1380 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_typeName1382 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_typeName1384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_type1395 = new BitSet(new long[]{0x0000020210000002L});
    public static final BitSet FOLLOW_typeArguments_in_type1398 = new BitSet(new long[]{0x0000020010000002L});
    public static final BitSet FOLLOW_28_in_type1403 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_type1405 = new BitSet(new long[]{0x0000020210000002L});
    public static final BitSet FOLLOW_typeArguments_in_type1408 = new BitSet(new long[]{0x0000020010000002L});
    public static final BitSet FOLLOW_41_in_type1416 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_type1418 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_primitiveType_in_type1425 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_type1428 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_type1430 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_set_in_primitiveType0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_variableModifier1518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_variableModifier1528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_typeArguments1539 = new BitSet(new long[]{0xFF80000000000010L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1541 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_34_in_typeArguments1544 = new BitSet(new long[]{0xFF80000000000010L});
    public static final BitSet FOLLOW_typeArgument_in_typeArguments1546 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_35_in_typeArguments1550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeArgument1562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_typeArgument1567 = new BitSet(new long[]{0x0000000080000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_typeArgument1570 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_type_in_typeArgument1578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList1592 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_qualifiedNameList1595 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_qualifiedName_in_qualifiedNameList1597 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_65_in_formalParameters1611 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000084L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameters1613 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_formalParameters1616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifier_in_formalParameterDecls1628 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_formalParameterDecls1631 = new BitSet(new long[]{0x0000000000000012L,0x0000000000000008L});
    public static final BitSet FOLLOW_formalParameterDeclsRest_in_formalParameterDecls1633 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclaratorId_in_formalParameterDeclsRest1646 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_formalParameterDeclsRest1649 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_formalParameterDecls_in_formalParameterDeclsRest1651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_formalParameterDeclsRest1660 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaratorId_in_formalParameterDeclsRest1662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_methodBody1674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_qualifiedName1685 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_qualifiedName1688 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_qualifiedName1690 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_integerLiteral_in_literal1707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FloatingPointLiteral_in_literal1717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CharacterLiteral_in_literal1727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_literal1737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanLiteral_in_literal1747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_literal1757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_integerLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_booleanLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_annotations1838 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_annotation1850 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_annotationName_in_annotation1852 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_annotation1855 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F300000000F7L});
    public static final BitSet FOLLOW_elementValuePairs_in_annotation1857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_annotation1860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_annotationName1874 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_annotationName1877 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_annotationName1879 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_elementValuePair_in_elementValuePairs1893 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_elementValuePairs1896 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F300000000F3L});
    public static final BitSet FOLLOW_elementValuePair_in_elementValuePairs1898 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_Identifier_in_elementValuePair1913 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_elementValuePair1915 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F300000000F3L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair1919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_elementValue1931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_elementValue1938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_elementValueArrayInitializer_in_elementValue1945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_elementValueArrayInitializer1957 = new BitSet(new long[]{0x7F80016200000FD0L,0x0003F300000000F3L});
    public static final BitSet FOLLOW_elementValue_in_elementValueArrayInitializer1960 = new BitSet(new long[]{0x0000004400000000L});
    public static final BitSet FOLLOW_34_in_elementValueArrayInitializer1963 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F300000000F3L});
    public static final BitSet FOLLOW_elementValue_in_elementValueArrayInitializer1965 = new BitSet(new long[]{0x0000004400000000L});
    public static final BitSet FOLLOW_38_in_elementValueArrayInitializer1972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_annotationTypeDeclaration1984 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_annotationTypeDeclaration1986 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_annotationTypeDeclaration1988 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_annotationTypeBody_in_annotationTypeDeclaration1990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_annotationTypeBody2002 = new BitSet(new long[]{0x7FFFE0C048000030L,0x0000000000000080L});
    public static final BitSet FOLLOW_annotationTypeElementDeclarations_in_annotationTypeBody2005 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_annotationTypeBody2009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationTypeElementDeclaration_in_annotationTypeElementDeclarations2022 = new BitSet(new long[]{0x7FFFE08048000032L,0x0000000000000080L});
    public static final BitSet FOLLOW_annotationTypeElementDeclaration_in_annotationTypeElementDeclarations2026 = new BitSet(new long[]{0x7FFFE08048000032L,0x0000000000000080L});
    public static final BitSet FOLLOW_modifier_in_annotationTypeElementDeclaration2041 = new BitSet(new long[]{0x7FFFE08048000030L,0x0000000000000080L});
    public static final BitSet FOLLOW_annotationTypeElementRest_in_annotationTypeElementDeclaration2045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_annotationTypeElementRest2057 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_annotationMethodOrConstantRest_in_annotationTypeElementRest2059 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_annotationTypeElementRest2061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classDeclaration_in_annotationTypeElementRest2068 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_annotationTypeElementRest2070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceDeclaration_in_annotationTypeElementRest2078 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_annotationTypeElementRest2080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enumDeclaration_in_annotationTypeElementRest2088 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_annotationTypeElementRest2090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationTypeDeclaration_in_annotationTypeElementRest2098 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_annotationTypeElementRest2100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationMethodRest_in_annotationMethodOrConstantRest2113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationConstantRest_in_annotationMethodOrConstantRest2120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_annotationMethodRest2133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_annotationMethodRest2135 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_annotationMethodRest2137 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_defaultValue_in_annotationMethodRest2140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclarators_in_annotationConstantRest2157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_defaultValue2172 = new BitSet(new long[]{0x7F80012200000FD0L,0x0003F300000000F3L});
    public static final BitSet FOLLOW_elementValue_in_defaultValue2174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_block2188 = new BitSet(new long[]{0x7FFFE1E24A000FF0L,0x0003F300007DEAF3L});
    public static final BitSet FOLLOW_blockStatement_in_block2190 = new BitSet(new long[]{0x7FFFE1E24A000FF0L,0x0003F300007DEAF3L});
    public static final BitSet FOLLOW_38_in_block2193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_blockStatement2205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classOrInterfaceDeclaration_in_blockStatement2210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_blockStatement2219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifier_in_localVariableDeclaration2231 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_localVariableDeclaration2234 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclarators_in_localVariableDeclaration2236 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_localVariableDeclaration2238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_statement2250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_statement2258 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_statement2260 = new BitSet(new long[]{0x0000000002000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_statement2263 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_statement2265 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_statement2269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_statement2277 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_parExpression_in_statement2279 = new BitSet(new long[]{0x7F88012202000FD0L,0x0003F300007DEA73L});
    public static final BitSet FOLLOW_statement_in_statement2281 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_statement2291 = new BitSet(new long[]{0x7F88012202000FD0L,0x0003F300007DEA73L});
    public static final BitSet FOLLOW_statement_in_statement2293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_statement2303 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_statement2305 = new BitSet(new long[]{0x7F82010202000FD0L,0x0003F300000000F3L});
    public static final BitSet FOLLOW_forControl_in_statement2307 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_statement2309 = new BitSet(new long[]{0x7F88012202000FD0L,0x0003F300007DEA73L});
    public static final BitSet FOLLOW_statement_in_statement2311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_statement2319 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_parExpression_in_statement2321 = new BitSet(new long[]{0x7F88012202000FD0L,0x0003F300007DEA73L});
    public static final BitSet FOLLOW_statement_in_statement2323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_statement2331 = new BitSet(new long[]{0x7F88012202000FD0L,0x0003F300007DEA73L});
    public static final BitSet FOLLOW_statement_in_statement2333 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_statement2335 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_parExpression_in_statement2337 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_statement2339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_statement2347 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_statement2349 = new BitSet(new long[]{0x0000000000000000L,0x0000000000820000L});
    public static final BitSet FOLLOW_catches_in_statement2359 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_statement2361 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_statement2363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_catches_in_statement2373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_statement2383 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_statement2385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_statement2401 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_parExpression_in_statement2403 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_statement2405 = new BitSet(new long[]{0x0000004000000000L,0x0000000001000100L});
    public static final BitSet FOLLOW_switchBlockStatementGroups_in_statement2407 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_statement2409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_statement2417 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_parExpression_in_statement2419 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_statement2421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_statement2429 = new BitSet(new long[]{0x7F80010202000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_statement2431 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_statement2434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_statement2442 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_statement2444 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_statement2446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_statement2454 = new BitSet(new long[]{0x0000000002000010L});
    public static final BitSet FOLLOW_Identifier_in_statement2456 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_statement2459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_statement2467 = new BitSet(new long[]{0x0000000002000010L});
    public static final BitSet FOLLOW_Identifier_in_statement2469 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_statement2472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_statement2480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementExpression_in_statement2488 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_statement2490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_statement2498 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_statement2500 = new BitSet(new long[]{0x7F88012202000FD0L,0x0003F300007DEA73L});
    public static final BitSet FOLLOW_statement_in_statement2502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_catchClause_in_catches2514 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_catchClause_in_catches2517 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_catchClause2531 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_catchClause2533 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_formalParameter_in_catchClause2535 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_catchClause2537 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_catchClause2539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifier_in_formalParameter2550 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_formalParameter2553 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaratorId_in_formalParameter2555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_switchBlockStatementGroup_in_switchBlockStatementGroups2569 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000100L});
    public static final BitSet FOLLOW_switchLabel_in_switchBlockStatementGroup2583 = new BitSet(new long[]{0x7FFFE1A24A000FF2L,0x0003F300007DEAF3L});
    public static final BitSet FOLLOW_blockStatement_in_switchBlockStatementGroup2585 = new BitSet(new long[]{0x7FFFE1A24A000FF2L,0x0003F300007DEAF3L});
    public static final BitSet FOLLOW_88_in_switchLabel2598 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_constantExpression_in_switchLabel2600 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_switchLabel2602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_switchLabel2609 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_enumConstantName_in_switchLabel2611 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_switchLabel2613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_switchLabel2620 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_switchLabel2622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_moreStatementExpressions2635 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_statementExpression_in_moreStatementExpressions2637 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_forVarControl_in_forControl2658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forInit_in_forControl2663 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_forControl2666 = new BitSet(new long[]{0x7F80010202000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_forControl2668 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_forControl2671 = new BitSet(new long[]{0x7F80010200000FD2L,0x0003F30000000073L});
    public static final BitSet FOLLOW_forUpdate_in_forControl2673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifier_in_forInit2685 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_forInit2688 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclarators_in_forInit2690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionList_in_forInit2695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifier_in_forVarControl2707 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_forVarControl2710 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_forVarControl2712 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_forVarControl2714 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_forVarControl2716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionList_in_forUpdate2727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_parExpression2740 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_parExpression2742 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_parExpression2744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionList2761 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_expressionList2764 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_expressionList2766 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_expression_in_statementExpression2782 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_constantExpression2794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_expression2806 = new BitSet(new long[]{0x0000100A00000002L,0x00000001FE000000L});
    public static final BitSet FOLLOW_assignmentOperator_in_expression2809 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_expression2811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_assignmentOperator2825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_assignmentOperator2835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_assignmentOperator2845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_assignmentOperator2855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_assignmentOperator2865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_assignmentOperator2875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_assignmentOperator2885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_assignmentOperator2895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_assignmentOperator2905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_assignmentOperator2915 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_assignmentOperator2917 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_assignmentOperator2919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_assignmentOperator2929 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_assignmentOperator2931 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_assignmentOperator2933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_assignmentOperator2943 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_assignmentOperator2945 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_assignmentOperator2947 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_assignmentOperator2949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalExpression2965 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_63_in_conditionalExpression2969 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_conditionalExpression2971 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_conditionalExpression2973 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_conditionalExpression2975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression2994 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_conditionalOrExpression2998 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalOrExpression3000 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression3019 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_conditionalAndExpression3023 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_inclusiveOrExpression_in_conditionalAndExpression3025 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression3044 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_inclusiveOrExpression3048 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_exclusiveOrExpression_in_inclusiveOrExpression3050 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression3069 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_exclusiveOrExpression3073 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_andExpression_in_exclusiveOrExpression3075 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000000L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression3094 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_36_in_andExpression3098 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_equalityExpression_in_andExpression3100 = new BitSet(new long[]{0x0000001000000002L});
    public static final BitSet FOLLOW_instanceOfExpression_in_equalityExpression3119 = new BitSet(new long[]{0x0000000000000002L,0x0000006000000000L});
    public static final BitSet FOLLOW_set_in_equalityExpression3123 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_instanceOfExpression_in_equalityExpression3131 = new BitSet(new long[]{0x0000000000000002L,0x0000006000000000L});
    public static final BitSet FOLLOW_relationalExpression_in_instanceOfExpression3150 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_instanceOfExpression3153 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_type_in_instanceOfExpression3155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression3173 = new BitSet(new long[]{0x0000000A00000002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpression3177 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_shiftExpression_in_relationalExpression3179 = new BitSet(new long[]{0x0000000A00000002L});
    public static final BitSet FOLLOW_33_in_relationalOp3195 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_relationalOp3197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_relationalOp3201 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_relationalOp3203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_relationalOp3207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_relationalOp3211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression3228 = new BitSet(new long[]{0x0000000A00000002L});
    public static final BitSet FOLLOW_shiftOp_in_shiftExpression3232 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_additiveExpression_in_shiftExpression3234 = new BitSet(new long[]{0x0000000A00000002L});
    public static final BitSet FOLLOW_33_in_shiftOp3258 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_shiftOp3260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_shiftOp3264 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_shiftOp3266 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_shiftOp3268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_shiftOp3272 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_shiftOp3274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3292 = new BitSet(new long[]{0x0000000000000002L,0x0000030000000000L});
    public static final BitSet FOLLOW_set_in_additiveExpression3296 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3304 = new BitSet(new long[]{0x0000000000000002L,0x0000030000000000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3323 = new BitSet(new long[]{0x0000000020000002L,0x00000C0000000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3327 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3341 = new BitSet(new long[]{0x0000000020000002L,0x00000C0000000000L});
    public static final BitSet FOLLOW_104_in_unaryExpression3361 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_unaryExpression3371 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_unaryExpression3383 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_unaryExpression3395 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpressionNotPlusMinus_in_unaryExpression3407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_110_in_unaryExpressionNotPlusMinus3426 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus3428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_111_in_unaryExpressionNotPlusMinus3437 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpressionNotPlusMinus3439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_unaryExpressionNotPlusMinus3449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_in_unaryExpressionNotPlusMinus3459 = new BitSet(new long[]{0x0000020010000002L,0x0000300000000000L});
    public static final BitSet FOLLOW_selector_in_unaryExpressionNotPlusMinus3461 = new BitSet(new long[]{0x0000020010000002L,0x0000300000000000L});
    public static final BitSet FOLLOW_set_in_unaryExpressionNotPlusMinus3464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_castExpression3487 = new BitSet(new long[]{0x7F80000000000000L});
    public static final BitSet FOLLOW_primitiveType_in_castExpression3489 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_castExpression3491 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_castExpression3493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_castExpression3502 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_type_in_castExpression3505 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_castExpression3509 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_castExpression3512 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003C00000000073L});
    public static final BitSet FOLLOW_unaryExpressionNotPlusMinus_in_castExpression3514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parExpression_in_primary3531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_primary3541 = new BitSet(new long[]{0x0000000000000010L,0x0001000000000001L});
    public static final BitSet FOLLOW_explicitGenericInvocationSuffix_in_primary3552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_primary3556 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_primary3558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_primary3569 = new BitSet(new long[]{0x0000020010000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_primary3572 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_primary3574 = new BitSet(new long[]{0x0000020010000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_identifierSuffix_in_primary3579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_primary3591 = new BitSet(new long[]{0x0000000010000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_superSuffix_in_primary3593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_primary3603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_primary3613 = new BitSet(new long[]{0x7F80000200000010L});
    public static final BitSet FOLLOW_creator_in_primary3615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_primary3625 = new BitSet(new long[]{0x0000020010000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_primary3628 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_primary3630 = new BitSet(new long[]{0x0000020010000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_identifierSuffix_in_primary3635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_primary3647 = new BitSet(new long[]{0x0000020010000000L});
    public static final BitSet FOLLOW_41_in_primary3650 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_primary3652 = new BitSet(new long[]{0x0000020010000000L});
    public static final BitSet FOLLOW_28_in_primary3656 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_primary3658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_primary3668 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_primary3670 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_primary3672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_identifierSuffix3684 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_identifierSuffix3686 = new BitSet(new long[]{0x0000020010000000L});
    public static final BitSet FOLLOW_28_in_identifierSuffix3690 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_identifierSuffix3692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_identifierSuffix3698 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_identifierSuffix3700 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_identifierSuffix3702 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_arguments_in_identifierSuffix3715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_identifierSuffix3725 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_identifierSuffix3727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_identifierSuffix3737 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_explicitGenericInvocation_in_identifierSuffix3739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_identifierSuffix3749 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_identifierSuffix3751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_identifierSuffix3761 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_identifierSuffix3763 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_identifierSuffix3765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_identifierSuffix3775 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_identifierSuffix3777 = new BitSet(new long[]{0x0000000200000010L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_identifierSuffix3780 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_innerCreator_in_identifierSuffix3784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_creator3796 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_createdName_in_creator3799 = new BitSet(new long[]{0x0000020000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_arrayCreatorRest_in_creator3810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classCreatorRest_in_creator3814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_createdName3826 = new BitSet(new long[]{0x0000000210000002L});
    public static final BitSet FOLLOW_typeArguments_in_createdName3828 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_createdName3840 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_createdName3842 = new BitSet(new long[]{0x0000000210000002L});
    public static final BitSet FOLLOW_typeArguments_in_createdName3844 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_primitiveType_in_createdName3855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_innerCreator3867 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_classCreatorRest_in_innerCreator3869 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_arrayCreatorRest3880 = new BitSet(new long[]{0x7F80050200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest3894 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_41_in_arrayCreatorRest3897 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest3899 = new BitSet(new long[]{0x0000022000000000L});
    public static final BitSet FOLLOW_arrayInitializer_in_arrayCreatorRest3903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_arrayCreatorRest3917 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest3919 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_arrayCreatorRest3922 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_arrayCreatorRest3924 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest3926 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_arrayCreatorRest3931 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_arrayCreatorRest3933 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_arguments_in_classCreatorRest3956 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_classBody_in_classCreatorRest3958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_explicitGenericInvocation3971 = new BitSet(new long[]{0x0000000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_explicitGenericInvocationSuffix_in_explicitGenericInvocation3973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_nonWildcardTypeArguments3985 = new BitSet(new long[]{0x7F80000000000010L});
    public static final BitSet FOLLOW_typeList_in_nonWildcardTypeArguments3987 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_nonWildcardTypeArguments3989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_explicitGenericInvocationSuffix4001 = new BitSet(new long[]{0x0000000010000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_superSuffix_in_explicitGenericInvocationSuffix4003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_explicitGenericInvocationSuffix4010 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_explicitGenericInvocationSuffix4012 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_selector4024 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_selector4026 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_selector4029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_selector4038 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_112_in_selector4040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_selector4047 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_selector4049 = new BitSet(new long[]{0x0000000010000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_superSuffix_in_selector4051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_selector4058 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_113_in_selector4060 = new BitSet(new long[]{0x0000000200000010L});
    public static final BitSet FOLLOW_nonWildcardTypeArguments_in_selector4063 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_innerCreator_in_selector4067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_selector4074 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_selector4076 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_selector4078 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_superSuffix4090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_superSuffix4097 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_superSuffix4099 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_arguments_in_superSuffix4102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_arguments4118 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000077L});
    public static final BitSet FOLLOW_expressionList_in_arguments4120 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_arguments4123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotations_in_synpred154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodDeclaration_in_synpred38561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fieldDeclaration_in_synpred39566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_synpred851334 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_synpred851336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_synpred1201838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classDeclaration_in_synpred1352068 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_synpred1352070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceDeclaration_in_synpred1372078 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_synpred1372080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enumDeclaration_in_synpred1392088 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_synpred1392090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_synpred1442205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classOrInterfaceDeclaration_in_synpred1452210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_synpred1502291 = new BitSet(new long[]{0x7F88012202000FD0L,0x0003F300007DEA73L});
    public static final BitSet FOLLOW_statement_in_synpred1502293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_catches_in_synpred1552359 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_synpred1552361 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_block_in_synpred1552363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_catches_in_synpred1562373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_synpred1732598 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_constantExpression_in_synpred1732600 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_synpred1732602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_synpred1742609 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_enumConstantName_in_synpred1742611 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_synpred1742613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forVarControl_in_synpred1762658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableModifier_in_synpred1812685 = new BitSet(new long[]{0x7F82000000000010L,0x0000000000000080L});
    public static final BitSet FOLLOW_type_in_synpred1812688 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclarators_in_synpred1812690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentOperator_in_synpred1842809 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_synpred1842811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_synpred1952929 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_synpred1952931 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_synpred1952933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalOp_in_synpred2053177 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_shiftExpression_in_synpred2053179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_shiftOp_in_synpred2093232 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_additiveExpression_in_synpred2093234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_synpred2113264 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_synpred2113266 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_synpred2113268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_synpred2233449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_synpred2273487 = new BitSet(new long[]{0x7F80000000000000L});
    public static final BitSet FOLLOW_primitiveType_in_synpred2273489 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_synpred2273491 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_unaryExpression_in_synpred2273493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred2283505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_synpred2323572 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_synpred2323574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierSuffix_in_synpred2333579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_synpred2383628 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Identifier_in_synpred2383630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierSuffix_in_synpred2393635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_synpred2453698 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_synpred2453700 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_synpred2453702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_synpred2613922 = new BitSet(new long[]{0x7F80010200000FD0L,0x0003F30000000073L});
    public static final BitSet FOLLOW_expression_in_synpred2613924 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_synpred2613926 = new BitSet(new long[]{0x0000000000000002L});

}