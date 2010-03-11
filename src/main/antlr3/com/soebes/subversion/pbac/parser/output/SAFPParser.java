// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g 2010-03-11 19:29:35

	package com.soebes.subversion.pbac.parser;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.debug.*;
import java.io.IOException;
public class SAFPParser extends DebugParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NL", "EQUAL", "ID", "GROUPS", "ALIASES", "PATH", "WS", "CHARACTERS", "INTEGER_DIGITS", "'['", "']'", "':'", "'r'", "'R'", "'w'", "'W'", "'rw'", "'RW'", "','", "'&'", "'@'"
    };
    public static final int T__23=23;
    public static final int T__20=20;
    public static final int INTEGER_DIGITS=12;
    public static final int WS=10;
    public static final int T__13=13;
    public static final int T__21=21;
    public static final int T__19=19;
    public static final int T__14=14;
    public static final int T__22=22;
    public static final int GROUPS=7;
    public static final int NL=4;
    public static final int EQUAL=5;
    public static final int T__17=17;
    public static final int ALIASES=8;
    public static final int EOF=-1;
    public static final int T__16=16;
    public static final int T__24=24;
    public static final int CHARACTERS=11;
    public static final int T__18=18;
    public static final int T__15=15;
    public static final int PATH=9;
    public static final int ID=6;

    // delegates
    // delegators

    public static final String[] ruleNames = new String[] {
        "invalidRule", "group", "statement", "permission_write", "groups", 
        "permission_nothing", "repository", "groupreference", "permissionrule", 
        "repositorypath", "prog", "sectiongroup", "repos", "userreference", 
        "groupuserreference", "useraliasdefinition", "useralias", "permission", 
        "userpermission", "aliasreference", "permission_read_write", "sectionaliases", 
        "permission_read", "alias", "groupuserdefinition", "sectionrepository", 
        "grouppermission", "aliases"
    };
     
        public int ruleLevel = 0;
        public int getRuleLevel() { return ruleLevel; }
        public void incRuleLevel() { ruleLevel++; }
        public void decRuleLevel() { ruleLevel--; }
        public SAFPParser(TokenStream input) {
            this(input, DebugEventSocketProxy.DEFAULT_DEBUGGER_PORT, new RecognizerSharedState());
        }
        public SAFPParser(TokenStream input, int port, RecognizerSharedState state) {
            super(input, state);
            DebugEventSocketProxy proxy =
                new DebugEventSocketProxy(this, port, null);
            setDebugListener(proxy);
            try {
                proxy.handshake();
            }
            catch (IOException ioe) {
                reportError(ioe);
            }
        }
    public SAFPParser(TokenStream input, DebugEventListener dbg) {
        super(input, dbg, new RecognizerSharedState());

    }
    protected boolean evalPredicate(boolean result, String predicate) {
        dbg.semanticPredicate(result, predicate);
        return result;
    }


    public String[] getTokenNames() { return SAFPParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g"; }






    // $ANTLR start "prog"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:29:1: prog : ( statement )* ;
    public final void prog() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "prog");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(29, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:30:2: ( ( statement )* )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:30:4: ( statement )*
            {
            dbg.location(30,4);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:30:4: ( statement )*
            try { dbg.enterSubRule(1);

            loop1:
            do {
                int alt1=2;
                try { dbg.enterDecision(1);

                int LA1_0 = input.LA(1);

                if ( (LA1_0==13) ) {
                    alt1=1;
                }


                } finally {dbg.exitDecision(1);}

                switch (alt1) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:30:5: statement
            	    {
            	    dbg.location(30,5);
            	    pushFollow(FOLLOW_statement_in_prog54);
            	    statement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);
            } finally {dbg.exitSubRule(1);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(30, 16);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "prog");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "prog"


    // $ANTLR start "statement"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:32:1: statement : ( groups | repos | aliases );
    public final void statement() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "statement");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(32, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:33:2: ( groups | repos | aliases )
            int alt2=3;
            try { dbg.enterDecision(2);

            int LA2_0 = input.LA(1);

            if ( (LA2_0==13) ) {
                switch ( input.LA(2) ) {
                case GROUPS:
                    {
                    alt2=1;
                    }
                    break;
                case ALIASES:
                    {
                    alt2=3;
                    }
                    break;
                case ID:
                case PATH:
                    {
                    alt2=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(2);}

            switch (alt2) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:33:4: groups
                    {
                    dbg.location(33,4);
                    pushFollow(FOLLOW_groups_in_statement65);
                    groups();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:34:4: repos
                    {
                    dbg.location(34,4);
                    pushFollow(FOLLOW_repos_in_statement70);
                    repos();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:35:4: aliases
                    {
                    dbg.location(35,4);
                    pushFollow(FOLLOW_aliases_in_statement75);
                    aliases();

                    state._fsp--;


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
        dbg.location(36, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "statement");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "statement"


    // $ANTLR start "groups"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:38:1: groups : sectiongroup NL ( group EQUAL groupuserdefinition NL )* ;
    public final void groups() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "groups");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(38, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:39:2: ( sectiongroup NL ( group EQUAL groupuserdefinition NL )* )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:39:4: sectiongroup NL ( group EQUAL groupuserdefinition NL )*
            {
            dbg.location(39,4);
            pushFollow(FOLLOW_sectiongroup_in_groups86);
            sectiongroup();

            state._fsp--;

            dbg.location(39,17);
            match(input,NL,FOLLOW_NL_in_groups88); 
            dbg.location(39,20);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:39:20: ( group EQUAL groupuserdefinition NL )*
            try { dbg.enterSubRule(3);

            loop3:
            do {
                int alt3=2;
                try { dbg.enterDecision(3);

                int LA3_0 = input.LA(1);

                if ( (LA3_0==ID) ) {
                    alt3=1;
                }


                } finally {dbg.exitDecision(3);}

                switch (alt3) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:39:21: group EQUAL groupuserdefinition NL
            	    {
            	    dbg.location(39,21);
            	    pushFollow(FOLLOW_group_in_groups91);
            	    group();

            	    state._fsp--;

            	    dbg.location(39,27);
            	    match(input,EQUAL,FOLLOW_EQUAL_in_groups93); 
            	    dbg.location(39,33);
            	    pushFollow(FOLLOW_groupuserdefinition_in_groups95);
            	    groupuserdefinition();

            	    state._fsp--;

            	    dbg.location(39,53);
            	    match(input,NL,FOLLOW_NL_in_groups97); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);
            } finally {dbg.exitSubRule(3);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(40, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "groups");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "groups"


    // $ANTLR start "repos"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:42:1: repos : sectionrepository NL permissionrule ( NL )? ( permissionrule ( NL )? )* ;
    public final void repos() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "repos");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(42, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:43:2: ( sectionrepository NL permissionrule ( NL )? ( permissionrule ( NL )? )* )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:43:4: sectionrepository NL permissionrule ( NL )? ( permissionrule ( NL )? )*
            {
            dbg.location(43,4);
            pushFollow(FOLLOW_sectionrepository_in_repos110);
            sectionrepository();

            state._fsp--;

            dbg.location(43,22);
            match(input,NL,FOLLOW_NL_in_repos112); 
            dbg.location(43,25);
            pushFollow(FOLLOW_permissionrule_in_repos114);
            permissionrule();

            state._fsp--;

            dbg.location(43,40);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:43:40: ( NL )?
            int alt4=2;
            try { dbg.enterSubRule(4);
            try { dbg.enterDecision(4);

            int LA4_0 = input.LA(1);

            if ( (LA4_0==NL) ) {
                alt4=1;
            }
            } finally {dbg.exitDecision(4);}

            switch (alt4) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:43:40: NL
                    {
                    dbg.location(43,40);
                    match(input,NL,FOLLOW_NL_in_repos116); 

                    }
                    break;

            }
            } finally {dbg.exitSubRule(4);}

            dbg.location(43,44);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:43:44: ( permissionrule ( NL )? )*
            try { dbg.enterSubRule(6);

            loop6:
            do {
                int alt6=2;
                try { dbg.enterDecision(6);

                int LA6_0 = input.LA(1);

                if ( (LA6_0==ID||LA6_0==24) ) {
                    alt6=1;
                }


                } finally {dbg.exitDecision(6);}

                switch (alt6) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:43:45: permissionrule ( NL )?
            	    {
            	    dbg.location(43,45);
            	    pushFollow(FOLLOW_permissionrule_in_repos120);
            	    permissionrule();

            	    state._fsp--;

            	    dbg.location(43,60);
            	    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:43:60: ( NL )?
            	    int alt5=2;
            	    try { dbg.enterSubRule(5);
            	    try { dbg.enterDecision(5);

            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0==NL) ) {
            	        alt5=1;
            	    }
            	    } finally {dbg.exitDecision(5);}

            	    switch (alt5) {
            	        case 1 :
            	            dbg.enterAlt(1);

            	            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:43:60: NL
            	            {
            	            dbg.location(43,60);
            	            match(input,NL,FOLLOW_NL_in_repos122); 

            	            }
            	            break;

            	    }
            	    } finally {dbg.exitSubRule(5);}


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);
            } finally {dbg.exitSubRule(6);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(44, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "repos");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "repos"


    // $ANTLR start "aliases"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:46:1: aliases : sectionaliases NL ( alias EQUAL useraliasdefinition NL )* ;
    public final void aliases() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "aliases");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(46, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:47:2: ( sectionaliases NL ( alias EQUAL useraliasdefinition NL )* )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:47:4: sectionaliases NL ( alias EQUAL useraliasdefinition NL )*
            {
            dbg.location(47,4);
            pushFollow(FOLLOW_sectionaliases_in_aliases136);
            sectionaliases();

            state._fsp--;

            dbg.location(47,19);
            match(input,NL,FOLLOW_NL_in_aliases138); 
            dbg.location(47,22);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:47:22: ( alias EQUAL useraliasdefinition NL )*
            try { dbg.enterSubRule(7);

            loop7:
            do {
                int alt7=2;
                try { dbg.enterDecision(7);

                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID) ) {
                    alt7=1;
                }


                } finally {dbg.exitDecision(7);}

                switch (alt7) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:47:23: alias EQUAL useraliasdefinition NL
            	    {
            	    dbg.location(47,23);
            	    pushFollow(FOLLOW_alias_in_aliases141);
            	    alias();

            	    state._fsp--;

            	    dbg.location(47,29);
            	    match(input,EQUAL,FOLLOW_EQUAL_in_aliases143); 
            	    dbg.location(47,35);
            	    pushFollow(FOLLOW_useraliasdefinition_in_aliases145);
            	    useraliasdefinition();

            	    state._fsp--;

            	    dbg.location(47,55);
            	    match(input,NL,FOLLOW_NL_in_aliases147); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);
            } finally {dbg.exitSubRule(7);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(48, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "aliases");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "aliases"


    // $ANTLR start "group"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:50:1: group : ID ;
    public final void group() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "group");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(50, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:51:2: ( ID )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:51:4: ID
            {
            dbg.location(51,4);
            match(input,ID,FOLLOW_ID_in_group160); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(52, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "group");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "group"


    // $ANTLR start "alias"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:54:1: alias : ID ;
    public final void alias() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "alias");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(54, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:55:2: ( ID )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:55:4: ID
            {
            dbg.location(55,4);
            match(input,ID,FOLLOW_ID_in_alias171); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(56, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "alias");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "alias"


    // $ANTLR start "sectiongroup"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:58:1: sectiongroup : '[' GROUPS ']' ;
    public final void sectiongroup() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sectiongroup");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(58, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:59:2: ( '[' GROUPS ']' )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:59:4: '[' GROUPS ']'
            {
            dbg.location(59,4);
            match(input,13,FOLLOW_13_in_sectiongroup182); 
            dbg.location(59,8);
            match(input,GROUPS,FOLLOW_GROUPS_in_sectiongroup184); 
            dbg.location(59,15);
            match(input,14,FOLLOW_14_in_sectiongroup186); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(60, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sectiongroup");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sectiongroup"


    // $ANTLR start "sectionaliases"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:62:1: sectionaliases : '[' ALIASES ']' ;
    public final void sectionaliases() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sectionaliases");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(62, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:63:2: ( '[' ALIASES ']' )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:63:4: '[' ALIASES ']'
            {
            dbg.location(63,4);
            match(input,13,FOLLOW_13_in_sectionaliases198); 
            dbg.location(63,8);
            match(input,ALIASES,FOLLOW_ALIASES_in_sectionaliases200); 
            dbg.location(63,16);
            match(input,14,FOLLOW_14_in_sectionaliases202); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(64, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sectionaliases");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sectionaliases"


    // $ANTLR start "sectionrepository"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:66:1: sectionrepository : '[' repository repositorypath ']' ;
    public final void sectionrepository() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "sectionrepository");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(66, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:67:2: ( '[' repository repositorypath ']' )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:67:4: '[' repository repositorypath ']'
            {
            dbg.location(67,4);
            match(input,13,FOLLOW_13_in_sectionrepository213); 
            dbg.location(67,8);
            pushFollow(FOLLOW_repository_in_sectionrepository215);
            repository();

            state._fsp--;

            dbg.location(67,19);
            pushFollow(FOLLOW_repositorypath_in_sectionrepository217);
            repositorypath();

            state._fsp--;

            dbg.location(67,34);
            match(input,14,FOLLOW_14_in_sectionrepository219); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(68, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "sectionrepository");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "sectionrepository"


    // $ANTLR start "repository"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:70:1: repository : ( ID ':' )? ;
    public final void repository() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "repository");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(70, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:71:2: ( ( ID ':' )? )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:71:4: ( ID ':' )?
            {
            dbg.location(71,4);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:71:4: ( ID ':' )?
            int alt8=2;
            try { dbg.enterSubRule(8);
            try { dbg.enterDecision(8);

            int LA8_0 = input.LA(1);

            if ( (LA8_0==ID) ) {
                alt8=1;
            }
            } finally {dbg.exitDecision(8);}

            switch (alt8) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:71:5: ID ':'
                    {
                    dbg.location(71,5);
                    match(input,ID,FOLLOW_ID_in_repository232); 
                    dbg.location(71,8);
                    match(input,15,FOLLOW_15_in_repository234); 

                    }
                    break;

            }
            } finally {dbg.exitSubRule(8);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(72, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "repository");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "repository"


    // $ANTLR start "repositorypath"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:74:1: repositorypath : PATH ;
    public final void repositorypath() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "repositorypath");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(74, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:75:2: ( PATH )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:75:4: PATH
            {
            dbg.location(75,4);
            match(input,PATH,FOLLOW_PATH_in_repositorypath247); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(76, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "repositorypath");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "repositorypath"


    // $ANTLR start "permissionrule"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:78:1: permissionrule : ( userpermission | grouppermission );
    public final void permissionrule() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "permissionrule");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(78, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:79:2: ( userpermission | grouppermission )
            int alt9=2;
            try { dbg.enterDecision(9);

            int LA9_0 = input.LA(1);

            if ( (LA9_0==ID) ) {
                alt9=1;
            }
            else if ( (LA9_0==24) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }
            } finally {dbg.exitDecision(9);}

            switch (alt9) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:79:4: userpermission
                    {
                    dbg.location(79,4);
                    pushFollow(FOLLOW_userpermission_in_permissionrule258);
                    userpermission();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:80:4: grouppermission
                    {
                    dbg.location(80,4);
                    pushFollow(FOLLOW_grouppermission_in_permissionrule263);
                    grouppermission();

                    state._fsp--;


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
        dbg.location(81, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "permissionrule");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "permissionrule"


    // $ANTLR start "userpermission"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:83:1: userpermission : ID EQUAL permission ;
    public final void userpermission() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "userpermission");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(83, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:84:2: ( ID EQUAL permission )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:84:4: ID EQUAL permission
            {
            dbg.location(84,4);
            match(input,ID,FOLLOW_ID_in_userpermission274); 
            dbg.location(84,7);
            match(input,EQUAL,FOLLOW_EQUAL_in_userpermission276); 
            dbg.location(84,13);
            pushFollow(FOLLOW_permission_in_userpermission278);
            permission();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(85, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "userpermission");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "userpermission"


    // $ANTLR start "grouppermission"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:87:1: grouppermission : groupreference EQUAL permission ;
    public final void grouppermission() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "grouppermission");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(87, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:88:2: ( groupreference EQUAL permission )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:88:4: groupreference EQUAL permission
            {
            dbg.location(88,4);
            pushFollow(FOLLOW_groupreference_in_grouppermission289);
            groupreference();

            state._fsp--;

            dbg.location(88,19);
            match(input,EQUAL,FOLLOW_EQUAL_in_grouppermission291); 
            dbg.location(88,25);
            pushFollow(FOLLOW_permission_in_grouppermission293);
            permission();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(89, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "grouppermission");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "grouppermission"


    // $ANTLR start "permission"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:91:1: permission : ( permission_read | permission_write | permission_read_write | permission_nothing );
    public final void permission() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "permission");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(91, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:92:2: ( permission_read | permission_write | permission_read_write | permission_nothing )
            int alt10=4;
            try { dbg.enterDecision(10);

            switch ( input.LA(1) ) {
            case 16:
            case 17:
                {
                alt10=1;
                }
                break;
            case 18:
            case 19:
                {
                alt10=2;
                }
                break;
            case 20:
            case 21:
                {
                alt10=3;
                }
                break;
            case NL:
                {
                alt10=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(10);}

            switch (alt10) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:92:4: permission_read
                    {
                    dbg.location(92,4);
                    pushFollow(FOLLOW_permission_read_in_permission304);
                    permission_read();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:93:4: permission_write
                    {
                    dbg.location(93,4);
                    pushFollow(FOLLOW_permission_write_in_permission310);
                    permission_write();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:94:4: permission_read_write
                    {
                    dbg.location(94,4);
                    pushFollow(FOLLOW_permission_read_write_in_permission315);
                    permission_read_write();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    dbg.enterAlt(4);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:95:4: permission_nothing
                    {
                    dbg.location(95,4);
                    pushFollow(FOLLOW_permission_nothing_in_permission320);
                    permission_nothing();

                    state._fsp--;


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
        dbg.location(96, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "permission");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "permission"


    // $ANTLR start "permission_read"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:98:1: permission_read : ( 'r' | 'R' );
    public final void permission_read() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "permission_read");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(98, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:99:2: ( 'r' | 'R' )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:
            {
            dbg.location(99,2);
            if ( (input.LA(1)>=16 && input.LA(1)<=17) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(101, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "permission_read");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "permission_read"


    // $ANTLR start "permission_write"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:103:1: permission_write : ( 'w' | 'W' );
    public final void permission_write() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "permission_write");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(103, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:104:2: ( 'w' | 'W' )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:
            {
            dbg.location(104,2);
            if ( (input.LA(1)>=18 && input.LA(1)<=19) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(106, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "permission_write");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "permission_write"


    // $ANTLR start "permission_read_write"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:108:1: permission_read_write : ( 'rw' | 'RW' );
    public final void permission_read_write() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "permission_read_write");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(108, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:109:2: ( 'rw' | 'RW' )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:
            {
            dbg.location(109,2);
            if ( (input.LA(1)>=20 && input.LA(1)<=21) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                dbg.recognitionException(mse);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(111, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "permission_read_write");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "permission_read_write"


    // $ANTLR start "permission_nothing"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:113:1: permission_nothing : NL ;
    public final void permission_nothing() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "permission_nothing");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(113, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:114:2: ( NL )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:114:4: NL
            {
            dbg.location(114,4);
            match(input,NL,FOLLOW_NL_in_permission_nothing379); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(115, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "permission_nothing");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "permission_nothing"


    // $ANTLR start "useraliasdefinition"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:117:1: useraliasdefinition : useralias ( ',' useralias )* ;
    public final void useraliasdefinition() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "useraliasdefinition");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(117, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:118:2: ( useralias ( ',' useralias )* )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:118:4: useralias ( ',' useralias )*
            {
            dbg.location(118,4);
            pushFollow(FOLLOW_useralias_in_useraliasdefinition390);
            useralias();

            state._fsp--;

            dbg.location(118,14);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:118:14: ( ',' useralias )*
            try { dbg.enterSubRule(11);

            loop11:
            do {
                int alt11=2;
                try { dbg.enterDecision(11);

                int LA11_0 = input.LA(1);

                if ( (LA11_0==22) ) {
                    alt11=1;
                }


                } finally {dbg.exitDecision(11);}

                switch (alt11) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:118:15: ',' useralias
            	    {
            	    dbg.location(118,15);
            	    match(input,22,FOLLOW_22_in_useraliasdefinition393); 
            	    dbg.location(118,19);
            	    pushFollow(FOLLOW_useralias_in_useraliasdefinition395);
            	    useralias();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);
            } finally {dbg.exitSubRule(11);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(119, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "useraliasdefinition");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "useraliasdefinition"


    // $ANTLR start "useralias"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:121:1: useralias : ID EQUAL ( ID )+ ;
    public final void useralias() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "useralias");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(121, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:122:2: ( ID EQUAL ( ID )+ )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:122:4: ID EQUAL ( ID )+
            {
            dbg.location(122,4);
            match(input,ID,FOLLOW_ID_in_useralias408); 
            dbg.location(122,7);
            match(input,EQUAL,FOLLOW_EQUAL_in_useralias410); 
            dbg.location(122,13);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:122:13: ( ID )+
            int cnt12=0;
            try { dbg.enterSubRule(12);

            loop12:
            do {
                int alt12=2;
                try { dbg.enterDecision(12);

                int LA12_0 = input.LA(1);

                if ( (LA12_0==ID) ) {
                    alt12=1;
                }


                } finally {dbg.exitDecision(12);}

                switch (alt12) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:122:14: ID
            	    {
            	    dbg.location(122,14);
            	    match(input,ID,FOLLOW_ID_in_useralias413); 

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt12++;
            } while (true);
            } finally {dbg.exitSubRule(12);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(123, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "useralias");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "useralias"


    // $ANTLR start "groupuserdefinition"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:125:1: groupuserdefinition : groupuserreference ( ',' groupuserreference )* ;
    public final void groupuserdefinition() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "groupuserdefinition");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(125, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:126:2: ( groupuserreference ( ',' groupuserreference )* )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:126:4: groupuserreference ( ',' groupuserreference )*
            {
            dbg.location(126,4);
            pushFollow(FOLLOW_groupuserreference_in_groupuserdefinition426);
            groupuserreference();

            state._fsp--;

            dbg.location(126,23);
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:126:23: ( ',' groupuserreference )*
            try { dbg.enterSubRule(13);

            loop13:
            do {
                int alt13=2;
                try { dbg.enterDecision(13);

                int LA13_0 = input.LA(1);

                if ( (LA13_0==22) ) {
                    alt13=1;
                }


                } finally {dbg.exitDecision(13);}

                switch (alt13) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:126:25: ',' groupuserreference
            	    {
            	    dbg.location(126,25);
            	    match(input,22,FOLLOW_22_in_groupuserdefinition430); 
            	    dbg.location(126,29);
            	    pushFollow(FOLLOW_groupuserreference_in_groupuserdefinition432);
            	    groupuserreference();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);
            } finally {dbg.exitSubRule(13);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(127, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "groupuserdefinition");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "groupuserdefinition"


    // $ANTLR start "groupuserreference"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:129:1: groupuserreference : ( aliasreference | groupreference | userreference );
    public final void groupuserreference() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "groupuserreference");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(129, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:130:2: ( aliasreference | groupreference | userreference )
            int alt14=3;
            try { dbg.enterDecision(14);

            switch ( input.LA(1) ) {
            case 23:
                {
                alt14=1;
                }
                break;
            case 24:
                {
                alt14=2;
                }
                break;
            case ID:
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(14);}

            switch (alt14) {
                case 1 :
                    dbg.enterAlt(1);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:130:4: aliasreference
                    {
                    dbg.location(130,4);
                    pushFollow(FOLLOW_aliasreference_in_groupuserreference446);
                    aliasreference();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:131:4: groupreference
                    {
                    dbg.location(131,4);
                    pushFollow(FOLLOW_groupreference_in_groupuserreference452);
                    groupreference();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:132:4: userreference
                    {
                    dbg.location(132,4);
                    pushFollow(FOLLOW_userreference_in_groupuserreference457);
                    userreference();

                    state._fsp--;


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
        dbg.location(133, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "groupuserreference");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "groupuserreference"


    // $ANTLR start "aliasreference"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:135:1: aliasreference : '&' ID ;
    public final void aliasreference() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "aliasreference");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(135, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:136:2: ( '&' ID )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:136:4: '&' ID
            {
            dbg.location(136,4);
            match(input,23,FOLLOW_23_in_aliasreference468); 
            dbg.location(136,8);
            match(input,ID,FOLLOW_ID_in_aliasreference470); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(137, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "aliasreference");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "aliasreference"


    // $ANTLR start "groupreference"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:139:1: groupreference : '@' ID ;
    public final void groupreference() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "groupreference");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(139, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:140:2: ( '@' ID )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:140:4: '@' ID
            {
            dbg.location(140,4);
            match(input,24,FOLLOW_24_in_groupreference481); 
            dbg.location(140,8);
            match(input,ID,FOLLOW_ID_in_groupreference483); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(141, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "groupreference");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "groupreference"


    // $ANTLR start "userreference"
    // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:143:1: userreference : ID ;
    public final void userreference() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "userreference");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(143, 1);

        try {
            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:144:2: ( ID )
            dbg.enterAlt(1);

            // /Users/km/ws-git/pbac/src/main/antlr3/com/soebes/subversion/pbac/parser/SAFP.g:144:4: ID
            {
            dbg.location(144,4);
            match(input,ID,FOLLOW_ID_in_userreference494); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(145, 2);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "userreference");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "userreference"

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_prog54 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_groups_in_statement65 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_repos_in_statement70 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aliases_in_statement75 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sectiongroup_in_groups86 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NL_in_groups88 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_group_in_groups91 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUAL_in_groups93 = new BitSet(new long[]{0x0000000001800040L});
    public static final BitSet FOLLOW_groupuserdefinition_in_groups95 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NL_in_groups97 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_sectionrepository_in_repos110 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NL_in_repos112 = new BitSet(new long[]{0x0000000001000040L});
    public static final BitSet FOLLOW_permissionrule_in_repos114 = new BitSet(new long[]{0x0000000001000052L});
    public static final BitSet FOLLOW_NL_in_repos116 = new BitSet(new long[]{0x0000000001000042L});
    public static final BitSet FOLLOW_permissionrule_in_repos120 = new BitSet(new long[]{0x0000000001000052L});
    public static final BitSet FOLLOW_NL_in_repos122 = new BitSet(new long[]{0x0000000001000042L});
    public static final BitSet FOLLOW_sectionaliases_in_aliases136 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NL_in_aliases138 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_alias_in_aliases141 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUAL_in_aliases143 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_useraliasdefinition_in_aliases145 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NL_in_aliases147 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_ID_in_group160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_alias171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_sectiongroup182 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_GROUPS_in_sectiongroup184 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_sectiongroup186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_sectionaliases198 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ALIASES_in_sectionaliases200 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_sectionaliases202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_sectionrepository213 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_repository_in_sectionrepository215 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_repositorypath_in_sectionrepository217 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_sectionrepository219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_repository232 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_repository234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PATH_in_repositorypath247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_userpermission_in_permissionrule258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_grouppermission_in_permissionrule263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_userpermission274 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUAL_in_userpermission276 = new BitSet(new long[]{0x00000000003F0010L});
    public static final BitSet FOLLOW_permission_in_userpermission278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_groupreference_in_grouppermission289 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUAL_in_grouppermission291 = new BitSet(new long[]{0x00000000003F0010L});
    public static final BitSet FOLLOW_permission_in_grouppermission293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_permission_read_in_permission304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_permission_write_in_permission310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_permission_read_write_in_permission315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_permission_nothing_in_permission320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_permission_read0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_permission_write0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_permission_read_write0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NL_in_permission_nothing379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_useralias_in_useraliasdefinition390 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_useraliasdefinition393 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_useralias_in_useraliasdefinition395 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_ID_in_useralias408 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EQUAL_in_useralias410 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_useralias413 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_groupuserreference_in_groupuserdefinition426 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_groupuserdefinition430 = new BitSet(new long[]{0x0000000001800040L});
    public static final BitSet FOLLOW_groupuserreference_in_groupuserdefinition432 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_aliasreference_in_groupuserreference446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_groupreference_in_groupuserreference452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_userreference_in_groupuserreference457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_aliasreference468 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_aliasreference470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_groupreference481 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_groupreference483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_userreference494 = new BitSet(new long[]{0x0000000000000002L});

}