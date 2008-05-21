<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:schemadoc="http://www.mulesource.org/schema/mule/schemadoc/2.0"
        >

    <!-- $Id: -->

    <!-- generate documentation for all elements

         to be embedded in confluence pages
    -->

    <!-- which elements should be displayed. Value can be one of:
      - all: render all elements (common first)
      - common: render connector, inbound-endpoint, outbound-endpoint and endpoint elements
      - specific: all other elements specific to the schema being processed
      - 'element name': The name of a single element to render
      -->
    <xsl:param name="show"/>

    <xsl:output method="text"/>
    <!-- TODO REPLACE -->
    <!--<xsl:include href="schemadoc-core-wiki.xsl"/>-->
    <!--href="http://www.mulesource.org/xsl/mule/2.0/single-element-wiki.xsl"/>-->
    <xsl:include href="http://svn.codehaus.org/mule/branches/mule-2.0.x/tools/schemadocs/src/main/resources/xslt/schemadoc-core-wiki.xsl"/>
    <xsl:template match="/">

        <xsl:variable name="display">
            <xsl:choose>
                <xsl:when test="$show">
                    <xsl:value-of select="$show"/>
                </xsl:when>
                <xsl:otherwise>all</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:if test="$display = 'common' or $display = 'all'">

            <xsl:choose>
                <xsl:when test="/xsd:schema/xsd:annotation/xsd:appinfo/schemadoc:page-title">
                    h1. <xsl:value-of select="/xsd:schema/xsd:annotation/xsd:appinfo/schemadoc:page-title"/>
                </xsl:when>
                <xsl:otherwise>h1. Transport (schemadoc:page-title not set)</xsl:otherwise>
            </xsl:choose>
            \\
            <xsl:value-of select="normalize-space(/xsd:schema/xsd:annotation/xsd:documentation)"/>

            <xsl:apply-templates select="/xsd:schema/xsd:element[@name='connector']" mode="single-element"/>
            <xsl:apply-templates select="/xsd:schema/xsd:element[@name='inbound-endpoint']" mode="single-element"/>
            <xsl:apply-templates select="/xsd:schema/xsd:element[@name='outbound-endpoint']" mode="single-element"/>
            <xsl:apply-templates select="/xsd:schema/xsd:element[@name='endpoint']" mode="single-element"/>
        </xsl:if>
        <xsl:if test="$display = 'specific' or $display = 'all'">
            <xsl:apply-templates select="/xsd:schema/xsd:element[
                    @name!='connector' and
                    @name!='endpoint' and
                    @name!='inbound-endpoint' and
                    @name!='outbound-endpoint' and
                    not(starts-with(@name, 'abstract'))]" mode="single-element"/>
        </xsl:if>

        <xsl:if test="$display != 'specific' and $display != 'all' and $display != 'common'">
            <xsl:apply-templates select="/xsd:schema/xsd:element[@name=$display]" mode="single-element"/>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
