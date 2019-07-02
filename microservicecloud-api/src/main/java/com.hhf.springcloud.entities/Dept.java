package com.hhf.springcloud.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//@AllArgsConstructor //全参构造注解
@Data    //set设置值/get获取值注解
@NoArgsConstructor //无参数构造注解
@Accessors(chain=true) //链式访问
public class Dept {
    //主键
    private Long deptno;
    //部门名称
    private String 	dname;
    //来自那个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库。
    private String 	db_source;

    public Dept(String dname)
    {
        super();
        this.dname = dname;
    }
}
