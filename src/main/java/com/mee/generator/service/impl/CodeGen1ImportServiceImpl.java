package com.mee.generator.service.impl;

import com.mee.generator.entity.GenTable;
import com.mee.generator.entity.GenTableColumn;
import com.mee.generator.enums.TplCategoryEnum;
import com.mee.generator.mapper.GenTableColumnMapper;
import com.mee.generator.mapper.GenTableMapper;
import com.mee.generator.util.DateUtil;
import com.mee.generator.util.GenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
* 数据导入处理
* @className    CodeGen1ImportServiceImpl
* @author       shadow
* @date         2023/6/15 17:18
* @version      1.0
*/
@Service
public class CodeGen1ImportServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(CodeGen1ImportServiceImpl.class);

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;
    @Autowired
    private SeqGenServiceImpl seqGenService;

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void importGenTable(List<GenTable> tableList) {
        final String operator_name = "AUTO";
        final LocalDateTime now = DateUtil.now();
        for (GenTable table : tableList){
            String tableName = table.getTable_name();
            GenUtils.initTable(table, operator_name);
            table.setTable_id(seqGenService.shortKey());
            table.setCreate_by(operator_name);
            table.setCreate_time(now);
            if(null==table.getTpl_category()){
                // 设置默认值
                table.setTpl_category(TplCategoryEnum.CRUD.value);
            }
            int insert_count = genTableMapper.insertGenTable(table);
            if ( insert_count > 0 ){
                // 保存列信息
                List<GenTableColumn> table_columns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                for (GenTableColumn column : table_columns) {
                    GenUtils.initColumnField(column, table);
                    column.setColumn_id(seqGenService.shortKey());
                    column.setCreate_time(now);
                    genTableColumnMapper.insertGenTableColumn(column);
                }
            }
        }
    }

}
