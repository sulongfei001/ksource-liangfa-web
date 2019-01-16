package com.ksource.syscontext;

/**
 * 常量表
 * 
 * @author gengzi
 * 
 */
public interface Const {

	/** 用于保存当前登录用户 */
	String USER_SESSION_ID = "LOGIN_USER";

	/** systemAdmin不受任何权限限制 */
	String SYSTEM_ADMIN_ID = "systemAdmin";

	/** 系统根目录菜单(数据库初始化数据) */
	Integer TOP_MODULE_ID = -1;
	/** 系统根机构菜单(数据库初始化数据) */
	Integer TOP_ORG_ID = -1;
	/** 虚拟的根目录菜单：所有菜单的根 */
	Integer DUMMY_SUPER_TOP_MODULE_ID = -2;
	Integer DUMMY_SUPER_TOP_MODULE_ID_APP = -4;
	/** 判决情况根目录(数据库初始化数据) */
	Integer TOP_CONDITION_ID = -1;
	/** 状态：无效 */
	int STATE_INVALID = 0;
	/** 状态：有效 */
	int STATE_VALID = 1;
	
	/** 是否是牵头单位：是 */
	int LIANGFALEADER_NO = 0;
	/** 是否是牵头单位：不是*/
	int LIANGFALEADER_YES = 1;
	
	/** 系统根栏目菜单(数据库初始化数据) */
	Integer TOP_CHANNEL_ID = -1;
	/** 网站首页显示信息数 */
	Integer HOME_PAGE_NUMBER = 7;
	
	/* 是否叶子节点 */
	public static final Integer LEAF_YES = 1;
	public static final Integer LEAF_NO = 0;
	
	/* 是否显示 */
	public static final Integer SHOW_YES = 1;
	public static final Integer SHOW_NO = 0;

	/* 菜单根节点 */
	public static final Integer ROOT_LEAF = 1;
	
	/* 菜单(模块类型) */
	public static final Integer MODULE_CATEGORY_WEB = 1;
	public static final Integer MODULE_CATEGORY_APP = 2;

	/** 机构类型：行政机关 */
	String ORG_TYPE_XINGZHENG = "1";
	/** 机构类型：检察院 */
	String ORG_TYPE_JIANCHAYUAN = "2";
	/** 机构类型：公安局 */
	String ORG_TYPE_GOGNAN = "3";
	/** 机构类型：法院 */
	String ORG_TYPE_FAYUAN = "4";
	/** 机构类型：监察局 */
	String ORG_TYPE_JIANCHAJU = "5";
	/** 机构类型：法制局 */
	String ORG_TYPE_FAZHIJU = "6";
	/** 机构类型：两法牵头单位 */
	String ORG_TYPE_LIANGFALEADER = "7";

	/** 移送司法案件（移送司法流程key） */
	String CASE_CHUFA_PROC_KEY = "chufaProc";
	/** 涉嫌犯罪案件（涉嫌犯罪案件流程key） */
	String CASE_CRIME_PROC_KEY = "crimeProc";
	/** 违纪案件（违纪案件流程key） */
	String CASE_WEIJI_PROC_KEY = "weijiProc";
	/** 职务犯罪案件（职务犯罪案件流程key） */
	String CASE_ZHIWUFANZUI_PROC_KEY = "zhiwufanzuiProc";
	/** 移送行政违法案件（移送行政违法案件流程key） */
	String CASE_YISONGXINGZHENG_PROC_KEY = "yisongchufaProc";

	/** 报表类型:表格报表 */
	String REPORT_TYPE_TABLE = "1";
	/** 报表类型:曲线图 */
	String REPORT_TYPE_LINE = "2";
	/** 报表类型:柱状图 */
	String REPORT_TYPE_BAR = "3";

	/** 文本框 */
	int HTML_INPUT_TYPE_TEXT = 1;
	/** 文本框 */
	int HTML_INPUT_TYPE_TEXTAREA = 2;
	/** 单选按钮 */
	int HTML_INPUT_TYPE_RADIO = 3;
	/** 多选框 */
	int HTML_INPUT_TYPE_CHECKBOX = 4;
	/** 下拉菜单 */
	int HTML_INPUT_TYPE_SELECT = 5;
	/** 上传组件file */
	int HTML_INPUT_TYPE_FILE = 6;

	/** 数据类型-字符 */
	int INPUT_DATA_TYPE_STRING = 1;
	/** 数据类型-整数 */
	int INPUT_DATA_TYPE_INT = 2;
	/** 数据类型-数值 */
	int INPUT_DATA_TYPE_NUMBER = 3;
	/** 数据类型-日期(时间) */
	int INPUT_DATA_TYPE_DATE = 4;

	/** 表单模板：录入案件(无自定义表单模板) */
	int FORM_DEF_NEW_CASE = -1;
	/** 表单模板：修改案件(无自定义表单模板) */
	int FORM_DEF_UPDATE_CASE = -2;
	
	/** 任务类型：录入案件 */
	int TASK_TYPE_ADD_CASE = 1;
	/** 任务类型：修改案件 */
	int TASK_TYPE_UPDATE_CASE = 2;
	/** 任务类型：普通任务 */
	int TASK_TYPE_COMMON = 3;
	/** 任务类型：行政立案 */
	int TASK_TYPE_XINGZHENGLIAN = 4;
	/** 任务类型：行政处罚 */
	int TASK_TYPE_XINGZHENGCHUFA = 5;
	/** 任务类型：移送公安 */
	int TASK_TYPE_YISONGGONGAN = 6;
	/** 任务类型：建议移送 */
	int TASK_TYPE_JIANYIYISONG = 7;
	/** 任务类型：已作出行政处罚，行政结案*/
	int TASK_TYPE_XINGZHENGJIEAN = 8;
	/** 任务类型：案件退回 */
	int TASK_TYPE_TUIHUI = 9;
	/** 任务类型：行政不予立案 */
	int TASK_TYPE_BUYULIAN = 10;
	/** 任务类型：行政撤案 */
	int TASK_TYPE_XINGZHENGCHEAN = 11;
	/** 任务类型：分派案件 */
	int TASK_TYPE_FENPAI = 12;
	/** 任务类型：移送管辖 */
	int TASK_TYPE_YISONGGUANXIA = 13;
	/** 任务类型：不予处罚*/
	int TASK_TYPE_NOTPENALTY = 14;
	/** 任务类型：移送行政拘留*/
	int TASK_TYPE_TRANSFERDETENTION = 15;
	/** 任务类型：行政拘留*/
	int TASK_TYPE_DETENTIONINFO = 16;
	/** 任务类型：不予拘留*/
	int TASK_TYPE_NOTDETENTIONINFO = 17;
	/** 任务类型：要求说明不立案理由*/
	int TASK_TYPE_REQNOLIANREASON = 18;
	/** 任务类型：公安说明不立案理由*/
	int TASK_TYPE_NOLIANREASON = 19;
	/** 通知公安立案*/
	int TASK_TYPE_REQLIAN = 20;
	/** 同意不立案，已结案*/
	int TASK_TYPE_AGREENOLIAN = 21;
	
	
	
	/** 案件的办理机构（与录入人同机构） */
	String TASK_ASSGIN_EQUALS_INPUTER = "T-A-E-I";
	/** 案件的办理机构（录入人办理） */
	String TASK_ASSGIN_IS_INPUTER = "T-A-I-I";

	/** 任务分配方式：分给具体人 */
	int TASK_ASSIGN_TYPE_USER = 1;
	/** 任务分配方式：分给用户组 */
	int TASK_ASSIGN_TYPE_GROUP = 2;

	/** 案件附件类型　　　 */
	int CASE_DETAIL_FILE = 1;
	int PENALTY_JUDGMENT_FILE = 2;

	/** 超时时间　转换格式 */
	String TIMEOUT_DATE_FORMAT = "MM:dd:hh";

	/** 用户类型--系统管理员 */
	int USER_TYPE_ADMIN = 1;
	/** 用户类型--普通用户 */
	int USER_TYPE_PLAIN = 0;

	/** 资源类型--主要的，重要的 */
	int MODULE_CATEGORY_MAJOR = 1;
	/** 资源类型--次要的，普通的 */
	int MODULE_CATEGORY_MINOR = 0;
	/** 维护菜单 */
	int MODULE_IS_MAINTAIN_YES = 1;
	/** 非维护菜单 */
	int MODULE_IS_MAINTAIN_NO = 0;

	/** 组织机构级别 */
	int DISTRICT_JB_1 = 1;
	int DISTRICT_JB_2 = 2;
	int DISTRICT_JB_3 = 3;

	/** 案件指标：1处罚状态、2、移送状态、3立案状态、4逮捕状态、5起诉状态、6判决状态、7结案状态8.要求说明不立案理由状态9.说明不立案理由状态 */
	int CASE_IND_CHUFA = 1;
	int CASE_IND_YISONG = 2;
	int CASE_IND_LIAN = 3;
	int CASE_IND_DAIBU = 4;
	int CASE_IND_QISU = 5;
	int CASE_IND_PANJUE = 6;
	int CASE_IND_JIEAN = 7;
    int CASE_IND_REQ_EXPLAIN=8;
    int CASE_IND_EXPLAIN=9;
	/** 处罚状态:1未处罚，2已处罚,3不处罚 */
	int CHUFA_STATE_NOTYET = 1;
	int CHUFA_STATE_YES = 2;
	int CHUFA_STATE_NO = 3;
	/** 移送状态：1未移送(默认)、2直接移送、3建议移送 4.建议移送未移送*/
	int YISONG_STATE_NO = 1;
	int YISONG_STATE_ZHIJIE = 2;
	int YISONG_STATE_JIANYI = 3;
    int YISONG_STATE_JIANYI_NOT = 4;
	/** 立案状态：1未立案(默认)、2不立案、3立案 4.通知立案*/
	int LIAN_STATE_NOTYET = 1;
	int LIAN_STATE_NO = 2;
	int LIAN_STATE_YES = 3;
    int LIAN_STATE_NOTICE=4;
	/** 逮捕状态：1未提请逮捕(默认)、2、批准逮捕、3不逮捕 */
	int DAIBU_STATE_NOTYET = 1;
	int DAIBU_STATE_YES = 2;
	int DAIBU_STATE_NO = 3;
	/** 起诉状态：1未起诉(默认)、2不起诉、3起诉 */
	int QISU_STATE_NOTYET = 1;
	int QISU_STATE_NO = 2;
	int QISU_STATE_YES = 3;
	/** 判决状态：1未判决(默认)、2已判决 */
	int PANJUE_STATE_NOTYET = 1;
	int PANJUE_STATE_YES = 2;
	/** 结案状态：1未结案(默认)、2已结案 */
	int JIEAN_STATE_NO = 1;
	int JIEAN_STATE_YES = 2;
    /** 要求说明不立案理由状态：0.默认，1.(检察机关)要求(公安机关)说明不立案理由　**/
    int REQ_EXPLAIN_STATE_NOTYET=0;
    int REQ_EXPLAIN_STATE_YES=1;
    /**　说明不立案理由状态：0.默认1.(公安机关)说明不立案理由**/
    int EXPLAIN_STATE_NOTYET=0;
    int EXPLAIN_STATE_YES=1;
    /** 行政拘留状态：1不予拘留、2行政拘留 */
	int DETENTION_STATE_NO = 1;
	int DETENTION_STATE_YES = 2;
    /** 立案监督状态 1要求说明不立案理由:1要求说明不立案理由、2通知公安立案、3取消立案监督状态*/
	int LIAN_SUP_STATE_1 = 1;
	int LIAN_SUP_STATE_2 = 2;
	int LIAN_SUP_STATE_3 = 3;
	/* 嫌疑人状态************************* */
	/** 逮捕状态：1未提请逮捕(默认)、2、提请逮捕、3、批准逮捕、4不逮捕 */
	int XIANYIREN_DAIBU_STATE_NOTYET = 1;
	int XIANYIREN_DAIBU_STATE_TIQING = 2;
	int XIANYIREN_DAIBU_STATE_YES = 3;
	int XIANYIREN_DAIBU_STATE_NO = 4;
	/** 提请起诉状态：1未提请起诉、2提请起诉 */
	int XIANYIREN_TIQINGQISU_STATE_NOTYET = 1;
	int XIANYIREN_TIQINGQISU_STATE_YES = 2;
	/** 提起公诉状态：1未起诉、2不起诉、3起诉 */
	int XIANYIREN_TIQIGONGSU_STATE_NOTYET = 1;
	int XIANYIREN_TIQIGONGSU_STATE_NO = 2;
	int XIANYIREN_TIQIGONGSU_STATE_YES = 3;
	/** 判决状态：1、未判决、2已判决 */
	int XIANYIREN_PANJUE_STATE_NOTYET = 1;
	int XIANYIREN_PANJUE_STATE_YES = 2;

	/**
	 * 提交动作类型：1提请逮捕、6审查逮捕、2提请起诉、3提起公诉、4不起诉、5法院判决、0：普通
	 * 7案件审查（已处罚录入）、8案件审查（立案录入）、9案件审查（移送案件录入）
	 * ps：789是权宜之计，如果不提供3个录入口，就无需789动作定义，其目的是为了保证的录入时机（目的）在审核后流向正确的任务
	 * */
	int TASK_ACTION_TYPE_NORMAL = 0;
	int TASK_ACTION_TYPE_TIQINGDAIBU = 1;
	int TASK_ACTION_TYPE_TIQINGQISU = 2;
	int TASK_ACTION_TYPE_TIQIGONGSU = 3;
	int TASK_ACTION_TYPE_BUQISU = 4;
	int TASK_ACTION_TYPE_FAYUANPANJUE = 5;
	int TASK_ACTION_TYPE_SHENCHADAIBU = 6;
	int TASK_ACTION_TYPE_SHENCHA_CHUFA = 7;
	int TASK_ACTION_TYPE_SHENCHA_LIAN = 8;
	int TASK_ACTION_TYPE_SHENCHA_YISONG = 9;
	int TASK_ACTION_TYPE_YISONG_GONAN = 10;

	/** 案件状态　-1.待审核 */
	int CASE_STATE_SHENGHE = -1;
    /** 案件状态 4.已达刑事立案标准*/
    int CASE_STATE_XINGSHILIAN=4;
    /** 案件状态 9.建议移送*/
    int CASE_STATE_JIANYIYISONG=9;
    /** 案件状态 26.同意意见，已结案*/
    int CASE_STATE_TONGYIYIJIAN = 12;

	/** 参与人读取最新案件咨询状态 1.未读 2.已读 */
	int PARTICIPANT_READ_STATE_NO = 1;
	int PARTICIPANT_READ_STATE_YES = 2;

	/** 案件咨询状态(1:咨询中2:咨询结束) */
	int CASE_CONSULTCATION_START = 1;
	int CASE_CONSULTCATION_END = 2;

	/** 案件咨询阅读状态 */
	int CASE_CONSULTATION_NOREAD = 1;
	int CASE_CONSULTATION_READED = 2;
	
	/**通知消息读取状态 1未读 2已读*/
	int INSTANT_MESSAGE_READ_NO = 1;
	int INSTANT_MESSAGE_READ_YES = 2;

	/**
	 * 流程动作ID String PROC_ACTION_CHUFA="1"; String PROC_ACTION_LIAN="2"; String
	 * PROC_ACTION_YISONG="3";
	 */

	/**
	 * 流程状态ID String PROC_STATE_CHUFA="2"; String PROC_STATE_LIAN="3"; String
	 * PROC_STATE_YISONG="4";
	 */

	/** 案件录入时机 */
	int CASE_INPUT_TIMING_CHUFA = 1;
	int CASE_INPUT_TIMING_LIAN = 2;
	int CASE_INPUT_TIMING_YISONG = 3;
	int CASE_INPUT_TIMING_NO = 0;

	/** SMS 绑定时(分组 1:通知公告 2:案件咨询) **/
	String SMS_BIND_GROUP_DEF_NOTICE = "1";
	String SMS_BIND_GROUP_DEF_CASECONSULTATION = "2";
	/** SMS 模板类型 1:流程模板 2:通知公告模板 3:案件咨询模板 **/
	int SMS_TEMPLATE_PROC = 1;
	int SMS_TEMPLATE_NOTICE = 2;
	int SMS_TEMPLATE_CASECONSULTATION = 3;

	/** 置顶 0:非置顶1:本页置顶2:永久置顶(未用到) **/
	int TOP_NO = 0;
	int TOP_YES_PAGE = 1;
	int TOP_YES_ALL = 2;

	/**通知公告:1、生效；2、终止**/
	int NOTICE_IS_PUBLISHED =1;
	int NOTICE_ISNOT_PUBLISHED =2;
	/** 数据库方言：myql,oracle,db2,sqlserver 目前只用到：oracle,mysql */
	String DIALECT_MYSQL = "mysql";
	String DIALECT_ORACLE = "oracle";
	String DIALECT_DB2 = "db2";
	String DIALECT_SQLSERVER = "sqlserver";
	/** 数据库表名 */
	
	String TABLE_COMMUNION_REPLY="COMMUNION_REPLY";
	String TABLE_COMMUNION_SEND="COMMUNION_SEND";
	String TABLE_LAW_PERSON="LAW_PERSON";
	String TABLE_CASE_INFORMATION="CASE_INFORMATION";
	String TABLE_INTEGRATED_INFORMATION="INTEGRATED_INFORMATION";
	String TABLE_OTHER_INFORMATION="OTHER_INFORMATION";
	String TABLE_PUBLIC_OPINION="PUBLIC_OPINION";
	String TABLE_CASE_COMPANY = "CASE_COMPANY";
	String PARTICIPANTS_SEQ = "PARTICIPANTS_SEQ";

	String TABLE_CASE_YISONG_JIWEI = "CASE_YISONG_JIWEI";
	
	String TABLE_CASE_CONSULTATION = "CASE_CONSULTATION";

	String TABLE_CASE_CONSULTATION_CONTENT = "CASE_CONSULTATION_CONTENT";

	String TABLE_CASE_PARTY = "CASE_PARTY";

	String TABLE_CASE_STEP = "CASE_STEP";

	String TABLE_CASE_XIANYIREN = "CASE_XIANYIREN";

	String TABLE_FILE_RESOURCE = "FILE_RESOURCE";

	String TABLE_FORUM_THEME = "FORUM_THEME";

	String TABLE_MODULE = "MODULE";

	String TABLE_NOTICE_FILE = "NOTICE_FILE";

	String TABLE_NOTICE = "NOTICE";

	String TABLE_ORGANISE = "ORG";

	String TABLE_SMS_BIND = "SMS_BIND";

	String TABLE_TASK_ACTION = "TASK_ACTION";

	String TABLE_THEME_REPLY = "THEME_REPLY";

	String TABLE_USER_MSG = "USER_MSG";

	String TABLE_LAY_INFO = "LAY_INFO";

	String TABLE_ROLE = "ROLE";

	String TABLE_ZHIFA_INFO = "ZHIFA_INFO";

	String TABLE_INFO_TYPE = "INFO_TYPE";

	String TABLE_POST = "POST";

	String TABLE_ZHIFA_INFO_TYPE = "ZHIFA_INFO_TYPE";

	String TABLE_FORUM_SECTION = "FORUM_SECTION";

	String TABLE_SERIAL_MODEM_GATEWAY_DEF = "SERIAL_MODEM_GATEWAY_DEF";

	String TABLE_SMS_TEMPLATE = "SMS_TEMPLATE";

	String TABLE_PROC_DEPLOY = "PROC_DEPLOY";

	String TABLE_CASE_BASIC = "CASE_BASIC";
	
	String TABLE_YISONGJIEWEI_BEAN = "YISONGJIEWEI_BEAN";
	
	String TABLE_CASE_TODO = "CASE_TODO";

	String TABLE_USER = "USER";
	
	String TABLE_CASE_ATTA = "CASE_ATTACHMENT";
	String CASE_IMPORT = "CASE_IMPORT";
	
	String HOTLINE_IMPORT = "HOTLINE_IMPORT";

	String TABLE_FORM_DEF = "FORM_DEF";

	String TABLE_FORM_FIELD = "FORM_FIELD";

	String TABLE_FIELD_ITEM = "FIELD_ITEM";
	
	String TABLE_ACCUSE_TYPE = "ACCUSE_TYPE";
	String TABLE_ACCUSE_INFO = "ACCUSE_INFO";
	
	String TABLE_SPECIAL_ACTIVITY="SPECIAL_ACTIVITY";
	String TABLE_ACTIVITY_MEMBER = "ACTIVITY_MEMEBER";

	String TABLE_WEB_PROGRAMA = "WEB_PROGRAMA";
	String TABLE_WEB_ARTICLE_TYPE = "WEB_ARTICLE_TYPE";
	String TABLE_WEB_ARTICLE = "WEB_ARTICLE";

	String TABLE_ORG_AMOUNT = "ORG_AMOUNT";
	String TABLE_WJ_CASE_PARTY="WJ_CASE_PARTY";
	String TABLE_ZWFZ_CASE_PARTY="ZWFZ_CASE_PARTY";
	String TABLE_ADMDIV_LICENSE_INFO="ADMDIV_LICENSE_INFO";
	String TABLE_ADMDIV_LICENSE_FILED="ADMDIV_LICENSE_FILED";
	String TABLE_ADMDIV_LICENSE_OPTION="ADMDIV_LICENSE_OPTION";
	String TABLE_ADMDIV_LICENSE_REPLY="ADMDIV_LICENSE_REPLY";

    String  TABLE_SEND_EMAIL = "LF_MAIL_SEND_INFO";
    String  TABLE_EMAIL_FILE="LF_MAIL_FILE";
    String  TABLE_RECEIVED_EMAIL="LF_MAIL_RECEIVED_INFO";
	
    String TABLE_SUPERVISE_CASE = "SUPERVISE_CASE";
    String TABLE_PUBLISH_INFO_FILE = "PUBLISH_INFO_FILE";
    String TABLE_WEB_FRIENDLY_LINK = "WEB_FRIENDLY_LINK";
	
    String TABLE_DRAFT_EMAIL="LF_MAIL_DRAFT_INFO";
    
    String TABLE_CASE_FILTER="CASE_FILTER";
    
    String TABLE_CASE_STATE = "CASE_STATE";
    String TABLE_CASE_PROCESS = "CASE_PROCESS";
    String TABLE_PENALTY_CASE_EXT = "PENALTY_CASE_EXT";
    String TABLE_CRIME_CASE_EXT = "CRIME_CASE_EXT";
    String TABLE_PENALTY_REFER_CASE_EXT = "PENALTY_REFER_CASE_EXT";
    String TABLE_CASE_ACCUSE = "CASE_ACCUSE";
    String TABLE_XIANYIREN_ACCUSE = "XIANYIREN_ACCUSE";
    String TABLE_CASE_ATTACHMENT = "CASE_ATTACHMENT";
    String TABLE_FORM_INSTANCE = "FORM_INSTANCE";
    String TABLE_FIELD_INSTANCE = "FIELD_INSTANCE";
    
    String TABLE_CASE_MODIFIED_EXP_LOG = "CASE_MODIFIED_EXP_LOG";
    //网站
    String TABLE_CMS_ATTACHMENT="CMS_ATTACHMENT";
    String TABLE_CMS_CONTENT="CMS_CONTENT";
    String TABLE_CMS_CHANNEL="CMS_CHANNEL";
    String TABLE_CMS_FRIENDLY_LINK="CMS_FRIENDLY_LINK";
    
    
    String TABLE_COMMUNION_RECEIVE = "COMMUNION_RECEIVE";
    String TABLE_ORGANISE_EXTERNAL = "ORGANISE_EXTERNAL";
    String TABLE_INSTRUCTION_SEND = "INSTRUCTION_SEND";
    String TABLE_INSTRUCTION_RECEIVE = "INSTRUCTION_RECEIVE";
    String TABLE_INSTRUCTION_REPLY = "INSTRUCTION_REPLY";
    String TABLE_ILLEGAL_SITUATION = "ILLEGAL_SITUATION";
    String ACCUSE_RULE = "ACCUSE_RULE";
    String TABLE_WORK_REPORT = "WORK_REPORT";
    String TABLE_WORK_REPORT_REPLY = "WORK_REPORT_REPLY";
    String TABLE_WORK_REPORT_RECEIVE = "WORK_REPORT_RECEIVE";
    String TABLE_WEBSERVICE_CONFIG = "WEBSERVICE_CONFIG";
    String TABLE_CHUFA_TYPE = "CHUFA_TYPE_FORM";
    
    String TABLE_OFFICE_DOC = "OFFICE_DOC";
    
    String TABLE_OFFICE_TEMPLATE = "OFFICE_TEMPLATE";
    
    String TABLE_OFFICE_IDENTITY = "TABLE_OFFICE_IDENTITY";
    String TABLE_CASE_YISONG_NOTICE = "CASE_YISONG_NOTICE";
    
    String TABLE_CASE_SUSPECT_ACCUSE = "CASE_SUSPECT_ACCUSE";
    String TABLE_CASE_REQUIRE_NOLIAN_REASON= "CASE_REQUIRE_NOLIAN_REASON";
    //删除文件
    String DELETE = "DELETE";
    
    String TABLE_CASE_MODIFIED_IMP_LOG = "CASE_MODIFIED_IMP_LOG";
    
    String TABLE_PUNISH_BASIS = "PUNISH_BASIS";
    String TABLE_PUNISH_BASIS_TERM = "PUNISH_BASIS_TERM";
    String TABLE_DISCRETION_STANDARD = "DISCRETION_STANDARD";
    String TABLE_CASE_TURNOVER = "CASE_TURNOVER";
    String TABLE_PROC_SEQUENCE_POINT = "PROC_SEQUENCE_POINT";
    /**线索信息表*/
    String TABLE_CLUE_INFO = "CLUE_INFO";
    //是否回复
    Integer TABLE_CLUE_ISRECEIVE = 0;
    
    /**线索分派表*/
    String TABLE_CLUE_DISPATCH = "CLUE_DISPATCH";
    /**线索受理记录表*/
    String TABLE_CLUE_ACCEPT = "CLUE_ACCEPT";
    //线索回复表
    String TABLE_CLUE_REPLY = "CLUE_REPLY_SEQ";
    
    String TABLE_WARN_CONDITION = "WARN_CONDITION";
    
    String TABLE_INSTANT_MESSAGE = "INSTANT_MESSAGE";
    
    String TABLE_APP_VERSION = "APP_VERSION";
    
    //移送行政拘留表
    String TABLE_TRANSFER_DETENTION = "TRANSFER_DETENTION";
    
    //公安办理行政拘留信息
    String TABLE_DETENTION_INFO = "DETENTION_INFO";
    
    //热线一律设为举报
    Integer TABLE_HOTLINE_CASERES = 1;
    
	String CASE_REPLY_YES = "1";
	String CASE_REPLY_NO = "0";
	
//	市级
	Integer TIMEOUTWARNREMINDERKEY_CITY = 1 ;
//	县区级
	Integer TIMEOUTWARNREMINDERKEY_COUNTY = 2 ;
	
	Integer ORG_TYPE_NUM = 2 ;
	Integer IS_DEPT_NUM = 0 ;
	
	//罪名类型:罪名类型（1提请逮捕罪名、2批准逮捕罪名、3提请起诉罪名、4提起公诉罪名5、法院一审判决罪名、6法院终审判决罪名）
	int ZM_TYPE_tiqingdaibuZm=1;
	int ZM_TYPE_pizhundaibuZm=2;
	int ZM_TYPE_tiqingqisuZm=3;
	int ZM_TYPE_tiqigongsuZm=4;
	int ZM_TYPE_panjue1Zm=5;
	int ZM_TYPE_panjue2Zm=6;
	
	//案件罪名类型：（1移送公安罪名）
	int CASE_ZM_TYPE_yisonggongan = 1;
	
	//两法网站，文章类型（0是默认类型不能删除，1是非默认类型可以删除）
	int WEB_ARTICLE_TYPE_DEFAULT = 0;
	int WEB_ARTICLE_TYPE_NOTDEFAULT = 1;
	
	
	//是否已经备案(0:未备案，1：已备案，2：其它意见)
	Integer REPLY_NO=0;
	Integer REPLY_YES=1;
	Integer REPLY_OPTION=2;
	
	//批复类型(1:备案，2：其它意见)
	Integer REPLY_TYPE_FILED=1;
	Integer REPLY_TYPE_OPTION=2;

    //邮件类型:1:正常2:草稿3:加星标记
    Integer MAIL_TYPE_NORMAL =1 ;
    Integer MAIL_TYPE_DRAFT =2 ;
    Integer MAIL_TYPE_MARK =3 ;

    //邮件状态：0:删除状态1:正常2:永久删除
    Integer EMAIL_STATE_DEL =2 ;
    Integer EMAIL_STATE_NORMAL =1 ;
    Integer EMAIL_STATE_INVALD =0 ;
    
    //移送行政违法案件状态
    int YISONGCHUFA_STATE = 1;

    //角色状态
    Integer ROLE_TYPE_SYSTEM = 1;//系统管理
    Integer ROLE_TYPE_SECURITY = 2;//系统安全管理
    Integer ROLE_TYPE_AUDIT = 3;//系统安全审计
    Integer ROLE_TYPE_NORMAL = 4;//普通角色
    
    
    /**已做出处罚并且未移送*/
    Integer CASE_TYPE_CHUFA=1;
    /**已做出处罚并且主动移送*/
    Integer CASE_TYPE_CHUFA_YISONG=2;
    /**未出做出处罚并直接移送*/
    Integer CASE_TYPE_YISONG=3;
    /**未做出处罚建议移送*/
    Integer CASE_TYPE_YISONG_SUGGEST = 4;
    /**已作出处罚建议移送*/
    Integer CASE_TYPE_CHUFA_SUGGEST_YISONG = 5;
    
    /**流程文件中定义决定流程流向的的流程变量*/
    String PROC_ACTION_PARAM_NAME="action";
    //部门
    Integer IS_DEPT = 1;
    Integer IS_DEPT_NO = 0;
   
    /** 网站文章状态正常 */
	Integer CMS_CONTENT_NORMAL = 0;
	 /** 网站文章状态删除 */
	Integer CMS_CONTENT_DELETE = -1;
	 /** 网站文章置顶 */
	Integer CMS_CONTENT_TOP = 1;
	 /** 网站文章不置顶 */
	Integer CMS_CONTENT_NUTOP = 2;
	
	 /** 网站文章--->手动添加 */
	Integer CMS_FROM_TYPE_AUTO = 0;
	 /** 网站文章--->法律法规 */
	Integer CMS_FROM_TYPE_LAY = 1;
	 /** 网站文章--->执法动态 */
	Integer CMS_FROM_TYPE_ZHIFA = 2;
	 /** 网站文章--->通知公告 */
	Integer CMS_FROM_TYPE_NOTICE = 3;

	String OLUSER_LISTENER = "ONLINE_USERS_LISTENER";

	String OLUSER_LISTENER_SESSION_ID = "ONLINE_USER_LISTENER_SESSION_ID";
	
	/** 增量数据操作类型：插入标示 */
	String OPERATE_TYPE_INSERT = "I";
	/** 增量数据操作类型：修改标示 */
	String OPERATE_TYPE_UPDATE = "U";
	/** 增量数据操作类型：删除标示 */
	String OPERATE_TYPE_DELETE = "D";
	/**数据导出状态: Y 已导出*/
	String EXP_STATE_YES = "Y";
	/**数据导出状态: N 未导出*/
	String EXP_STATE_NO = "N";	
	/**自定义压缩文件后缀*/
	String ZIP_FILE_SUFFIX = ".ksc";
	/**自定义导出文件后缀*/
	String TXT_FILE_SUFFIX = ".json";
	
	/**案件录入类型---手工录入*/
	Integer INPUT_TYPE_HAND =1;
	/**案件录入类型---Excel录入*/
	Integer INPUT_TYPE_EXCEL =2;
	/**案件录入类型---数据导入*/
	Integer INPUT_TYPE_DATA =3;
	
	/**系统版本：1*/
	Integer SYSTEM_VERSION_1 = 1;
	/**系统版本：2*/
	Integer SYSTEM_VERSION_2 = 2;
	
	/**是否检察院建议移送 ： 0 否  1是*/
	Integer IS_SUGGEST_YISONG_NO = 0;
	/**是否检察院建议移送 ： 0 否  1是*/
	Integer IS_SUGGEST_YISONG_YES = 1;
	
    /**
     * 错误类型。SERVICE为打不到服务等问题，可以再次进行上传的错误；
     * OTHER为其它问题，如果生成或解决XML失败等问题，由于案件已经结束，相关数据已经确定，这样的错误不论上传多少次都会失败 
     * */
    public static final String PLATFORM_CALL_SERVICE_ERROR="SERVICE";
    public static final String PLATFORM_CALL_OTHER_ERROR="OTHER";
    public static final String PLATFORM_CALL_SUCCESS="SUCCESS";
    
    /** 1-未接收、未读 */
    int READ_STATUS_NO = 1;
    /** 2-已接收、未读  */
    int READ_STATUS_YES = 2;
    
    /** 标识查询上级组织机构  汇报工作时使用*/
    String SEARCH_HIGHER_ORG = "SEARCH_HIGHER_ORG";
    /** 标识查询上级组织机构  下发指令时使用*/
    String SEARCH_LOWER_ORG = "SEARCH_LOWER_ORG";
    /** 标识查询本级组织机构  横向交流指令时使用*/
    String SEARCH_COMMUNION_ORG = "SEARCH_COMMUNION_ORG";

    
    /**
     * 数据库标识
     */
    public static final String LIANGFA="LIANGFA";  
    public static final String LIANGFA_STAT="LIANGFA_STAT";  
   
    /***
     * 文书类型:涉嫌犯罪案件筛查报告(总)
     */
    public static final String DOC_TYPE_SXFZ_1 = "sxfz1";
    /***
     * 文书类型:涉嫌犯罪案件筛查报告(对下)
     */
    public static final String DOC_TYPE_SXFZ_2 = "sxfz2";   
    /***
     * 文书类型:降格处理案件线索筛查报告（总，面）
     */
    public static final String DOC_TYPE_JGCL_Z_1 = "jgcl1";
    /***
     * 文书类型:降格处理案件线索筛查报告（总，点）
     */
    public static final String DOC_TYPE_JGCL_Z_2 = "jgcl2";
    /***
     * 文书类型:降格处理案件线索筛查报告（对下面）
     */
    public static final String DOC_TYPE_JGCL_Z_3 = "jgcl3";
    /***
     * 文书类型:降格处理案件线索筛查报告（对下点）
     */
    public static final String DOC_TYPE_JGCL_Z_4 = "jgcl4";
    
    /***
     * 文书类型:大数据分析（总）
     */
    public static final String DOC_TYPE_DSJFX_1 = "dsjfx1";
    
    /***
     * 文书类型:大数据分析（对下1）
     */
    public static final String DOC_TYPE_DSJFX_2 = "dsjfx2";
    
    /***
     * 文书类型:大数据分析（对下2）
     */
    public static final String DOC_TYPE_DSJFX_3 = "dsjfx3";
    
    public static final int CASE_STATE_CHAYUE = 26;
    
    /**
     * 字典表dt_code设置，以便行政案件case_step插入
     */
    /**行政受理*/
    String CHUFA_PROC_0="0";
    /**行政立案*/
    String CHUFA_PROC_1="1";
    /**行政处罚*/
    String CHUFA_PROC_2="2";
    /**行政结案，行政机关不予立案*/
    String CHUFA_PROC_3="3";
    /**行政撤案*/
    String CHUFA_PROC_4="4";
    /**行政不予处罚*/
    String CHUFA_PROC_5="5";
    /**查阅后未发现涉嫌犯罪线索，已结案*/
    String CHUFA_PROC_6="6";
    /**行政机关补充调查*/
    String CHUFA_PROC_8="8";
    /**建议移送公安*/
    String CHUFA_PROC_9="9";
    /**已移送，等待公安受理*/
    String CHUFA_PROC_10="10";
    /**公安不(予)立案*/
    String CHUFA_PROC_11="11";
    /**同意不立案，已结案*/
    String CHUFA_PROC_12="12";
    /**要求公安说明不立案理由*/
    String CHUFA_PROC_13="13";
    /**公安立案*/
    String CHUFA_PROC_14="14";
    /**公安说明不(予)立案理由*/
    String CHUFA_PROC_15="15";
    /**通知公安立案*/
    String CHUFA_PROC_16="16";
    /**提请逮捕，待审查*/
    String CHUFA_PROC_17="17";
    /**审查逮捕，待移送起诉*/
    String CHUFA_PROC_18="18";
    /**移送起诉，待审查*/
    String CHUFA_PROC_19="19";
    /**已移送管辖，等待受理*/
    String CHUFA_PROC_27="27";
    /**已分派，等待受理*/
    String CHUFA_PROC_28="28";
    /**已做出行政处罚，已结案*/
    String CHUFA_PROC_29="29";
    /**已移送行政拘留*/
    String CHUFA_PROC_30="30";
    /**已办理行政拘留，已结案*/
    String CHUFA_PROC_31="31";
    /**已办理不予拘留，已结案*/
    String CHUFA_PROC_32="32";
    /**行政不予处罚，已结案*/
    String ChUFA_PROC_5="5";
    /**提起公诉*/
    String ChUFA_PROC_21="21";
    /**法院判决，已结案*/
    String ChUFA_PROC_22="22";
    
    /**是否涉嫌犯罪1是  0否*/
    int SUSPECTED_CRIMINAL_NO=0;
    int SUSPECTED_CRIMINAL_YES=1;
    /**是否移送管辖*/
    int IS_TURNOVER_NO = 0;
    int IS_TURNOVER_YES = 1;
    
    //是否交办案件(1-否，2-是)
    int IS_ASSIGN_NO=1;
    int IS_ASSIGN_YES=2;
    
    // 行政立案状态:1未立案，2不立案，3立案
    int XINGZHENGLIAN_STATE_NOTYET=1;
    int XINGZHENGLIAN_STATE_NO=2;
    int XINGZHENGLIAN_STATE_YES=3;
    
	public static Integer warnType_TIME_TIMEOUT=-1;//超时预警key
	public static Integer warnType_CASE_PARTY_WARN= -2;//案件当事人预警key
	public static Integer warnType_CASE_COMPANY_WARN=-3;//案件涉案单位预警key
	public static Integer warnType_CASE_NORMAL= -4;//历史案件预警正常
	public static Integer warnType_AMOUNT_NORMAL= -5;//涉案金额预警正常
	public static Integer warnType_AMOUNT_WARN= -6;//涉案金额超出预警key
	public static Integer warnType_ORG_AMOUNT= -7; //涉案金额标准key
	
	//初查核实步骤的4个动作，绘制流程图分支线时${action == 1}要和这些动作对应
	/**行政立案*/
	String CHUCHAHESHI_XINGZHENGLIAN="1";
	/**移送司法*/
	String CHUCHAHESHI_YISONGSIFA="2";
	/**行政现场处理*/
	String CHUCHAHESHI_XIANCHANGCHULI="3";
	/**行政不予立案*/
	String CHUCHAHESHI_NOT_XINGZHENGLIAN="4";
	
	//行政立案审查步骤动作
	/**行政处罚*/
	String ANJIANSHENCHA_XINGZHENGCHUFA="1";
	/**行政撤案*/
	String ANJIANSHENCHA_XINGZHENGCHEAN="2";
	/**移送司法*/
	String ANJIANSHENCHA_YISONGSIFA="3";
	
	//已作出处罚步骤
	/**移送司法*/
	String ZUOCHUCHUFA_YISONGSIFA="2";
	
	//检查机关查阅步骤
	String JIANCHAYUAN_JIANYIYISONG="1";
	
	//taskType,将其赋值给caseStep，案件详情展示需要
	String VAR_TASK_TYPE="varTaskType";

	
	/** 行政处罚案件（行政处罚流程key）展示行政机关办理步骤流程图使用 */
	String PENALTY_PROC_KEY = "penaltyProc";
	/**行政处罚办理任务节点:开始节点*/
	String PENALTY_TASK_START = "startevent1";
	/**行政处罚办理任务节点：受理节点*/
	String PENALTY_TASK_SHOULI = "usertask1";
	/**行政处罚办理任务节点：初查核实节点*/
	String PENALTY_TASK_CHUCHAHESHI = "usertask2";
	/**行政处罚办理任务节点：行政不予立案节点*/
	String PENALTY_TASK_BUYULIAN = "endevent1";
	/**行政处罚办理任务节点：行政立案审批节点*/
	String PENALTY_TASK_LIAN = "usertask3";
	/**行政处罚办理任务节点：移送公安节点*/
	String PENALTY_TASK_YISONGGONGAN = "endevent2";
	/**行政处罚办理任务节点：行政撤案节点*/
	String PENALTY_TASK_CHEAN = "endevent3";
	/**行政处罚办理任务节点：已做出行政处罚节点*/
	String PENALTY_TASK_CHUFA = "usertask18";
	
	//线索状态
	/**待分派*/
	Integer CLUE_STATE_DAIFENPAI = 1;
	/**已分派*/
	Integer CLUE_STATE_YIFENPAI = 2;
	/**已受理*/
	Integer CLUE_STATE_YISHOULI = 3;
	/**已回复*/
	Integer CLUE_STATE_YIHUIFU = 4;
	/**已办理*/
	Integer CLUE_STATE_YIBANLI = 5;

	
	/**预警类型：超时预警*/
	public static int WARN_CONDITION_TYPE_1= 1;
	/**预警类型：涉案金额预警*/
	public static int WARN_CONDITION_TYPE_2= 2;
	/**预警类型：案件滞留超时预警*/
	public static int WARN_CONDITION_TYPE_3 = 3;
	
	/**公安机关类型：1 普通公安*/
    public static int POLICE_TYPE_1 = 1;
    /**公安机关类型：2 森林公安*/
    public static int POLICE_TYPE_2 = 2;
	
    /**查询范围：1 包含下级 2本级*/
    public static Integer QUERY_SCOPE_1 = 1;
    /**查询范围：1 包含下级 2 本级*/
    public static Integer QUERY_SCOPE_2 = 2;
    
}
