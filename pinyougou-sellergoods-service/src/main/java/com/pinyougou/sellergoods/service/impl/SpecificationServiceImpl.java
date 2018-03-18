package com.pinyougou.sellergoods.service.impl;
import java.util.List;

import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;

	@Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	//相当于多表的连接保存
	@Override
	public void add(Specification specification) {
		TbSpecification tbSpecification= specification.getSpecification();
        specificationMapper.insert(tbSpecification);
		List<TbSpecificationOption> tbSpecificationOptions=specification.getSpecificationOptionList();
        for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptions) {
            tbSpecificationOption.setSpecId(specification.getSpecification().getId());
            tbSpecificationOptionMapper.insert(tbSpecificationOption);
        }

	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
        TbSpecification tbSpecification = specification.getSpecification();
        specificationMapper.updateByPrimaryKey(tbSpecification);
        //先删除原来的在update
        TbSpecificationOptionExample tbSpecificationOptionExample =new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        tbSpecificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
        //在进行保存
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
            tbSpecificationOption.setSpecId(specification.getSpecification().getId());
            tbSpecificationOptionMapper.insert(tbSpecificationOption);
        }
    }
	
	/**
	 * 根据ID获取实体 获得自定义的pojo Specification 页面回显 然后进行修改操作
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
        TbSpecification tbSpecification=specificationMapper.selectByPrimaryKey(id);
        Specification specification = new Specification();
        specification.setSpecification(tbSpecification);
        TbSpecificationOptionExample tbSpecificationOptionExample=new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(tbSpecificationOptionExample);
        specification.setSpecificationOptionList(tbSpecificationOptions);
        return specification;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
            specificationMapper.deleteByPrimaryKey(id);
//删除原有的规格选项
            TbSpecificationOptionExample example=new TbSpecificationOptionExample();
            com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria =
                    example.createCriteria();
            criteria.andSpecIdEqualTo(id);//指定规格 ID 为条件
            tbSpecificationOptionMapper.deleteByExample(example);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
