/*资源共享、执法动态、通知公告、法律法规、电子邮件、论坛、专项活动*/
truncate table file_resource;
truncate table file_org;

truncate table zhifa_info;
truncate table zhifa_info_type;

truncate table notice;
truncate table notice_file;
truncate table notice_org;
truncate table notice_read;

truncate table lay_info;
truncate table info_type;

truncate table USER_MSG;

truncate table THEME_REPLY;
truncate table FORUM_THEME;
truncate table FORUM_SECTION;

truncate table ACTIVITY_MEMBER;
truncate table SPECIAL_ACTIVITY;

/*打侵打假*/
truncate table DQDJ_CASE_CATEGORY --案件关联表
--truncate table DQDJ_CATEGORY --打侵打假类别表

/*行政许可*/
truncate table ADMDIV_LICENSE_INFO
truncate table ADMDIV_LICENSE_APPROVE
truncate table ADMDIV_LICENSE_REPLY

/*专项活动关联表*/
truncate table ACTIVITY_CASE

/*简易案件、案件咨询*/
truncate table SIMPLE_CASE_;

truncate table PARTICIPANTS;
truncate table CASE_CONSULTATION_CONTENT;
truncate table CASE_CONSULTATION;

/*移送职务犯罪案件、移送违纪案件*/
truncate table CASE_DUTY;
TRUNCATE table ZWFZ_CASE_PARTY;
truncate table CASE_WEIJI;
TRUNCATE table WJ_CASE_PARTY;

/*行政违法案件*/
truncate table CASE_BASIC;
truncate table CASE_STATE;
TRUNCATE TABLE CASE_PROCESS;
TRUNCATE TABLE PENALTY_CASE_EXT;
TRUNCATE TABLE CRIME_CASE_EXT;
TRUNCATE TABLE PENALTY_REFER_CASE_EXT;

truncate table CASE_ATTACHMENT;
truncate table CASE_PARTY;
truncate table CASE_COMPANY;
truncate table CASE_XIANYIREN;
truncate table CASE_STEP;
truncate table FIELD_INSTANCE;
truncate table FORM_INSTANCE;
truncate table ACT_RU_VARIABLE;
truncate table ACT_RU_IDENTITYLINK;
delete from ACT_RU_TASK;
delete from ACT_RU_EXECUTION;
commit;
truncate table TAKEOFF_ADMINISTRATIVE_ORGAN;/*行政移送*/
truncate table CASE_ACCUSE;
truncate table XIANYIREN_ACCUSE;
truncate table CASE_REPLY;/*案件批复*/
truncate table CASE_JIEAN_NOTICE;
truncate table CASE_SUPERVISION;

/*流程信息*/
truncate table ACT_HI_ACTINST;
truncate table ACT_HI_COMMENT;
truncate table ACT_HI_DETAIL;
truncate table ACT_HI_PROCINST;
truncate table ACT_HI_TASKINST;
truncate table ACT_HI_PROCINST;

truncate table ACT_RU_IDENTITYLINK;
truncate table ACT_RU_VARIABLE;


/*预警信息、日志、个人库、企业库*/
truncate table TIMEOUT_WARN;
truncate table TIMEOUT_WARN_REMINDER;

truncate table BUSINESS_LOG;
truncate table PEOPLE_LIB;
truncate table COMPANY_LIB;

/*网站*/
DELETE FROM WEB_ARTICLE_TYPE t WHERE t.is_default = 1;
truncate table WEB_ARTICLE;
truncate table WEB_FORUM;
truncate table WEB_FRIENDLY_LINK;

/*案件批量导入表*/
truncate table CASE_IMPORT;

/*专项立案填报*/
truncate table TB_ZXLA_TJ;

/*督办案件*/
truncate table SUPERVISE_CASE;

/*行政许可*/
truncate table ADMDIV_LICENSE_APPROVE;
truncate table ADMDIV_LICENSE_FILED;
truncate table ADMDIV_LICENSE_INFO;
truncate table ADMDIV_LICENSE_OPTION;
truncate table ADMDIV_LICENSE_REPLY;

/*邮件*/
truncate table LF_MAIL_FILE;
truncate table LF_MAIL_LINK;
truncate table LF_MAIL_RECEIVED_INFO;
truncate table LF_MAIL_SEND_INFO;

/*SYSTEM_INFO表是初始化授权文件用的，只有一条数据，如果修改行政区划，这个表的数据也要修改*/
