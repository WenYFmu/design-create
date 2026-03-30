package com.wyf.designcreate.ai.core.saver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;

import java.io.File;

public abstract class SaveCodeFileTemplate<T> {
    //file name
    private final String FILE_SAVE_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    //处理对应类 <T>
    public void saveCodeFile(T result, Long appId) {
        if(validateInput(result)){
            //生成实际路径
            String dirPath = buildUniqueDir(appId);
            //写入文件，并校验
            saveFiles(result, dirPath);
        }
    }

    protected boolean validateInput(T result) {
        if (result == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存文件失败，请检查输入参数");
        }
        return true;
    }

    /**
     * 生成唯一路径
     *
     * @param appId 应用id
     * @return 唯一路径
     */
    protected String buildUniqueDir(Long appId) {
        if (appId == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "appId不存在");
        }
        String codeType = getCodeTypeEnum().getValue();
        String uniqueName = String.format("%s_%s", codeType, appId);
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqueName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }

    /**
     * 写入单个文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param content  文件内容
     */
    protected final void writeToFile(String filePath, String fileName, String content, boolean confirmExit) {
        if (StrUtil.isNotBlank(content) && confirmExit) {
            FileUtil.writeUtf8String(content, filePath + File.separator + fileName);
        }
    }

    protected abstract CodeTypeEnum getCodeTypeEnum();

    protected abstract void saveFiles(T result, String filePath);
}
