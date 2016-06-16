<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl"
    exclude-result-prefixes="xd"
    version="1.0">
  
    <xsl:template match="/catalog">
        <html>
            <head>
                
            </head>
            <body>
                
                <xsl:for-each select="book">
                    <p>
                    <xsl:value-of select="."/>
                    </p>
                    
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>