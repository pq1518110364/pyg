package com.pinyougou.sellergoods.service;

import java.util.List;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;
import entity.Result;

/**
 * 品牌接口
 * @author Administrator
 *
 */
public interface BrandService {

	public List<TbBrand> findAll();

	PageResult findPage(int page, int size);

	TbBrand findOne(Long id);

    void save(TbBrand tbBrand);

	void delete(Long [] ids);

	PageResult search(TbBrand tbBrand, int page, int size);
}
