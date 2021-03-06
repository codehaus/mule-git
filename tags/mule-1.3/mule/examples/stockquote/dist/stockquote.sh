#! /bin/sh

# There is no need to call this if you set the MULE_HOME in your environment
if [ -z "$MULE_HOME" ] ; then
  MULE_HOME=../../..
  export MULE_HOME
fi

# Any changes to the files in ./conf will take precedence over those deployed to $MULE_HOME/lib/user
MULE_LIB=./conf
export MULE_LIB

echo "(make sure you have configured your HTTP proxy if behind a firewall - see README.txt)"
echo
echo "The Stock Quote example is available in three variations:"
echo "  1. REST"
echo "  2. SOAP"
echo "  3. WSDL"
echo "Select the one you wish to execute and press Enter..."
read i

if [ 1 = $i ]
then
    exec $MULE_HOME/bin/mule -config ./conf/rest-config.xml
elif [ 2 = $i ]
then
    exec $MULE_HOME/bin/mule -config ./conf/soap-config.xml
elif [ 3 = $i ]
then
    exec $MULE_HOME/bin/mule -config ./conf/wsdl-config.xml
fi
