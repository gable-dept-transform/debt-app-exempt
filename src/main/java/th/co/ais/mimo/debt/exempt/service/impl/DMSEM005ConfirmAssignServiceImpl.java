package th.co.ais.mimo.debt.exempt.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.DMSEM005ConfirmAssignDao;
import th.co.ais.mimo.debt.exempt.dto.DMSEM005QueryAssignDto;
import th.co.ais.mimo.debt.exempt.dto.DMSEM005SearchDataDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetExemptDescriptionRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetExemptDescriptionResponse;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetReasonDescriptionRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetReasonDescriptionResponse;
import th.co.ais.mimo.debt.exempt.model.DMSEM005QueryAssignRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005SearchDataRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005UpdateExemptInfoRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005UpdateExemptInfoResponse;
import th.co.ais.mimo.debt.exempt.service.DMSEM005ConfirmAssignService;

@Service
@Transactional
public class DMSEM005ConfirmAssignServiceImpl implements DMSEM005ConfirmAssignService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	DMSEM005ConfirmAssignDao dmsem005ConfirmAssignDao;

	public List<DMSEM005SearchDataDto> searchData(DMSEM005SearchDataRequest request) throws ExemptException {
		return this.dmsem005ConfirmAssignDao.searchData(request);
	}

	public List<DMSEM005QueryAssignDto> queryAssign(DMSEM005QueryAssignRequest request) throws ExemptException {
		return this.dmsem005ConfirmAssignDao.queryAssign(request);
	}

	public DMSEM005UpdateExemptInfoResponse updateExemptInfo(DMSEM005UpdateExemptInfoRequest request)
			throws ExemptException {
		log.info("updateExemptInfo request : {}", request);
		DMSEM005UpdateExemptInfoResponse result = new DMSEM005UpdateExemptInfoResponse();
		try {

			if (StringUtils.isBlank(request.getModeId())) {
				return setErrorAndReturn(result, "Mode Id is required");
			}
			if (!AppConstant.EM.equals(request.getModeId())) {
				return setErrorAndReturn(result, "Mode not mapping");
			}
			if (StringUtils.isBlank(request.getPreAssignId())) {
				return setErrorAndReturn(result, "Pre Assign Id is required");
			}
			if (StringUtils.isBlank(request.getConfirmAssignFlag())) {
				return setErrorAndReturn(result, "Confirm Assign Flag is required");
			}
			if (StringUtils.isBlank(request.getUsername())) {
				return setErrorAndReturn(result, "Username is required");
			}
			if (AppConstant.FLAG_Y.equals(request.getConfirmAssignFlag())) {
				result = this.dmsem005ConfirmAssignDao.updateExemptInfo(request);
			} else {
				return setErrorAndReturn(result, "Exempt Criteria History Data not found");
			}
		} catch (PersistenceException | IllegalArgumentException e) {
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		}
		return result;
	}

	public DMSEM005UpdateExemptInfoResponse deleteInformation(DMSEM005UpdateExemptInfoRequest request)
			throws ExemptException {
		log.info("deleteInformation request: {}", request);
		DMSEM005UpdateExemptInfoResponse result = new DMSEM005UpdateExemptInfoResponse();
		try {
			if (StringUtils.isBlank(request.getModeId())) {
				return setErrorAndReturn(result, "Mode Id is required");
			}
			if (!AppConstant.EM.equals(request.getModeId())) {
				return setErrorAndReturn(result, "Mode not mapping");
			}
			if (StringUtils.isBlank(request.getPreAssignId())) {
				return setErrorAndReturn(result, "Pre Assign Id is required");
			}
			if (StringUtils.isBlank(request.getUsername())) {
				return setErrorAndReturn(result, "Username is required");
			}

			result = this.dmsem005ConfirmAssignDao.deleteInformation(request);
		} catch (PersistenceException | IllegalArgumentException e) {
			result.setErrorMsg(e.getMessage());
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		}
		return result;

	}

	private DMSEM005UpdateExemptInfoResponse setErrorAndReturn(DMSEM005UpdateExemptInfoResponse response,
			String errorMsg) {
		response.setErrorMsg(errorMsg);
		return response;
	}

	@Override
	public DMSEM005GetExemptDescriptionResponse getExemptDescription(DMSEM005GetExemptDescriptionRequest request)
			throws ExemptException {
		log.info("getExemptDescription request: {}", request);
		DMSEM005GetExemptDescriptionResponse result = new DMSEM005GetExemptDescriptionResponse();
		try {
			if (StringUtils.isBlank(request.getSectionName())) {
				result.setErrorMsg("sectionName is required");
				return result;
			}
			if (StringUtils.isBlank(request.getKeyword())) {
				result.setErrorMsg("Keyword is required");
				return result;
			}
			if (StringUtils.isBlank(request.getKeywordValue())) {
				result.setErrorMsg("keywordValue is required");
				return result;
			}

			result = this.dmsem005ConfirmAssignDao.getExemptDescription(request);
		} catch (PersistenceException | IllegalArgumentException e) {
			result.setErrorMsg(e.getMessage());
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		}
		return result;
	}

	@Override
	public DMSEM005GetReasonDescriptionResponse getReasonDescription(DMSEM005GetReasonDescriptionRequest request)
			throws ExemptException {
		log.info("getExemptDescription request: {}", request);
		DMSEM005GetReasonDescriptionResponse result = new DMSEM005GetReasonDescriptionResponse();
		try {
			if (StringUtils.isBlank(request.getReasonCode())) {
				result.setErrorMsg("reasonCode is required");
				return result;
			}
			if (StringUtils.isBlank(request.getReasonType())) {
				result.setErrorMsg("reasonType is required");
				return result;
			}

			result = this.dmsem005ConfirmAssignDao.getReasonDescription(request);
		} catch (PersistenceException | IllegalArgumentException e) {
			result.setErrorMsg(e.getMessage());
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		}
		return result;
	}

}
