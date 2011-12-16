<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="xml">
    <TRANSFORMED><xsl:copy-of select="*"/></TRANSFORMED>
  </xsl:template>
</xsl:stylesheet>
