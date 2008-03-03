<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        >

    <!-- $Id: -->

    <!-- generate text to cut+paste into the wiki and links document

         this should be run on a transport's schema
    -->

    <!-- the transport we are generating docs for -->
    <xsl:param name="transport"/>

    <xsl:output method="text"/>

    <xsl:template match="/">
h2. Detailed Configuration Information
        <xsl:apply-templates select="//xsd:element" mode="wiki-menu"/>
            
        <xsl:apply-templates select="//xsd:element" mode="wiki-content"/>
    </xsl:template>

    <xsl:template match="xsd:element[@name]" mode="wiki-menu"><xsl:variable name="textname" select="translate(@name, '-', ' ')"/>
* [<xsl:value-of select="upper-case(substring($textname, 1, 1))"/><xsl:value-of select="substring($textname, 2)"/>|#<xsl:value-of select="$transport"/>-<xsl:value-of select="@name"/>]</xsl:template>

    <xsl:template match="xsd:element[@name]" mode="wiki-content">

{cache:showDate=true|showRefresh=true}
{xslt:style=#http://svn.codehaus.org/mule/branches/mule-2.0.x/tools/schemadocs/src/main/resources/single-doc-html.xsl|source=#http://dev.mulesource.com/docs/xsd-doc/normalized.xsd|elementName=<xsl:value-of select="$transport"/>:<xsl:value-of select="@name"/>}
{xslt}
{cache}

//</xsl:template>

</xsl:stylesheet>
