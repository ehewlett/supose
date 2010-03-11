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
	:	sectiongroup NL (group EQUAL groupuserdefinition NL)* 
	;

repos
	:	sectionrepository NL permissionrule NL? (permissionrule NL?)*
	;

aliases
	:	sectionaliases NL (alias EQUAL useraliasdefinition NL)*
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
	:	ID EQUAL permission
	;

grouppermission
	:	groupreference EQUAL permission
	;

permission
	:	permission_read 
	|	permission_write
	|	permission_read_write
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

groupuserdefinition
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
