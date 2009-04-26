#!/bin/bash
POS=`pwd`
REPOS=repos
WC1=wc1
WC2=wc2
#
URL=file://$POS/$REPOS
#
# Remove any existing repository, may be from a previous run
rm -fr $POS/$REPOS
# Removed any existing working copies
rm -fr $POS/$WC1
rm -fr $POS/$WC2
svnadmin create $POS/$REPOS
#
svn mkdir --parents $URL/project1 \
	$URL/project1/trunk \
	$URL/project1/tags \
	$URL/project1/branches \
	-m"- Create structure in repository"

# Create a real working copy

# Create a working copy
#
svn co $URL/project1/trunk $WC1
cd $WC1
echo "Content1" >f1.txt
echo "Content1" >f2.txt
svn add f1.txt
svn add f2.txt
svn ci -m"- First changes"
cd $POS
#
#
# Create a first Tag
svn cp $URL/project1/trunk $URL/project1/tags/RELEASE-0.0.1 -m"- First Tag for Release 0.0.1"
#
#
#
# Replace URL's for the repository with actual path's.
cat pom-template.xml | sed  "s!@@SVNCON@@!$URL/project1/!g" | sed  "s!@@SVNDEV@@!$URL/project1/trunk/!g" >$WC1/pom.xml
cd $WC1
svn add pom.xml
svn ci -m"- First Maven file."
#
# Create release cycle with Maven (Maven-Tags!)
mvn -B release:prepare
cd $POS
#
# Create a first branch
svn cp $URL/project1/trunk $URL/project1/branches/B_0.0.2 -m"- First Branch for Release 0.0.2"
#
#
svn co $URL/project1/branches/B_0.0.2 $WC2
cd $WC2
mkdir module
cd $POS
cat pom-module-template.xml >$WC2/module/pom.xml
cat pom-root-template.xml | sed  "s!@@SVNCON@@!$URL/project1/!g" | sed  "s!@@SVNDEV@@!$URL/project1/trunk/!g" >$WC2/pom.xml
cd $WC2

svn add module
svn ci -m"- Added Sub Module"
cd $POS
#
#
cd $WC1
svn update
svn merge $URL/project1/branches/B_0.0.2
svn ci -m"- Merged branches/B_0.0.2"
cd $POS
#
#
cd $WC1
svn mv f1.txt f3.txt
svn ci -m"- Rename file."
cd $POS
#
#
svnadmin dump $POS/$REPOS >$REPOS.dump
#
#
svn log $URL -v
