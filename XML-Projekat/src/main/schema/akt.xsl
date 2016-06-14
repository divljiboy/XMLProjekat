<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:akt="aktovi" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    >
    <xsl:template match="/akt:Pravni_akt">
        <html>
            <head>
                <meta charset="UTF-8"/>
            </head>
            <body style="font-family: Calibri">
                <div style="text-align: center; margin-bottom: 15px">
                    <img src="http://novosadska.tv/uploads/image/news/news3675/Grb-NoviSad.jpg" height="96" width="96"/>
                </div>
                    <xsl:apply-templates select="akt:Preambula"></xsl:apply-templates>
                
                
               <xsl:for-each select="akt:Naziv">
                   <div align="center">
                       <h2>
                   <xsl:value-of select="."/>
                       </h2>
                   </div>
               </xsl:for-each>
                <xsl:apply-templates select="akt:Glavni_deo"></xsl:apply-templates>
               <p></p>
                <p> <xsl:value-of select="akt:Drzava"/></p>
               <p> <xsl:value-of select="akt:Pokrajina"/></p>
               <p> <xsl:value-of select="akt:Grad"/></p>
                <p> <xsl:value-of select="akt:Skupstina"/></p>
                    <p>  <xsl:value-of select="akt:Broj_popisa_kod_organa"/></p>
                        <p><xsl:value-of select="akt:Datum_donosenja_propisa"/></p>
                            <p><xsl:value-of select="akt:Mesto_donosenja_propisa"/></p>
                                <p> <xsl:value-of select="akt:Ovlasceno_lice/akt:Titula"/></p>
                                    <p><xsl:value-of select="akt:Ovlasceno_lice/akt:Ime"/></p>
                                        <p><xsl:value-of select="akt:Ovlasceno_lice/akt:Prezime"/></p>
                
                
                
            </body>
            
        </html>
    </xsl:template>
    <xsl:template match="akt:Preambula">
        <p align="center">
            <xsl:value-of select="."/>
        </p>
    </xsl:template>
    <xsl:template match="akt:Alineja">
        <p align="center">
            <xsl:value-of select="."/>
        </p>
    </xsl:template>
    
    
    <xsl:template match="akt:Podtacka">
        <div align="center">
            <h4>(
                <xsl:value-of select="@broj"/>
                ) </h4>
            <xsl:value-of select="text()"/>
            <xsl:choose>
                <xsl:when test="akt:Alineja">
                    <p>
                        <xsl:apply-templates select="akt:Alineja"/>
                    </p>
                </xsl:when>
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="@broj"/>
                        <xsl:value-of select="."/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    
    
    <xsl:template match="akt:Tacka">
        <div align="center">
            <h3>
                Tacka
                <xsl:value-of select="@broj"/>)
            </h3>
            <xsl:value-of select="text()"/>
            <xsl:choose>
                <xsl:when test="akt:Podtacka">
                    <p>
                        <xsl:apply-templates select="akt:Podtacka"/>
                    </p>
                </xsl:when>
                <xsl:when test="akt:Alineja">
                    <p>
                        <xsl:apply-templates select="akt:Alineja"/>
                    </p>
                </xsl:when>
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="."/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    
    
    <xsl:template match="akt:Stav">
        <div align="center">
            <xsl:value-of select="text()"/>
            <xsl:choose>
                
                 <xsl:when test="akt:Alineja">
                     <p>
                         <xsl:apply-templates select="akt:Alineja"/>
                     </p>
                 </xsl:when>
                 <xsl:when test="akt:Tacka">
                    <p>
                        <xsl:apply-templates select="akt:Tacka"/>
                    </p>
                </xsl:when>
                
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="."/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    <xsl:template match="akt:Clan">
        
        <div align="center">
            <h3>
                Clan <xsl:value-of select="@Broj_clana"/>.)
                <xsl:value-of select="@Naziv"/>
            </h3>
            <xsl:value-of select="text()"/>
            <xsl:choose>
                <xsl:when test="akt:Stav">
                    <p>
                        <xsl:apply-templates select="akt:Stav"/>
                    </p>
                </xsl:when>
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="."/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    
    
    
    <xsl:template match="akt:Odeljak">
        <div align="center">
            <h3>Odeljak
                <xsl:value-of select="@Id"/>.
                
                <xsl:value-of select="@Naziv"/>
            </h3>
            <xsl:choose>
                <xsl:when test="akt:Clan">
                    <p>
                        <xsl:apply-templates select="akt:Clan"/>
                    </p>
                </xsl:when>
                <xsl:when test="akt:Odeljak">
                    <p>
                        <xsl:apply-templates select="akt:Odeljak"/>
                    </p>
                </xsl:when>
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="."/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    
    <xsl:template match="akt:Glava">
        <div align="center">
            <h2>
                Glava
                
                <xsl:value-of select="@Naziv"/>
            </h2>
            <xsl:choose>
                <xsl:when test="akt:Odeljak">
                    <p>
                        <xsl:apply-templates select="akt:Odeljak"/>
                    </p>
                </xsl:when>
                <xsl:when test="akt:Clan">
                    <p>
                        <xsl:apply-templates select="akt:Clan"/>
                    </p>
                </xsl:when>
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="."/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    
    <xsl:template match="akt:Deo">
        <div align="center"><h2>
            <xsl:value-of select="@Redni_broj"/>
            
            <xsl:value-of select="@Naziv"/>
            <xsl:value-of select="@Id"/>
        </h2>
            <xsl:choose>
                <xsl:when test="akt:Odeljak">
                    <p>
                        <xsl:apply-templates select="akt:Odeljak"/>
                    </p>
                </xsl:when>
                <xsl:when test="akt:Clan">
                    <p>
                        <xsl:apply-templates select="akt:Clan"/>
                    </p>
                </xsl:when>
                <xsl:when test="akt:Glava">
                    <p>
                        <xsl:apply-templates select="akt:Glava"/>
                    </p>
                </xsl:when>
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="."/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    <xsl:template match="akt:Glavni_deo">
        <div align="center">
            <xsl:choose>
                <xsl:when test="akt:Deo">
                    <p>
                        <xsl:apply-templates select="akt:Deo"/>
                    </p>
                </xsl:when>
                <xsl:when test="akt:Glava">
                    <p>
                        <xsl:apply-templates select="akt:Glava"/>
                    </p>
                </xsl:when>
                <xsl:when test="akt:Odeljak">
                    <p>
                        <xsl:apply-templates select="akt:Odeljak"/>
                    </p>
                </xsl:when>
                <xsl:when test="akt:Clan">
                    <p>
                        <xsl:apply-templates select="akt:Clan"/>
                    </p>
                </xsl:when>
                <xsl:otherwise>
                    <p>
                        <xsl:value-of select="."/>
                    </p>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
    
    
    
</xsl:stylesheet>