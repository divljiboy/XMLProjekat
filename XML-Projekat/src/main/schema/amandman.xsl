<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:am="amandmani"
    xmlns:akt="aktovi"
    version="1.0">
    <xsl:variable name="PravniAktId" select="am:Amandman/@IdAct" />

    <xsl:template match="/am:Amandman">
        <html>
            <head>
                
            </head>
            <body>
                <div style="text-align: center; margin-bottom: 15px">
                    <img src="http://novosadska.tv/uploads/image/news/news3675/Grb-NoviSad.jpg" height="96" width="96"/>
                </div>
                
               <p align="center"> <xsl:value-of select="am:Kontekst"/></p>
               <xsl:apply-templates select="am:Podamandman"></xsl:apply-templates>
                <p align="center"> <xsl:value-of select="am:Obrazlozenje"/></p>
                <p align="center"> <xsl:value-of select="am:Ovlasceno_lice"/></p>



            </body>
        </html>
        
        
        
    </xsl:template>
    <xsl:template match="akt:Preambula">
        <p align="center">
            <xsl:apply-templates select="akt:Referenca|text()"></xsl:apply-templates>
        </p>
    </xsl:template>
    <xsl:template match="akt:Alineja">
        <p align="left" style="margin-left: 15%">
            -<xsl:value-of select="text()"/>
        </p>
    </xsl:template>
    <xsl:template match="akt:Referenca">

        <xsl:choose>
            <!--ako referencira drugi akt-->
            <xsl:when test="@ref_akt">
                <xsl:choose>
                    <!-- po hijerarhiji -->
                    <xsl:when test="@ref_tacka">
                        <a href="http://localhost:8080/#/aktDetails/usvojeni/{@ref_akt}#Tacka{@ref_tacka}"> <xsl:value-of select="text()"/></a>
                    </xsl:when>
                    <xsl:when test="@ref_stav">
                        <a href="http://localhost:8080/#/aktDetails/usvojeni/{@ref_akt}#Stav{@ref_stav}"> <xsl:value-of select="text()"/></a>
                    </xsl:when>
                    <xsl:when test="@ref_clan">
                        <a href="http://localhost:8080/#/aktDetails/usvojeni/{@ref_akt}#Clan{@ref_clan}"> <xsl:value-of select="text()"/></a>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <!-- po hijerarhiji -->
                <xsl:choose>
                    <xsl:when test="@ref_tacka">
                        <a href="http://localhost:8080/#/aktDetails/usvojeni/{$PravniAktId}#Tacka{@ref_tacka}"> <xsl:value-of select="text()"/></a>
                    </xsl:when>
                    <xsl:when test="@ref_stav">
                        <a href="http://localhost:8080/#/aktDetails/usvojeni/{$PravniAktId}#Stav{@ref_stav}"> <xsl:value-of select="text()"/></a>
                    </xsl:when>
                    <xsl:when test="@ref_clan">
                        <a href="http://localhost:8080/#/aktDetails/usvojeni/{$PravniAktId}#Clan{@ref_clan}"> <xsl:value-of select="text()"/></a>
                    </xsl:when>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>


        <!--<xsl:if test="@ref_akt">-->
            <!--<a href="http://localhost:8080/#/aktDetails/{@ref_akt}"> <xsl:value-of select="text()"/></a>-->
        <!--</xsl:if>-->
        <!--<xsl:if test="@ref_stav">-->
            <!--<a href="http://localhost:8080/#/aktDetails/{$PravniAktId}#Stav{@ref_stav}"> <xsl:value-of select="text()"/></a>-->
        <!--</xsl:if>-->
        <!--<xsl:if test="@ref_clan">-->
            <!--<a href="http://localhost:8080/#/aktDetails/{$PravniAktId}#Clan{@ref_clan}"> <xsl:value-of select="text()"/></a>-->
        <!--</xsl:if>-->
        <!--<xsl:if test="@ref_tacka">-->
            <!--<a href="http://localhost:8080/#/aktDetails/{$PravniAktId}#Tacka{@ref_tacka}"> <xsl:value-of select="text()"/></a>-->
        <!--</xsl:if>-->

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
        <div align="center" id="Clan{@broj}">
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
        <div align="center" id="Stav{@Id}">

            <p>
                <xsl:apply-templates select="akt:Alineja | akt:Tacka |akt:Referenca| text()"/>
            </p>

        </div>
    </xsl:template>
    <xsl:template match="akt:Clan">

        <div align="center" id="Clan{@Broj_clana}">
            <h3>
                Clan <xsl:value-of select="@Broj_clana"/>.
                <xsl:value-of select="@Naziv"/>
            </h3>
            <p>
                <xsl:apply-templates select="akt:Stav|text()"/>
            </p>
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
    <xsl:template match="am:Sadrzaj">
        <div align="center">
            
            
            <p>
                <xsl:apply-templates select="akt:Glavni_deo|akt:Stav|akt:Tacka|akt:Podtacka|akt:Alineja|text()"/>
            </p>
            
        </div>
    </xsl:template>
    <xsl:template match="am:Podamandman">
        <div align="center">
            
            
            <p>
                <xsl:apply-templates select="am:Operacija|am:Sadrzaj|text()"/>
            </p>
            
        </div>
    </xsl:template>
    
    
    
    
</xsl:stylesheet>