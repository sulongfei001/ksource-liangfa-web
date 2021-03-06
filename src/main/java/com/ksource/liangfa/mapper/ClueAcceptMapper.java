package com.ksource.liangfa.mapper;

import com.ksource.liangfa.domain.ClueAccept;
import com.ksource.liangfa.domain.ClueAcceptExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ClueAcceptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_ACCEPT
     *
     * @mbggenerated Tue May 16 10:37:18 CST 2017
     */
    int countByExample(ClueAcceptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_ACCEPT
     *
     * @mbggenerated Tue May 16 10:37:18 CST 2017
     */
    int deleteByExample(ClueAcceptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_ACCEPT
     *
     * @mbggenerated Tue May 16 10:37:18 CST 2017
     */
    int insert(ClueAccept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_ACCEPT
     *
     * @mbggenerated Tue May 16 10:37:18 CST 2017
     */
    int insertSelective(ClueAccept record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_ACCEPT
     *
     * @mbggenerated Tue May 16 10:37:18 CST 2017
     */
    List<ClueAccept> selectByExample(ClueAcceptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_ACCEPT
     *
     * @mbggenerated Tue May 16 10:37:18 CST 2017
     */
    int updateByExampleSelective(@Param("record") ClueAccept record, @Param("example") ClueAcceptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table LIANGFA_XINXIANG20170222.CLUE_ACCEPT
     *
     * @mbggenerated Tue May 16 10:37:18 CST 2017
     */
    int updateByExample(@Param("record") ClueAccept record, @Param("example") ClueAcceptExample example);
    
    /**
     * 查询线索办理情况
     * @param map
     * @return
     */
    List<ClueAccept> getClueTransactInfo(Map<String,Object> map);
}