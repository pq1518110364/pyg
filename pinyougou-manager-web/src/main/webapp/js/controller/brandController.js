//品牌控制层
app.controller('brandController' ,function($scope,$controller,brandService){
    $controller('baseController',{$scope:$scope});//继承
//读取列表数据绑定到表单中
    $scope.findAll=function(){
        brandService.findAll().success(
            function(response){
                $scope.list=response;
            }
    );
    }
        //分页
        $scope.findPage=function (page,size) {
            brandService.findPage(page,size).success(
                function(response){
                    $scope.list=response.rows;//显示当前页数据
                    $scope.paginationConf.totalItems=response.total;//更新总记录数
                }
            );
        }
        //查询单个
        $scope.findOne=function (id) {
           brandService.findOne(id).success(function (response) {

                $scope.entity=response;
            });
        }
        $scope.save=function () {
            brandService.save($scope.entity).success(
                function (response) {
                    if(response.success){
                        alert(response.message);
                        $scope.reloadList();//刷新
                    }else{
                        alert(response.message);
                    }
                }
            );
        }
        //删除选中的品牌 传递到后台 为ids的数组
        $scope.dele =function () {
            if(confirm('是否真的要删除呀')){
                brandService.dele($scope.selectIds).success(function (response) {
                    if(response.success){
                        alert('删除成功')
                        $scope.reloadList();//刷新
                    }else{
                        alert(response.message);
                    }
                });
            }
        }
        $scope.searchEntity={};
        //按条件查询页面
        $scope.search=function (page,size) {
            brandService.search(page,size,$scope.searchEntity).success(
                function (response) {
                    $scope.list=response.rows;//显示当前页数据
                    $scope.paginationConf.totalItems=response.total;//更新总记录数
                }
            );

        }
})