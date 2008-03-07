<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        >

    <!-- $Id: -->

    <!-- generate documentation for an entire transport or module

         to be embedded in confluence pages
    -->

    <!-- the transport we are generating docs for -->
    <xsl:param name="transport"/>
    <xsl:variable name="prefix" select="concat($transport, ':')"/>

    <xsl:output method="html"/>
    <!-- xsl:include href="schemadoc-core.xsl"/ -->
    <xsl:include href="http://svn.codehaus.org/mule/branches/mule-2.0.x/tools/schemadocs/src/main/resources/xslt/schemadoc-core.xsl"/>

    <xsl:template match="/">
        <html>
            <body>
<h2>Detailed Configuration Information</h2>
        <xsl:apply-templates select="//xsd:element[@name=concat($prefix, 'connector')]" mode="wiki-menu-connector"/>
        <xsl:apply-templates select="//xsd:element[@name=concat($prefix, 'inbound-endpoint')]" mode="wiki-menu"/>
        <xsl:apply-templates select="//xsd:element[@name=concat($prefix, 'outbound-endpoint')]" mode="wiki-menu"/>
        <xsl:apply-templates select="//xsd:element[@name=concat($prefix, 'endpoint')]" mode="wiki-menu-global"/>
        <xsl:apply-templates select="//xsd:element[
        @name!=concat($prefix, 'connector') and
        @name!=concat($prefix, 'endpoint') and
        @name!=concat($prefix, 'inbound-endpoint') and
        @name!=concat($prefix, 'outbound-endpoint') and
        starts-with(@name, $prefix)]" mode="wiki-menu"/>

        <xsl:apply-templates select="//xsd:element[@name=concat($prefix, 'connector')]" mode="single-element"/>
        <xsl:apply-templates select="//xsd:element[@name=concat($prefix, 'inbound-endpoint')]" mode="single-element"/>
        <xsl:apply-templates select="//xsd:element[@name=concat($prefix, 'outbound-endpoint')]" mode="single-element"/>
        <xsl:apply-templates select="//xsd:element[@name=concat($prefix, 'endpoint')]" mode="single-element"/>
        <xsl:apply-templates select="//xsd:element[
        @name!=concat($prefix, 'connector') and
        @name!=concat($prefix, 'endpoint') and
        @name!=concat($prefix, 'inbound-endpoint') and
        @name!=concat($prefix, 'outbound-endpoint') and
        starts-with(@name, $prefix)]" mode="single-element"/>
        <xsl:text>

</xsl:text>
            </body>
        </html>
    </xsl:template>

    <!-- note that confluence xslt doesn't have upper-case extension -->

    <xsl:template match="xsd:element[@name]" mode="wiki-menu-connector"><xsl:variable name="textname" select="translate(@name, '-', ' ')"/>
* [<xsl:value-of select="translate(substring($transport, 1, 1), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/><xsl:value-of select="substring($transport, 2)"/> connector|#<xsl:value-of select="$transport"/>-<xsl:value-of select="@name"/>]</xsl:template>

    <xsl:template match="xsd:element[@name]" mode="wiki-menu-global"><xsl:variable name="textname" select="translate(@name, '-', ' ')"/>
* [Global endpoint|#<xsl:value-of select="$transport"/>-<xsl:value-of select="@name"/>]</xsl:template>

    <xsl:template match="xsd:element[@name]" mode="wiki-menu"><xsl:variable name="textname" select="translate(substring-after(@name, ':'), '-', ' ')"/>
* [<xsl:value-of select="translate(substring($textname, 1, 1), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/><xsl:value-of select="substring($textname, 2)"/>|#<xsl:value-of select="$transport"/>-<xsl:value-of select="@name"/>]</xsl:template>

</xsl:stylesheet>
