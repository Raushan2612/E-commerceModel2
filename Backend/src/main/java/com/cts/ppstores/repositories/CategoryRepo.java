package com.cts.ppstores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ppstores.models.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	public Category findByName(String name);
}