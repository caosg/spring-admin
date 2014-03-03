package com.hstyle.framework.excel.imp.validate;

import com.hstyle.framework.excel.imp.util.StringUtil;

/**
 * 校验数据不能为空
 */
public class NotNullValidator extends AbstractValidator
{
        public String processValidate()
        {
                if(StringUtil.isEmpty(getFieldValue()))
                {
                        return getCellRef() + "单元格数据 : " + getFieldValue() + ", 不可以为空!";
                }
                return OK;
        }
}