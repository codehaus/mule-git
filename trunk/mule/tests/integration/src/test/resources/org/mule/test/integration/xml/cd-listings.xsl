<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>
    <xsl:param name="title"/>
    <xsl:param name="rating"/>
    <xsl:template match="catalog">
        <xsl:element name="cd-listings">
            <xsl:attribute name="title">
                <xsl:value-of select="$title"/>
            </xsl:attribute>
            <xsl:attribute name="rating">
                <xsl:value-of select="$rating"/>
            </xsl:attribute>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="cd">
        <xsl:element name="cd-title">
            <xsl:value-of select="title"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>