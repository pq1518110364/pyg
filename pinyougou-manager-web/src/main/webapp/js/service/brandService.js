//品牌服务层
app.service('brandService',function($http){
//读取列表数据绑定到表单中 查询所有
    this.findAll=function(){
        return $http.get('../brand/findAll.do');
    }
//无条件分页查询
    this.findPage=function (page,size) {
        return $http.get('../brand/findPage.do?page='+page+'&size='+size);
    }
   // 按id查询
    this.findOne=function (id) {
        return $http.get('../brand/findOne.do?id='+id);

    }

    //保存品牌实体
    this.save = function (entity) {
       return $http.post('../brand/save.do',entity);
    }
    //按照ids批量删除ids
    this.dele=function (ids) {
        return $http.get('../brand/delete.do?ids='+ids);
    }
    //按条件收索
    this.search=function (page,size,searchEntity) {
        return $http.post('../brand/search.do?page='+page+'&size='+size,searchEntity);
    }
//其它方法省略........
});