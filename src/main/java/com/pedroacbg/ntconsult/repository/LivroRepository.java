package com.pedroacbg.ntconsult.repository;

import com.pedroacbg.ntconsult.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Long, Livro> {
}
