<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
#foreach($column in $columns)
#if($column.is_query)
#set($dictType=$column.dictType)
#set($AttrName=$column.javaField.substring(0,1).toUpperCase() + ${column.javaField.substring(1)})
#set($parentheseIndex=$column.columnComment.indexOf("（"))
#if($parentheseIndex != -1)
#set($comment=$column.columnComment.substring(0, $parentheseIndex))
#else
#set($comment=$column.columnComment)
#end
#if($column.htmlType == "input")
      <el-form-item label="${comment}" prop="${column.javaField}">
        <el-input
          v-model="queryParams.${column.javaField}"
          placeholder="请输入${comment}"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
#elseif(($column.htmlType == "select" || $column.htmlType == "radio") && "" != $dictType)
      <el-form-item label="${comment}" prop="${column.javaField}">
        <el-select v-model="queryParams.${column.javaField}" placeholder="请选择${comment}" clearable>
          <el-option
            v-for="dict in ${dictType}"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
#elseif(($column.htmlType == "select" || $column.htmlType == "radio") && $dictType)
      <el-form-item label="${comment}" prop="${column.javaField}">
        <el-select v-model="queryParams.${column.javaField}" placeholder="请选择${comment}" clearable>
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
#elseif($column.htmlType == "datetime" && $column.queryType != "BETWEEN")
      <el-form-item label="${comment}" prop="${column.javaField}">
        <el-date-picker clearable
          v-model="queryParams.${column.javaField}"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择${comment}">
        </el-date-picker>
      </el-form-item>
#elseif($column.htmlType == "datetime" && $column.queryType == "BETWEEN")
      <el-form-item label="${comment}" style="width: 308px">
        <el-date-picker
          v-model="daterange${AttrName}"
          value-format="YYYY-MM-DD"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
#end
#end
#end
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['${moduleName}:${businessName}:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Sort"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="${businessName}List"
      row-key="${treeCode}"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
#foreach($column in $columns)
#set($javaField=$column.javaField)
#set($parentheseIndex=$column.columnComment.indexOf("（"))
#if($parentheseIndex != -1)
#set($comment=$column.columnComment.substring(0, $parentheseIndex))
#else
#set($comment=$column.columnComment)
#end
#if($column.pk)
#elseif($column.list && $column.htmlType == "datetime")
      <el-table-column label="${comment}" align="center" prop="${javaField}" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.${javaField}, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
#elseif($column.list && $column.htmlType == "imageUpload")
      <el-table-column label="${comment}" align="center" prop="${javaField}" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.${javaField}" :width="50" :height="50"/>
        </template>
      </el-table-column>
#elseif($column.list && "" != $column.dictType)
      <el-table-column label="${comment}" align="center" prop="${javaField}">
        <template #default="scope">
#if($column.htmlType == "checkbox")
          <dict-tag :options="${column.dictType}" :value="scope.row.${javaField} ? scope.row.${javaField}.split(',') : []"/>
#else
          <dict-tag :options="${column.dictType}" :value="scope.row.${javaField}"/>
#end
        </template>
      </el-table-column>
#elseif($column.list && "" != $javaField)
#if(${foreach.index} == 1)
      <el-table-column label="${comment}" prop="${javaField}" />
#else
      <el-table-column label="${comment}" align="center" prop="${javaField}" />
#end
#end
#end
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['${moduleName}:${businessName}:edit']"
          >修改</el-button>
          <el-button
            type="text"
            icon="Plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['${moduleName}:${businessName}:add']"
          >新增</el-button>
          <el-button
            type="text"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['${moduleName}:${businessName}:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改${functionName}对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="${businessName}Ref" :model="form" :rules="rules" label-width="80px">
#foreach($column in $columns)
#set($field=$column.javaField)
#if($column.insert && !$column.pk)
#if(($column.usableColumn) || (!$column.superColumn))
#set($parentheseIndex=$column.columnComment.indexOf("（"))
#if($parentheseIndex != -1)
#set($comment=$column.columnComment.substring(0, $parentheseIndex))
#else
#set($comment=$column.columnComment)
#end
#set($dictType=$column.dictType)
#if("" != $treeParentCode && $column.javaField == $treeParentCode)
        <el-form-item label="${comment}" prop="${treeParentCode}">
          <el-tree-select
            v-model="form.${treeParentCode}"
            :data="${businessName}Options"
            :props="{ value: '${treeCode}', label: '${treeName}', children: 'children' }"
            value-key="${treeCode}"
            placeholder="请选择${comment}"
            check-strictly
          />
        </el-form-item>
#elseif($column.htmlType == "input")
        <el-form-item label="${comment}" prop="${field}">
          <el-input v-model="form.${field}" placeholder="请输入${comment}" />
        </el-form-item>
#elseif($column.htmlType == "imageUpload")
        <el-form-item label="${comment}">
          <image-upload v-model="form.${field}"/>
        </el-form-item>
#elseif($column.htmlType == "fileUpload")
        <el-form-item label="${comment}">
          <file-upload v-model="form.${field}"/>
        </el-form-item>
#elseif($column.htmlType == "editor")
        <el-form-item label="${comment}">
          <editor v-model="form.${field}" :min-height="192"/>
        </el-form-item>
#elseif($column.htmlType == "select" && "" != $dictType)
        <el-form-item label="${comment}" prop="${field}">
          <el-select v-model="form.${field}" placeholder="请选择${comment}">
            <el-option
              v-for="dict in ${dictType}"
              :key="dict.value"
              :label="dict.label"
              #if($column.javaType == "Integer" || $column.javaType == "Long"):value="parseInt(dict.value)"#else:value="dict.value"#end

            ></el-option>
          </el-select>
        </el-form-item>
#elseif($column.htmlType == "select" && $dictType)
        <el-form-item label="${comment}" prop="${field}">
          <el-select v-model="form.${field}" placeholder="请选择${comment}">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
#elseif($column.htmlType == "checkbox" && "" != $dictType)
        <el-form-item label="${comment}">
          <el-checkbox-group v-model="form.${field}">
            <el-checkbox
              v-for="dict in ${dictType}"
              :key="dict.value"
              :label="dict.value">
              {{dict.label}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
#elseif($column.htmlType == "checkbox" && $dictType)
        <el-form-item label="${comment}">
          <el-checkbox-group v-model="form.${field}">
            <el-checkbox>请选择字典生成</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
#elseif($column.htmlType == "radio" && "" != $dictType)
        <el-form-item label="${comment}">
          <el-radio-group v-model="form.${field}">
            <el-radio
              v-for="dict in ${dictType}"
              :key="dict.value"
              #if($column.javaType == "Integer" || $column.javaType == "Long"):label="parseInt(dict.value)"#else:label="dict.value"#end

            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
#elseif($column.htmlType == "radio" && $dictType)
        <el-form-item label="${comment}">
          <el-radio-group v-model="form.${field}">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
#elseif($column.htmlType == "datetime")
        <el-form-item label="${comment}" prop="${field}">
          <el-date-picker clearable
            v-model="form.${field}"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="选择${comment}">
          </el-date-picker>
        </el-form-item>
#elseif($column.htmlType == "textarea")
        <el-form-item label="${comment}" prop="${field}">
          <el-input v-model="form.${field}" type="textarea" placeholder="请输入内容" />
        </el-form-item>
#end
#end
#end
#end
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="${BusinessName}">
import { list${BusinessName}, get${BusinessName}, del${BusinessName}, add${BusinessName}, update${BusinessName} } from "@/api/${moduleName}/${businessName}";

const { proxy } = getCurrentInstance();
#if(${dicts} != '')
#set($dictsNoSymbol=$dicts.replace("'", ""))
const { ${dictsNoSymbol} } = proxy.useDict(${dicts});
#end

const ${businessName}List = ref([]);
const ${businessName}Options = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const title = ref("");
const isExpandAll = ref(true);
const refreshTable = ref(true);
#foreach ($column in $columns)
#if($column.htmlType == "datetime" && $column.queryType == "BETWEEN")
#set($AttrName=$column.javaField.substring(0,1).toUpperCase() + ${column.javaField.substring(1)})
const daterange${AttrName} = ref([]);
#end
#end

const data = reactive({
  form: {},
  queryParams: {
    #foreach ($column in $columns)
#if($column.is_query)
    $column.javaField: null#if($foreach.count != $columns.size()),#end
#end
#end
  },
  rules: {
    #foreach ($column in $columns)
#if($column.required)
#set($parentheseIndex=$column.columnComment.indexOf("（"))
#if($parentheseIndex != -1)
#set($comment=$column.columnComment.substring(0, $parentheseIndex))
#else
#set($comment=$column.columnComment)
#end
    $column.javaField: [
      { required: true, message: "$comment不能为空", trigger: #if($column.htmlType == "select")"change"#else"blur"#end }
    ]#if($foreach.count != $columns.size()),#end
#end
#end
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询${functionName}列表 */
function getList() {
  loading.value = true;
#foreach ($column in $columns)
#if($column.htmlType == "datetime" && $column.queryType == "BETWEEN")
  queryParams.value.params = {};
#break
#end
#end
#foreach ($column in $columns)
#if($column.htmlType == "datetime" && $column.queryType == "BETWEEN")
#set($AttrName=$column.javaField.substring(0,1).toUpperCase() + ${column.javaField.substring(1)})
  if (null != daterange${AttrName} && '' != daterange${AttrName}) {
    queryParams.value.params["begin${AttrName}"] = daterange${AttrName}.value[0];
    queryParams.value.params["end${AttrName}"] = daterange${AttrName}.value[1];
  }
#end
#end
  list${BusinessName}(queryParams.value).then(response => {
    ${businessName}List.value = proxy.handleTree(response.data, "${treeCode}", "${treeParentCode}");
    loading.value = false;
  });
}

/** 查询${functionName}下拉树结构 */
function getTreeselect() {
  list${BusinessName}().then(response => {
    ${businessName}Options.value = [];
    const data = { ${treeCode}: 0, ${treeName}: '顶级节点', children: [] };
    data.children = proxy.handleTree(response.data, "${treeCode}", "${treeParentCode}");
    ${businessName}Options.value.push(data);
  });
}
	
// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
#foreach ($column in $columns)
#if($column.htmlType == "radio")
    $column.javaField: #if($column.javaType == "Integer" || $column.javaType == "Long")0#else"0"#end#if($foreach.count != $columns.size()),#end

#elseif($column.htmlType == "checkbox")
    $column.javaField: []#if($foreach.count != $columns.size()),#end
#else
    $column.javaField: null#if($foreach.count != $columns.size()),#end
#end
#end
  };
  proxy.resetForm("${businessName}Ref");
}

/** 搜索按钮操作 */
function handleQuery() {
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
#foreach ($column in $columns)
#if($column.htmlType == "datetime" && $column.queryType == "BETWEEN")
#set($AttrName=$column.javaField.substring(0,1).toUpperCase() + ${column.javaField.substring(1)})
  daterange${AttrName}.value = [];
#end
#end
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd(row) {
  reset();
  getTreeselect();
  if (row != null && row.${treeCode}) {
    form.value.${treeParentCode} = row.${treeCode};
  } else {
    form.value.${treeParentCode} = 0;
  }
  open.value = true;
  title.value = "添加${functionName}";
}

/** 展开/折叠操作 */
function toggleExpandAll() {
  refreshTable.value = false;
  isExpandAll.value = !isExpandAll.value;
  nextTick(() => {
    refreshTable.value = true;
  });
}

/** 修改按钮操作 */
async function handleUpdate(row) {
  reset();
  await getTreeselect();
  if (row != null) {
    form.value.${treeParentCode} = row.${treeCode};
  }
  get${BusinessName}(row.${pkColumn.javaField}).then(response => {
    form.value = response.data;
#foreach ($column in $columns)
#if($column.htmlType == "checkbox")
    form.value.$column.javaField = form.value.${column.javaField}.split(",");
#end
#end
    open.value = true;
    title.value = "修改${functionName}";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.#[[$]]#refs["${businessName}Ref"].validate(valid => {
    if (valid) {
#foreach ($column in $columns)
#if($column.htmlType == "checkbox")
      form.value.$column.javaField = form.value.${column.javaField}.join(",");
#end
#end
      if (form.value.${pkColumn.javaField} != null) {
        update${BusinessName}(form.value).then(response => {
          proxy.#[[$modal]]#.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        add${BusinessName}(form.value).then(response => {
          proxy.#[[$modal]]#.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  proxy.#[[$modal]]#.confirm('是否确认删除${functionName}编号为"' + row.${pkColumn.javaField} + '"的数据项？').then(function() {
    return del${BusinessName}(row.${pkColumn.javaField});
  }).then(() => {
    getList();
    proxy.#[[$modal]]#.msgSuccess("删除成功");
  }).catch(() => {});
}

getList();
</script>
