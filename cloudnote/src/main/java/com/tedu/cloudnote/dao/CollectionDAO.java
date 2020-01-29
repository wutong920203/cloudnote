package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.Admin;

public interface CollectionDAO {
	public List<Admin> findAllUser();
	public Admin findById(String userId);
}
