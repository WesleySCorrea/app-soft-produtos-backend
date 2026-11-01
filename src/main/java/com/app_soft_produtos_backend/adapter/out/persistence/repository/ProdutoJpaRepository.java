package com.app_soft_produtos_backend.adapter.out.persistence.repository;

import com.app_soft_produtos_backend.adapter.out.persistence.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoJpaRepository extends JpaRepository<ProdutoEntity, Long> {

}
