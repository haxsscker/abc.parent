package com.autoserve.abc.service.biz.intf.cash;

import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface CheckInvestService {

	public PlainResult<Integer> checkInvest(Invest pojo);
}
