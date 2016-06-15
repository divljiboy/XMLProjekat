<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:am="amandmani"
    xmlns:akt="aktovi"
    version="1.0">
    
    <xsl:template match="/am:Amandman">
        <html>
            <head>
                
            </head>
            <body>
                <div style="text-align: center; margin-bottom: 15px">
                    <img src="http://novosadska.tv/uploads/image/news/news3675/Grb-NoviSad.jpg" height="96" width="96"/>
                </div>
                
               <p align="center"> <xsl:value-of select="am:Kontekst"/></p>
            <p align="center"><xsl:value-of select="am:Operacija"/></p>
                <xsl:apply-templates select="am:Sadrzaj/akt:Glavni_deo"></xsl:apply-templates>
            </body>
        </html>
        
        
        
    </xsl:template>
    <xsl:template match="akt:Preambula">
        <p align="center">
            <xsl:apply-templates select="akt:Reference|text()"></xsl:apply-templates>
        </p>
    </xsl:template>
    <xsl:template match="akt:Alineja">
        <p align="left" style="margin-left: 15%">
            -<xsl:value-of select="text()"/>
        </p>
    </xsl:template>
    <xsl:template match="akt:Reference">
        
        
        <a href="asda"> <xsl:value-of select="text()"/></a>
        
    </xsl:template>
    
    
    <xsl:template match="akt:Podtacka">
        <div align="center">
            <h4>(
                <xsl:value-of select="@broj"/>
                ) </h4>
            
            <p>
                <xsl:apply-templates select="akt:Alineja|text()"/>
            </p>
            
            
        </div>
    </xsl:template>
    
    
    <xsl:template match="akt:Tacka">
        <div align="center">
            <h3>
                Tacka
                <xsl:value-of select="@broj"/>)
            </h3>
            
            <p>
                <xsl:apply-templates select="akt:Podtacka|akt:Alineja|text()"/>
            </p>
            
            
        </div>
    </xsl:template>
    
    
    <xsl:template match="akt:Stav">
        <div align="center">
            
            
            <p>
                <xsl:apply-templates select="akt:Alineja | akt:Tacka |akt:Reference| text()"/>
            </p>   
            
        </div>
    </xsl:template>
    <xsl:template match="akt:Clan">
        
        <div align="center">
            <h3>
                Clan <xsl:value-of select="@Broj_clana"/>.
                <xsl:value-of select="@Naziv"/>
            </h3>
            
            <xsl:apply-templates select="akt:Stav|text()"/>
            
        </div>
    </xsl:template>
    
    
    
    <xsl:template match="akt:Odeljak">
        <div align="center">
            <h3>
                <xsl:value-of select="@Id"/>.
                
                <xsl:value-of select="@Naziv"/>
            </h3>
            <p>
                <xsl:apply-templates select="akt:Clan|akt:Odeljak|text()"/>
            </p>
            
        </div>
    </xsl:template>
    
    <xsl:template match="akt:Glava">
        <div align="center">
            <h2>
                
                
                <xsl:value-of select="@Naziv"/>
            </h2>
            
            <p>
                <xsl:apply-templates select="akt:Odeljak|akt:Clan|text()"/>
            </p>
            
        </div>
    </xsl:template>
    
    <xsl:template match="akt:Deo">
        <div align="center">
            <h2>
                Deo 
                <xsl:value-of select="@Redni_broj"/>
                
                <xsl:value-of select="@Naziv"/>
                <xsl:value-of select="@Id"/>
            </h2>
            
            <p>
                <xsl:apply-templates select="akt:Odeljak|akt:Clan|akt:Glava|text()"/>
            </p>
            
            
        </div>
    </xsl:template>
    <xsl:template match="akt:Glavni_deo">
        <div align="center">
            
            
            <p>
                <xsl:apply-templates select="akt:Deo|akt:Glava|akt:Odeljak|akt:Clan|text()"/>
            </p>
            
        </div>
    </xsl:template>
    
    
    
</xsl:stylesheet>