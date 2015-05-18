package br.unb.loa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.unb.loa.data.ItemDAO;
import br.unb.loa.data.SimpleDAO;
import br.unb.loa.model.Classifier;
import br.unb.loa.model.ClassifierType;
import br.unb.loa.model.Item;
import br.unb.loa.util.ClassifierUtil;
import static br.com.caelum.vraptor.view.Results.json;

@Resource
public class ClassifierController {
	
	private final Result result;
	private final Validator validator;
	private final SimpleDAO<Item, ClassifierType> classifierDAO;
	private List<Integer> loaYears; 
	
	private static final int LOA_START = 2000;
	private static final int LOA_END = 2015;
	
	private static final String CLASSIFIER_DEFAULT = ClassifierType.PROGRAMA.getId();
	private static final int YEAR_DEFAULT = 2015;
	
	private static final String ERROR_MESSAGE_OFFLINE = "Serviço indisponível, tente novamente mais tarde";
	
	public ClassifierController(Result result, Validator validator){
		this.result = result;
		this.validator = validator;
		this.classifierDAO = new ItemDAO();
		this.loaYears = new ArrayList<Integer>();
		
		for(int i=LOA_START; i<=LOA_END; i++)
			loaYears.add(new Integer(i));
	}
	
	@Path("/")
	public void index(){
		
		result.redirectTo(ClassifierController.class).classifier(CLASSIFIER_DEFAULT, YEAR_DEFAULT);
	}
	
	@Path("/classificador/{enumID}/{year:[0-9]{4}}")
	public void classifier(String enumID, int year){
		ClassifierType enumType;
		List<Item> itemList;
		
		enumType = ClassifierUtil.getClassifierTypeById(enumID);
		
		if (enumType == null) {
			send404Error();
			return;
		}

		itemList = classifierDAO.searchValuesByType(enumType, year);
		
		if(itemList == null){
			send500Error();
			return;
		}
		
		result.include("itemList", itemList);
		result.include("selectedEnum", enumType);
		result.include("selectedYear", year);
		result.include("loaYears", loaYears);
		result.include("defaultYear", YEAR_DEFAULT);
		result.include("enumList", Arrays.asList(ClassifierType.values()));
	}
	
	@Path("/classificadores")
	public void customClassifiers(){
		List<ClassifierType> enumList;
		enumList = Arrays.asList(ClassifierType.values());
		
		result.include("loaYears", loaYears);
		result.include("defaultYear", YEAR_DEFAULT);
		result.include("enumList", enumList);
		result.include("enumSize", enumList.size());
	}
	
	@Get
	@Path("/json/classificador/{enumID}/{year:[0-9]{4}}")
	public void jsonClassifier(String enumID, int year){
		ClassifierType enumType;
		List<Classifier> classifiers;
		
		enumType = ClassifierUtil.getClassifierTypeById(enumID);
		
		if (enumType == null) {
			send404Error();
			return;
		}

		classifiers = classifierDAO.searchCodesByType(enumType, year);
		
		if(classifiers == null){
			send500Error();
			return;
		}
		
		result.use(json()).from(classifiers).serialize();
	}
	
	@Post
	@Path("/classificadores/busca")
	public void item(List<String> idList, List<String> idListTextFilter, int year){
		List<ClassifierType> typeList;
		List<Item> itemList;
		
		for (int i = 0; i < idListTextFilter.size(); i++) {
			String filter = idListTextFilter.get(i);
		    if (filter != null) {
		    	filter = filter.trim();
		    }
		}
		
		System.out.println(idListTextFilter);
		
		typeList = ClassifierUtil.getClassifierTypeListByIds( idList );
		
		if (idListTextFilter != null) {
			return;
		}
		
		if(typeList.isEmpty()){
			send404Error();
			return;
		}
		
		itemList = classifierDAO.searchValuesByTypeList(typeList, year);
		
		if(itemList == null){
			send500Error();
			return;
		}
		
		result.include("selectedEnumList", typeList);
		result.include("itemList", itemList);
		result.include("selectedYear", year);
		result.include("loaYears", loaYears);
		result.include("defaultYear", YEAR_DEFAULT);
		result.include("enumList", Arrays.asList(ClassifierType.values()));
	}

	private void send404Error(){
		result.include("errorCode", 404);
		result.use(Results.http()).setStatusCode(404);
		result.use(Results.http()).sendError(404);
	}
	
	private void send500Error(){
		result.include("errorCode", 500);
		result.include("errorMessage", ERROR_MESSAGE_OFFLINE);
		result.use(Results.http()).setStatusCode(500);
		result.use(Results.http()).sendError(500);
	}
}
