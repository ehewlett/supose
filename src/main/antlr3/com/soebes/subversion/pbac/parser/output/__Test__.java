import java.io.*;
import org.antlr.runtime.*;
import org.antlr.runtime.debug.DebugEventSocketProxy;

import com.soebes.subversion.pbac.parser.*;


public class __Test__ {

    public static void main(String args[]) throws Exception {
        SAFPLexer lex = new SAFPLexer(new ANTLRFileStream("/Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/output/__Test___input.txt", "UTF8"));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        SAFPParser g = new SAFPParser(tokens, 49100, null);
        try {
            g.prog();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}