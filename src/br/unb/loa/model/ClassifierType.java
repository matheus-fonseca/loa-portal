package br.unb.loa.model;

public enum ClassifierType {
	ESFERA("Esfera", "temEsfera", "Esfera", "esfera"),
	ORGAO("Órgão", "temOrgao", "Orgao", "orgao"),
	UO("Unidade Orçamentária", "temUnidadeOrcamentaria", "UnidadeOrcamentaria", "uo"),
	FUNCAO("Função", "temFuncao", "Funcao", "funcao"),
	SUBFUNCAO("Subfunção", "temSubfuncao", "Subfuncao", "subfuncao"),
	PROGRAMA("Programa", "temPrograma", "Programa", "programa"),
	ACAO("Ação", "temAcao", "Acao", "acao"),
	SUBTITULO("Subtítulo", "temSubtitulo", "Subtitulo", "subtitulo"),
	PO("Plano Orçamentário", "temPlanoOrcamentario", "PlanoOrcamentario", "po"),
	CE("Categoria Econômica", "temCategoriaEconomica", "CategoriaEconomica", "catEconomica"),
	GND("Grupo de Natureza da Despesa", "temGND", "GrupoNatDespesa", "gnd"),
	MODALIDADE("Modalidade da Aplicação", "temModalidadeAplicacao", "ModalidadeAplicacao", "modalidade"),
	ELEMENTO("Elemento de Despesa", "temElementoDespesa", "ElementoDespesa", "elemento"),
	FONTE("Fonte de Recursos", "temFonteRecursos", "FonteRecursos", "fonte"),
	IDUSO("Identificador de Uso", "temIdentificadorUso", "IdentificadorUso", "idUso"),
	RP("Resultado Primário", "temResultadoPrimario", "ResultadoPrimario", "rp") ;
	
	private final String name;
	private final String property;
	private final String classifierClass;
	private final String id;
	
	private ClassifierType(final String name, final String property, final String classifierClass,final String id){
		this.name = name;
		this.property = property;
		this.classifierClass = classifierClass;
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getProperty(){
		return property;
	}
	
	public String getId(){
		return id;
	}
	
	public String getClassifierClass() {
		return classifierClass;
	}
	
	@Override
	public String toString(){
		return id;
	}
}
