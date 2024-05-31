package th.co.ais.mimo.debt.exempt.service;

import java.util.List;

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


public interface DMSEM005ConfirmAssignService {
	List<DMSEM005SearchDataDto> searchData(DMSEM005SearchDataRequest request) throws ExemptException;

	List<DMSEM005QueryAssignDto> queryAssign(DMSEM005QueryAssignRequest request) throws ExemptException;

	DMSEM005UpdateExemptInfoResponse updateExemptInfo(DMSEM005UpdateExemptInfoRequest request) throws ExemptException;
	
	DMSEM005UpdateExemptInfoResponse deleteInformation(DMSEM005UpdateExemptInfoRequest request) throws ExemptException;
	
	DMSEM005GetExemptDescriptionResponse getExemptDescription(DMSEM005GetExemptDescriptionRequest request) throws ExemptException;
	
	DMSEM005GetReasonDescriptionResponse getReasonDescription(DMSEM005GetReasonDescriptionRequest request) throws ExemptException;
}
