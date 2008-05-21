<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        >

    <!-- $Id: -->

    <!-- generate documentation for all elements

         to be embedded in confluence pages
    -->

    <xsl:output method="text"/>
    <!-- TODO REPLACE -->
    <!--<xsl:include href="single-element-wiki.xsl"/>-->
    <!--href="http://www.mulesource.org/xsl/mule/2.0/single-element-wiki.xsl"/>-->
    <xsl:include href="http://svn.codehaus.org/mule/branches/mule-2.0.x/tools/schemadocs/src/main/resources/xslt/single-element-wiki.xsl"/>
    <xsl:template match="/">

        <!--<xsl:apply-templates select="//xsd:element[@name=$elementName]" mode="single-element-wiki"/>-->
        <!--<xsl:apply-templates select="//xsd:element[@name=$elementName]/xsd:annotation/xsd:appinfo"/>-->

        <xsl:apply-templates select="/xsd:schema/xsd:element[@name='connector']" mode="single-element-wiki"/>
        <xsl:apply-templates select="/xsd:schema/xsd:element[@name='inbound-endpoint']" mode="single-element-wiki"/>
        <xsl:apply-templates select="/xsd:schema/xsd:element[@name='outbound-endpoint']" mode="single-element-wiki"/>
        <xsl:apply-templates select="/xsd:schema/xsd:element[@name='endpoint']" mode="single-element-wiki"/>

        <xsl:apply-templates select="/xsd:schema/xsd:element[
        @name!='connector' and
        @name!='endpoint' and
        @name!='inbound-endpoint' and
        @name!='outbound-endpoint' and
        not(starts-with(@name, 'abstract'))]" mode="single-element-wiki"/>

    </xsl:template>

</xsl:stylesheet>
