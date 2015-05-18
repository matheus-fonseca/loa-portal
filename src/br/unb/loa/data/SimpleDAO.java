package br.unb.loa.data;

import java.util.List;

import br.unb.loa.model.Classifier;

public interface SimpleDAO<V, T> {
	
	public List<V> searchValuesByType(T type, int year);
	public List<V> searchValuesByTypeList(List<T> typeList, int year);
	
	public List<Classifier> searchCodesByType(T type, int year);
}
