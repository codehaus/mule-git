<xsl:stylesheet
        version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:schemadoc="http://www.mulesource.org/schema/mule/schemadoc/2.0"
        >

    <!-- $Id: -->

    <!-- Generates a table of Transport features. One entry per schema -->

    <!-- Should a table header be generated -->
    <xsl:param name="header"/>

    <!-- We're rendering Wiki test -->
    <xsl:output method="text"/>

    <xsl:template match="/">
        <xsl:apply-templates select="/xsd:schema/xsd:annotation/xsd:appinfo/schemadoc:transport-features"/>
    </xsl:template>

    <xsl:template match="/xsd:schema/xsd:annotation/xsd:appinfo/schemadoc:transport-features">
        <xsl:variable name="transport"> [<xsl:value-of select="/xsd:schema/xsd:annotation/xsd:appinfo/schemadoc:short-name"/>|<xsl:value-of select="/xsd:schema/xsd:annotation/xsd:appinfo/schemadoc:page-title"/>] </xsl:variable>
        <xsl:variable name="receive">
            <xsl:choose>
                <xsl:when test="@receiveEvents = 'true'">(/)</xsl:when>
                <xsl:otherwise>(x)</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="send">
            <xsl:choose>
                <xsl:when test="@dispatchEvents = 'true'">(/)</xsl:when>
                <xsl:otherwise>(x)</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="request">
            <xsl:choose>
                <xsl:when test="@requestEvents = 'true'">(/)</xsl:when>
                <xsl:otherwise>(x)</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="response">
            <xsl:choose>
                <xsl:when test="@responseEvents = 'true'">(/)</xsl:when>
                <xsl:otherwise>(x)</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="trans">
            <xsl:choose>
                <xsl:when test="@transactions = 'true'">(/) <xsl:value-of select="@transaction-types"/></xsl:when>
                <xsl:otherwise>(x)</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="stream">
            <xsl:choose>
                <xsl:when test="@streaming = 'true'">(/)</xsl:when>
                <xsl:otherwise>(x)</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="inmeps">
        <xsl:choose>
            <xsl:when test="schemadoc:inboundMEPs/@none = 'true'">None</xsl:when>
            <xsl:otherwise>
                <xsl:if test="schemadoc:inboundMEPs/@in-only = 'true'">In-Only </xsl:if>
                <xsl:if test="schemadoc:inboundMEPs/@in-out = 'true'">In-Out </xsl:if>
                <xsl:if test="schemadoc:inboundMEPs/@in-optional-out = 'true'">In-Optional-Out</xsl:if>
            </xsl:otherwise>
        </xsl:choose>
        </xsl:variable>
        <xsl:variable name="outmeps">
        <xsl:choose>
            <xsl:when test="schemadoc:outboundMEPs/@none = 'true'">None</xsl:when>
            <xsl:otherwise>
                <xsl:if test="schemadoc:outboundMEPs/@out-only = 'true'">Out-Only </xsl:if>
                <xsl:if test="schemadoc:outboundMEPs/@out-in = 'true'">Out-In </xsl:if>
                <xsl:if test="schemadoc:outboundMEPs/@out-optional-in = 'true'">Out-Optional-In</xsl:if>
            </xsl:otherwise>
        </xsl:choose>
        </xsl:variable>
        <xsl:variable name="line">
            |<xsl:value-of select="$transport"/>|<xsl:value-of select="$receive"/>|<xsl:value-of select="$send"/>|<xsl:value-of select="$request"/>|<xsl:value-of select="$response"/>|<xsl:value-of select="$trans"/>|<xsl:value-of select="$stream"/>|<xsl:value-of select="$inmeps"/>|<xsl:value-of select="$outmeps"/>|
        </xsl:variable>
        <xsl:if test="$header = 'true'">
            ||Transport||Receive Events||Send Events||Request Events||Request/Response||Transactions||Streaming||Inbound MEPs||Outbound MEPs||
        </xsl:if>
        <xsl:value-of select="normalize-space($line)"/>
</xsl:template>
</xsl:stylesheet>
