<template>
    <el-dialog class="level-one-dialog" v-dialogDrag title="成品加晒申请" :visible.sync="dialog" :close-on-click-modal=false v-loading="loading">
        <el-scrollbar style="height:498px;margin-right:-20px;" wrapStyle="padding-right:20px;overflow-x:hidden;" viewStyle="">
            <el-row class="block-area">
                <el-row class="block-header">
                    <span class="block-title">基本信息</span>
                </el-row>
                <el-row class="block-body">
                    <el-form :model="form" :rules="rules" ref="form">
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="项目名称(编号)" :label-width="formLabelWidth" prop="project_id">
                                    <el-select
                                            :disabled="disabled"
                                            style="width: 100%"
                                            v-model="form.project_id"
                                            filterable
                                            remote
                                            reserve-keyword
                                            placeholder="请输入关键词"
                                            :remote-method="remoteMethod"
                                            @change="projectSelected(form.project_id)"
                                            :loading="projectsLoading">
                                        <el-option
                                                v-for="item in projects"
                                                :key="item.value"
                                                :label="item.label"
                                                :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="申请单号" :label-width="formLabelWidth">
                                    <el-input :disabled="disabled" v-model="form.apply_no" maxlength="30" autocomplete="off" readonly></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="设计阶段" :label-width="formLabelWidth" prop="design_phase">
                                    <el-select :disabled="disabled"
                                                v-model="form.design_phase"
                                                value-key="id"
                                                style="width: 100%">
                                        <el-option  v-for="item in stages"
                                                    :key="item.id"
                                                    :label="item.stage_name"
                                                    :value="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="是否加急" :label-width="formLabelWidth">
                                    <el-select :disabled="disabled"
                                                v-model="form.is_urgent"
                                                style="width: 100%">
                                        <el-option v-for="item in select"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="计划完成时间" :label-width="formLabelWidth" prop="plan_time">
                                    <el-date-picker
                                            style="width: 100%"
                                            :disabled="disabled"
                                            v-model="form.plan_time"
                                            type="datetime"
                                            placeholder="请选择日期">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="出版份数" :label-width="formLabelWidth" prop="publish_total">
                                    <el-input :disabled="disabled" v-model.number="form.publish_total" maxlength="15" autocomplete="off"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="项目/部门负责人" :label-width="formLabelWidth" prop="project_leader">
                                    <el-input :disabled="disabled" v-model="form.project_leader_name" maxlength="30" autocomplete="off" readonly @click.native="clickPeople"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="委托人" :label-width="formLabelWidth">
                                    <el-input :disabled="disabled" v-model="form.client_name" maxlength="30" autocomplete="off" readonly></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="委托日期" :label-width="formLabelWidth">
                                    <el-date-picker
                                            style="width: 100%"
                                            readonly
                                            :disabled="disabled"
                                            v-model="form.client_time"
                                            type="datetime"
                                            placeholder="请选择日期">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="委托部门" :label-width="formLabelWidth">
                                    <el-input :disabled="disabled" v-model="form.client_org_name" maxlength="30" autocomplete="off" readonly></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="备注" :label-width="formLabelWidth">
                                    <el-input :disabled="disabled" type="textarea" v-model="form.remark" maxlength="500" autocomplete="off"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-row>
            </el-row>
            <el-row class="block-area">
                <el-row class="block-header">
                    <span class="block-title">成果列表</span>
                </el-row>
                <el-row class="block-tool">
                    <el-button type="primary" @click="addResult" :disabled="disabled">添加</el-button>
                    <el-button type="danger" :disabled="disabled" @click="deleteFinish">删除</el-button>
                    <el-button type="success" v-if="this.type !== 'add'" @click="download">下载</el-button>
                </el-row>
                    <el-row class="block-body">
                        <VmBaseTable
                                :setTableHigh="true"
                                @on-select-change="selectResult"
                                ref="result_add_model"
                                :data="resultTable"
                                :columns="resultColumns"
                                showCheck
                                showIndex
                                :tableHigh="tableHigh"
                                :noPage="true">
                        </VmBaseTable>
                    </el-row>
            </el-row>
            <el-row class="block-area">
                <el-row class="block-header">
                    <span class="block-title">配送信息</span>
                </el-row>
                <el-row class="block-tool">
                    <el-button type="primary" :disabled="disabled" @click="addDelivery">增加</el-button>
                    <el-button type="danger" :disabled="disabled" @click="deleteDelivery">删除</el-button>
                </el-row>
                <el-row class="block-body">
                    <VmBaseTable
                            :setTableHigh="true"
                            :selectable="selectable"
                            ref="send_model"
                            :data="sendTable"
                            :columns="sendColumns"
                            showCheck
                            showIndex
                            :tableHigh="tableHigh"
                            @on-select-change="selectChange"
                            :noPage="true">
                    </VmBaseTable>
                </el-row>
            </el-row>

            <!--添加弹框的底部按钮-->
            <el-row style="margin-left: 75%">
                <el-col :span="12">

                </el-col>
            </el-row>
            <!--审批弹框的底部按钮-->
            <el-row style="margin-left: 85%">
                <el-col :span="12">
                </el-col>
                <el-col :span="12">
                </el-col>
            </el-row>
        </el-scrollbar>
        <div slot="footer" class="dialog-footer">
            <!--添加弹框的底部按钮-->
            <el-row v-show="this.type === 'add' || this.type === 'edit'">
                <el-button @click="click('form')" type="primary">发起申请</el-button>
            </el-row>
            <!--审批弹框的底部按钮-->
            <el-row v-show="this.type === 'audit'">
                <el-button type="primary" @click="click('form')">通过</el-button>
                <el-button type="primary" @click="dialog = false">驳回</el-button>
            </el-row>
        </div>
    </el-dialog>
</template>

<script>
import VmBaseTable from '../../base/base-table'
export default {
    name: "add-application-model",
    data () {
        // 自定义校验
        var checkNum = (rule, value, callback) => {
            if (value === '') {
                return callback(new Error('请输入出版份数'));
            } else {
                if (!Number.isInteger(value)) {
                    return callback(new Error('请输入数字'));
                }
                if (value < rule.min_num) {
                    callback(new Error('必须为正数'));
                }
                callback();
            }
        };
        return {
            fixedIndex: 'left',
            resultTypeName: [],
            resultLevelName: [],
            selection: [],
            loading: false,
            projectInfo: {
                projectId: '',
                projectName: ''
            },
            projects: [],
            project: [],
            projectsLoading: false,
            disabled: false,
            resultTable: [],
            sendTable: [],
            userObj: {},
            users:[],
            index: null,
            indexes: [],
            userObjCopy: {},
            stages: [],
            form: {
                id: 0,
                project_id: '',
                volume_id: '',
                draw_count: 0,
                is_urgent: 0,
                published_seal_type: 0,
                draw_fold_type: 0,
                remark: '',
                project_name: '',
                project_no: '',
                design_phase: '',
                volume_name: '',
                volume_no: '',
                apply_no: '',
                plan_time: new Date(),
                publish_total: 0,
                project_leader: '',
                project_leader_name: '',
                client: '',
                client_name: '',
                client_time: new Date(),
                client_org: '',
                client_org_name: '',
                state:  0,
                type: 0,
                dim_type: '',
                file_id: 0,
                name: '',
                result_ids: [],
                delivery_list: []
            },
            table_options: [],
            tableHigh: '34vh',
            dialog: false,
            type: '',
            formLabelWidth: '140px',
            resultColumns: [
                {
                    label: '卷册名称(编号)',
                    prop: 'result_no',
                    style: 'center',
                    minWidth: '100'
                }, {
                    label: '成果名称(编号)',
                    prop: 'result_name',
                    style: 'center',
                    minWidth: '100',
                }, {
                    label: '成果类型',
                    prop: 'result_type',
                    style: 'center',
                    minWidth: '100',
                    render: (h, params) => {
                        for (let i = 0; i < this.resultTypeName.length; i++) {
                            if(this.resultTypeName[i].data_value.toString() === params.row.result_type) {
                                return h('div', {}, this.resultTypeName[i].data_name);
                            }
                        }
                    }
                }, {
                    label: '成果文件(PDF)',
                    prop: 'file_name',
                    style: 'center',
                    minWidth: '100'
                }],
            sendColumns: [
                {
                    label: '配送份数',
                    prop: 'copies',
                    style: 'center',
                    minWidth: '100',
                    render: (h, params) => {
                        let _this = this
                        if(this.type === 'see') {
                            return h('div', {}, params.row.copies)
                        } else if (this.type === 'add') {
                            return h('el-input', {
                                class: 'int',
                                props: {
                                    value: params.row.copies
                                },
                                on: {
                                    input (value) {
                                        params.row.copies = value
                                    },
                                    blur () {
                                    }
                                }
                            })
                        }
                    }
                }, {
                    label: '配送方式',
                    prop: 'mode',
                    style: 'center',
                    minWidth: '140',
                    render: (h, params) => {
                        if(this.type === 'see') {
                            if(params.row.mode) {
                                return h('div', {}, this.table_options[params.row.mode - 1].label)
                            } else {
                                return true
                            }
                        } else if (this.type === 'add') {
                        }
                    }
                }, {
                    label: '配送要求',
                    prop: 'required',
                    style: 'center',
                    minWidth: '120',
                    render: (h, params) => {
                        if(this.type === 'see') {
                            return h('div', {}, params.row.required)
                        } else if (this.type === 'add') {
                            return h('el-input', {
                                props: {
                                    value: params.row.required
                                },
                                on: {
                                    input (value) {
                                        params.row.required = value
                                    }
                                }
                            })
                        }
                    }
                }, {
                    label: '配送地址',
                    prop: 'address',
                    style: 'center',
                    minWidth: '150',
                    render: (h, params) => {
                        if(this.type === 'see') {
                            return h('div', {}, params.row.address)
                        } else if (this.type === 'add') {
                            return h('el-input', {
                                props: {
                                    value: params.row.address
                                },
                                on: {
                                    input (value) {
                                        params.row.address = value
                                    }
                                }
                            })
                        }
                    }
                }, {
                    label: '接收人',
                    prop: 'receiver',
                    style: 'center',
                    minWidth: '110',
                    render: (h, params) => {
                        if(this.type === 'see') {
                            return h('div', {}, params.row.receiver)
                        } else if (this.type === 'add') {
                            return h('el-input', {
                                props: {
                                    value: params.row.receiver
                                },
                                on: {
                                    input (value) {
                                        params.row.receiver = value
                                    }
                                }
                            })
                        }
                    }
                }, {
                    label: '联系电话',
                    prop: 'telephone',
                    style: 'center',
                    minWidth: '140',
                    render: (h, params) => {
                        let _this = this
                        if(this.type === 'see') {
                            return h('div', {}, params.row.telephone)
                        } else if (this.type === 'add') {
                            return h('el-input', {
                                props: {
                                    value: params.row.telephone
                                },
                                on: {
                                    input (value) {
                                        params.row.telephone = value
                                    },
                                    blur () {
                                        if (params.row.telephone && !(/^1[3|4|5|7|8][0-9]{9}$/.test(params.row.telephone))) {
                                            _this.$notify.error({
                                                title: '联系电话',
                                                message: '格式不正确'
                                            });
                                            params.row.telephone = ''
                                        }
                                    }
                                }
                            })
                        }
                    }
                }, {
                    label: '快递单号',
                    prop: 'courier_no',
                    style: 'center',
                    minWidth: '120',
                    render: (h, params) => {
                        if(this.type === 'see') {
                            return h('div', {}, params.row.courier_no)
                        } else if (this.type === 'add') {
                            return h('el-input', {
                                props: {
                                    value: params.row.courier_no
                                },
                                on: {
                                    input (value) {
                                        params.row.courier_no = value
                                    }
                                }
                            })
                        }
                    }
                }, {
                    label: '配送时间',
                    prop: 'delivery_time',
                    style: 'center',
                    minWidth: '150',
                    render: (h, params) => {
                        if(this.type === 'see') {
                            return h('div', {}, this.formatDateTime(params.row.delivery_time))
                        } else if (this.type === 'add') {
                            return h('el-date-picker', {
                                props: {
                                    type: 'datetime',
                                    format: "yyyy-MM-dd hh:mm:ss",
                                    valueFormat: 'yyyy-MM-dd hh:mm:ss',
                                    value: params.row.delivery_time
                                },
                                on: {
                                    input (value) {
                                        params.row.delivery_time = value
                                    }
                                }
                            })
                        }
                    }
                }, {
                    label: '接收时间',
                    prop: 'receiver_time',
                    style: 'center',
                    minWidth: '150',
                    fixed: 'right',
                    render: (h, params) => {
                        if(this.type === 'see') {
                            return h('div', {}, this.formatDateTime(params.row.receiver_time))
                        } else if (this.type === 'add') {
                            return h('el-date-picker', {
                                props: {
                                    type: 'datetime',
                                    format: "yyyy-MM-dd hh:mm:ss",
                                    valueFormat: 'yyyy-MM-dd hh:mm:ss',
                                    value: params.row.receiver_time
                                },
                                on: {
                                    input (value) {
                                        params.row.receiver_time = value
                                    },
                                }
                            })
                        }
                    }
                }],
            rules: {
                project_id: [
                    {required: 'true', message: '请输入项目名称', trigger: 'change'}
                ],
                design_phase: [
                    {required: 'true', message: '请输入设计阶段', trigger: 'change'}
                ],
                plan_time: [
                    {required: 'true', message: '请输入计划完成时间', trigger: 'blur'}
                ],
                project_leader: [
                    {required: 'true', message: '请输入项目/部门负责人', trigger: 'change'}
                ],
                publish_total: [
                    {required: true, min_num: 0, validator: checkNum, trigger: 'blur'}
                ],
            },
            select: [{
                label: '是',
                value: 1
            }, {
                label: '否',
                value: 0
            }]
        }
    },
    components: {
        VmBaseTable
    },
    methods: {
        init (obj) {
            this.dialog = true;
            this.type = obj.type;
            if(this.type === 'add') {
                this.clearForm();
                this.disabled = false;
                this.form.apply_no = this.applyNo();
                this.getLoginInfo()
            }
            if (this.type === 'see') {
                this.clearForm()
                this.disabled = true;
                this.getList(obj.row.id)
            }
            if (this.type === 'edit') {
                this.disabled = false;
                this.getList(obj.row.id)
            }
            if (this.type === 'audit') {
                this.disabled = true;
                this.getList(obj.row.id)
            }
            this.$nextTick(() => {
                this.$refs['form'].resetFields();
            })
        },
        // 获取登录信息
        getLoginInfo () {
        },
        getIndex (index) {
            this.index = index;
        },
        selectChange (select) {
            this.indexes = [];
            for (let i = 0; i < select.length; i++) {
                let temp = select[i];
                this.indexes.push(temp.index);
            }
        },
        // 获取配送类型
        initTableSelectData () {
            this.axiosHelper.get(
                '/v0.1/common/dim/'+ this.DIM_TYPE.DELIVERY_TYPE
            ).then(response => {
                let arr = response.data;
                arr.some((item) => {
                    this.table_options.push({
                        value: item.data_value,
                        label: item.data_name
                    });
                });
            }).catch(error => {
                this.$message.error('获取配送类型列表失败', error);
            })
        },
        // 下载成PDF
        download () {
            let ids = [];
            this.selection.forEach(data => {
                ids.push(data.file_id)
            });
            if (ids.length > 0) {
                let data = new FormData();
                data.append('ids', ids);
                this.axiosHelper.post(
                    '/v0.1/result/sun/modelFileZipFile',
                    data,
                    {responseType: 'blob'}
                ).then(response => {
                    if (!response) {
                        return
                    }
                    let url = window.URL.createObjectURL(response.data);
                    let link = document.createElement('a');
                    link.style.display = 'none';
                    link.href = url;
                    link.setAttribute('download', '加晒申请成果列表' + '.zip');
                    document.body.appendChild(link);
                    link.click()
                }).catch(error => {
                    this.$message.error('下载失败', error)
                })
            } else {
                this.$message.warning({
                    message: '请选择项目！'
                })
            }
        },
        // 申请单号
        applyNo () {
            let date = new Date();
            //年
            let year=date.getFullYear();
            //月
            let month=date.getMonth()+1;
            //日
            let day=date.getDate();
            //时
            let hh=date.getHours();
            //分
            let mm=date.getMinutes();
            //秒
            let ss=date.getSeconds();
            return year + '' + month + '' + day + '' + hh + '' + mm + '' + ss;
        },
        // 清空表单
        clearForm () {
            this.resultTable = [];
            this.sendTable = [];
            this.form = {
                id: 0,
                project_id: '',
                volume_id: 0,
                draw_count: 0,
                is_urgent: 0,
                published_seal_type: 0,
                draw_fold_type: 0,
                remark: '',
                project_name: '',
                project_no: '',
                design_phase: '',
                volume_name: '',
                volume_no: '',
                apply_no: '',
                plan_time: null,
                publish_total: null,
                project_leader: '',
                project_leader_name: '',
                client_name: '',
                client: '',
                client_time: new Date(),
                client_org: '',
                client_org_name: '',
                state: 0,
                type: 0,
                dim_type: 'a',
                file_id: 0,
                name: '',
                result_ids: [],
                delivery_list: []
            }
        },
        // 渲染项目名称的选择项
        projectSelected (id) {
            this.axiosHelper.get(
                '/v0.1/common/project',
                {params: {projectId: id}}
            ).then(response => {
                let list = response.data;
                let param = list[0];
                // 获取设计阶段下拉选项
                this.stages = param.project_stages;
            }).catch();
        },
        // 获取项目名称
        remoteMethod (query) {
            if (query !== '') {
                this.projectsLoading = true;
                this.axiosHelper.get(
                    '/v0.1/common/project',
                    {params: {projectName: query}}
                ).then(response => {
                    this.projectsLoading = false;
                    this.project = response.data;
                    let list = response.data;
                    let arr = list.map(item => {
                        return {value: item.id, label: item.project_no};
                    });
                    this.projects = arr;
                }).then(() => {
                    this.loading = false
                })
            } else {
                this.projects = [];
                this.loading = false
            }
        },
        // 配送信息的增加按钮
        addDelivery () {
            let row = {
                copies: null,
                mode: 1,
                required: null,
                address: null,
                receiver: null,
                telephone: null,
                courier_no: null,
                delivery_time: null,
                receiver_time: null,
                index: null
            };
            // 产品限制5条
            if (this.sendTable.length < 5) {
                this.sendTable.push(row);
                for (let i = 0; i < this.sendTable.length; i++) {
                    let param = this.sendTable[i];
                    param.index = i;
                }
            } else {
            }
        },
        // 删除配送信息
        deleteDelivery () {
            let num = [];
            if (this.indexes.length > 0) {
                for (let i = 0; i < this.sendTable.length; i++) {
                    let temp = this.sendTable[i];
                    for (let j = 0; j < this.indexes.length; j++) {
                        let index = this.indexes[j];
                        if (index === temp.index) {
                            num.push(i);
                        }
                    }
                }
                num.reverse().forEach(i => {
                    this.sendTable.splice(i, 1);
                })
            } else {
                this.$message.warning({
                    message: '请选择后再执行删除'
                })
            }
        },
        // 查看
        getList (id) {
            this.form.client_time = null;
            this.axiosHelper.get(
                '/v0.1/result/sun/' +id
            ).then(response => {
                this.stages = [];
                let params = response.data;
                let selected = {
                    id: params.design_phase,
                    stage_name: params.design_phase_name
                };
                this.stages.push(selected);
                this.remoteMethod(params.project_no);
                this.form = params;
                this.sendTable = params.delivery_list;
                this.resultTable = params.temp_list;
            })
        },
        clickPeople () {
            if(!this.disabled) {
                this.$refs['leader'].popType = 'user-single';
                this.$refs['leader'].initUser();
            }
        },
        // 获取项目负责人
        getUser (user) {
            this.form.project_leader = user.uid;
            this.form.project_leader_name = user.real_name;
        },
        clickTableUser () {
            if(!this.disabled) {
                this.$refs['user'].popType = 'user-single';
                this.$refs['user'].initUser();
            }
        },
        // 配送信息接收人
        getTableUser (user) {
            this.userObj = user;
        },
        // 获取成果列表的id集合
        getResultIds () {
            let arr = this.resultTable;
            let ids = [];
            if (arr.length > 0) {
                for (let i = 0; i < arr.length; i++) {
                    let obj = arr[i];
                    ids.push(obj.id)
                }

            }
            return ids;
        },
        // 新增
        click (formName) {
            if(this.getResultIds().length > 0) {
                let _this = this;
                this.form.type = 2;
                this.form.result_ids = this.getResultIds();
                this.form.delivery_list = this.sendTable;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let data = _this.form;
                        _this.axiosHelper.post(
                            '/v0.1/result/sun',
                            data
                        ).then(response => {
                            if (response.status === 200) {
                                _this.$message.success({
                                    message: '新增成功'
                                });
                                _this.$emit('getList');
                            }
                        }).catch(() => {
                            _this.$message.error({
                                message: '新增失败'
                            })
                        });
                        _this.dialogFormVisible = false
                    } else {
                        return false;
                    }
                    _this.dialog = false
                })
            } else {
                this.$message.error({
                    message: '未选择成果列表，无法出版!'
                })
            }
        },
        // 新增成果列表按钮
        addResult () {
            let name = 'add';
            this.$refs['result'].open(name, null)
        },
        // 渲染成果列表
        getAchievements (arr, volumeId) {
            this.resultTable = arr
            this.form.volume_id = volumeId
        },
        // 删除成果列表
        deleteFinish () {
            let num = [];
            let tables = this.resultTable;
            let ids =this.$refs['result_add_model'].getIds();
            for (let i = 0; i < tables.length; i++) {
                let row = tables[i];
                for (let j = 0; j < ids.length; j++) {
                    let id = ids[j];
                    if (row.id === id) {
                        num.push(i)
                    }
                }
            }
            num.reverse().forEach(i => {
                this.resultTable.splice(i, 1);
            })
        },
        // 表格选中框是否可勾选
        selectable () {
            return !this.disabled;
        },
        selectResult (selection) {
            this.selection = selection
        },
        getResult () {
            // 获取成果类型
            this.axiosHelper.get('/v0.1/common/dim/' + this.DIM_TYPE.RESULT_TYPE).then(
                response => {
                    this.resultTypeName = response.data
                }
            );
            // 获取成果等级
            this.axiosHelper.get('/v0.1/common/dim/' + this.DIM_TYPE.RESULT_LEVEL).then(
                response => {
                    this.resultLevelName = response.data
                }
            )
        }
    },
    watch: {
        userObj (value) {
            let param = {
                index: this.index,
                data: value
            };
            if (this.users.length > 0) {
                // 遍历数组中的对象，是否是同一行的数据，
                let flag = this.users.some((item) => {
                    if (item.index === param.index) {
                        // 如果是同一行数据就替换当前用户对象
                        item.data = param.data;
                        return true;
                    }
                });
                // 结果为false则添加这个对象
                if (!flag) {
                    this.users.push(param)
                }
            } else {
                this.users.push(param)
            }
        }
    },
    mounted () {
        // 获取成果类型列表
        this.initTableSelectData()
        this.getResult();
    }
}
</script>

<style scoped>
.level-one-dialog >>> .el-dialog{
    width: 1040px;
    height: 650px;
}
    /deep/ .el-scrollbar__wrap {
        overflow-x: hidden !important;
        overflow-y: auto !important;
    }
    h2{
        margin-bottom: 10px;
    }
    /deep/ .default-height{
        height: 34vh;
    }
</style>
