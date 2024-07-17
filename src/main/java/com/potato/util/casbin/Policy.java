package com.potato.util.casbin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

  //想要访问资源的用户或者角色
  private String sub;

  //将要访问的资源，可以使用*作为通配符
  private String obj;

  //用户对资源操作的方法，比如POST,GET,DELETE,PUT
  private String act;
}
