package com.ksource.common.poi.adapter;

import com.ksource.common.bean.ServiceResponse;
import com.ksource.common.poi.Excel;

import java.util.List;

/**
 * User: zxl
 * Date: 13-4-25
 * Time: 上午10:33
 * 对象转换器：用于把excel中的一组数据(一行)转换成对象
 */
public interface ObjectAdapter {
    /**
     * 验证excel模板
     * @param excel
     * @return
     */
    ServiceResponse validate(Excel excel);

    /**
     * 把excel数据转换成对象
     * @param excel
     * @return
     */
    List convertCellToObject(Excel excel);

    /**
     * 得到excel中的数据行数，也就是能转换成多少个对象。
     * @param excel
     * @return
     */
    int getObjectCount(Excel excel);
}
