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
svn up
cd $POS
#
#
#
cp $POS/test-documents/testPPT.ppt $WC1/testPPT.ppt
cp $POS/test-documents/testPPT.pptx $WC1/testPPT.pptx
cd $WC1
svn add testPPT.*
svn ci -m"- Added PPT files."
svn up
cd $POS
#
cp $POS/test-documents/testOpenOffice2.odt $WC1/testOpenOffice2.odt
cp $POS/test-documents/testOpenOffice3.odt $WC1/testOpenOffice3.odt
cp $POS/test-documents/testOpenOffice3.odp $WC1/testOpenOffice3.odp
cp $POS/test-documents/testOpenOffice3.ods $WC1/testOpenOffice3.ods
cp $POS/test-documents/TextArchive.tar.gz $WC1/TextArchive.tar.gz
cp $POS/test-documents/TextArchive.zip $WC1/TextArchive.zip
cd $WC1
svn add testOpenOffice*
svn add TextArchive*
svn ci -m"- Added OpenOffice files."
svn up
cd $POS
#
cp $POS/test-documents/testWORD.doc $WC1/testWORD.doc
cp $POS/test-documents/testWORD.docx $WC1/testWORD.docx
cd $WC1
svn add testWORD.*
svn ci -m"- Added DOC files."
svn up
cd $POS
#
cp $POS/test-documents/testEXCEL-formats.xls $WC1/testEXCEL-formats.xls
cp $POS/test-documents/testEXCEL-formats.xlsx $WC1/testEXCEL-formats.xlsx
cp $POS/test-documents/testEXCEL.xls $WC1/testEXCEL.xls
cp $POS/test-documents/testEXCEL.xlsx $WC1/testEXCEL.xlsx
cd $WC1
svn add testEXCEL*.*
svn ci -m"- Added XLS files."
svn up
cd $POS
#
svn cp $URL/project1/trunk/f3.txt $URL/project1/branches/B_0.0.2/f3added.txt -m"- Added f3 as f3added.txt from trunk."
#
cd $WC1
svn merge --accept mine-full $URL/project1/branches/B_0.0.2
svn ci -m"- Merge branches/B_0.0.2"
svn up
cd $POS
#
svn rm $URL/project1/branches/B_0.0.2 -m"- Removed integrated branch"
#
svn rm $URL/project1/tags/RELEASE-0.0.1 -m"- Removed unused tag."
#
rm -fr $WC1
#
svn co $URL/project1/branches/B_0.0.2@16 $WC1
cd $WC1
echo "Release 0.0.2" >>f1.txt
svn cp . $URL/project1/tags/RELEASE-0.0.2 -m"- Release 0.0.2"
cd $POS
#
rm -fr $WC1
svn co $URL/project1/trunk $WC1
cd $WC1
echo "This is a README file without extension." >README
svn add README
svn ci -m"- README added."
cd $POS
#
# Bug #306
# _is_variable problem with searching
#
cp $POS/bugs/306/test.c $WC1/test.c
cd $WC1
svn add test.c
svn ci -m"- Bug #306 added."
cd $POS
#
#
svnadmin dump $POS/$REPOS >$REPOS.dump
#
#
svn log $URL -v
