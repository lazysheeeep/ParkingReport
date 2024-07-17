package com.potato.util.casbin;

import com.potato.config.CasbinAdapterConfig;
import lombok.Getter;
import org.casbin.adapter.JDBCAdapter;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnforceFactory implements InitializingBean {

  @Getter
  private Enforcer enforcer;

  @Autowired
  private CasbinAdapterConfig casbinAdapterConfig;
  private static CasbinAdapterConfig config;


  @Override
  public void afterPropertiesSet() throws Exception {

    config = casbinAdapterConfig;
    JDBCAdapter jdbcAdapter = config.jdbcAdapter();
    enforcer = new Enforcer(config.getModelPath(),jdbcAdapter);
    enforcer.loadPolicy();

  }

  public boolean addPolicy(Policy policy) {

    boolean addPolicy = enforcer.addPolicy(policy.getSub(), policy.getObj(), policy.getAct());
    enforcer.savePolicy();
    return addPolicy;

  }

  public boolean removePolicy(Policy policy) {

    boolean removePolicy = enforcer.removePolicy(policy.getSub(), policy.getObj(), policy.getAct());
    enforcer.savePolicy();
    return removePolicy;

  }

}
