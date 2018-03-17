package com.pinyougou.sellergoods.service.impl;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.StringUtils;
import com.pinyougou.pojo.TbBrandExample;
import entity.PageResult;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {

		return brandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public TbBrand findOne(Long id) {
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void save(TbBrand tbBrand) {
		if(null==tbBrand.getId())
			brandMapper.insert(tbBrand);
		else
			brandMapper.updateByPrimaryKey(tbBrand);
	}

	@Override
	public void delete(Long [] ids) {
		for (long id : ids) {
			brandMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult search(TbBrand tbBrand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		TbBrandExample tbBrandExample = new TbBrandExample();
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();

        if (tbBrand!=null){

            if(!StringUtils.isNullOrEmpty(tbBrand.getName()))
                criteria.andNameLike("%"+tbBrand.getName()+"%");

            if (!StringUtils.isNullOrEmpty(tbBrand.getFirstChar()))
                criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
        }
       Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(tbBrandExample);
        return new PageResult(page.getTotal(),page.getResult());
	}

}
