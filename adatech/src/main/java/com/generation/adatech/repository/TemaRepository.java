package com.generation.adatech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.adatech.model.TemaModel;

@Repository
public interface TemaRepository extends JpaRepository<TemaModel, Long>{
	public List<TemaModel> findAllByDescricaoContainingIgnoreCase(String descricao);

}