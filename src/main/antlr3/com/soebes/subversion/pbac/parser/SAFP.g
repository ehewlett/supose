//
// Subversion Access File Parser (SAFP)
//
// Copyright (c) 2010 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
// Copyright (c) 2010 by Karl Heinz Marbaise
//
//
grammar SAFP;
options {
//	language = Java;
	output = AST;
}
@parser::header {
	package com.soebes.subversion.pbac.parser;

	import com.soebes.subversion.pbac.AccessLevel;
	import com.soebes.subversion.pbac.IReference;

}
@lexer::header{
	package com.soebes.subversion.pbac.parser;
}
@members {

}

/*
 * ----------------------------------------------------------------------------
 * Parser Rules 
 * ----------------------------------------------------------------------------
 */
prog
	:	(statement)*;

statement
	:	groups
	|	repos
	|	aliases
	;

groups
	:	sectiongroup NL (group EQUAL groupuserdefinition 
		{
			System.out.println("Group:" + $group.text + " def:" + $groupuserdefinition.text);
		}
		NL)* 
	;

repos @init { System.out.println("Repository"); }
	:	sectionrepository NL 
		perm=permissionrule {
			System.out.println("permission: " + $perm.text); 
		} NL?
		(
			perm1=permissionrule {
				System.out.println("permission: " + $perm1.text);
			} 
			NL?
		)*
	;

aliases @init { System.out.println("Init:Aliases"); }
	:	sectionaliases NL 
		(
			alias EQUAL useraliasdefinition NL 
			{ 
				System.out.println("ALIAS=" + $alias.text); 
				System.out.println("DEF:" + $useraliasdefinition.text); 
			}
		)*
	;

group
	:	ID
	;

alias
	:	ID
	;

sectiongroup
	:	'[' GROUPS ']' 
	;

sectionaliases
	:	'[' ALIASES ']'
	;

sectionrepository
	:	'[' repository repositorypath ']' 
		{
			if ($repository.text != null) {
				System.out.println("Repository->:" + $repository.text);
			}
			System.out.println("Repository Path:" + $repositorypath.text);
		}
	;

repository
	:	(ID ':')?
	;

repositorypath
	:	PATH
	;

permissionrule
	:	userpermission
	|	grouppermission
	;

userpermission
	:	user EQUAL permission
		{
			System.out.println("User:" + $user.text + " perm:" + $permission.perm);
		}
	;

user
	: (ID|'*')
	;

grouppermission
	:	groupreference EQUAL permission 
		{
			System.out.println("Group:" + $groupreference.text + " perm:" + $permission.perm);
		}
	;

permission returns [ AccessLevel perm; ] @init { $perm = AccessLevel.NOTHING; }
	:	permission_read { $perm = AccessLevel.READ; }
	|	permission_write { $perm = AccessLevel.WRITE; }
	|	permission_read_write { $perm = AccessLevel.READ_WRITE; }
	|	permission_nothing
	;

permission_read
	:	'r'
	|	'R'
	;

permission_write
	:	'w'
	|	'W'
	;

permission_read_write
	:	'rw'
	|	'RW'
	;

permission_nothing
	:	NL
	;

useraliasdefinition
	:	useralias (',' useralias)*
	;

useralias
	:	ID EQUAL (ID)+
	;

groupuserdefinition returns [ArrayList<IReference> gud; ] @init { $gud = new ArrayList<IReference>(); }
	:	groupuserreference ( ',' groupuserreference )*
	;

groupuserreference
	:	aliasreference
	|	groupreference
	|	userreference
	;

aliasreference
	:	'&' ID
	;

groupreference
	:	'@' ID
	;

userreference
	:	ID
	;

/*
 * ----------------------------------------------------------------------------
 * Lexer Rules 
 * ----------------------------------------------------------------------------
 */

EQUAL	:	'=';

GROUPS	:	'groups';

ALIASES	:	'aliases';

WS		: ( '\t' | ' ' )+ { $channel = HIDDEN; };

CHARACTERS:	'_'|'a'..'z'|'A'..'Z'|'.'|'-'
		;

INTEGER_DIGITS
		:	'0'..'9'+
		;

ID		:	CHARACTERS (CHARACTERS | INTEGER_DIGITS)*
		;

PATH	:	'/' (CHARACTERS | INTEGER_DIGITS | '/')*
		;

NL		:	('\r' | '\n')+
		;
