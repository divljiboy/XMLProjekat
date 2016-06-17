<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:am="amandmani"
	xmlns:akt="aktovi" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<fo:root>

			<fo:layout-master-set>
				<fo:simple-page-master master-name="my-page">
					<fo:region-body margin="1in" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="my-page">
				<fo:flow flow-name="xsl-region-body">
					<fo:block text-align="center" font-family="Calibri">
						<fo:block>
							<xsl:value-of select="am:Amandman/am:Kontekst" />
						</fo:block>

					

						<fo:block>
							<xsl:apply-templates select="am:Amandman/am:Podamandman"></xsl:apply-templates>
						</fo:block>
						<fo:block>
						<xsl:value-of select="am:Amandman/am:Obrazlozenje"></xsl:value-of>
						</fo:block>
						<fo:block>
						<xsl:value-of select="am:Amandman/am:Ovlasceno_lice"></xsl:value-of>
						</fo:block>
					</fo:block>



				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="akt:Preambula">
		<fo:block>
			<xsl:apply-templates select="akt:Reference|text()"></xsl:apply-templates>
		</fo:block>
	</xsl:template>

	<xsl:template match="akt:Alineja">

		<fo:block text-align="center" font-size="8pt">
			<xsl:value-of select="text()" />
		</fo:block>

	</xsl:template>
	<xsl:template match="akt:Reference">
		<xsl:value-of select="text()"></xsl:value-of>



	</xsl:template>


	<xsl:template match="akt:Podtacka">
		<fo:block text-align="center">
			<fo:block font-size="14pt">
				(
				<xsl:value-of select="@broj" />
				)
			</fo:block>

			<fo:block font-size="12pt">
				<xsl:apply-templates select="akt:Alineja|text()" />
			</fo:block>


		</fo:block>
	</xsl:template>


	<xsl:template match="akt:Tacka">
		
			<fo:block font-size="12pt" text-align="left" margin-left="50px" margin-right="50px">Tacka <xsl:value-of select="@broj" />)
			
			<xsl:apply-templates select="akt:Podtacka|akt:Alineja|text()" />
			
			</fo:block >
			
				
		
	</xsl:template>


	<xsl:template match="akt:Stav">
		<fo:block>
			<xsl:apply-templates select="akt:Alineja | akt:Tacka |akt:Reference| text()" />
		</fo:block>
	</xsl:template>
	<xsl:template match="akt:Clan">

		<fo:block>
			<fo:block font-size="14pt">
				Clan
				<xsl:value-of select="@Broj_clana" />
				.
				<xsl:value-of select="@Naziv" />
			</fo:block>
			<fo:block>
				<xsl:apply-templates select="akt:Stav|text()" />
			</fo:block>
		</fo:block>
	</xsl:template>



	<xsl:template match="akt:Odeljak">
		<fo:block>
			<fo:block font-size="14pt">
				<xsl:value-of select="@Id" />
				.

				<xsl:value-of select="@Naziv" />
			</fo:block>

			<xsl:apply-templates select="akt:Clan|akt:Odeljak|text()" />


		</fo:block>
	</xsl:template>

	<xsl:template match="akt:Glava">
		<fo:block>
			<fo:block font-size="17pt">
				<xsl:value-of select="@Naziv" />
			</fo:block>


			<xsl:apply-templates select="akt:Odeljak|akt:Clan|text()" />


		</fo:block>
	</xsl:template>

	<xsl:template match="akt:Deo">
		<fo:block>
			<fo:block font-size="19pt">
				Deo
				<xsl:value-of select="@Redni_broj" />

				<xsl:value-of select="@Naziv" />
				<xsl:value-of select="@Id" />
			</fo:block>


			<xsl:apply-templates select="akt:Odeljak|akt:Clan|akt:Glava|text()" />



		</fo:block>
	</xsl:template>
	<xsl:template match="akt:Glavni_deo">
		<fo:block>



			<xsl:apply-templates select="akt:Deo|akt:Glava|akt:Odeljak|akt:Clan|text()" />


		</fo:block>
	</xsl:template>
	<xsl:template match="am:Podamandman">
		<fo:block>



			<xsl:apply-templates select="am:TOperacija|am:Sadrzaj|text()" />


		</fo:block>
	</xsl:template>
	<xsl:template match="am:Sadrzaj">
		<fo:block>



			<xsl:apply-templates select="akt:Glavni_deo|akt:Stav|akt:Tacka|akt:Podtacka|akt:Alineja|text()" />


		</fo:block>
	</xsl:template>
</xsl:stylesheet>





