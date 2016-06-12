<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" version="1.0">
  
    <xsl:template match="/Pravni_akt">
        <html>
            <head>
                <meta charset="UTF-8"/>
            </head>
            <body style="font-family: Calibri">
                <div style="text-align: center; margin-bottom: 15px">
                    <img src="http://novosadska.tv/uploads/image/news/news3675/Grb-NoviSad.jpg" height="96" width="96"/>
                </div>
                
                <xsl:value-of select="Preambula"/>
                   
                       
                   
               
            </body>
            
        </html>
    </xsl:template>
    
    <xsl:template match="Preambula">
        <p>
            <xsl:value-of select="."/>
        </p>
    </xsl:template>
    
    
    <xsl:template match="Podtacka">
        <xsl:choose>
            <xsl:when test="count(Alineja) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Alineja"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    
    <xsl:template match="Tacka">
        <xsl:choose>
            <xsl:when test="count(Podtacka) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Podtacka"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Alineja) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Alineja"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    
    <xsl:template match="Stav">
        <xsl:choose>
            <xsl:when test="count(Tacka) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Tacka"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Alineja) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Alineja"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="Clan">
        <xsl:choose>
            <xsl:when test="count(Stav) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Stav"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    
    
    <xsl:template match="Odeljak">
        <xsl:choose>
            <xsl:when test="count(Clan) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Clan"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Odeljak) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Odeljak"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="Glava">
        <xsl:choose>
            <xsl:when test="count(Odeljak) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Odeljak"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Clan) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Clan"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="Deo">
        <xsl:choose>
            <xsl:when test="count(Odeljak) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Odeljak"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Clan) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Clan"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Glava) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Glava"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="Glavni_deo">
        <xsl:choose>
            <xsl:when test="count(Deo) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Deo"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Glava) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Glava"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Odeljak) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Odeljak"/>
                </ol>
            </xsl:when>
            <xsl:when test="count(Clan) &gt; 0">
                <ol>
                    <xsl:apply-templates select="Clan"/>
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ol>
                    <xsl:value-of select="."/>
                </ol>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    
   
    
</xsl:stylesheet>