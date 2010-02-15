

pdf:
	pdftk target/gov1.pdf target/gov2.pdf target/gov3.pdf target/gov4.pdf cat output target/gov-all.pdf

keys:
	keytool -list -keystore target/keystore 


sig:
	echo "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" > target/the-signature.xml
	echo "<opm:opmGraph xmlns:opm=\"http://openprovenance.org/model/v1.1.a\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://example.com/\">" >> target/the-signature.xml
	echo "<opm:accounts/>" >> target/the-signature.xm
	echo "<opm:processes/>" >> target/the-signature.xml
	echo "<opm:artifacts/>" >> target/the-signature.xml
	echo "<opm:agents/>" >> target/the-signature.xml
	echo "<opm:causalDependencies/>" >> target/the-signature.xml
	xpath target/gov-signature.xml //opm:signature >> target/the-signature.xml
	echo "</opm:opmGraph>" >> target/the-signature.xml





