package com.pinyougou.manager.controller;

import java.util.List;

import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;

@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	@RequestMapping("/findAll")
	public List<TbBrand> findAll(){
		return brandService.findAll();		
	}

	@RequestMapping("/findPage")
	public PageResult findPage(int page ,int size){
		return brandService.findPage(page, size);
	}

	@RequestMapping("/findOne")
	public TbBrand findOne(Long id){
		return brandService.findOne(id);
	}

	@RequestMapping("/save")
	public Result save(@RequestBody TbBrand tbBrand){
		try{
			brandService.save(tbBrand);
			return new Result(true,"保存成功");
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false,"保存失败成功,可能已经存在该品牌"+tbBrand.getName());
		}

	}

	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try{

			brandService.delete(ids);
			return new Result(true,"删除成功");
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false,"删除失败");
		}

	}

	//按条件查询
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbBrand tbBrand,int page,int size){
		return brandService.search(tbBrand,page,size);
	}

}
