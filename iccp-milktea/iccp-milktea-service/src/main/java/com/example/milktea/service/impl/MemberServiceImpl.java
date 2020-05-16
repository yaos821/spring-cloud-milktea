package com.example.milktea.service.impl;

import com.example.common.dto.SearchDTO;
import com.example.common.util.PageResult;
import com.example.common.vo.JSONResultVO;
import com.example.milktea.mapper.MemberDOMapper;
import com.example.milktea.pojo.MemberDO;
import com.example.milktea.pojo.MemberDOExample;
import com.example.milktea.pojo.MemberDOExample.Criteria;
import com.example.milktea.service.MemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.common.vo.JSONResultVO.CODE_ERROR;
import static com.example.common.vo.JSONResultVO.CODE_SUCCESS;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDOMapper memberDOMapper;

	@Override
	@Transactional(readOnly = true)
	public JSONResultVO pageList(SearchDTO<MemberDO> query) {
		MemberDOExample example = new MemberDOExample();
		Criteria criteria = example.createCriteria();
		//TODO edit your query condition
		PageHelper.startPage(query.getPage(), query.getLimit(), query.getSort());
		List<MemberDO> list = memberDOMapper.selectByExample(example);
		return JSONResultVO.builder()
				.data(PageResult.build(new PageInfo<>(list)))
				.code(CODE_SUCCESS)
				.message("客户信息分页列表查询成功").build();
	}

	@Override
	@Transactional(readOnly = true)
	public JSONResultVO get(Long id) {
		checkNotNull(id, "param id is null");
		return JSONResultVO.builder()
				.data(memberDOMapper.selectByPrimaryKey(id))
				.code(CODE_SUCCESS)
				.message("客户信息详情查询成功").build();
	}

	@Override
	@Transactional
	public JSONResultVO add(MemberDO record) {
		memberDOMapper.insertSelective(record);
		return JSONResultVO.builder()
				.code(CODE_SUCCESS)
				.message("客户信息添加成功").build();
	}

	@Override
	@Transactional
	public JSONResultVO delete(Long id) {
		checkNotNull(id, "param id is null");
		memberDOMapper.deleteByPrimaryKey(id);
		return JSONResultVO.builder()
				.code(CODE_SUCCESS)
				.message("客户信息删除成功").build();
	}

	@Override
	@Transactional
	public JSONResultVO update(MemberDO record) {
		checkNotNull(record.getId(), "record's id is null");
		memberDOMapper.updateByPrimaryKeySelective(record);
		return JSONResultVO.builder()
				.code(CODE_SUCCESS)
				.message("客户信息修改成功").build();
	}

	@Override
	@Transactional
	public JSONResultVO updateCAS(MemberDO record) {
		throw new IllegalAccessError("无法访问的方法");
	}

	@Override
	@Transactional(readOnly = true)
	public JSONResultVO listBy(MemberDO query) {
		MemberDOExample example = new MemberDOExample();
		Criteria criteria = example.createCriteria();
		//TODO edit your query condition
		return JSONResultVO.builder()
				.data(memberDOMapper.selectByExample(example))
				.code(CODE_SUCCESS)
				.message("客户信息列表查询成功").build();
	}

	@Override
	@Transactional(readOnly = true)
	public JSONResultVO getBy(MemberDO query) {
		MemberDOExample example = new MemberDOExample();
		Criteria criteria = example.createCriteria();
		//TODO edit your query condition
		List<MemberDO> result = memberDOMapper.selectByExample(example);
		checkState(result.size()<2, "multy result by query");
		if (result.isEmpty()) {
			return JSONResultVO.builder()
					.code(CODE_ERROR)
					.message("客户信息列表查询失败").build();
		}
		return JSONResultVO.builder()
				.data(result.get(0))
				.code(CODE_SUCCESS)
				.message("客户信息列表查询成功").build();
	}
}
