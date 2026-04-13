package com.wyf.designcreate.constant;

public interface AppConstant {
    /**
     * 精选优先级
     */
    Integer FEATURED_PRIORITY = 99;
    /**
     * 默认优先级
     */
    Integer DEFAULT_PRIORITY = 1;

    /**
     * 应用生成目录
     */
    String CODE_OUTPUT_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    /**
     * 应用部署目录
     */
    String CODE_DEPLOY_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_deploy";

    /**
     * 应用部署域名
     */
    String CODE_DEPLOY_HOST = "http://localhost:18080";

}
